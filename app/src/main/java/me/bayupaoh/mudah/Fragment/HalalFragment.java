package me.bayupaoh.mudah.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.bayupaoh.mudah.Adapter.HalalAdapter;
import me.bayupaoh.mudah.Model.ModelHalal;
import me.bayupaoh.mudah.R;
import me.bayupaoh.mudah.Util.OkHttpConnection;
import me.bayupaoh.mudah.Util.URLConnection;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HalalFragment extends Fragment {


    RecyclerView recyclerView;
    Context context;
    ArrayList<ModelHalal> listHalal;
    LinearLayout layout;
    ProgressBar progressBar;
    RelativeLayout layoutGagal;
    LinearLayout layoutHasil;
    EditText edtKeyword;
    Spinner spnKategori;
    TextView txtPesan;
    boolean statuCari = false;

    public HalalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_halal, container, false);

        deklarasiWidget(parentView);

        setSpinner();
        //setListHalal();


        //getListHalalFormAPI();


        edtKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if(edtKeyword.getText().toString().trim().equalsIgnoreCase("")){
                        Toast.makeText(getActivity().getApplicationContext(),"Keyword Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
                        return true;
                    }

                    if(!statuCari) {
                        statuCari = true;
                        progressBar.setVisibility(View.VISIBLE);
                        listHalal = new ArrayList<>();
                        try {
                            getListHalalFormAPI();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        return parentView;
    }

    private void setSpinner() {
        List<String> list = new ArrayList<String>();
        list.add("Nama Produk");
        list.add("Nama Produsen");
        list.add("Nomor Sertifikat");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnKategori.setAdapter(dataAdapter);

    }

    private void getListHalalFormAPI() throws IOException {
        String namaProduk = "nama_produk";
        String namaProdukSpinner = spnKategori.getSelectedItem().toString();
        String query = edtKeyword.getText().toString();
        if(namaProdukSpinner.equalsIgnoreCase("Nama Produk")){
            namaProduk="nama_produk";
        }else if(namaProdukSpinner.equalsIgnoreCase("Nama Produsen")){
            namaProduk="nama_produsen";
        }else if(namaProdukSpinner.equalsIgnoreCase("Nomor Sertifikat")){
            namaProduk="nomor_sertifikat";
        }

            String url = URLConnection.getUrlHalal(namaProduk.trim(),query.trim(),0);

        OkHttpConnection.getDataFromServer(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        layoutGagal.setVisibility(View.VISIBLE);
                        layoutHasil.setVisibility(View.GONE);
                        txtPesan.setText("Koneksi Sedang Mengalami Masalah");
                        statuCari =false;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    String hasil = object.getString("status");
                    Log.i("request data", hasil);

                    if(!hasil.equalsIgnoreCase("error")){
                        JSONArray jsonArray = object.getJSONArray("data");

                        for (int i =0;i<jsonArray.length();i++){
                            JSONObject objectListHalal = jsonArray.getJSONObject(i);

                            ModelHalal model = new ModelHalal();
                            model.setIdProduk(objectListHalal.getString("nomor_sertifikat"));
                            model.setNamaProduk(objectListHalal.getString("nama_produk"));
                            model.setNamaProdusen(objectListHalal.getString("nama_produsen"));
                            model.setTanggal(objectListHalal.getString("berlaku_hingga"));

                            listHalal.add(model);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                layoutGagal.setVisibility(View.GONE);
                                layoutHasil.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                setDaftarHalal();
                                statuCari =false;
                            }
                        });
                    }else{
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                layoutGagal.setVisibility(View.VISIBLE);
                                layoutHasil.setVisibility(View.GONE);
                                txtPesan.setText("Data Yang Anda Cari Tidak Dtemukan");
                                statuCari =false;
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        });
    }

    private void setDaftarHalal() {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
            HalalAdapter adapterHome = new HalalAdapter(listHalal,context);
            recyclerView.setAdapter(adapterHome);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);


    }

    private void deklarasiWidget(View parentView) {
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerview_halal);
        progressBar = (ProgressBar) parentView.findViewById(R.id.progressBar);
        layoutHasil = (LinearLayout) parentView.findViewById(R.id.layout_hasil);
        layoutGagal = (RelativeLayout) parentView.findViewById(R.id.layout_gagal);
        edtKeyword = (EditText) parentView.findViewById(R.id.edt_keyword);
        spnKategori = (Spinner) parentView.findViewById(R.id.spinner_kategori);
        txtPesan = (TextView) parentView.findViewById(R.id.txtPesan);
        progressBar.setVisibility(View.GONE);
    }

    private void setListHalal() {
        listHalal.add(new ModelHalal("Indomie","PT. Indomie","14254524","12 Maret 2015"));
        listHalal.add(new ModelHalal("Indomie","PT. Indomie","14254524","12 Maret 2015"));
        listHalal.add(new ModelHalal("Indomie","PT. Indomie","14254524","12 Maret 2015"));
        listHalal.add(new ModelHalal("Indomie","PT. Indomie","14254524","12 Maret 2015"));
        listHalal.add(new ModelHalal("Indomie","PT. Indomie","14254524","12 Maret 2015"));
    }


}
