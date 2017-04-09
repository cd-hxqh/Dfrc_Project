package com.dfrc.hxqh.dfrc_project.view.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.ASSET;


/**
 * 设备详情
 */
public class AssetDetailsActivity extends BaseActivity {
    private static String TAG = "AssetDetailsActivity";

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    /**
     * 菜单
     **/
    private ImageView menuImageView;
    private PopupWindow popupWindow;

    private TextView assetnumTextView; //设备编码
    private TextView descriptionTextView; //设备描述
    private TextView locationTextView; //位置编码
    private TextView locdescTextView; //位置描述
    private TextView n_mantypeTextView; //管理类型
    private TextView n_specialtypeTextView; //设备类型
    private TextView n_importanceTextView; //重要程度
    private TextView n_crewidTextView; //使用班组
    private TextView n_userdepartmentTextView; //使用部门
    private TextView n_managementTextView; //管理部门
    private TextView n_assetnumTextView; //资产编码
    private TextView n_brandTextView; //品牌
    private TextView n_modelTextView; //规格型号
    private TextView n_sortTextView; //设备分类
    private TextView n_releasedateTextView; //出厂日期
    private TextView n_makernameTextView; //制造厂商
    private TextView n_providernameTextView; //供应商
    private TextView n_minitabdateTextView; //启用日期
    private TextView n_recordateTextView; //等记日期
    private TextView n_cardidTextView; //使用证号
    private TextView n_bonuscodeTextView; //注册代码
    private CheckBox n_dcequipmentCheckBox; //数控设备
    private CheckBox n_pcequipmentCheckBox; //精密设备
    private CheckBox n_ipequipmentCheckBox; //进口设备
    private CheckBox n_virtualCheckBox; //虚拟设备
    private TextView statusTextView; //状态
    private TextView siteidTextView; //地点

    private ASSET asset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_details);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        asset = (ASSET) getIntent().getSerializableExtra("asset");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        menuImageView = (ImageView) findViewById(R.id.title_add);

        assetnumTextView = (TextView) findViewById(R.id.assetnum_text_id);
        descriptionTextView = (TextView) findViewById(R.id.description_text_id);
        locationTextView = (TextView) findViewById(R.id.location_text_id);
        locdescTextView = (TextView) findViewById(R.id.loc_desc_text_id);
        n_mantypeTextView = (TextView) findViewById(R.id.n_mantype_text_id);
        n_specialtypeTextView = (TextView) findViewById(R.id.n_specialtype_text_id);
        n_importanceTextView = (TextView) findViewById(R.id.n_importance_text_id);
        n_crewidTextView = (TextView) findViewById(R.id.n_crewid_text_id);
        n_userdepartmentTextView = (TextView) findViewById(R.id.n_userdepartment_text_id);
        n_managementTextView = (TextView) findViewById(R.id.n_management_text_id);
        n_assetnumTextView = (TextView) findViewById(R.id.n_assetnum_text_id);
        n_brandTextView = (TextView) findViewById(R.id.n_brand_text_id);
        n_modelTextView = (TextView) findViewById(R.id.n_model_text_id);
        n_sortTextView = (TextView) findViewById(R.id.n_sort_text_id);
        n_releasedateTextView = (TextView) findViewById(R.id.n_releasedate_text_id);
        n_makernameTextView = (TextView) findViewById(R.id.n_makername_text_id);
        n_providernameTextView = (TextView) findViewById(R.id.n_providername_text_id);
        n_minitabdateTextView = (TextView) findViewById(R.id.n_minitabdate_text_id);
        n_recordateTextView = (TextView) findViewById(R.id.n_recordate_text_id);
        n_cardidTextView = (TextView) findViewById(R.id.n_cardid_text_id);
        n_bonuscodeTextView = (TextView) findViewById(R.id.n_bonuscode_text_id);
        n_dcequipmentCheckBox = (CheckBox) findViewById(R.id.n_dcequipment_text_id);
        n_pcequipmentCheckBox = (CheckBox) findViewById(R.id.n_pcequipment_text_id);
        n_ipequipmentCheckBox = (CheckBox) findViewById(R.id.n_ipequipment_text_id);
        n_virtualCheckBox = (CheckBox) findViewById(R.id.n_virtual_text_id);
        statusTextView = (TextView) findViewById(R.id.status_text_id);
        siteidTextView = (TextView) findViewById(R.id.siteid_text_id);


    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.asset_detail_text);
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setImageResource(R.mipmap.ic_more);
        menuImageView.setOnClickListener(menuImageViewOnClickListener);
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


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener menuImageViewOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showPopupWindow(menuImageView);
        }
    };



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

//        invbalancesLayout = (LinearLayout) contentView.findViewById(R.id.invbalances_id);
//        invbalancesLayout.setOnClickListener(invbalancesOnClickListener);

    }

//    private View.OnClickListener invbalancesOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Intent intent = new Intent(InventoryDetailsActivity.this, Inventory_InvbalancesActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("inventory", inventory);
////            bundle.putSerializable("woactivityList", woactivityList);
//            intent.putExtras(bundle);
//            startActivityForResult(intent, 1000);
//            popupWindow.dismiss();
//        }
//    };

}
