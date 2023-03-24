package com.sugardaddy.sugarbot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {

    public final AudioPlayer audioPlayer;
    public final BlockingQueue<AudioTrack> blockingQueue;

    public TrackScheduler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        this.blockingQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }

    public void nextTrack() {
        this.audioPlayer.startTrack(this.blockingQueue.poll(), false);
    }

    public void queue(AudioTrack audioTrack) {
        if (!this.audioPlayer.startTrack(audioTrack, true)) {
            this.blockingQueue.offer(audioTrack);
        }
    }
}