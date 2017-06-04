package com.so7.mudah;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailInfoActivity extends AppCompatActivity {
    TextView titleDetail;
    TextView bodyDetail;
    ImageView imgDetail;
    String title,image,body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        titleDetail = (TextView)findViewById(R.id.textv_title_newsdetail);
        bodyDetail = (TextView)findViewById(R.id.textv_isi_newsdetail);
        imgDetail = (ImageView) findViewById(R.id.image_newsdetail);

        setupToolbar();
        title = getIntent().getExtras().getString("title");
        image = getIntent().getExtras().getString("image");
        body = getIntent().getExtras().getString("body");

        titleDetail.setText(title);
        bodyDetail.setText(Html.fromHtml(body));
        Glide.with(this).load("http://mudahapp.herokuapp.com/img/"+image).into(imgDetail);
    }
    private void setupToolbar() {
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar.setTitle("Detail info");
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.lightPrimaryCollor));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.transparent));
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
