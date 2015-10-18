package s172589.bursdagsplanner;

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

/**
 * Created by Roger on 16.10.2015.
 */
public class Meldingsender extends Service {
    Kontakt kontakt;
    String melding;
    SmsManager smsMan = SmsManager.getDefault();

    @Override
    public IBinder onBind(Intent arg0) { return null; }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        test();
        return super.onStartCommand(intent, flags, startId);
    }

    public void test() {
        Toast.makeText(getApplicationContext(), "Sender melding", Toast.LENGTH_SHORT).show();
        Log.d("MELDINGSENDER", "I meldingsender, sjekker hver gang jeg kjøres");
    }
    public boolean gratuler(Kontakt kontakt) {
        this.kontakt = kontakt;
        readFromFile();
        Log.d("Meldingssender/gratuler", "Leser melding. Inputstream:\r\n"+melding);
        if (kontakt==null || melding.equals("")) {
            Log.d("Meldingssender/gratuler","Kontakt kontakt var null, eller så var meldingen tom.");
            return false;
        }
        smsMan.sendTextMessage(Integer.toString(kontakt.getTlf()),null,melding,null,null);
        Log.d("Meldingssender/gratuler", "Melding ble sendt med suksess, til "+kontakt.getNavn());
        return true;
    }

    private String readFromFile() {
        try {
            InputStream inputStream = openFileInput("melding.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                melding = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return melding;
    }
}
