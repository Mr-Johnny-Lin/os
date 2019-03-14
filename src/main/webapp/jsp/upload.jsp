<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" isELIgnored="false"%>
  
<form action="uploadTestData" method="post" enctype="multipart/form-data">
  选择测试数据:<input type="file" name="text" accept="text/*" /> <br>
  <input type="submit" value="上传">
</form>
<a href="/">返回</a>