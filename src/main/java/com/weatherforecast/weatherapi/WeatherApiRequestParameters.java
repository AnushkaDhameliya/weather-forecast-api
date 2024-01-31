package com.weatherforecast.weatherapi;

import java.sql.Date;
import java.sql.Timestamp;

public class WeatherApiRequestParameters {

    private String key;
    private String q;
    private int days;
    private Date dt;
    private Timestamp unixdt;
    private Date end_dt;
    private Timestamp unixend_dt;
    private int hour;
    private String alert;
    private String aqi;
    private String tide;
    private int tp;
    private String lang;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Timestamp getUnixdt() {
        return unixdt;
    }

    public void setUnixdt(Timestamp unixdt) {
        this.unixdt = unixdt;
    }

    public Date getEnd_dt() {
        return end_dt;
    }

    public void setEnd_dt(Date end_dt) {
        this.end_dt = end_dt;
    }

    public Timestamp getUnixend_dt() {
        return unixend_dt;
    }

    public void setUnixend_dt(Timestamp unixend_dt) {
        this.unixend_dt = unixend_dt;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getTide() {
        return tide;
    }

    public void setTide(String tide) {
        this.tide = tide;
    }

    public int getTp() {
        return tp;
    }

    public void setTp(int tp) {
        this.tp = tp;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
