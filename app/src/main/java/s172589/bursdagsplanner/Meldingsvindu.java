package s172589.bursdagsplanner;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Halvor on 15.10.2015.
 */
public class Meldingsvindu extends AppCompatActivity {

    private EditText meldingTekstFelt;
    private ImageButton lagreMeld,avbrytMeld;
    private String MY_FILE_NAME = "melding.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meldingsvindu);

        meldingTekstFelt = (EditText) findViewById(R.id.meldingTekst);
        lagreMeld = (ImageButton)findViewById(R.id.lagreMeld);
        avbrytMeld = (ImageButton)findViewById(R.id.avbrytMeld);
        lyttere();
    }

    public void leggTil() throws FileNotFoundException{
        Context c = getApplicationContext();
        int dur = Toast.LENGTH_SHORT;

        Log.d("LEGGTIL","leggTil() kalt, meldinga er: " + meldingTekstFelt.getText().toString());

        try {
            FileOutputStream fileout = openFileOutput(MY_FILE_NAME, Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(meldingTekstFelt.getText().toString());
            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void lyttere() {

        lagreMeld = (ImageButton) findViewById(R.id.lagreMeld);
        avbrytMeld = (ImageButton) findViewById(R.id.avbrytMeld);

        lagreMeld.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                try {
                    leggTil();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
        avbrytMeld.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
