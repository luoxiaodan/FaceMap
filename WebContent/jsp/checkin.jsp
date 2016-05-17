<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="photo.js" language="javascript"></script>
 <script src="jquery-1.5.2.js" language="javascript"></script>
    <script src="../js/jquery.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/main.js"></script>
 <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=c7dd742fd48984414b8b02909d9eac60&plugin=AMap.Autocomplete,AMap.PlaceSearch,AMap.Walking"></script>
    
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
<title>Insert title here</title>
 <!-- Bootstrap Core CSS -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../css/main.css" rel="stylesheet">
    <!-- Custom Fonts -->
 <!--    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">--> 
    <link href="http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container">
   <div class="row">
      <div class="col-lg-7 get-vedio">
         
        <video id="video" width="500" height="400" autoplay></video>
      <!-- <div class="list-inline" id="takephoto-group">
                &nbsp&nbsp&nbsp&nbsp
                <button id="snap" class="btn btn-default btn-lg button-name">Take Photo</button>
                &nbsp&nbsp&nbsp&nbsp
                <button id="snapconfirm" class="btn btn-default btn-lg button-name">Confirm</button>
                &nbsp&nbsp&nbsp&nbsp
                <button id="snapagain" class="btn btn-default btn-lg button-name">Picture Again</button>
            </div>
 --> 
        <canvas id="canvas" width="500" height="400"></canvas>
       

        <div class="list-inline" id="takephoto-group">
          	<button id="snap"  class="btn btn-default btn-lg button-name">take a Photo</button>
        </div>
       </div>
     <div class="col-lg-4 get-destination">
        <div  class="form-group class-inline">
            <h5 id="photopage-des">destination:</h5> <input type="text" id="destination" type="text" class="form-control" placeholder="Please Input Destination"/>
            <input type="submit" id="photopage-des-submit" class="btn btn-default btn-lg button-name" onfocus="SetEnd()" value="确定目的地" />
        </div>
        
        <div id="maparea">
        <p>pppppp</p>
  
        </div>
     </div>
     
  </div>
</div>
 <!-- <a href="../jsp/findmap.jsp"> -->
 <a href="../jsp/findmap.jsp"><input type="submit"  class="btn btn-default btn-lg button-name" onfocus="addPerson()" value="Submit Page" /></a> 
    
  <script type="text/javascript">
  var map = new AMap.Map("maparea", {
	    resizeEnable: true,
	    center: [121.2152770000,31.2860990000],//地图中心点
	    zoom: 13 //地图显示的缩放级别
	});
  var autoOptions = {
	        input: "destination"
	    };
	    var auto = new AMap.Autocomplete(autoOptions);
	    var placeSearch = new AMap.PlaceSearch({
	        map: map
	    });  //构造地点查询类
	    AMap.event.addListener(auto, "select", select);//注册监听，当选中某条记录时会触发
	    function select(e) {
	        placeSearch.setCity(e.poi.adcode);
	        placeSearch.search(e.poi.name);  //关键字查询查询
	    }
	    
	    function ee(){
	    	//alert("fuck");
	    }
function SetEnd(){
	    	map.clearMap();
	    	var walking = new AMap.Walking({
	            map: map,
	            panel: "panel"
	        }); 
	        //根据起终点坐标规划步行路线
	        newEnd=document.getElementById('destination').value;
	        walking.search([ {keyword: '同济大学嘉定校区10号楼'},
	                         {keyword: newEnd}]);
	    
	    }

  
   function addPerson(){
	 
	   var destination = document.getElementById("destination").value;
	   //alert(destination);
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
	     asyn:false,
	     dataType:"text",
	     url: "/sitp/servlet/FaceServlet?image="+filename+"&destination="+destination+"&method=addPerson"
      	  
    });
    
} 
     
   </script>
</body>
</html>