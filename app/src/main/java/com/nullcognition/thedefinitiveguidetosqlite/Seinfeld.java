package com.nullcognition.thedefinitiveguidetosqlite;

public class Seinfeld extends android.app.Activity {

  private android.widget.TextView output;
  private MyDatabaseHelper        mySeinfeldDBHelper;
  //this boolean is mainly to test fresh database creation without the foods.db asset
  private boolean                 useFallback;

  
  
  public void onCreate(android.os.Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.food_list);
	mySeinfeldDBHelper = new MyDatabaseHelper(this);
	try{
	  mySeinfeldDBHelper.createDatabase();
	}
	catch(java.io.IOException e){
	  e.printStackTrace();
	}
	mySeinfeldDBHelper.openDatabase();
	fillData();
  }

//  @Override
//  public void onCreate(android.os.Bundle mySavedState){
//	super.onCreate(mySavedState);
//	setContentView(R.layout.main);
//	this.output = (android.widget.TextView)this.findViewById(R.id.out_text);
//
//	this.mySeinfeldDBHelper = new MyDatabaseHelper(this);
//	this.useFallback = false;
//
//	try{
//	  this.mySeinfeldDBHelper.openDatabase();
//	}
//	catch(android.database.SQLException e){
//	  this.useFallback = true;
//	}
//
//	// if something goes wrong using foods.db asset, revert to creating a simplified version for demo
//	if(this.useFallback){
//	  //this.mySeinfeldDBHelper.deleteFoods();
//	  //this.mySeinfeldDBHelper.insert(1,"Bagels");
//	  //this.mySeinfeldDBHelper.insert(1,"Bagels, raisin");
//	  //this.mySeinfeldDBHelper.insert(1,"Bavarian Cream Pie");
//	}
//
//	java.util.List<String> names = this.mySeinfeldDBHelper.selectFoods();
//	StringBuilder myString = new StringBuilder();
//	for(String name : names){
//	  myString.append(name + "\n");
//	}
//	this.output.setText(myString.toString());
//  }

  private void fillData(){
	android.database.Cursor foodCursor = mySeinfeldDBHelper.fetchAllFoods();
	startManagingCursor(foodCursor);
// Create an array for our food names
	String[] from = new String[]{MyDatabaseHelper.FOODS_NAME_FIELD};
// Create an array of fields for binding to
	int[] to = new int[]{R.id.text1};
	android.widget.SimpleCursorAdapter foods = new android.widget.SimpleCursorAdapter(this, R.layout.food_row, foodCursor, from, to);
	android.widget.ListView listView = (android.widget.ListView)findViewById(com.nullcognition.thedefinitiveguidetosqlite.R.id.list);
	listView.setAdapter(foods);
  }
}











