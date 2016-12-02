package com.gosuncn.cu.module.other;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.core.utils.BitmapUtil;
import com.gosuncn.core.utils.helpers.LocationHelper;
import com.gosuncn.core.utils.helpers.VersionUpdateManager;
import com.gosuncn.cu.R;

public class OtherActivity extends BaseActivity {
    private static final String TAG = "OtherActivity";

    LocationHelper locationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();//测试全屏
        showShortToast("屏幕宽高：" + getScreenWidth() + "*" + getScreenHeight());
        Log.e(TAG,"屏幕宽高:"+getScreenWidth() + "*" + getScreenHeight());
        setContentView(R.layout.activity_other);
        VersionUpdateManager.getInstance().checkUpdateWithDialog(this);//测试版本更新

        testMergeBitmap();
        locationHelper = new LocationHelper(this);
        locationHelper.setOnLocationChangeListener(new LocationHelper.OnLocationChangeListener() {
            @Override
            public void onLocationChange(Location location) {
                String locationStr = "纬度：" + location.getLatitude() + "\n"
                        + "经度：" + location.getLongitude()+ "\n"
                        + "海拔：" + location.getAltitude();
                Toast.makeText(context, locationStr, Toast.LENGTH_SHORT).show();
            }
        });

        testLocation();
    }

    @Override
    protected void onDestroy() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "请允许定位权限", Toast.LENGTH_SHORT).show();
        }else{
            locationHelper.close();
        }
        super.onDestroy();
    }

    private void testMergeBitmap() {
        ImageView img = (ImageView) findViewById(R.id.iv_img);
        ImageView img2 = (ImageView) findViewById(R.id.iv_img2);
        ImageView preview = (ImageView) findViewById(R.id.iv_preview);
        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        Bitmap bitmap2 = ((BitmapDrawable) img2.getDrawable()).getBitmap();

        preview.setImageBitmap(BitmapUtil.mergeBitmap(bitmap, bitmap2));
    }

    private void testLocation() {
        if (locationHelper.prepare()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "请允许定位权限", Toast.LENGTH_SHORT).show();
                return;
            }
            locationHelper.startLocation();
        }else{
            Toast.makeText(context, "无法定位,可能是由于未打开GPS定位", Toast.LENGTH_SHORT).show();
        }
    }

}
