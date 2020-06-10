package jg.example.bazadanych;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.Cursor;

import java.util.Random;

public class ZarzadzajDanymi {

    private SQLiteDatabase db;

    public static final String TABLE_ROW_ID = "_id";
    public static final String TABLE_ROW_PESEL = "pesel";
    public static final String TABLE_ROW_NAME = "name";
    public static final String TABLE_ROW_SURNAME = "surname";
    public static final String TABLE_ROW_AGE = "age";
    public static final String TABLE_ROW_SEX = "sex";

    private static final String DB_NAME = "address_book_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_N_AND_A = "names_and_addresses";

    Random gen = new Random();

    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
        public CustomSQLiteOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            String newTableQueryString = "create table "
                    + TABLE_N_AND_A + " ("
                    + TABLE_ROW_ID
                    + " integer primary key autoincrement not null,"
                    + TABLE_ROW_PESEL
                    + " text not null,"
                    + TABLE_ROW_NAME
                    + " text not null,"
                    + TABLE_ROW_SURNAME
                    + " text not null,"
                    + TABLE_ROW_AGE
                    + " text not null,"
                    + TABLE_ROW_SEX
                    + " text not null) ;";
            db.execSQL(newTableQueryString);

        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
        }
    }

    public ZarzadzajDanymi(Context context) {
        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
        db = helper.getWritableDatabase();
    }
    public void insert(int pesel, String name, String surname, int age, String sex){
        // pola pesel i wiek sa typu int, a ich editBoxy przyjmuja numery, nie ma wiec potrzeby
        // sprawdzania, czy ktoś wprowadził doń string (i vice versa dla pól string)
    if (pesel == 0) {
    pesel = gen.nextInt(10000000-1) + 1;
    if(sex.charAt(1) == 'k' || sex.charAt(1) == 'z' && pesel%2 != 0) pesel += 1;
    }
    if (name == "") {
        StringBuilder sb = new StringBuilder(10);
        String litery = "abcdefghijklmnopqrstuvxyz";
        for (int i = 0; i < 10; i++) {
            int index = (int)(litery.length() * Math.random());
            sb.append(litery.charAt(index));
        }
        name = sb.toString();
    }
    if (surname == ""){
        StringBuilder sb = new StringBuilder(10);
        String litery = "abcdefghijklmnopqrstuvxyz";
        for (int i = 0; i < 10; i++) {
            int index = (int)(litery.length() * Math.random());
            sb.append(litery.charAt(index));
        }
        surname = sb.toString();
    }
    if (age == 0) {
age = gen.nextInt(99) + 1;
    }
    if (sex == ""){
int pomocnicza = gen.nextInt(2);
if (pomocnicza == 0) sex = "kobieta";
   else sex = "mezczyzna";
    }

        String query = "INSERT INTO " + TABLE_N_AND_A + " (" + TABLE_ROW_PESEL + ", "
                + TABLE_ROW_NAME + ", " + TABLE_ROW_SURNAME + ", " + TABLE_ROW_AGE +
                ", " + TABLE_ROW_SEX + ") " +
                "VALUES (" + "'" + pesel + "'" + ", " + "'" + name + "'" + ", " + "'" + surname + "'" + ", " + "'" +
        age + "'" + ", " + "'" + sex +"'" + ") ;";
        Log.i("insert() = ", query);
        db.execSQL(query);

    }
    public void delete(String name){
        String query = "DELETE FROM "+ TABLE_N_AND_A + " WHERE " + TABLE_ROW_NAME +
                " =  '" + name + "';";
        Log.i("delete() = ", query);
        db.execSQL(query);
    }

    public Cursor selectAll() {
        Cursor c = db.rawQuery("SELECT *" + " FROM " + TABLE_N_AND_A, null);
        return c;
    }

    public Cursor searchName(String name) {
       String query = "SELECT " + TABLE_ROW_ID + ", " + TABLE_ROW_PESEL + ", "+ TABLE_ROW_NAME + ", " + TABLE_ROW_SURNAME +
               ", "+ TABLE_ROW_AGE + ", " + TABLE_ROW_SEX + " FROM " + TABLE_N_AND_A + " WHERE " + TABLE_ROW_NAME + " = '" + name + "';";
       Log.i("searchName() = ", query);
       Cursor c = db.rawQuery(query,null);
       return c;

    }


}
