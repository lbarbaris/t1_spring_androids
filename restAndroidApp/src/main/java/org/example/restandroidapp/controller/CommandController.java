package org.example.restandroidapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.restandroidapp.dto.CommandDto;
import org.example.syntheticcorestarter.command.Command;
import org.example.syntheticcorestarter.service.CommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/androids/{androidId}/commands")
@RequiredArgsConstructor
public class CommandController {

    private final CommandService commandService;

    @PostMapping
    public ResponseEntity<CommandDto> createCommand(
            @PathVariable Long androidId,
            @RequestBody CommandDto commandDto) {
        try {
            var command = mapToEntity(commandDto);
            commandService.createCommand(androidId, command);
            return ResponseEntity.ok(commandDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private Command mapToEntity(CommandDto dto) {
        return new Command(dto.time(), dto.priority(), dto.author(), dto.description());
    }
}

