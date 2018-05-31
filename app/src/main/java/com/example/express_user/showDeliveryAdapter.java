package com.example.express_user;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class showDeliveryAdapter extends RecyclerView.Adapter<showDeliveryAdapter.ViewHolder> {
    private Context mContext;
    private List<Delivery> mDeliveryList;
    private List<Boolean> isShowQR = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView deliveryPhoneNum,deliveryNum,deliveryLoc;
        ImageView showQRCode;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            deliveryPhoneNum = itemView.findViewById(R.id.delivery_phoneNum);
            deliveryNum = itemView.findViewById(R.id.delivery_Num);
            deliveryLoc = itemView.findViewById(R.id.delivery_Location);
            showQRCode = itemView.findViewById(R.id.QRCode);
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
        final View view = LayoutInflater.from(mContext).inflate(R.layout.delivery_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                if (isShowQR.get(position)) {
                    viewHolder.showQRCode.setVisibility(View.GONE);
                    isShowQR.set(position, false);
                    performAnimate(v,602,202);
                } else {
                    viewHolder.showQRCode.setVisibility(View.VISIBLE);
                    isShowQR.set(position, true);
                    performAnimate(v,202,602);
                }
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
        String QRtext = delivery.getLocation() + "#" + delivery.getRandomCode();
        Bitmap bitmap = ZxingUtils.createBitmap(QRtext);
        holder.showQRCode.setImageBitmap(bitmap);
        isShowQR.add(false);
    }

    @Override
    public int getItemCount() {
        return mDeliveryList.size();
    }

    private void performAnimate(final View target,final int start,final int end){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator mEvaluator = new IntEvaluator();
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();//获得当前进度占整个动画过程的比例，浮点型，0-1之间
                target.getLayoutParams().height = mEvaluator.evaluate(fraction,start,end);
                target.requestLayout();
            }
        });
        valueAnimator.setDuration(500).start();
    }
}
