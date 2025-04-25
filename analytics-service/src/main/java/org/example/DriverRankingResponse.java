package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverRankingResponse {
    private Long driverId;
    private String driverName;
    private Double rating;
    private Integer position;
}