package com.example.denny.mytextrecognition.microblink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.denny.mytextrecognition.R;
import com.microblink.activity.ScanCard;
import com.microblink.recognizers.BaseRecognitionResult;
import com.microblink.recognizers.RecognitionResults;
import com.microblink.recognizers.blinkid.mrtd.MRTDRecognizerSettings;
import com.microblink.recognizers.settings.RecognitionSettings;
import com.microblink.recognizers.settings.RecognizerSettings;

public class MicroBlinkActivity extends AppCompatActivity {

    static final int DEFAULT_REQUEST_CODE = 9543;

    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micro_blink);

        display = (TextView) findViewById(R.id.display);
    }

    public void customizeClick(View view){

    }

    public void defaultClick(View view){
        // Intent for ScanCard Activity
        Intent intent = new Intent(this, ScanCard.class);

        // set your licence key
        // obtain your licence key at http://microblink.com/login or
        // contact us at http://help.microblink.com
        intent.putExtra(ScanCard.EXTRAS_LICENSE_KEY, "INRI37N4-M4YTK7UV-73PPICOR-FOXVCMXL-I3DPHREE-27XIBUSB-KW6S6PVU-MFOTFEC3");

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
                // perform processing of the data here

                // for example, obtain parcelable recognition result
                Bundle extras = data.getExtras();
                RecognitionResults result = data.getParcelableExtra(ScanCard.EXTRAS_RECOGNITION_RESULTS);

                // get array of recognition results
                BaseRecognitionResult[] resultArray = result.getRecognitionResults();
                StringBuilder builder = new StringBuilder();
                for(BaseRecognitionResult e: resultArray){
                    builder.append(e.toString());
                }
                display.setText(builder.toString());
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
