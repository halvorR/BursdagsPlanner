package s172589.bursdagsplanner;
// Endret navn på Startskjerm.

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Startskjerm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startskjerm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_startskjerm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Context c = getApplicationContext();
        int dur = Toast.LENGTH_SHORT;

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            Toast toast = Toast.makeText(c,"Settings klikket",dur);
            toast.show();
            return true;
        }
        if (id == R.id.lag_ny) {
            Toast toast = Toast.makeText(c,"Lag ny klikket",dur);
            toast.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
