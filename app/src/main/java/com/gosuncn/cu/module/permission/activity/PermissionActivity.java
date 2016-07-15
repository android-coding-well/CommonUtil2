package com.gosuncn.cu.module.permission.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.cu.R;
import com.gosuncn.cu.module.permission.fragment.CameraPreviewFragment;
import com.gosuncn.cu.module.permission.fragment.ContactsFragment;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 使用第三方Dexter来处理允许时权限
 */
public class PermissionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        ButterKnife.inject(this);
    }

    void showCamera() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_content, CameraPreviewFragment.newInstance())
                .addToBackStack("camera")
                .commitAllowingStateLoss();
    }

    void showContacts() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_content, ContactsFragment.newInstance())
                .addToBackStack("contacts")
                .commitAllowingStateLoss();
    }

    public void onBackClick(View view) {
        getSupportFragmentManager().popBackStack();
    }


    @OnClick({R.id.btn_camera, R.id.btn_contacts})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_camera:
                RxPermissions.getInstance(this).request(Manifest.permission.CAMERA)
                        .subscribe(granted -> {
                            if (granted) {
                                showCamera();
                            } else {
                                showDialog("接下的操作需要拥有camera权限，请允许该权限，否则无法正常使用");
                            }
                        });
                break;
            case R.id.btn_contacts:
                //多权限经测试发现只要其中一个权限为允许或询问状态，则granted为true
                //当全部为拒绝状态时则granted为false
                RxPermissions.getInstance(this).request(Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS)
                        .subscribe(granted -> {
                            if (granted) {
                                showContacts();
                            } else {
                                showDialog("接下的操作需要拥有CONTACTS权限，请允许该权限，否则无法正常使用");
                            }
                        });
                break;
        }
    }

    private void showDialog(String msg) {
        new AlertDialog.Builder(PermissionActivity.this)
                .setMessage(msg)
                .create().show();
    }


}
