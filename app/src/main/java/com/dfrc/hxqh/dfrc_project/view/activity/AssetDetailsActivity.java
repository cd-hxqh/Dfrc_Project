package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.ASSET;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 设备详情
 */
public class AssetDetailsActivity extends BaseActivity {
    private static String TAG = "AssetDetailsActivity";


    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮

    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.title_add)
    ImageView menuImageView; //菜单
    PopupWindow popupWindow;
    @Bind(R.id.assetnum_text_id)
    TextView assetnumTextView; //设备编码
    @Bind(R.id.description_text_id)
    TextView descriptionTextView; //设备描述
    @Bind(R.id.location_text_id)
    TextView locationTextView; //位置编码
    @Bind(R.id.loc_desc_text_id)
    TextView locdescTextView; //位置描述
    @Bind(R.id.n_mantype_text_id)
    TextView n_mantypeTextView; //管理类型
    @Bind(R.id.n_specialtype_text_id)
    TextView n_specialtypeTextView; //设备类型
    @Bind(R.id.n_importance_text_id)
    TextView n_importanceTextView; //重要程度
    @Bind(R.id.n_crewid_text_id)
    TextView n_crewidTextView; //使用班组
    @Bind(R.id.n_userdepartment_text_id)
    TextView n_userdepartmentTextView; //使用部门
    @Bind(R.id.n_management_text_id)
    TextView n_managementTextView; //管理部门
    @Bind(R.id.n_assetnum_text_id)
    TextView n_assetnumTextView; //资产编码
    @Bind(R.id.n_brand_text_id)
    TextView n_brandTextView; //品牌
    @Bind(R.id.n_model_text_id)
    TextView n_modelTextView; //规格型号
    @Bind(R.id.n_sort_text_id)
    TextView n_sortTextView; //设备分类
    @Bind(R.id.n_releasedate_text_id)
    TextView n_releasedateTextView; //出厂日期
    @Bind(R.id.n_makername_text_id)
    TextView n_makernameTextView; //制造厂商
    @Bind(R.id.n_providername_text_id)
    TextView n_providernameTextView; //供应商
    @Bind(R.id.n_minitabdate_text_id)
    TextView n_minitabdateTextView; //启用日期
    @Bind(R.id.n_recordate_text_id)
    TextView n_recordateTextView; //等记日期
    @Bind(R.id.n_cardid_text_id)
    TextView n_cardidTextView; //使用证号
    @Bind(R.id.n_bonuscode_text_id)
    TextView n_bonuscodeTextView; //注册代码
    @Bind(R.id.n_dcequipment_text_id)
    CheckBox n_dcequipmentCheckBox; //数控设备
    @Bind(R.id.n_pcequipment_text_id)
    CheckBox n_pcequipmentCheckBox; //精密设备
    @Bind(R.id.n_ipequipment_text_id)
    CheckBox n_ipequipmentCheckBox; //进口设备
    @Bind(R.id.n_virtual_text_id)
    CheckBox n_virtualCheckBox; //虚拟设备
    @Bind(R.id.status_text_id)
    TextView statusTextView; //状态
    @Bind(R.id.siteid_text_id)
    TextView siteidTextView; //地点

    private ASSET asset;

    LinearLayout bejjianLinearLayout; //备件
    LinearLayout djLinearLayout; //点击问题点记录
    LinearLayout gzLinearLayout; //故障记录


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        asset = (ASSET) getIntent().getSerializableExtra("asset");
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.asset_detail_text);
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setImageResource(R.mipmap.ic_more);
        if (asset != null) {
            assetnumTextView.setText(asset.getASSETNUM());
            descriptionTextView.setText(asset.getDESCRIPTION());
            locationTextView.setText(asset.getLOCATION());
            locdescTextView.setText(asset.getLOCATION_DESCRIPTION());
            n_mantypeTextView.setText(asset.getN_MANTYPE());
            n_specialtypeTextView.setText(asset.getN_SPECIALTYPE());
            n_importanceTextView.setText(asset.getN_IMPORTANCE());
            n_crewidTextView.setText(asset.getN_CREWID());
            n_userdepartmentTextView.setText(asset.getN_USERDEPARTMENT());
            n_managementTextView.setText(asset.getN_MANAGEMENT());
            n_assetnumTextView.setText(asset.getN_ASSETNUM());
            n_brandTextView.setText(asset.getN_BRAND());
            n_modelTextView.setText(asset.getN_MODEL());
            n_sortTextView.setText(asset.getN_SORT());
            n_releasedateTextView.setText(asset.getN_RELEASEDATE());
            n_makernameTextView.setText(asset.getN_MAKERNAME());
            n_providernameTextView.setText(asset.getN_PROVIDERNAME());
            n_minitabdateTextView.setText(asset.getN_MINITABDATE());
            n_recordateTextView.setText(asset.getN_RECORDATE());
            n_cardidTextView.setText(asset.getN_CARDID());
            n_bonuscodeTextView.setText(asset.getN_BONUSCODE());
            if (asset.getN_DCEQUIPMENT().equals("1")) {
                n_dcequipmentCheckBox.setChecked(true);
            } else {
                n_dcequipmentCheckBox.setChecked(false);
            }
            if (asset.getN_PCEQUIPMENT().equals("1")) {
                n_pcequipmentCheckBox.setChecked(true);
            } else {
                n_pcequipmentCheckBox.setChecked(false);
            }
            if (asset.getN_IPEQUIPMENT().equals("1")) {
                n_ipequipmentCheckBox.setChecked(true);
            } else {
                n_ipequipmentCheckBox.setChecked(false);
            }
            if (asset.getN_VIRTUAL().equals("1")) {
                n_virtualCheckBox.setChecked(true);
            } else {
                n_virtualCheckBox.setChecked(false);
            }
            statusTextView.setText(asset.getSTATUS());
            siteidTextView.setText(asset.getSITEID());

        }

    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }

    //菜单事件
    @OnClick(R.id.title_add)
    void setMenuImageViewOnClickListener() {
        showPopupWindow(menuImageView);
    }


    /**
     * 初始化showPopupWindow*
     */
    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(AssetDetailsActivity.this).inflate(
                R.layout.asset_popup_window, null);


        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.popup_background_mtrl_mult));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);
        bejjianLinearLayout = (LinearLayout) contentView.findViewById(R.id.beijian_linearlayout_id);
        djLinearLayout = (LinearLayout) contentView.findViewById(R.id.djwtdjl_linearlayout_id);
        gzLinearLayout = (LinearLayout) contentView.findViewById(R.id.gzjl_linearlayout_id);
        bejjianLinearLayout.setOnClickListener(beijianOnClickListener);
        djLinearLayout.setOnClickListener(djOnClickListener);
        gzLinearLayout.setOnClickListener(gzOnClickListener);

    }

    private View.OnClickListener beijianOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AssetDetailsActivity.this, SparepartActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("assetnum", asset.getASSETNUM());
            intent.putExtras(bundle);
            startActivityForResult(intent, 1000);
            popupWindow.dismiss();
        }
    };
    private View.OnClickListener djOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AssetDetailsActivity.this, N_problem1Activity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("assetnum", asset.getASSETNUM());
            intent.putExtras(bundle);
            startActivityForResult(intent, 1000);
            popupWindow.dismiss();
        }
    };
    private View.OnClickListener gzOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AssetDetailsActivity.this, GzWorkorderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("assetnum", asset.getASSETNUM());
            intent.putExtras(bundle);
            startActivityForResult(intent, 1000);
            popupWindow.dismiss();
        }
    };

}
