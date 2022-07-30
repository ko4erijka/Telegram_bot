package ru.ko4erijka.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Fact {
    Long temp;
    @JsonProperty("feels_like")
    Long feelsLike;
    String condition;
    @JsonProperty("wind_speed")
    Long windSpeed;
    @JsonProperty("pressure_mm")
    Long pressureMm;
    Long humidity;

    public String getCondition() {
        String newCondition ="";
        if(condition.contains("-")) {
            newCondition = condition.replace("-","_");
            return newCondition;
        } else return condition;
    }

    public Long getTemp() {
        return temp;
    }

    public Long getFeelsLike() {
        return feelsLike;
    }

    public Long getWindSpeed() {
        return windSpeed;
    }

    public Long getPressureMm() {
        return pressureMm;
    }

    public Long getHumidity() {
        return humidity;
    }
}
