package automation.APITesting;

import org.junit.Test;
import static io.restassured.RestAssured.*;

import java.util.Iterator;
import java.util.List;

import configuration.VideoGameConfig;
import configuration.VideoGameEndpoints;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.element.Node;
import io.restassured.response.Response;

public class GPathXMLTest extends VideoGameConfig {

	@Test
	public void getFirstGameName()
	{
		Response response = get(VideoGameEndpoints.ALL_VIDEO_GAMES);
		
		String gameName = response.path("videoGames.videoGame.name[0]");
		
		System.out.println(gameName);
	}
	
	@Test
	public void getCategory()
	{
		Response response = get(VideoGameEndpoints.ALL_VIDEO_GAMES);
		
		String category = response.path("videoGames.videoGame[0].@category");
		
		System.out.println(category);
	}

	@Test
	public void getListOfAllXMLNodes()
	{
		String responseAsAString = get(VideoGameEndpoints.ALL_VIDEO_GAMES).asString();
		
		List<Node> allResult = XmlPath.from(responseAsAString).get("videoGames.videoGame.findAll { element -> return element }");
		
		for(Node a : allResult)
		{
			System.out.println(a.get("name"));
		}
	}
	
	@Test
	public void getListOfAllXMLNodesByCategoty()
	{
		String responseAsAString = get(VideoGameEndpoints.ALL_VIDEO_GAMES).asString();
		
		List<Node> allResult = XmlPath.from(responseAsAString).
				get("videoGames.videoGame.findAll { videoGame -> def category = videoGame.@category; category == 'Driving' }");
		
		for(Node a : allResult)
		{
			System.out.println(a.get("name"));
		}
	}
	
	@Test
	public void getSingleNode() {
		
		String responseAsAString = get(VideoGameEndpoints.ALL_VIDEO_GAMES).asString();
		
		Node videoGame = XmlPath.from(responseAsAString).
				get("videoGames.videoGame.find { videoGame -> def name = videoGame.name; name == 'Grand Theft Auto III' }");
		
		String name = videoGame.get("name").toString();
		System.out.println(name);
	}
	
	@Test
	public void getAllNodesBasedOnCondition()
	{
		String responseAsAString = get(VideoGameEndpoints.ALL_VIDEO_GAMES).asString();
		
		int reviewScore = 90;
		
		List<Node> allVideoGameBasedOnScore = XmlPath.from(responseAsAString).
				get("videoGames.videoGame.findAll { it.reviewScore.toFloat() >= "+reviewScore+ "}");
		
		System.out.println(allVideoGameBasedOnScore);
	}
}
