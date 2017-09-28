package com.example.denny.mytextrecognition.microblink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.denny.mytextrecognition.R;
import com.microblink.activity.ScanCard;
import com.microblink.recognizers.BaseRecognitionResult;
import com.microblink.recognizers.RecognitionResults;
import com.microblink.recognizers.blinkid.mrtd.MRTDRecognitionResult;
import com.microblink.recognizers.blinkid.mrtd.MRTDRecognizerSettings;
import com.microblink.recognizers.settings.RecognitionSettings;
import com.microblink.recognizers.settings.RecognizerSettings;
import com.microblink.results.ocr.OcrResult;

public class MicroBlinkActivity extends AppCompatActivity {

    static final int DEFAULT_REQUEST_CODE = 9543;
    static final int CUSTOMIZE_REQUEST_CODE = 9547;

    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micro_blink);

        display = (TextView) findViewById(R.id.display);
    }

    public void customizeActClick(View view){
        Intent intent = new Intent(this, CustomizeAcitivity.class);
        startActivityForResult(intent, CUSTOMIZE_REQUEST_CODE);
    }

    public void bitmapClick(View view){
        Intent intent = new Intent(this, BitmapActivity.class);
        startActivityForResult(intent, CUSTOMIZE_REQUEST_CODE);
    }

    public void defaultClick(View view){
        // Intent for ScanCard Activity
        Intent intent = new Intent(this, ScanCard.class);

        // set your licence key
        // obtain your licence key at http://microblink.com/login or
        // contact us at http://help.microblink.com
        intent.putExtra(ScanCard.EXTRAS_LICENSE_KEY, "RGDQ4HOZ-SOWAK7K3-KWQFMSSK-62TQCNGN-FWDPQ37Z-HTFY6XKF-XRXCCJBQ-R3W5UNGO");

        RecognitionSettings settings = new RecognitionSettings();
        // setup array of recognition settings (described in chapter "Recognition
        // settings and results")
        settings.setRecognizerSettingsArray(setupSettingsArray());
        intent.putExtra(ScanCard.EXTRAS_RECOGNITION_SETTINGS, settings);

        // Starting Activity
        startActivityForResult(intent, DEFAULT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DEFAULT_REQUEST_CODE) {
            if (resultCode == ScanCard.RESULT_OK && data != null) {
                RecognitionResults result = data.getParcelableExtra(ScanCard.EXTRAS_RECOGNITION_RESULTS);
                displayResult2(result);
            }
        }
        else if(requestCode == CUSTOMIZE_REQUEST_CODE){
            if (resultCode == CustomizeAcitivity.RESULT_OK && data != null) {
                RecognitionResults result = data.getParcelableExtra(CustomizeAcitivity.EXTRAS_RECOGNITION_RESULTS);
                displayResult2(result);
            }
        }
    }

    void displayResult(RecognitionResults result){
        // get array of recognition results
        BaseRecognitionResult[] resultArray = result.getRecognitionResults();
        StringBuilder builder = new StringBuilder();
        for(BaseRecognitionResult e: resultArray){
            builder.append(e.toString());
        }
        display.setText(builder.toString());
    }

    static String TAG = "MicroBlink";
    void displayResult2(RecognitionResults results){
        BaseRecognitionResult[] dataArray = results.getRecognitionResults();
        Log.i(TAG, "DataArray length: " + dataArray.length);
        for(BaseRecognitionResult baseResult : dataArray) {
            if(baseResult instanceof MRTDRecognitionResult) {
                MRTDRecognitionResult result = (MRTDRecognitionResult) baseResult;

                // you can use getters of MRTDRecognitionResult class to
                // obtain scanned information
                if(result.isValid() && !result.isEmpty()) {
                    if(result.isMRZParsed()) {
                        PassportData data = new PassportData();
                        data.name = String.format("%s %s" ,result.getPrimaryId(),result.getSecondaryId());
                        data.nationality = result.getNationality();
                        data.passport_number = result.getDocumentNumber();
                        data.expire_date = result.getRawDateOfExpiry();
                        data.date_of_birth = result.getRawDateOfBirth();
                        data.gender = result.getSex();
                        data.raw_data = result.getMRZText();
                        display.setText(data.toString());
                    } else {
                        OcrResult rawOcr = result.getOcrResult();
                        // attempt to parse OCR result by yourself
                        // or ask user to try again
                    }
                    // If additional fields of interest are expected, obtain
                    // results here. Here we assume that each parser is the only parser in its
                    // group, and parser name is equal to the group name.
                    // e.g. we have member variable
                    // private String[] mParserNames = new String[]{address, dateOfBirth};
//                    for (String parserName : mParserNames) {
//                        // use getParsedResult(String parserGroupName, String parserName)
//                        String groupName = parserName;
//                        String parsedResult = result.getParsedResult(groupName, parserName);
//                        // check whether parsing was successfull (parsedResult is not null nor empty)
//                        if (parsedResult != null && !parsedResult.isEmpty()) {
//                            // do something with the result
//                        } else {
//                            // you can read unparsed raw ocr result if parsing was unsuccessful
//                            // use getOcrResult(String parserGroupName)
//                            String ocrResult = result.getOcrResult(groupName);
//                            // attempt to parse OCR result by yourself
//                        }
//                    }
                } else {
                    // not all relevant data was scanned, ask user
                    // to try again
                }
            }
        }
    }

    private RecognizerSettings[] setupSettingsArray() {
        MRTDRecognizerSettings sett = new MRTDRecognizerSettings();

        // now add sett to recognizer settings array that is used to configure
        // recognition
        return new RecognizerSettings[] { sett };
    }
}
