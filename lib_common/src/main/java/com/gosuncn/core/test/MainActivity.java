/*
package com.gosuncn.core.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gosuncn.core.R;
import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.core.event.CommonEvent;
import com.gosuncn.core.event.IEvent;
import com.gosuncn.core.utils.AnimationUtil;
import com.gosuncn.core.utils.SDCardUtil;
import com.gosuncn.core.utils.ScreenUtil;
import com.gosuncn.core.utils.helpers.DownloadAPKHelper;
import com.gosuncn.core.utils.helpers.HeadPortraitHelper;

import java.io.File;

import de.greenrobot.event.EventBus;
import uk.co.senab.photoview.PhotoView;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button showLoadingDialogBtn;
    private Button shakeBtn;//摇头动画
    private Button snapshotBtn;//截屏
    private PhotoView imgPView;//可伸缩图

    private Button takePhotoBtn;//拍照裁剪
    private Button openAlbumBtn;//相册裁剪
    private ImageView cicleIView;//圆形图
    private HeadPortraitHelper headPortraitHelper;


    private Button downloadapkBtn;//下载apk
    private Button sweetalertBtn;//对话框
    private Button jacksonBtn;//json解析
    private Button volleyBtn;//volley网络请求
    private Button ormBtn;//ormlite数据库


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        headPortraitHelper=new HeadPortraitHelper(this, SDCardUtil.getSDCardPath()+"CORE");
        initView();
    }

    private void initView() {
        showLoadingDialogBtn = (Button) findViewById(R.id.btn_main_showloadingdialog);
        shakeBtn = (Button) findViewById(R.id.btn_main_anim_shake);
        takePhotoBtn = (Button) findViewById(R.id.btn_main_take_photo);
        openAlbumBtn = (Button) findViewById(R.id.btn_main_open_album);
        cicleIView = (ImageView) findViewById(R.id.civ_main_circleview);
        imgPView = (PhotoView) findViewById(R.id.pv_main_show_img);
        snapshotBtn = (Button) findViewById(R.id.btn_snapshot);
        downloadapkBtn = (Button) findViewById(R.id.btn_main_downloadapk);
        sweetalertBtn = (Button) findViewById(R.id.btn_main_sweetalert);
        jacksonBtn = (Button) findViewById(R.id.btn_main_jacksontest);
        volleyBtn = (Button) findViewById(R.id.btn_main_volleytest);
        ormBtn = (Button) findViewById(R.id.btn_main_ormlitetest);
        showLoadingDialogBtn.setOnClickListener(this);
        shakeBtn.setOnClickListener(this);
        takePhotoBtn.setOnClickListener(this);
        openAlbumBtn.setOnClickListener(this);
        snapshotBtn.setOnClickListener(this);
        downloadapkBtn.setOnClickListener(this);
        sweetalertBtn.setOnClickListener(this);
        jacksonBtn.setOnClickListener(this);
        volleyBtn.setOnClickListener(this);
        ormBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_main_showloadingdialog:
                showLoadingDialog();
                break;
            case R.id.btn_main_anim_shake:
                shakeBtn.startAnimation(AnimationUtil.shakeAnimation(5));
                break;
            case R.id.btn_main_take_photo:
                headPortraitHelper.takePhoto("123.png");
                break;
            case R.id.btn_main_open_album:
                headPortraitHelper.openAlbum();
                break;
            case R.id.btn_snapshot:
                imgPView.setImageBitmap(ScreenUtil.snapShotWithStatusBar(this));
                break;
            case R.id.btn_main_downloadapk:
                DownloadAPKHelper downloadUtil=new DownloadAPKHelper(this,"http://gdown.baidu.com/data/wisegame/55dc62995fe9ba82/jinritoutiao_448.apk");
                File dir=new File(Environment.getExternalStorageDirectory().toString()+"/a");
                if(!dir.exists()){
                    dir.mkdir();
                }
                File file=new File(dir,"syun.apk");
                downloadUtil.setSavePath(file);
                downloadUtil.startDownload();
                break;
            case R.id.btn_main_sweetalert:
                intent=new Intent(this,SweetAlertActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_main_jacksontest:
                intent=new Intent(this,JacksonActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_main_volleytest:
                intent=new Intent(this,VolleyActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_main_ormlitetest:
                intent=new Intent(this,OrmLiteActivity.class);
                startActivity(intent);
                break;
        }
    }

    //头像裁剪---------------------------------------------
    public void onEventMainThread(CommonEvent<Bitmap> event){
        if(event.code== IEvent.CROP_SUCCESS){
            cicleIView.setImageBitmap(event.t);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        headPortraitHelper.onActivityResult(requestCode, resultCode, data);
        //super.onActivityResult(arg0, arg1, arg2);
    }
    //---------------------------------------------头像裁剪
}
*/
