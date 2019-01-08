package com.itacademy.juangarcia.database_animal.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.itacademy.juangarcia.database_animal.R;

public class AnimalInfoActivity extends AppCompatActivity {
    public static final int UPDATE_ANIMAL_REQUEST = 3;

    EditText editTextName,editTextType,editTextAge,editTextDate;
    CheckBox chkboxChip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_info);

        editTextName = findViewById(R.id.txtNameInfo);
        editTextType = findViewById(R.id.txtTypeInfo);
        editTextDate = findViewById(R.id.txtDateInfo);
        editTextAge = findViewById(R.id.txtAgeInfo);
        chkboxChip = findViewById(R.id.chkboxChipInfo);

        /*Intent intentFromMainActivity = getIntent();

        editTextName.setText(intentFromMainActivity.getStringExtra("name"));
        editTextType.setText(intentFromMainActivity.getStringExtra("type"));
        editTextDate.setText(intentFromMainActivity.getStringExtra("date"));
        editTextAge.setText(String.valueOf(intentFromMainActivity.getIntExtra("age", 0)));
        chkboxChip.setChecked(intentFromMainActivity.getBooleanExtra("chip", false));*/

        fillInfo();
    }

    private void fillInfo() {

        Intent intentFromMainActivity = getIntent();

        editTextName.setText(intentFromMainActivity.getStringExtra("name"));
        editTextType.setText(intentFromMainActivity.getStringExtra("type"));
        editTextDate.setText(intentFromMainActivity.getStringExtra("date"));
        editTextAge.setText(String.valueOf(intentFromMainActivity.getIntExtra("age", 0)));
        chkboxChip.setChecked(intentFromMainActivity.getBooleanExtra("chip", false));
    }

    public void updateAnimal(View view) {

        int id = getIntent().getIntExtra("id", 0);
        String name = editTextName.getText().toString();
        String type = editTextType.getText().toString();
        int age = Integer.parseInt(editTextAge.getText().toString());
        String date = editTextDate.getText().toString();
        boolean chip = chkboxChip.isChecked();

        Intent intentToAddAnimal = new Intent(AnimalInfoActivity.this, AddAnimalActivity.class);
        intentToAddAnimal.putExtra(AddAnimalActivity.KEY_ID, id);
        intentToAddAnimal.putExtra(AddAnimalActivity.KEY_NAME, name);
        intentToAddAnimal.putExtra(AddAnimalActivity.KEY_TYPE, type);
        intentToAddAnimal.putExtra(AddAnimalActivity.KEY_AGE, age);
        intentToAddAnimal.putExtra(AddAnimalActivity.KEY_DATE, date);
        intentToAddAnimal.putExtra(AddAnimalActivity.KEY_CHIP, chip);

        startActivityForResult(intentToAddAnimal, UPDATE_ANIMAL_REQUEST);
    }
}
