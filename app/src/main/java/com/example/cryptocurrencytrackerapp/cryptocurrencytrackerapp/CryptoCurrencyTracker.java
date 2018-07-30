package com.example.cryptocurrencytrackerapp.cryptocurrencytrackerapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cryptocurrencytrackerapp.cryptocurrencytrackerapp.Adapter.CryptoCurrencyAdapter;
import com.example.cryptocurrencytrackerapp.cryptocurrencytrackerapp.CryptoCurrencyApi.CryptoCurrencyApiClient;
import com.example.cryptocurrencytrackerapp.cryptocurrencytrackerapp.CryptoCurrencyApi.ICryptoCurrencyApi;
import com.example.cryptocurrencytrackerapp.cryptocurrencytrackerapp.Model.CryptoCurrencyModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CryptoCurrencyTracker extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CryptoCurrencyModel> cryptoCurrencyModelList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ICryptoCurrencyApi iCryptoCurrencyApi;
    private CryptoCurrencyModel cryptoCurrencyModel;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;
    private Context context;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryptocurrency_tracker);
        trackerSetUp();
        getData(CryptoCurrencyTracker.this);
    }


    public void trackerSetUp(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        cryptoCurrencyModelList = new ArrayList<>();
        cryptoCurrencyModel = new CryptoCurrencyModel();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        iCryptoCurrencyApi = CryptoCurrencyApiClient.getApiClient().create(ICryptoCurrencyApi.class);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    cryptoCurrencyModelList.clear();
                    getData(CryptoCurrencyTracker.this);
                    adapter = new CryptoCurrencyAdapter(CryptoCurrencyTracker.this,cryptoCurrencyModelList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    },2000);
                }


        });
    }

    public void getData(final Context context){
        progressDialog.setMessage("Getting Data...");
        progressDialog.show();
        Call<List<CryptoCurrencyModel>> call = iCryptoCurrencyApi.getAll();
        call.enqueue(new Callback<List<CryptoCurrencyModel>>() {
            @Override
            public void onResponse(Call<List<CryptoCurrencyModel>> call, Response<List<CryptoCurrencyModel>> response) {
                List<CryptoCurrencyModel> temp = response.body();
                cryptoCurrencyModelList.clear();
                for(CryptoCurrencyModel model : temp){
                    CryptoCurrencyModel newModel = new CryptoCurrencyModel();
                    newModel.setName(model.getName());
                    newModel.setSymbol(model.getSymbol());
                    newModel.setPriceUsd(model.getPriceUsd());
                    newModel.setId(model.getId());
                    newModel.setRank(model.getRank());
                    newModel.setPriceBtc(model.getPriceBtc());
                    newModel.set24hVolumeUsd(model.get24hVolumeUsd());
                    newModel.setMarketCapUsd(model.getMarketCapUsd());
                    newModel.setAvailableSupply(model.getAvailableSupply());
                    newModel.setTotalSupply(model.getTotalSupply());
                    newModel.setMaxSupply(model.getMaxSupply());
                    newModel.setLastUpdated(model.getLastUpdated());
                    newModel.setPercentChange1h(model.getPercentChange1h());
                    newModel.setPercentChange24h(model.getPercentChange24h());
                    newModel.setPercentChange7d(model.getPercentChange7d());
                    cryptoCurrencyModelList.add(newModel);
                }

                progressDialog.dismiss();
                adapter = new CryptoCurrencyAdapter(context,cryptoCurrencyModelList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CryptoCurrencyModel>> call, Throwable t) {
                Toast.makeText(CryptoCurrencyTracker.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionSearch:
                search(CryptoCurrencyTracker.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void search(Context context ) {
        builder = new AlertDialog.Builder(this);
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_dialog, null);
        Button buttonSearch = (Button) view.findViewById(R.id.buttonSearch);
        final EditText editTextSearch = (EditText) view.findViewById(R.id.editTextSearch);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextSearch.getText().toString().isEmpty()) {
                    String temp = editTextSearch.getText().toString();
                    searchOption(CryptoCurrencyTracker.this, temp.toLowerCase());
                    dialog.dismiss();
                } else {
                    Toast.makeText(CryptoCurrencyTracker.this, "Empty search line", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void searchOption(final Context context, String temp) {
        progressDialog.setMessage("Searching...");
        progressDialog.show();
        Call<List<CryptoCurrencyModel>> call = iCryptoCurrencyApi.getSearch(temp);
        call.enqueue(new Callback<List<CryptoCurrencyModel>>() {
            @Override
            public void onResponse(Call<List<CryptoCurrencyModel>> call, Response<List<CryptoCurrencyModel>> response) {
                if(response.body() != null) {
                    List<CryptoCurrencyModel> temp = response.body();
                    cryptoCurrencyModelList.clear();
                    for (CryptoCurrencyModel model : temp) {
                        CryptoCurrencyModel newModel = new CryptoCurrencyModel();
                        newModel.setName(model.getName());
                        newModel.setSymbol(model.getSymbol());
                        newModel.setPriceUsd(model.getPriceUsd());
                        newModel.setId(model.getId());
                        newModel.setRank(model.getRank());
                        newModel.setPriceBtc(model.getPriceBtc());
                        newModel.set24hVolumeUsd(model.get24hVolumeUsd());
                        newModel.setMarketCapUsd(model.getMarketCapUsd());
                        newModel.setAvailableSupply(model.getAvailableSupply());
                        newModel.setTotalSupply(model.getTotalSupply());
                        newModel.setMaxSupply(model.getMaxSupply());
                        newModel.setLastUpdated(model.getLastUpdated());
                        newModel.setPercentChange1h(model.getPercentChange1h());
                        newModel.setPercentChange24h(model.getPercentChange24h());
                        newModel.setPercentChange7d(model.getPercentChange7d());
                        cryptoCurrencyModelList.add(newModel);
                    }
                    progressDialog.dismiss();
                    adapter = new CryptoCurrencyAdapter(context, cryptoCurrencyModelList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(CryptoCurrencyTracker.this, "Item Not Found", Toast.LENGTH_SHORT).show();
                    getData(CryptoCurrencyTracker.this);
                }
            }
            @Override
            public void onFailure(Call<List<CryptoCurrencyModel>> call, Throwable t) {

            }
        });
    }
}
