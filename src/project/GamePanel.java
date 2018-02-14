package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{
    
    public final int MAX_METEORS = 10;
    public final int DIFFICULTY = 10;
    public static ArrayList<Meteor> meteorList = new ArrayList<>();
    public static ArrayList<Meteor> deadMeteors = new ArrayList<>();
    public static ArrayList<Laser> laserList = new ArrayList<>();
    public static ArrayList<Laser> deadLasers = new ArrayList<>();
    public static ArrayList<Turret> turretList = new ArrayList<>();
    public char[] controlList = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
    'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
    'Y', 'Y', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
    public boolean playing = false;
    public int deathcount = 0;
    public static int lives = 3;
    public int lastScore = 0;
    public boolean mainMenu = true;
    public boolean gameOver = false;
    public int players = 1;
   
    public GamePanel(){
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent ke) {
                
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                mainMenu = false;
                if(ke.getKeyCode() == KeyEvent.VK_ESCAPE && gameOver){
                    initializeGame(players);
                }
                if(ke.getKeyCode() == KeyEvent.VK_ESCAPE){
                    if(playing == false) playing = true;
                    else if(playing == true) playing = false;
                }
//                if(ke.getKeyCode() == KeyEvent.VK_A){
//                    for(Turret t: turretList){
//                        if(t.getCharLeft() == 'A'){
//                            t.setMovingLeft(true);
//                        }
//                    }
//                }
                for(char c: controlList){
                    if(ke.getKeyCode() == (int)c){
                        for(Turret t: turretList){
                            if(t.getCharLeft() == c){
                                t.setMovingLeft(true);
                                break;
                            }
                            if(t.getCharRight() == c){
                                t.setMovingRight(true);
                                break;
                            }
                            if(t.getCharFire() == c){
                                if(t.getCooldownProgress() == t.getCooldownTime()){
                                    Laser l = new Laser(t.getOuterTurretPoint(), t.getCenterTurretPoint());
                                    laserList.add(l);  
                                    t.setCooldownProgress(0);
                                }

                            }
                        }
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent ke) {
                for(char c: controlList){
                    if(ke.getKeyCode() == (int)c){
                        for(Turret t: turretList){
                            if(t.getCharLeft() == c){
                                t.setMovingLeft(false);
                                break;
                            }
                            if(t.getCharRight() == c){
                                t.setMovingRight(false);
                                break;
                            }
                        }
                    }
                }
            }
            
        });
        
        initializeGame(players);
        Timer timer = new Timer(1000/60, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        tick();
    }
    
    private void initializeGame(int playerCount){
        deathcount = 0;
        lives = 3;
        gameOver = false;
        playing = false;
        turretList.clear();
        meteorList.clear();
        laserList.clear();
        switch(playerCount){
            case(1):
                Turret player = new Turret(new Point(200, 500), 100, 100, 0.1, 'A', 'D', 'S', 10);
                turretList.add(player);
                break;
            case(2):
                Turret player1 = new Turret(new Point(50, 500), 100, 100, 0.1, 'A', 'D', 'S', 20);
                Turret player2 = new Turret(new Point(325, 500), 100, 100, 0.1, 'J', 'L', 'K', 20);
                turretList.add(player1);
                turretList.add(player2);
                break;
            case(3):
                Turret p1 = new Turret(new Point(25, 500), 100, 100, 0.1, 'A', 'D', 'S', 30);
                Turret p2 = new Turret(new Point(185, 500), 100, 100, 0.1, 'J', 'L', 'K', 30);
                Turret p3 = new Turret(new Point(350, 500), 100, 100, 0.1, '7', '9', '8', 30);
                turretList.add(p1);
                turretList.add(p2);
                turretList.add(p3);
                break;
            case(4):
                Turret t1 = new Turret(new Point(10, 500), 100, 100, 0.1, 'A', 'D', 'S', 40);
                Turret t2 = new Turret(new Point(130, 500), 100, 100, 0.1, 'J', 'L', 'K', 40);
                Turret t3 = new Turret(new Point(260, 500), 100, 100, 0.1, '7', '9', '8', 40);
                Turret t4 = new Turret(new Point(380, 500), 100, 100, 0.1, 'V', 'N', 'B', 40);
                turretList.add(t1);
                turretList.add(t2);
                turretList.add(t3);
                turretList.add(t4);
                break;
            default:
                System.out.println("Something broke...");
        }
    }
    public void tick(){
        if(playing){ 
            if(lives > 0){
                if(meteorList.size() < MAX_METEORS){
                    Random r = new Random();
                    if(r.nextInt(DIFFICULTY) == 1){
                        meteorList.add(new Meteor());
                    }     
                }
                calculateLogic();
                repaint();
            }
            if(lives == 0){
                gameOver = true;
            }
        } 
    }
    public void updateGameObjects(){
        updateTurrets();
        updateLasers();
        updateMeteors();
    }
    public void killGameObjects(){
        killMeteors();
        killLasers();
    }
    public void calculateLogic(){
        updateGameObjects();
        killGameObjects();
    }
    
    public void updateMeteors(){
        for(Meteor m: meteorList){
            if(!deadMeteors.isEmpty()){
                break;
            }
            if(m.getActivity() == false){
                deadMeteors.add(m);
            }
            m.update();
        }       
    }
    
    public void updateLasers(){
        for(Laser l: laserList){
            if(!deadLasers.isEmpty()){
                break;
            }
            for(Meteor m: meteorList){
                if(m.getPosX() < l.getPosX() && l.getPosX() < m.getPosX() + 35){
                    if(m.getPosY() < l.getPosY() && l.getPosY() < m.getPosY() + 35){
                        deadMeteors.add(m);
                        deadLasers.add(l);
                        deathcount++;
                        playSound("meteor_death.wav", -15F);
                    }
                }
            }
            l.update();
        }
    }
    public void updateTurrets(){
        for(Turret t: turretList){
            t.update();
        }
    }
    public void killMeteors(){
        for(Meteor m: deadMeteors){
            meteorList.remove(m);
        }       
        deadMeteors.clear();
    }
    public void killLasers(){
        for(Laser l: deadLasers){
            laserList.remove(l);
        }
        deadLasers.clear();
    }
    public static void killAllMeteors(){
        for(Meteor m: meteorList){
            deadMeteors.add(m);
        }
    }

    
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(mainMenu){
            drawMainMenu(g);
        }
        if(playing){
            if(lives > 0){
                drawLives(g, lives);
                drawScore(g, deathcount);
                g.setColor(Color.WHITE);
                g.drawLine(0, 600, 500, 600);
                
                for(Turret t: turretList){
                    drawTurret(g, t);
                }
                for(Meteor m: meteorList){
                    drawMeteor(g, m);
                }
                for(Laser l: laserList){
                    drawLaser(g, l);
                }
                g.setColor(Color.WHITE);
            }
        }
        if(gameOver){
            drawGameOver(g);
        }
    }
    
    public void drawTurret(Graphics g, Turret t){
        g.setColor(t.getColor());
        g.fillRect(t.getPosX(), t.getPosY(), t.getWidth(), t.getHeight());
        g.fillOval(t.getPosX(), t.getPosY() - t.getHeight()/2, t.getWidth(), t.getWidth());
        g.setColor(Color.WHITE);
        g.fillRect(t.getCenterTurretPoint().x, t.getCenterTurretPoint().y, 1, 1);
        g.drawLine(t.getCenterTurretPoint().x, t.getCenterTurretPoint().y, t.getOuterTurretPoint().x, t.getOuterTurretPoint().y); 
    }
    public void drawMeteor(Graphics g, Meteor m){
        g.setColor(m.getColor());
        g.fillOval(m.getPosX(), m.getPosY(), 35, 35);

    }
    public void drawLaser(Graphics g, Laser l){
        g.setColor(Color.RED);
        g.fillRect(l.getPosX(), l.getPosY(), 5, 5);        
    }
    
    public void drawLives(Graphics g, int lives){
        g.setColor(Color.RED);
        g.setFont(new Font(("Helvetica"), Font.PLAIN, 50));
        g.drawString(Integer.toString(lives), 400, 50);
    }
    public void drawScore(Graphics g, int score){
        g.setColor(new Color(255, 215, 0));
        g.setFont(new Font(("Batang"), Font.PLAIN, 50));
        g.drawString(Integer.toString(score*10), 10, 50);
    }
    public void drawGameOver(Graphics g){
        lastScore = deathcount;
        g.setColor(Color.RED);
        g.setFont(new Font(("Batang"), Font.PLAIN, 75));
        g.drawString("GAME OVER", 15, 100);
        g.setColor(new Color(255, 215, 0));
        g.setFont(new Font(("Batang"), Font.PLAIN, 50));
        g.drawString(Integer.toString(lastScore*10), 25, 200);   
    }
    public void drawMainMenu(Graphics g){
        lastScore = deathcount;
        g.setColor(Color.WHITE);
        g.setFont(new Font(("Batang"), Font.PLAIN, 45));
        g.drawString("METEOR DEFENDER", 15, 100);
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
