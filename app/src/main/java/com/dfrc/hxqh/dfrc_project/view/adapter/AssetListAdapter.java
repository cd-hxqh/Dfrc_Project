package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.ASSET;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 设备查询
 */
public class AssetListAdapter extends BaseQuickAdapter<ASSET> {
    public AssetListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, ASSET item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.assetnum_text_id, item.getASSETNUM());
        helper.setText(R.id.item_desc_text, item.getDESCRIPTION());
        helper.setText(R.id.status_text_id, item.getSTATUS());
        helper.setText(R.id.family_displayname_text_id, item.getFAMILY_DISPLAYNAME());
    }


}
