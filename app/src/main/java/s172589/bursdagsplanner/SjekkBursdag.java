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

        Calendar cal = Calendar.getInstance();
        Intent i = new Intent(this, Meldingsender.class);

        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 10 * 1000, pintent);
        return super.onStartCommand(intent, flags, startId);
    }

}
