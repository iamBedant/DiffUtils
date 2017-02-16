package com.rajsuvariya.diffutilstest;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rajsuvariya.diffutilstest.API.MyAPI;
import com.rajsuvariya.diffutilstest.Adapter.MyRecyclerViewAdapter;
import com.rajsuvariya.diffutilstest.Model.Contact;
import com.rajsuvariya.diffutilstest.Model.ContactApiResult;
import com.rajsuvariya.diffutilstest.Utils.MyDiffUtilCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ArrayList<Contact> result;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        prepareData();
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                String BASE_URL = "http://demo8889499.mockable.io/";
                Gson gson = new GsonBuilder().create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                MyAPI api = retrofit.create(MyAPI.class);

                Call<ContactApiResult> resultCall = api.getResult();

                resultCall.enqueue(new Callback<ContactApiResult>() {
                    @Override
                    public void onResponse(Call<ContactApiResult> call, Response<ContactApiResult> response) {
                        int responseCode = response.code();

                        ContactApiResult moviesResults = response.body();

//                        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffUtilCallback(adapter.getData(), moviesResults.getData()));
//                        diffResult.dispatchUpdatesTo(adapter);
                        adapter.onNewProducts(moviesResults.getData());
                        swipeRefreshLayout.setRefreshing(false);

                        Log.d("RetrofitApi", "Status: "+responseCode);
                    }

                    @Override
                    public void onFailure(Call<ContactApiResult> call, Throwable t) {
                        Log.d("RetrofitApi", "Status: "+t.getMessage());
                    }
                });
            }
        });

    }

    private void prepareData() {
        result = new ArrayList<Contact>();

        String BASE_URL = "http://demo8889499.mockable.io/";
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        MyAPI api = retrofit.create(MyAPI.class);

        Call<ContactApiResult> resultCall = api.getResult();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        resultCall.enqueue(new Callback<ContactApiResult>() {
            @Override
            public void onResponse(Call<ContactApiResult> call, Response<ContactApiResult> response) {
                int responseCode = response.code();

                progressDialog.cancel();
                ContactApiResult moviesResults = response.body();

                adapter = new MyRecyclerViewAdapter(moviesResults.getData());
                recyclerView.setAdapter(adapter);

                Log.d("RetrofitApi", "Status: "+responseCode);
            }

            @Override
            public void onFailure(Call<ContactApiResult> call, Throwable t) {
                Log.d("RetrofitApi", "Status: "+t.getMessage());
            }
        });

    }


}
