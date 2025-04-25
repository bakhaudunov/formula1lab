package org.example;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "race-service")
public interface RaceClient {

    @GetMapping("/races/results/year/{year}")
    List<RaceResultDto> getRaceResultsByYear(@PathVariable("year") Integer year);
}