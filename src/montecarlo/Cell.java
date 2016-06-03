/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package montecarlo;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author Dendelion
 */
public class Cell {
    private int id;
    private int energy;
    
    private boolean checked;
    private boolean onEdge;
    
    private Color color;
    
    
    public Cell(){
        this.id = 0;
        this.energy = 8;
        
        this.checked = false;
        this.onEdge = true;
        
        this.color = Color.WHITE;
    }
    
    public void setID(int id){
        this.id = id;
    }
    
    public int getID(){
        return this.id;
    }
    
    public void setEnergy(int energy){
        this.energy = energy;
    }
    
    public int getEnergy(){
        return this.energy;
    }
    
    public void checked(){
        this.checked = true;
    }
    
    public boolean isChecked(){
        return this.checked;
    }
    
    public void onEdge(){
        this.onEdge = true;
    }
    
    public boolean isOnEdge(){
        return this.onEdge;
    }
    
    public void drawColor(){
        
        float hsb[] = new float[3];
        Random random = new Random();
        Color.RGBtoHSB(random.nextInt(255), random.nextInt(255), random.nextInt(255), hsb);
        this.color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);  
    }
    
    public void setColor(Color c){
        this.color = c;
    }
    
    public Color getColor(){ 
        return this.color;
    }
    
    public void resetColor(){
        this.color = Color.WHITE;
    }

    public void unchecked() {
        this.checked = false;
    }
    
}
