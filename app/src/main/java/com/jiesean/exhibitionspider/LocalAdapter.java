package com.jiesean.exhibitionspider;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Jiesean on 2017/4/18.
 */

public class LocalAdapter extends BaseQuickAdapter<LocalBean, BaseViewHolder> {

    private Context context;

    public LocalAdapter(Context context) {
        super(R.layout.item);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalBean bean) {
        helper.setText(R.id.title_tv, bean.getTitle())
                .setText(R.id.cost_tv, bean.getCost())
                .setText(R.id.time_tv, bean.getTime())
                .setText(R.id.location_tv, bean.getLocation())
                .setText(R.id.tag_tv, bean.getTag())
                .addOnClickListener(R.id.title_tv)
                .addOnClickListener(R.id.image_iv);
        Glide.with(context).load(bean.getImgURL()).crossFade().override(600, 200).centerCrop().into((ImageView) helper.getView(R.id.image_iv));
    }
}
