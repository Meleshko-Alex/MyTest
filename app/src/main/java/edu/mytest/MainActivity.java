package edu.mytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.mytest.models.ProductModel;
import edu.mytest.models.ReviewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    private Gson gson;
    private Retrofit retrofit;
    private final String URL = "http://smktesting.herokuapp.com";
    private APIService service;
    private ArrayList<ProductModel> productsList;
    private List<ReviewModel> reviewsList;
    private TextView tv_hello;
    private ListView listView;
    private ListView lv_reviews;
    private boolean isGuest;
    private ImageView iv_foto;
    private TextView tv_prod_name, tv_description, tv_reviews, tv_specification;
    private Button btn_reviews;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_foto = (ImageView)findViewById(R.id.iv_foto);
        tv_prod_name = (TextView)findViewById(R.id.tv_prod_name);
        tv_description = (TextView)findViewById(R.id.tv_description);
        tv_reviews = (TextView)findViewById(R.id.tv_reviews);
        tv_specification = (TextView)findViewById(R.id.tv_specification);

        listView = (ListView)findViewById(R.id.listView);
        lv_reviews = (ListView)findViewById(R.id.lv_reviews);

        productsList = new ArrayList<>();
        reviewsList = new ArrayList<>();

        tv_hello = (TextView)findViewById(R.id.tv_hello);

        gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(URL)
                .build();

        service = retrofit.create(APIService.class);

        btn_reviews = (Button)findViewById(R.id.btn_reviews);

        if (isGuest) btn_reviews.setVisibility(View.INVISIBLE);
        btn_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ReviewActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String name = getIntent().getStringExtra("name");
        isGuest = getIntent().getBooleanExtra("isGuest", true);
        tv_hello.setText(tv_hello.getText() + name);
        createListView();
    }

    private void createListView() {
        final ArrayList<String> productName = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productName);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String product_name = ((TextView) view).getText().toString();
                iv_foto.setVisibility(View.VISIBLE);
                tv_prod_name.setVisibility(View.VISIBLE);
                tv_description.setVisibility(View.VISIBLE);
                tv_reviews.setVisibility(View.VISIBLE);
                if (!isGuest) btn_reviews.setVisibility(View.VISIBLE);
                loadInfo(product_name);
            }
        });

        listView.setAdapter(adapter);

        Call<List<ProductModel>> call = service.getProducts();
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.code() == 200){
                    productsList.addAll(response.body());
                    for (ProductModel product : productsList){
                        productName.add(product.getTitle());
                        adapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "SOME ERROR", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadInfo(String product_name) {

        final ArrayList<ItemReview> data = new ArrayList<>();
        for (ProductModel product : productsList){
            if (product.getTitle().equals(product_name)) {
                tv_prod_name.setText(product.getTitle());
                tv_specification.setText(product.getText());
                setImage(product.getId());

                Call<List<ReviewModel>> call = service.getReviews(product.getId());
                call.enqueue(new Callback<List<ReviewModel>>() {
                    @Override
                    public void onResponse(Call<List<ReviewModel>> call, Response<List<ReviewModel>> response) {
                        if (response.code() == 200){
                            reviewsList.addAll(response.body());
                            for (ReviewModel review : reviewsList){

                                data.add(new ItemReview(review.getText(), getText(R.string.rate) + " " + review.getRate()));
                            }
                            ReviewAdapter adapter = new ReviewAdapter(getBaseContext(), data);
                            lv_reviews.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ReviewModel>> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "SOME ERROR", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    private void setImage(Integer id) {
        String Url = "http://smktesting.herokuapp.com/static/img"
                    + id
                    + ".png";
        Picasso.with(this)
                .load(Url)
                .resize(500, 500)
                .centerCrop()
                .into(iv_foto);
    }
}
