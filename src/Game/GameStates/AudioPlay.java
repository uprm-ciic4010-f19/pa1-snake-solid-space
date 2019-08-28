package Game.GameStates;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlay {

	private static Clip clip;
	public static InputStream audioFile;
	private static AudioFormat format;
	private static DataLine.Info info;

	public AudioPlay() {
		try {
			this.clip = AudioSystem.getClip();
		} catch (LineUnavailableException ex) {
			Logger.getLogger(AudioPlay.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
	public static void stopSound() {
		clip.stop();   
		//clip.flush();
		clip.close();
	}

	public static void playSound(int a){

		// Open an audio input stream.
		String[] sounds = new String[10];
		sounds[0]= "/music/twinsnakes1.wav";
		sounds[1]= "/music/gamemusic.wav";
		sounds[2]= "/music/pausemusic.wav";
		sounds[3]= "/music/collision.wav";

		try {

			audioFile = AudioPlay.class.getResourceAsStream(sounds[a]);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			format = audioStream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioStream);
			if (a == 3) {
				clip.start();
			} else {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
