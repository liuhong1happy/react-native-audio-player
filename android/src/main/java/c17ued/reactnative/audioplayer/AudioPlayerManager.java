package com.c17ued.reactnative.audioplayer;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.views.imagehelper.ResourceDrawableIdHelper;
import com.facebook.react.views.imagehelper.ImageSource;

import java.io.IOException;
import java.util.Map;

public class AudioPlayerManager extends ReactContextBaseJavaModule {
    private MediaPlayerController mediaPlayer;

    public AudioPlayerManager(ReactApplicationContext reactContext) {
        super(reactContext);
        mediaPlayer = new MediaPlayerController(reactContext);
    }

    @Override
    public String getName() {
        return "AudioPlayer";
    }

    @ReactMethod
    public void setDataSource(String src, Promise promise){
        try {
            mediaPlayer.setDataSource(src);
            promise.resolve(null);
        } catch (IOException e) {
            promise.reject("Error", e.getMessage());
        }
    }

    @ReactMethod
    public void isPlaying(Promise promise){
        promise.resolve(mediaPlayer.isPlaying());
    }

    @ReactMethod
    public void start(){
        mediaPlayer.start();
    }

    @ReactMethod
    public void stop(Promise promise){
        try {
            mediaPlayer.stop();
            promise.resolve(null);
        } catch (IOException e) {
            promise.reject("Error", e.getMessage());
        }
    }

    @ReactMethod
    public void pause(){
        mediaPlayer.pause();
    }

    @ReactMethod
    public void getCurrentPosition(Promise promise){
        promise.resolve(mediaPlayer.getCurrentPosition());
    }

    @ReactMethod
    public void getDuration(Promise promise){
        promise.resolve(mediaPlayer.getDuration());
    }

    @ReactMethod
    public void seekTo(int number){
        mediaPlayer.seekTo(number);
    }
};

