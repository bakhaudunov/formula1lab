package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RaceWinnerResponse {
    private String raceName;
    private LocalDate raceDate;
    private DriverDto winner;
}