package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private DriverStatisticsRepository driverStatisticsRepository;

    @Autowired
    private TeamStatisticsRepository teamStatisticsRepository;

    @GetMapping("/drivers/{driverId}")
    public ResponseEntity<DriverStatisticsResponse> getDriverStatistics(@PathVariable Long driverId) {
        return driverStatisticsRepository.findById(driverId)
                .map(stats -> ResponseEntity.ok(analyticsService.enrichDriverStatistics(stats)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/teams/{teamId}")
    public ResponseEntity<TeamStatisticsResponse> getTeamStatistics(@PathVariable Long teamId) {
        return teamStatisticsRepository.findById(teamId)
                .map(stats -> ResponseEntity.ok(analyticsService.enrichTeamStatistics(stats)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/drivers/ranking")
    public ResponseEntity<List<DriverRankingResponse>> getDriverRanking() {
        return ResponseEntity.ok(analyticsService.getDriverRanking());
    }

    @GetMapping("/teams/ranking")
    public ResponseEntity<List<TeamRankingResponse>> getTeamRanking() {
        return ResponseEntity.ok(analyticsService.getTeamRanking());
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshAllStatistics() {
        analyticsService.recalculateAllStatistics();
        return ResponseEntity.ok().build();
    }
}