package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.ZKWORKORDER;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 总库领料单
 */
public class ZkworkOrderListAdapter extends BaseQuickAdapter<ZKWORKORDER> {
    public ZkworkOrderListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, ZKWORKORDER item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.workorder_sb_title, mContext.getString(R.string.lld_text));
        helper.setText(R.id.assetnum_text_id, item.getWONUM());
        helper.setText(R.id.item_desc_text, item.getDESCRIPTION());
        helper.setText(R.id.status_text_id, mContext.getString(R.string.status_text)+item.getSTATUS());
        helper.setText(R.id.reportdate_text_id, mContext.getString(R.string.reportdate_text)+item.getREPORTDATE());
        helper.setText(R.id.reportedbyid_text_id,  mContext.getString(R.string.tbr_text)+item.getREPORTEDBYNAME());
        helper.setText(R.id.crewid_text_id,  mContext.getString(R.string.bz_text)+item.getCREWID());
    }


}
