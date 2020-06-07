一个简单的Spark SQL查询分析器

Browser-Server 前后端分离式  

前端(sqleditor/)：vue2 + webpack  
后台请求集成于：src/components/Service.js（基于vue-resource）  
编辑器的主界面：src/components/Editor.vue  
Sql输入框使用了vue2-ace-editor，可以提供语法高亮、关键字提示等功能。  
页面布局使用ElementUI构建。  
Table.vue为展示查询结果的组件，被解耦成单独文件。  
  
  
后端(bigdate_work/)：tomcat + springMVC  
src/servlet/：接收前端请求  
src/Dao/：查询和建立连接使用的方法  