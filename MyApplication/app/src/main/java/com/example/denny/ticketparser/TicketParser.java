package com.example.denny.ticketparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by denny on 2017/8/4.
 */

public class TicketParser {

    static TicketParser instance = new TicketParser();

    private TicketParser(){

    }

    public static TicketData parse(String text) {
        return instance.parse2(text);
    }

    Pattern [] namePatterns = {
            Pattern.compile("(\\p{Alpha}+)/(\\p{Alpha}+)"),
            Pattern.compile("(\\p{Alpha}+)/(\\p{Alpha}+\\p{Space}\\p{Alpha}+)"),
            Pattern.compile("(\\p{Alpha}+)\\p{Space}(\\p{Alpha}+)"),
            Pattern.compile("()(\\p{Alpha}+)")
    };

    Pattern bookingRefNumberPattern = Pattern.compile("(\\p{Alnum}+)");
    Pattern arrivalPattern = Pattern.compile("(\\p{Alpha}{3})");
    Pattern departurePattern = Pattern.compile("(\\p{Alpha}{3})");
    Pattern airlinePattern = Pattern.compile("(\\p{Alpha}{2,3})");
    Pattern flightNumberPattern = Pattern.compile("(\\d+)");
    Pattern julianDatePattern = Pattern.compile("(\\d{3})");
    Pattern frequentFlyerPattern = Pattern.compile("(\\p{Alnum}{2}\\p{Blank}\\d{8,})");

    TicketData parse2(String text){
        TicketData data = new TicketData();
        try{
            if(!text.substring(0,1).equals("M")){
                // Throw Exception
            }

            getName(text.substring(2, 22).trim(), data);
            data.booking_ref_num = checkString(text.substring(23, 30).trim(), bookingRefNumberPattern);
            data.departure = checkString(text.substring(30, 33), departurePattern);
            data.arrival = checkString(text.substring(33, 36), arrivalPattern);
            data.airline_id = checkString(text.substring(36, 39).trim(), airlinePattern);
            data.flight_num = checkString(text.substring(39, 44).trim(), flightNumberPattern);
            data.julian_date = checkString(text.substring(44,47), julianDatePattern);
            data.departure_date = JulianToFormatDate.convert(Integer.valueOf(data.julian_date));

        }catch (IndexOutOfBoundsException e){
            // Throw Exception
        }

        try{
            data.frequent_flyer_id = findString(text.substring(60,text.length()-1), frequentFlyerPattern);
        }catch (IndexOutOfBoundsException e){

        }

        return data;
    }

    void getName(String text, TicketData data){
        for(Pattern namePattern: namePatterns){
            Matcher matcher = namePattern.matcher(text);
            if(matcher.matches()){
                data.family_name = matcher.group(1);
                data.given_name = matcher.group(2);
                return;
            }
        }
        // TODO: Throw exception
    }

    String checkString(String text, Pattern pattern){
        Matcher matcher = pattern.matcher(text);
        if(matcher.matches()){
            return matcher.group(1);
        }else{
            // TODO: Throw exception
            return null;
        }
    }

    String findString(String text, Pattern pattern){
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()){
            return matcher.group(1);
        }else{
            // TODO: Throw exception
            return null;
        }
    }
}
