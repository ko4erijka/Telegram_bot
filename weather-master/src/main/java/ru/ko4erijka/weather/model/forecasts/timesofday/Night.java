package ru.ko4erijka.weather.model.forecasts.timesofday;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@EqualsAndHashCode
@ToString
public class Night {
    @JsonProperty("temp_min")
    private Long tempMin;
    @JsonProperty("temp_avg")
    private Long tempAvg;
    @JsonProperty("temp_max")
    private Long tempMax;
    @JsonProperty("feels_like")
    private Long feelsLike;
    @JsonProperty("pressure_mm")
    private Long pressureMm;
    @JsonProperty("condition")
    private String condition;
    public String getCondition() {
        String newCondition ="";
        if(condition.contains("-")) {
            newCondition = condition.replace("-","_");
            return newCondition;
        } else return condition;
    }

    public Long getTempMin() {
        return tempMin;
    }

    public Long getTempAvg() {
        return tempAvg;
    }

    public Long getTempMax() {
        return tempMax;
    }

    public Long getFeelsLike() {
        return feelsLike;
    }

    public Long getPressureMm() {
        return pressureMm;
    }
}
