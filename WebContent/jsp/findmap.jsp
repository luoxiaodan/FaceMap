<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="photo.js" language="javascript"></script>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=c7dd742fd48984414b8b02909d9eac60&plugin=AMap.Walking"></script>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>

 <script src="jquery-1.5.2.js" language="javascript"></script>
     <script src="../js/jquery.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/main.js"></script>
<title>Insert title here</title>
<!-- Bootstrap Core CSS -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../css/main.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <!-- <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"> -->
    <link href="http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container">
  <div class="row">
 <div class="col-lg-7">
	<div class="face-recognize">
		<video id="video" width="500" height="400" autoplay></video>
		<canvas id="canvas" width="500" height="400"></canvas>
	</div>

 </div>
<div class="col-lg-4">
	<div class="rode-result">
		<button id="snap" onclick="comparePerson()" class="btn btn-default btn-lg button-name">ok</button>
	</div>
	<div id="map"></div>
	<div id="panel"></div>
	
</div>
</div>
<div class="form-group class-inline">
  <button  class="btn btn-default btn-lg button-name">change destination</button>
<h5>destination:</h5> 
<input type="text" id="destination" class="form-control" placeholder="Please Input Destination"/>


    </div>
    </div>
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

 var filename = "sitp.png";

saveFile(dataURL,filename);

$.ajax({  
	     type:"POST",
	    dataType:"text",
	    asyn:false,
	     url: "/sitp/servlet/FaceServlet?image="+filename+"&method=comparePerson"
	  });

loadEnd();  
}

var map = new AMap.Map("map", {
    resizeEnable: true,
    center: [121.2152770000,31.2860990000],//地图中心点
    zoom: 13 //地图显示的缩放级别
});

function loadEnd()
{
	map.clearMap();
xmlhttp=null;
if (window.XMLHttpRequest)
  {// code for Firefox, Opera, IE7, etc.
  xmlhttp=new XMLHttpRequest();
  }
else if (window.ActiveXObject)
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
if (xmlhttp!=null)
  {
	var url="/sitp/servlet/GetDes?";
  xmlhttp.onreadystatechange=getDes;
  xmlhttp.open("GET",url,false);
  xmlhttp.send(null);
  }
else
  {
  alert("Your browser does not support XMLHTTP.");
  }
}
function getDes()
{
if (xmlhttp.readyState==4)
  {// 4 = "loaded"
  if (xmlhttp.status==200)
    {// 200 = "OK"
//   document.getElementById('T1').innerHTML=xmlhttp.responseText;
    
    str=xmlhttp.responseText;
    alert(str);
	var walking = new AMap.Walking({
        map: map,
        panel: "panel"
    }); 
    //根据起终点坐标规划步行路线
    walking.search([ {keyword: '同济大学嘉定校区10号楼'},
                     {keyword: str}]);
    //步行导航
  
    }
  else
    {
	  alert("aaa")
    alert("Problem retrieving data:" + xmlhttp.statusText);
    }
  }
}

function routeplan(){
	var walking = new AMap.Walking({
        map: map,
        panel: "panel"
    }); 
    //根据起终点坐标规划步行路线
    walking.search([ {keyword: '同济大学嘉定校区图书馆'},
                     {keyword: '同济大学嘉定校区10号楼'}]);
}

</script>   
</body>
</html>