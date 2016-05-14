com.stip.dbase

Connection getConn()

int addPerson(String desitination)

String updatePerson(String name,String desitination)

String findPerson(String name)


com.stip.face

int detection(String url)

void putFace(String url,String name)

String compareFace(String url)

Servlet接口

DbaseServlet

request.getParameter("method"); //the name od Dbase method

method:updatePerson,findPerson

request.getParameter("name"); //people's name
 
request.getParameter("destination"); //people's destination


FaceServlet

request.getPararment("method");//the name of Face method

method:addPerson,comparePerson

request.getParameter("name"); //people's name
 
request.getParameter("destination"); //people's destination

request.getParameter("image");//the url of people's face  


注：

com.stip.face   Face.path为电脑默认下载路径，需要修改

com.stip.dbase  Dbase数据库相关配置需要修改

