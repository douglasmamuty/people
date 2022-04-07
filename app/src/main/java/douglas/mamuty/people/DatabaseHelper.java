package douglas.mamuty.people;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName";
    public static final String TABLE_NAME = "people";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table "+ TABLE_NAME +"(id integer primary key, name text,email text,birthday date)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(String name, String email, String birthday) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("email", email);
            contentValues.put("birthday", birthday);
            db.insert(TABLE_NAME, null, contentValues);
            return true;
        } catch (SQLException e){
            return  false;
        }

    }

    public ArrayList<PeopleModel> list() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<PeopleModel> peopleModelsArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (c.moveToFirst()) {
            do {

                // on below line we are adding the data from cursor to our array list.
                peopleModelsArrayList.add(new PeopleModel(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3)
                ));
            } while (c.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        c.close();
        return peopleModelsArrayList;
    }

    public PeopleModel get(int id){
        PeopleModel people;
        SQLiteDatabase db = this.getReadableDatabase();

        //Query
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(id)});
        if(cursor.getCount()<1) // Name Not Exist
        {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        people = new PeopleModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );

        cursor.close();
        return people;
    }

    public boolean update(PeopleModel people){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", people.getName());
            contentValues.put("email", people.getEmail());
            contentValues.put("birthday", people.getBirthday());
            db.update(TABLE_NAME, contentValues, "id = ?",new String[]{String.valueOf(people.getId())});
            return true;
        } catch (SQLException e){
            return false;
        }

    }

    public boolean delete(int id){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME, "id = ?",new String[]{String.valueOf(id)});
            return true;
        } catch (SQLException e){
            return false;
        }

    }

}