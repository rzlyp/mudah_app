package me.bayupaoh.mudah.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import me.bayupaoh.mudah.Model.ModelHalal;
import me.bayupaoh.mudah.R;

/**
 * Created by codelabsunikom on 8/7/16.
 */
public class HalalAdapter extends RecyclerView.Adapter<HalalAdapter.ViewHOlderHalal> {
    ArrayList<ModelHalal> listHalal;
    Context context;

    public HalalAdapter(ArrayList<ModelHalal> listHalal, Context context) {
        this.listHalal = listHalal;
        this.context = context;
    }

    @Override
    public ViewHOlderHalal onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_halal,parent,false);
        ViewHOlderHalal view = new ViewHOlderHalal(viewItem);
        return view;
    }

    @Override
    public void onBindViewHolder(ViewHOlderHalal holder, int position) {
        holder.txtIdProduk.setText(listHalal.get(position).getIdProduk());
        holder.txtProduk.setText(listHalal.get(position).getNamaProduk());
        holder.txtMasaBerlaku.setText(listHalal.get(position).getTanggal());
        holder.txtProdusen.setText(listHalal.get(position).getNamaProdusen());

    }

    @Override
    public int getItemCount() {
        return listHalal.size();
    }

    public class ViewHOlderHalal extends RecyclerView.ViewHolder {
        TextView txtProduk;
        TextView txtProdusen;
        TextView txtIdProduk;
        TextView txtMasaBerlaku;

        public ViewHOlderHalal(View itemView) {
            super(itemView);
            txtIdProduk = (TextView) itemView.findViewById(R.id.txt_idperusahaan);
            txtProduk = (TextView) itemView.findViewById(R.id.txt_namaproduk);
            txtProdusen = (TextView) itemView.findViewById(R.id.txt_namaprodusen);
            txtMasaBerlaku = (TextView) itemView.findViewById(R.id.tgl_kadaluarsa);
        }
    }
}
