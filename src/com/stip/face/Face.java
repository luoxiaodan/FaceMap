package com.stip.face;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

public class Face {
	
	static String apikey="173bc712ef23dde31b62b12dd72645e6";
	static String apisecret="z33fmTgR2EDlcXvFJLSHw0rW56KxsDbp";
	static byte[] bytes;
	public static String path="C:\\Users\\dell\\Downloads\\";//your downloads
	
	public static int detection(String url) throws FaceppParseException, JSONException{
        HttpRequests httpRequests = new HttpRequests(apikey, apisecret, true, true);
		
		JSONObject  result = httpRequests.detectionDetect(new PostParameters().setImg(new File(path+url)));
		return result.getJSONArray("face").length();
	}
	public static void putFace(String url,String name) throws FaceppParseException, JSONException{
		HttpRequests httpRequests = new HttpRequests(apikey, apisecret, true, true);
		
		JSONObject  result = httpRequests.detectionDetect(new PostParameters().setImg(new File(path+url)));
		System.out.println(httpRequests.personCreate(new PostParameters().setPersonName(name)));
		System.out.println(httpRequests.personAddFace(new PostParameters().setPersonName(name).setFaceId(
		result.getJSONArray("face").getJSONObject(0).getString("face_id"))));
		 
	}
	
	public static 	String compareFace(String url) throws FaceppParseException, JSONException{
		HttpRequests httpRequests = new HttpRequests(apikey, apisecret, true, true);
		// HttpRequests httpRequests = new HttpRequests("159e732cb3df5c4dfa29fda42e7c96e3", "FGzcLGCK4LYheFzCYCid-SS4oIa4mFeQ", true, true);
		 
		float simila=0;String name="";
		JSONObject  result = httpRequests.infoGetPersonList();
		JSONObject newface= httpRequests.detectionDetect(new PostParameters().setImg(new File(path+url)));
		for(int i=0;i<result.getJSONArray("person").length();i++){
			String person=result.getJSONArray("person").getJSONObject(i).getString("person_name");
			String faceid=httpRequests.personGetInfo(new PostParameters().setPersonName(person))
			              .getJSONArray("face").getJSONObject(0).getString("face_id");
			
			
			JSONObject compare=httpRequests.recognitionCompare(new PostParameters().setFaceId1(
					newface.getJSONArray("face").getJSONObject(0).getString("face_id"))
					.setFaceId2(faceid));
		  if((float)compare.getInt("similarity")>simila){
			  simila=(float)compare.getInt("similarity");
			  name=person;
		  }
		  }
		return "----similar="+name;
		}
	
	public static void main(String[] args) throws FaceppParseException {
		try
		{
			String url="C:\\Users\\dell\\Downloads\\sitp_1463200022140.png";
			Face.putFace(url,"test");
			
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	

}
