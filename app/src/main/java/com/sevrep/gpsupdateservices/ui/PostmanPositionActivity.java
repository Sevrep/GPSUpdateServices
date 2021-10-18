package com.sevrep.gpsupdateservices.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sevrep.gpsupdateservices.R;
import com.sevrep.gpsupdateservices.adapters.PostmanPositionAdapter;
import com.sevrep.gpsupdateservices.interfaces.PostmanPositionWebApiRequest;
import com.sevrep.gpsupdateservices.models.PostmenPositionModel;
import com.sevrep.gpsupdateservices.networks.RetrofitClientInstance;
import com.sevrep.gpsupdateservices.services.LocationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by : Ivan Peter Semilla on 07/10/2021
 */
public class PostmanPositionActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtIpAddress;
    private Button btnSubmit;
    private TextView txtIpAddress;
    private RecyclerView recyclerView;
    private PostmanPositionAdapter postmanPositionAdapter;
    private List<PostmenPositionModel> postmenPositionModelList;
    private RetrofitClientInstance retrofitClientInstance;
    private PostmanPositionWebApiRequest postmanPositionWebApiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postman_position);

        edtIpAddress = findViewById(R.id.edtIpAddress);
        txtIpAddress = findViewById(R.id.txtIpAddress);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                startLocService();
            }
        } else {
            startLocService();
        }
    }

    private void startLocService() {
        LocationBroadcastReceiver receiver = new LocationBroadcastReceiver();
        IntentFilter filter = new IntentFilter("ACT_LOC");
        registerReceiver(receiver, filter);
        Intent intent = new Intent(PostmanPositionActivity.this, LocationService.class);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocService();
            } else {
                Toast.makeText(this, "Give me permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnSubmit) {
            edtIpAddress.setText(R.string.ip_port_test);
            String ipAddress = edtIpAddress.getText().toString().trim();

            if (TextUtils.isEmpty(ipAddress)) {
                edtIpAddress.setError("Enter an ip address");
            } else {
                txtIpAddress.setText(String.format("Connected to: %s", ipAddress));
                getPostmanLocationList(ipAddress);
            }
        }
    }

    private void getPostmanLocationList(String ipPort) {
        retrofitClientInstance = new RetrofitClientInstance(this);
        postmanPositionWebApiRequest = retrofitClientInstance.getRetrofitInstance(ipPort).create(PostmanPositionWebApiRequest.class);
        postmanPositionWebApiRequest.GetPostmanPosition().enqueue(new Callback<List<PostmenPositionModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<PostmenPositionModel>> call, @NonNull Response<List<PostmenPositionModel>> response) {
                if (response.isSuccessful()) {
                    postmenPositionModelList = new ArrayList<>();
                    for (int i = 0; i < Objects.requireNonNull(response.body()).size(); i++) {
                        postmenPositionModelList.add(new PostmenPositionModel(
                                response.body().get(i).getId(),
                                response.body().get(i).getPostmen_id(),
                                response.body().get(i).getPostmen_lat(),
                                response.body().get(i).getPostmen_long(),
                                response.body().get(i).getCreated_at(),
                                response.body().get(i).getUpdated_at()
                        ));
                        postmanPositionAdapter = new PostmanPositionAdapter(PostmanPositionActivity.this, postmenPositionModelList);
                        recyclerView.setAdapter(postmanPositionAdapter);
                        postmanPositionAdapter.notifyDataSetChanged();
                    }

                } else if (response.code() == 400) {
                    Toast.makeText(PostmanPositionActivity.this, "Error connecting to server.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PostmenPositionModel>> call, @NonNull Throwable t) {
                Toast.makeText(PostmanPositionActivity.this, "Error connecting to server.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class LocationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("ACT_LOC")) {
                Log.d("TAG", "startLocService: ");
                double lat = intent.getDoubleExtra("latitude", 0f);
                double longitude = intent.getDoubleExtra("longitude", 0f);
                Toast.makeText(PostmanPositionActivity.this, "Latitude is: " + lat + ", Longitude is " + longitude, Toast.LENGTH_LONG).show();
            }
        }

    }

}