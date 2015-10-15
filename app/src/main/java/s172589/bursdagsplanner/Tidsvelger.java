package s172589.bursdagsplanner;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

/**
 * Created by Halvor on 13.10.2015.
 */
public class Tidsvelger extends AppCompatActivity {

    private TimePicker tidsvelger;
    private Button lagreKnapp,avbrytKnapp;
    private int time;
    private int minutt;
    String MY_FILE_NAME = "tid";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tidsvelger);
        tidsvelger = (TimePicker) findViewById(R.id.timePicker);
        tidsvelger.setIs24HourView(true);
        visRiktigTid();
        lyttere();

    }

    public void lagreTid() throws FileNotFoundException {
        int timeValg = tidsvelger.getCurrentHour();
        int minuttValg = tidsvelger.getCurrentMinute();
        Log.d("lagreTid() kalt:","Tiden er: " + timeValg +":"+ minuttValg);

        try {
            FileOutputStream fileout = openFileOutput(MY_FILE_NAME, Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write("INN HER MÅ TIDEN LEGGES! PARSE STRING");
            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void visRiktigTid() {

        final Calendar c = Calendar.getInstance();
        time = c.get(Calendar.HOUR_OF_DAY);
        minutt = c.get(Calendar.MINUTE);

        tidsvelger.setCurrentHour(time);
        tidsvelger.setCurrentMinute(minutt);

    }
    public void lyttere() {

        lagreKnapp = (Button) findViewById(R.id.lagreTid);
        avbrytKnapp = (Button) findViewById(R.id.avbrytTid);

        lagreKnapp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                try {
                    lagreTid();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
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