package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.MATRECTRANS;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 物料接收
 */
public class WljsListAdapter extends BaseQuickAdapter<MATRECTRANS> {
    public WljsListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, MATRECTRANS item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.polinenum_text_id, item.getPOLINENUM());
        helper.setText(R.id.item_desc_text, item.getITEMNUM());
        helper.setText(R.id.desc_text, item.getDESCRIPTION());
        helper.setText(R.id.storeloc_title_id, mContext.getString(R.string.issue_type_text));
        helper.setText(R.id.storeloc_text_id, item.getISSUETYPE());
        helper.setText(R.id.orderqty_text_id, item.getRECEIPTQUANTITY());
    }


}
