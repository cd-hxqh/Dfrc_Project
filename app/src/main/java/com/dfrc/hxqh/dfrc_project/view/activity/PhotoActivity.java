package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.api.HttpManager;
import com.dfrc.hxqh.dfrc_project.api.HttpRequestHandler;
import com.dfrc.hxqh.dfrc_project.api.JsonUtils;
import com.dfrc.hxqh.dfrc_project.bean.Results;
import com.dfrc.hxqh.dfrc_project.constants.Constants;
import com.dfrc.hxqh.dfrc_project.dialog.FlippingLoadingDialog;
import com.dfrc.hxqh.dfrc_project.model.DOCLINKS;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.view.adapter.ImageLoadAdapter;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 图片选择*
 */
public class PhotoActivity extends BaseActivity implements ImageLoadAdapter.OnRecyclerViewItemClickListener {

    private static final String TAG = "PhotoActivity";

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;


    private ImageLoadAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数


    private static final String URL_STRING = "/share/doclinks/";

    private static final int PICK_PHOTO = 1;

    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮
    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.text_id)
    TextView textView; //text_hint

    @Bind(R.id.title_add)
    ImageView uploadImgeView; //附件上传


    /**
     * 表名*
     */
    private String ownertable;
    /**
     * 主键ID*
     */
    private String ownerid;

    protected FlippingLoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo1);
        ButterKnife.bind(this);
        getInitData();

        findViewById();
        initView();
        initWidget();
        getLoadingDialog("获取图标").show();
        getData();


    }

    private void getInitData() {
        ownertable = getIntent().getExtras().getString("ownertable");
        ownerid = getIntent().getExtras().getString("ownerid");
    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {
        titleTextView.setText(getString(R.string.attachment_details));
        uploadImgeView.setImageResource(R.mipmap.add);
        uploadImgeView.setVisibility(View.VISIBLE);

    }

    //选择需要上传的图片
    @OnClick(R.id.title_add)
    void setUploadBtnOnClickListener() {
        Intent intent = getIntent();
        intent.setClass(PhotoActivity.this, WxDemoActivity.class);
        intent.putExtra("isCamera", 1);
        startActivityForResult(intent, 0);
    }


    private void initWidget() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImageLoadAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }


    private void showResult(ArrayList<ImageItem> paths) {

        adapter.setImages(paths);
        if (paths == null || paths.size() == 0) {
            textView.setVisibility(View.VISIBLE);

        } else {
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
//            case IMAGE_ITEM_ADD:
//                //打开选择,本次允许选择的数量
//                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
//                Intent intent = new Intent(this, ImageGridActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_SELECT);
//                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra("results", ImagePicker.SERVER_IMAGE_ITEMS);
                intentPreview.putExtra("IMAGE_URL", AccountUtils.getIpAddress(this) + Constants.WORK_FLOW_URL);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "resultCode=" + resultCode + ",requestCode=" + requestCode);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selImageList.addAll(images);
                adapter.setImages(selImageList);
                if (adapter.getItemCount() != 0) {
                    textView.setVisibility(View.GONE);
                } else {
                    textView.setVisibility(View.VISIBLE);
                }

            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);

                selImageList.clear();
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        }
    }


    private void getData() {
        HttpManager.getData(this, HttpManager.getDoclinks(ownertable, ownerid), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                mLoadingDialog.dismiss();
                ArrayList<DOCLINKS> item = JsonUtils.parsingDOCLINKS(results.getResultlist());
                if (item == null || item.isEmpty()) {
                    textView.setVisibility(View.VISIBLE);
                } else {
                    if (item != null || item.size() != 0) {
                        textView.setVisibility(View.GONE);
                        for (int i = 0; i < item.size(); i++) {
                            String url = item.get(i).getURLNAME();
                            if (url != null) {
                                //拼接图片的URL
                                ImageItem imageItem = new ImageItem();
                                imageItem.path = getUrls(url);
                                imageItem.name = item.get(i).getDOCINFOID();
                                selImageList.add(imageItem);
                            }
                        }
                        if (selImageList != null || selImageList.size() != 0) {
                            showResult(selImageList);
                        } else {
                            textView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                mLoadingDialog.dismiss();
            }

            @Override
            public void onFailure(String error) {
                mLoadingDialog.dismiss();
            }
        });
    }


    /**
     * 拼接图片的URl
     **/
    private String getUrls(String url) {
        //测试环境的附件
        String ip_url = AccountUtils.getIpAddress(PhotoActivity.this);
        String imagesUrl = Constants.BASE_URL;
        url = url.replace(URL_STRING, "");


        return imagesUrl + url;
    }


    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }


}