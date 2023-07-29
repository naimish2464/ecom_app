package com.example.shopease.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.shopease.R;
import com.example.shopease.databinding.ActivityProductDetailBinding;
import com.example.shopease.model.Product;
import com.example.shopease.utils.Constants;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {
    ActivityProductDetailBinding binding;
    Product currentProduct;
    ArrayList<Product> products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            binding=ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String name=getIntent().getStringExtra("name");
        String image=getIntent().getStringExtra("image");

        int  id=getIntent().getIntExtra("id",0);
        double price=getIntent().getDoubleExtra("price",0);
        Glide.with(this)
                .load(image)
                .into(binding.productimg);
        getProductDetails(id);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Cart cart= TinyCartHelper.getCart();

        binding.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.addItem(currentProduct,1);
                binding.addtocart.setEnabled(false);
                binding.addtocart.setText("Added in cart");
                Toast.makeText(ProductDetailActivity.this, "Item Added in Cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.cart)
        {
            startActivity(new Intent(this,cartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    void getProductDetails(int id){
        RequestQueue queue= Volley.newRequestQueue(this);
        String url = Constants.GET_PRODUCT_DETAILS_URL+id;
        StringRequest request=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equals("success")) {
                        JSONObject product = object.getJSONObject("product");
                        String description = product.getString("description");
                        binding.productdesc.setText(
                                Html.fromHtml(description));

                        currentProduct =new Product(
                                product.getString("name"),
                               Constants.PRODUCTS_IMAGE_URL+ product.getString("image"),
                                product.getString("status"),
                                product.getDouble("price"),
                                product.getDouble("price_discount"),
                                product.getInt("stock"),
                                product.getInt("id")
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}