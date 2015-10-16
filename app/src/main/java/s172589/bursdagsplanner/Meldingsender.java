package s172589.bursdagsplanner;

import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Roger on 16.10.2015.
 */
public class Meldingsender extends AppCompatActivity {
    Kontakt kontakt;
    String melding;
    SmsManager smsMan = SmsManager.getDefault();

    public Meldingsender(Kontakt kontakt) {
        this.kontakt = kontakt;
        readFromFile();
        smsMan.sendTextMessage(Integer.toString(kontakt.getTlf()),null,melding,null,null);
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
