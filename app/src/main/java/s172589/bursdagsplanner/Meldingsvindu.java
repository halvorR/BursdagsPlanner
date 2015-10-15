package s172589.bursdagsplanner;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Halvor on 15.10.2015.
 */
public class Meldingsvindu extends AppCompatActivity {

    EditText meldingTekst;
    Button lagreMeld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meldingsvindu);

        lagreMeld = (Button)findViewById(R.id.lagreMeldingKnapp);
        lagreMeld.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                leggTil();
            }
        });
    }

    public void leggTil(){
        Context c = getApplicationContext();
        int dur = Toast.LENGTH_SHORT;

//
//        DBHandler db = new DBHandler(this);
//        db.leggTilKontakt(new Kontakt(datoFelt.getText().toString(),navnFelt.getText().toString(),telefonFeltInt));
//
//        List<Kontakt> kontakter = db.finnAlleKontakter();
//        for (Kontakt k : kontakter) {
//            String log = "\r\nLeggTilNy-klassen rapporterer at ny kontakt blir lagret, med data:" +
//                    "\r\nId: "+ k.get_ID() + "\r\nNavn: " + k.getNavn() + "\r\nTelefonnr: " + k.getTlf() + "\r\nDato: " + k.getDato();
//            Log.d("Navn: ", log);
//        }
//        Toast toast = Toast.makeText(c, navnFelt.getText().toString()+" lagret! Hurra!",dur);
//        toast.show();
//        this.finish();
    }
}
