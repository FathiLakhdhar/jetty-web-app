package com.flk.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;


@ApplicationPath("rest")
public class RestApplication extends ResourceConfig
{
	public RestApplication()
	{
		// register with Jersey (assumes all Jersey resource classes are in this package or below)
		System.out.print("*** Load Jersey resource classes: ");
		System.out.println(this.getClass().getPackage().getName());
		
		packages(this.getClass().getPackage().getName());

		// activate Jackson-based JSON support
		register(JacksonFeature.class);
	}
}