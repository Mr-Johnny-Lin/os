package com.lin.os.util;

import com.lin.os.controller.UploadController;
import com.lin.os.data.TestData;
import com.lin.os.data.TestFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtil {
    public static File creatfile(TestFile data) throws IOException {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String filename = dateFormat.format(date)+data.getType();
        File file = new File(UploadController.getServletContext().getRealPath("/testdata"),filename);
        file.getParentFile().mkdirs();
        data.getData().transferTo(file);
        return file;
    }

    public static List readfile(Object data)throws IOException{
        ArrayList<TestData> arrayList=new ArrayList<>();
        String s[];
        byte[] b;
        if (data instanceof  TestFile){
             b=((TestFile)data).getData().getBytes();
        }
        else{
            FileInputStream in = new FileInputStream((File)data);
            b =new byte[in.available()];
            in.read(b);
            in.close();
        }
        s = new String(b).split("\\n");
        for(String s1:s){
            String s2[] = s1.split("\\s");
            if (s2.length==4){

                try{
                    arrayList.add(new TestData(s2[0], s2[1], Integer.valueOf(s2[2]),Integer.valueOf(s2[3]) ));
                }catch (Exception e){
                    return null;



                }
            }
        }


        return arrayList;
    }
    public static String readfile1(File file)throws IOException{

        FileInputStream in = new FileInputStream(file);
        byte b[]=new byte[in.available()];
        in.read(b);
        in.close();
        String s = new String(b);
        s=s.replace("\n", "</br>");

        return s;
    }
    public static String[] getSelectfile(String path)throws IOException{
        String s[] = null;
        File file = new File(path);
        if (file.exists()&&file.isDirectory()){
            s=file.list();
        }
        return s;
    }


}

