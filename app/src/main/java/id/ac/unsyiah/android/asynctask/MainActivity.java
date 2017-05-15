package id.ac.unsyiah.android.asynctask;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import model.SmartPhone;
import model.SmartPhoneList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends ListActivity {

    TextView output;
    ProgressBar pb;
    List<SmartPhone> phoneList;
    public static final String PHONE_BASE_URL =
            "http://202.4.186.247/api/toko-hape/foto/";

    public static final String ENDPOINT = "http://202.4.186.247";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.action_do_task){
            setListAdapter(null);
            if (isOnline()){
                requestData("http://202.4.186.247/api/toko-hape/rest/list/json");
            }else {
                Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    private void requestData(String uri) {

        pb.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PhoneAPI api = retrofit.create(PhoneAPI.class);

        Call<SmartPhoneList> call = api.getFeed();

        call.enqueue(new Callback<SmartPhoneList>() {
            @Override
            public void onResponse(Call<SmartPhoneList> call, Response<SmartPhoneList> response) {
                if (response.isSuccessful()){
                    phoneList = response.body().getSmartphone();
                    updateDisplay();
                    pb.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<SmartPhoneList> call, Throwable t) {
                pb.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "Gagal Terhubung", Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected void updateDisplay(){
        //Gunakan kelas PhoneAdapter untuk menampilkan data
        PhoneAdapter adapter = new PhoneAdapter(this, R.layout.item_phone, phoneList);
        setListAdapter(adapter);
    }

    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(MainActivity.this, DetailPhoneActivity.class);
        //Toast.makeText(MainActivity.this, "hahaha "+position, Toast.LENGTH_SHORT).show();
        SmartPhone sp = phoneList.get(position);
        i.putExtra("productId", sp.getProductId());
        i.putExtra("productName", sp.getProductName());
        i.putExtra("category", sp.getCategory());
        i.putExtra("description", sp.getDescription());
        i.putExtra("photo", sp.getPhoto());
        i.putExtra("price", sp.getPrice());
        startActivity(i);
    }
}