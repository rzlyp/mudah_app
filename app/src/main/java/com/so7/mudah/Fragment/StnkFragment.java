package com.so7.mudah.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.so7.mudah.DetailStnkActivity;
import com.so7.mudah.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StnkFragment extends Fragment {
    EditText cariStnk;
    Button cari;
    String no;
    TextView test;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stnk, container, false);

        cariStnk = (EditText)view.findViewById(R.id.stnk_edt_no_stnk);
        test = (TextView)view.findViewById(R.id.tests);

        cari = (Button)view.findViewById(R.id.stnk_search);
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                Intent i = new Intent(getActivity(), DetailStnkActivity.class);
                Log.e("No : ",cariStnk.getText().toString());
                b.putString("no_stnk",cariStnk.getText().toString());
                i.putExtras(b);
                getContext().startActivity(i);
                //test.setText(cariStnk.getText().toString());

            }
        });
        return view;
    }

}
