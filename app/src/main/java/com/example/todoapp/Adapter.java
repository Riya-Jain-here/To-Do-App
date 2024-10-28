package com.example.todoapp;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolderClass> {
    private List<ToDoModel> listobjj;
    private MainActivity mainActivity;
    private Database dbobjj;

    public Adapter(Database dbobjj,MainActivity mainActivity) {
        this.dbobjj = dbobjj;
        this.mainActivity=mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        return new MyViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderClass holder, int position) {
        final ToDoModel item=listobjj.get(position);
        holder.checkBoxobj.setText(item.getTask());
        holder.checkBoxobj.setChecked(toBoolean(item.getStatus()));
        holder.checkBoxobj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    dbobjj.updateStatus(item.getId(),1);
                }
                else{
                    dbobjj.updateStatus(item.getId(),0);
                }
            }
        });
    }
    public Boolean toBoolean(int num){
        return num!=0;
    }

    public Context getContext(){
        return  mainActivity;
    }

    public void setTask(List<ToDoModel>listobjj){
        this.listobjj=listobjj;
        notifyDataSetChanged();
    }

    public void deleteTask(int position){
        ToDoModel item=listobjj.get(position);
        dbobjj.deleteTask(item.getId());
        listobjj.remove(position);
        notifyItemRemoved(position);
    }

    public void editTask(int position){
        ToDoModel item=listobjj.get(position);
        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTask());

        AddNewTask newTask=new AddNewTask();
        newTask.setArguments(bundle);
        newTask.show(mainActivity.getSupportFragmentManager(), newTask.getTag());
    }


    @Override
    public int getItemCount() {
        if(listobjj==null) {
            return 0;
        }
        return listobjj.size();
    }

    public static class MyViewHolderClass extends RecyclerView.ViewHolder{
        CheckBox checkBoxobj;
        public MyViewHolderClass(@NonNull View itemView) {
            super(itemView);
            checkBoxobj=itemView.findViewById(R.id.checkbox);
        }
    }
}
