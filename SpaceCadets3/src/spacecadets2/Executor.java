/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacecadets2;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
/**
 *
 * @author Lamptington
 */
public class Executor {
    ArrayList<Variable> variables = new ArrayList<Variable>();
    ArrayList<String[]> code = new ArrayList<String[]>();
    int currentLine;
    int count = 0;
    
    public void execute() throws Exception {
        //System.out.println("Executing Program");
        this.readFile();
        for(currentLine = 0; currentLine < code.size(); currentLine++){
            if(code.get(currentLine)[0].equals("clear")|| code.get(currentLine)[0].equals("incr") || code.get(currentLine)[0].equals("decr")){
                manipulate(code.get(currentLine));
            }
            else if(code.get(currentLine)[0].equals("while")){
                whileLoop(currentLine);
            }
            if(code.get(currentLine).length > 1){
                if(code.get(currentLine)[1].equals("+") || code.get(currentLine)[1].equals("-") || code.get(currentLine)[1].equals("*")){
                    math(code.get(currentLine));
                }
            }
        }
        for(int j = 0; j < variables.size(); j++){
            System.out.println("Variable: "+variables.get(j).name+" Value: "+String.valueOf(variables.get(j).value));
        }
        System.out.println(count);
    }
    
    public void readFile() throws Exception {
        //System.out.println("Reading File");
        BufferedReader br = new BufferedReader(new FileReader("D:\\Users\\Lamptington\\My Documents\\NetBeansProjects\\SpaceCadets2\\BareBonesCode.txt"));
        String line;
        while((line = br.readLine()) != null){
            line = line.split(";")[0];
            code.add(line.trim().split(" "));
        }         
    }
    
    public Variable findVariable(String searchName){
        //System.out.println("Finding Variable");
        for(int i = 0; i < variables.size(); i++){
            if(searchName.equals(variables.get(i).name)){
                return variables.get(i);
            }            
        }
        variables.add(new Variable(searchName));
        //System.out.println("Making new Variable");
        return variables.get(variables.size()-1);
    }  
    
    public void manipulate(String[] line){
        //System.out.println("Manipulating Variable");
        Variable target = findVariable(line[1]);
        if(line[0].equals("clear")){target.clear();}
        else if(line[0].equals("incr")){target.increment();}
        else if(line[0].equals("decr")){target.decrement();}
    }
    
    public void math(String[] line){
        System.out.println("Doing math");
        Variable target = findVariable(line[0]);
        Variable secondTarget = findVariable(line[2]);
        if(line[1].equals("+")){target.add(secondTarget);}
        else if(line[1].equals("-")){target.subtract(secondTarget);}
        else if(line[1].equals("*")){target.multiply(secondTarget);}
    }
    
    public void whileLoop(int lineNumber){
        
        Variable controlVariable = this.findVariable(code.get(lineNumber)[1]);        
        int controlValue = Integer.parseInt(code.get(lineNumber)[3]);
        int endLineNumber = 0;
        int i = lineNumber+1;
        System.out.println("Running loop: while "+controlVariable.name+" != "+controlValue);
        boolean endFound = false;
        int whileCount = 0;
        while(i < code.size() && !endFound){
            if(code.get(i)[0].equals("end")){                
                if(whileCount == 0){                    
                    endLineNumber = i;
                    endFound = true;
                }
                else{whileCount--;}
            }
            else if(code.get(i)[0].equals("while")){
                whileCount++;
            }
            i++;
        }
        //System.out.println(endLineNumber);
        //System.out.println("running while loop with variable "+controlVariable.name);
        while(controlVariable.value != controlValue+1){
            //System.out.println("control variable is: "+controlVariable.name+" the value is: "+controlVariable.value);
            for(int q = lineNumber+1; q < endLineNumber; q++){
                if(code.get(q)[0].equals("clear")|| code.get(q)[0].equals("incr") || code.get(q)[0].equals("decr")){
                manipulate(code.get(q));
                }
                else if(code.get(q)[0].equals("while")){
                    whileLoop(q);
                }
            }
            //System.out.println("control variable is: "+controlVariable.name+" the value is: "+controlVariable.value);
            count++;
        }
    }
    
}
