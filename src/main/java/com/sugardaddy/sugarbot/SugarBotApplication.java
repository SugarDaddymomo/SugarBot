package com.sugardaddy.sugarbot;

import com.sugardaddy.sugarbot.commands.Menu;
import com.sugardaddy.sugarbot.events.Gaali;
import com.sugardaddy.sugarbot.events.HelloEvent;
import com.sugardaddy.sugarbot.music.CommandPlay;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SugarBotApplication implements CommandLineRunner {

    @Autowired
    JDA jda;

    public static void main(String[] args) {

        SpringApplication.run(SugarBotApplication.class, args);

        // To be fixed later
//        CommandClientBuilder commandClientBuilder = new CommandClientBuilder();
//        commandClientBuilder.setPrefix("$");
//        commandClientBuilder.setOwnerId("1088057090578321468");
//        commandClientBuilder.setHelpWord("help");
//        commandClientBuilder.addCommand(new ServerInfo());
//
//        CommandClient commandClient = commandClientBuilder.build();
//        jda.addEventListener(commandClient);
    }

    @Override
    public void run(String... args) throws Exception {
        jda.addEventListener(new HelloEvent());
        jda.addEventListener(new Menu());
        jda.addEventListener(new Gaali());
        jda.addEventListener(new CommandPlay());
        jda.getPresence().setActivity(Activity.watching("SERVER"));
    }
}
