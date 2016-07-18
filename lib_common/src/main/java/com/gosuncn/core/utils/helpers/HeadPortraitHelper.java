package com.gosuncn.core.utils.helpers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * 头像裁剪助手
 *
 * @author HWJ
 */
public class HeadPortraitHelper {

    interface HeadPortraitCropResultListener {
        /**
         * 头像裁剪结果
         *
         * @param isSuccess 是否成功
         * @param bitmap    裁剪成功后的头像
         */
        void headPortraitCropResult(boolean isSuccess, Bitmap bitmap);
    }

    //保存图片本地路径
    public static final String ACCOUNT_DIR = Environment.getExternalStorageDirectory().getPath()
            + "/account/";
    public static final String ACCOUNT_MAINTRANCE_ICON_CACHE = "icon_cache/";
    private static String IMGPATH = ACCOUNT_DIR + ACCOUNT_MAINTRANCE_ICON_CACHE;

    private static String IMAGE_FILE_NAME = "faceImage.jpeg";
    private static String TMP_IMAGE_FILE_NAME = "tmp_faceImage.jpeg";

    //常量定义
    /**
     * 拍照
     */
    public static final int TAKE_A_PICTURE = 10;
    /**
     * 选择
     */
    public static final int SELECT_A_PICTURE = 20;
    /**
     * 裁剪
     */
    public static final int SET_PICTURE = 30;
    /**
     * 裁剪（kitkat之前）
     */
    public static final int SET_ALBUM_PICTURE_KITKAT = 40;
    /**
     * 选择（kitkat之后）
     */
    public static final int SELECET_A_PICTURE_AFTER_KITKAT = 50;
    private String mAlbumPicturePath = null;

    //版本比较：是否是4.4及以上版本
    final boolean mIsKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    private Activity context;
    private int width = 200;
    private int height = 200;
    private HeadPortraitCropResultListener listener;

    public HeadPortraitHelper(Activity context) {
        this.context = context;
    }

    /**
     * 构造函数
     *
     * @param context
     * @param dir     头像保存目录
     */
    public HeadPortraitHelper(Activity context, String dir) {
        this(context);
        this.IMGPATH = dir;
    }

    /**
     * 设置头像裁剪结果监听器
     *
     * @param listener
     */
    public void setHeadPortraitCropResultListener(HeadPortraitCropResultListener listener) {
        this.listener = listener;
    }


    /**
     * 设置裁剪图片的宽高，默认为200*200
     *
     * @param width
     * @param height
     */
    public void setImageSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * 调用系统拍照
     */
    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(IMGPATH, IMAGE_FILE_NAME)));
        context.startActivityForResult(intent, TAKE_A_PICTURE);
    }

    /**
     * 调用系统拍照
     *
     * @param picName 图片名称
     */
    public void takePhoto(String picName) {
        this.IMAGE_FILE_NAME = picName;
        this.TMP_IMAGE_FILE_NAME = "tmp_" + picName;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(IMGPATH, IMAGE_FILE_NAME)));
        context.startActivityForResult(intent, TAKE_A_PICTURE);
    }

    /**
     * 打开系统相册
     */
    public void openAlbum() {
        if (mIsKitKat) {
            selectImageUriAfterKikat();
        } else {
            cropImageUri();
        }
    }

    /**
     * 需要在相应的Activity中的onActivityResult中调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SELECT_A_PICTURE:
                if (resultCode == context.RESULT_OK && null != data) {
                    //				Log.i("zou", "4.4以下的");
                    Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH,
                            TMP_IMAGE_FILE_NAME)));
                    if (listener != null) {
                        if (bitmap != null) {
                            listener.headPortraitCropResult(true, bitmap);
                        } else {
                            listener.headPortraitCropResult(false, bitmap);
                        }
                    }
                } else if (resultCode == context.RESULT_CANCELED) {
                    Toast.makeText(context, "取消头像设置", Toast.LENGTH_SHORT).show();
                }
                break;
            case SELECET_A_PICTURE_AFTER_KITKAT:
                if (resultCode == context.RESULT_OK && null != data) {
                    mAlbumPicturePath = getPath(context.getApplicationContext(), data.getData());
                    cropImageUriAfterKikat(Uri.fromFile(new File(mAlbumPicturePath)));
                } else if (resultCode == context.RESULT_CANCELED) {
                    Toast.makeText(context, "取消头像设置", Toast.LENGTH_SHORT).show();
                }
                break;
            case SET_ALBUM_PICTURE_KITKAT:
                Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH, TMP_IMAGE_FILE_NAME)));
                if (listener != null) {
                    if (bitmap != null) {
                        listener.headPortraitCropResult(true, bitmap);
                    } else {
                        listener.headPortraitCropResult(false, bitmap);
                    }
                }
                break;
            case TAKE_A_PICTURE:
                if (resultCode == context.RESULT_OK) {
                    cameraCropImageUri(Uri.fromFile(new File(IMGPATH, IMAGE_FILE_NAME)));
                } else {
                    Toast.makeText(context, "取消头像设置", Toast.LENGTH_SHORT).show();
                }
                break;
            case SET_PICTURE:
                Bitmap bitmap1 = null;
                if (resultCode == context.RESULT_OK && null != data) {
                    bitmap1 = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH, IMAGE_FILE_NAME)));
                    if (listener != null) {
                        if (bitmap1 != null) {
                            listener.headPortraitCropResult(true, bitmap1);
                        } else {
                            listener.headPortraitCropResult(false, bitmap1);
                        }
                    }
                } else if (resultCode == context.RESULT_CANCELED) {
                    Toast.makeText(context, "取消头像设置", Toast.LENGTH_SHORT).show();
                } else {
                    if (listener != null) {
                        listener.headPortraitCropResult(false, bitmap1);
                    }
                }
                break;
        }
    }

    /**
     * 裁剪图片
     */
    private void cropImageUri() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(IMGPATH, TMP_IMAGE_FILE_NAME)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        context.startActivityForResult(intent, SELECT_A_PICTURE);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void selectImageUriAfterKikat() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        context.startActivityForResult(intent, SELECET_A_PICTURE_AFTER_KITKAT);
    }

    /**
     * 裁剪图片
     *
     * @param uri
     */
    private void cameraCropImageUri(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        context.startActivityForResult(intent, SET_PICTURE);
    }

    private void cropImageUriAfterKikat(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        //		intent.putExtra("return-data", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(IMGPATH, TMP_IMAGE_FILE_NAME)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        context.startActivityForResult(intent, SET_ALBUM_PICTURE_KITKAT);
    }


    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 4.4及以上获取图片的路径
     *
     * @param context
     * @param uri
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private String getDataColumn(Context context, Uri uri, String selection,
                                 String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
