package com.example.denny.ticketparser;

/**
 * Created by denny on 2017/8/4.
 */

public class TicketData {

    public String given_name;
    public String family_name;
    public String departure;
    public String arrival;
    public String flight_num;
    public String booking_ref_num;
    public String departure_date;
    public String julian_date;
    public String airline_id;
    public String frequent_flyer_id;
    public String raw_data;

    @Override
    public String toString() {
        return "TicketData{" +
                "given_name='" + given_name + '\'' +
                ", family_name='" + family_name + '\'' +
                ", departure='" + departure + '\'' +
                ", arrival='" + arrival + '\'' +
                ", flight_num='" + flight_num + '\'' +
                ", booking_ref_num='" + booking_ref_num + '\'' +
                ", julian_date='" + julian_date + '\'' +
                ", airline_id='" + airline_id + '\'' +
                ", frequent_flyer_id='" + frequent_flyer_id + '\'' +
                '}';
    }
}
