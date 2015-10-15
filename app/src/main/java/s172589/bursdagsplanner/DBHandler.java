package s172589.bursdagsplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Roger on 15.09.2015.
 */

public class DBHandler extends SQLiteOpenHelper {

        static String TABLE_KONTAKTER="Kontakter";
        static String KEY_ID="_ID";
        static String KEY_NAME= "Navn";
        static String KEY_PH_NO= "Telefon";
        static String KEY_DATE="Fodselsdato";
        static int DATABASE_VERSION=1;
        static String DATABASE_NAME= "Telefonkontakter";

        public DBHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String LAG_TABELL = "CREATE TABLE " + TABLE_KONTAKTER +"(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PH_NO + " INTEGER,"
                    + KEY_NAME + " TEXT," + KEY_DATE + " TEXT" + ")";
            Log.d("SQL", LAG_TABELL);
            db.execSQL(LAG_TABELL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_KONTAKTER);
            onCreate(db);
        }

        public void leggTilKontakt(Kontakt k){
            // Dato vil sannsynligvis sendes med på annen måte, venter på litt research.
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(KEY_NAME, k.getNavn());
            values.put(KEY_DATE, k.getDato());
            values.put(KEY_PH_NO, k.getTlf());
            db.insert(TABLE_KONTAKTER, null, values);
            db.close();
        }
        public List<Kontakt> finnAlleKontakter(){
            List<Kontakt> kontaktListe = new ArrayList<Kontakt>();
            String selectQuery = "SELECT * FROM " +	TABLE_KONTAKTER;
            SQLiteDatabase db =	this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if	(cursor.moveToFirst())	{
                do{
                    Kontakt	kontakt	= new Kontakt();
                    kontakt.set_ID(Integer.parseInt(cursor.getString(0)));
                    kontakt.setTlf(Integer.parseInt(cursor.getString(1)));
                    kontakt.setNavn(cursor.getString(2));
                    kontakt.setDato(cursor.getString(3));
                    kontaktListe.add(kontakt);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return kontaktListe;
        }

    public int oppdaterKontakt(Kontakt k) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, k.getNavn());
        values.put(KEY_PH_NO, k.getTlf());
        int endret = db.update(TABLE_KONTAKTER, values, KEY_ID + "= ?", new String[]{String.valueOf(k.get_ID())});
        db.close();
        return endret;
    }

    public void slettKontakt(Kontakt k) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_KONTAKTER, KEY_ID + " =?", new String[]{String.valueOf(k.get_ID())});
        db.close();
    }


    public Kontakt finnKontakt(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_KONTAKTER, new String[]{
                KEY_ID, KEY_NAME, KEY_PH_NO
        }, KEY_ID + "=?", new String[]{
                String.valueOf(id)
        }, null, null, null, null);
        if(c != null)
            c.moveToFirst();
        Kontakt k = new Kontakt(Integer.parseInt(c.getString(0)),c.getString(1), Integer.parseInt(c.getString(2)));
        c.close();
        db.close();

        return k;
    }

    public List<Kontakt> finnBursdag(){
        List<Kontakt> alleKontakter = finnAlleKontakter();
        List<Kontakt> dagensBursdagsbarn = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Calendar dagens = Calendar.getInstance();
        format.setCalendar(dagens);

        String dagensF = format.format(dagens.getTime());
        Log.d("FINNB", dagensF);

        for(Kontakt k : alleKontakter){
            try {
                if(k.getDato().equals(dagensF)){
                    dagensBursdagsbarn.add(k);
                    Log.d("FINNK", dagensBursdagsbarn.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dagensBursdagsbarn;
    }
}
