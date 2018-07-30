package com.example.cryptocurrencytrackerapp.cryptocurrencytrackerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cryptocurrencytrackerapp.cryptocurrencytrackerapp.Model.CryptoCurrencyModel;
import com.squareup.picasso.Picasso;

public class CryptoCurrencyDetails extends AppCompatActivity {

    private TextView textViewDName;
    private TextView textViewDSymbol;
    private TextView textViewDPriceUsd;
    private TextView textViewDId;
    private TextView textViewDRank;
    private TextView textViewDPriceBtc;
    private TextView textView24hVolumeUsd;
    private TextView textViewDMarketCapUsd;
    private TextView textViewDAvailableSupply;
    private TextView textViewDTotalSupply;
    private TextView textViewDMaxSupply;
    private TextView textViewPercentChange1h;
    private TextView textViewPercentChange24h;
    private TextView textViewPercentChange7d;
    private CryptoCurrencyModel model;
    private ImageView imageViewDImage;
    private static final String IMAGE_URL="http://res.cloudinary.com/dxi90ksom/image/upload/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_currency_details);
        detailSetUp();
        uI();
    }

    public void detailSetUp(){
        imageViewDImage = (ImageView) findViewById(R.id.imageViewDImage);
        textViewDName = (TextView) findViewById(R.id.textViewDName);
        textViewDSymbol = (TextView) findViewById(R.id.textViewDSymbol);
        textViewDPriceUsd = (TextView) findViewById(R.id.textViewDPriceUsd);
        textViewDId = (TextView) findViewById(R.id.textViewDId);
        textViewDRank = (TextView) findViewById(R.id.textViewDRank);
        textViewDPriceBtc = (TextView) findViewById(R.id.textViewDPriceBtc);
        textView24hVolumeUsd = (TextView) findViewById(R.id.textView24hVolumeUsd);
        textViewDMarketCapUsd = (TextView) findViewById(R.id.textViewDMarketCapUsd);
        textViewDAvailableSupply = (TextView) findViewById(R.id.textViewDAvailableSupply);
        textViewDTotalSupply = (TextView) findViewById(R.id.textViewDTotalSupply);
        textViewDMaxSupply = (TextView) findViewById(R.id.textViewDMaxSupply);
        textViewPercentChange1h = (TextView) findViewById(R.id.textViewDPercentChange1h);
        textViewPercentChange24h = (TextView) findViewById(R.id.textViewDPercentChange24h);
        textViewPercentChange7d = (TextView) findViewById(R.id.textViewDPercentChange7d);
        model = (CryptoCurrencyModel) getIntent().getSerializableExtra("Data");
    }

    public void uI(){
        textViewDName.setText("Name: "+model.getName());
        textViewDSymbol.setText("Symbol: "+model.getSymbol());
        textViewDPriceUsd.setText("Price USD: "+model.getPriceUsd()+"$");
        textViewDRank.setText("Rank:  "+model.getRank());
        textViewDPriceBtc.setText("Price BTC: "+model.getPriceBtc());
        textView24hVolumeUsd.setText("24h - Volume USD: "+model.get24hVolumeUsd());
        textViewDMarketCapUsd.setText("MarketCapUSD: "+model.getMarketCapUsd());
        textViewDAvailableSupply.setText("Available Supply: "+model.getAvailableSupply());
        textViewDTotalSupply.setText("Total Supply: "+model.getTotalSupply());
        textViewDMaxSupply.setText("Max Supply: "+String.valueOf(model.getMaxSupply()));
        textViewPercentChange1h.setText("Percente Change - 1h:   "+model.getPercentChange1h());
        textViewPercentChange24h.setText("Percente Change - 24h:   "+model.getPercentChange24h());
        textViewPercentChange7d.setText("Percente Change - 7h:   "+model.getPercentChange7d());
        Picasso.with(this).load(new StringBuilder(IMAGE_URL).append(model.getSymbol().toLowerCase()).append(".png").toString())
                .into(imageViewDImage);
    }
}
