<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="photo.js" language="javascript"></script>
 <script src="jquery-1.5.2.js" language="javascript"></script>
    <script src="..js/jquery.js"></script>
    <script src="..js/bootstrap.min.js"></script>
    <script src="..js/main.js"></script>
<title>Insert title here</title>
 <!-- Bootstrap Core CSS -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../css/main.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container">
   <div class="row">
      <div class="col-lg-7 get-vedio">
         
        <video id="video" width="500" height="400" autoplay></video>
 
       

          
          <div class="list-inline" id="takephoto-group">
                        &nbsp&nbsp&nbsp&nbsp
                        <button id="snap" class="btn btn-default btn-lg button-name">开始拍照</button>
                        &nbsp&nbsp&nbsp&nbsp
                        <button id="snapconfirm" class="btn btn-default btn-lg button-name">确认</button>
                        &nbsp&nbsp&nbsp&nbsp
                        <button id="snapagain" class="btn btn-default btn-lg button-name">重新拍照</button>
                    
          </div>
           <canvas id="canvas" width="500" height="400"></canvas>
       
       </div>
     <div class="col-lg-4 get-destination">
        <div  class="form-group class-inline">
            <h5 id="photopage-des">destination:</h5> <input type="text" id="destination" type="text" class="form-control" placeholder="Please Input Destination"/>
            <input type="submit" id="photopage-submit" class="btn btn-default btn-lg button-name" onfocus="addPerson()" value="Submit" />
           <script type="text/javascript">
          function addPerson(){
	 
	   var destination = document.getElementById("destination").value;
	   var canvas=document.getElementById("canvas");
	   var context = canvas.getContext("2d");
    var image = context.getImageData(0, 0, 320, 240);
    var dataURL = canvas.toDataURL("image/png");
    var _fixType = function(type) {
    type = type.toLowerCase().replace(/jpg/i, 'jpeg');
    var r = type.match(/png|jpeg|bmp|gif/)[0];
    return 'image/' + r;
   };
   

  dataURL = dataURL.replace(_fixType("png"),'image/octet-stream');
  var saveFile = function(data, filename){
    var save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a');
    save_link.href = data;
    save_link.download = filename;
   
    var event = document.createEvent('MouseEvents');
    event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
    save_link.dispatchEvent(event);
};
  
     var filename = "sitp.png";
  
    saveFile(dataURL,filename);

    $.ajax({  
	     type:"POST",
	     dataType:"text",
	     url: "/sitp/servlet/FaceServlet?image="+filename+"&destination="+destination+"&method=addPerson"
      	  
    });
    
} 


    
      
     
   </script>
  </div>
</div>
</div>
    </div>
</body>
</html>