package com.example.shopease.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shopease.adapters.CartAdapter;
import com.example.shopease.databinding.ActivityCartBinding;
import com.example.shopease.model.Product;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;
import java.util.Map;

public class cartActivity extends AppCompatActivity {
    ActivityCartBinding binding;
    CartAdapter adapter;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        products =new ArrayList<>();
        Cart cart = TinyCartHelper.getCart();

        for(Map.Entry<Item,Integer> item :cart.getAllItemsWithQty().entrySet())
        {
            Product product=(Product) item.getKey();
            int quantity =item.getValue();
            product.setQuantity(quantity);
            products.add(product);
        }

       adapter=new CartAdapter(this,products, new CartAdapter.CartListener(){

           @Override
           public void onQuantityChanged() {
               binding.SubTotal.setText(String.format("Rs. %.2f",cart.getTotalPrice()));
           }
       });

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration =new DividerItemDecoration(this,layoutManager.getOrientation());


        binding.Cartlist.setLayoutManager(layoutManager);
        binding.Cartlist.addItemDecoration(itemDecoration);
        binding.Cartlist.setAdapter(adapter);

        binding.SubTotal.setText(String.format("Rs %.2f",cart.getTotalPrice()));
        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(cartActivity.this,CheckoutActivity.class));
            }
        });
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
