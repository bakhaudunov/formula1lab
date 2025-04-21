package org.example;

import org.example.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {
    private Long id;
    private String name;
    private String country;
    private List<DriverDto> drivers;

    public static TeamResponse from(Team team, List<DriverDto> drivers) {
        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getCountry(),
                drivers
        );
    }
}