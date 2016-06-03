/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package montecarlo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Dendelion
 */
public final class MyFrame extends JFrame{
    
    private final JPanel contentPane;
    private final Board board;
    private Conditions c;
    
    public MyFrame(){
        
        board = new Board();
        c = board.cond;
        
        setTitle("Monte Carlo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());      
        setSize(this.getPrefferredSize());
        
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        
        setContentPane(contentPane);
        
        JPanel panel = new JPanel();
	contentPane.add(panel, BorderLayout.NORTH);
        
        JButton initializeButton = new JButton("Initialize");
        initializeButton.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e){
                c.setStatus(3);
                System.out.println("Initializing");
            }
        });
                         
        JButton startButton = new JButton("Start");
        startButton.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e){
                c.setStatus(1);
                System.out.println("Start clicked");
            }
        });
        
        JButton stopButton = new JButton("Stop");
        stopButton.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e){
                c.setStatus(0);
                System.out.println("Stop clicked");
            }
        });
        
        JButton cleanButton = new JButton("Clean");
        cleanButton.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e){
                c.setStatus(2);
                System.out.println("Clean clicked");
            }
        });
        
        panel.add(initializeButton);
        panel.add(startButton);
        panel.add(stopButton);
        panel.add(cleanButton);
            
        contentPane.add(board, BorderLayout.CENTER);
        
        new Thread(board).start();   
    } 
    
    public Dimension getPrefferredSize(){
        return new Dimension(800, 800);
    }

}
