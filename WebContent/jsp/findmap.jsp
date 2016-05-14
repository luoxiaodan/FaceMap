<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="photo.js" language="javascript"></script>
 <script src="jquery-1.5.2.js" language="javascript"></script>
    
<title>Insert title here</title>
</head>
<body>
<div float:left>
<div>
 <section class="left">
<video id="video" width="320" height="240" autoplay></video>
 
<canvas id="canvas" width="320" height="240"></canvas>
 </section>
</div>
<div>
<button id="snap" onfocus="comparePerson()" class="sexyButton">ok</button>
<script type="text/javascript">
function comparePerson(){
	 
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

 var filename = "sitp_"+ (new Date()).getTime() +".png";

saveFile(dataURL,filename);

$.ajax({  
	     type:"POST",
	    dataType:"text",
	     url: "/sitp/servlet/FaceServlet?image="+filename+"&method=comparePerson"
	  });

	  
}

</script>


</div>

<div>
<a>destination:</a> <input type="text" id="destination" />

</div>
    </div>
</body>
</html>