package com.so7.mudah.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.so7.mudah.DetailInfoActivity;
import com.so7.mudah.Model.InfoModel;
import com.so7.mudah.R;

import java.util.ArrayList;

/**
 * Created by Rizal Y on 6/4/2017.
 */

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHOlderInfo>{
    ArrayList<InfoModel> listInfo;
    Context context;

    public InfoAdapter(ArrayList<InfoModel> listInfo, Context context) {
        this.listInfo = listInfo;
        this.context = context;
    }

    @Override
    public ViewHOlderInfo onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info,parent,false);
        ViewHOlderInfo view = new ViewHOlderInfo(viewItem);
        return view;
    }

    @Override
    public void onBindViewHolder(ViewHOlderInfo holder, int position) {
        holder.title.setText(listInfo.get(position+1).getTitle());
        Glide.with(context).load("http://mudahapp.herokuapp.com/img/"+listInfo.get(position+1).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return listInfo.size()-1;
    }

    public class ViewHOlderInfo extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;


        public ViewHOlderInfo(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_info_title);
            image = (ImageView) itemView.findViewById(R.id.item_info_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    Intent intent = new Intent(v.getContext(), DetailInfoActivity.class);
                    b.putString("title", listInfo.get(getAdapterPosition()+1).getTitle());
                    b.putString("image", listInfo.get(getAdapterPosition()+1).getImage());
                    b.putString("body", listInfo.get(getAdapterPosition()+1).getBody());
                    intent.putExtras(b);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
