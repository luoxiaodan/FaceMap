package com.stip.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.facepp.error.FaceppParseException;
import com.stip.dbase.Dbase;
import com.stip.face.Face;

public class DbaseServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)  
    throws ServletException, IOException {  
        
       String method=request.getParameter("method");  
       System.out.println("post success");
       try {
       if(method.equals("findPerson")){//find destination according to people's name
    	   String name=request.getParameter("name"); 
    	  
			String destination=Dbase.findPerson(name);
		////return destination
       }
       if(method.equals("updatePerson")){//update people's destination
    	   String name=request.getParameter("name"); 
    	   String destination=request.getParameter("destination"); 
    	   Dbase.updatePerson(name,destination);//update
       }
       } catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
 
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)  
    throws ServletException, IOException {  
          doPost(request,response);  
}  

}
