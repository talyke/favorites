/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package painting;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import java.applet.AudioClip;
import java.awt.*;

/**
 *
 * @author Pebbles
 */
public class Painting extends JApplet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    public ImageIcon paintingImage;

    private static AudioClip sounds[];
    private int currentSound = -1;
    private static final String captions[] = {
        "[Sky] 'When you realize how perfect everything is you will tilt you head back and laugh at the sky.' ~Buddha",
        "[Water] 'Nothing is softer or more flexible than water, yet nothing can resist it.' ~Lao Tzu ",
        "[Sun] 'Three things cannot be long hidden: the sun, the moon and the truth.' ~Buddah",
        "[Tree] 'The root of suffering is attachment.' ~Buddha",
        "[Boat] 'You're gonna need a bigger boat.' ~Jaws",
        ""};
}
public void init()
   {
      setSize(1100, 874); //size background image coordinates to panel(y, x) // :)
     
      sounds = new AudioClip[5];
      
      sounds[0] = getAudioClip( getDocumentBase(), "wind.wav" );
      sounds[1] = getAudioClip( getDocumentBase(), "owlLakeWavesHjalmaren.wav" );
      sounds[2] = getAudioClip( getDocumentBase(), "messageOfTheSun.wav" );
      sounds[3] = getAudioClip( getDocumentBase(), "windChimes.wav" );
      sounds[4] = getAudioClip( getDocumentBase(), "scaryWaters.wav" );
}
 addMouseListener(
{
         new MouseAdapter() 
         {
            public void mouseExited( MouseEvent event )
            {
showStatus( "pointer has drifted away from serene painting" );
               sounds[0].play();

  }
         }
         );
 addMouseMotionListener(
         
         new MouseMotionAdapter()
         
         {
            // mouse appears...with movement/event
            public void mouseMoved( MouseEvent event )
            {
               showStatus( translateLocation(
                  event.getXOnScreen(), event.getYOnScreen() ) );
            }
         }
         );
       
      paintingImage = new ImageIcon( "pics/Painting.png" );
   }  
   public void paint( Graphics g )
   {
      super.paint( g );      
      paintingImage.paintIcon( this, g, 0, 0 );
   }
  public String translateLocation( int x, int y )
   {
      Point mp = this.getMousePosition(true);
      x = mp.x;
      y = mp.y;

     if ( x >= paintingImage.getIconWidth() || y >= paintingImage.getIconHeight() )
         return ("");
  int iconNumber = 5;
  if (x> 0 && x < 1054 && y > 0 && y < 320) //Image Grid Measurements
      {
         iconNumber = 0; // The SKY
         if (currentSound > 0)
            sounds[currentSound].stop();
         if (currentSound != 0)
            sounds[0].play();
         currentSound = 0;
      }
      if (x > 378 && x  <1049 && y > 321 && y < 613)
      {
         iconNumber = 1; // The WATER
         if (currentSound >= 0 && currentSound != 1)
            sounds[currentSound].stop();
         if (currentSound !=1)
            sounds[1].play();
         currentSound = 1;
      }
      if (x > 808 && x < 912 && y > 207 && y < 280)
      {
         iconNumber = 2; // The SUN
         if (currentSound >= 0  && currentSound != 2)
            sounds[currentSound].stop();
         if (currentSound != 2)
            sounds[2].play();
         currentSound = 2;
      }
      if (x > 33 && x < 378 && y > 322 && y < 698)
      {
         iconNumber = 3; //The TREE
         if (currentSound >= 0  && currentSound != 3)
            sounds[currentSound].stop();
         if (currentSound != 3)
            sounds[3].play();
         currentSound = 3;
      }
      if (x > 502 && x < 570 && y > 420 && y < 490)
      {
         iconNumber = 4; // The BOAT
         if (currentSound >= 0  && currentSound != 4)
            sounds[currentSound].stop();
         if (currentSound != 4)
            sounds[4].play();
         currentSound = 4; 
  return captions[ iconNumber ] + "   " + x + "," + y;
  
      }
}
