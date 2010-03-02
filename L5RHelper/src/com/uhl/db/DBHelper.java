package com.uhl.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.uhl.calc.Roll;

//adapted from writeup at: http://www.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/
public class DBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "data.db3";
	private static final String DB_PATH = "/data/data/com.uhl/databases/";
	private SQLiteDatabase myDataBase;
	private final Context myContext;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
		myContext = context;
		try {
			this.createDataBase();
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			
			File f = new File(DB_PATH);
			if (!f.exists()) {
				f.mkdir();
			}
			
			
			this.getReadableDatabase();
			this.close();
			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");
			}
		}

	}

	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		onCreate(arg0);
	}
	//Is this the proper place for these?
	public void saveProfile(Profile profile){
		
		String insertString = "";
		String[] values = new String[0];
		
		if(profile.getId() == -1){
			//create
			insertString = "insert into Profiles values (null,?,?,?,?,?,?,?,?,?,?,?)";
			values = new String[] {String.valueOf(profile.getName()),String.valueOf(profile.getDefaultViewId()),
					String.valueOf(profile.getEarthRing()),String.valueOf(profile.getWaterRing()),String.valueOf(profile.getFireRing()),
					String.valueOf(profile.getAirRing()),String.valueOf(profile.getVoidRing()),String.valueOf(profile.getReflexes()),
					String.valueOf(profile.getAgility()),String.valueOf(profile.getLuck()),String.valueOf(profile.getGp())};
		}else
		{
			//update
			insertString = "update Profiles set name = ?, defaultViewId = ?, earth = ?, water = ?, fire = ?, air = ?, void = ?, reflexes = ?, agility = ?, luck = ?, gp = ?)";
			values = new String[] {String.valueOf(profile.getId()),
					String.valueOf(profile.getName()),String.valueOf(profile.getDefaultViewId()),
					String.valueOf(profile.getEarthRing()),String.valueOf(profile.getWaterRing()),String.valueOf(profile.getFireRing()),
					String.valueOf(profile.getAirRing()),String.valueOf(profile.getVoidRing()),String.valueOf(profile.getReflexes()),
					String.valueOf(profile.getAgility()),String.valueOf(profile.getLuck()),String.valueOf(profile.getGp())};
		}		
		this.openDataBase();
		SQLiteDatabase db2 = this.getWritableDatabase();
		db2.execSQL(insertString, values);						
		this.close();
	}
	
	public Profile loadProfile(int id){
		
		this.openDataBase();
		SQLiteDatabase db2 = this.getReadableDatabase();
		Cursor cursor = db2.rawQuery(
				"select _id, name, defaultViewId, earth, water, fire, air, void, reflexes, agility, luck, gp from Profiles where _id = ?",
				new String[] { String.valueOf(id)});
		cursor.moveToFirst();
		this.close();
		Profile result = new Profile(cursor);
		return result;
		
	}

	public Cursor getHistogram(Roll roll) {
		this.openDataBase();
		SQLiteDatabase db2 = this.getReadableDatabase();
		Cursor cursor = db2.rawQuery(
				"select histogram from rolls where rolled = ? and kept = ? and gp = ? and luck = ?",
				new String[] { String.valueOf(roll.getRolled()),
						String.valueOf(roll.getKept()),
						String.valueOf(roll.getGp()),
						String.valueOf(roll.getLuck())});
		cursor.moveToFirst();
		this.close();
		return cursor;
	}
	
	public Cursor GetProfiles(){
		this.openDataBase();
		SQLiteDatabase db2 = this.getReadableDatabase();
		Cursor cursor = db2.rawQuery("select _id, name from Profiles", new String[0]);
		cursor.moveToFirst();
		this.close();		
		return cursor;
	}

	public void deleteProfile(int id) {				
	
		String deleteString = "delete from Profiles where _id = ?";
		String[] values = new String[] {String.valueOf(id)};				
		this.openDataBase();
		SQLiteDatabase db2 = this.getWritableDatabase();
		db2.execSQL(deleteString, values);						
		this.close();		
	}	

}
