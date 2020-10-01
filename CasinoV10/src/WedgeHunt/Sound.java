package WedgeHunt;


/**
 * Jack Brand
 * 2018
 */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

@SuppressWarnings("unused")
public class Sound
{
    //ALL DIFFERENT CLIP OBJECTS
    public Clip musicClip;
    public Clip shootClip;
    public Clip killClip;
    public Clip errorClip;
    public Clip owClip;

    //default constructor
    public Sound()
    {
    }

    public Clip getMusic()
    {
        return musicClip;
    }

  
    {
    	//starts the song when the game opens
    	    try {
    	    	//File tempT = new File(getClass().getClassLoader().getResource("WHResources/song.wav").getFile());WHResources/song.wav
    	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("WHResources/song.wav"));
    	        musicClip = AudioSystem.getClip();
    	        musicClip.open(audioInputStream);
    	        musicClip.start();
    	        //musicClip.loop(musicClip.LOOP_CONTINUOUSLY);
    	    } 
    	    catch(Exception ex) 
    	    {
    	        System.out.println("Error with playing background sound."+ex);
    	        ex.printStackTrace();
    	    }
    	
    }

    /**
     *
     * STOP MUSIC
     */
    public void stopMusic()
    {
        musicClip.stop();
    }

    /**
     *
     * START SHOOT SOUND
     */
    public void playShoot()
    {
    	try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("WHResources/shotgun.wav"));
	        shootClip = AudioSystem.getClip();
	        shootClip.open(audioInputStream);
	        shootClip.start();
	       
	    } 
	    catch(Exception ex) 
	    {
	        System.out.println("Error with playing shoot sound.");
	        ex.printStackTrace();
	    }
    }

    public void stopShoot()
    {
        shootClip.stop();
    }

    //plays death sound
    public void playDie()
    {
    	try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("WHResources/death.wav"));
	        killClip = AudioSystem.getClip();
	        killClip.open(audioInputStream);
	        killClip.start();
	       
	    } 
	    catch(Exception ex) 
	    {
	        System.out.println("Error with playing die sound.");
	        ex.printStackTrace();
	    }
    }

    public void stopDie()
    {
        killClip.stop();
    }
    
    //plays error sound
    public void playError()
    {
    	try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("WHResources/error.wav"));
	        errorClip = AudioSystem.getClip();
	        errorClip.open(audioInputStream);
	        errorClip.start();
	       
	    } 
	    catch(Exception ex) 
	    {
	        System.out.println("Error with playing error sound.");
	        ex.printStackTrace();
	    }
    }
    
    //plays ow sound
    public void playOw()
    {
    	try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(getClass().getResource("WHResources/ow.wav").getFile()).getAbsoluteFile());
	        owClip = AudioSystem.getClip();
	        owClip.open(audioInputStream);
	        owClip.start();
	       
	    } 
	    catch(Exception ex) 
	    {
	        System.out.println("Error with playing ow sound.");
	        ex.printStackTrace();
	    }
    }


} //end of class
