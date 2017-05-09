package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.POLINE;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 采购接收
 */
public class PoLineListAdapter extends BaseQuickAdapter<POLINE> {
    public PoLineListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, POLINE item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.polinenum_text_id, item.getPOLINENUM());
        helper.setText(R.id.item_desc_text, item.getITEMNUM());
        helper.setText(R.id.desc_text, item.getDESCRIPTION());
        helper.setText(R.id.storeloc_text_id, item.getSTORELOC());
        helper.setText(R.id.orderqty_text_id, item.getORDERQTY());
    }


}
