package project;

import java.awt.Color;
import java.net.URL;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Meteor {
    private int posX;
    private int speed;
    private int posY;
    private Color color = Color.WHITE;
    private boolean isActive = true;
    
    public Meteor(){
        generateSelf();
    }
    public Color getColor(){
        return this.color;
    }
    public void setColor(Color newColor){
        this.color = newColor;
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
    public int getSpeed(){
        return this.speed;
    }
    public void setSpeed(int x){
        this.speed = x;
    }
    public boolean getActivity(){
        return this.isActive;
    }
    private void generateSelf(){
        Random r = new Random();
        this.posX = r.nextInt(360) + 40;
        this.posY = -50;
        this.speed = r.nextInt(4) + 1;
    }
    public void update(){
        this.posY += speed;
        if(this.posY + 25 > 600){
            this.isActive = false;
            GamePanel.lives -= 1;
            GamePanel.killAllMeteors();
            playSound("hurt.wav", -10F);
        }
    }
    public void playSound(String soundFile, float f){
        try{
            URL url = this.getClass().getClassLoader().getResource(soundFile);
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
