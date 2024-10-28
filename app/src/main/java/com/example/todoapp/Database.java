package com.example.todoapp;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static final String db_Name="ToDo_DB";
    private static final String table_Name="ToDo_Table";
    private static final String col1="Id";
    private static final String col2="Task";
    private static final String col3="Status";

    public Database(@Nullable Context context) {
        super(context,db_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + table_Name + "(Id INTEGER PRIMARY KEY AUTOINCREMENT, Task TEXT,Status INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_Name );
        onCreate(db);
    }

    public void insertTask(ToDoModel modelobj){
        db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col2,modelobj.getTask());
        contentValues.put(col3,0);
        db.insert(table_Name,null,contentValues);
    }

    public void updateTask(int id,String task){
        db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col2,task);
        db.update(table_Name,contentValues,"Id=?",new String[]{String.valueOf(id)});
    }

    public void updateStatus(int id,int status){
        db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col3,status);
        db.update(table_Name,contentValues,"Id=?",new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id){
        db=this.getWritableDatabase();
        db.delete(table_Name,"Id=?",new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<ToDoModel>getAllTasks(){
        db=this.getWritableDatabase();
        Cursor cur=null;
        List<ToDoModel>modelList=new ArrayList<>();
        db.beginTransaction();
        try{
            cur=db.query(table_Name,null,null,null,null,null,
                    null);
            if(cur!=null){
                if(cur.moveToFirst()){
                    do{
                        ToDoModel task=new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndex(col1)));
                        task.setTask(cur.getString(cur.getColumnIndex(col2)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(col3)));
                        modelList.add(task);
                    }while(cur.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            db.close();
        }
        return modelList;
    }
}
