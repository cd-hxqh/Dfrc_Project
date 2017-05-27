package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.LOCATIONS;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 库房选择
 */
public class LocationListAdapter extends BaseQuickAdapter<LOCATIONS> {
    public LocationListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, LOCATIONS item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.title1_text_id, mContext.getString(R.string.location1_text));
        helper.setText(R.id.persionid_text_id, item.getLOCATION());
        helper.setText(R.id.title2_text_id,  mContext.getString(R.string.desc_text));
        helper.setText(R.id.displaname_text_id, item.getDESCRIPTION());
        helper.setText(R.id.title3_text_id, mContext.getString(R.string.siteid_text));
        helper.setText(R.id.bz_text_id, item.getSITEID());
    }


}
