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
    private ArrayList<String> outputDisplayArea = new ArrayList<String>();
    private String currentDisplay;
    private String display1;
    private String display2;
    //private String outputDisplayArea = "";
    private String message;
    private Double lastValue;
    private Double result;
    private Boolean hasOp;
    private int intOperator;
    private int index;


    
    
    // Constructor
    public Controls() 
    {
        currentDisplay = display1 = "0";
        display2="";
        result = lastValue = 0.0;
        hasOp=true;
        intOperator = 1; //ADD
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
        String temp="";
        for(String item: outputDisplayArea)
        {
            temp += (item + "\r");
        }
        return temp;
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
        String showOperator = "";
   
        switch (intOperator) 
        {
          case 1:  //ADD
                   showOperator="+";
                   break;
          case 2:  //SUBTRACT
                   showOperator="-";
                   break;
          case 3:  //MULTIPLY
                   showOperator="*";
                   break;

          case 4:  //DIVIDE - This has to check for divide by zero
                   if (Double.parseDouble(getDisplay1())!=0)
                       showOperator="/";
                   else
                   {
                       showOperator="/";
                       setDisplay2("THIS IS A FATAL ACTION - DIVIDE BY ZERO - " + getDisplay2());
                       //setMessage("DIVIDE: Not by Zero");
                   }
                       
                   break;
         }
        return showOperator;

    }

    public Double getLastValue() {
        return lastValue;
    }

    public void setLastValue(Double lastValue) {
        this.lastValue = lastValue;
    }

    public void setIntOperator(int intOperator) {
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
    
    public void setOutputDisplayArea(String fullDisplay) 
    {
        this.outputDisplayArea.add(fullDisplay);
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
      return str;
    }
    
    public void resetDisplays()
    {
        currentDisplay = "0";            
        setDisplay1(currentDisplay);
        setDisplay2("");
        
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
         currentDisplay="";
         // CHANGE NEEDED - Determine which textbox is being worked on. 
         // Clear that box only. This may requiren a switch statement.
         setDisplay1(currentDisplay);
         setDisplay2(currentDisplay);
         hasOp = false;
         setMessage("No Operator");
     }
     
     public void clearAll()
     {
         
     }

     // Flip value between positive and negative
     public void flip()
     {

         if (currentDisplay != null && !currentDisplay.isEmpty())
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
                 || currentDisplay==null || currentDisplay.equals("NaN"))
             currentDisplay="7";
         else
             currentDisplay+="7";
         setDisplay1(currentDisplay);

     }

     public void eight()
     {
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN"))

             currentDisplay="8";
         else
             currentDisplay+="8";
         setDisplay1(currentDisplay);

     }

     public void nine()
     {
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN"))
             currentDisplay="9";
         else
             currentDisplay+="9";
         setDisplay1(currentDisplay);

     }

      public void six()
     {
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN"))
             currentDisplay="6";
         else
             currentDisplay+="6";
         setDisplay1(currentDisplay);

     }

     public void five()
     {
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN"))
             currentDisplay="5";
         else
             currentDisplay+="5";
         setDisplay1(currentDisplay);

     }

     public void four()
     {
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN"))
             currentDisplay="4";
         else
             currentDisplay+="4";
         setDisplay1(currentDisplay);

     }

     public void three()
     {
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN"))
             currentDisplay="3";
         else
             currentDisplay+="3";
         setDisplay1(currentDisplay);

     }

     public void two()
     {
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN"))
             currentDisplay="2";
         else
             currentDisplay+="2";
         setDisplay1(currentDisplay);

     }

     public void one()
     {
         if (currentDisplay.equals("0") || currentDisplay.equals("") 
                 || currentDisplay==null || currentDisplay.equals("NaN"))
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
         else if (currentDisplay.equals("NaN"))
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

     //The operator buttons move the lower operand to 
     //the upper display and put zero in lower operand
     //They also display the operator value in the message bar
     public void add() 
     {

        index++;
        setOutputDisplayArea(index+". ; "+getIntOperator()+" ; "+getDisplay1()+" ; "+getDisplay2());
        setLastValue(Double.parseDouble(getDisplay1()));
        resetDisplays();
        hasOp=true;
        intOperator = 1; //ADD
        setMessage("ADD");
        //result = This will go to a parse of the ArrayList 
 
     }

     public void subtract() 
     {

        index++;
        setOutputDisplayArea(index+". ; "+getIntOperator()+" ; "+getDisplay1()+" ; "+getDisplay2());
        setLastValue(Double.parseDouble(getDisplay1()));
        resetDisplays();
        hasOp=true;
        intOperator = 2; //SUBTRACT
        setMessage("SUBTRACT");
        //result = This will go to a parse of the ArrayList 


     }

     public void multiply() 
     {

        index++;
        setOutputDisplayArea(index+". ; "+getIntOperator()+" ; "+getDisplay1()+" ; "+getDisplay2());
        setLastValue(Double.parseDouble(getDisplay1()));
        resetDisplays();
        hasOp=true;
        intOperator = 3; //MULTIPLY
        setMessage("MULTIPLY");

     }

     public void divide() 
     {
        // The check divide by zero is not in effect yet!!
        index++;
        setOutputDisplayArea(index+". ; "+getIntOperator()+" ; "+getDisplay1()+" ; "+getDisplay2());
        setLastValue(Double.parseDouble(getDisplay1()));
        resetDisplays();
        hasOp=true;             
        intOperator = 4; //DIVIDE
        setMessage("DIVIDE");


     }
     
     public void squaroot()
     {
         
     }
     
     public void percentOf()
     {
         
     }
     
     public void fractionOf()
     {
         
     }
     
     public void moveUp()
     {
         
     }
     
     public void moveDown()
     {
         
     }
     
     // Equl is the only way to apply the operator to the operands
     // Division by zero is not allowed.
     // After the operation, hasOp is set to false
     public void equl()
     {
         //if (hasOp)
         {
             //DEBUG - setMessage(String.valueOf(intOperator));
             // This is all turned off for now -- Parser must be added
             switch (intOperator) 
             {
                case 1:  //ADD
/*                         result = Double.parseDouble(display1) + Double.parseDouble(display2);
                         setDisplay1("");
                         currentDisplay = result.toString();
                         setDisplay2(currentDisplay);
                         hasOp=false;
                         setMessage("No Operator"); */
                         break;
                case 2:  //SUBTRACT
/*                         result = Double.parseDouble(display1) - Double.parseDouble(display2);
                         setDisplay1("");
                         currentDisplay = result.toString();
                         setDisplay2(currentDisplay);
                         hasOp=false;
                         setMessage("No Operator"); */
                         break;
                case 3:  //MULTIPLY
/*                         result = ((Double.parseDouble(display1)) * (Double.parseDouble(display2)));
                         setDisplay1("");
                         currentDisplay = result.toString();
                         setDisplay2(currentDisplay);
                         hasOp=false;
                         setMessage("No Operator"); */
                         break;
                case 4:  //DIVIDE
/*                         if (Double.parseDouble(display1)!=0)
                         {
                             result = ((Double.parseDouble(display1)) / (Double.parseDouble(display2)));
                             setDisplay1("");
                             currentDisplay = result.toString();
                             setDisplay2(currentDisplay);
                             hasOp=false;
                             setMessage("No Operator");
                         }
                         else
                             setMessage("DIVIDE: Not by Zero"); */                  
                         break;
            }   
             /* 
             if (!hasOp)
                 intOperator = 0;   
             */

         }  
    }
     
    public void saveIt()
    {
        
    }
    
    public void loadIt()
    {
        
    }

}
