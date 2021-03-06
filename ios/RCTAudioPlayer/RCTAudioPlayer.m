//
//  RTCAudioPlayer.m
//  RTCAudioPlayer
//
//  Created by 刘红 on 2018/1/9.
//  Copyright © 2018年 Holly Liu. All rights reserved.
//

#import "RCTAudioPlayer.h"
#import "AVKit/AVKit.h"
#import "AVFoundation/AVFoundation.h"
#import <React/RCTEventDispatcher.h>
#import <React/RCTBridge.h>
#import <React/RCTLog.h>

typedef int (^BLOCK_CALLBACK_FUNC)(NSString *__strong);

#define INVOKE_FAILED (@"调用接口失败")

@interface RCTAudioPlayer ()
    @property AVPlayer * player;
@end

@implementation RCTAudioPlayer

RCT_EXPORT_MODULE()

RCTPromiseResolveBlock __resolve;
RCTPromiseRejectBlock __reject;

- (void)observeValueForKeyPath:(NSString *)keyPath
                      ofObject:(id)object
                        change:(NSDictionary<NSKeyValueChangeKey,id> *)change
                       context:(void *)context
{
    
    if (object == _player && [keyPath isEqualToString:@"status"]) {
        switch ([_player status]) {
            case AVPlayerStatusFailed:
                if(__reject!=nil) __reject(@"-1", INVOKE_FAILED, nil);
                __reject = nil;
                break;
            case AVPlayerStatusReadyToPlay:
                if(__resolve!=nil) __resolve(@"0");
                __resolve = nil;
                break;
            case AVPlayerStatusUnknown:
                if(__resolve!=nil) __resolve(@"1");
                __resolve = nil;
                break;
            default:
                break;
        }
    }
}

RCT_EXPORT_METHOD(setDataSource:(NSString*)src
                 :(RCTPromiseResolveBlock)resolve
                 :(RCTPromiseRejectBlock)reject)
{
    NSURL * url  = [NSURL URLWithString:src];
    _player = [[AVPlayer alloc] initWithURL:url];
    [_player addObserver:self forKeyPath:@"status" options:NSKeyValueObservingOptionNew context:nil];
    __resolve = resolve;
    __reject = reject;
}

RCT_EXPORT_METHOD(isPlaying:(RCTPromiseResolveBlock)resolve
                  :(RCTPromiseRejectBlock)reject)
{
    if([[UIDevice currentDevice] systemVersion].intValue >= 10){
        BOOL flag = _player.timeControlStatus == AVPlayerTimeControlStatusPlaying;
        if(flag) resolve(@"1");
        else resolve(@"0");
    }else{
        BOOL flag = [_player rate] == 1;
        if(flag) resolve(@"1");
        else resolve(@"0");
    }
}

RCT_EXPORT_METHOD(start)
{
    [_player play];
}

RCT_EXPORT_METHOD(pause)
{
    [_player pause];
}

RCT_EXPORT_METHOD(stop:(RCTPromiseResolveBlock)resolve
                  :(RCTPromiseRejectBlock)reject) {
    // _player.currentItem.replaceCurrentItemWithPlayerItem(nil)
    [_player replaceCurrentItemWithPlayerItem:nil];
    resolve(@'1');
}

RCT_EXPORT_METHOD(seekTo:(nonnull NSNumber*)time)
{
    Float64 cmTimemake = [time floatValue];
    [_player seekToTime:CMTimeMake(cmTimemake,1) toleranceBefore: kCMTimeZero toleranceAfter: kCMTimeZero];
}

RCT_EXPORT_METHOD(getDuration:(RCTPromiseResolveBlock)resolve
                  :(RCTPromiseRejectBlock)reject) {
    CMTime duration = [[_player currentItem] duration];
    CGFloat totalDuration = CMTimeGetSeconds(duration);
    NSString *number = [NSString stringWithFormat:@"%f",totalDuration];
    resolve(number);
}

RCT_EXPORT_METHOD(getCurrentPosition:(RCTPromiseResolveBlock)resolve
                  :(RCTPromiseRejectBlock)reject) {
    AVPlayerItem* playerItem = [_player currentItem];
    CGFloat currentSecond = playerItem.currentTime.value/playerItem.currentTime.timescale;
    NSString *number = [NSString stringWithFormat:@"%f",currentSecond];
    resolve(number);
}

@end
