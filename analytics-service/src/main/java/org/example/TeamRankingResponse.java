package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamRankingResponse {
    private Long teamId;
    private String teamName;
    private Double rating;
    private Integer position;
}