/**
 * Class: CMSC 495 Current Trends and Projects in Computer Science
 * Instructor: Nicholas Duchon - Nicholas.Duchon@umuc.edu
 * Coder: Kaigh Taylor
 * Team: TeAmazing
 * Date of Coding: 10/27/2014
 * Description: Project - JSF Tabulating Calculator
 * Platform:  Netbeans IDE 7.4
 * Due: 
 * I pledge that I have completed the programming assignment
   with the assistance of TeAmazing members.
   This code has been developed for our team alone.
   Print your Name here: Kaigh Taylor, TeAmazing Lead Programmer
*/


package com.teamazing.beans;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;

import java.util.ArrayList; // The ArrayList library

import com.teamazing.modelbeans.InputLine;
// I thought this was unnecessary with @ManagedProperty
//import com.teamazing.backingbeans.Loader;
import com.towel.math.Expression;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


//import Beans.OutputDisplay;

//import Beans.OutputDisplay;


/**
 * The ViewScoped allows persistent variable values
 */
@Named
@SessionScoped
public class Controls implements Serializable 
{

    // Variable Declarations;
    @SuppressWarnings("FieldMayBeFinal")
    private final Logger logger = Logger.getLogger(Controls.class.getName());
    
    private String outputDisplayArea ;
    private ArrayList<InputLine> outputLines = new ArrayList<InputLine>();
    
    private String currentDisplay;
    private String display1;
    private String oldDisplay1; //Hold value for Edit Mode check
    
    private String display2;
    private String oldDisplay2; //Hold value for Edit Mode check
    
    private String strOperator; 
    private String strOldOperator; //Hold value for Edit Mode check
    
    private String message;
    private String strEquation;
    private Double result;
    
    private Boolean dblClear;
    private Boolean completEquation; //Determines if equation has been completed
    private Boolean editMode; //This flag indicates if app is in edit mode
    private int stepIndex; //This index holds the current location in the arrayList
    private int endIndex; // This holds the last position in the index
    
    // Constructor
    Controls() 
    {
        outputDisplayArea = "";
        currentDisplay = display1 = "0";
        display2="";
        strEquation ="0";
        message = "";
        result = 0.0;
        
        dblClear=false;
        completEquation=false;
        editMode=false;
        
        stepIndex = 0;
        
    }
    
    // Get and Set Methods
    public String getDisplay1() 
    {
        return display1;
    }

    public String getOldDisplay1() 
    {
        return oldDisplay1;
    }

    public String getDisplay2() 
    {
        return display2;
    }
    
    public String getOldDisplay2() 
    {
        return oldDisplay2;
    }

    public String getStrOperator() 
    {
        return strOperator;

    }

    public String getStrOldOperator() 
    {
        return strOldOperator;
    }

    public String getOutputDisplayArea() 
    {
        return outputDisplayArea;
    }
    
    public String getStrEquation() 
    {
        return strEquation;
    }

    public String getMessage() 
    {
        return message;
    }
    
    public Double getResult() 
    {
        return result;
    }

    public void setStrOperator(String strOperator) {
        this.strOperator = strOperator;
    }
    
    public void setStrOldOperator(String strOldOperator) 
    {
        this.strOldOperator = strOldOperator;
    }
  
    public void setDisplay1(String number1) 
    {
        this.display1 = number1;
    }
  
    public void setOldDisplay1(String oldDisplay1) 
    {
        this.oldDisplay1 = oldDisplay1;
    }

    public void setDisplay2(String comment) 
    {
        this.display2 = comment;
    }

    public void setOldDisplay2(String oldDisplay2) 
    {
        this.oldDisplay2 = oldDisplay2;
    }

    public void setOutputDisplayArea(String outputDisplayArea) 
    {
        this.outputDisplayArea = outputDisplayArea;
    }
    
