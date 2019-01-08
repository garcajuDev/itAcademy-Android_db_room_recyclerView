package com.itacademy.juangarcia.database_animal.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.itacademy.juangarcia.database_animal.Model.Animal;
import com.itacademy.juangarcia.database_animal.R;
import com.itacademy.juangarcia.database_animal.ViewModel.AnimalViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_ANIMAL_REQUEST = 1;

    private AnimalViewModel animalViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_activity_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final AnimalAdapter adapter = new AnimalAdapter();
        recyclerView.setAdapter(adapter);

        animalViewModel = ViewModelProviders.of(this).get(AnimalViewModel.class);
        animalViewModel.getAllAnimals().observe(this, new Observer<List<Animal>>() {
            @Override
            public void onChanged(@Nullable List<Animal> animals) {
                adapter.setAnimals(animals);
            }
        });
    }

    public void addAnimal(View view) {
        Intent intent = new Intent(MainActivity.this,AddAnimalActivity.class);
        startActivityForResult(intent, ADD_ANIMAL_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ANIMAL_REQUEST && resultCode == RESULT_OK){
            String name = data.getStringExtra(AddAnimalActivity.KEY_NAME);
            String type = data.getStringExtra(AddAnimalActivity.KEY_TYPE);
            int age = data.getIntExtra(AddAnimalActivity.KEY_AGE, 1);
            String date = data.getStringExtra(AddAnimalActivity.KEY_DATE);
            String photo = data.getStringExtra(AddAnimalActivity.KEY_PHOTO);
            boolean chip = data.getBooleanExtra(AddAnimalActivity.KEY_CHIP, true);

            Animal animal = new Animal(name,photo,type,date,age,chip);

            animalViewModel.insert(animal);

            Toast.makeText(this,"animal saved!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"animal not saved!",Toast.LENGTH_LONG).show();
        }
    }
}
