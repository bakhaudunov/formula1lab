package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamStatisticsResponse {
    private Long teamId;
    private String teamName;
    private Integer totalRaces;
    private Integer wins;
    private Integer podiums;
    private Integer constructorChampionships;
    private Double averagePosition;
    private Double winRate;
}