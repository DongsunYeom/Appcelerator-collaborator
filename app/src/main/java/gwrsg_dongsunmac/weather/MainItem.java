package gwrsg_dongsunmac.weather;

import java.util.List;

/**
 * Created by gwrsg-dongsunmac on 1/10/16.
 */
public class MainItem {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getForecastIssue() {
        return forecastIssue;
    }

    public void setForecastIssue(String forecastIssue) {
        this.forecastIssue = forecastIssue;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public List<WeatherForecast> getWeatherForecast() {
        return WeatherForecast;
    }

    public void setWeatherForecast(List<WeatherForecast> WeatherForecast) {
        this.WeatherForecast = WeatherForecast;
    }

    private String title;
    private String category;
    private String forecastIssue;
    private String validTime;
    private List<WeatherForecast> WeatherForecast;

}
