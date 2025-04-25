package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandingDto {
    private Long driverId;
    private String driverName;
    private Integer points;
    private Integer position;
}