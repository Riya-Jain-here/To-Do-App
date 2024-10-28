package com.example.todoapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FirstPg extends AppCompatActivity {
    private Button btnobj;
    private TextView txtobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_first_pg);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        btnobj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent objintent=new Intent(FirstPg.this, MainActivity.class);
                startActivity(objintent);
            }
        });

    }

    private void initViews(){
        btnobj=findViewById(R.id.start);
        txtobj=findViewById(R.id.txtLicense);
    }
}