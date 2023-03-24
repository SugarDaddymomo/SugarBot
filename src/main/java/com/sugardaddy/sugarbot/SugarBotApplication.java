package com.sugardaddy.sugarbot;

import com.sugardaddy.sugarbot.commands.Menu;
import com.sugardaddy.sugarbot.events.Gaali;
import com.sugardaddy.sugarbot.events.HelloEvent;
import com.sugardaddy.sugarbot.music.CommandPlay;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SugarBotApplication {

    public static void main(String[] args) {
        JDA jda = JDABuilder.
                createDefault("MTA4ODA1NzA5MDU3ODMyMTQ2OA.G3X9kg.jNn9OGmsjzjR3bzDQ0g69pyxlpqX4qOJby67GE")
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
        jda.addEventListener(new HelloEvent());
        jda.addEventListener(new Menu());
        jda.addEventListener(new Gaali());
        jda.addEventListener(new CommandPlay());

//        CommandClientBuilder commandClientBuilder = new CommandClientBuilder();
//        commandClientBuilder.setPrefix("$");
//        commandClientBuilder.setOwnerId("1088057090578321468");
//        commandClientBuilder.setHelpWord("help");
//        commandClientBuilder.addCommand(new ServerInfo());
//
//        CommandClient commandClient = commandClientBuilder.build();
//        jda.addEventListener(commandClient);
        SpringApplication.run(SugarBotApplication.class, args);
    }

}
