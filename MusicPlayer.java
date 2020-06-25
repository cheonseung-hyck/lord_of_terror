import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.*;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.io.File;
import java.io.IOException;

public class MusicPlayer {
    
    MainProgram mp;
    mainUI mu;
    public Clip clip;

    int lastPoint;
    int music_now;
    
    File currentMusic;

    public  MusicPlayer(){

        Timer timer = new Timer();
    	TimerTask task = new TimerTask() {
    		public void run() {
    			if (clip != null && clip.isRunning()) {
    				mu.music_slider.setValue(clip.getFramePosition()*100/clip.getFrameLength());
    			} 
    		}
        };
        timer.schedule(task, 100, 100);
    }
    public void setPointer(MainProgram mp, mainUI mu){
        this.mp=mp;
        this.mu=mu;
    }

    public void playMusic(JButton play_Button, Icon pauseIcon, Icon playIcon){

        if (mp.music_num > -1) { 
            if (clip == null) { //first state
                try {
                    currentMusic = new File(mp.musicFileList.get(music_now));
                    loadClip(currentMusic);
                    clip.start();
                    play_Button.setIcon(pauseIcon);
                    
                } catch(Exception ex) {
                 JOptionPane.showMessageDialog(null, "error with playing music");
                 ex.printStackTrace();
                }
            } else {
                if (clip.isRunning()) { //running state
                      lastPoint = clip.getFramePosition();
                    clip.stop();
                    play_Button.setIcon(playIcon);
                    
                } else { 
                    if (lastPoint < clip.getFrameLength()) {
                        clip.setFramePosition(lastPoint);
                        
                    } else {
                        clip.setFramePosition(0);
                        
                    }
                    
                    clip.start();
                    play_Button.setIcon(pauseIcon);
                    
                }
                }
            }
    }
    public void prevMusic(JButton play_Button,Icon pauseIcon){
        if (clip != null) {
            if (music_now == 0) { 
                clip.setFramePosition(0);
                clip.stop();
                
            } else {
                clip.stop(); 
                clip = null;
                music_now--;
                     currentMusic = new File(mp.musicFileList.get(music_now));
                     
                     try {
                    loadClip(currentMusic);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
                     
                play_Button.setIcon(pauseIcon);
            }
            clip.start();
        }
    }
    public void nextMusic(JButton play_Button, Icon pauseIcon){
        if (clip != null) { // next music
            clip.stop(); 
            clip = null;
            music_now++;
                 currentMusic = new File(mp.musicFileList.get(music_now));
                 
                 try {
                loadClip(currentMusic);
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                // TODO Auto-generated catch block
                JOptionPane.showMessageDialog(null, e1.getMessage());
                e1.printStackTrace();
            }
            clip.start();
            play_Button.setIcon(pauseIcon);
        }
    }
    public void loadClip(File audioFile) throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(audioStream);
    }
}