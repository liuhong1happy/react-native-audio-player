import { 
    NativeModules
} from 'react-native';
import resolveAssetSource from ('react-native/Libraries/Image/resolveAssetSource');

const AudioPlayer = NativeModules.AudioPlayer

module.exports = {
    async setDataSource(src){
        let source = resolveAssetSource(src);
        return await AudioPlayer.setDataSource(source.uri ? source.uri : source);
    },
    async isPlaying() {
        return parseInt(await AudioPlayer.isPlaying());
    },
    start() {
        AudioPlayer.start();
    },
    pause() {
        AudioPlayer.pause();
    },
    async stop() {
        return await AudioPlayer.stop();
    },
    async getCurrentPosition() {
        return parseFloat(await AudioPlayer.getCurrentPosition());
    },
    async getDuration() {
        return parseFloat(await AudioPlayer.getDuration());
    },
    seekTo(number){
        AudioPlayer.seekTo(number);
    }
} ;