package com.flk.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.flk.model.Product;

public class ProductEntryPoint {
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProducts(){
		//String json = "[{\"name\": \"Product 1\", \"detail\": \"Lorem ipsum dolor sit amet\", \"price\": \"99\"},{\t\"name\": \"Product 2\",\"detail\": \"Lorem ipsum dolor sit amet\",\"price\": \"99\"}]";
		
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("Product 1", "Lorem ipsum dolor sit amet", 99));
		products.add(new Product("Product 2", "Lorem ipsum dolor sit amet", 55));
		
		return products;
	}
}
