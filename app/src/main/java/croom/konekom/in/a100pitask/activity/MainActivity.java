package croom.konekom.in.a100pitask.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import croom.konekom.in.a100pitask.R;
import croom.konekom.in.a100pitask.adapter.CurrencyItemsAdapter;
import croom.konekom.in.a100pitask.database.AppDatabase;
import croom.konekom.in.a100pitask.model.Currency;
import croom.konekom.in.a100pitask.model.SuccessResponse;
import croom.konekom.in.a100pitask.rest.ApiClient;
import croom.konekom.in.a100pitask.rest.ApiInterface;
import croom.konekom.in.a100pitask.util.ConnectivityController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ApiInterface apiInterface;
    private final String TAG = MainActivity.class.getSimpleName();
    private AppDatabase appDatabase;
    private ArrayList<Currency> currencyArrayList;
    private RecyclerView currencyRecyclerView;
    private CurrencyItemsAdapter currencyItemsAdapter;
    private ImageView noNetworkView;
    private ProgressBar progressBar;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        toolbar.setTitle("Currencies");
        currencyArrayList = new ArrayList<>();
        if (ConnectivityController.isNetworkAvailable(this)) {
            loadDataFromInternet();
        } else {
            loadDataFromDatabase();
        }

    }

    private void init() {
        toolbar = getSupportActionBar();
        appDatabase = AppDatabase.getInstance(this);
        currencyRecyclerView = findViewById(R.id.currenciesList);
        noNetworkView = findViewById(R.id.networkerror);
        progressBar = findViewById(R.id.currency_loader);
    }

    private void loadDataFromDatabase() {
        currencyArrayList.addAll(appDatabase.userDao().getAllCurrencies());
        if (currencyArrayList.size() > 0) {
            setAdapter();
        } else {
            setNoContentPage();
        }
    }

    private void setNoContentPage() {
        currencyRecyclerView.setVisibility(View.GONE);
        noNetworkView.setVisibility(View.VISIBLE);
    }

    private void setAdapter() {
        currencyItemsAdapter = new CurrencyItemsAdapter(this, currencyArrayList);
        currencyRecyclerView.setAdapter(currencyItemsAdapter);
        currencyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadDataFromInternet() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressBar.setVisibility(View.VISIBLE);
        Call<SuccessResponse> successResponseCall = apiInterface.getAllCurrencies();

        successResponseCall.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                //Toast.makeText(MainActivity.this, response.code() + " " + response.body().getCurrencies().size(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Api call success" + response.body().getCurrencies().size());
                progressBar.setVisibility(View.GONE);
                saveCurrencyToDatabase(response.body().getCurrencies());
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                Log.d(TAG, "Api call fail" + t.toString());
                progressBar.setVisibility(View.GONE);
                loadDataFromDatabase();
            }
        });
    }

    private void saveCurrencyToDatabase(List<Currency> currencies) {
        appDatabase.userDao().nukeTable();
        appDatabase.userDao().insertCurrencies(currencies);
       // Log.d(TAG,"Current size "+appDatabase.userDao().getAllCurrencies().size());
        currencyArrayList.addAll(appDatabase.userDao().getAllCurrencies());
        setAdapter();

    }

}
