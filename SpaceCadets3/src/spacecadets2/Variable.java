/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacecadets2;

/**
 *
 * @author Lamptington
 */
public class Variable {
    String name;
    int value;
    public Variable(String nameParam){
        this.name = nameParam;
    }
    
    public void clear(){
        this.value = 0;
    }
    
    public void increment(){
        this.value++;
    }
    
    public void decrement(){
        if(this.value>0){this.value--;}
    }
    
    public void add(Variable target){
        this.value = this.value + target.value;
    }
    
    public void subtract(Variable target){
        this.value = this.value - target.value;
    }
    
    public void multiply(Variable target){
        this.value = this.value * target.value;
    }
}
