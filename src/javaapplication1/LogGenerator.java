/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogGenerator {
    
    static String LOG = null;
    LogGenerator(String LOG){
        this.LOG = LOG;
    }
    
    void createLog(String logMessage) throws FileNotFoundException{
        String timeStamp;
        PrintWriter pw = new PrintWriter(new FileOutputStream(LOG, true));
        StringBuilder sb = new StringBuilder();
        timeStamp = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss").format(Calendar.getInstance().getTime());
        sb.append(timeStamp + " ---> " + logMessage);
        pw.println(sb.toString());
        pw.close();
    }   
}
