package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChampionshipService {

    @Autowired
    private ChampionshipRepository championshipRepository;

    @Autowired
    private DriverClient driverClient;

    @Autowired
    private RaceClient raceClient;

    public Championship createChampionship(ChampionshipRequest request) {
        Championship championship = new Championship();
        championship.setName(request.getName());
        championship.setYear(request.getYear());
        return championshipRepository.save(championship);
    }

    public ChampionshipResponse createChampionshipResponse(Championship championship, List<DriverDto> drivers) {
        Map<Long, String> driverMap = drivers.stream()
                .collect(Collectors.toMap(DriverDto::getId, DriverDto::getName));

        List<StandingDto> standingDtos = championship.getStandings().stream()
                .map(standing -> {
                    StandingDto dto = new StandingDto();
                    dto.setDriverId(standing.getDriverId());
                    dto.setDriverName(driverMap.getOrDefault(standing.getDriverId(), "Unknown Driver"));
                    dto.setPoints(standing.getPoints());
                    dto.setPosition(standing.getPosition());
                    return dto;
                })
                .collect(Collectors.toList());

        ChampionshipResponse response = new ChampionshipResponse();
        response.setId(championship.getId());
        response.setName(championship.getName());
        response.setYear(championship.getYear());
        response.setStandings(standingDtos);
        return response;
    }

    public List<ChampionshipStandingResponse> getStandingsByYear(Integer year) {
        Championship championship = championshipRepository.findByYear(year);
        if (championship == null) {
            return List.of();
        }

        List<DriverDto> drivers = driverClient.getAllDrivers();
        Map<Long, DriverDto> driverMap = drivers.stream()
                .collect(Collectors.toMap(DriverDto::getId, driver -> driver));

        return championship.getStandings().stream()
                .map(standing -> {
                    DriverDto driver = driverMap.getOrDefault(standing.getDriverId(), new DriverDto());

                    ChampionshipStandingResponse response = new ChampionshipStandingResponse();
                    response.setDriverId(standing.getDriverId());
                    response.setDriverName(driver.getName());
                    response.setTeamName(driver.getTeamName());
                    response.setPoints(standing.getPoints());
                    response.setPosition(standing.getPosition());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public Optional<Championship> calculateStandings(Long championshipId) {
        Optional<Championship> championshipOpt = championshipRepository.findById(championshipId);

        if (championshipOpt.isPresent()) {
            Championship championship = championshipOpt.get();

            // Получаем результаты гонок за год чемпионата
            List<RaceResultDto> raceResults = raceClient.getRaceResultsByYear(championship.getYear());

            // Вычисляем очки для каждого пилота
            Map<Long, Integer> driverPoints = calculateDriverPoints(raceResults);

            // Обновляем или создаем standings для каждого пилота
            List<ChampionshipStanding> standings = driverPoints.entrySet().stream()
                    .map(entry -> {
                        Long driverId = entry.getKey();
                        Integer points = entry.getValue();

                        ChampionshipStanding standing = championship.getStandings().stream()
                                .filter(s -> s.getDriverId().equals(driverId))
                                .findFirst()
                                .orElse(new ChampionshipStanding());

                        standing.setDriverId(driverId);
                        standing.setPoints(points);
                        standing.setChampionship(championship);

                        return standing;
                    })
                    .collect(Collectors.toList());

            // Сортируем и устанавливаем позиции
            standings.sort((s1, s2) -> Integer.compare(s2.getPoints(), s1.getPoints()));
            for (int i = 0; i < standings.size(); i++) {
                standings.get(i).setPosition(i + 1);
            }

            championship.setStandings(standings);
            return Optional.of(championshipRepository.save(championship));
        }

        return Optional.empty();
    }

    private Map<Long, Integer> calculateDriverPoints(List<RaceResultDto> results) {
        return results.stream()
                .collect(Collectors.groupingBy(
                        RaceResultDto::getDriverId,
                        Collectors.summingInt(this::getPointsForPosition)
                ));
    }

    private int getPointsForPosition(RaceResultDto result) {
        // Стандартная система начисления очков в Формуле 1
        switch (result.getPosition()) {
            case 1: return 25;
            case 2: return 18;
            case 3: return 15;
            case 4: return 12;
            case 5: return 10;
            case 6: return 8;
            case 7: return 6;
            case 8: return 4;
            case 9: return 2;
            case 10: return 1;
            default: return 0;
        }
    }
}