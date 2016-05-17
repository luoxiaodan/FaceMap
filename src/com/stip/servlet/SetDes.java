package com.stip.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/setdes")
public class SetDes extends HttpServlet{
	
    private static final long serialVersionUID = 1L;
   
    public SetDes() {
        super();
        // TODO Auto-generated constructor stub
    }

	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
			    throws ServletException, IOException {
			        //Process the request in method processRequest
			    doPost(request, response);
	 }
			 
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
			    throws ServletException, IOException {
			        //Process the request in method processRequest
    	 String des="";
    	 des=request.getParameter("des");
    	 System.out.println(des);
     }
	
}