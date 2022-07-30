package ru.ko4erijka.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.ko4erijka.weather.model.forecasts.Forecasts;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Weather {
    @JsonProperty("now_dt")
    LocalDateTime nowDT;
    @JsonProperty("def_pressure_mm")
    Long defPressureMM;
    Fact fact;
    @JsonProperty("forecasts")
    List<Forecasts> forecastsList;
}
