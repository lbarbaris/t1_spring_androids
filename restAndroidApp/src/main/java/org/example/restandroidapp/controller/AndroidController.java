package org.example.restandroidapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.restandroidapp.dto.AndroidDto;
import org.example.syntheticcorestarter.android.Android;
import org.example.syntheticcorestarter.service.AndroidService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/androids")
@RequiredArgsConstructor
public class AndroidController {

    private final AndroidService androidService;

    @PostMapping("/{id}")
    public ResponseEntity<AndroidDto> createAndroid(
            @PathVariable Long id,
            @RequestParam(defaultValue = "10") int queueCapacity) {

        var android = androidService.createAndroid(id, queueCapacity);
        var dto = mapToDTO(android);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AndroidDto> getAndroid(@PathVariable Long id) {
        var androidOpt = androidService.getAndroid(id);
        return androidOpt
                .map(this::mapToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchElementException("No android found"));

    }

    @GetMapping
    public ResponseEntity<List<AndroidDto>> getAllAndroids() {
        var dtoList = androidService.getAllAndroids()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAndroid(@PathVariable Long id) {
        androidService.deleteAndroid(id);
        return ResponseEntity.noContent().build();
    }

    private AndroidDto mapToDTO(Android android) {
        return new AndroidDto(
                android.getId(),
                android.getCompletedTasksByAuthor()
        );
    }
}
