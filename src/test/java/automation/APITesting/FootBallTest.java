package automation.APITesting;

import static io.restassured.RestAssured.*;

import org.junit.Test;

import configuration.FootballAPIConfig;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.apache.http.entity.ContentType;
public class FootBallTest extends FootballAPIConfig {

	@Test
	public void getDetailsOfOneArea()
	{
		given()
		.queryParam("areas", 2027)
		.when().get("areas")
		.then();
	}
	
	@Test
	public void getDateFounded()
	{
		given()
		.when().get("teams/20")
		
		.then()
		.body("email", equalTo("info@fcstpauli.com"));
	}
	
	@Test
	public void getThirdTeamName()
	{
		given()
		.when().get("competitions/2021/teams")
		.then()
		.body("teams.name[2]", equalTo("Chelsea FC"));
		
	}
	
	@Test
	public void getAllMatchOfPlayer()
	{
		Response response = 
				given()
				.when().get("players/44/matches")
				.then()
				.contentType("application/json")
				.extract().response();
		
		String jsonBody = response.asString();
		System.out.println(jsonBody);
	}
	
	@Test
	public void getHeader()
	{
		Response response = 
				given()
				.when().get("competitions/")
				.then()
				.contentType("application/json")
				.extract().response();
		
		Headers header = response.getHeaders();
		
		String contentType = response.getHeader("Content-Type");
		
		System.out.println(contentType);
	}
	
	@Test
	public void getFirstCompetitionName()
	{
		String competitionName = get("competitions/").jsonPath().get("competitions.name[1]");
		
		System.out.println(competitionName);
	}
	
	@Test
	public void extractAllCompetition()
	{
		Response response = 
				given()
				.when().get("competitions/")
				.then().extract().response();
		ArrayList<String> competitions = response.path("competitions.name");
		
		System.out.println(competitions.size());
		
		for(String name: competitions)
		{
			System.out.println(name);
		}
		
		
	}
}
