package com.sugardaddy.sugarbot.commands;

import com.sugardaddy.sugarbot.events.HelloEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Menu extends ListenerAdapter {

    Logger logger = LoggerFactory.getLogger(HelloEvent.class);

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Help Menu");
        embedBuilder.setColor(Color.YELLOW);
//        embedBuilder.setThumbnail("https://ibb.co/hW3VccJ");
        embedBuilder.setAuthor("Built By SugarDaddy");
        embedBuilder.addField("For commands", "$help", true);
        embedBuilder.addField("For music commands", "$musicHelp", true);
        embedBuilder.setFooter("For any queries and feedbacks reach out to me on Instagram: @sugardaddy_senpai");
        String eventMessage = event.getMessage().getContentRaw();
        if (eventMessage.equalsIgnoreCase("$help") && !event.getAuthor().isBot()) {
            logger.info("trying to send the help menu in text channel");
            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        }
    }
}
