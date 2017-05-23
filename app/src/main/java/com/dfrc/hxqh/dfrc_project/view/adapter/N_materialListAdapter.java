package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.N_MATERIAL;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 申请领用物料明细
 */
public class N_materialListAdapter extends BaseQuickAdapter<N_MATERIAL> {
    public N_materialListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, N_MATERIAL item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.wlbm_text_id, item.getITEMNUM());
        helper.setText(R.id.desc_text_id, item.getITEM_DESCRIPTION());
        helper.setText(R.id.n_sap3_text_id, item.getN_SAP3());
        helper.setText(R.id.curbal_text_id, item.getCURBAL());
        helper.setText(R.id.binnum_text_id, item.getDESCRIPTION1());
        helper.setText(R.id.n_reason_text_id, item.getN_REASON());
    }


}
