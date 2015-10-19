package s172589.bursdagsplanner;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Halvor on 13.10.2015.
 */
public class LeggTilNy extends AppCompatActivity implements Serializable {

    private String navn, dato;
    private int telefonNr,year,month,day;
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

        ImageButton lagreKnapp = (ImageButton) findViewById(R.id.lagreK);
        ImageButton avbrytKnapp = (ImageButton) findViewById(R.id.avbrytK);
        lagreKnapp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                leggTil();
            }
        });
        avbrytKnapp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        datoFelt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                String datoFeltTekst = datoFelt.getText().toString();
                Log.v("datoFeltTekst",datoFeltTekst);
                try {
                    String[] split = datoFeltTekst.split("-");
                    Log.v("Datotekst",datoFeltTekst);
                    day = Integer.parseInt(split[0]);
                    month = Integer.parseInt(split[1]);
                    year = Integer.parseInt(split[2]);
                    Log.v("Mine splits er","day "+day +", month "+month+", year "+ year);

                } catch (Exception e) {
                    year = 1990;
                    month = c.get(Calendar.MONTH);
                    month++;
                    day = c.get(Calendar.DAY_OF_MONTH);
                }
                DatePickerDialog dialog = new DatePickerDialog(LeggTilNy.this,
                        new Datodialog(), year, month-1, day);
                dialog.show();
            }
        });
    }

    class Datodialog implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int month,
                              int day) {
            int pickedYear = year;
            int pickedMonth = month;
            int pickedDay = day;
            datoFelt.setText(new StringBuilder()
                    .append(pickedDay).append("-").append(pickedMonth + 1).append("-")
                    .append(pickedYear));
            System.out.println(datoFelt.getText().toString());
        }
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