    public void setOutputDisplayArea(ArrayList<InputLine>  fullDisplay) 
    {
        String temp="";
        String tempSign ="";
        String divOp = "";        
        
        if (!editMode)
        {        
        //this.outputDisplayArea.add(fullDisplay);

            for(InputLine lineEntry : fullDisplay) 
            {   
                //This catches the divide by Zero Error and indicates in comments
                if (tempSign.equals("/") && (Double.parseDouble(lineEntry.getOperand())==0))
                {
                     divOp = "DIVIDE BY ZERO - Infinity Result. ";
                 }
                temp += "\t";
                temp += lineEntry.getOperand();
                temp += "\t";
                temp += divOp + (lineEntry.getComments());
                temp += "\r";           
                temp += (lineEntry.getOperator());
                //This is necessary to catch Divide by Zero 
                tempSign = (lineEntry.getOperator());
                divOp="";
            }
            this.outputDisplayArea = temp; 
            stepIndex = 0;
            endIndex = fullDisplay.size()-1;

        }
        else    //EDIT MODE TRUE
        {
            String tempDisplay1 = "";
            String tempDisplay2 = "";
            String tempOp = "";

            //InputLine lineEntry = fullDisplay.get(stepIndex);
            for (int i = 0; i < fullDisplay.size(); i++) 
            {
                if (stepIndex == i)
                {
                    temp += ">";
                
                    tempDisplay1 = (fullDisplay.get(stepIndex).getOperand());
                    tempDisplay2 = (fullDisplay.get(stepIndex).getComments());
                    currentDisplay = (tempDisplay1);
                    tempOp = (fullDisplay.get(stepIndex).getOperator());
                }
               
                if (tempSign.equals("/") && (Double.parseDouble(fullDisplay.get(i).getOperand())==0))
                {   
                    divOp = "DIVIDE BY ZERO - Infinity Result. ";

                }
               
                temp += "\t";
                temp += (fullDisplay.get(i).getOperand());
                temp += "\t";
                temp += (fullDisplay.get(i).getOperator());
                temp += "\t";
                temp += divOp + (fullDisplay.get(i).getComments());
                temp += "\r";
                
                //This is necessary to catch Divide by Zero 
                tempSign = (fullDisplay.get(i).getOperator());

                divOp = "";
            }
                setDisplay1(tempDisplay1);
                setDisplay2(tempDisplay2);
                setStrOperator(tempOp);
                setMessage(tempOp);

            this.outputDisplayArea = "EDIT MODE" + "\r" + temp;            
            endIndex = fullDisplay.size()-1;
           
        }
        
    }
    
    public String signCheck(String unSign)
    {

        String realSign;
        
        if(Double.parseDouble(unSign)<0)
        {
           realSign = "(0-" + (unSign.substring(1)) + ")";
        }
        else
            realSign = unSign;
        
        return realSign; 
    }

    public void setStrEquation(ArrayList<InputLine>  fullDisplay)
    {
        String tempEquat="";
        for(InputLine lineEntry : fullDisplay) 
        {
            tempEquat += signCheck(lineEntry.getOperand());
            tempEquat += (lineEntry.getOperator());
        }
        
        this.strEquation = ("0+"+tempEquat);
    }
    
    public void setMessage(String msgtxt) 
    {
        this.message = msgtxt;
    }
    
    public void setResult(Double result) 
    {
        this.result = result;
    }

    // deleteString removes last character
    // from the currentDisplay
    public String deleteString(String str) 
    {
        if (str.length() > 0)
        {
            str = str.substring(0, str.length()-1);
        }
        if (str.length()== 0)
            str = "0";
      return str;
    }
    
    public void resetDisplays()
    {
        // After every operand is entered the display is reset
        currentDisplay = "0";            
        setDisplay1(currentDisplay);
        setDisplay2("");
        setStrOperator("");
        setMessage("");
        
    }
    
    public void refeshEditDisplay()
    {
        InputLine totalEntry = new InputLine(getDisplay1(),getStrOperator(),getDisplay2());
        outputLines.set(stepIndex, totalEntry);
        setOutputDisplayArea(outputLines);            
        if ((stepIndex==endIndex) && (completEquation))
        {
            setOldDisplay1(outputLines.get(endIndex).getOperand());
        }        
    }
    
