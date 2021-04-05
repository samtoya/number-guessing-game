package com.postkaya;

import com.postkaya.config.GameConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Guess the number game!");

        // Create context
        ConfigurableApplicationContext context =
                new AnnotationConfigApplicationContext(GameConfig.class);

        // Get the number generator bean from context (container)
        NumberGenerator numberGenerator
                = context.getBean(NumberGenerator.class);

        int number = numberGenerator.next();

        log.info("number = {}", number);

        // Get the game bean from context (container)
        Game game = context.getBean(Game.class);

        // Get the message generator bean from context
        MessageGenerator messageGenerator = context.getBean(MessageGenerator.class);
        String mainMessage = messageGenerator.getMainMessage();
        log.info("Main message = {}", mainMessage);

        String resultMessage = messageGenerator.getResultMessage();
        log.info("Result message = {}", resultMessage);

        // close context
        context.close();
    }
}
