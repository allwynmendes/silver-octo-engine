/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JavaApplication1 {
    static String FILENAME = null;
    static String REPORT = null;
    static String LOG = null;
    static String TARGET = null;
    
    static void readStartMe(){
        System.out.println("Working Directory = " +
              System.getProperty("user.dir"));
        int loopNumber = 1;
        BufferedReader br = null;
        FileReader fr = null;
        LinkedList<String> files = new LinkedList<String>();
        try{
            fr = new FileReader("startMe.txt");
            br = new BufferedReader(fr);
            String sCurrentLine;
            br = new BufferedReader(new FileReader("startMe.txt"));
            
            while((sCurrentLine = br.readLine()) != null){
                System.out.println(sCurrentLine);
                files.add(sCurrentLine);
                switch(loopNumber){
                    case 1:
                        FILENAME = sCurrentLine;
                        break;
                    case 2:
                        REPORT = sCurrentLine;
                        break;
                    case 3:
                        LOG = sCurrentLine;
                        break;
                    case 4:
                        TARGET = sCurrentLine;
                        File file = new File(TARGET);
                        if (!file.exists()) {
                            if (file.mkdir()) {
                                System.out.println("Directory is created!");
                            } else {
                                System.out.println("Failed to create directory!");
                            }
                        }
                        break;
                }
                loopNumber++;
            }
        }
        catch(Exception e){
            
        }
        //System.exit(0);
    }
    
    public static void main(String[] args) throws IOException {
        //gc1.mainFunction();
        readStartMe();
        CopyFilesLocally cfl1 = new CopyFilesLocally(REPORT, LOG, TARGET);
        LogGenerator lg1 = new LogGenerator(LOG);
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
            e.printStackTrace();
            lg1.createLog("file_list.txt not found in required directory.");
            System.out.println("file_list.txt not found in required directory.");
        }   
        lg1.createLog("Program End");
    }
}