    public void movementProc()
    {
        InputLine totalEntry = new InputLine(getDisplay1(),getStrOperator(),getDisplay2());
        outputLines.set(stepIndex, totalEntry);
        if ((stepIndex==endIndex) && (completEquation))
        {
            setOldDisplay2(outputLines.get(endIndex).getComments());

        }
    }
    
    public void equationReset()
    {
        dblClear=false;

        if ((completEquation) && (!editMode))
        {
            setMessage("Equation Reset");
            outputLines.clear();
            completEquation = false;
        }        
    }
    
    public void updateOperandNormalDisplay()
    {
            InputLine totalEntry = new InputLine(getDisplay1(),getStrOperator(),getDisplay2());
            outputLines.add(totalEntry);

            setOutputDisplayArea(outputLines);

            resetDisplays();
    }
    
    public void updateOperandEditDisplay()
    {
            // If in edit mode commit changes to arraylist and display them
            InputLine totalEntry = new InputLine(getDisplay1(),getStrOperator(),getDisplay2());
            outputLines.set(stepIndex, totalEntry);

            setOutputDisplayArea(outputLines);            
    }
    
    public void integrateLoad(ArrayList<InputLine>  fullDisplay)
    {
        clearAll();
        
        stepIndex = 0;
        endIndex = fullDisplay.size()-1;
        int x = 0; // DEBUG VARIABLE
        System.out.println("~~~~~ Inside intLoad: fullDisplay Size = " + endIndex);
        
        // DEBUG
        for(InputLine lineEntry : fullDisplay) 
        {   
            x++;
            System.out.println("~~~~~ Operand @ "+x +": " + (lineEntry.getOperand()));
            System.out.println("~~~~~ Operator = "+x +": " + (lineEntry.getOperator()));
            System.out.println("~~~~~ Comments = "+x +": " + (lineEntry.getComments()));
            System.out.println();
        }
        // END DEBUG
        
        // A completed equation can be determined by no operator being 
        // in last index
        if (fullDisplay.get(endIndex).getOperator().equals("="))
        {
            System.out.println("~~~~~ Inside intLoad: Found = @ endIndex");
            
            currentDisplay = fullDisplay.get(endIndex).getOperand();
            System.out.println("~~~~~ Inside intLoad @= curentDisplay:" + currentDisplay);
            setDisplay1 (currentDisplay);
            setStrOperator ("");
            setDisplay2(fullDisplay.get(endIndex).getComments());

            fullDisplay.remove(endIndex);
            //DEBUG
            x = 0;
            for(InputLine lineEntry : fullDisplay) 
            {   
                x++;
                System.out.println("~~~~~ lineEntry trimmed ~~~~~~~~~~~");
                System.out.println("~~~~~ Operand @ "+x +": " + (lineEntry.getOperand()));
                System.out.println("~~~~~ Operator = "+x +": " + (lineEntry.getOperator()));
                System.out.println("~~~~~ Comments = "+x +": " + (lineEntry.getComments()));
                System.out.println();
            }
            // END DEBUG
            setOldDisplay1(getDisplay1());
            setOldDisplay2(getDisplay2());            
            setStrOldOperator(getStrOperator());
            
            outputLines = fullDisplay;
            equl();
        }
        // Everything in this method past this point may be unnecesary...  
        // I will do some tests!
        else
        {
            System.out.println("~~~~~ Inside intLoad: No = @ endIndex");
            // If edit Mode was entered as a completed equation
            // and then an operator was added to the last index
            // This is no longer a complete equation
            outputLines = fullDisplay;
            setOutputDisplayArea(fullDisplay);
            resetDisplays();

        }
    }
    public ArrayList<InputLine> saveLines()
    {   
        return outputLines;
    }
    
// ---------------------------------------------    
//############################################################################            
// ---------------------------------------------
//   Methods below are calculator key methods    
    public void del()
     {
        dblClear=false;

         currentDisplay = deleteString(currentDisplay);
         setDisplay1(currentDisplay);
         // In edit Mode all changes will reflect in the outputDisplay Pane
         if (editMode)
         {
            InputLine totalEntry = new InputLine(getDisplay1(),getStrOperator(),getDisplay2());
            outputLines.set(stepIndex, totalEntry);
            setOutputDisplayArea(outputLines);            
            if ((stepIndex==endIndex) && (completEquation))
            {
                setOldDisplay1(outputLines.get(endIndex).getOperand());
            }
         }
     }

