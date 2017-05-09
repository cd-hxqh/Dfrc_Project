package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.INVBALANCES;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 库存余量
 */
public class Invbalances1ListAdapter extends BaseQuickAdapter<INVBALANCES> {
    public Invbalances1ListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, INVBALANCES item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.item_text_id, item.getITEMNUM());
        helper.setText(R.id.desc_text_id, item.getDESCRIPTION());
        helper.setText(R.id.quantity_text_id, item.getCURBALTOTAL());
    }


}
