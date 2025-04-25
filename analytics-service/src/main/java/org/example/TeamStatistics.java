package org.example;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "team_statistics")
public class TeamStatistics {
    @Id
    private Long teamId;

    private Integer totalRaces;
    private Integer wins;
    private Integer podiums;
    private Integer constructorChampionships;
    private Double averagePosition;
}