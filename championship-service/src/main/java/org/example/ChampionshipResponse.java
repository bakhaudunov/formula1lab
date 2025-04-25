package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChampionshipResponse {
    private Long id;
    private String name;
    private Integer year;
    private List<StandingDto> standings;
}