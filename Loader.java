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

import com.teamazing.modelbeans.InputLine;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.ArrayList;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

/**
 * This block of code was found on the internet
 * @javatutorials.com
 * @author javatutorials
 * Utilized by TeAmazing as base for uploading tabulations
 * 
 * This file will be changed. Currently, it uploads a file and then
 * saves that file in a directory named c:\calcTab
 * 
 * After the upload, the file will need to be parsed into type InputLine
 * and sent back tot index.xhtml
 * 
 */

@Named
@RequestScoped

public class Loader implements Serializable 
{
           

   private static final String regExp = "[\\x00-\\x20]*[+-]?(((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*";
    private static final Pattern pattern = Pattern.compile(regExp);

    private ArrayList<InputLine> uploadLines;
 
    private Part part;
    private String statusMessage;
    private String fileDate;
  
    
    @Inject private Controls controls;
    
    public Part getPart() 
    {
        return part;
    }

    public void setPart(Part part) 
    {
        this.part = part;
    }

    public String getStatusMessage() 
    {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) 
    {
        this.statusMessage = statusMessage;
    }

    public String uploadFile() throws IOException 
    {
        // Extract file name from content-disposition header of file part
        String fileName = getFileName(part);
        System.out.println("*****uploadFile fileName: " + fileName);
        
        String errorMsg = "Upload Successful!";
        
        // This creates the input stream from the file
        InputStream inputStream = null; 
        
        // In this block the file contents are put in the input stream and 
        // parsed into an arraylist of Object InputLine
        try 
        {
                inputStream = part.getInputStream(); // This fills the input stream
                parseFile(inputStream);  // This send the input stream to the parser
                    
                statusMessage = "File upload successfull !!";
        } 
        catch (IOException e) 
        {
            errorMsg = e.getMessage();
            System.out.println(errorMsg);
            statusMessage = "File upload failed !!";
        } 
        finally 
        {
            if (inputStream != null) 
            {
                inputStream.close();
            }

            //Clear this from the stream
            part.delete();          
        }
       
        controls.setMessage(fileName + " " + errorMsg);
        
        return "index";    // return to calculator page
    }

    // Extract file name from content-disposition header of file part
    private String getFileName(Part part) 
    {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) 
        {
                if (content.trim().startsWith("filename")) 
                {
                        return content.substring(content.indexOf('=') + 1).trim()
                                        .replace("\"", "");
                }
        }
        return null;
    }
    
    // The parser reads one line at a time and accepts only 3 elements per line
    // which are separated by commas. The proper format is: operand,operator,comment
    // Each line will be evaluated for validity and file will be accepted if correct
    // Every line must have an operand. Only the final line may not have an operator.
    // Only the final line may have an = operator. Valid operators (+, -, *, /, =)
    // Comments are optional on every line.
    private void parseFile(InputStream csvInput) throws IOException
    {
        @SuppressWarnings("UnusedAssignment")       

        // Prepare.
        BufferedReader csvReader = null;
        uploadLines = new ArrayList<InputLine>();

	String line = "";
	String cvsSplitBy = ",";
        
        String operand = "0.0";
        String operator = "";
        String comments = "";
        
        InputLine lineEntry;
        
        try 
        {
            // Create a BufferedReader to read file in one line at a time.
            csvReader = new BufferedReader(new InputStreamReader(csvInput, "UTF-8"));

            // br = new BufferedReader(new FileReader(csvFile));
            while ((line = csvReader.readLine()) != null) 
            {

                // use comma as separator. Maximum of 3 splits (parts) per line
                String[] newLine = line.split(cvsSplitBy, 3);
                operand = newLine[0];
                operator = newLine[1];
                comments = newLine[2];

                lineEntry = new InputLine(operand,operator,comments);
                uploadLines.add(lineEntry);

            }
 
	} 
        catch (IOException e) 
        {
            throw new IOException("Error in parseFile main body!");
	} 
        catch (ArrayIndexOutOfBoundsException e) 
        {
            throw new IOException("Error - array out of bounds!");
	} 
        finally 
        {
            if (csvReader != null) 
            {
                try 
                {
                   csvReader.close();
                } 
                catch (IOException e) 
                {
                    throw new IOException("Error in parseFile closing csvReader!");

                }
            }
	}
        // When file is fully parsed to arraylist send it back to the Contols bean
            csvValidator(uploadLines);
            controls.integrateLoad(uploadLines);
       
        //Debug
	System.out.println("Parse File Worked!");
    }
    
    private void csvValidator(ArrayList<InputLine> fieldCheck) throws IOException
    {
        // The parser reads one line at a time and accepts only 3 elements per line
        // which are separated by commas. The proper format is: operand,operator,comment
        // Each line will be evaluated for validity and file will be accepted if correct
        // Every line must have an operand. Only the final line may not have an operator.
        // Only the final line may have an = operator. Valid operators (+, -, *, /, =)
        // Comments are optional on every line.

        int endIndex = fieldCheck.size()-1;
        int itCount = 0;
        
        String oprtr;
        String comments;
    
        for(InputLine lineEntry : fieldCheck) 
        {   
            if(!isDoubleCompiledRegex(lineEntry.getOperand())) 
            //True operand - File upload continues
            //False operand - File upload cancelled
                throw new IOException(">>>>> csvValidator Operand Error!");
           
           oprtr=lineEntry.getOperator();
           //This could be expanded later for more advanced mathematical calculations
           if ((oprtr.length()==1)&&((oprtr.equalsIgnoreCase("+"))||(oprtr.equalsIgnoreCase("-"))
                   ||(oprtr.equalsIgnoreCase("*"))||(oprtr.equalsIgnoreCase("/"))||(oprtr.equalsIgnoreCase("="))
                   ||(oprtr.equals(" "))))
                   
               //True operator - File upload continues
            {
                if ((itCount != endIndex) && ((oprtr.equalsIgnoreCase("=")) || (oprtr.equalsIgnoreCase(" "))))
                // = operator before end cancel upload.
                {
                 throw new IOException(">>>>> csvValidator Operator Error '=' or blank before EOF!");
                }
           }
           else
               //False operator - File upload cancelled
           {
                throw new IOException(">>>>> csvValidator Operator Error: Length or operator invalid!");
           }
           
           //The only check for comments it on their length. Only first 80 characters will be kept
           comments=lineEntry.getComments();
           if (comments.length()>100)
               lineEntry.setComments(comments.substring(0, 80));
           
           itCount++;
           
        }
    }
    public static boolean isDoubleCompiledRegex(String s) 
    {
        Matcher m = pattern.matcher(s);
        return m.matches();
    }
    
}
