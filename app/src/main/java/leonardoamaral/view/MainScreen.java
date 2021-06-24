package leonardoamaral.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;

import leonardoamaral.java.Stats;
import leonardoamaral.java.Utils;
import leonardoamaral.prepositions.R;

public class MainScreen extends Activity {

    String correctedAnswer;
    InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        generateQuestion();
        addListenerOnButton();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5739869500751104/1800802279");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });

        requestNewInterstitial();

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


    public void backToStartScreen(View view){
        Intent intent = new Intent(this, StartScreen.class);
        startActivity(intent);
    }


    public void generateQuestion() {

        Utils u = new Utils();
        AssetManager am = getAssets();
        List<String> srtFiles = u.getSrtFiles(am);
        List<String> allLines = u.getAllLines(am, srtFiles);

        List<String> linesPrepositions = u.getLinesWithPrepositions(allLines);

        String[] string = u.maskSentence(linesPrepositions);

        String newSentence = string[0];
        correctedAnswer = string[1];

        List<String> mixedAnswer = u.mixAnswers(correctedAnswer);

        TextView tv = (TextView) findViewById(R.id.question);
        tv.setText(newSentence);

        TextView a = (TextView) findViewById(R.id.option_a);
        a.setText(mixedAnswer.get(0));

        TextView b = (TextView) findViewById(R.id.option_b);
        b.setText(mixedAnswer.get(1));

        TextView c = (TextView) findViewById(R.id.option_c);
        c.setText(mixedAnswer.get(2));

        TextView d = (TextView) findViewById(R.id.option_d);
        d.setText(mixedAnswer.get(3));

    }

    public void addListenerOnButton(){

        Button skip = (Button) findViewById(R.id.button4);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stats.skip();
                generateQuestion();
                RadioGroup rg = (RadioGroup) findViewById(R.id.radiogroup);
                rg.clearCheck();
            }
        });

        Button check = (Button) findViewById(R.id.button3);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioGroup rg = (RadioGroup) findViewById(R.id.radiogroup);

                if (rg.getCheckedRadioButtonId() == -1){
                    Context context = getApplicationContext();
                    CharSequence text = "Please, select at least one answer!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }else {


                    TextView checked = (TextView) findViewById(rg.getCheckedRadioButtonId());


                    if (checked.getText().toString().equalsIgnoreCase(correctedAnswer)) {

                        Context context = getApplicationContext();
                        CharSequence text = "Corrected answer!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        Stats.correct();


                    } else {
                        Context context = getApplicationContext();
                        CharSequence text = correctedAnswer + "is the correct answer!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        Stats.wrong();

                    }
                    generateQuestion();
                    rg.clearCheck();
                }

                if (Stats.getInstance().getTotalOfAll() % 15 == 0) {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            }
        });

    }
}
