package s172589.bursdagsplanner;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Roger on 16.10.2015.
 */
public class Meldingsender extends Service implements Serializable {
    private List<Kontakt> dagens;
    DBHandler db = new DBHandler(this);
    Kontakt kontakt;
    String melding;
    Boolean sendt;
    int teller = 0;
    private static long resetTidMilisek;
    SmsManager smsMan = SmsManager.getDefault();

    @Override
    public IBinder onBind(Intent arg0) { return null; }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MELDINGSENDER", "I meldingsenders onStartCommand()");
        resetSendt();
        if(!sendt) {
            Log.d("startcommand", "Sender meldinga!");
            sendMeld();
        } else {
            Log.d("startcommand", "Meldinga allerede sendt!");
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.melding_sendt), Toast.LENGTH_SHORT).show();
        }

        return super.onStartCommand(intent, flags, startId);
    }



    public boolean resetSendt() {

        Calendar currentTid = Calendar.getInstance();
        Calendar resetTid = Calendar.getInstance();
        teller++;


        resetTid.set(Calendar.HOUR_OF_DAY, 23);
        resetTid.set(Calendar.MINUTE, 59);
        resetTid.set(Calendar.SECOND, 59);
        resetTid.set(Calendar.MILLISECOND,59);

        resetTidMilisek = resetTid.getTimeInMillis();

        Log.v("TESTTID", "Dato på currentTid " + currentTid.getTime().toString());
        Log.v("TESTTID", "Dato på resetTid " + resetTid.getTime().toString());
        Long t = currentTid.getTimeInMillis() - resetTid.getTimeInMillis();
        Log.v("TESTTID", "Diff milis" + t);

        // Hvis det er første gang
        if(teller == 1 ){
            Log.v("Første", "Første gangen!");
            sendt = false;
            return sendt;
        }

        // Reset melding hvis det er en ny dag
        if(currentTid.getTimeInMillis() >= resetTidMilisek){
            Log.v("neste dag", "Endres hvis det er neste dag");
            sendt = false;
            return sendt;
        }

        return sendt;

    }



    public boolean gratuler(Kontakt kontakt) {
        this.kontakt = kontakt;
        readFromFile();
        Log.d("Meldingssender/gratuler", "Leser melding. Inputstream:\r\n"+melding);
        if (kontakt==null) {
            Log.d("Meldingssender/gratuler","Kontakt kontakt var null");
            sendt = false;
            return sendt;
        }
        if(melding.equals("")){
            Log.d("Meldingssender/gratuler","Meldingen er tom.");
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.melding_ikke_sendt), Toast.LENGTH_SHORT).show();
            sendt = false;
            return sendt;
        }
        smsMan.sendTextMessage(Integer.toString(kontakt.getTlf()), null, melding, null, null);
        Log.d("Meldingssender/gratuler", "Melding ble sendt med suksess, til " + kontakt.getNavn());

        sendt = true;
        return sendt;
    }

    private String readFromFile() {
        try {
            InputStream inputStream = openFileInput("melding.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                melding = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        if(melding == null){
            melding = "";
        }
        return melding;
    }

    public void sendMeld() {
        String meld = getResources().getString(R.string.grat_noti_start) +"\r\n";
        dagens = db.finnBursdag();
        if(!dagens.isEmpty()){

            for(Kontakt k : dagens) {
                gratuler(k);
                meld += k.getNavn() + "\r\n";
            }

            meld += getResources().getString(R.string.grat_noti_slutt);

            if(sendt){
                NotificationManager nM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Intent inte = new Intent(this, Startskjerm.class);
                PendingIntent pI = PendingIntent.getActivity(this, 0, inte, 0);

                Notification.Builder build = new Notification.Builder(this);
                build.setContentTitle(getResources().getString(R.string.app_name))
                        .setContentText(meld)
                        .setSmallIcon(R.drawable.crown)
                        .setContentIntent(pI).build();

                Notification storTekst = new Notification.BigTextStyle(build)
                        .bigText(meld).build();

                storTekst.flags |= Notification.FLAG_AUTO_CANCEL;
                nM.notify(0, storTekst);
            }

        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.ingen_kontakt), Toast.LENGTH_SHORT).show();
        }
    }
}
