package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverStatisticsResponse {
    private Long driverId;
    private String driverName;
    private Integer totalRaces;
    private Integer wins;
    private Integer podiums;
    private Integer dnfs;
    private Double averagePosition;
    private Double winRate;
}