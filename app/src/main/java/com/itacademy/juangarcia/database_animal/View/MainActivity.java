package com.itacademy.juangarcia.database_animal.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.itacademy.juangarcia.database_animal.Model.Animal;
import com.itacademy.juangarcia.database_animal.R;
import com.itacademy.juangarcia.database_animal.ViewModel.AnimalViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_ANIMAL_REQUEST = 1;
    public static final int EDIT_ANIMAL_REQUEST = 2;

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

        //swipe to delete a animal
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                animalViewModel.delete(adapter.getAnimalAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Animal deleted!", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //do a element item clickable
        adapter.setOnItemClickListener(new AnimalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Animal animal) {
                Intent intent = new Intent(MainActivity.this,
                        AnimalInfoActivity.class);
                intent.putExtra("id", animal.getId());
                intent.putExtra("name", animal.getName());
                intent.putExtra("type", animal.getType());
                intent.putExtra("age", animal.getAge());
                intent.putExtra("date", animal.getDate());
                intent.putExtra("photo", animal.getPhoto());
                intent.putExtra("chip", animal.isChip());
                startActivityForResult(intent, EDIT_ANIMAL_REQUEST);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAllNotes:
                animalViewModel.deleteAll();
                Toast.makeText(this, "All animals deleted!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addAnimal(View view) {
        Intent intent = new Intent(MainActivity.this, AddAnimalActivity.class);
        startActivityForResult(intent, ADD_ANIMAL_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ANIMAL_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddAnimalActivity.KEY_NAME);
            String type = data.getStringExtra(AddAnimalActivity.KEY_TYPE);
            int age = data.getIntExtra(AddAnimalActivity.KEY_AGE, 1);
            String date = data.getStringExtra(AddAnimalActivity.KEY_DATE);
            String photo = data.getStringExtra(AddAnimalActivity.KEY_PHOTO);
            boolean chip = data.getBooleanExtra(AddAnimalActivity.KEY_CHIP, true);

            Animal animal = new Animal(name, photo, type, date, age, chip);

            animalViewModel.insert(animal);

            Toast.makeText(this, "animal saved!", Toast.LENGTH_LONG).show();
        } else if (requestCode == EDIT_ANIMAL_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddAnimalActivity.KEY_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Animal can't be updated!", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = data.getStringExtra(AddAnimalActivity.KEY_NAME);
            String type = data.getStringExtra(AddAnimalActivity.KEY_TYPE);
            int age = data.getIntExtra(AddAnimalActivity.KEY_AGE, 1);
            String date = data.getStringExtra(AddAnimalActivity.KEY_DATE);
            String photo = data.getStringExtra(AddAnimalActivity.KEY_PHOTO);
            boolean chip = data.getBooleanExtra(AddAnimalActivity.KEY_CHIP, true);

            Animal animal = new Animal(name, photo, type, date, age, chip);
            animal.setId(id);
            animalViewModel.update(animal);
        } else {
            Toast.makeText(this, "animal not saved!", Toast.LENGTH_LONG).show();
        }
    }
}
