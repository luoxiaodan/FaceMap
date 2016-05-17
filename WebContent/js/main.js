$(document).ready(function(){
	$("#canvas").css("display","none");
	$("#photopage-submit").click(
        function(){
          //var text=$('#search').val();
          alert("Next pagepppp");
          var url="destination.html";
          window.location.href=url;
        }
    )
    $('#snap').click(
    	function(){
    		alert("ppp");
    		//$('#canvas').show();
    		$('#canvas').css("display","block");
    		//$('#video').css("display","none");
    		$('#video').hide();
    	}
    	)
    $('#snapconfirm').click(
      function(){
        alert("confirm");
         var canvans = document.getElementById("canvas"); 
// //获取浏览器页面的画布对象                         
        var imgData = canvans.toDataURL(); 
// //将图像转换为base64数据
        var base64Data = imgData.substr(22); 
        alert(base64Data);
      }
      )

    var photopage_input=$('#photopage-desinput').val();
    $("#snapagain").click(
        function(){
			$('#canvas').hide();
			//alert(photopage_input);
        }
    )

//    window.onload = function() {  
//            var snapconfirmButton = document.getElementById("snapconfirm");  
//            bindButtonEvent(snapconfirmButton, "click", saveAsLocalImage);  
//        }; 
    /*function saveAsLocalImage () {  
        var myCanvas = document.getElementById("canvas");  
        // here is the most important part because if you dont replace you will get a DOM 18 exception.  
        var image = myCanvas.toDataURL("image/png").replace("image/png", "image/octet-stream;Content-Disposition: attachment;filename=foobar.png");  
       // var image = myCanvas.toDataURL("image/png").replace("image/png", "image/octet-stream");  
            // $('#snapconfirm').attr({
            //   'download': 'myFilename.png',  /// set filename
            //   'href'    : image              /// set data-uri
            // }); 
        window.location.href=image; // it will save locally  
            } */

 /*   function bindButtonEvent(element, type, handler)  
            {  
              if(element.addEventListener) {  
                  element.addEventListener(type, handler, false);  
              } else {  
                  element.attachEvent('on'+type, handler);  
                  }  
            } */
    
    //alert(photopage_input);
   // $('#canvas').css('display','none');
   //$('#canvas').hide();
}
)