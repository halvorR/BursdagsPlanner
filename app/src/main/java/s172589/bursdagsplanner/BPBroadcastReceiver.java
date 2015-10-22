package s172589.bursdagsplanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Halvor on 16.10.2015.
 */


public class BPBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            Toast.makeText(context, "I BroadcastReceiver etter boot", Toast.LENGTH_SHORT).show();
            Log.d("BOOT", "I BroadcastReceiver etter boot");

            Intent intEmt = new Intent(context, SjekkBursdag.class);
            context.startService(intEmt);
        } else {
            Toast.makeText(context, "I BroadcastReceiver etter switch", Toast.LENGTH_SHORT).show();
            Log.d("SWITCH", "I BroadcastReceiver etter switch");

            Intent intEmt = new Intent(context, SjekkBursdag.class);
            context.startService(intEmt);
        }

    }
}
