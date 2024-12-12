package game;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;

public class Audio {
    private static MediaPlayer mediaPlayer = null;
    private static HashMap<String, Media> audioCollection = new HashMap<>(){{
        put("in_game", new Media(Audio.class.getResource("/assets/audio/inGame.wav").toExternalForm()));
        put("lose", new Media(Audio.class.getResource("/assets/audio/lose.wav").toExternalForm()));
        put("selection", new Media(Audio.class.getResource("/assets/audio/selection.wav").toExternalForm()));
        put("win", new Media(Audio.class.getResource("/assets/audio/win.wav").toExternalForm()));
        put("menu", new Media(Audio.class.getResource("/assets/audio/menu.wav").toExternalForm()));
    }};
    private static AudioClip clip;

    // PLAYCLIP
    public static void playClip(String soundName, double volume){
        clip = new AudioClip(Audio.class.getResource("/assets/audio/" + soundName + ".wav").toExternalForm());
        clip.setVolume(volume);
        clip.play();
    }

    // PLAYSOUND
    public static void playSound(String soundName, double volume, boolean loop){
        if(mediaPlayer!=null){
            try{
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }catch(Exception e){
                System.out.println("Something went wrong during the playback");
            }
        }
        Media selectedAudio = audioCollection.get(soundName);
        Audio.mediaPlayer = new MediaPlayer(selectedAudio);
        mediaPlayer.setVolume(volume);
        if(loop){mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);}
        mediaPlayer.play();
        System.out.println("Audio "+soundName+" played");
    }

    // STOPSOUND
    public static void stopSound(){
        mediaPlayer.stop();
        mediaPlayer.dispose();
    }

}
