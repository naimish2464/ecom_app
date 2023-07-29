package com.example.shopease.activities;

import static com.android.volley.Request.Method.GET;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopease.adapters.CategoryAdepter;
import com.example.shopease.adapters.ProductAdapter;
import com.example.shopease.databinding.ActivityMainBinding;
import com.example.shopease.model.Category;
import com.example.shopease.model.Product;
import com.example.shopease.utils.Constants;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CategoryAdepter categoryAdepter;
    ArrayList<Category> categories;

    ProductAdapter productAdapter;
    ArrayList<Product> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener(){
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            public void onSearchConfirmed(CharSequence text){
                Intent i=new Intent(MainActivity.this,SearchActivity.class);
                i.putExtra("query",text.toString());
                startActivity(i);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }

        });

        Categories();
        Products();
        Slider();

    }



    void Categories()
    {
        categories=new ArrayList<>();
        categoryAdepter=new CategoryAdepter(this,categories);
        getCategories();
      /*  categories.add(new Category("Mens Clothing","https://rukminim1.flixcart.com/image/612/612/xif0q/t-shirt/n/l/7/m-tsrt-catalog-01-kajaru-original-imagm9644srfq5bj.jpeg?q=70","","",1));
        categories.add(new Category("Food & Groceries","https://rukminim1.flixcart.com/flap/128/128/image/29327f40e9c4d26b.png?q=100","","",2));
        categories.add(new Category("Home & Lifestyle","https://rukminim1.flixcart.com/flap/128/128/image/0ff199d1bd27eb98.png?q=100","","",3));
        categories.add(new Category("Womens Fashion","https://cdn.pixabay.com/photo/2017/08/01/11/48/woman-2564660_640.jpg","","",4));
        categories.add(new Category("Sports & outdoor","https://images-eu.ssl-images-amazon.com/images/G/31/img22/Fashion/Gateway/BAU/BTF-Refresh/May/PF_MF/MF-2-186-116._SY116_CB636110853_.jpg","","",5));
        categories.add(new Category("Babies and Toys","https://cdn.pixabay.com/photo/2014/11/09/21/44/teddy-bear-524251_640.jpg","","",6));
        categories.add(new Category("Consumer Electronics","https://rukminim1.flixcart.com/flap/128/128/image/69c6589653afdb9a.png?q=100","","",7));
        categories.add(new Category("Health & Beauty","https://ecommercephotographyindia.com/blog/wp-content/uploads/2022/07/beauty-products-1.jpg","","",8));
        categoryAdepter =new CategoryAdepter(this,categories);
        */


        GridLayoutManager layoutManager =new GridLayoutManager(this,4);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoryAdepter);
    }

    void getCategories()
    {

        RequestQueue queue= Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(GET, Constants.GET_CATEGORIES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("err",response);
                try {
                    Log.e("err",response);
                    JSONObject mainObj=new JSONObject(response);
                    if(mainObj.getString("status").equals("success"))
                    {
                        JSONArray categoriesArray =mainObj.getJSONArray("categories");
                        for(int i=0;i<categoriesArray.length();i++)
                        {
                            JSONObject object =categoriesArray.getJSONObject(i);
                            Category category=new Category(
                                    object.getString("name"),
                                    Constants.CATEGORIES_IMAGE_URL+ object.getString("icon"),
                                    object.getString("color"),
                                    object.getString("brief"),
                                    object.getInt("id")
                            );
                            categories.add(category);
                        }
                        categoryAdepter.notifyDataSetChanged();
                    }else {
                        // nothing
                    }
                }catch (JSONException e)
                {
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

    void Products()
    {
        products =new ArrayList<>();
    /*  products.add(new Product("NIKON Camera","https://rukminim1.flixcart.com/image/312/312/kokdci80/dslr-camera/v/e/x/z-24-200mm-z5-nikon-original-imag2zuekuxgxsgg.jpeg?q=70","hello_hello",144000,12,2,1 ));
        products.add(new Product("Embroidery work Gown","https://images.meesho.com/images/products/193367363/giecr_512.webp","",499,12,3,2));
        products.add(new Product("Tritoe Elegant Synthetic Slip On Loafers (BLACK)","https://images.meesho.com/images/products/150392615/zoyzc_512.webp","",1399,12,1,3 ));
        products.add(new Product("Lightweight,Comfort Sports Shoes","https://rukminim1.flixcart.com/image/832/832/xif0q/shoe/6/l/q/6-mrj2089-6-aadi-black-grey-original-imagmkfznyvdhzeb.jpeg?q=70","",759,12,1,4));
        products.add(new Product("Men-Shirt","https://rukminim1.flixcart.com/image/612/612/xif0q/t-shirt/t/x/c/l-ausk0265-ausk-original-imagmzxa7qx3kvsy.jpeg?q=70","",399,12,1,5));
        products.add(new Product("Men-Tshirt","https://rukminim1.flixcart.com/image/832/832/xif0q/t-shirt/y/t/k/xxs-t653-cgblwh-eyebogler-original-imaghyjv7kppbqxb.jpeg?q=70","",899,12,1,6 ));
        products.add(new Product("Noise Earpods","https://m.media-amazon.com/images/I/51C0T-op8nL._SX425_.jpg","",2000,12,12,7));
        products.add(new Product("Dancing Cactus Toy","https://m.media-amazon.com/images/I/71w7tMlKSnL._AC_UL400_.jpg","",890,12,1,8));
        products.add(new Product("Boat SmartWatch","https://m.media-amazon.com/images/I/61H5PEqBBAL._AC_UL400_.jpg","",1599,11,13,9 ));
        products.add(new Product("Women Puff Sleevs Top","https://m.media-amazon.com/images/I/71sm8MRnZ2L._AC_UL400_.jpg","",599,12,51,10));
        products.add(new Product("Sony HDTv","https://m.media-amazon.com/images/I/81wxS8abrgL._SX679_.jpg","",30999,11,16,11));
        products.add(new Product("Rapido men-Tshirt","https://m.media-amazon.com/images/I/61Mw4M+mHwL._UL1300_.jpg","",799,12,41,12 ));
        products.add(new Product("Womens Cotton Top","https://m.media-amazon.com/images/I/71Ad1HH4pDL._AC_UL400_.jpg","",680,11,26,13 ));
        products.add(new Product("Mens Geans","https://rukminim1.flixcart.com/image/612/612/k687wy80/jean/b/d/8/30-10046869-roadster-original-imafzp9m3hkad8fh.jpeg?q=70","",899,11,26,14 ));
        */
        productAdapter =new ProductAdapter(this,products);
        getProducts();

        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        binding.productList.setLayoutManager(layoutManager);
        binding.productList.setAdapter(productAdapter);

    }

    void getProducts()
    {
        RequestQueue queue=Volley.newRequestQueue(this);
        String url=Constants.GET_PRODUCTS_URL+"?count=10 ";
        StringRequest request=new StringRequest(GET,url, response -> {

            try {
                JSONObject object = new JSONObject(response);
           if(object.getString("status").equals("success"))
            {
                JSONArray productsArray =object.getJSONArray("products");
                for(int i=0;i<productsArray.length();i++)
                {
                    JSONObject childobject =productsArray.getJSONObject(i);
                   Product product=new Product(
                            childobject.getString("name"),
                           Constants.PRODUCTS_IMAGE_URL+ childobject.getString("image"),
                            childobject.getString("status"),
                           childobject.getDouble("price"),
                           childobject.getDouble("price_discount"),
                           childobject.getInt("stock"),
                           childobject.getInt("id")
                    );
                   products.add(product);
                }
                productAdapter.notifyDataSetChanged();

            }
        } catch (JSONException e) {
                e.printStackTrace();
            }
            },error -> { });
            queue.add(request);
    }

    void Slider()
    {
        binding.carousel.addData( new CarouselItem("https://cdn.pixabay.com/photo/2018/07/25/20/29/ecommerce-3562191_1280.jpg",""));
        binding.carousel.addData( new CarouselItem("https://cdn.pixabay.com/photo/2020/04/02/05/19/beauty-4993472_1280.jpg",""));
        binding.carousel.addData( new CarouselItem("https://cdn.pixabay.com/photo/2019/09/19/10/15/black-friday-4488821_1280.jpg",""));
        binding.carousel.addData( new CarouselItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRBavzVJzgctxiCjtZhTo8nwr2P36b52J86Iw&usqp=CAU",""));
    }

}