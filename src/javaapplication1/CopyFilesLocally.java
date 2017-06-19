/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.lang.Object;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CopyFilesLocally{
    static String REPORT, LOG, TARGET;
    CopyFilesLocally(String REPORT, String LOG, String TARGET){
        this.REPORT = REPORT;
        this.LOG = LOG;
        this.TARGET = TARGET;
    }
    
    File source;
    File target;
    CreateReport cr1 = new CreateReport(JavaApplication1.REPORT, JavaApplication1.LOG);
    static LogGenerator lg1 = new LogGenerator(LOG);
    void readFiles(LinkedList<String> urls) throws IOException, Exception{
        String url;
        Double fileSize;
        String timeStamp_start = null;
        String timeStamp_end = null;
            String time_start = null;
            String time_end = null;
            String date_start = null;
            String date_end = null;
        String status = null;
        int count = 0;
        Iterator<String> itr = urls.iterator();
        int protocolType;
        cr1.createCsv();
        while(itr.hasNext()){
            url = itr.next();
            protocolType = scanForProtocol(url);
            
            time_start = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
            date_start = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
            switch(protocolType){
                case 0:
                    System.out.println(getUrl(url));
                    System.out.println(getFileName(url));
                    status = downloadFiles(getUrl(url), getFileName(url));
                    time_end = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                    date_end = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                    fileSize = getFileSize(url);
                    cr1.addToCsv(getUrl(url),getFileName(url), date_start, date_end, time_start, time_end, fileSize, protocolType, status);
                    break;
                case 1:
                    System.out.println(getWebUrl(url));
                    System.out.println(getWebFileName(url));
                    status = downloadWebFile(url);
                    time_end = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                    date_end = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                    fileSize = getFileSize(TARGET+getWebFileName(url));
                    cr1.addToCsv(getWebUrl(url),getWebFileName(url), date_start, date_end, time_start, time_end, fileSize, protocolType, status);
                    break;
            }
            count++;
        }
        cr1.totalDownloadSize(count);
    }
    
    int scanForProtocol(String urlStr){
        /*
            Note for scanForProtocol method
            return 0 = local filesystem
            return 1 = http
        */
        String check = urlStr.substring(0,4);
        if(check.equals("http")){
            return 1;
        }
        return 0;
    }
    
    String getWebUrl(String urlStr){
        String fileName1 = urlStr.substring(urlStr.lastIndexOf('/')+1, urlStr.length());
        int len1 = fileName1.length();
        String fileName2 = urlStr.substring(0, urlStr.length()-len1);
        return fileName2;
    }
    
    String getWebFileName(String urlStr){
        String fileName = urlStr.substring(urlStr.lastIndexOf('/')+1, urlStr.length());
        return fileName;
    }
    
    String downloadWebFile(String urlStr) throws Exception{
        URL url = new URL(urlStr);
        InputStream in = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1!=(n=in.read(buf))){
           out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();
        FileOutputStream fos = new FileOutputStream(TARGET+getWebFileName(urlStr));
        fos.write(response);
        fos.close();
        return "SUCCESS(W)";
    }
    
    String getUrl(String urlStr){
        String fileName1 = urlStr.substring(urlStr.lastIndexOf('\\')+1, urlStr.length());
        int len1 = fileName1.length();
        String fileName2 = urlStr.substring(0, urlStr.length()-len1);
        return fileName2;
    }
    
    String getFileName(String urlStr){
        String fileName = urlStr.substring(urlStr.lastIndexOf('\\')+1, urlStr.length());
        return fileName;
    }
    
    double getFileSize(String urlStr){
        File file = new File(urlStr);
        double bytes = file.length();
        return bytes;
    }
    

    String downloadFiles(String url, String file) throws FileNotFoundException{
        String fullPath = url+"\\"+file;
        target = new File(TARGET + file);
        source = new File(fullPath);
        try {
            Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
            lg1.createLog("File Downloaded - " + fullPath);
            return "SUCCESS";
        } catch (IOException ex) {
            lg1.createLog("File Does Not Exist (" + fullPath +") - Not Downloadeds");
            return "FAILED : " + ex.toString();
        }
    }
}
