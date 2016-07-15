package com.hpw.frame.mvp.ui.girl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hpw.frame.R;
import com.hpw.frame.mvp.bean.Image;
import com.hpw.frame.widget.RatioImageView;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * 作者：杭鹏伟
 * 日期：16-7-14 12:10
 * 邮箱：424346976@qq.com
 */
public class GirlAdapter extends RecyclerView.Adapter<GirlAdapter.GirlViewHolder> implements Action1<List<Image>> {

    @Inject
    GirlAdapter() {

    }

    private List<Image> mImages = new ArrayList<>();
    private OnTouchListener onTouchListener;
    private Context mContext;

    public void bind(Context context, List<Image> images) {

        if (images != null && images.size() > 0) {
            this.mContext = context;
            this.mImages = images;
        } else {
            this.mImages.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public GirlViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GirlViewHolder holder = new GirlViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_girl, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(GirlViewHolder holder, int position) {
        Image image = mImages.get(position);

        holder.image = image;
        Glide.with(mContext)
                .load(image.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
    }

    @Override
    public int getItemViewType(int position) {
        Image image = mImages.get(position);
        return Math.round((float) image.width / (float) image.height * 10f);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    @Override
    public void call(List<Image> images) {
        notifyDataSetChanged();
    }

    public class GirlViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        RatioImageView imageView;
        Image image;

        public GirlViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            RxView.clicks(imageView)
                    .throttleFirst(1000, TimeUnit.MILLISECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            if (onTouchListener != null) {
                                onTouchListener.onImageClick(imageView, image);
                            }
                        }
                    });
        }
    }

    public interface OnTouchListener {
        void onImageClick(View v, Image image);
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }
}
