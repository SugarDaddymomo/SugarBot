package com.sugardaddy.sugarbot.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
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
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueCommand extends ListenerAdapter {

    Logger logger = LoggerFactory.getLogger(QueueCommand.class);

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        final TextChannel textChannel = event.getChannel().asTextChannel();
        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();
        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        final GuildMusicManager guildMusicManager = PlayerManager.getINSTANCE().getGuildMusicManager(event.getGuild());
        final BlockingQueue<AudioTrack> queue = guildMusicManager.trackScheduler.blockingQueue;

        try {
            if (event.getMessage().getContentRaw().equalsIgnoreCase("$queue") && !event.getMessage().getAuthor().isBot()) {
                if (!selfVoiceState.inAudioChannel() && !event.getMessage().getAuthor().isBot()) {
                    textChannel.sendMessage("I need to be in voice channel for this command to work.").queue();
                    return;
                }
                if (!memberVoiceState.inAudioChannel() && !event.getMessage().getAuthor().isBot()) {
                    textChannel.sendMessage("You need to be in voice channel for this command to work!").queue();
                    return;
                }
                logger.info("queue command used");
                if (queue.isEmpty()) {
                    textChannel.sendMessage("The queue is currently empty.").queue();
                    return;
                } else {
                    final int trackCount = Math.min(queue.size(), 20);
                    final List<AudioTrack> tracks = new ArrayList<>(queue);
                    final MessageCreateAction action = textChannel.sendMessage("**Current Queue:**\n");

                    for (int i=0; i<trackCount; i++) {
                        final AudioTrack audioTrack = tracks.get(i);
                        final AudioTrackInfo audioTrackInfo = audioTrack.getInfo();
                        action.setMessageReference("#"+
                                String.valueOf(i+1)+
                                " `"+
                                audioTrackInfo.title+" by "+audioTrackInfo.author+
                                "` [`"+formatTime(audioTrack.getDuration())+
                                "`]\n");
                    }

                    if (tracks.size() > trackCount) {
                        action.setMessageReference("And `" +
                                String.valueOf(tracks.size() - trackCount) +
                                "` more...");
                    }

                }
            }
        } catch (Exception e) {
            logger.error("error occured");
            if (!event.getMessage().getAuthor().isBot()) {
                textChannel.sendMessage("You need to be in the same voice channel as me for this to work!").queue();
            }
        }
    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
