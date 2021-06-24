package leonardoamaral.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import leonardoamaral.java.Stats;
import leonardoamaral.prepositions.R;

public class StartScreen extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        changeButtonText();

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);

    }

    public void changeButtonText(){

        if (Stats.getInstance().getTotalOfAll() > 0 ){
            Button b = (Button) findViewById(R.id.button);
            b.setText("Continue");
        }else{
            Button b = (Button) findViewById(R.id.button);
            b.setText("Start Now");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_start_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendFeedbackScreen(MenuItem item){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"lnd.amaral@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Feedback - Prepositions app");
        i.putExtra(Intent.EXTRA_TEXT   , "Write here your comment. It is really important for me your feedback. Thanks!");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(StartScreen.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void inviteFriend(MenuItem item){

        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "Hey! How about give a try and download this app to practice your skills using prepositions in English? http://bit.ly/1VEQu4M";

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(StartScreen.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void clearProgress(MenuItem item){
        Stats.getInstance().clearStats();
        changeButtonText();
    }

    public void openMainScreen(View view){
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
    }

    public void openProgressScreen(View view){
        Intent intent = new Intent(this, ProgressScreen.class);
        startActivity(intent);
    }

}
