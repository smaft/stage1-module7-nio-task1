package com.epam.mjc.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        String text = getStringFromFile(file);
        return getProfileInfoFromText(text);
    }

    private String getStringFromFile(File file){
        
        StringBuilder text = new StringBuilder();
        try (RandomAccessFile aFile = new RandomAccessFile(file.getPath(), "r");
            FileChannel inChannel = aFile.getChannel();){
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                while (inChannel.read(buffer) > 0) {
                    buffer.flip();
                    for(int i = 0; i < buffer.limit(); i++){
                        text.append((char) buffer.getChar());
                    }
                    buffer.clear();
                    
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }
    public static Profile getProfileInfoFromText(String text){
        Profile profile = new Profile();
        String[] data = text.split("[\\u0020\\n\\r]");   
        for (int i = 0; i<data.length; i++){
                if(data[i].isEmpty())
                    continue;
                if(data[i].toLowerCase().contains("name")) {
                    profile.setName(data[i+1]);
                }else if(data[i].toLowerCase().contains("age")){
                    profile.setAge(Integer.valueOf(data[i+1]));
                }else if(data[i].toLowerCase().contains("email")){
                    profile.setEmail(data[i+1]);
                }else if(data[i].toLowerCase().contains("phone")){
                    profile.setPhone(Long.valueOf(data[i+1]));
                }
            }
        
        return profile;
    }
    
    
}

    
 

