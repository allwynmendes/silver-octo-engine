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
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateReport{
    static LogGenerator lg1 = new LogGenerator();
    static int counter = 2;
    void createCsv() throws FileNotFoundException{
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("C:\\Users\\inrp10181\\Documents\\JavaApplication1\\report.csv"));
            StringBuilder sb = new StringBuilder();
            sb.append("URL");
            sb.append(',');
            sb.append("FileName");
            sb.append(',');
            sb.append("DST");
            sb.append(',');
            sb.append("DED");
            sb.append(',');
            sb.append("TST");
            sb.append(',');
            sb.append("TED");
            sb.append(',');
            sb.append("ETA");
            sb.append(',');
            sb.append("FSize(KB)");
            sb.append(',');
            sb.append("DL_From");
            sb.append(',');
            sb.append("Status");
            sb.append('\n');
            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException ex) {
            lg1.createLog("report.csv is being used by another program. Program Terminated");
            System.out.println("report.csv is being used by another program. Close such applications and run again.");
            System.exit(0);
        } finally {
            pw.close();
        }
    }
    
    void addToCsv(String url, String fileName, String dST, String dED, String tST, String tED, Double fileSize, int protocolType, String status) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(new FileOutputStream("C:\\Users\\inrp10181\\Documents\\JavaApplication1\\report.csv", true));
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append(',');
        sb.append(fileName);
        sb.append(',');
        sb.append(dST);
        sb.append(',');
        sb.append(dED);
        sb.append(',');
        sb.append(tST);
        sb.append(',');
        sb.append(tED);
        sb.append(',');
        sb.append("=F"+counter+"-E"+counter);   counter++;
        sb.append(',');
        sb.append(fileSize/1024);
        sb.append(',');
        switch(protocolType){
            case 0:
                sb.append("LOCAL");
                break;
            case 1:
                sb.append("WEB");
                break;
            default:
                sb.append("OTHER");
                break;
        }
        sb.append(',');
        sb.append(status);
        sb.append('\n');
        pw.append(sb.toString());
        pw.close();
    }

    void totalDownloadSize(int listLength) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(new FileOutputStream("C:\\Users\\inrp10181\\Documents\\JavaApplication1\\report.csv", true));
        StringBuilder sb = new StringBuilder();
        sb.append("Downloaded (KB) : ");
        sb.append(',');
        sb.append("=SUM(H2:H"+listLength+2+")");
        sb.append('\n');
        pw.append(sb.toString());
        pw.close();
    }
}
