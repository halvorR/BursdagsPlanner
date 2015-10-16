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

import java.util.Calendar;

/**
 * Created by Halvor on 16.10.2015.
 */
public class SjekkBursdag extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "SJEKKER BURSDAG", Toast.LENGTH_SHORT).show();
        Log.d("SJEKKBURS", "I SjekkBursdag");

        Calendar c = Calendar.getInstance();

        // HENTE INN TIDSPUNKT FRA FIL

        Intent i =  new Intent(this, Meldingsender.class);
        PendingIntent pI = PendingIntent.getActivity(this, 0, i, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(), 10 * 1000, pI);



        // Sjekker dato i DB mot dagens dato

        // Finnes det noen, send Intent til Meldingssender


        return super.onStartCommand(intent, flags, startId);
    }

}
