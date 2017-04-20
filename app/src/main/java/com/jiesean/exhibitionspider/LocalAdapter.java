package com.jiesean.exhibitionspider;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Jiesean on 2017/4/18.
 */

public class LocalAdapter extends BaseQuickAdapter<ExhibitionBean, BaseViewHolder> {

    private Context context;

    public LocalAdapter(Context context) {
        super(R.layout.item);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ExhibitionBean bean) {
        helper.setText(R.id.tv_title, bean.getTitle())
                .addOnClickListener(R.id.iv_primary)
                .addOnClickListener(R.id.tv_content)
                .addOnClickListener(R.id.tv_title)
                .addOnClickListener(R.id.tv_collectTag)
                .addOnClickListener(R.id.iv_avatar)
                .addOnClickListener(R.id.tv_author);
//        Glide.with(context).load(bean.getAvatarImg()).crossFade().into((ImageView) helper.getView(R.id.iv_avatar));
//        Glide.with(context).load(bean.getPrimaryImg()).crossFade().into((ImageView) helper.getView(R.id.iv_primary));
    }
}
