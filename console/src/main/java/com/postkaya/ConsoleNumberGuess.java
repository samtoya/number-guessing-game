package com.postkaya;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Slf4j
@Component
public class ConsoleNumberGuess {
    private final MessageGenerator messageGenerator;
    private final Game game;

    @Autowired
    public ConsoleNumberGuess(MessageGenerator messageGenerator, Game game) {
        this.messageGenerator = messageGenerator;
        this.game = game;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void start(ContextRefreshedEvent event) {
        log.info("start() -> Container ready for use.");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(messageGenerator.getMainMessage());
            System.out.println(messageGenerator.getResultMessage());

            int guess = scanner.nextInt();
            scanner.nextLine();
            game.setGuess(guess);
            game.check();

            if (game.isGameWon() || game.isGameLost()) {
                System.out.println(messageGenerator.getResultMessage());
                System.out.println("Play again y/n");

                String playAgainString = scanner.nextLine().trim();
                if (!playAgainString.equals("y")) {
                    break;
                }

                game.reset();
            }
        }
    }
}
