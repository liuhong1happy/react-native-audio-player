package com.c17ued.reactnative.audioplayer;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MediaPlayerController {
    private MediaPlayer mediaPlayer;
    private ReactApplicationContext context;
    private Map<String, Integer> resourceDrawableIdMap;
    private final String LOCAL_RESOURCE_SCHEME = "res";

    public MediaPlayerController(ReactApplicationContext _context) {
        context = _context;
        mediaPlayer = new MediaPlayer();
        resourceDrawableIdMap = new HashMap<String, Integer>();
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
        if(src.startsWith("http") || src.startsWith("file")) {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(src);
            mediaPlayer.prepare();
        } else {
            mediaPlayer = MediaPlayer.create(context, this.getResourceIdentifier(src));
        }
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

    public int getResourceIdentifier(String name) {
        if (resourceDrawableIdMap.containsKey(name)) {
            return resourceDrawableIdMap.get(name);
        }
        int resId = context.getResources().getIdentifier(name, "raw", context.getPackageName());
        resourceDrawableIdMap.put(name, resId);
        Log.i("MediaPlayerController", String.valueOf(resId));
        return resId;
    }

    public Uri getResourceDrawableUri(String name) {
        int resId = this.getResourceIdentifier(name);
        return resId > 0 ? new Uri.Builder()
                .scheme(LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resId))
                .build() : Uri.EMPTY;
    }
};
