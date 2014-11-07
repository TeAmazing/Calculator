/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
