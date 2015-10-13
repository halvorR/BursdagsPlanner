package s172589.bursdagsplanner;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Halvor on 13.10.2015.
 */
public class LeggTilNy extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leggtilny);
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
