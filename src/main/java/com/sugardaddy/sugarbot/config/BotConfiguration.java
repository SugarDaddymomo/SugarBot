package com.sugardaddy.sugarbot.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfiguration {

    Logger logger = LoggerFactory.getLogger(BotConfiguration.class);

    @Value("${discord.token}")
    private String token;

    @Bean
    public JDA getJda() {
        logger.info("token is: "+token);
        return JDABuilder.
                createDefault(token.trim())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
    }
}