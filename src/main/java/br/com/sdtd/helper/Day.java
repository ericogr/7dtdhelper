package br.com.sdtd.helper;

/**
 *
 * @author erico
 */
public class Day {
    private final int day;
    private final int hour;
    private final int minutes;
    
    public Day (int day, int hour, int minutes) {
        this.day = day;
        this.hour = hour;
        this.minutes = minutes;
    }
    
    public int day() {
        return this.day;
    }
    
    public int hour() {
        return this.hour;
    }
    
    public int minutes() {
        return this.minutes;
    }
    
    @Override
    public String toString() {
        return "Day: " + this.day + " " + this.hour + ":" + this.minutes;
    }
}
