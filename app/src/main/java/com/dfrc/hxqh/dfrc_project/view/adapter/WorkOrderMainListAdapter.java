package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.WORKORDER;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 定期点检工单
 */
public class WorkOrderMainListAdapter extends BaseQuickAdapter<WORKORDER> {
    public WorkOrderMainListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, WORKORDER item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.workorder_wonum_id, item.getWONUM());
        helper.setText(R.id.workorder_desc_text_id, item.getDESCRIPTION());
    }


}
