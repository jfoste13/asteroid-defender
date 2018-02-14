/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package project;

import java.awt.Point;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Laser {
    
    private int posX;
    private int posY;
    private Point slope;
    private boolean isActive = true;
    
    public Laser(Point origin, Point innerOrigin){
        this.posX = origin.x;
        this.posY = origin.y;     
        slope = calculateSlope(innerOrigin, origin);
        playSound(-10F);
    }
    public int getPosX(){
        return this.posX;
    }
    public int getPosY(){
        return this.posY;
    }
    public void setPosX(int x){
        this.posX = x;
    }
    public void setPosY(int y){
        this.posY = y;
    }
    public boolean getActivity(){
        return this.isActive;
    }
    public void update(){
        this.posX -= slope.x;
        this.posY -= slope.y;
    }
    private Point calculateSlope(Point p1, Point p2){
        int y = (int)(p1.y - p2.y)/10;
        int x = (int)(p1.x - p2.x)/10;
        return new Point(x, y);
    }
    private void playSound(Float f){
        try{
            URL url = this.getClass().getClassLoader().getResource("laser.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(f);
            clip.start();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
