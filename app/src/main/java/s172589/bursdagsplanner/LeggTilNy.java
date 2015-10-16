package s172589.bursdagsplanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Halvor on 13.10.2015.
 */
public class LeggTilNy extends AppCompatActivity implements Serializable {

    private String navn, dato;
    private int telefonNr;
    private EditText navnFelt,telefonFelt,datoFelt;
    Kontakt k;
    DBHandler db = new DBHandler(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leggtilny);

        navnFelt = (EditText)findViewById(R.id.navn);
        telefonFelt = (EditText)findViewById(R.id.telefon);
        datoFelt = (EditText)findViewById(R.id.dato);

        Intent i = getIntent();
        k = (Kontakt)i.getSerializableExtra("Kontakt");
        if (k!=null) {
            Log.v("LeggTilNy", "Det ble sendt en kontakt med når vi gikk til LeggTilNy");
            navnFelt.setText(k.getNavn());
            telefonFelt.setText(Integer.toString(k.getTlf()));
            datoFelt.setText(k.getDato());
        }

        Button lagreKnapp = (Button) findViewById(R.id.lagreNy);
        lagreKnapp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                leggTil();
            }
        });
    }


    // Legg til person
    public void leggTil(){
        int telefonFeltInt = Integer.parseInt(telefonFelt.getText().toString());
        Context c = getApplicationContext();
        int dur = Toast.LENGTH_SHORT;

        if (k!=null) {
            db.slettKontakt(k);
            db.leggTilKontakt(new Kontakt(datoFelt.getText().toString(), navnFelt.getText().toString(),telefonFeltInt));
            Toast toast = Toast.makeText(c, navnFelt.getText().toString()+" oppdatert! Hurra!",dur);
            toast.show();
            this.finish();
        } else {

            db.leggTilKontakt(new Kontakt(datoFelt.getText().toString(), navnFelt.getText().toString(), telefonFeltInt));

            List<Kontakt> kontakter = db.finnAlleKontakter();
            for (Kontakt k : kontakter) {
                String log = "\r\nLeggTilNy-klassen rapporterer at ny kontakt blir lagret, med data:" +
                        "\r\nNavn: " + k.getNavn() + "\r\nTelefonnr: " + k.getTlf() + "\r\nDato: " + k.getDato();
                Log.d("Navn: ", log);
            }
            Toast toast = Toast.makeText(c, navnFelt.getText().toString() + " lagret! Hurra!", dur);
            toast.show();
            this.finish();
        }
    }

}