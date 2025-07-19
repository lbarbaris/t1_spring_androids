package org.example.syntheticcorestarter.service;

import org.example.syntheticcorestarter.command.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandService {
    private final AndroidService androidService;

    public Command createCommand(Long androidId, Command command) {
        if (!androidService.androidExists(androidId)) {
            throw new IllegalArgumentException("Android with id " + androidId + " not found");
        }

        var android = androidService.getAndroid(androidId).orElseThrow();
        android.addCommand(command);
        return command;
    }

}