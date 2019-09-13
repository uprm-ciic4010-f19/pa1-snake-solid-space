package Resources;

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

public class Soundtrack {

	private static Clip clip;
	public static InputStream audioFile;
	private static AudioFormat format;
	private static DataLine.Info info;
	
	private static Boolean soundOff = false;
	
	public static Boolean getSoundOff() {
		return soundOff;
	}
	public static void setSoundOff(Boolean soundOff) {
		Soundtrack.soundOff = soundOff;
	}


	public Soundtrack() {
		try {
			this.clip = AudioSystem.getClip();
		} catch (LineUnavailableException ex) {
			Logger.getLogger(Soundtrack.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
	public static void stopSound() {
		clip.stop();   
		clip.flush();
		clip.close();
	}

	public static void playSound(int a){

		String[] sounds = new String[10];
		sounds[0]= "/Music/TitleMusic.wav";
		sounds[1]= "/Music/GameMusic.wav";
		sounds[2]= "/Music/PauseMenuMusic.wav";
		sounds[3]= "/Music/SPGameOver.wav";
		sounds[4]="/Music/VersusGameOver.wav";
		sounds[5]="/Music/Eat.wav";
		sounds[6]= "/Music/SelectionSound.wav";
		
		try {

			audioFile = Soundtrack.class.getResourceAsStream(sounds[a]);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			format = audioStream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioStream);
			
			//Ensures that audio is only played once for sounds 3 and up.
			if (a >= 3) {
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
