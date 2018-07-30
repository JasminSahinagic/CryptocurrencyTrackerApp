package com.example.cryptocurrencytrackerapp.cryptocurrencytrackerapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cryptocurrencytrackerapp.cryptocurrencytrackerapp.CryptoCurrencyDetails;
import com.example.cryptocurrencytrackerapp.cryptocurrencytrackerapp.Model.CryptoCurrencyModel;
import com.example.cryptocurrencytrackerapp.cryptocurrencytrackerapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CryptoCurrencyAdapter extends RecyclerView.Adapter<CryptoCurrencyAdapter.ViewHolder> {

    private Context context;
    private List<CryptoCurrencyModel> cryptoCurrencyModelList;
    private static final String IMAGE_URL="http://res.cloudinary.com/dxi90ksom/image/upload/";


    public CryptoCurrencyAdapter(Context context, List<CryptoCurrencyModel> cryptoCurrencyModelList) {
        this.context = context;
        this.cryptoCurrencyModelList = cryptoCurrencyModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cryptocurrency_row,parent,false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CryptoCurrencyModel model = cryptoCurrencyModelList.get(position);
        holder.cryptoCurrencyName.setText(model.getName());
        holder.cryptoCurrencySymbol.setText(model.getSymbol());
        holder.cryptoCurrencyPriceUsd.setText(model.getPriceUsd());
        holder.oneHour.setText(model.getPercentChange1h());
        holder.twentyFourHour.setText(model.getPercentChange24h());
        holder.sevenDay.setText(model.getPercentChange7d());
        Picasso.with(context).load(new StringBuilder(IMAGE_URL).append(model.getSymbol().toLowerCase()).append(".png").toString())
                .into(holder.cryptoCurrencyIcon);
    }

    @Override
    public int getItemCount() {
        return cryptoCurrencyModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView cryptoCurrencySymbol;
        public TextView cryptoCurrencyName;
        public TextView cryptoCurrencyPriceUsd;
        public TextView oneHour;
        public TextView twentyFourHour;
        public TextView sevenDay;
        public ImageView cryptoCurrencyIcon;

        public ViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            cryptoCurrencySymbol = (TextView)itemView.findViewById(R.id.cryptoCurrencySymbol);
            cryptoCurrencyName = (TextView)itemView.findViewById(R.id.cryptoCurrencyName);
            oneHour = (TextView)itemView.findViewById(R.id.oneHour);
            cryptoCurrencyPriceUsd = (TextView)itemView.findViewById(R.id.cryptoCurrencyPriceUsd);
            twentyFourHour = (TextView)itemView.findViewById(R.id.twentyFourHour);
            sevenDay = (TextView)itemView.findViewById(R.id.sevenDay);
            cryptoCurrencyIcon = (ImageView)itemView.findViewById(R.id.cryptoCurrencyIcon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CryptoCurrencyModel model = cryptoCurrencyModelList.get(getAdapterPosition());
                    Intent intent = new Intent(context, CryptoCurrencyDetails.class);
                    intent.putExtra("Data",model);
                    context.startActivity(intent);
                }
            });
        }
    }
}
