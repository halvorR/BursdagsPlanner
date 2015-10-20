package s172589.bursdagsplanner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

/**
 * Created by Halvor on 16.10.2015.
 */
public class SjekkBursdag extends Service {
    private String tid;
    private int[] tidspunkt;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "SJEKKER BURSDAG", Toast.LENGTH_SHORT).show();
        Log.d("SJEKKBURS", "I SjekkBursdag");
        readTidFromFile();
        tidspunkt = timeOgMinutt(tid);

        Log.d("SJEKKBURSDAG", "Tiden hentet fra fil: " + tid);

        Calendar cal = Calendar.getInstance();
        Calendar cal_ny = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        Log.d("CAL FØR SDF", "Uendret cal: " + cal.getTime() + " | Millisek: " + cal.getTimeInMillis());

        cal_ny.set(Calendar.HOUR_OF_DAY, tidspunkt[0]);
        cal_ny.set(Calendar.MINUTE, tidspunkt[1]);
        cal_ny.set(Calendar.SECOND, 0);

        Log.d("CAL etter ", "Endret: " + cal_ny.getTime()+ " | Millisek: " +cal_ny.getTimeInMillis());

        long te = cal.getTimeInMillis() - cal_ny.getTimeInMillis();
        Log.v("DIFF", "Differansen er " +te);
        Intent i = new Intent(this, Meldingsender.class);

        PendingIntent pintent = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pintent);
        return super.onStartCommand(intent, flags, startId);
    }

    private String readTidFromFile() {
        try {
            InputStream inputStream = openFileInput("tid");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                tid = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        if (tid==null)
            tid="0:0";
        return tid;
    }

    public int[] timeOgMinutt(String t) {
        String[] tidStringArray = t.split(":");
        int[] tidIntArray  = new int[tidStringArray.length];

        for(int i = 0; i < tidStringArray.length; i++){
            tidIntArray[i] = Integer.parseInt(tidStringArray[i]);
        }
        return tidIntArray;
    }
}
