package com.itacademy.juangarcia.database_animal.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.itacademy.juangarcia.database_animal.Model.Animal;
import com.itacademy.juangarcia.database_animal.R;

import java.util.ArrayList;
import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.Animalholder> {

    private List<Animal> animalsList = new ArrayList<>();

    @NonNull
    @Override
    public Animalholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.item_animal, viewGroup, false);
        return new Animalholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Animalholder animalholder, int i) {
            Animal currentAnimal = animalsList.get(i);
            animalholder.textViewName.setText(currentAnimal.getName());
            animalholder.textViewDate.setText(currentAnimal.getDate());
            animalholder.imageViewPhoto.setImageResource(R.drawable.animal_placeholder);

    }

    public void setAnimals(List<Animal> animals){
        this.animalsList = animals;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
         return animalsList.size();
    }

    class Animalholder extends RecyclerView.ViewHolder{
        private TextView textViewName;
        private TextView textViewDate;
        private ImageView imageViewPhoto;

        public Animalholder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.txtNameList);
            textViewDate = itemView.findViewById(R.id.txtDateList);
            imageViewPhoto = itemView.findViewById(R.id.imgPlaceholderList);
        }
    }
}
