package com.sugardaddy.sugarbot.events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.*;

public class Gaali extends ListenerAdapter {

    static final List<String> GAALI = Arrays.asList("bsdk", "bc", "randi",
            "gaand", "mkc", "saale", "bhadwe",
            "chutiye", "bkl", "chutiya",
            "mf", "asshole", "motherfucker", "behenchod",
            "nigga", "bitch", "cunt",
            "bitch ass nigga");

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getMessage().getAuthor().isBot()) {
            Random random = new Random();
            List<String> list = Arrays.asList(event.getMessage().getContentRaw().split(" "));
            for (String s : list) {
                if (GAALI.contains(s)) {
                    event.getChannel().sendMessage(GAALI.get(random.nextInt((GAALI.size())))+" "+event.getAuthor().getName()).queue();
                    break;
                }
            }
        }
    }
}