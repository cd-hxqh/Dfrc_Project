package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.PERSON;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 设备查询
 */
public class PersonListAdapter extends BaseQuickAdapter<PERSON> {
    public PersonListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, PERSON item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.persionid_text_id, item.getPERSONID());
        helper.setText(R.id.displaname_text_id, item.getDISPLAYNAME());
        helper.setText(R.id.bz_text_id, item.getN_CREWID());
    }


}
