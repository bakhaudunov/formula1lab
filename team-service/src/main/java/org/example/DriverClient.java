package org.example;

import org.example.DriverDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "driver-service")
public interface DriverClient {

    @GetMapping("/drivers/team/{teamId}")
    List<DriverDto> getDriversByTeamId(@PathVariable("teamId") Long teamId);
}