package configuration;

import org.junit.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class FootballAPIConfig {

	public static RequestSpecification footballRequest;
	public static ResponseSpecification footballResponse;
	
	@BeforeClass
	public static void footballConfiguration() {
		
		footballRequest = new RequestSpecBuilder()
				.setBaseUri("https://api.football-data.org")
				.setBasePath("/v2/")
				.addHeader("X-Auth-Token", "50760d1bafd74c5487f091059eeefe13")
				.addHeader("X-Response-Control", "minified")
				.addFilter(new RequestLoggingFilter())
				.addFilter(new ResponseLoggingFilter())
				.build();
		
		footballResponse = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.build();
		
		RestAssured.requestSpecification = footballRequest;
		RestAssured.responseSpecification = footballResponse;
	}
}
