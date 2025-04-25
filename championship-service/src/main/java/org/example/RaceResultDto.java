package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RaceResultDto {
    private Long raceId;
    private Long driverId;
    private Integer position;
    private Boolean fastestLap;
    private Boolean dnf;
}