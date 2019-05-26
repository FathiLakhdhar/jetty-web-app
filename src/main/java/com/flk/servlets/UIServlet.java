package com.flk.servlets;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class UIServlet extends HttpServlet {
	private final int ARBITARY_SIZE = 1048;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if ("/".equals(request.getPathInfo()) || "/index.html".equals(request.getPathInfo())) {
			try {
				response.setContentType("text/html");
				InputStream in = getServletContext().getResourceAsStream(
						getInitParameter("distFolder") + "/index.html");
				
				//OutputStream out = response.getOutputStream();
				
				String content = IOUtils.toString(in);
				String randomNonce = (String) request.getAttribute(CSPFilter.REQUEST_HEADER__CSP_RANDOM_NONCE);

				if(randomNonce!=null) {
					//add nonce attr to inline script
					content = content.replaceAll("nonce=\"\"", "nonce=\""+randomNonce+"\"");
				}
				
				
				response.getWriter().write(content);
				
				//IOUtils.copy(in, response.getOutputStream());
				
			} finally {
				response.flushBuffer();
			}
		} else {
			
			
			
			
			InputStream in = getServletContext().getResourceAsStream(getInitParameter("distFolder") + request.getPathInfo());
			/*URL url = getServletContext().getResource(
					getInitParameter("distFolder") + request.getPathInfo());
			if (url != null) {
				File file = new File(url.getFile());
				file.exists();
				response.setContentType("application/javascript");
				response.getWriter().println(file.exists());
				IOUtils.copy(file., response.getOutputStream());
			}*/
			response.setContentType("application/javascript");
			IOUtils.copy(in, response.getOutputStream());
			
			response.flushBuffer();

		}

	}

}
