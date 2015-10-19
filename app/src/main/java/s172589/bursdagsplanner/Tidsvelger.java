package s172589.bursdagsplanner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

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

        Switch s = (Switch) findViewById(R.id.velgAvPå);

        SharedPreferences shared = getSharedPreferences("MeldingAvPå",MODE_PRIVATE);
        s.setChecked(shared.getBoolean("meldAvPå",true));

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences("MeldingAvPå", MODE_PRIVATE).edit();
                    editor.putBoolean("meldAvPå", true);
                    editor.commit();

                    Toast.makeText(getApplicationContext(),"SWITCH PÅ", Toast.LENGTH_SHORT).show();
                    Log.d("SWITCH", "Jeg har blitt satt som på");
                    startService();
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("MeldingAvPå", MODE_PRIVATE).edit();
                    editor.putBoolean("meldAvPå", false);
                    editor.commit();

                    Toast.makeText(getApplicationContext(),"SWITCH AV", Toast.LENGTH_SHORT).show();
                    Log.d("SWITCH", "Jeg har blitt satt som av");
                    stoppService();
                }
            }
        });

    }

    public void lagreTid() throws FileNotFoundException {
        int timeValg = tidsvelger.getCurrentHour();
        int minuttValg = tidsvelger.getCurrentMinute();
        String lagreTekst = Integer.toString(timeValg)+":"+Integer.toString(minuttValg);

        try {
            FileOutputStream fileout = openFileOutput(MY_FILE_NAME, Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(lagreTekst);
            Log.d("lagreTid()", "Tiden er: " + timeValg + ":" + minuttValg + " og dette er skrevet til filen " + MY_FILE_NAME + ": " + lagreTekst);
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

        lagreKnapp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    lagreTid();
                    startService();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"Ny tid for utsendelse lagret", Toast.LENGTH_SHORT).show();
            }
        });
        avbrytKnapp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void startService() {

        Intent intent = new Intent();
        intent.setAction("s172589.tidsvelger.meldingssender");
        sendBroadcast(intent);
    }

    public void stoppService() { Intent i = new Intent(this, Meldingsender.class);
        Toast.makeText(getApplicationContext(),"STOPPER MELDING", Toast.LENGTH_SHORT).show();
        Log.d("STOPPMELD","Stopper meldingssending");


        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pintent);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}