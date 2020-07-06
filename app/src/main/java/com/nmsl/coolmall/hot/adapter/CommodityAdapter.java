package com.nmsl.coolmall.hot.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nmsl.coolmall.R;
import com.nmsl.coolmall.core.image.GlideHelper;
import com.nmsl.coolmall.core.model.NewCommodityBean;

/**
 * @author NoOne 2019.03.09
 */
public class CommodityAdapter extends BaseQuickAdapter<NewCommodityBean, BaseViewHolder> {

    public CommodityAdapter() {
        super(R.layout.item_commodity);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewCommodityBean item) {
        if (item == null) {
            return;
        }
        GlideHelper.loadImage(mContext, item.getImage(), (ImageView) helper.getView(R.id.thumbnail_iv));
        helper.setText(R.id.name_tv, item.getName());
        helper.setText(R.id.intro_tv, item.getCouponIntro());
        helper.setText(R.id.price_after_tv, item.getPrice());
        helper.setText(R.id.price_before_tv, item.getPrice());
        helper.setText(R.id.discount_coupon_tv, "￥" + item.getPriceTicket() + "券");
    }
}
