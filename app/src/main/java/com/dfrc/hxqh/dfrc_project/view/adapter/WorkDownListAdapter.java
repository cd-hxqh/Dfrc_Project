package com.dfrc.hxqh.dfrc_project.view.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;
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
        helper.setOnClickListener(R.id.down_btn_id, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView statusText = helper.getView(R.id.workorder_status_id);
                View pb = helper.getView(R.id.pb);
                downOnClickListener.cDownOnClickListener(helper.getPosition(), statusText, pb);
//                helper.setVisible(R.id.workorder_status_id, false);
//                helper.setVisible(R.id.pb, true);
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
