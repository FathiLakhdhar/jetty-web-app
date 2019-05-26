package com.flk.servlets;

import javax.servlet.http.HttpServletRequest;

/**
 * https://gist.github.com/c0rp-aubakirov/a4349cbd187b33138969
 * 
 *
 */

public class ClientInfo {

	public void printClientInfo(HttpServletRequest request) {
		final String referer = getReferer(request);
		final String fullURL = getFullURL(request);
		final String clientIpAddr = getClientIpAddr(request);
		final String clientOS = getClientOS(request);
		final Browser clientBrowser = getClientBrowser(request);
		final String userAgent = getUserAgent(request);

		System.out.println("\n" + "User Agent \t" + userAgent + "\n"
				+ "Operating System\t" + clientOS + "\n" + "Browser Name\t"
				+ clientBrowser + "\n" + "IP Address\t" + clientIpAddr + "\n"
				+ "Full URL\t" + fullURL + "\n" + "Referrer\t" + referer);
	}

	public static String getReferer(HttpServletRequest request) {
		final String referer = request.getHeader("referer");
		return referer;
	}

	public static String getFullURL(HttpServletRequest request) {
		final StringBuffer requestURL = request.getRequestURL();
		final String queryString = request.getQueryString();

		final String result = queryString == null ? requestURL.toString()
				: requestURL.append('?').append(queryString).toString();

		return result;
	}

	// http://stackoverflow.com/a/18030465/1845894
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	// http://stackoverflow.com/a/18030465/1845894
	public static String getClientOS(HttpServletRequest request) {
		final String browserDetails = request.getHeader("User-Agent");

		// =================OS=======================
		final String lowerCaseBrowser = browserDetails.toLowerCase();
		if (lowerCaseBrowser.contains("windows")) {
			return "Windows";
		} else if (lowerCaseBrowser.contains("mac")) {
			return "Mac";
		} else if (lowerCaseBrowser.contains("x11")) {
			return "Unix";
		} else if (lowerCaseBrowser.contains("android")) {
			return "Android";
		} else if (lowerCaseBrowser.contains("iphone")) {
			return "IPhone";
		} else {
			return "UnKnown, More-Info: " + browserDetails;
		}
	}

	// http://stackoverflow.com/a/18030465/1845894
	public static Browser getClientBrowser(HttpServletRequest request) {
		final String browserDetails = request.getHeader("User-Agent");
		final String user = browserDetails.toLowerCase();

		Browser browser = new Browser();

		// ===============Browser===========================
		if (user.contains("msie")) {
			String substring = browserDetails.substring(
					browserDetails.indexOf("MSIE")).split(";")[0];
			browser.setName(substring.split(" ")[0].replace("MSIE", "IE"));
			browser.setVersion(substring.split(" ")[1]);
		} else if (user.contains("safari") && user.contains("version")) {
			browser.setName((browserDetails.substring(browserDetails.indexOf("Safari")).split(" ")[0]).split("/")[0]);
			browser.setVersion((browserDetails.substring(
							browserDetails.indexOf("Version")).split(" ")[0])
							.split("/")[1]);
		} else if (user.contains("opr") || user.contains("opera")) {
			if (user.contains("opera")){
				
				browser.setName(
						(browserDetails.substring(
								browserDetails.indexOf("Opera")).split(" ")[0])
								.split("/")[0]
						);
				browser.setVersion((browserDetails.substring(browserDetails.indexOf("Version")).split(" ")[0]).split("/")[1]);
			}else if (user.contains("opr")){
				browser.setName(
						((browserDetails.substring(
								browserDetails.indexOf("OPR")).split(" ")[0]).split(
								"/")[0]).replace("OPR", "Opera")
						);
				browser.setVersion(
						((browserDetails.substring(
								browserDetails.indexOf("OPR")).split(" ")[0]).split(
								"/")[1])
						);
			}
		} else if (user.contains("chrome")) {
			
			browser.setName(
					(browserDetails.substring(
							browserDetails.indexOf("Chrome")).split(" ")[0]).split("/")[0]
					);
			browser.setVersion(
					(browserDetails.substring(
							browserDetails.indexOf("Chrome")).split(" ")[0]).split("/")[1]
					);
		} else if ((user.indexOf("mozilla/7.0") > -1)
				|| (user.indexOf("netscape6") != -1)
				|| (user.indexOf("mozilla/4.7") != -1)
				|| (user.indexOf("mozilla/4.78") != -1)
				|| (user.indexOf("mozilla/4.08") != -1)
				|| (user.indexOf("mozilla/3") != -1)) {
			// browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/",
			// "-");
			browser.setName("Netscape");
			browser.setVersion("?");

		} else if (user.contains("firefox")) {
			
			browser.setName(
					(browserDetails.substring(
							browserDetails.indexOf("Firefox")).split(" ")[0]).split(
							"/")[0]
					);
			browser.setVersion(
					(browserDetails.substring(
							browserDetails.indexOf("Firefox")).split(" ")[0]).split(
							"/")[1]
					);
		} else if (user.contains("rv")) {
			browser.setName("IE");
		} else {
			browser.setName("UnKnown, More-Info: " + browserDetails);
		}

		return browser;
	}

	public static String getUserAgent(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

}


class Browser{
	private String _name;
	private String _version;
	
	public Browser() {
		super();
	}
	
	public Browser(String name, String version) {
		super();
		this._name = name;
		this._version = version;
	}
	public String getName() {
		return _name;
	}
	public void setName(String _name) {
		this._name = _name;
	}
	public String getVersion() {
		return _version;
	}
	public void setVersion(String _version) {
		this._version = _version;
	}
	
	@Override
	public String toString() {
		return _name+"-"+_version;
	}
	
}
