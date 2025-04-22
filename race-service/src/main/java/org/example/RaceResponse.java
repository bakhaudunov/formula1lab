package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RaceResponse {
    private Long id;
    private String location;
    private LocalDate date;
    private List<RaceResultResponse> results;

    public static RaceResponse from(Race race, List<DriverDto> drivers) {
        List<RaceResultResponse> resultResponses = race.getResults().stream()
                .map(result -> {
                    DriverDto driver = drivers.stream()
                            .filter(d -> d.getId().equals(result.getDriverId()))
                            .findFirst()
                            .orElse(null);
                    return new RaceResultResponse(
                            result.getId(),
                            driver,
                            result.getPosition()
                    );
                })
                .collect(Collectors.toList());

        return new RaceResponse(
                race.getId(),
                race.getLocation(),
                race.getDate(),
                resultResponses
        );
    }
}
