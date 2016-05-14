package com.stip.servlet;

import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.facepp.error.FaceppParseException;
import com.stip.dbase.Dbase;
import com.stip.face.Face;


public class FaceServlet extends HttpServlet{
	
    public FaceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
		public void doPost(HttpServletRequest request, HttpServletResponse response)  
	    throws ServletException{  
			
			System.out.println("post success");
			String method=request.getParameter("method"); 
			System.out.println(method);
			if(method.equals("addPerson")){//add a new person face
				try {
					addPerson(request,response);
				} catch (FaceppParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		    	
				
			}
			if(method.equals("comparePerson")){
				try {
					try {
						comparePerson(request,response);//find person
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (FaceppParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
				
	       }
		 public void comparePerson(HttpServletRequest request, HttpServletResponse response) throws FaceppParseException, JSONException, InterruptedException{
			 String image = request.getParameter("image"); 
		    	//String destination=request.getParameter("destination"); 
			 System.out.println("compare "+image);
			 Thread.sleep(1000);
		    	int num=Face.detection(image);
				if(num!=0){
					String res=Face.compareFace(image);//return people's name---res
					System.out.println(res);
				}else{
					System.out.println("no face");////////messagebox
				}
		 }
	    public void addPerson(HttpServletRequest request, HttpServletResponse response) throws FaceppParseException, JSONException, ClassNotFoundException, SQLException{
	    	String image = request.getParameter("image"); 
	    	System.out.println("add "+image);
	    	String destination=request.getParameter("destination"); 
	    
	    	int num= Face.detection(image);
			
			if(num!=0){
				int name=Dbase.addPerson(destination);
				Face.putFace(image,String.valueOf(name));//add people's information include name and destination
			}else{
				System.out.println("no face");////////messagebox
			}
	   }
		
		public void doGet(HttpServletRequest request, HttpServletResponse response)  
	    throws ServletException {  
	          doPost(request,response);  
	}  
	}



