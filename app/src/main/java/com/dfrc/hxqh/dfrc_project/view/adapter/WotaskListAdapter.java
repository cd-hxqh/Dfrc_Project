package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.WOTASK;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 定期点检工单明细行
 */
public class WotaskListAdapter extends BaseQuickAdapter<WOTASK> {
    public WotaskListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, WOTASK item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.workorder_sb_title, mContext.getString(R.string.wosequence_text));
        helper.setText(R.id.assetnum_text_id, item.getWOSEQUENCE());
        helper.setText(R.id.item_desc_title, mContext.getString(R.string.assetnum_text));
        helper.setText(R.id.item_desc_text, item.getASSETNUM());
        helper.setText(R.id.status_title_id, mContext.getString(R.string.type_text));
        helper.setText(R.id.status_text_id, item.getREFBOOKLINE_TYPE());
        helper.setText(R.id.family_displayname_text_id, item.getRESPONSOR_DISPLAYNAME());
    }


}
