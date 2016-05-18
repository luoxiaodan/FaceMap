package com.stip.servlet;

import java.io.File;
import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.json.JSONException;

import com.facepp.error.FaceppParseException;
import com.stip.dbase.Dbase;
import com.stip.face.Face;


public class FaceServlet extends HttpServlet{
	public static String path="C:\\Users\\lenovo\\Downloads\\";
	public static String destination="同济大学嘉定校区图书馆";
    public static String getDestination() {
		return destination;
	}
    public static FaceServlet faceservlet=null;
	public static void setDestination(String destination) {
		FaceServlet.destination = destination;
	}
	public static FaceServlet shareSerlet(){
		if(faceservlet==null){
			return new FaceServlet();
		}else{
			return faceservlet;
		}
		
		
	}
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
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
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
		 public void comparePerson(HttpServletRequest request, HttpServletResponse response) throws FaceppParseException, JSONException, InterruptedException, ClassNotFoundException, SQLException{
		
			 
			 String image = request.getParameter("image"); 
		    	//String destination=request.getParameter("destination"); 
			 System.out.println("compare "+image);
			
			 File e=new File(path+image);
		        if(e.exists()){
		    	int num=Face.detection(image);
				if(num!=0){
					String res=Face.compareFace(image);//return people's name---res
					String des=Dbase.findPerson(res);
				FaceServlet.shareSerlet().destination=des;
					System.out.println(res);
				}else{
					System.out.println("no face");////////messagebox
					 JOptionPane.showMessageDialog(null,"cannot detect face");
	  				 
					File d=new File(path+image);
					d.delete();
				}
		        }else{
		        	 System.out.println("no exist");
		        }
		 }
	    public void addPerson(HttpServletRequest request, HttpServletResponse response) throws FaceppParseException, JSONException, ClassNotFoundException, SQLException{
	    	
	    	String image = request.getParameter("image"); 
	    	System.out.println("add "+image);
	    	String destination=request.getParameter("destination"); 
	        File e=new File(path+image);
	        if(e.exists()){
	    	
	    	int num= Face.detection(image);
			
			if(num!=0){
				int name=Dbase.addPerson(destination);
				Face.putFace(image,String.valueOf(name));//add people's information include name and destination
			}else{
				System.out.println("no face");////////messagebox
				 JOptionPane.showMessageDialog(null,"cannot detect face");
  				 
			}
			
			
			File d=new File(path+image);
			d.delete();
	        }else{
	        	System.out.println("no ");
	        }
	   }
		
		public void doGet(HttpServletRequest request, HttpServletResponse response)  
	    throws ServletException {  
	          doPost(request,response);  
	}  
	}



