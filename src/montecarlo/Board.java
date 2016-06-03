/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package montecarlo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Dendelion
 */
public class Board extends JPanel implements Runnable{
    
    int t = 0;
    
    private int size = 300;
    private int sq = size*size;
    private int counter = sq;
    private int checkedCounter = sq;
    private int id = 0;
    
    private int nbi = 0;
    private int []nbTab = new int[8];
    private Color []colorTab = new Color[8];
    
    private int energySum = 0;
    private int oldID = 0;
    private int oldEnergy = 0;
    private Color newColor = Color.WHITE;
    
    private Cell [][]tab = new Cell[size][size];
    private final Cell [][]temp = new Cell[size][size];
    
    public Conditions cond;
        
        public Board(){
                    
        cond = new Conditions();
        
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                
                tab[i][j] = new Cell();          
                temp[i][j] = new Cell();
            }
        }
    }
   
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        int w = getWidth();
        int h = getHeight();
                              
        int cellW = w/size;
        int cellH = h/size;
        
        int x = (w - size * cellW)/2;
        int y = (h - size * cellH)/2;
                
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(tab[i][j].getID()==0){
                    g2d.setColor(tab[i][j].getColor());
                }else{
                    g2d.setColor(tab[i][j].getColor());
                }
                Rectangle cell = new Rectangle(x + j*cellW,
                            y + i*cellH,
                            cellW,
                            cellH);
                g2d.fill(cell);
            }
        }     
    }
    
    public void checkPosition(int i, int j){       //-----sprawdz pozycje komorki (granica/ srodek)
        
        for(int k=i-1; k<i+2; k++){
            for(int l=j-1; l<j+2; l++){
                int kk, ll;
                                            
                if(k<0)
                    kk=size-1;
                else if(k>=size)
                    kk=0;
                else
                    kk=k;
                                            
                if(l<0)
                    ll=size-1;
                else if(l>=size)
                    ll=0;
                else 
                    ll=l;
                                            
                if(k==i && l==j)
                    ;//System.out.println("don't check itself");
                else{
                    if(tab[kk][ll].getID()!=tab[i][j].getID()){
                     tab[i][j].onEdge();
                     //break;
                    }                   
                }
            }
        }
    }
    
    public void cleaning(){
        energySum = 0;
        nbi = 0;
        
//        oldID = 0;
//        oldEnergy = 0;
//        newColor = Color.WHITE;
        
        for(int i=0; i<8; i++){
            nbTab[i]=0;
            colorTab[i] = Color.WHITE;
        }
    }
    
    
    
    
    public int countEnergy(int i, int j){
        
        cleaning();
        
        for(int k=i-1; k<i+2; k++){
            for(int l=j-1; l<j+2; l++){
                int kk, ll;
                                            
                if(k<0)
                    kk=size-1;
                else if(k>=size)
                    kk=0;
                else
                    kk=k;
                                            
                if(l<0)
                    ll=size-1;
                else if(l>=size)
                    ll=0;
                else 
                    ll=l;
                                            
                if(k==i && l==j)
                    ;//System.out.println("don't check itself");
                else{
                    nbTab[nbi] = tab[kk][ll].getID();
                    colorTab[nbi] = tab[kk][ll].getColor();
                    nbi++;
                    if(tab[i][j].getID()!=tab[kk][ll].getID()){
                        energySum++;
                    }
                }                   
            }
        }
        
        return energySum;
    }
    
    public void chooseID(int i, int j){
        Random rand = new Random();
        int rr = rand.nextInt(8);
        oldID = tab[i][j].getID();
        oldEnergy = tab[i][j].getEnergy();
        tab[i][j].setID(nbTab[rr]);
        newColor = colorTab[rr];
    }
    
    public void unchecked(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                tab[i][j].unchecked();
            }
        }
    }
    
    public void action(){
        unchecked();
        checkedCounter=sq;
        //System.out.println(" nie spe "+checkedCounter);
        t++;
        int a, b;
        
        Random random = new Random();
                
        while(checkedCounter>0){
            
            a = random.nextInt(size);
            b = random.nextInt(size);
            
            //System.out.println("wylosowalem "+a+" "+b);
            
            if(!tab[a][b].isChecked() && tab[a][b].isOnEdge()){
                
                //System.out.println("wybralem: "+a+" "+b);
                
                checkPosition(a, b);
                
                tab[a][b].setEnergy(countEnergy(a, b));
                chooseID(a, b);
                tab[a][b].setEnergy(countEnergy(a, b));
                
                if(tab[a][b].getEnergy()<=oldEnergy){
                    tab[a][b].setColor(newColor);
                    //System.out.println("zmieniam ID");
                }else{
                    tab[a][b].setID(oldID);
                    //System.out.println("nie zmieniam ID");
                }
                
                tab[a][b].checked();
                checkedCounter--;
                oldID = 0;
                oldEnergy = 0;
                newColor = Color.WHITE;
                
                repaint();
            }
        }
    }
    
    
    @Override
    public void run() {
        
        System.out.println("Running ...");
        
        while(true){
            if(cond.getStatus()==1){
                //System.out.println(t);
                this.action();
                 //repaint();
            }
            if(cond.getStatus()==2){
                this.clean();
                //repaint();
            }
            if(cond.getStatus()==3){
                this.initialize();
            }
            
            
            try{
                Thread.sleep(100);
            }catch (Exception ex){
            }
        }
    }

    public void clean(){
        
        id=0;
        counter = sq;
        checkedCounter = sq;
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){

                tab[i][j] = new Cell();
                temp[i][j] = new Cell();
                repaint();
            }
        }
        repaint();
        cond.setStatus(0);
    }
    
    public void initialize(){
        
        int a, b;
        
        Random random = new Random();
                
        while(counter>0){
            
            a = random.nextInt(size);
            b = random.nextInt(size);
            
            if(tab[a][b].getID()==0){
                id++;
                tab[a][b].setID(id);
                tab[a][b].drawColor();
                counter--;
                //System.out.println(a+" "+b+" id:"+tab[a][b].getID());
                repaint();
            }
        }
    }
}
