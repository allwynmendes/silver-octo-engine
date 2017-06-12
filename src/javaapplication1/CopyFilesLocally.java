/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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

public class CopyFilesLocally{
    File source;
    File target;
    CreateReport cr1 = new CreateReport();
    void readFiles(LinkedList<String> urls) throws IOException, Exception{
        String url;
        Double fileSize;
        String timeStamp_start;
        String timeStamp_end;
        Iterator<String> itr = urls.iterator();
        int protocolType;
        cr1.createCsv();
        while(itr.hasNext()){
            url = itr.next();
            protocolType = scanForProtocol(url);
            switch(protocolType){
                case 0:
                    System.out.println(getUrl(url));
                    System.out.println(getFileName(url));
                    timeStamp_start = new SimpleDateFormat("ddMMyyyy_HHmmss").format(Calendar.getInstance().getTime());
                    downloadFiles(getUrl(url), getFileName(url));
                    timeStamp_end = new SimpleDateFormat("ddMMyyyy_HHmmss").format(Calendar.getInstance().getTime());
                    fileSize = getFileSize(url);
                    cr1.addToCsv(getUrl(url),getFileName(url), timeStamp_start, timeStamp_end, fileSize, protocolType);
                    break;
                case 1:
                    System.out.println(getWebUrl(url));
                    System.out.println(getWebFileName(url));
                    timeStamp_start = new SimpleDateFormat("ddMMyyyy_HHmmss").format(Calendar.getInstance().getTime());
                    downloadWebFile(url);
                    timeStamp_end = new SimpleDateFormat("ddMMyyyy_HHmmss").format(Calendar.getInstance().getTime());
                    fileSize = getFileSize("C:\\Users\\inrp10181\\Documents\\JavaApplication1\\target\\"+getWebFileName(url));
                    cr1.addToCsv(getWebUrl(url),getWebFileName(url), timeStamp_start, timeStamp_end, fileSize, protocolType);
                    break;
            }
        }
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
    
    void downloadWebFile(String urlStr) throws Exception{
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
        FileOutputStream fos = new FileOutputStream("C:\\Users\\inrp10181\\Documents\\JavaApplication1\\target\\"+getWebFileName(urlStr));
        fos.write(response);
        fos.close();
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
    
    void downloadFiles(String url, String file) throws IOException{
        target = new File("C:\\Users\\inrp10181\\Documents\\JavaApplication1\\target\\" + file);
        source = new File(url+"\\"+file);
        Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}
