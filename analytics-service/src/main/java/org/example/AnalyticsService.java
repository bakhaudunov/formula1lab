package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    @Autowired
    private DriverStatisticsRepository driverStatisticsRepository;

    @Autowired
    private TeamStatisticsRepository teamStatisticsRepository;

    @Autowired
    private DriverClient driverClient;

    @Autowired
    private TeamClient teamClient;

    public DriverStatisticsResponse enrichDriverStatistics(DriverStatistics stats) {
        // Получаем информацию о гонщике через Feign-клиент
        DriverDto driver = driverClient.getDriverById(stats.getDriverId());

        DriverStatisticsResponse response = new DriverStatisticsResponse();
        response.setDriverId(stats.getDriverId());
        response.setDriverName(driver != null ? driver.getName() : "Unknown");
        response.setTotalRaces(stats.getTotalRaces());
        response.setWins(stats.getWins());
        response.setPodiums(stats.getPodiums());
        response.setDnfs(stats.getDnfs());
        response.setAveragePosition(stats.getAveragePosition());

        // Рассчитываем процент побед
        double winRate = stats.getTotalRaces() > 0
                ? (double) stats.getWins() / stats.getTotalRaces() * 100
                : 0.0;
        response.setWinRate(winRate);

        return response;
    }

    public TeamStatisticsResponse enrichTeamStatistics(TeamStatistics stats) {
        // Получаем информацию о команде через Feign-клиент
        TeamDto team = teamClient.getTeamById(stats.getTeamId());

        TeamStatisticsResponse response = new TeamStatisticsResponse();
        response.setTeamId(stats.getTeamId());
        response.setTeamName(team != null ? team.getName() : "Unknown");
        response.setTotalRaces(stats.getTotalRaces());
        response.setWins(stats.getWins());
        response.setPodiums(stats.getPodiums());
        response.setConstructorChampionships(stats.getConstructorChampionships());
        response.setAveragePosition(stats.getAveragePosition());

        // Рассчитываем процент побед
        double winRate = stats.getTotalRaces() > 0
                ? (double) stats.getWins() / stats.getTotalRaces() * 100
                : 0.0;
        response.setWinRate(winRate);

        return response;
    }

    public List<DriverRankingResponse> getDriverRanking() {
        List<DriverStatistics> allStats = driverStatisticsRepository.findAll();
        List<DriverDto> allDrivers = driverClient.getAllDrivers();

        return allStats.stream()
                .map(stats -> {
                    DriverDto driver = findDriverById(allDrivers, stats.getDriverId());
                    double rating = calculateDriverRating(stats);

                    DriverRankingResponse ranking = new DriverRankingResponse();
                    ranking.setDriverId(stats.getDriverId());
                    ranking.setDriverName(driver != null ? driver.getName() : "Unknown");
                    ranking.setRating(rating);
                    return ranking;
                })
                .sorted(Comparator.comparing(DriverRankingResponse::getRating).reversed())
                .collect(Collectors.toList());
    }

    public List<TeamRankingResponse> getTeamRanking() {
        List<TeamStatistics> allStats = teamStatisticsRepository.findAll();
        List<TeamDto> allTeams = teamClient.getAllTeams();

        return allStats.stream()
                .map(stats -> {
                    TeamDto team = findTeamById(allTeams, stats.getTeamId());
                    double rating = calculateTeamRating(stats);

                    TeamRankingResponse ranking = new TeamRankingResponse();
                    ranking.setTeamId(stats.getTeamId());
                    ranking.setTeamName(team != null ? team.getName() : "Unknown");
                    ranking.setRating(rating);
                    return ranking;
                })
                .sorted(Comparator.comparing(TeamRankingResponse::getRating).reversed())
                .collect(Collectors.toList());
    }

    public void recalculateAllStatistics() {
        // Здесь логика пересчета статистики на основе данных из других сервисов
        // Например, получение данных о гонках из race-service и пересчет статистики
    }

    private DriverDto findDriverById(List<DriverDto> drivers, Long driverId) {
        return drivers.stream()
                .filter(d -> d.getId().equals(driverId))
                .findFirst()
                .orElse(null);
    }

    private TeamDto findTeamById(List<TeamDto> teams, Long teamId) {
        return teams.stream()
                .filter(t -> t.getId().equals(teamId))
                .findFirst()
                .orElse(null);
    }

    private double calculateDriverRating(DriverStatistics stats) {
        // Пример алгоритма расчета рейтинга пилота
        if (stats.getTotalRaces() == 0) {
            return 0.0;
        }

        double winFactor = stats.getWins() * 25.0;
        double podiumFactor = stats.getPodiums() * 10.0;
        double avgPosFactor = (10.0 - Math.min(stats.getAveragePosition(), 10.0)) * 5.0;
        double dnfPenalty = stats.getDnfs() * 5.0;

        return (winFactor + podiumFactor + avgPosFactor - dnfPenalty) / stats.getTotalRaces();
    }

    private double calculateTeamRating(TeamStatistics stats) {
        // Пример алгоритма расчета рейтинга команды
        if (stats.getTotalRaces() == 0) {
            return 0.0;
        }

        double winFactor = stats.getWins() * 25.0;
        double podiumFactor = stats.getPodiums() * 10.0;
        double championshipFactor = stats.getConstructorChampionships() * 50.0;
        double avgPosFactor = (10.0 - Math.min(stats.getAveragePosition(), 10.0)) * 5.0;

        return (winFactor + podiumFactor + championshipFactor + avgPosFactor) / stats.getTotalRaces();
    }
}