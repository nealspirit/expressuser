package com.example.express_user;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class showDeliveryAdapter extends RecyclerView.Adapter<showDeliveryAdapter.ViewHolder> {
    private Context mContext;
    private List<Delivery> mDeliveryList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView deliveryPhoneNum,deliveryNum,deliveryLoc;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            deliveryPhoneNum = itemView.findViewById(R.id.delivery_phoneNum);
            deliveryNum = itemView.findViewById(R.id.delivery_Num);
            deliveryLoc = itemView.findViewById(R.id.delivery_Location);
        }
    }

    public showDeliveryAdapter(List<Delivery> deliveryList){
        mDeliveryList = deliveryList;
    }

    @NonNull
    @Override
    public showDeliveryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.delivery_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Delivery delivery = mDeliveryList.get(position);
                String delivery_Num = delivery.getdeliveryNum();
                String delivery_PhoneNum = delivery.getPhoneNum();
                String delivery_location = delivery.getLocation();
                String delivery_code = delivery.getRandomCode();
                Intent intent = new Intent(mContext,QRcodeActivity.class);
                intent.putExtra(QRcodeActivity.DELIVERY_NUM,delivery_Num);
                intent.putExtra(QRcodeActivity.DELIVERY_PHONENUM,delivery_PhoneNum);
                intent.putExtra(QRcodeActivity.DELIVERY_LOCATION,delivery_location);
                intent.putExtra(QRcodeActivity.DELIVERY_RANDOMCODE,delivery_code);
                mContext.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull showDeliveryAdapter.ViewHolder holder, int position) {
        Delivery delivery = mDeliveryList.get(position);
        holder.deliveryPhoneNum.setText(delivery.getPhoneNum());
        holder.deliveryNum.setText(delivery.getdeliveryNum());
        String hang = delivery.getLocation().split(" ")[0];
        String lie = delivery.getLocation().split(" ")[1];
        holder.deliveryLoc.setText(hang + "行" + lie + "列");
    }

    @Override
    public int getItemCount() {
        return mDeliveryList.size();
    }
}
