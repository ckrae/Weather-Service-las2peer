package i5.las2peer.services.weatherService;

public class WeatherData {

	String cityName;
	String info;
	String temperature;
	String botMessage;

	public WeatherData(String cityName, String description, String temperature) {
		super();
		this.cityName = cityName;
		this.info = description;
		this.temperature = temperature;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String description) {
		this.info = description;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getBotMessage() {
		return botMessage;
	}

	public void setBotMessage(String botMessage) {
		this.botMessage = botMessage;
	}

}
