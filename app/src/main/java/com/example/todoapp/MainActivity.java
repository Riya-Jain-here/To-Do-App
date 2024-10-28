package com.example.todoapp;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements onDialogClose {
    private RecyclerView recobj;
    private FloatingActionButton fabobj;
    private Database dbobj;
    private List<ToDoModel> toDoModelList;
    private Adapter adapterobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recobj=findViewById(R.id.recycle);
        fabobj=findViewById(R.id.fab);
        dbobj=new Database(MainActivity.this);
        toDoModelList =new ArrayList<>();
        adapterobj=new Adapter(dbobj,MainActivity.this);
        recobj.setHasFixedSize(true);
        recobj.setLayoutManager(new LinearLayoutManager(this));
        recobj.setAdapter(adapterobj);
        toDoModelList=dbobj.getAllTasks();
        Collections.reverse(toDoModelList);
        adapterobj.setTask(toDoModelList);

        fabobj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerViewTouch(adapterobj));
        itemTouchHelper.attachToRecyclerView(recobj);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        toDoModelList=dbobj.getAllTasks();
        Collections.reverse(toDoModelList);
        adapterobj.setTask(toDoModelList);
        adapterobj.notifyDataSetChanged();
    }
}