package com.sugardaddy.sugarbot.music;

import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class CommandPlay extends ListenerAdapter {

    Logger logger = LoggerFactory.getLogger(CommandPlay.class);

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String commandReal = event.getMessage().getContentRaw().substring(0, 5);
        String restCommand = event.getMessage().getContentRaw().substring(6);

        if (Objects.equals(commandReal, "$play")) {
            if (!event.getMember().getVoiceState().inAudioChannel()) {
                event.getChannel().sendMessage("You need to be in voice channel for this command to work!").queue();
            }
            final AudioManager audioManager = event.getGuild().getAudioManager();
            final VoiceChannel voiceChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();

            try {
                audioManager.openAudioConnection(voiceChannel);
            } catch (Exception e) {
                logger.error("error bhau");
            }

            String link = String.join(" ", restCommand);

            if (!isURL(link)) {
                link = "ytsearch:"+link+" audio";
            }
            logger.info(link);
            PlayerManager.getINSTANCE().loadAndPlay(event.getChannel().asTextChannel(), link);
        } else if (Objects.equals(commandReal, "$leave")) {
            logger.info("leave krna h?");
            if (!event.getMember().getVoiceState().inAudioChannel()) {
                event.getChannel().sendMessage("You need to be in voice channel for this command to work!").queue();
            }
            final AudioManager audioManager = event.getGuild().getAudioManager();

            try {
                audioManager.closeAudioConnection();
            } catch (Exception e) {
                logger.error("error bhau");
            }
        }
    }

    public boolean isURL(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
