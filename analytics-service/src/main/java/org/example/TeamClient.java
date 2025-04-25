package org.example;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "team-service")
public interface TeamClient {

    @GetMapping("/teams")
    List<TeamDto> getAllTeams();

    @GetMapping("/teams/{id}")
    TeamDto getTeamById(@PathVariable("id") Long id);
}