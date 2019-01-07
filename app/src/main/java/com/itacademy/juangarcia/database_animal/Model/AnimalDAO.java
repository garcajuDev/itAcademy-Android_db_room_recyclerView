package com.itacademy.juangarcia.database_animal.Model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AnimalDAO {

    @Insert
    void insert(Animal animal);

    @Query("select * from animal_table")
    LiveData<List<Animal>> getAllAnmals();

    @Query("delete from animal_table")
    void deleteAll();

    @Delete
    void delete(Animal animal);
}