package org.example.restandroidapp;

import org.example.syntheticcorestarter.android.AndroidFactory;
import org.example.syntheticcorestarter.command.Command;
import org.example.syntheticcorestarter.command.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.example.syntheticcorestarter.android.Android;


@SpringBootApplication
public class RestAndroidAppApplication {

    @Autowired
    private AndroidFactory androidFactory;

    public static void main(String[] args) {
        SpringApplication.run(RestAndroidAppApplication.class, args);
    }


    @Bean
    public CommandLineRunner runner() {
        return args -> {
            try {
                var android = androidFactory.getOrCreate(1L, 1);

                // Добавляем команды
                //android.addCommand(new Command("fff", Priority.CRITICAL, "Alice", "Fix urgent bug in payment module"));
                android.addCommand(new Command("2025-07-17T09:30:00", Priority.CRITICAL, "Bob", "Refactor legacy login controller"));
                android.addCommand(new Command("2025-07-17T11:00:00", Priority.CRITICAL, "Charlie", "Write unit tests for user service"));
                android.addCommand(new Command("2025-07-17T11:00:00", Priority.CRITICAL, "Charlie", "Write unit tests for user service"));

                Thread.sleep(1000);

                var android2 = androidFactory.getOrCreate(2L, 5);

                // Добавляем команды
                //android2.addCommand(new Command("аааа", Priority.COMMON, "Alice", "Fix urgent bug in payment module"));
                android2.addCommand(new Command("2025-07-17T09:30:00", Priority.COMMON, "Bob", "Refactor legacy login controller"));
                android2.addCommand(new Command("2025-07-17T11:00:00", Priority.COMMON, "Charlie", "Write unit tests for user service"));
                android2.addCommand(new Command("2025-07-17T11:00:00", Priority.COMMON, "Charlie", "Write unit tests for user service"));
                android2.addCommand(new Command("2025-07-17T11:00:00", Priority.COMMON, "Charlie", "Write unit tests for user service"));
                android2.addCommand(new Command("2025-07-17T11:00:00", Priority.COMMON, "fff", "Write unit tests for user service"));

                // Ждём немного, чтобы увидеть выполнение
                Thread.sleep(5000);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
