package me.bayupaoh.mudah.Fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.bayupaoh.mudah.Model.ModelHalal;
import me.bayupaoh.mudah.Model.ModelKurs;
import me.bayupaoh.mudah.R;
import me.bayupaoh.mudah.Util.OkHttpConnection;
import me.bayupaoh.mudah.Util.URLConnection;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class KursAsingFragment extends Fragment {

    Spinner spnBank;
    ProgressBar progressLoading;
    Button btnKurs;
    LinearLayout layoutHasil;
    TextView txtBank;
    TextView txtHargaJual;
    TextView txtHargaBeli;
    TextView txtStatus;
    TextView mataUang;
    ArrayList<ModelKurs> listKurs;
    String strNamaBank,strStatus;
    int hargaBeli,hargaJual;

    public KursAsingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_kurs_asing, container, false);
        deklarasiWidget(parentView);
        setSpinner();
        btnKurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listKurs = new ArrayList<ModelKurs>();
                progressLoading.setVisibility(View.VISIBLE);
                try {
                    getDataFromAPI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return parentView;
    }

    private void getDataFromAPI() throws IOException {
        String namaBank = spnBank.getSelectedItem().toString().trim().toLowerCase().replace("bank ","bank");
        String url = URLConnection.getUrlKurs(namaBank);
        Log.i("request data",url);
        OkHttpConnection.getDataFromServer(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressLoading.setVisibility(View.GONE);
                        Snackbar.make(getView(),"Koneksi Bermasalah",Snackbar.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    JSONObject object = new JSONObject(response.body().string());
                    Log.i("request data",object.getString("data"));
                    JSONArray jsonArray = object.getJSONArray("data");

                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject objectKurs = jsonArray.getJSONObject(i);


                        ModelKurs modelKurs = new ModelKurs();
                        modelKurs.setNamaBank(objectKurs.getString("bank"));
                        Log.i("request data",objectKurs.getString("bank"));
                        modelKurs.setStatus(objectKurs.getString("status"));


                            JSONObject objectKursData = objectKurs.getJSONObject("kurs");

                            modelKurs.setMataUang(objectKursData.getString("mata_uang"));
                            Log.i("request data",objectKursData.getString("mata_uang"));
                            modelKurs.setHargaJual(objectKursData.getInt("jual"));
                            modelKurs.setHargaBeli(objectKursData.getInt("beli"));


                        listKurs.add(modelKurs);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressLoading.setVisibility(View.GONE);
                            layoutHasil.setVisibility(View.VISIBLE);
                            String namaBankTampil = listKurs.get(0).getNamaBank().toUpperCase().replace("BANK","BANK ");
                            txtBank.setText(namaBankTampil);
                            txtHargaBeli.setText("Rp."+String.valueOf(listKurs.get(0).getHargaBeli()));
                            txtHargaJual.setText("Rp."+String.valueOf(listKurs.get(0).getHargaJual()));
                            txtStatus.setText(listKurs.get(0).getStatus().toUpperCase());
                            mataUang.setText(listKurs.get(0).getMataUang().toUpperCase());
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setSpinner() {
        List<String> list = new ArrayList<String>();
        list.add("BI");
        list.add("BCA");
        list.add("Permata");
        list.add("BRI");
        list.add("BNI");
        list.add("Bukopin");
        list.add("Danamon");
        list.add("Mandiri");
        list.add("Mega");
        list.add("MyBank");
        list.add("Bank Jatim");
        list.add("BTN");
        list.add("BJB");
        list.add("Bank Muamalat");
        list.add("Bank Sinarmas");
        list.add("Bankaltim");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBank.setAdapter(dataAdapter);
    }

    private void deklarasiWidget(View parentView) {
        spnBank = (Spinner) parentView.findViewById(R.id.spnBank);
        progressLoading = (ProgressBar) parentView.findViewById(R.id.progressBar);
        btnKurs = (Button) parentView.findViewById(R.id.btnKurs);
        layoutHasil = (LinearLayout) parentView.findViewById(R.id.layoutHasil);
        txtBank = (TextView) parentView.findViewById(R.id.txtNamaBank);
        txtHargaJual = (TextView) parentView.findViewById(R.id.txtHargaJual);
        txtHargaBeli = (TextView) parentView.findViewById(R.id.txtHargaBeli);
        txtStatus = (TextView) parentView.findViewById(R.id.status);
        mataUang = (TextView) parentView.findViewById(R.id.txtMataUang);
        layoutHasil.setVisibility(View.GONE);
    }

}
