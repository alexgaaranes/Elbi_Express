package game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;


public class Audio {
    private static HashMap<String, Media> paths = new HashMap<>(){{
        put("key_pressed", new Media("file:src/assets/audio/keyHit.wav"));
    }};

    public static void play(String audioName){
        try{
            MediaPlayer mediaPlayer = new MediaPlayer(new Media("file:src/assets/audio/keyHit.wav"));
            mediaPlayer.play();
        } catch (Exception e){
            System.out.println("cannot create media player");
        }
    }
}
