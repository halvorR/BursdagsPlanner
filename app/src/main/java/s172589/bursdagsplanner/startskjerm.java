package s172589.bursdagsplanner;
// Endret navn på Startskjerm.

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

public class Startskjerm extends AppCompatActivity implements Serializable {

    ListView listViewDagens, listViewAlle;
    DBHandler db = new DBHandler(this);
    ArrayAdapter ListAdapter;
    String tlf;

    public void mekkListe() {
        listViewDagens = (ListView) findViewById(R.id.listeDagens);
        List<Kontakt> dagens = db.finnBursdag();
        if(dagens.size()>0) // check if list contains items.
        {
            ListAdapter = new CustomAdapter(this,dagens,R.layout.custom_listview_dagens);
            listViewDagens.setAdapter(ListAdapter);
        } else {
            Kontakt k = new Kontakt("",getString(R.string.IngenBurs),0);
            dagens.add(k);
            ListAdapter = new CustomAdapter(this,dagens,R.layout.custom_listview_dagens);
            listViewDagens.setAdapter(ListAdapter);
        }
        listViewAlle = (ListView) findViewById(R.id.listeAlle);
        List<Kontakt> alle = db.finnAlleKontakter();
        if(alle.size()>0) // check if list contains items.
        {
            ListAdapter = new CustomAdapter(this,alle,R.layout.custom_listview_alle);
            listViewAlle.setAdapter(ListAdapter);
        }
        else
        {
            Toast.makeText(Startskjerm.this,"Ingen kontakter å vise",Toast.LENGTH_LONG).show();
            listViewAlle.setAdapter(null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startskjerm);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setIcon(R.drawable.crown);
        mekkListe();
        listViewAlle.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                Kontakt k = (Kontakt) listViewAlle.getItemAtPosition(pos);
                tlf = Integer.toString(k.getTlf());
                longClickMessage(k.getNavn()+"\r\n"+tlf+"\r\n"+k.getDato());
                return true;
            }
        });
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
        int id = item.getItemId();
        Context c = getApplicationContext();
        int dur = Toast.LENGTH_SHORT;

        switch(id) {
            case R.id.lag_ny:
                Toast toast = Toast.makeText(c,"Lag ny klikket",dur);
                toast.show();

                Intent i = new Intent(this, LeggTilNy.class);
                startActivity(i);

                return true;
            case R.id.settings:
                toast = Toast.makeText(c,"endreTid klikket",dur);
                toast.show();

                Intent t = new Intent(this, Settings.class);
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
    public void longClickMessage(String navn) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        final int tlfParsed = Integer.parseInt(tlf);
        final Kontakt k = db.finnKontakt(tlfParsed);
        final Context c = getBaseContext();
        builder1.setMessage(navn);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Avbryt",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder1.setNeutralButton("Edit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(c, LeggTilNy.class);
                        i.putExtra("Kontakt", (Serializable) k);
                        startActivity(i);
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton("Slett",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.slettKontakt(k);
                        mekkListe();
                        Toast.makeText(Startskjerm.this, k.getNavn() + " slettet.", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                });

        AlertDialog aDialog = builder1.create();
        aDialog.show();

        // avbryt
        Button avbrytIcon = aDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        // edit
        Button editIcon = aDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        // slett
        Button slettIcon = aDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        avbrytIcon.setCompoundDrawablesWithIntrinsicBounds(null, null, null,this.getResources().getDrawable(R.drawable.ic_cancel_black_18dp));
        avbrytIcon.setText("");
        editIcon.setCompoundDrawablesWithIntrinsicBounds(null, null, null, this.getResources().getDrawable(R.drawable.ic_mode_edit_black_18dp));
        editIcon.setText("");
        slettIcon.setCompoundDrawablesWithIntrinsicBounds(null, null, null, this.getResources().getDrawable(R.drawable.ic_delete_black_18dp));
        slettIcon.setText("");
    }

}
