package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.POLINE;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 选择已订购项目
 */
public class PoLineChooseListAdapter extends BaseQuickAdapter<POLINE> {

    /**
     * 选中事件*
     */
    public OnCheckedChangeListener onCheckedChangeListener;


    public PoLineChooseListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(final BaseViewHolder helper, POLINE item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.polinenum_text_id, item.getPOLINENUM());
        helper.setText(R.id.item_desc_text, item.getITEMNUM());
        helper.setText(R.id.desc_text, item.getDESCRIPTION());
        helper.setText(R.id.storeloc_text_id, item.getSTORELOC());
        helper.setText(R.id.dgl_text_id, item.getORDERQTY());
        helper.setText(R.id.receivedqty_text_id, Integer.valueOf(item.getORDERQTY()) - Integer.valueOf(item.getRECEIVEDQTY()) + "");
        final EditText t = (EditText) helper.getView(R.id.receivedqty_text_id);
        final EditText hg = (EditText) helper.getView(R.id.binnum_text_id);
        helper.setOnCheckedChangeListener(R.id.checkbox_text_id, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                onCheckedChangeListener.cOnCheckedChangeListener(b, helper.getPosition(), t.getText().toString(),hg.getText().toString());
            }
        });


    }

    public interface OnCheckedChangeListener {
        public void cOnCheckedChangeListener(boolean b, int postion, String t,String hg);
    }

    public OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }
}
