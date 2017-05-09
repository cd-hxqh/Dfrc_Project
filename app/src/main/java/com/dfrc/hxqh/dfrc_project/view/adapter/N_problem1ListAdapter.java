package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.N_PROBLEM;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 点检问题点
 */
public class N_problem1ListAdapter extends BaseQuickAdapter<N_PROBLEM> {
    public N_problem1ListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, N_PROBLEM item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.wonum_text_id, item.getWONUM());
        helper.setText(R.id.item_desc_text, item.getPRODESC());
        helper.setText(R.id.finddate_text_id, item.getFINDDATE());
        helper.setText(R.id.family_displayname_text_id, item.getDISPLAYNAME());
    }


}
