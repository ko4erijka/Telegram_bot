package ru.ko4erijka.weather.model.forecasts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.ko4erijka.weather.model.MoonCode;
import ru.ko4erijka.weather.model.forecasts.timesofday.Parts;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class Forecasts {
    String date;
    String sunrise;
    String sunset;
    @JsonProperty("moon_code")
    Integer moonCode;
    @JsonIgnore
    String moonCodeInText;
    Parts parts;

    public Forecasts(String date, String sunrise, String sunset, Integer moonCode, Parts parts) {
        this.date = date;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.moonCode = moonCode;
        this.parts = parts;
    }

    public String getDate() {
        return date;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public Integer getMoonCode() {
        return moonCode;
    }

    public Parts getParts() {
        return parts;
    }

    public String getMoonCodeInText() {
        return chooseMoonCodeInText();
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public void setMoonCode(Integer moonCode) {
        this.moonCode = moonCode;
    }

    public void setParts(Parts parts) {
        this.parts = parts;
    }

    private String chooseMoonCodeInText() {
        switch (this.moonCode) {
            case 0:
                return MoonCode.FULL_MOON.getNameMoon();
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
                return MoonCode.WANING_MOON.getNameMoon();
            case 4:
                return MoonCode.LAST_QUARTER.getNameMoon();
            case 8:
                return MoonCode.NEW_MOON.getNameMoon();
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
                return MoonCode.THE_GROWING_MOON.getNameMoon();
            case 12:
                return MoonCode.FIRST_QUARTER.getNameMoon();
            default:
                return "Ошибка кода для фазы луны";
        }
    }
}
