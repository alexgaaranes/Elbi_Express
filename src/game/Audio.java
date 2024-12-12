package game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;

public class Audio {
    private static MediaPlayer mediaPlayer;
    private static HashMap<String, Media> audioCollection = new HashMap<>(){{
        put("key_hit", new Media(Audio.class.getResource("/assets/audio/keyHit.wav").toExternalForm()));
    }};

    public static void playSound(String soundName){
        Media selectedAudio = audioCollection.get(soundName);
        Audio.mediaPlayer = new MediaPlayer(selectedAudio);
        mediaPlayer.play();
        System.out.println("Audio "+soundName+" played");
    }

}
