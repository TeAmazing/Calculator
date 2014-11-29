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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@RequestScoped

public class Saver implements Serializable 
{
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    private String statusMessage;
    private String fileDate;
  
    private DateFormat dateFormat;  
    private Date date;
    
    @Inject private Controls controls;
    
    public String getStatusMessage() 
    {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) 
    {
        this.statusMessage = statusMessage;
    }

    public String saveFile() throws IOException 
    {
        
        // Prepare.
    
        // Create Standard File name
        String fileName = "TeAmazing_CalcTab.txt";
        String fullName;
        String fileLine;
        String cvsSplitBy = ",";
        String lineEnd = "\r";
        String errorMsg = "Saved";

        
        System.out.println("***** Save File fileName: " + fileName);
        
        //loaderSetAction(fileName);
        
        // Add the timedate stamp to the file name
        dateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm_");
        date = new Date();
        fileDate = dateFormat.format(date);
        
        // Setup the file to receive the download
        String basePath = "C:" + File.separator + "calcTab"; 
        // Determine if path (Directory) exists, if not create
        createDirectoryIfNeeded(basePath);
        //complete file name
        basePath += File.separator;
        
        fullName = fileDate + fileName;
        File outputFilePath = new File(basePath + fullName);
 
        // if file doesnt exists, then create it
        if (!outputFilePath.exists()) 
        {
            outputFilePath.createNewFile();
        }
            // Copy uploaded file to destination path
            FileWriter fileWrite;
            fileWrite = new FileWriter(outputFilePath);
            //Limit the buffer to 10KB
            BufferedWriter bw = new BufferedWriter(fileWrite, DEFAULT_BUFFER_SIZE);
        try 
        {

            int x = 0;
            for(InputLine lineEntry : controls.saveLines()) 
            {   
                fileLine = (lineEntry.getOperand());
                fileLine += cvsSplitBy;
                fileLine += (lineEntry.getOperator());
                fileLine += cvsSplitBy;
                fileLine += (lineEntry.getComments());
                //fileLine += lineEnd;
                
                bw.write(fileLine);
                bw.newLine();
                x++;

            }
            // END DEBUG


            statusMessage = "File upload successfull !!";
        } 
        catch (IOException e) 
        {
            errorMsg = e.getMessage();
            //e.printStackTrace();
                statusMessage = "File upload failed !!";
        } 
        finally 
        {
            bw.flush();
            bw.close();
        }
       
        controls.setMessage(fullName + " " + errorMsg);
        return "index";    // return to calculator page
    }


    private void createDirectoryIfNeeded(String directoryName)
    {
        File theDir = new File(directoryName);
        System.out.println("@@@@@@ checking directory: " + directoryName);

        // if the directory does not exist, create it
        if (!theDir.exists())
        {
          System.out.println("@@@@@@ creating directory: " + directoryName);
          theDir.mkdir();
        }
    }

}

