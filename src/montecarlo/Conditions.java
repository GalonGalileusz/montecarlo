/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package montecarlo;

/**
 *
 * @author Dendelion
 */
public class Conditions {
    
    private int status;
    
    public Conditions(){
        this.status = 0;
    }
    
    public void setStatus(int arg){
        status = arg;
    }
    
    public int getStatus(){
        return status;
    }
}
