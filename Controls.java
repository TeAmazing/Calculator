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


package Beans;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedProperty;

import java.util.ArrayList; // The ArrayList library
import java.util.Iterator; // The Iterator Library
import java.util.Arrays; // The Arrays Library

import JustJava.InputLine;
import com.towel.math.Expression;

//import Beans.OutputDisplay;

//import Beans.OutputDisplay;


/**
 * The ViewScoped allows persistent variable values
 */
@ManagedBean
@ViewScoped
public class Controls 
{

    // Variable Declarations;
    @SuppressWarnings("FieldMayBeFinal")
    private String outputDisplayArea ;
    private ArrayList<InputLine> outputLines = new ArrayList<InputLine>();
    
    private String currentDisplay;
    private String display1;
    private String display2;
    private String message;
    private String strEquation;
    private String intOperator;

    private Double lastValue;
    private Double result;
    private Boolean completEquation; //Determines if equation has been completed
    private int index;
    



    
    
    // Constructor
    public Controls() 
    {
        outputDisplayArea = "";
        currentDisplay = display1 = "0";
        display2="";
        strEquation ="0";
        result = lastValue = 0.0;
        completEquation=false;
        //intOperator = 1; //ADD
        message = "";
        index = 0;
    }
    
    // Get and Set Methods
    public String getDisplay1() 
    {
        return display1;
    }
  
    public String getDisplay2() 
    {
        return display2;
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

    public String getIntOperator() 
    {
        return intOperator;

    }

    public Double getLastValue() {
        return lastValue;
    }

    
    public void setLastValue(Double lastValue) {
        this.lastValue = lastValue;
    }

    public void setIntOperator(String intOperator) {
        this.intOperator = intOperator;
    }
    
  
    public void setDisplay1(String number1) 
    {
        this.display1 = number1;
    }
  
    public void setDisplay2(String comment) 
    {
        this.display2 = comment;
    }

    public void setOutputDisplayArea(String outputDisplayArea) 
    {
        this.outputDisplayArea = outputDisplayArea;
    }
    
    public void setOutputDisplayArea(ArrayList<InputLine>  fullDisplay) 
    {
        //this.outputDisplayArea.add(fullDisplay);
        String temp="";
        String tempSign ="";
        for(InputLine lineEntry : fullDisplay) 
        {   
            //This catches the divide by Zero Error and resets equation
            if (tempSign.equals("/") && (Double.parseDouble(lineEntry.getOperand())==0))
            {
                setMessage("DIVIDE BY ZERO");
                //outputLines.clear();
                completEquation = true;
                break;
             }
            temp += "\t";
            temp += lineEntry.getOperand();
            temp += "\t";
            temp += (lineEntry.getComments());
            temp += "\r";           
            temp += (lineEntry.getOperator());
            tempSign = (lineEntry.getOperator());
        }
        
        this.outputDisplayArea = temp;

    }
    
    public String signCheck(String unSign)
    {

        String realSign;
        
        if(Double.parseDouble(unSign)<0)
        {
           realSign = "(0-" + (unSign.substring(1)) + ")";
           // DEBUG Output
           System.out.println("This is the realSign value: " + realSign);
        }
        else
            realSign = unSign;
        
           // DEBUG Output
        System.out.println("This is the unSign value: " + unSign);
        return realSign; 
    }

    public void setStrEquation(ArrayList<InputLine>  fullDisplay)
    {
        String temp="";
        for(InputLine lineEntry : fullDisplay) 
        {
            temp += signCheck(lineEntry.getOperand());
            temp += (lineEntry.getOperator());
        }
        
        this.strEquation = ("0+"+temp);
           // DEBUG Output
        System.out.println("This is the equation value: " +strEquation);
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
        setIntOperator("");
        
    }
// ---------------------------------------------
//   Methods below are calculator key methods    
    public void del()
     {
         currentDisplay = deleteString(currentDisplay);
         setDisplay1(currentDisplay);
     }

     // Clear display; Reset the operator
     public void clear()
     {
         // This needs to clear the current textbox being input in. 
         // Clear All will clear the entire equation - with Warning Dialogue
         
         if (completEquation)
         {
             clearAll();
             return;
         }
         currentDisplay="0";
         
         // CHANGE NEEDED - Determine which textbox is being worked on. 
         // Clear that box only. This may requiren a switch statement.
         setDisplay1(currentDisplay);
         setDisplay2("");
         setMessage("");         
         completEquation = false;
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
         setMessage("");
         setOutputDisplayArea("");
         intOperator = ""; //NULL
         outputLines.clear();
         completEquation=false;
     }

     // Flip value between positive and negative
     public void flip()
     {

         if (currentDisplay != null && !currentDisplay.isEmpty() 
                 && !currentDisplay.equals("0"))
         {
             if(currentDisplay.charAt(0)=='-')

                 currentDisplay=currentDisplay.substring(1);

             else
                 currentDisplay= ("-" + currentDisplay);          

             setDisplay1(currentDisplay);
         }
     }

     // All number buttons check for leading zero; empty(ness)
     // After certain operations the NaN value would appear
     // The Nan issue should be obsolete
     public void seven()
     {
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || completEquation)
             currentDisplay="7";
         else
             currentDisplay+="7";
         setDisplay1(currentDisplay);

     }

