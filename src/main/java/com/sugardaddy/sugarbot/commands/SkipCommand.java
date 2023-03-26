package com.sugardaddy.sugarbot.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sugardaddy.sugarbot.music.GuildMusicManager;
import com.sugardaddy.sugarbot.music.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkipCommand extends ListenerAdapter {

    Logger logger = LoggerFactory.getLogger(SkipCommand.class);

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        final TextChannel textChannel = event.getChannel().asTextChannel();
        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();
        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        final GuildMusicManager guildMusicManager = PlayerManager.getINSTANCE().getGuildMusicManager(event.getGuild());

        try {
            if (event.getMessage().getContentRaw().equalsIgnoreCase("$skip") && !event.getMessage().getAuthor().isBot()) {
                if (!selfVoiceState.inAudioChannel() && !event.getMessage().getAuthor().isBot()) {
                    textChannel.sendMessage("I need to be in voice channel for this command to work.").queue();
                    return;
                }
                if (!memberVoiceState.inAudioChannel() && !event.getMessage().getAuthor().isBot()) {
                    textChannel.sendMessage("You need to be in voice channel for this command to work!").queue();
                    return;
                }
                logger.info("skip command used");
                final AudioPlayer audioPlayer = guildMusicManager.audioPlayer;
                if (audioPlayer.getPlayingTrack() == null) {
                    textChannel.sendMessage("There is no track playing currently!").queue();
                    return;
                } else {
                    guildMusicManager.trackScheduler.nextTrack();
                    textChannel.sendMessage("Skipped the current track").queue();
                }
            }
        } catch (Exception e) {
            logger.error("error occured");
            if (!event.getMessage().getAuthor().isBot()) {
                textChannel.sendMessage("You need to be in the same voice channel as me for this to work!").queue();
            }
        }
    }
}
