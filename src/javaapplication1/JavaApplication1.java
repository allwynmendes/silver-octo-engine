/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JavaApplication1 {
    
    static String FILENAME = "C:\\Users\\inrp10181\\Documents\\JavaApplication1\\files_list.txt";
    static CopyFilesLocally cfl1 = new CopyFilesLocally();
    //static GmailClass gc1 = new GmailClass();
    static LogGenerator lg1 = new LogGenerator();
    public static void main(String[] args) throws IOException {
        //gc1.mainFunction();
        lg1.createLog("Program Start");
        BufferedReader br = null;
        FileReader fr = null;
        LinkedList<String> files = new LinkedList<String>();
        try{
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);
            String sCurrentLine;
            br = new BufferedReader(new FileReader(FILENAME));
            
            while((sCurrentLine = br.readLine()) != null){
                //System.out.println(sCurrentLine);
                files.add(sCurrentLine);
            }
            cfl1.readFiles(files);
        }catch(Exception e){
            //e.printStackTrace();
            lg1.createLog("file_list.txt not found in required directory.");
            System.out.println("file_list.txt not found in required directory.");
        }   
        lg1.createLog("Program End");

    }
}
