/**
 * The Audio class provides methods to manage and play audio files in the game.
 * It supports playing sound effects (clips) and background music, with options for looping and volume control.
 */
package game;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;

public class Audio {

    //The MediaPlayer object used for playing background music or longer audio tracks.
    private static MediaPlayer mediaPlayer = null;

    /**
     * A collection of Media objects mapped by their sound names.
     * This is used to preload and manage audio resources efficiently.
     */
    @SuppressWarnings("serial")
	private static HashMap<String, Media> audioCollection = new HashMap<>() {{
        put("in_game", new Media(Audio.class.getResource("/assets/audio/inGame.wav").toExternalForm()));
        put("lose", new Media(Audio.class.getResource("/assets/audio/lose.wav").toExternalForm()));
        put("selection", new Media(Audio.class.getResource("/assets/audio/selection.wav").toExternalForm()));
        put("win", new Media(Audio.class.getResource("/assets/audio/win.wav").toExternalForm()));
        put("menu", new Media(Audio.class.getResource("/assets/audio/menu.wav").toExternalForm()));
    }};

    /**
     * An AudioClip object used for playing short sound effects.
     */
    private static AudioClip clip;

    /**
     * Plays a short sound effect without interrupting other audio.
     *
     * @param soundName The name of the sound file (without extension) to play.
     * @param volume    The volume level of the clip, ranging from 0.0 to 1.0.
     */
    public static void playClip(String soundName, double volume) {
        clip = new AudioClip(Audio.class.getResource("/assets/audio/" + soundName + ".wav").toExternalForm());
        clip.setVolume(volume);
        clip.play();
    }

    /**
     * Plays a background sound or longer audio track, optionally looping it.
     *
     * @param soundName The name of the sound resource (key in the audioCollection) to play.
     * @param volume    The volume level of the sound, ranging from 0.0 to 1.0.
     * @param loop      Whether the sound should loop indefinitely.
     */
    public static void playSound(String soundName, double volume, boolean loop) {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            } catch (Exception e) {
                System.out.println("Something went wrong during the playback");
            }
        }
        Media selectedAudio = audioCollection.get(soundName);
        Audio.mediaPlayer = new MediaPlayer(selectedAudio);
        mediaPlayer.setVolume(volume);
        if (loop) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
        mediaPlayer.play();
        System.out.println("Audio " + soundName + " played");
    }

    /**
     * Stops any currently playing background sound or longer audio track.
     * Ensures the MediaPlayer is properly disposed to release resources.
     */
    public static void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
    }
}
