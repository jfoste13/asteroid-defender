/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public class Turret{
    private int posX, posY, width, height, cooldownTime, cooldownProgress;
    private double rotationSpeed, angle;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private char charLeft, charRight, charFire;
    private Point centerTurretPoint, outerTurretPoint;
    private Color color;
    
    public Turret(Point origin, int width, int height, double rotationSpeed, char charLeft, char charRight, char charFire, int cooldownTime){
        this.posX = origin.x;
        this.posY = origin.y;
        this.width = width;
        this.height = height;
        this.cooldownTime = cooldownTime;
        this.cooldownProgress = cooldownTime;
        this.rotationSpeed = rotationSpeed;
        this.charLeft = charLeft;
        this.charRight = charRight;
        this.charFire = charFire;
        this.color = randomizeColor();
        this.centerTurretPoint = calculateCenterPoint();
        this.outerTurretPoint = calculateOuterPoint(centerTurretPoint, 50);
    }
    
    public void update(){
        if(movingRight) angle += rotationSpeed;
        if(movingLeft) angle -= rotationSpeed;
        if(cooldownProgress < cooldownTime) cooldownProgress++;
        this.outerTurretPoint = calculateOuterPoint(centerTurretPoint, 75);
    }
    //getters and setters
    public int getPosX(){
        return this.posX;
    }
    public void setPosX(int newX){
        this.posX = newX;
    }
    public int getPosY(){
        return this.posY;
    }
    public void setPosY(int newY){
        this.posY = newY;
    }
    public int getWidth(){
        return this.width;
    }
    public void setWidth(int newWidth){
        this.width = newWidth;
    }
    public int getHeight(){
        return this.height;
    }
    public void setHeight(int newHeight){
        this.height = newHeight;
    }
    public int getCooldownTime(){
        return this.cooldownTime;
    }
    public void setCooldownTime(int newCooldownTime){
        this.cooldownTime = newCooldownTime;
    }
    public int getCooldownProgress(){
        return this.cooldownProgress;
    }
    public void setCooldownProgress(int newCooldownProgress){
        this.cooldownProgress = newCooldownProgress;
    }
    public double getRotationSpeed(){
        return this.rotationSpeed;
    }
    public void setRotationSpeed(double newRotationSpeed){
        this.rotationSpeed = newRotationSpeed;
    }
    public char getCharLeft(){
        return this.charLeft;
    }
    public void setCharLeft(char newCharLeft){
        this.charLeft = newCharLeft;
    }
    public char getCharRight(){
        return this.charRight;
    }
    public void setCharRight(char newCharRight){
        this.charRight = newCharRight;
    }
    public double getAngle(){
        return this.angle;
    }
    public void setAngle(double newAngle){
        this.angle = newAngle;
    }
    public boolean getMovingLeft(){
        return this.movingLeft;
    }
    public void setMovingLeft(boolean newMovingLeft){
        this.movingLeft = newMovingLeft;
    }
    public boolean getMovingRight(){
        return this.movingRight;
    }
    public void setMovingRight(boolean newMovingRight){
        this.movingRight = newMovingRight;
    }
    public char getCharFire(){
        return this.charFire;
    }
    public void setCharFire(char newCharFire){
        this.charFire = newCharFire;
    }
    public Color getColor(){
        return this.color;
    }
    public void setColor(Color newColor){
        this.color = newColor;
    }
    public Point getCenterTurretPoint(){
        return this.centerTurretPoint;
    }
    public void setCenterTurretPoint(Point newCenterTurretPoint){
        this.centerTurretPoint = newCenterTurretPoint;
    }
    public Point getOuterTurretPoint(){
        return this.outerTurretPoint;
    }
    public void setOuterTurretPoint(Point newOuterTurretPoint){
        this.outerTurretPoint = newOuterTurretPoint;
    }
    
    //used for game logic
    private Point calculateCenterPoint(){
        return new Point(posX + (width)/2, posY - 25);
    }
    private Point calculateOuterPoint(Point centerPoint, int radius){
        int x = (int) (centerPoint.x + radius * Math.cos(angle));
        int y = (int) (centerPoint.y + radius * Math.sin(angle));
        return new Point(x, y);
    }
    private Color randomizeColor(){
        Random r = new Random();
        return new Color((r.nextInt(255) + 1),(r.nextInt(255) + 1),(r.nextInt(255) + 1));
    }

}
