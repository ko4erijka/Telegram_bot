package ru.ko4erijka.weather.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ko4erijka.weather.model.Weather;

@FeignClient(name = "WeatherFeignClient", url = "https://api.weather.yandex.ru/v2/forecast")
public interface WeatherFeignClient {

    //?lat=55.75396&lon=37.620393&extra=true
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET,value = "/")
    Weather getWeather(@RequestHeader("X-Yandex-API-Key") String header, @RequestParam(value = "lat",defaultValue = "55.75396" )
                             String lat, @RequestParam(value = "lon",defaultValue = "37.62039" ) String lon,
                       @RequestParam(value = "extra", defaultValue = "true") Boolean extra);

}
