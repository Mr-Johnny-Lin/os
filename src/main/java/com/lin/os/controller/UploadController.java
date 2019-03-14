package com.lin.os.controller;

import com.lin.os.data.TestFile;
import com.lin.os.service.DataProcess;
import com.lin.os.util.FileUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller

public class UploadController {


    private static ServletContext servletContext;

    @RequestMapping("/uploadTestData")
    public ModelAndView upload(HttpServletRequest request, TestFile data)throws IllegalStateException, IOException {

        servletContext=request.getServletContext();

        if (data.getData()!=null){
            ApplicationContext context= WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
            DataProcess dataProcess = (DataProcess) context.getBean("DataProcess");
            dataProcess.setTest(data);
            return dataProcess.getView();
        }
        else{
            ModelAndView modelAndView = new ModelAndView("upload");
            return  modelAndView;
        }

    }
    @RequestMapping("/selectTestData")
    public ModelAndView select(HttpServletRequest request,String filename) throws IOException {
        servletContext=request.getServletContext();
        if (filename!=null){
            ApplicationContext context= WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
            DataProcess dataProcess = (DataProcess) context.getBean("DataProcess");
            dataProcess.setTest(filename);
            return dataProcess.getView();
        }
        else{
            String s[]=null;
            try {
               s = FileUtil.getSelectfile(servletContext.getRealPath("/testdata"));

            }
           catch (Exception e){
                e.printStackTrace();

           }
            String s1="";
            if (s!=null)
            for (String s2:s){
                s1+="<a href=\"/selectTestData?filename="+s2+"\">"+s2+"</a></br>";
            }

            ModelAndView modelAndView = new ModelAndView("ShowSelection");
            modelAndView.addObject("message", s1);
            return modelAndView;
        }

    }

    public static ServletContext getServletContext() {
        return servletContext;
    }
}
