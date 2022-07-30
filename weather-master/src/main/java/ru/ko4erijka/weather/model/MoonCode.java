package ru.ko4erijka.weather.model;

public enum MoonCode {
    FULL_MOON("Полнолуние"),
    WANING_MOON("Убывающая Луна"),
    LAST_QUARTER("Последняя четверть"),
    NEW_MOON("Новолуние"),
    THE_GROWING_MOON("Растущая Луна"),
    FIRST_QUARTER("Первая четверть");

    private final String nameMoon;


    MoonCode(String nameMoon) {
        this.nameMoon = nameMoon;
    }

    public String getNameMoon() {
        return nameMoon;
    }
}
