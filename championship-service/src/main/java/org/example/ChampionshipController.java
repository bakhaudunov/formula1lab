package org.example;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/championships")
public class ChampionshipController {

    @Autowired
    private ChampionshipRepository championshipRepository;

    @Autowired
    private ChampionshipService championshipService;

    @Autowired
    private DriverClient driverClient;

    @GetMapping
    public ResponseEntity<List<Championship>> getAllChampionships() {
        List<Championship> championships = championshipRepository.findAll();
        return ResponseEntity.ok(championships);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChampionshipResponse> getChampionshipById(@PathVariable Long id) {
        return championshipRepository.findById(id)
                .map(championship -> {
                    List<DriverDto> drivers = driverClient.getAllDrivers();
                    return ResponseEntity.ok(championshipService.createChampionshipResponse(championship, drivers));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{year}/standings")
    public ResponseEntity<List<ChampionshipStandingResponse>> getStandingsByYear(@PathVariable Integer year) {
        return ResponseEntity.ok(championshipService.getStandingsByYear(year));
    }

    @PostMapping
    public ResponseEntity<Championship> createChampionship(@Valid @RequestBody ChampionshipRequest request) {
        Championship championship = championshipService.createChampionship(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(championship);
    }

    @PostMapping("/{id}/calculate")
    public ResponseEntity<Championship> calculateChampionshipStandings(@PathVariable Long id) {
        return championshipService.calculateStandings(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChampionship(@PathVariable Long id) {
        if (championshipRepository.existsById(id)) {
            championshipRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}