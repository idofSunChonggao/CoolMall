package com.nmsl.coolmall.mine.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nmsl.coolmall.R;
import com.nmsl.coolmall.mine.model.BillBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author NoOne 2019.03.10
 */
public class ProfitAdapter extends BaseQuickAdapter<BillBean, BaseViewHolder> {

    SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm");

    public ProfitAdapter() {
        super(R.layout.item_profit);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillBean item) {
        helper.setText(R.id.date_tv, mDateFormat.format(new Date(item.getTime())));
        helper.setText(R.id.time_tv, mTimeFormat.format(new Date(item.getTime())));
        if (item.getType() == 0) {
            helper.setText(R.id.profit_tv, "+" + item.getMoneyNum());
            helper.setTextColor(R.id.profit_tv, mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            helper.setText(R.id.profit_tv, "-" + item.getMoneyNum());
            helper.setTextColor(R.id.profit_tv, mContext.getResources().getColor(R.color.colorPrimaryText));
        }

    }
}
