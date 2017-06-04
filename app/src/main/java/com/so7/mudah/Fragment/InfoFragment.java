package com.so7.mudah.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.so7.mudah.Adapter.InfoAdapter;
import com.so7.mudah.DetailInfoActivity;
import com.so7.mudah.Model.InfoModel;
import com.so7.mudah.R;
import com.so7.mudah.Util.OkHttpConnection;
import com.so7.mudah.Util.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class InfoFragment extends Fragment {

    RecyclerView recyclerView;
    Context context;
    ArrayList<InfoModel> listinfo = new ArrayList<>();
    LinearLayout layout;
    ImageView imgHead;
    TextView titleHead;
    InfoAdapter adapterHome;
    RelativeLayout klikHead;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_info, container, false);

        imgHead = (ImageView) parentView.findViewById(R.id.image_headline_news);
        titleHead = (TextView)parentView.findViewById(R.id.textv_title_headline_news);
        recyclerView = (RecyclerView)  parentView.findViewById(R.id.recycleviewnews);
        klikHead = (RelativeLayout)parentView.findViewById(R.id.headline_news);

        setData();
        return parentView;


    }

    private void setRecylcle() {
        context = getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        adapterHome = new InfoAdapter(listinfo,context);
        recyclerView.setAdapter(adapterHome);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        adapterHome.notifyDataSetChanged();
    }

    private void setData() {

        try {
            String url = URLConnection.URL_INFO;
            OkHttpConnection.getDataFromServer(url).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),"Error connection", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        String hasil = object.getString("message");
                        JSONArray jsonArray = object.getJSONArray("data");

                        for (int i =0;i<jsonArray.length();i++){
                            JSONObject objectList = jsonArray.getJSONObject(i);
                            Log.e("Data",objectList.getString("title"));


                            InfoModel model = new InfoModel();
                            model.set_id(objectList.getString("_id"));
                            model.setTitle(objectList.getString("title"));
                            model.setImage(objectList.getString("image"));
                            model.setBody(objectList.getString("body"));
                            listinfo.add(model);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(getContext()).load("http://mudahapp.herokuapp.com/img/"+listinfo.get(0).getImage()).into(imgHead);
                                titleHead.setText(listinfo.get(0).getTitle());

                                klikHead.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Bundle b = new Bundle();
                                        Intent intent = new Intent(v.getContext(), DetailInfoActivity.class);
                                        b.putString("title", listinfo.get(0).getTitle());
                                        b.putString("image", listinfo.get(0).getImage());
                                        b.putString("body", listinfo.get(0).getBody());
                                        intent.putExtras(b);
                                        startActivity(intent);
                                    }
                                });
                                setRecylcle();
                                adapterHome.notifyDataSetChanged();
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


}
