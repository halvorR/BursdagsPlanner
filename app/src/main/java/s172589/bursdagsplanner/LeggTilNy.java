package s172589.bursdagsplanner;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Halvor on 13.10.2015.
 */
public class LeggTilNy extends AppCompatActivity {

    private String navn, dato;
    private int telefonNr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leggtilny);

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
        Context c = getApplicationContext();
        int dur = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(c,"Bursdagsbarn lagret! Hurra!",dur);
        toast.show();

        this.finish();
    }

}
