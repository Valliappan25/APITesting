package automation.APITesting;

import org.junit.Test;

import configuration.VideoGameConfig;
import configuration.VideoGameEndpoints;

import static io.restassured.RestAssured.*;

public class MyFirstTest extends VideoGameConfig {
	
	@Test
	public void myFirstMethod()
	{
		given()
				.log().all().
		when().get("videogames")
		.then().log().all();
	}
	
	@Test
	public void mySecondMethod()
	{
		when().get(VideoGameEndpoints.ALL_VIDEO_GAMES)
		.then().log().all();
	}

}
