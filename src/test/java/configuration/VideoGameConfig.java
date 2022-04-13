package configuration;

import org.junit.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.Matchers.lessThan;

public class VideoGameConfig {

	public static RequestSpecification videoGameRequest;
	public static ResponseSpecification videoGameResponse;
	
	
	@BeforeClass
	public static void gameConfiguration()
	{
		videoGameRequest = new RequestSpecBuilder()
				.setBaseUri("http://localhost:8080")
				.setBasePath("/app/")
				.setPort(8080)
				.addHeader("Content-Type", "application/json")
				.addHeader("Accept", "application/json")
				.addFilter(new RequestLoggingFilter())
				.addFilter(new ResponseLoggingFilter())
				.build();
		
		videoGameResponse = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectResponseTime(lessThan(2000L))
				.build();
		
		RestAssured.requestSpecification = videoGameRequest;
		RestAssured.responseSpecification = videoGameResponse;
	}
}
