package i5.las2peer.services.weatherService;

import java.net.HttpURLConnection;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import i5.las2peer.connectors.webConnector.client.ClientResponse;
import i5.las2peer.connectors.webConnector.client.MiniClient;
import i5.las2peer.restMapper.RESTService;
import i5.las2peer.restMapper.annotations.ServicePath;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

// TODO Describe your own service
/**
 * las2peer-Template-Service
 * 
 * This is a template for a very basic las2peer service that uses the las2peer
 * WebConnector for RESTful access to it.
 * 
 * Note: If you plan on using Swagger you should adapt the information below in
 * the SwaggerDefinition annotation to suit your project. If you do not intend
 * to provide a Swagger documentation of your service API, the entire Api and
 * SwaggerDefinition annotation should be removed.
 * 
 */
// TODO Adjust the following configuration
@Api
@SwaggerDefinition(info = @Info(title = "las2peer Weather Service", version = "1.0.0", description = "A las2peer Weather Service for demonstration purposes.", termsOfService = "http://your-terms-of-service-url.com", contact = @Contact(name = "John Doe", url = "provider.com", email = "john.doe@provider.com"), license = @License(name = "your software license name", url = "http://your-software-license-url.com")))
@ServicePath("/weather")
// TODO Your own service class
public class WeatherService extends RESTService {

	/**
	 * Template of a get function.
	 * 
	 * @return Returns an HTTP response with the username as string content.
	 */
	@GET
	@Path("/{cityName}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get Information about the current Weather of a city", notes = "Weather data")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Get Information about the current Weather of a city", response = WeatherData.class) })
	public Response getWeather(@PathParam("cityName") String cityName) {

		System.out.println("called: " + cityName);
		String appId = "be776fd00526beacac5e7814b35ee8d8";

		System.out.println("send open weather api request");
		MiniClient client = new MiniClient();
		client.setConnectorEndpoint("https://api.openweathermap.org/data/2.5");
		String path = "/weather?q=" + cityName + "&appid=" + appId + "&units=metric";

		ClientResponse result = client.sendRequest("GET", path, "", "application/json", "application/json",
				new HashMap<String, String>());
		System.out.println(result.getHttpCode() + result.getResponse());

		if (result.getHttpCode() == 404)
			return Response.serverError().entity("sorry i dont know the weather of " + cityName).build();

		try {
						
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(result.getResponse());
			JSONArray weather = (JSONArray) json.get("weather");
			JSONObject descObj = (JSONObject) weather.get(0);
			String description = descObj.getAsString("description");
			JSONObject main = (JSONObject) json.get("main");
			String temp = main.getAsString("temp");

			WeatherData res = new WeatherData(cityName, description, temp);
			res.setBotMessage("In " + cityName + " we have " + description + " with " + temp + " degrees");

			return Response.ok().entity(res).build();

		} catch (Exception e) {
			return Response.serverError().entity("sorry i dont know the weather of " + cityName).build();
		}

	}

	/**
	 * Tempate of a notification test
	 * 
	 * @return Returns an HTTP response with the username as string content.
	
	@GET
	@Path("/notify")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Get Weather Data", notes = "Weather data")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE") })
	public Response getNotify() {

		JSONObject body = new JSONObject();
		body.put("functionName", "notify");
		body.put("msg", "notification service triggered");

		Context.get().monitorEvent(MonitoringEvent.SERVICE_CUSTOM_MESSAGE_78, body.toJSONString());
		System.out.println("notify A activiated");

		TestRoutine routine = new TestRoutine(Context.get());
		routine.run();

		return Response.ok().entity("notification send").build();
	}
	
	/**
	 * Tempate of a error response
	 * 
	 * @return 
	 
	@GET
	@Path("/error")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Get Weather Data", notes = "Weather data")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE") })
	public Response getError() {

	
		return Response.serverError().entity("test error message").build();
	}

	/**
	 * Template of a get function with query parameter
	 * 
	 * @return Returns an HTTP response with the username as string content.
	 
	@GET
	@Path("/weather/query")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Get Weather Data", notes = "Weather data")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE") })
	public Response getWeatherWithQuery(@QueryParam("cityName") String cityName, @QueryParam("qwer") String qwer) {

		String res = "Query received: " + cityName + " " + qwer;
		return Response.ok().entity(res).build();

	}

	/**
	 * Template of a post function.
	 * 
	 * @param myInput The post input the user will provide.
	 * @return Returns an HTTP response with plain text string content derived from
	 *         the path input param.
	 
	@POST
	@Path("/post/{input}/{parameter}")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE") })
	@ApiOperation(value = "REPLACE THIS WITH AN APPROPRIATE FUNCTION NAME", notes = "Example method that returns a phrase containing the received input.")
	public Response postTemplate(@PathParam("input") String myInput, @PathParam("parameter") String myParameter) {
		String returnString = "";
		returnString += "POST Input " + myInput + " " + myParameter;
		return Response.ok().entity(returnString).build();
	}
	
	/**
	 * Template of a post function.
	 * 
	 * @param myInput The post input the user will provide.
	 * @return Returns an HTTP response with plain text string content derived from
	 *         the path input param.
	 
	@POST
	@Path("/put/{input}/{parameter}")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE") })
	@ApiOperation(value = "REPLACE THIS WITH AN APPROPRIATE FUNCTION NAME", notes = "Example method that returns a phrase containing the received input.")
	public Response putTemplate(@PathParam("input") String myInput, @PathParam("parameter") String myParameter) {
		String returnString = "";
		returnString += "PUT Input " + myInput + " " + myParameter;
		return Response.ok().entity(returnString).build();
	}

	// TODO your own service methods, e. g. for RMI
*/
}
