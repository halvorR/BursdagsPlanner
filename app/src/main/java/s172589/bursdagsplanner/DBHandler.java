package s172589.bursdagsplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roger on 15.09.2015.
 */

public class DBHandler extends SQLiteOpenHelper {

        static String TABLE_KONTAKTER="Kontakter";
        static String KEY_ID="_ID";
        static String KEY_NAME= "Navn";
        static String KEY_PH_NO= "Telefon";
        static String KEY_DATE="Fødselsdato";
        static int DATABASE_VERSION=1;
        static String DATABASE_NAME= "Telefonkontakter";

        public DBHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String LAG_TABELL = "CREATE TABLE " + TABLE_KONTAKTER +"(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                    + KEY_DATE + "TEXT," + KEY_PH_NO + " INTEGER" + ")";
            Log.d("SQL", LAG_TABELL);
            db.execSQL(LAG_TABELL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_KONTAKTER);
            onCreate(db);
        }

        public void leggTilKontakt(String n,int t,String d){
            // Dato vil sannsynligvis sendes med på annen måte, venter på litt research.
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, n);
            values.put(KEY_PH_NO, t);
            values.put(KEY_DATE, d);
            db.insert(TABLE_KONTAKTER, null, values);
            db.close();

        }
        public	List<Kontakt>	finnAlleKontakter(){
            List<Kontakt> kontaktListe = new ArrayList<Kontakt>();
            String selectQuery = "SELECT * FROM " +	TABLE_KONTAKTER;
            SQLiteDatabase db =	this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if	(cursor.moveToFirst())	{
                do{
                    Kontakt	kontakt	= new Kontakt();
                    kontakt.set_ID(Integer.parseInt(cursor.getString(0)));
                    kontakt.setNavn(cursor.getString(1));
                    kontakt.setTlf(Integer.parseInt(cursor.getString(2)));
                    kontaktListe.add(kontakt);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return kontaktListe;
        }

    }
