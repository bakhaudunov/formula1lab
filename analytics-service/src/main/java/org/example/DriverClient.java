package org.example;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "driver-service")
public interface DriverClient {

    @GetMapping("/drivers")
    List<DriverDto> getAllDrivers();

    @GetMapping("/drivers/{id}")
    DriverDto getDriverById(@PathVariable("id") Long id);
}