     public void eight()
     {
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || completEquation)

             currentDisplay="8";
         else
             currentDisplay+="8";
         setDisplay1(currentDisplay);

     }

     public void nine()
     {
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || completEquation)
             currentDisplay="9";
         else
             currentDisplay+="9";
         setDisplay1(currentDisplay);

     }

      public void six()
     {
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || completEquation)
             currentDisplay="6";
         else
             currentDisplay+="6";
         setDisplay1(currentDisplay);

     }

     public void five()
     {
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || completEquation)
             currentDisplay="5";
         else
             currentDisplay+="5";
         setDisplay1(currentDisplay);

     }

     public void four()
     {
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || completEquation)
             currentDisplay="4";
         else
             currentDisplay+="4";
         setDisplay1(currentDisplay);

     }

     public void three()
     {
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || completEquation)
             currentDisplay="3";
         else
             currentDisplay+="3";
         setDisplay1(currentDisplay);

     }

     public void two()
     {
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || completEquation)
             currentDisplay="2";
         else
             currentDisplay+="2";
         setDisplay1(currentDisplay);

     }

     public void one()
     {
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN")
                 || completEquation)
             currentDisplay="1";
         else
             currentDisplay+="1";
         setDisplay1(currentDisplay);

     }

     public void zero()
     {
         if (currentDisplay.equals("") || currentDisplay.isEmpty() 
                 || !currentDisplay.equals("0"))
                  currentDisplay+="0";
         else if (currentDisplay.equals("NaN") || completEquation)
             currentDisplay = "0";
         setDisplay1(currentDisplay);
     }

     // Add a decimal only if no decimal is in the string
     public void decim()
     {
         int count = 0;
         for(int i =0; i < currentDisplay.length(); i++)
             if(currentDisplay.charAt(i) == '.')
                 count++;
         if (count == 0)
         {
                 currentDisplay+=".";
                 setDisplay1(currentDisplay);
         }

     }

     //The operator buttons move the operand  to 
     //the upper display and put zero in lower operand
     //They also display the operator value in the message bar
     public void add() 
     {
        // If the previous calculation complete reset all
        if (completEquation)
        {
            setMessage("Equation Reset");
            outputLines.clear();
            completEquation = false;
        }
                
        setIntOperator("+"); //ADD
        
        InputLine totalEntry = new InputLine(getDisplay1(),getIntOperator(),getDisplay2());
        outputLines.add(totalEntry);

        setOutputDisplayArea(outputLines);
        setStrEquation(outputLines);

        index++;
        setLastValue(Double.parseDouble(getDisplay1()));
        resetDisplays();

     }

     public void subtract() 
     {
         
        if (completEquation)
        {
            setMessage("Equation Reset");
            outputLines.clear();
            completEquation = false;
        }
                
        setIntOperator("-"); //SUBTRACT
        
        InputLine totalEntry = new InputLine(getDisplay1(),getIntOperator(),getDisplay2());
        outputLines.add(totalEntry);
        setOutputDisplayArea(outputLines);
        
        index++;
        setLastValue(Double.parseDouble(getDisplay1()));
        resetDisplays();

     }

     public void multiply() 
     {
         
        if (completEquation)
        {
            setMessage("Equation Reset");
            outputLines.clear();
            completEquation = false;
        }
                
        setIntOperator("*"); //MULTIPLY
        
        InputLine totalEntry = new InputLine(getDisplay1(),getIntOperator(),getDisplay2());
        outputLines.add(totalEntry);
        setOutputDisplayArea(outputLines);

        index++;
        resetDisplays();

     }

     public void divide() 
     {
        if (completEquation)
        {
            setMessage("Equation Reset");
            outputLines.clear();
            completEquation = false;
        }
                
        setIntOperator("/"); //DIVIDE
        
        // The check divide by zero is not in effect yet!!
        InputLine totalEntry = new InputLine(getDisplay1(),getIntOperator(),getDisplay2());
        outputLines.add(totalEntry);
        setOutputDisplayArea(outputLines);

        index++;
        setLastValue(Double.parseDouble(getDisplay1()));
        resetDisplays();

     }
     
     public void squaroot()
     {
        // This is a holder for an expansion of this application          
     }
     
     public void percentOf()
     {
        // This is a holder for an expansion of this application          
     }
     
     public void fractionOf()
     {
        // This is a holder for an expansion of this application 
     }
     
     public void moveUp()
     {
         
     }
     
     public void moveDown()
     {
         
     }
     
     // Equl is the only way to apply the operator to the operands
     // Division by zero is not allowed.
     // After the operation, completEquation is set to false
     public void equl()
     {
         // Equl uses the Expression class in Towel jar;
         // Expression can convert a string equation into a Double result
         
         String strTemp, strResult;
         
         InputLine totalEntry = new InputLine(getDisplay1(),getIntOperator(),getDisplay2());
         outputLines.add(totalEntry);

         setOutputDisplayArea(outputLines);
         setStrEquation(outputLines);

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
     
    public void saveIt()
    {
        
    }
    
    public void loadIt()
    {
        
    }

    public void newEntry()
    {
        
    }
}
