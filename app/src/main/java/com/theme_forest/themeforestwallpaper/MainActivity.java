package com.theme_forest.themeforestwallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private DatabaseReference reference;
    private ArrayList<String> list;
    private WallpaperAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reference = FirebaseDatabase.getInstance().getReference().child("images");
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        getData();
    }

    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressBar.setVisibility((View.GONE));
                list= new ArrayList<>();

                for (DataSnapshot shot : snapshot.getChildren()) {
                    String data = shot.getValue().toString();
                    list.add(data);
                }

                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                adapter=new WallpaperAdapter(list, MainActivity.this);
                recyclerView.setAdapter((adapter));
                progressBar.setVisibility(View.GONE);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressBar.setVisibility((View.GONE));
                Toast.makeText(MainActivity.this, "Error :"+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}
