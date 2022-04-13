package automation.APITesting;

import org.junit.Test;

import configuration.FootballAPIConfig;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class GpathToJSONTest extends FootballAPIConfig{
	
	@Test
	public void getSingleMatchDetails()
	{
		Response response = given()
						.header("Content-Type", "application/json")
						.header("Accept","application/json")
						.when() 
				.get("competitions/2021/teams")
				.then().extract().response();
		
		Map<String,?> matchDetail = response.path("teams.find { it.name == 'Everton FC' }");
		
		System.out.println(matchDetail);
		System.out.println(matchDetail.size());
	}
	
	@Test
	public void extractNameByFind()
	{
		Response response = get("teams/57");
		
		String playerName = response.path("squad.find { it.shirtNumber == 48 }.name ");
		
		System.out.println("Player name of shirt 48 is "+playerName);
	}
	
	@Test
	public void extractAllPlayerNameByFindAll()
	{
		Response response = get("teams/57");
		
		ArrayList<String> playerNames = response.path("squad.findAll { it.position == 'Defence' }.name");
		
		System.out.println(playerNames.toString());
	}
	
	@Test
	public void extractHigestNumberUsingMax()
	{
	Response response = get("teams/57");
		
		String playerName = response.path("squad.min { it.shirtNumber }.name ");
		
		System.out.println("Player name of min shirt Number is "+playerName);
	}

	@Test
	public void extractSumofIdUsingCollectAndSum()
	{
	Response response = get("teams/57");
		
		int sumOfIds = response.path("squad.collect { it.id }.sum() ");
		
		System.out.println("Sum of Player Id's "+sumOfIds);
	}
	
	@Test
	public void extractAllPlayerNameByFindAndFindAll()
	{
		Response response = get("teams/57");
		String position = "Defence";
		String nationality = "Brazil";
		
		Map<String,?> playerDetails = response.path("squad.findAll { it.position == '%s' }.find { it.nationality == '%s' }", position,nationality);
		
		System.out.println(playerDetails.toString());
	}
	
	@Test
	public void extractAllPlayer()
	{
		Response response = get("teams/57");
		String position = "Defence";
		String nationality = "England";
		
		ArrayList<Map<String,?>> playerDetails = response.path("squad.findAll { it.position == '%s' }.findAll { it.nationality == '%s' }", position,nationality);
		
		for(Map<String, ?> player : playerDetails)
		{
			System.out.println(player);
			for(Map.Entry<String,?> value : player.entrySet())
			{
				System.out.println(value.getKey()+" : "+value.getValue());
			}
		}
	}
}

