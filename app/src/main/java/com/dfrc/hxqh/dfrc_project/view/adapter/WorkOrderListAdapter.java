package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.CompoundButton;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.WORKORDER;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 定期点检工单
 */
public class WorkOrderListAdapter extends BaseQuickAdapter<WORKORDER> {

    private boolean allChoose;

    /**
     * 选中事件*
     */
    public OnCheckedChangeListener onCheckedChangeListener;

    public WorkOrderListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final WORKORDER item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.workorder_wonum_id, item.getWONUM());
        helper.setText(R.id.workorder_desc_text_id, item.getDESCRIPTION());
        helper.setText(R.id.workorder_status_id, item.getSTATUS());
        if (item.getN_QTYOPEN() == null) {
            helper.setText(R.id.n_qtyopen_text_id, "未完成:" + "0");
        } else {
            helper.setText(R.id.n_qtyopen_text_id, "未完成:" + item.getN_QTYOPEN().replace(",", ""));
        }
        if (item.getN_QTYCOMP() == null) {
            helper.setText(R.id.n_qtycomp_text_id, "已完成:" + "0");
        } else {
            helper.setText(R.id.n_qtycomp_text_id, "已完成:" + item.getN_QTYCOMP().replace(",", ""));
        }

        helper.setChecked(R.id.check_box_id, allChoose);

        helper.setOnCheckedChangeListener(R.id.check_box_id, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                onCheckedChangeListener.cOnCheckedChangeListener(b, helper.getPosition(), item);
            }
        });



    }

    /**
     * 设置全选
     **/
    public void setAll(boolean b) {
        allChoose = b;
    }

    public interface OnCheckedChangeListener {
        public void cOnCheckedChangeListener(boolean b, int postion,WORKORDER item);
    }

    public OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }
}
