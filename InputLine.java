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

package JustJava;

/**
 *
 * @author Kaigh
 */
public class InputLine 
{
    private String operand = "0.0";
    private String operator = "";
    private String comments = "";

    public InputLine(String oprnd, String oprtr, String cmnts) 
    {
        this.operand = oprnd;
        this.operator = oprtr;
        this.comments = cmnts;
    }

    // GETTERS
    public String getOperand() 
    {
        return operand;
    }

    public String getOperator() 
    {
        return operator;
    }

    public String getComments() 
    {
        return comments;
    }


    // SETTERS
    public void setOperator(String operator) 
    {
        this.operator = operator;
    }

    public void setOperand(String operand) 
    {
        this.operand = operand;
    }

    public void setComments(String comments) 
    {
        this.comments = comments;
    }
    
}
