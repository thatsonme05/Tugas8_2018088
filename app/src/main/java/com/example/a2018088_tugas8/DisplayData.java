package com.example.a2018088_tugas8;

import static com.example.a2018088_tugas8.DBmain.TABLENAME;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import com.example.a2018088_tugas8.databinding.ActivityDisplayDataBinding;
import java.util.ArrayList;
public class DisplayData extends AppCompatActivity {
    DBmain dBmain;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    private ActivityDisplayDataBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =
                ActivityDisplayDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        findId();
        dBmain = new DBmain(this);
        displayData();
        recyclerView.setLayoutManager(new
                LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent a = new Intent(DisplayData.this,
                        MainActivity.class);
                startActivity(a);
            }
        });
    }
    private void displayData() {
        sqLiteDatabase = dBmain.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TABLENAME,null);
        ArrayList<Model> models = new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);String name = cursor.getString(1);
            byte[]avatar = cursor.getBlob(4);String star = cursor.getString(2); String price = cursor.getString(3);
            models.add(new Model(id,avatar,name,star,price));
        }
        cursor.close();
        myAdapter = new MyAdapter(this, R.layout.singledata,models,sqLiteDatabase);
        recyclerView.setAdapter(myAdapter);
    }
    private void findId() {
        recyclerView = findViewById(R.id.rv);
    }
}