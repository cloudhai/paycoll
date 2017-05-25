package com.hai.web.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Created by cloud on 2017/4/12.
 */
public class FileUtils {
    private static int BUFSIZE = 100;

    public static String readFileInLine(String path){
        MappedByteBuffer mbb = null;
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(path,"r");
            FileChannel fc = raf.getChannel();
            mbb = fc.map(FileChannel.MapMode.READ_ONLY,0,raf.length());
            String lineSeparator = System.getProperty("line.separator", "\n");
            String content = null;
            String[] lines = null;
            byte[] buf = new byte[BUFSIZE];
            for(int offset = 0; offset < mbb.capacity();offset += BUFSIZE){
                if(offset + BUFSIZE <= mbb.capacity()){
                    mbb.get(buf);
                }else{
                    mbb.get(buf,offset,mbb.capacity());
                }
                content = new String(buf);
                lines = content.split(lineSeparator);
                for(String line:lines){
                    System.out.println(line);
                }
            }
            return "";
        } catch (FileNotFoundException e) {
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        finally {
            if(mbb != null){
                clean(mbb);
            }
            if(raf != null){
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        readFileInLine("d:\\user.txt");
    }





    public static void clean(final Object buffer){
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                try {
                    Method getCleanerMethod = buffer.getClass().getMethod("cleaner",new Class[0]);
                    getCleanerMethod.setAccessible(true);
                    sun.misc.Cleaner cleaner =(sun.misc.Cleaner)getCleanerMethod.invoke(buffer,new Object[0]);
                    cleaner.clean();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                return null;}});
    }
}
