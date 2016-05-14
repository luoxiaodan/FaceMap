package com.stip.dbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dbase {
	
	
	public static Connection getConn() throws ClassNotFoundException, SQLException{

		String url="jdbc:mysql://localhost:3306/sitp";
		String username="root";
		String password="tongji";
		 
		 Class.forName("com.mysql.jdbc.Driver");
         String surl =  url+ "?useUnicode=true&characterEncoding=utf-8";
         Connection conn = DriverManager.getConnection(surl,username,password);
		
		
		return conn;
	}
	
	public static int addPerson(String desitination) throws ClassNotFoundException, SQLException{
		
		Connection conn=getConn();
		Statement state =conn.createStatement();
		String sql="insert into information(destination) values('"+desitination+"')";
		
		state.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);    
        ResultSet rs = state.getGeneratedKeys();    
        int id=-1;
        if(rs.first()){
        	id=rs.getInt(1);
        }
        return id;
	}
	
	
	public static void updatePerson(String name,String des) throws ClassNotFoundException, SQLException{
		
		Connection conn=getConn();
		Statement state =conn.createStatement();
		int id=Integer.parseInt(name);
		String sql="update information set destination='"+des+"' where id="+id;
		
		state.executeUpdate(sql);
		
	}
	
public static String findPerson(String name) throws ClassNotFoundException, SQLException{
		
		Connection conn=getConn();
		Statement state =conn.createStatement();
		int id=Integer.parseInt(name);
		String sql="select destination from information where id ="+id;
		
		
		ResultSet re=state.executeQuery(sql);
		String destination="";
		if(re.first())
	     destination=re.getString("destination");
		
		return destination;
	}

public static void main(String[] args) throws ClassNotFoundException, SQLException{
	
	System.out.println(Dbase.addPerson("Í¼Êé¹Ý"));
	//System.out.println(Dbase.findPerson("1"));
	//Dbase.updatePerson("1","ÈýºÃÎë");
}

}
