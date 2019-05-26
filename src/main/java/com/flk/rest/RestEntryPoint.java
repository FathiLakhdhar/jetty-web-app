package com.flk.rest;

import javax.ws.rs.Path;

@Path("")
public class RestEntryPoint {

	
	@Path("products")
	public ProductEntryPoint productsEntryPoint(){
		return new ProductEntryPoint();
	}
	
	@Path("proxy")
	public ProxyEntryPoint proxyEntryPoint(){
		return new ProxyEntryPoint();
	}
}
