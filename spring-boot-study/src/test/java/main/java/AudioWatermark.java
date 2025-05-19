package main.java;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioWatermark {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        String audioPath = "D:\\KuGou\\筠子-立秋.wav";
        String backgroundMusicPath = "D:\\KuGou\\watermark.wav";
        addBackgroundMusic(audioPath, backgroundMusicPath);
    }

    public static void addBackgroundMusic(String audioPath, String backgroundMusicPath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File audioFile = new File(audioPath);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(audioFile);
        AudioInputStream newAudioInputStream = new AudioInputStream(new BackgroundMusicAudioInputStream(audioInputStream, backgroundMusicPath), fileFormat.getFormat(), audioInputStream.getFrameLength());
        String outputFilePath = "D:\\KuGou\\result.wav";
        AudioSystem.write(newAudioInputStream, AudioFileFormat.Type.WAVE, new File(outputFilePath));
    }
}

class BackgroundMusicAudioInputStream extends AudioInputStream {

    private String backgroundMusicPath;
    private AudioInputStream backgroundMusicInputStream;
    private Clip clip;
    private FloatControl gainControl;

    public BackgroundMusicAudioInputStream(AudioInputStream audioInputStream, String backgroundMusicPath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(audioInputStream, audioInputStream.getFormat(), audioInputStream.getFrameLength());
        this.backgroundMusicPath = backgroundMusicPath;
        this.backgroundMusicInputStream = AudioSystem.getAudioInputStream(new File(backgroundMusicPath));
        this.clip = AudioSystem.getClip();
        this.clip.open(backgroundMusicInputStream);
        this.gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        this.gainControl.setValue(-10.0f);
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int bytesRead = super.read(b, off, len);
        if (bytesRead == -1){
            clip.stop();
            clip.close();
            backgroundMusicInputStream.close();
            return -1;
        }
        return bytesRead;
    }
}
