package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChampionshipStandingResponse {
    private Long driverId;
    private String driverName;
    private String teamName;
    private Integer points;
    private Integer position;
}