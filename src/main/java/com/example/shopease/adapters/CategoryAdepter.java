package com.example.shopease.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.shopease.R;
import com.example.shopease.activities.CategoryActivity;
import com.example.shopease.databinding.ItemCategoriesBinding;
import com.example.shopease.model.Category;

import java.util.ArrayList;

public class CategoryAdepter extends  RecyclerView.Adapter<CategoryAdepter.CategoryViewHolder> {

    Context context;
    ArrayList<Category> categories;

    public  CategoryAdepter(Context context,ArrayList<Category> categories){

        this.context=context;
        this.categories=categories;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_categories,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category= categories.get(position);
        holder.binding.label.setText(category.getName());
        Target<Drawable> into = Glide.with(context)
                .load(category.getIcon())
                .into(holder.binding.image);
     holder.binding.image.setBackgroundColor(Color.parseColor(category.getColor()));
     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent i=new Intent(context, CategoryActivity.class);
             i.putExtra("catId",category.getId());
             i.putExtra("categoryName",category.getName());
             context.startActivity(i);

         }
     });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
    ItemCategoriesBinding binding;
        public CategoryViewHolder(@NonNull View itemView){
            super(itemView);
            binding = ItemCategoriesBinding.bind(itemView);

        }
    }
}
