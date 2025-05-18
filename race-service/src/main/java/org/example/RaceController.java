package org.example;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/races")
public class RaceController {

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private DriverClient driverClient;

    @GetMapping("/{id}")
    public ResponseEntity<RaceResponse> getRaceById(@PathVariable Long id) {
        return raceRepository.findById(id)
                .map(race -> {
                    List<DriverDto> drivers = driverClient.getAllDrivers();
                    return ResponseEntity.ok(RaceResponse.from(race, drivers));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Race> createRace(@Valid @RequestBody RaceRequest raceRequest) {
        Race race = new Race();
        race.setLocation(raceRequest.getLocation());
        race.setDate(raceRequest.getDate());

        List<RaceResult> results = new ArrayList<>();
        for (RaceResultRequest resultRequest : raceRequest.getResults()) {
            RaceResult result = new RaceResult();
            result.setDriverId(resultRequest.getDriverId());
            result.setPosition(resultRequest.getPosition());
            result.setRace(race);
            results.add(result);
        }

        race.setResults(results);
        Race savedRace = raceRepository.save(race);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRace);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRace(@PathVariable Long id) {
        if (raceRepository.existsById(id)) {
            raceRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @GetMapping("/winner")
    public ResponseEntity<RaceWinnerResponse> getLatestRaceWinner() {
        Optional<Race> latestRaceOpt = raceRepository.findLatestRace();

        if (latestRaceOpt.isPresent()) {
            Race latestRace = latestRaceOpt.get();
            Optional<RaceResult> winnerResultOpt = latestRace.getResults().stream()
                    .filter(result -> result.getPosition() == 1)
                    .findFirst();

            if (winnerResultOpt.isPresent()) {
                RaceResult winnerResult = winnerResultOpt.get();
                DriverDto winner = driverClient.getDriverById(winnerResult.getDriverId());

                RaceWinnerResponse response = new RaceWinnerResponse(
                        latestRace.getLocation(),
                        latestRace.getDate(),
                        winner
                );

                return ResponseEntity.ok(response);
            }
        }

        return ResponseEntity.notFound().build();
    }
}