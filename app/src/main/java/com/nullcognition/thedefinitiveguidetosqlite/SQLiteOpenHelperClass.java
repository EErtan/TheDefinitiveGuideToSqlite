package com.nullcognition.thedefinitiveguidetosqlite;

public class SQLiteOpenHelperClass extends android.database.sqlite.SQLiteOpenHelper {

  public static final String DATABASE_NAME    = "myDB.db";
  public static final int    DATABASE_VERSION = 1;
  private static      String DBPATH           = "/data/data/com.nullcognition.thedefinitiveguidetosqlite/databases/";

  public static final String TABLE_NAME = "table"; // can have more than one
  public static final String COL_KEY_ID = "_id";
  public static final String COL_NAME   = "_name";

  private       android.database.sqlite.SQLiteDatabase myDatabase;
  private final android.content.Context                context;

  public SQLiteOpenHelperClass(android.content.Context inContext){
	super(inContext, DATABASE_NAME, null, DATABASE_VERSION);
	context = inContext;
  }

  public void createDatabase() throws java.io.IOException{
	if(! checkDatabase()){
	  this.getWritableDatabase();
	  try{
		copyDatabase();
	  }
	  catch(java.io.IOException e){
		throw new Error("Error copying database from system assets");
	  }
	}
  }

  private boolean checkDatabase(){
	android.database.sqlite.SQLiteDatabase checkableDatabase = null;
	try{
	  checkableDatabase = android.database.sqlite.SQLiteDatabase
		.openDatabase(DBPATH + DATABASE_NAME, null, android.database.sqlite.SQLiteDatabase.OPEN_READONLY);
	}
	catch(android.database.sqlite.SQLiteException e){}
	if(checkableDatabase != null){
	  checkableDatabase.close();
	}
	return checkableDatabase != null ? true : false;
  }

  private void copyDatabase() throws java.io.IOException{
	java.io.InputStream myInput = context.getAssets().open(DATABASE_NAME);
	java.io.OutputStream myOutput = new java.io.FileOutputStream(DBPATH + DATABASE_NAME);

	byte[] buffer = new byte[1024];
	int length;

	while((length = myInput.read(buffer)) > 0){
	  myOutput.write(buffer, 0, length);
	}

	myOutput.flush();
	myOutput.close();
	myInput.close();
  }

  public void openDatabase() throws android.database.SQLException{
	myDatabase = android.database.sqlite.SQLiteDatabase
	  .openDatabase(DBPATH + DATABASE_NAME, null, android.database.sqlite.SQLiteDatabase.OPEN_READWRITE);
  }

  @Override
  public synchronized void close(){
	if(myDatabase != null){ myDatabase.close(); }
	super.close();
  }

  @Override
  public void onCreate(android.database.sqlite.SQLiteDatabase db){}

  @Override
  public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion){}
}
