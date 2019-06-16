package com.flk.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.flk.model.Product;

public class ProductEntryPoint {
	
	static List<Product> products;
	
	ProductEntryPoint() {
		if (products == null){
			products = new ArrayList<Product>();
			products.add(new Product("Product 1", "Lorem ipsum dolor sit amet", 99));
			products.add(new Product("Product 2", "Lorem ipsum dolor sit amet", 55));
		}
	}
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProducts(){
		return products;
	}
	
	@GET
	@Path("{index}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product getProduct(@PathParam("index") int index){
		return products.get(index);
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> addProducts(Product product){
		products.add(product);
		return products;
	}
	
	
	@PUT
	@Path("{index}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product updateProduct(@PathParam("index") int index, Product product){
		products.set(index, product);
		return products.get(index);
	}
	
}
