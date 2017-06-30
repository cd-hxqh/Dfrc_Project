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
        helper.setText(R.id.wosequence_text_id, item.getWOSEQUENCE());
        helper.setText(R.id.assetnum_text_id, item.getASSETNUM());
        helper.setText(R.id.sbms_text_id, item.getASSET_DESCRIPTION());
        helper.setText(R.id.item_text_id, item.getITEM());
        if (null != item.getN_RESULT() && item.getN_RESULT().equals("NG")) {
            helper.setTextColorRes(R.id.n_result_title_id, R.color.red);
            helper.setTextColorRes(R.id.n_result_text_id, R.color.red);
        }else{
            helper.setTextColorRes(R.id.n_result_title_id, R.color.black);
            helper.setTextColorRes(R.id.n_result_text_id, R.color.black);
        }

        helper.setText(R.id.n_result_text_id, item.getN_RESULT());
    }


}
