package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.N_MATERIAL;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 申请领用物料明细
 */
public class N_materialListAdapter extends BaseQuickAdapter<N_MATERIAL> {

    private static final String TAG = "N_materialListAdapter";

    /**
     * 点击事件
     */
    public OnClickListener onclicklistener;

    public N_materialListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(final BaseViewHolder helper, N_MATERIAL item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.wlbm_text_id, item.getITEMNUM());
        helper.setText(R.id.desc_text_id, item.getITEM_DESCRIPTION());
        helper.setText(R.id.curbal_text_id, item.getCURBAL());
        helper.setText(R.id.n_sap5_text_id, item.getN_SAP5());
        helper.setText(R.id.n_sap3_text_id, item.getN_SAP3());
        helper.setText(R.id.fk_tobin_text_id, item.getTOBIN());
        helper.setText(R.id.zk_frombin_id, item.getFROMBIN());
        helper.setText(R.id.n_reason_text_id, item.getN_REASON());
        if (item.getTID().equals("N")) {
            helper.setChecked(R.id.tid_checkbox_id, false);
        } else if (item.getTID().equals("Y")) {
            helper.setChecked(R.id.tid_checkbox_id, true);
        }

        EditText n_sap3 = (EditText) helper.getView(R.id.n_sap3_text_id);
        n_sap3.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onclicklistener.cOnClickListener(helper.getPosition(), s.toString());
            }
        });

    }


    public interface OnClickListener {
        public void cOnClickListener(int postion, String t);
    }

    public OnClickListener getOnclicklistener() {
        return onclicklistener;
    }

    public void setOnclicklistener(OnClickListener onclicklistener) {
        this.onclicklistener = onclicklistener;
    }
}
