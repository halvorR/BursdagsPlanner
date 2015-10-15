package s172589.bursdagsplanner;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Halvor on 13.10.2015.
 */
public class Tidsvelger extends AppCompatActivity {

    private TimePicker tidsvelger;
    private Button lagreKnapp,avbrytKnapp;
    private int time;
    private int minutt;

    static final int TIME_DIALOG_ID = 999;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tidsvelger);
        tidsvelger.setIs24HourView(true);
        visRiktigTid();
        lyttere();

    }

    public void lagreTid() {
        int timeValg = tidsvelger.getCurrentHour();
        int minuttValg = tidsvelger.getCurrentMinute();
        Log.d("\r\nlagreTid() kalt:","Tiden er: " + timeValg +":"+ minuttValg);

    }

    public void visRiktigTid() {

        tidsvelger = (TimePicker) findViewById(R.id.timePicker);

        final Calendar c = Calendar.getInstance();
        time = c.get(Calendar.HOUR_OF_DAY);
        minutt = c.get(Calendar.MINUTE);

        tidsvelger.setCurrentHour(time);
        tidsvelger.setCurrentMinute(minutt);

    }
    public void lyttere() {

        lagreKnapp = (Button) findViewById(R.id.lagreTid);
        avbrytKnapp = (Button) findViewById(R.id.avbrytTid);

        Button lagreKnapp = (Button) findViewById(R.id.lagreNy);
        lagreKnapp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                lagreTid();
                finish();
            }
        });
        avbrytKnapp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

}