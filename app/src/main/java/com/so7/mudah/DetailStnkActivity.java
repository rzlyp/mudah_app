package com.so7.mudah;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.so7.mudah.Model.InfoModel;
import com.so7.mudah.Util.OkHttpConnection;
import com.so7.mudah.Util.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetailStnkActivity extends AppCompatActivity {
    String no_stnk;
    TextView nama,alamat,merk, tahun, warna, bpkb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_stnk);
        setupToolbar();
        no_stnk = getIntent().getExtras().getString("no_stnk");
        nama = (TextView) findViewById(R.id.v_txt_nama);
        alamat = (TextView) findViewById(R.id.v_txt_alamat);
        merk = (TextView) findViewById(R.id.v_txt_merk);
        tahun = (TextView) findViewById(R.id.v_txt_tahun);
        warna = (TextView) findViewById(R.id.v_txt_warna);
        bpkb = (TextView) findViewById(R.id.v_txt_bpkb);

        getData();

    }

    private void getData() {
        try {
            String url = URLConnection.URL_STNK;
            RequestBody body = new  FormBody.Builder()
                    .add("nomor_motor",no_stnk)
                    .build();
            OkHttpConnection.postDataToServer(url, body).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    DetailStnkActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DetailStnkActivity.this,"Error connection", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        String hasil = object.getString("message");
                        JSONArray jsonArray = object.getJSONArray("data");


                         final JSONObject objectList = jsonArray.getJSONObject(0);
                         final String tnama = objectList.getString("nama_pemilik");
                        final String talamat = objectList.getString("alamat");
                        final String tmerk = objectList.getString("merk");
                        final String twarna = objectList.getString("warna");
                        final String ttahun = objectList.getString("tahun_pembuatan");
                        final String tbpkb = objectList.getString("no_bpkb");



                        DetailStnkActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                nama.setText(tnama);
                                alamat.setText(talamat);
                                merk.setText(tmerk);
                                warna.setText(twarna);
                                tahun.setText(ttahun);
                                bpkb.setText(tbpkb);

                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detail STNK");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nav, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