     // Clear display; Reset the operator
     public void clear()
     {
         // This needs to clear the current textbox being input in. 
         // Clear All will clear the entire equation - with Warning Dialogue
         
         if ((completEquation) && (!editMode))
         {
             clearAll();
             return;
         }

         currentDisplay="0";
         
         // CHANGE NEEDED - Determine which textbox is being worked on. 
         // Clear that box only. This may requiren a switch statement.
         setDisplay1(currentDisplay);
         setDisplay2("");
         if (!editMode)
            setStrOperator("");
         setMessage("");
         
         if (editMode)
         {
             //In editMode clicking clr button 2x in row will delete the current line
            if (dblClear)
            {
                outputLines.remove(stepIndex);
                if (stepIndex==endIndex)
                {
                    if (outputLines.isEmpty())
                    {
                        editMode=false;
                        setOutputDisplayArea(outputLines);   
                        dblClear=false;
                        return;                    
                    }
                    endIndex = outputLines.size()-1;
                    stepIndex = endIndex;
                }
                
                setOutputDisplayArea(outputLines);   
                dblClear=false;
                return;
            }
            InputLine totalEntry = new InputLine(getDisplay1(),getStrOperator(),getDisplay2());
            outputLines.set(stepIndex, totalEntry);
            setOutputDisplayArea(outputLines);   
            dblClear=true;
            
         }
         
         //completEquation = false;
     }
     
     public void clearAll()
     {
         // This needs to clear the current textbox being input in. 
         // Clear All will clear the entire equation - with Warning Dialogue
         currentDisplay="0";
         
         // CHANGE NEEDED - Determine which textbox is being worked on. 
         // Clear that box only. This may requiren a switch statement.
         setDisplay1(currentDisplay);
         setDisplay2("");
         setStrOperator("");
         setMessage("");
         setOldDisplay1("");
         setOldDisplay2("");            
         setStrOldOperator("");
         setOutputDisplayArea("");
         strOperator = ""; //NULL
         outputLines.clear();
         dblClear=false;
         editMode=false;
         completEquation=false;
     }

     // Flip value between positive and negative
     public void flip()
     {
         dblClear=false;
         if (currentDisplay != null && !currentDisplay.isEmpty() 
                 && !currentDisplay.equals("0"))
         {
             if(currentDisplay.charAt(0)=='-')

                 currentDisplay=currentDisplay.substring(1);

             else
                 currentDisplay= ("-" + currentDisplay);          

             setDisplay1(currentDisplay);
         }
         // In edit Mode all changes will reflect in the outputDisplay Pane
         if (editMode)
             refeshEditDisplay();         
     }

     // All number buttons check for leading zero; empty(ness)
     // After certain operations the NaN value would appear
     // The Nan issue should be obsolete
     public void seven()
     {
         dblClear=false;
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || ((completEquation) && (!editMode)))
             currentDisplay="7";
         else
             currentDisplay+="7";

         setDisplay1(currentDisplay);

