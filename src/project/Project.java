
package project;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Project{
    
    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Final Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel, BorderLayout.CENTER);

        frame.setSize(500, 700);
        frame.setVisible(true);  
        
    }
    
}
