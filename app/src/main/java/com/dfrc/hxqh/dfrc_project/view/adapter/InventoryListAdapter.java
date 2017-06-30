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
 * 库存查询
 */
public class InventoryListAdapter extends BaseQuickAdapter<INVBALANCES> {
    public InventoryListAdapter(Context context, int layoutResId, List data) {
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
        helper.setText(R.id.assetnum_text_id, item.getITEMNUM());
        helper.setText(R.id.item_desc_text, item.getITEMNUMNAME());
        helper.setText(R.id.location_text_id,item.getLOCATION2());
        helper.setText(R.id.curbal_text_id, "余量:" + item.getCURBAL());
        if(null==item.getBINNUM()){
            helper.setText(R.id.binnum_text_id, "货柜:无");
        }else {
            helper.setText(R.id.binnum_text_id, "货柜:" + item.getBINNUM());
        }
        helper.setText(R.id.siteid_text_id, "地点:" + item.getSITEID());
    }


}
