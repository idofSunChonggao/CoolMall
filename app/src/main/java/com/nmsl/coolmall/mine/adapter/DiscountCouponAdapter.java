package com.nmsl.coolmall.mine.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nmsl.coolmall.R;
import com.nmsl.coolmall.core.model.NewCommodityBean;

/**
 * @author NoOne 2019.03.10
 */
public class DiscountCouponAdapter extends BaseQuickAdapter<NewCommodityBean, BaseViewHolder> {


    public DiscountCouponAdapter() {
        super(R.layout.item_discount_coupon);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewCommodityBean item) {
        helper.setText(R.id.coupon_price_tv, item.getPriceTicket() + "元");
        helper.setText(R.id.intro_tv, item.getCouponIntro());
        helper.setText(R.id.store_name_tv, item.getName());
        helper.setText(R.id.time_tv, item.getStartPeriod()
                + "\n - "
                + item.getEndPeriod());
    }
}
