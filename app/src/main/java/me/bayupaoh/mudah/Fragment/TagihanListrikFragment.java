package me.bayupaoh.mudah.Fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.bayupaoh.mudah.R;
import me.bayupaoh.mudah.Util.OkHttpConnection;
import me.bayupaoh.mudah.Util.URLConnection;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TagihanListrikFragment extends Fragment {

    Button btnCek;
    EditText edtIDPelanggan;
    TextView txtTanggal,txtTotalTagihan,txtTanggalLunas,txtCalender;
    TextView txtPemakaian,txtTarif,txtTanggalBacaAkhir;
    TextView txtIDPelanggan,txtNamaPelanggan,txtALamat,txtProvinsi;
    LinearLayout layoutHasil;
    ProgressBar progressBar;
    String bulan="";
    String tahun="";

    private int mYear, mMonth, mDay, mHour, mMinute;


    String tarif,strTanggal,strTanggalLunas,strTanggalBacaAKhir,strIdPelanggan,strNamaPelanggan,strAlamat,strProv;
    int totalTagihan,pemakain;
    public TagihanListrikFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_tagihan_listrik, container, false);
        declarationWidget(parentView);

        btnCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bulan.trim().equalsIgnoreCase("") || tahun.trim().equalsIgnoreCase("") || edtIDPelanggan.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getActivity().getApplicationContext(),"Semua Field Harus Terisi",Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    getDataFromAPI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        txtCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        return parentView;
    }

    private void getDataFromAPI() throws IOException {
        String idPelanggan = edtIDPelanggan.getText().toString().trim();
        String url = URLConnection.getUrlPLN("441200574889",tahun,bulan);
        OkHttpConnection.getDataFromServer(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity().getApplicationContext(),"Jaringan Bermasalah",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    JSONObject object = new JSONObject(response.body().string());
                    Log.i("request data",object.getString("status"));
                    String hasil = object.getString("status");
                    if(hasil.trim().equalsIgnoreCase("success")){
                        JSONObject objectData = object.getJSONObject("data");
                        strAlamat = objectData.getString("alamat");
                        strIdPelanggan = objectData.getString("idpel");
                        strNamaPelanggan = objectData.getString("nama");
                        strProv = objectData.getString("namaupi");
                        strTanggal = objectData.getString("namathblrek");
                        strTanggalBacaAKhir = objectData.getString("tglbacaakhir");
                        strTanggalLunas = objectData.getString("ketlunas");
                        totalTagihan = objectData.getInt("tagihan");
                        tarif =objectData.getString("tarif");
                        pemakain = objectData.getInt("pemkwh");

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                layoutHasil.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                txtProvinsi.setText(strProv);
                                txtTanggalLunas.setText(strTanggalLunas);
                                txtTanggal.setText(strTanggal);
                                txtALamat.setText(strAlamat);
                                txtNamaPelanggan.setText(strNamaPelanggan);
                                txtPemakaian.setText(String.valueOf(pemakain)+" KWH");
                                txtTarif.setText(tarif);
                                txtTanggalBacaAkhir.setText(strTanggalBacaAKhir);
                                txtTotalTagihan.setText(String.valueOf(totalTagihan));
                                txtIDPelanggan.setText(strIdPelanggan);
                            }
                        });

                    }else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity().getApplicationContext(),"Data Yang Dicari Tidak Ditemukan",Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private void declarationWidget(View parentView) {
        btnCek = (Button) parentView.findViewById(R.id.btn_cektagihan);
        edtIDPelanggan = (EditText) parentView.findViewById(R.id.txt_idpelanggan);
        txtTanggal = (TextView) parentView.findViewById(R.id.txtTanggal);
        txtTotalTagihan = (TextView) parentView.findViewById(R.id.txtTotalTagihan);
        txtTanggalLunas = (TextView) parentView.findViewById(R.id.txtTanggalLunas);
        txtPemakaian = (TextView) parentView.findViewById(R.id.txtPemakaian);
        txtTarif = (TextView) parentView.findViewById(R.id.txtTarif);
        txtTanggalBacaAkhir = (TextView) parentView.findViewById(R.id.txtTglBacaAkhir);
        txtIDPelanggan = (TextView) parentView.findViewById(R.id.txtIdPelangganPln);
        txtNamaPelanggan = (TextView) parentView.findViewById(R.id.txtNamaPelanggan);
        txtALamat = (TextView) parentView.findViewById(R.id.txtAlamat);
        txtProvinsi = (TextView) parentView.findViewById(R.id.txtProvinsi);
        layoutHasil = (LinearLayout) parentView.findViewById(R.id.layoutHasil_tagihan);
        progressBar = (ProgressBar) parentView.findViewById(R.id.progressBar);
        txtCalender = (TextView) parentView.findViewById(R.id.txtCalendar);

    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Pilih Tanggal");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            bulan = String.valueOf(monthOfYear);
            if(monthOfYear >9) {
                bulan = "0" + bulan;
            }
            tahun = String.valueOf(dayOfMonth);
            txtCalender.setText(String.valueOf(dayOfMonth) + "-" + bulan+ "-" + String.valueOf(year));
        }
    };

}
