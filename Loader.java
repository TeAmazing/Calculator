package com.teamazing.backingbeans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

/**
 * This block of code was found on the internet
 * @javatutorials.com
 * @author javatutorials
 * Utilized by TeAmazing as base for uploading tabulations
 */

@ManagedBean(name = "loader")
@ViewScoped

public class Loader implements Serializable {

    private static final long serialVersionUID = 9040359120893077422L;
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    private Part part;
    private String statusMessage;

    public String uploadFile() throws IOException 
    {
        // Extract file name from content-disposition header of file part
        String fileName = getFileName(part);
        System.out.println("***** fileName: " + fileName);

        String basePath = "C:" + File.separator + "temp" + File.separator;
        File outputFilePath = new File(basePath + fileName);

        // Copy uploaded file to destination path
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try 
        {
                inputStream = part.getInputStream();
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
