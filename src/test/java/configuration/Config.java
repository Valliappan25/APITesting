package configuration;

import org.junit.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Config {
	
	@BeforeClass
	public static void myConfig()
	{
		//RestAssured.proxy("localhost", 8080);
		RestAssured.baseURI="http://localhost:8080";
		RestAssured.basePath = "/app/";
		RestAssured.port = 8080;
		
		RequestSpecification request = new RequestSpecBuilder()
				.addHeader("Content-Type", "application/json")
				.addHeader("Accept", "application/json")
				.build();
		
		RestAssured.requestSpecification = request;
		
		ResponseSpecification response = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.build();
		
		RestAssured.responseSpecification = response;
	}

}
