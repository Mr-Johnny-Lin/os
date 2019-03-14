<%--
  Created by IntelliJ IDEA.
  User: 93410
  Date: 2018/10/26
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <script>
        function isEmpty(obj){
            if(typeof obj == "undefined" || obj == null || obj == ""){
                return true;
            }else{
                return false;
            }
        }
        if(isEmpty( ${requestScope.message})){
            alert("无法读取测试数据，可能输入数据格式不正确,无法进行测试！");
            window.location.href = "/";
        }
        else{

        }

    </script>

    <div style="font-size:20px;text-align:center;width:100%;height:100%">
        <div style="width:50%;height:95%;float: left;overflow-y: scroll">
            <h1>测试数据</h1>
            <div style="text-align: left;display: inline-block">
                ${requestScope.context}
            </div>

        </div>
        <div style="width:50%;height:95%;float: right;overflow-y: scroll ">
            <h1>测试结果</h1>
            <div style="text-align: left;display: inline-block">
                ${requestScope.message}
            </div>

        </div>
        <div style="font-size:30px;text-align: center;float: bottom">
            <a  href="/">返回</a>
        </div>

    </div>






</body>

</html>
