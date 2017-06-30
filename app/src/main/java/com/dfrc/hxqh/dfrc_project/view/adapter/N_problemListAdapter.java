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
 * 问题点管理
 */
public class N_problemListAdapter extends BaseQuickAdapter<N_PROBLEM> {
    public N_problemListAdapter(Context context, int layoutResId, List data) {
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
        helper.setText(R.id.workorder_sb_title, mContext.getString(R.string.num_text));
        helper.setText(R.id.assetnum_text_id, item.getN_PROBLEMNUM());
        helper.setText(R.id.item_desc_text, item.getPRODESC());
        helper.setText(R.id.sbmc_text_id, item.getASSET_DESCRIPTION());
        helper.setText(R.id.status_text_id, item.getSTATUS());
        helper.setText(R.id.family_displayname_text_id, item.getRESPONSOR());
    }


}
