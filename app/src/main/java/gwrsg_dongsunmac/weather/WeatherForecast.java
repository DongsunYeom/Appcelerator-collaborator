package gwrsg_dongsunmac.weather;

/**
 * Created by gwrsg-dongsunmac on 1/10/16.
 */
public class WeatherForecast {
    private String forecast;

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String lat;
    private String lon;
    private String name;
}
