package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.PO;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 采购接收
 */
public class PoListAdapter extends BaseQuickAdapter<PO> {
    public PoListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, PO item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.workorder_sb_title, mContext.getString(R.string.ddh_text));
        helper.setText(R.id.assetnum_text_id, item.getPONUM());
        helper.setText(R.id.item_desc_text, item.getDESCRIPTION());
        helper.setText(R.id.status_title_id, mContext.getString(R.string.receipts_text));
        helper.setText(R.id.status_text_id, item.getRECEIPTS());
        helper.setText(R.id.family_displayname_text_id, item.getSHIPTOPERSON_DISPLAYNAME());
    }


}
