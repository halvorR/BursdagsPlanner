package s172589.bursdagsplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Roger on 15.09.2015.
 */

public class DBHandler extends SQLiteOpenHelper {

        static String TABLE_KONTAKTER="Kontakter";
        static String KEY_NAME= "Navn";
        static String KEY_PH_NO= "Telefon";
        static String KEY_DATE="Fodselsdato";
        static int DATABASE_VERSION=2;
        static String DATABASE_NAME= "Telefonkontakter";

        public DBHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String LAG_TABELL = "CREATE TABLE " + TABLE_KONTAKTER +"(" + KEY_PH_NO + " INTEGER PRIMARY KEY,"
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
                    kontakt.setTlf(Integer.parseInt(cursor.getString(0)));
                    kontakt.setNavn(cursor.getString(1));
                    kontakt.setDato(cursor.getString(2));
                    kontaktListe.add(kontakt);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return kontaktListe;
        }

    public void slettKontakt(Kontakt k) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_KONTAKTER, KEY_PH_NO + " =?", new String[]{String.valueOf(k.getTlf())});
        db.close();
    }


    public Kontakt finnKontakt(int id) {
        Kontakt	kontakt	= new Kontakt();
        String selectQuery = "SELECT * FROM " +	TABLE_KONTAKTER + " WHERE " + KEY_PH_NO + " = " + id;
        SQLiteDatabase db =	this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if	(cursor.moveToFirst())	{
                kontakt.setTlf(Integer.parseInt(cursor.getString(0)));
                kontakt.setNavn(cursor.getString(1));
                kontakt.setDato(cursor.getString(2));
                cursor.close();
                db.close();
                return kontakt;
        }
        cursor.close();
        db.close();
        return kontakt;
    }

    public List<Kontakt> finnBursdag(){
        List<Kontakt> alleKontakter = finnAlleKontakter();
        List<Kontakt> dagensBursdagsbarn = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Calendar dagens = Calendar.getInstance();
        format.setCalendar(dagens);

        String dagensF = format.format(dagens.getTime());

        for(Kontakt k : alleKontakter){
            try {
                if(k.getDato().substring(0,5).equals(dagensF.substring(0,5))){
                    dagensBursdagsbarn.add(k);
                    Log.d("FINNBURSDAG", "Dagens bursdagsbarn: " + dagensBursdagsbarn.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dagensBursdagsbarn;
    }
}
