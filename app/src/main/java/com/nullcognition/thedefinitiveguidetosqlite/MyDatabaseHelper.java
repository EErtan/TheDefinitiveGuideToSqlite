package com.nullcognition.thedefinitiveguidetosqlite;

public class MyDatabaseHelper {

  private static final String DBNAME     = "foods.db";
  private static final String DBPATH     = "/data/data/com.nullcognition.thedefinitiveguidetosqlite/databases/";
  private static final String TABLE_NAME = "foods";
  private android.content.Context                 myContext;
  private android.database.sqlite.SQLiteDatabase  myDatabase;
  private android.database.sqlite.SQLiteStatement myInsertStatement;
  private static final String FOODINSERT = "insert into " + TABLE_NAME + "(type_id, name) values (?, ?)";

  public static final String FOODS_NAME_FIELD = "name";


  //constructor
  public MyDatabaseHelper(android.content.Context context){

	this.myContext = context;
	com.nullcognition.thedefinitiveguidetosqlite.MyDatabaseHelper.OpenHelper openHelper = new com.nullcognition.thedefinitiveguidetosqlite.MyDatabaseHelper.OpenHelper(
	  this.myContext);

	try{
	  this.createDatabase();
	}
	catch(java.io.IOException e){
	  //throw new Error("Failed to create Seinfeld database");
	}

	this.myDatabase = openHelper.getWritableDatabase();
	this.myInsertStatement = this.myDatabase.compileStatement(FOODINSERT);
  }

  //Insert method for recreating foods table if needed.
  public long insert(Integer type_id, String name){

	this.myInsertStatement.bindLong(1, type_id);
	this.myInsertStatement.bindString(2, name);

	return this.myInsertStatement.executeInsert();
  }

  //Delete method for deleting all rows
  public void deleteFoods(){
	this.myDatabase.delete(TABLE_NAME, null, null);
  }

  public android.database.Cursor fetchAllFoods(){

	return myDatabase.rawQuery("select name from foods order by name", null);
  }

  public java.util.List<String> selectFoods(){

	java.util.List<String> list = new java.util.ArrayList<String>();

	android.database.Cursor cursor = this.myDatabase.query(TABLE_NAME, new String[]{"name"}, null, null, null, null, null);

	if(cursor.moveToFirst()){
	  do{
		list.add(cursor.getString(0));
	  }
	  while(cursor.moveToNext());
	}
	if(cursor != null && ! cursor.isClosed()){
	  cursor.close();
	}
	return list;
  }

  //create an empty db, and replace with our chosen db
  public void createDatabase() throws java.io.IOException{

	if(! checkDatabase()){
	  com.nullcognition.thedefinitiveguidetosqlite.MyDatabaseHelper.OpenHelper openHelper = new com.nullcognition.thedefinitiveguidetosqlite.MyDatabaseHelper.OpenHelper(
		this.myContext);
	  this.myDatabase = openHelper.getWritableDatabase();

	  try{
		copyDatabase();
	  }
	  catch(java.io.IOException e){
		throw new Error("Error copying database from system assets");
	  }
	}
  }

  //Check if our database already exists
  private boolean checkDatabase(){

	android.database.sqlite.SQLiteDatabase checkableDatabase = null;

	try{
	  checkableDatabase = android.database.sqlite.SQLiteDatabase
		.openDatabase(DBPATH + DBNAME, null, android.database.sqlite.SQLiteDatabase.OPEN_READONLY);
	}
	catch(android.database.sqlite.SQLiteException e){
	  //our database doesn't exist, so we'll return false below.
	}
	if(checkableDatabase != null){
	  checkableDatabase.close();
	}
	return checkableDatabase != null ? true : false;
  }

  //Copy our database from the Application's assets
//over the empty DB for use
  private void copyDatabase() throws java.io.IOException{

	java.io.InputStream myInput = myContext.getAssets().open(DBNAME);
	java.io.OutputStream myOutput = new java.io.FileOutputStream(DBPATH + DBNAME);

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
	myDatabase = android.database.sqlite.SQLiteDatabase.openDatabase(DBPATH + DBNAME, null, android.database.sqlite.SQLiteDatabase.OPEN_READWRITE);
  }

  private static class OpenHelper extends android.database.sqlite.SQLiteOpenHelper {

	OpenHelper(android.content.Context context){
	  super(context, DBNAME, null, 1);
	}

	@Override
	public void onCreate(android.database.sqlite.SQLiteDatabase db){
	  // if something goes wrong using foods.db asset, revert to creating a simplified version for demo
	  //db.execSQL("create table " + TABLE_NAME + "(id integer primary key, type_id integer, name text)");
	}

	@Override
	public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion){
	  db.execSQL("drop table if exists " + TABLE_NAME);
	  onCreate(db);
	}
  }
}
