package com.flk.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Path("proxy")
public class ProxyEntryPoint {
	private final int ARBITARY_SIZE = 10048;
	
	public static void main(String[] args){
		
		System.out.println(ProxyEntryPoint.class.getCanonicalName());
	}
	
	
	@GET
	public void Hello(@Context HttpServletResponse response) throws IOException{
		//return "Hello ^o^";
		
		InputStream in = ProxyEntryPoint.class.getClassLoader().getResourceAsStream("files/text.txt");
		OutputStream out = response.getOutputStream();
	
		response.setStatus(200);
		response.setContentType("text/plain");
		
		byte[] buffer = new byte[ARBITARY_SIZE];
		int numBytesRead;
		while ((numBytesRead = in.read(buffer)) > 0) {
			out.write(buffer, 0, numBytesRead);
		}
		
		response.flushBuffer();
	}
	
	@POST
	public void callProxy(@Context HttpServletResponse response) throws IOException{
		
		URL url = new URL("http://localhost:8080/ch/rest/proxy");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setInstanceFollowRedirects(false);
		

		response.setStatus(con.getResponseCode());
		response.setContentType(con.getContentType());
		
		InputStream in = con.getInputStream();
		OutputStream out = response.getOutputStream();
		byte[] buffer = new byte[ARBITARY_SIZE];
		int numBytesRead;
		while ((numBytesRead = in.read(buffer)) > 0) {
			out.write(buffer, 0, numBytesRead);
		}
		
		//response.flushBuffer();
		/*con.disconnect();
		in.close();*/
		out.close();
	}
}
