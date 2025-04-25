package org.example;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "driver_statistics")
public class DriverStatistics {
    @Id
    private Long driverId;

    private Integer totalRaces;
    private Integer wins;
    private Integer podiums;
    private Integer dnfs; // Did Not Finish
    private Double averagePosition;
}