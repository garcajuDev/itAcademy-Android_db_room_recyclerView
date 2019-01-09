package com.itacademy.juangarcia.database_animal.View;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itacademy.juangarcia.database_animal.Model.Animal;
import com.itacademy.juangarcia.database_animal.R;
import com.itacademy.juangarcia.database_animal.ViewModel.AnimalViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddAnimalActivity extends AppCompatActivity {
    public static final String KEY_ID =
            "com.itacademy.juangarcia.database_animal.View.KEY_ID";
    public static final String KEY_NAME =
            "com.itacademy.juangarcia.database_animal.View.KEY_NAME";
    public static final String KEY_TYPE =
            "com.itacademy.juangarcia.database_animal.View.KEY_TYPE";
    public static final String KEY_AGE =
            "com.itacademy.juangarcia.database_animal.View.KEY_AGE";
    public static final String KEY_DATE =
            "com.itacademy.juangarcia.database_animal.View.KEY_DATE";
    public static final String KEY_PHOTO =
            "com.itacademy.juangarcia.database_animal.View.KEY_PHOTO";
    public static final String KEY_CHIP =
            "com.itacademy.juangarcia.database_animal.View.KEY_CHIP";

    AnimalViewModel animalViewModel;
    TextView textViewName, textViewType, textViewAge, textViewDate;
    CheckBox checkBoxChip;
    ImageView imageViewPhoto;
    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        textViewName = findViewById(R.id.txtName);
        textViewType = findViewById(R.id.txtType);
        textViewAge = findViewById(R.id.txtAge);
        textViewDate = findViewById(R.id.txtDate);
        //imageViewPhoto = findViewById(R.drawable.);
        checkBoxChip = findViewById(R.id.chkboxChip);
    }

    @Override
    protected void onResume() {
        super.onResume();

        animalViewModel = ViewModelProviders.of(this).get(AnimalViewModel.class);
        LiveData<List<Animal>> animalList = animalViewModel.getAllAnimals();

        Intent intent = getIntent();
        if (intent.hasExtra(KEY_ID)) {
            for (Animal animal : animalList) {
                if (animal.getId() == intent.getIntExtra(KEY_ID, 0))
                    setTitle("Edit Animal");
                fill(animal.getName(), animal.getType(), animal.getAge(),
                        animal.getDate(), animal.isChip());
            }
        } else {
            setTitle("Regist Animal");
        }

        /*Intent intent = getIntent();
        if (intent.hasExtra(KEY_ID)) {
            setTitle("Edit Animal");
            //animal = animalViewModel.getAnimal(getIntent().getIntExtra(KEY_ID, 0));
        } else {
            setTitle("Regist Animal");
        }*/

    }

    public void fill(String name, String type, int age, String date, boolean chip) {
        textViewName.setText(name);
        textViewType.setText(type);
        textViewAge.setText("" + age);
        textViewDate.setText(date);
        checkBoxChip.setChecked(chip);
    }

    public Animal updatedAnimal(Animal ani) {
        if (!ani.getName().equals("")) {
            ani.setName(textViewName.getText().toString());
        }
        if (!ani.getType().equals("")) {
            ani.setType(textViewType.getText().toString());
        }
        if (ani.getAge() != 0) {
            ani.setAge(Integer.parseInt(textViewAge.getText().toString()));
        }
        if (!ani.getDate().equals("")) {
            ani.setDate(textViewDate.getText().toString());
        }
        if (!ani.isChip()) {
            ani.setChip(ani.isChip());
        }
        //if (!ani.getName().equals("")){ ani.setName(textViewName.getText().toString());}

        return new Animal(textViewName.getText().toString(),
                textViewType.getText().toString(), textViewName.getText().toString(),
                textViewDate.getText().toString(), (Integer.parseInt(textViewAge.getText().toString())),
                checkBoxChip.isChecked());

    }

    private void saveAnimal() {
        String animalName = textViewName.getText().toString();
        String animalType = textViewType.getText().toString();
        int animalAge = ageToInt();
        String animalDate = textViewDate.getText().toString();
        //String photo = "Placeholder String";
        boolean chip = hasChip();

        //form validator property
        boolean test = testInfo(animalName, animalType, animalAge, animalDate);

        //form validator
        if (test == false) {
            Toast.makeText(this, "Wrong info!", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        Intent intent = getIntent();

        if (intent.hasExtra(KEY_ID)) {
            for (Animal animal : animalList) {
                if (animal.getId() == intent.getIntExtra(KEY_ID, 0)) {
                    Animal toStoreAnimal = updatedAnimal(animal);
                    animalViewModel.update(toStoreAnimal);
                    fill(animal.getName(), animal.getType(), animal.getAge(),
                            animal.getDate(), animal.isChip());
                    Toast.makeText(this, "Animal Updated!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        /*Intent data = new Intent();
        data.putExtra(KEY_NAME, animalName);
        data.putExtra(KEY_TYPE, animalType);
        data.putExtra(KEY_AGE, animalAge);
        data.putExtra(KEY_DATE, animalDate);
        data.putExtra(KEY_PHOTO, photo);
        data.putExtra(KEY_CHIP, chip);

        int id = getIntent().getIntExtra(KEY_ID, -1);
        System.out.println(""+id);
        System.out.println(animalName);
        if (id != -1){
            data.putExtra(KEY_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_new_animal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_animal:
                saveAnimal();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //form validator flag method.
    public boolean testInfo(String name, String type, int age, String date) {
        if (name.trim().isEmpty() || type.trim().isEmpty()
                || age <= 0 || date.trim().isEmpty()) {
            return false;
        }

        return true;
    }

    /*makes sure that the age property is always an integer type.
     The empty field will return 0 avoiding a NumberFormatException*/
    public int ageToInt() {
        int age;

        if (textViewAge.getText().toString().equals("")) {
            age = 0;
        } else {
            age = Integer.parseInt(textViewAge.getText().toString());
        }

        return age;
    }

    //set a registration date
    public void pickDate(View view) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                textViewDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                //animalDate = date.getText().toString();
            }
        }, year, month, day);
        dialog.show();
    }

    //check if this animal has a chip or not
    public boolean hasChip() {
        if (checkBoxChip.isChecked() == true) {
            return true;
        } else {
            return false;
        }
    }
}
