package com.example.denny.mytextrecognition.microblink;

/**
 * Created by denny on 2017/9/27.
 */

public class PassportData {
    String name;
    String nationality;
    String passport_number;
    String expire_date;
    String date_of_birth;
    String gender;
    String raw_data;
    String passport_image_url;

    @Override
    public String toString() {
        return "PassportData{" +
                "\nname='" + name + '\'' +
                "\nnationality='" + nationality + '\'' +
                "\npassport_number='" + passport_number + '\'' +
                "\nexpire_date='" + expire_date + '\'' +
                "\ndate_of_birth='" + date_of_birth + '\'' +
                "\ngender='" + gender + '\'' +
                "\nraw_data='" + raw_data + '\'' +
                "\n}";
    }
}
