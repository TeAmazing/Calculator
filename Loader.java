/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamazing.backingbeans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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

@ManagedBean(name = "loader")
@RequestScoped

public class Loader implements Serializable 
{

    private static final long serialVersionUID = 9040359120893077422L;
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    private Part part;
    private String statusMessage;
    private String fileDate;
    private final ArrayList<InputLine> uploadLines = new ArrayList<InputLine>();
   
    private DateFormat dateFormat;  
    private Date date;

    
    public String uploadFile() throws IOException 
    {
        // Extract file name from content-disposition header of file part
        String fileName = getFileName(part);
        System.out.println("***** fileName: " + fileName);
        
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
        File outputFilePath = new File(basePath + fileDate + fileName);

        // Copy uploaded file to destination path
        InputStream inputStream = null; // This creates the input stream
        OutputStream outputStream = null;
        try 
        {
                inputStream = part.getInputStream(); // This fills the input stream
                outputStream = new FileOutputStream(outputFilePath);

                int read = 0;
                final byte[] bytes = new byte[DEFAULT_BUFFER_SIZE]; //Originally 1024
                while ((read = inputStream.read(bytes)) != -1) 
                {
                        outputStream.write(bytes, 0, read);
                }

                statusMessage = "File upload successfull !!";
        } 
        catch (IOException e) 
        {
                e.printStackTrace();
                statusMessage = "File upload failed !!";
        } 
        finally 
        {
                if (outputStream != null) 
                {
                        outputStream.close();
                }
                if (inputStream != null) 
                {
                        inputStream.close();
                }
                part.delete();  //Clear this from the stream
        }
        return "index";    // return to calculator page
    }

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
    // Extract file name from content-disposition header of file part
    private String getFileName(Part part) 
    {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("***** partHeader: " + partHeader);
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
}
   
