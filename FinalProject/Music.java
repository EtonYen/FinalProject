
/*import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.*;

import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class Music{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setSize(200,200);
		JButton button = new JButton("play");
		frame.add(button);
		button.addActionListener(new AL());
		frame.show(true);
	}
	public static class AL implements ActionListener{
		public final void actionPerformed(ActionEvent e){
			music();
		}
	}
	private static void music() {
		AudioPlayer MGP = AudioPlayer.player;
		AudioStream BGM;
		AudioData MD;
		ContinuousAudioDataStream loop = null;
		
		try{
			BGM = new AudioStream (new FileInputStream("m.mp3"));
			MD = BGM.getData();
			loop = new ContinuousAudioDataStream(MD);
		}catch(IOException error){}
		MGP.start(loop);
	}
}
*/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class Music extends JFrame {

   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JButton b = new JButton("click to pauce");
	private URL url;
	private AudioInputStream audioIn;
	//public static Clip clip; 
	//public static boolean sw = true;//no music
    
	public Clip clip; 
	public boolean sw = true;//no music
	/*
	public static class AL implements ActionListener{
  		public final void actionPerformed(ActionEvent e){
  			if(sw==false)sw=true;
  			else sw=false;
  			//===================
  			if(sw==true){
  				clip.start();
  				b.setText("click to pause");
  			}
  			else {
  				clip.stop();
  				b.setText("click to start");
  			}
  		}
  	}
	*/
	public Music() {
	  
    //  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //  this.setTitle("Test Sound Clip");
     // this.setSize(300, 200);
    //  this.setVisible(true);
    //  this.add(b);
    //  b.addActionListener(new AL());
      

      try {
         // Open an audio input stream.
         url = this.getClass().getClassLoader().getResource("m.wav");
         audioIn = AudioSystem.getAudioInputStream(url);
         // Get a sound clip resource.
         clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioIn);
         clip.start();
         clip.loop(Clip.LOOP_CONTINUOUSLY);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
/*	
   public static void main(String[] args) {
      new Music();
   }*/
}