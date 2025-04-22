package org.example;

import org.example.Race;
import org.example.RaceResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RaceRequest {
    private String location;
    private LocalDate date;
    private List<RaceResultRequest> results;
}
