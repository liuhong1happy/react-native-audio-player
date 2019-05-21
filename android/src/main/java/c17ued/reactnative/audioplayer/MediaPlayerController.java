package com.c17ued.reactnative.audioplayer;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import android.media.MediaPlayer;

import java.io.IOException;

public class MediaPlayerController {
    private MediaPlayer mediaPlayer;

    public MediaPlayerController() {
        mediaPlayer = new MediaPlayer();
    }

    public int isPlaying() {
        boolean _isPlaying = mediaPlayer.isPlaying();
        return _isPlaying ? 1 : 0;
    }

    public void start() {
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() throws IOException {
        mediaPlayer.stop();
        mediaPlayer.prepare();
        mediaPlayer.seekTo(0);
    }

    public void setDataSource(String src) throws IOException {
        mediaPlayer.reset();
        mediaPlayer.setDataSource(src);
        mediaPlayer.prepare();
    }

    public float getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public float getDuration() {
        return mediaPlayer.getDuration();
    }

    public void seekTo(int currentPosition) {
        mediaPlayer.seekTo(currentPosition);
    }
};
