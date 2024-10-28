package com.example.todoapp;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG="AddNewTask";
    private EditText editTextobj;
    private Button btnobj;
    private Database dbobj;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.add_task,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextobj=view.findViewById(R.id.edtadd);
        btnobj=view.findViewById(R.id.btnsave);
        dbobj=new Database(getActivity());

        Boolean isUpdate=false;
        final Bundle bundle=getArguments();
        if(bundle!=null){
            isUpdate=true;
            String task= bundle.getString("task");
            editTextobj.setText(task);

            if(task.length()>0){
                btnobj.setEnabled(false);
            }
        }
        editTextobj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    btnobj.setEnabled(false);
                    btnobj.setBackgroundColor(Color.GRAY);
                }
                else{
                    btnobj.setEnabled(true);
                    btnobj.setBackgroundColor (getActivity().getResources().getColor(android.R.color.holo_blue_dark, getActivity().getTheme()));
                    //btnobj.setBackgroundColor(getResources().getColor(com.google.android.material.R.color.design_default_color_primary));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final boolean finalUpdate=isUpdate;
        btnobj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=editTextobj.getText().toString();
                if(finalUpdate){
                    dbobj.updateTask(bundle.getInt("id"),text);
                }
                else{
                    ToDoModel item=new ToDoModel();
                    item.setTask(text);
                    item.setStatus(0);
                    dbobj.insertTask(item);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity=getActivity();
        if(activity instanceof onDialogClose){
            ((onDialogClose)activity).onDialogClose(dialog);
        }
    }
}
