package com.lin.os.service;

import com.lin.os.controller.UploadController;
import com.lin.os.corecode.Threadpool;
import com.lin.os.data.TestFile;
import com.lin.os.data.TestResult;
import com.lin.os.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service("DataProcess")

public class DataProcess {

   private List result;
   private List test;
   private String filecontext;

    public void setTest( String testFilename) throws IOException {
        File file = new File(UploadController.getServletContext().getRealPath("/testdata")+"/"+testFilename);
        test = FileUtil.readfile(file);
        filecontext=FileUtil.readfile1(file);
        Threadpool pool = new Threadpool(200);
        result = pool.excute(test);


    }
    public void setTest( TestFile testFile) throws IOException {
        test = FileUtil.readfile( testFile);
        if (test!=null&&!test.isEmpty()){
            File file = FileUtil.creatfile(testFile);
            filecontext=FileUtil.readfile1(file);
            Threadpool pool = new Threadpool(200);
            result = pool.excute(test);
        }




    }
    public ModelAndView getView(){
        String message = "";
        Iterator<TestResult> i;
        try{
             i= result.iterator();
             while(i.hasNext()){
                TestResult testResult = i.next();
                if(testResult.getMessage().endsWith("creat")){
                    if(testResult.getMessage().startsWith("R")){
                        message+="["+testResult.getTime()+"] 线程"+testResult.getId()+"：读操作创建成功</br>";
                    }
                    else{
                        message+="["+testResult.getTime()+"] 线程"+testResult.getId()+"：写操作创建成功</br>";
                    }
                }
                else if(testResult.getMessage().endsWith("apply")){
                    if(testResult.getMessage().startsWith("R")){
                        message+="["+testResult.getTime()+"] 线程"+testResult.getId()+"：发出读操作申请</br>";
                    }
                    else{
                        message+="["+testResult.getTime()+"] 线程"+testResult.getId()+"：发出写操作申请</br>";
                    }
                }
                else if(testResult.getMessage().endsWith("start")){
                    if(testResult.getMessage().startsWith("R")){
                        message+="["+testResult.getTime()+"] 线程"+testResult.getId()+"：开始读操作</br>";
                    }
                    else{
                        message+="["+testResult.getTime()+"] 线程"+testResult.getId()+"：开始写操作</br>";
                    }
                }
                else if(testResult.getMessage().endsWith("finish")){
                    if(testResult.getMessage().startsWith("R")){
                        message+="["+testResult.getTime()+"] 线程"+testResult.getId()+"：结束读操作</br>";
                    }
                    else{
                        message+="["+testResult.getTime()+"] 线程"+testResult.getId()+"：结束写操作</br>";
                    }
                }
            }
            ModelAndView modelAndView = new ModelAndView("ShowResult");
            modelAndView.addObject("context", filecontext);
            modelAndView.addObject("message", message);
            return modelAndView;

        }catch (NullPointerException e){
            return  new ModelAndView("ShowResult");
        }
        finally {
            filecontext="";
            result=null;
        }

    }
}
