package com.nullcognition.thedefinitiveguidetosqlite;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ActivityMain extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	android.content.Intent sein = new android.content.Intent(this, Seinfeld.class);
	sein.putExtra("sein", "value");
	startActivity(sein);
	
	//createDB(); // creating database should be off the main thread


	// SQLiteQueryBuilder also protects against injection attacks
	// when using SQLiteQueryBuilder a seperate command must be used to select which table to operate on
	// pg 293
//	These include setDistinct(), for indicating the SQL should use the distinct keyword, and setProjectionMap
//	  (java.util.Map <String, String> columnMap), which controls the aliasing of columns and column disambiguation.
	// public void setTables (String inTables) // takes in inTables = "table1, table2, table3";
  }

  private void createDB(){
	SQLiteOpenHelperClass mySeinfeldDBHelper;
	mySeinfeldDBHelper = new SQLiteOpenHelperClass(this);
	try{
	  mySeinfeldDBHelper.createDatabase();
	}
	catch(java.io.IOException e){
	  throw new Error("Failed to create Seinfeld database");
	}
	try{
	  mySeinfeldDBHelper.openDatabase();
	}
	catch(android.database.SQLException e){
	  throw e;
	}
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu){
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.menu_activity_main, menu);
	return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item){
	// Handle action bar item clicks here. The action bar will
	// automatically handle clicks on the Home/Up button, so long
	// as you specify a parent activity in AndroidManifest.xml.
	int id = item.getItemId();

	//noinspection SimplifiableIfStatement
	if(id == R.id.action_settings){
	  return true;
	}

	return super.onOptionsItemSelected(item);
  }
}
