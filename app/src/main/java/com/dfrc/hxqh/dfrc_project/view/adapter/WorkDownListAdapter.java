package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.WORKORDER;
import com.dfrc.hxqh.dfrc_project.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 定期点检工单
 */
public class WorkDownListAdapter extends BaseQuickAdapter<WORKORDER> {
    private static final String TAG="WorkDownListAdapter";
    /**
     * 下载事件
     **/
    public DownOnClickListener downOnClickListener;


    public WorkDownListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(final BaseViewHolder helper, WORKORDER item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.workorder_wonum_id, item.getWONUM());
        helper.setText(R.id.workorder_desc_text_id, item.getDESCRIPTION());
        helper.setText(R.id.n_qtyopen_text_id, "未完成:" + item.getN_QTYOPEN());
        helper.setText(R.id.n_qtycomp_text_id, "已完成:" + item.getN_QTYCOMP());
        if (null != item.getDOWNSTATUS() && item.getDOWNSTATUS().equals("已下载")) {
            helper.setText(R.id.workorder_status_id, "已下载");
            helper.setTextColorRes(R.id.workorder_status_id, R.color.red);
        }else{
            helper.setText(R.id.workorder_status_id, "未下载");
            helper.setTextColorRes(R.id.workorder_status_id, R.color.black);
        }

        helper.setOnClickListener(R.id.down_btn_id, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView statusText = helper.getView(R.id.workorder_status_id);
                View pb = helper.getView(R.id.pb);
                downOnClickListener.cDownOnClickListener(helper.getPosition(), statusText, pb);
            }
        });
    }

    public interface DownOnClickListener {
        public void cDownOnClickListener(int postion, TextView statusText, View pb);
    }

    public DownOnClickListener getDownOnClickListener() {
        return downOnClickListener;
    }

    public void setDownOnClickListener(DownOnClickListener downOnClickListener) {
        this.downOnClickListener = downOnClickListener;
    }
}
