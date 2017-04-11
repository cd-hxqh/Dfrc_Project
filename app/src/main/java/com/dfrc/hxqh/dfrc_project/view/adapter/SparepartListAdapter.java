package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.SPAREPART;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 设备备件
 */
public class SparepartListAdapter extends BaseQuickAdapter<SPAREPART> {
    public SparepartListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, SPAREPART item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.itemnum_text_id, item.getITEMNUM());
        helper.setText(R.id.itemnum_description_text_id, item.getITEMNUM_DESCRIPTION());
        helper.setText(R.id.n_location_text_id, item.getN_LOCATION());
        helper.setText(R.id.n_brand_text_id, item.getN_BRAND());
        helper.setText(R.id.n_model_text_id, item.getN_MODELNUM());
        helper.setText(R.id.quantity_text_id, item.getQUANTITY());
    }


}
