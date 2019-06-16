package com.flk.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

public class ProxyEntryPoint {
	private final int ARBITARY_SIZE = 10048;
	
	public static void main(String[] args){
		
		System.out.println(ProxyEntryPoint.class.getCanonicalName());
	}
	
	@GET
	@Path("{path: [A-Za-z0-9-/.]*}")
	public void doGet(
			@PathParam("path") String path, 
			@QueryParam("host") String host,
			@Context HttpServletRequest httpServletRequest,
		    @Context HttpServletResponse httpServletResponse
			) throws IOException {
		writeResponse(httpServletRequest, httpServletResponse, host, path, "GET", null);
	}
	
	@POST
	@Path("{path: [A-Za-z0-9-/.]*}")
	public void doPost(
			@PathParam("path") String path, 
			String body,
			@QueryParam("host") String host,
			@Context HttpServletRequest httpServletRequest,
		    @Context HttpServletResponse httpServletResponse
			) throws IOException {
		writeResponse(httpServletRequest, httpServletResponse,host, path, "POST", body);
	}
	
	@PUT
	@Path("{path: [A-Za-z0-9-/.]*}")
	public void doPut(
			@PathParam("path") String path, 
			String body,
			@QueryParam("host") String host,
			@Context HttpServletRequest httpServletRequest,
		    @Context HttpServletResponse httpServletResponse
			) throws IOException {
		writeResponse(httpServletRequest, httpServletResponse,host, path, "PUT", body);
	}
	
	
	
	private void writeResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String host, String path, String method, String body) throws IOException{
				
		//TODO validate host : host must be url 
		if (!host.endsWith("/")) host+="/";
        URL url = new URL(host+path);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod(method);
		con.setRequestProperty("Content-Type", httpServletRequest.getContentType());
		con.setInstanceFollowRedirects(false);
		
		if (("POST".equals(method) || "PUT".equals(method)) && body !=null) {
			con.setDoOutput(true);
			try (OutputStream os = con.getOutputStream()) {
			    byte[] input = body.getBytes("utf-8");
			    os.write(input, 0, input.length);           
			}
		}
		
		
		httpServletResponse.setStatus(con.getResponseCode());
		httpServletResponse.setContentType(con.getContentType());
		
		for (Entry<String, List<String>> entry: con.getHeaderFields().entrySet()) {
			if (entry.getKey()==null) continue;
			for (String v: entry.getValue()) {
				httpServletResponse.setHeader(entry.getKey(), v);
			}
		}
		
		InputStream in = con.getInputStream();
		OutputStream out = httpServletResponse.getOutputStream();
		
		byte[] buffer = new byte[ARBITARY_SIZE];
		int numBytesRead;
		while ((numBytesRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, numBytesRead);
			out.flush();
		}
		
		con.disconnect();
		in.close();
		out.close();
	}
	
	
/*	
	@GET
	public void Hello(@Context HttpServletResponse response) throws ServletException{
		//return "Hello ^o^";
		try {
			InputStream in = ProxyEntryPoint.class.getClassLoader().getResourceAsStream("files/text.txt");
			OutputStream out = response.getOutputStream();
		
			response.setStatus(200);
			response.setContentType("text/plain");
			
			byte[] buffer = new byte[ARBITARY_SIZE];
			int numBytesRead;
		
			while ((numBytesRead = in.read(buffer)) > 0) {
					out.write(buffer, 0, numBytesRead);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ServletException(e);
		}finally{
			try {
				response.flushBuffer();
			} catch (IOException e) {
				throw new ServletException(e);
			}
		}
		
		
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
		//con.disconnect();
		//in.close();
		out.close();
	}
*/
}
