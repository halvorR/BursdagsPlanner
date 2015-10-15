package s172589.bursdagsplanner;
// Endret navn på Startskjerm.

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Startskjerm extends AppCompatActivity {

    ListView listViewDagens, listViewAlle;
    DBHandler db = new DBHandler(this);
    ArrayAdapter ListAdapter;

    public void mekkListe() {
        listViewDagens = (ListView) findViewById(R.id.listeDagens);
        List<Kontakt> dagens = db.finnBursdag();
        if(dagens.size()>0) // check if list contains items.
        {
            List<String> navnListe = new ArrayList<>();
            for (Kontakt k : dagens) {
                navnListe.add(k.getNavn());
            }
            ListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, navnListe);
            listViewDagens.setAdapter(ListAdapter);
        }
        listViewAlle = (ListView) findViewById(R.id.listeAlle);
        List<Kontakt> alle = db.finnAlleKontakter();
        if(alle.size()>0) // check if list contains items.
        {
            List<String> navnListe = new ArrayList<>();
            for (Kontakt k : alle) {
                navnListe.add(k.getNavn());
            }
            ListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, navnListe);
            listViewAlle.setAdapter(ListAdapter);
        }
        else
        {
            Toast.makeText(Startskjerm.this,"Ingen kontakter å vise",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startskjerm);
        mekkListe();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_startskjerm, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mekkListe();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Context c = getApplicationContext();
        int dur = Toast.LENGTH_SHORT;

        switch(id) {
            case R.id.settings:
                Toast toast = Toast.makeText(c,"Settings klikket",dur);
                toast.show();
                return true;
            case R.id.lag_ny:
                toast = Toast.makeText(c,"Lag ny klikket",dur);
                toast.show();

                Intent i = new Intent(this, LeggTilNy.class);
                startActivity(i);

                return true;
            case R.id.endreTid:
                toast = Toast.makeText(c,"endreTid klikket",dur);
                toast.show();

                Intent t = new Intent(this, Tidsvelger.class);
                startActivity(t);

                return true;
            case R.id.melding:
                Intent m = new Intent(this, Meldingsvindu.class);
                startActivity(m);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
