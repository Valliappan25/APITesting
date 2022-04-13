package automation.APITesting;

import org.junit.Test;

import configuration.VideoGameConfig;
import configuration.VideoGameEndpoints;
import io.restassured.response.Response;
import pojoPackage.VideoGamePOJO;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.lessThan;

import org.hamcrest.Matcher;

public class VideoGameDBTest extends VideoGameConfig{

	@Test
	public void getAllGames()
	{
		given()
		.when().get(VideoGameEndpoints.ALL_VIDEO_GAMES)
		.then();
		
	}
	
	@Test
	public void createNewGameByJSON() {
		String jsonBody = "{\r\n"
				+ "  \"id\": 16,\r\n"
				+ "  \"name\": \"Popey\",\r\n"
				+ "  \"releaseDate\": \"2022-04-12T03:47:31.658Z\",\r\n"
				+ "  \"reviewScore\": 90,\r\n"
				+ "  \"category\": \"Arcade\",\r\n"
				+ "  \"rating\": \"minor\"\r\n"
				+ "}";
		
		given()
		.body(jsonBody)
		.when().post(VideoGameEndpoints.ALL_VIDEO_GAMES)
		.then();
	}
	
	@Test
	public void createNewGameByXML()
	{
		String xmlBody = "<videoGame category=\"Child\" rating=\"normal\">\r\n"
				+ "    <id>17</id>\r\n"
				+ "    <name>CandyCrush</name>\r\n"
				+ "    <releaseDate>2005-10-01T00:00:00+05:30</releaseDate>\r\n"
				+ "    <reviewScore>95</reviewScore>\r\n"
				+ "  </videoGame>";
		given().body(xmlBody)
				.header("Content-Type","application/xml")
				.header("Accept","application/xml")
		.when().post(VideoGameEndpoints.ALL_VIDEO_GAMES)
		.then();
	}
	
	@Test
	public void updateExistingGame()
	{
		String jsonBody = " {\r\n"
				+ "    \"id\": 14,\r\n"
				+ "    \"name\": \"FreeFire\",\r\n"
				+ "    \"releaseDate\": \"2005-10-01\",\r\n"
				+ "    \"reviewScore\": 90,\r\n"
				+ "    \"category\": \"Shooting\",\r\n"
				+ "    \"rating\": \"Major\"\r\n"
				+ "  }";
		given()
			.body(jsonBody)
		.when().put("videogames/14")
		.then();
	}
	
	@Test
	public void deleteExistingGame()
	{
		given()
		.when().delete("videogames/11")
		.then();
	}
	
	@Test
	public void getSingleGame()
	{
		given()
		.pathParam("videoGameId", 14)
		.when().get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
		.then();
	}
	
	@Test
	public void serializeObjectToJSON()
	{
		VideoGamePOJO videoGame = new VideoGamePOJO("90","1996-07-25","Need for Speed","9","11","Action");
		
		given().body(videoGame)
		.when().post(VideoGameEndpoints.ALL_VIDEO_GAMES)
		.then();
		
	}
	
	@Test
	public void testVideoGameSchemaXSD()
	{
		given()
		.pathParam("videoGameId", 1)
		.header("Content-Type","application/xml")
		.header("Accept","application/xml")
		.when()
			.get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
		.then()
			.body(matchesXsdInClasspath("VideoGame.xsd"));
		
	}
	
	@Test
	public void testVideoGameSchemaJSON()
	{
		given()
		.pathParam("videoGameId", 2)
		.header("Content-Type","application/json")
		.header("Accept","application/json")
		.when()
			.get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
		.then()
			.body(matchesJsonSchemaInClasspath("VideoGameJSON.json"));
	}
	
	@Test
	public void convertJSONToPOJO()
	{
		Response response = given()
				.pathParam("videoGameId",6)
		.when().get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
		.then().extract().response();
		
		VideoGamePOJO videoGame = response.getBody().as(VideoGamePOJO.class);
		
		System.out.println(videoGame.toString());
		
	}
	
	@Test
	public void captureResponseTime()
	{
		long responseTime = get(VideoGameEndpoints.ALL_VIDEO_GAMES).time();
		System.out.println(responseTime);
	}
	
	@Test
	public void assertResponseTime()
	{
		given()
		.when()
		.get(VideoGameEndpoints.ALL_VIDEO_GAMES)
		.then()
			.time(lessThan(1000L));
	}

		
}