         // In edit Mode all changes will reflect in the outputDisplay Pane
         if (editMode)
             refeshEditDisplay();         
     }

     public void eight()
     {
         dblClear=false;
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || ((completEquation) && (!editMode)))

             currentDisplay="8";
         else
             currentDisplay+="8";

         setDisplay1(currentDisplay);

         // In edit Mode all changes will reflect in the outputDisplay Pane
         if (editMode)
             refeshEditDisplay();         

     }

     public void nine()
     {
         dblClear=false;
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || ((completEquation) && (!editMode)))
             currentDisplay="9";
         else
             currentDisplay+="9";

         setDisplay1(currentDisplay);
         
         // In edit Mode all changes will reflect in the outputDisplay Pane
         if (editMode)
             refeshEditDisplay();         
     }

      public void six()
     {
         dblClear=false;
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || ((completEquation) && (!editMode)))
             currentDisplay="6";
         else
             currentDisplay+="6";

         setDisplay1(currentDisplay);

         // In edit Mode all changes will reflect in the outputDisplay Pane
         if (editMode)
             refeshEditDisplay();         
     }

     public void five()
     {
         dblClear=false;
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || ((completEquation) && (!editMode)))
             currentDisplay="5";
         else
             currentDisplay+="5";

         setDisplay1(currentDisplay);

         // In edit Mode all changes will reflect in the outputDisplay Pane
         if (editMode)
             refeshEditDisplay();         
     }

     public void four()
     {
         dblClear=false;
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || ((completEquation) && (!editMode)))
             currentDisplay="4";
         else
             currentDisplay+="4";

         setDisplay1(currentDisplay);

         // In edit Mode all changes will reflect in the outputDisplay Pane
         if (editMode)
             refeshEditDisplay();         
     }

     public void three()
     {
         dblClear=false;
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || ((completEquation) && (!editMode)))
             currentDisplay="3";
         else
             currentDisplay+="3";

         setDisplay1(currentDisplay);

         // In edit Mode all changes will reflect in the outputDisplay Pane
         if (editMode)
             refeshEditDisplay();         
     }

     public void two()
     {
         dblClear=false;
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || ((completEquation) && (!editMode)))
             currentDisplay="2";
         else
             currentDisplay+="2";

         setDisplay1(currentDisplay);

         // In edit Mode all changes will reflect in the outputDisplay Pane
         if (editMode)
             refeshEditDisplay();         
     }

     public void one()
     {
         dblClear=false;
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || ((completEquation) && (!editMode)))
             currentDisplay="1";
         else
             currentDisplay+="1";

          setDisplay1(currentDisplay);

         // In edit Mode all changes will reflect in the outputDisplay Pane
         if (editMode)
             refeshEditDisplay();         
     }

     public void zero()
     {
         dblClear=false;
         if (currentDisplay.equals("") || currentDisplay.isEmpty() 
                 || !currentDisplay.equals("0"))
                  currentDisplay+="0";
         else if (currentDisplay.equals("NaN") || ((completEquation) && (!editMode)))
             currentDisplay = "0";
         setDisplay1(currentDisplay);

         // In edit Mode all changes will reflect in the outputDisplay Pane
         if (editMode)
             refeshEditDisplay();         
     }

     // Add a decimal only if no decimal is in the string
     public void decim()
     {
         dblClear=false;
         int count = 0;
         for(int i =0; i < currentDisplay.length(); i++)
             if(currentDisplay.charAt(i) == '.')
                 count++;
         if (count == 0)
         {
                 currentDisplay+=".";
                 setDisplay1(currentDisplay);
         }

         // In edit Mode all changes will reflect in the outputDisplay Pane
         if (editMode)
             refeshEditDisplay();         
     }

     //The operator buttons move the operand  to 
     //the upper display and put zero in lower operand
     //They also display the operator value in the message bar
     public void add() 
     {
        // If the previous calculation complete reset all
        equationReset() ;               
        setStrOperator("+"); //ADD
        
        if (!editMode)
            updateOperandNormalDisplay();
        else
            updateOperandEditDisplay();
     }

     public void subtract() 
     {
        equationReset() ;               
        setStrOperator("-"); //SUBTRACT
        
        if (!editMode)
            updateOperandNormalDisplay();
        else
            updateOperandEditDisplay();

     }

     public void multiply() 
     {
        equationReset() ;               
        setStrOperator("*"); //MULTIPLY
        
        if (!editMode)
            updateOperandNormalDisplay();
        else
            updateOperandEditDisplay();

     }

     public void divide() 
     {
        equationReset() ;               
        setStrOperator("/"); //DIVIDE
        
        // The check divide by zero is not in effect yet!!
        if (!editMode)
            updateOperandNormalDisplay();
        else
            updateOperandEditDisplay();

     }
     
     public void squaroot()
     {
         dblClear=false;
        // This is a holder for an expansion of this application          
     }
     
     public void percentOf()
     {
         dblClear=false;
        // This is a holder for an expansion of this application          
     }
     
     public void fractionOf()
     {
         dblClear=false;
        // This is a holder for an expansion of this application 
     }
     
     public void moveUp()
     {
         dblClear=false;
        if (!outputLines.isEmpty())
        {
            if (editMode)
            {
                movementProc();
                if (stepIndex >0)
                     stepIndex--;
            }

            resetDisplays();
            editMode = true;
            setOutputDisplayArea(outputLines);
        }
        else
            editMode = false;
     }
     
     public void moveDown()
     {
        dblClear=false;
        if (!outputLines.isEmpty())
        {
            if (editMode)
             {
                movementProc();
                if (stepIndex < endIndex)
                    stepIndex++;
             }

             resetDisplays();
             editMode = true;
             setOutputDisplayArea(outputLines);
        }
        else
            editMode = false;
     }
     
     public void newEntry()
    {
        dblClear=false;
        if (editMode)
        {
            editMode = false;
            // A check needs to be made to determine if editMode was entered
            // from a completed equation. 
            
            if (completEquation)
            {
                completEquation = false;
                // A completed equation can be determined by no operator being 
                // in last index
                if (outputLines.get(endIndex).getOperator().equals(""))
                {
                    outputLines.remove(endIndex);
                    setOutputDisplayArea(outputLines);
                    resetDisplays();
                    
                    currentDisplay = (getOldDisplay1());
                    setDisplay1 (currentDisplay);
                    setStrOperator (getStrOldOperator());
                    setDisplay2(getOldDisplay2());
                    equl();
                }
                // Everything in this method past this point may be unnecesary...  
                // I will do some tests!
                else
                {
                    // If edit Mode was entered as a completed equation
                    // and then an operator was added to the last index
                    // This is no longer a complete equation
                    setOutputDisplayArea(outputLines);
                    resetDisplays();
                                        
                }
            }
            else
            {
                
                setOutputDisplayArea(outputLines);
                resetDisplays();

            }
            
        }
    }
 
     // Equl is the only way to apply the operator to the operands
     // Division by zero is not allowed.
     // After the operation, completEquation is set to false
     public void equl()
     {
         dblClear=false;
         // Equl uses the Expression class in Towel jar;
         // Expression can convert a string equation into a Double result
         // Equl not allowed in edit mode
        if ((!completEquation) && (!editMode))
        {
            String strTemp, strResult;

            InputLine totalEntry = new InputLine(getDisplay1(),getStrOperator(),getDisplay2());
            setOldDisplay1(getDisplay1());
            setOldDisplay2(getDisplay2());            
            setStrOldOperator(getStrOperator());
            
            outputLines.add(totalEntry);

            setOutputDisplayArea(outputLines);

            setStrEquation(outputLines);

            // This 
            Expression exp = new Expression(getStrEquation());
            setResult(exp.resolve());
            strResult = String.valueOf(getResult());

            strTemp = getOutputDisplayArea();
            strTemp += "=\t";
            strTemp += strResult;

            setDisplay1(strResult);
            currentDisplay = strResult;

            setOutputDisplayArea(strTemp);
            completEquation=true;
        }

     }
     
    public void saveIt()
    {
        dblClear=false;
        
        
    }
    
    public String loadIt()
    {
        
        return "loadIt";
    }

}
