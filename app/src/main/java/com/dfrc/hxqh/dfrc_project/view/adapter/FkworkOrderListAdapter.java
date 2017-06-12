package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.FKWORKORDER;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 分库领料单
 */
public class FkworkOrderListAdapter extends BaseQuickAdapter<FKWORKORDER> {
    public FkworkOrderListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, FKWORKORDER item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.worknum_text_id, mContext.getString(R.string.lld_text)+item.getWONUM());
        helper.setText(R.id.item_desc_text, mContext.getString(R.string.desc_text)+item.getDESCRIPTION());
        helper.setText(R.id.status_text_id, "状态\n"+item.getSTATUS());
        helper.setText(R.id.reportdate_text_id, "报告日期\n"+item.getREPORTDATE());
        helper.setText(R.id.crewid_text_id, "班组\n"+item.getCREWID());
        helper.setText(R.id.reportedbyid_text_id, "申请人\n"+item.getREPORTEDBYNAME());
    }


}
