package com.gosuncn.core.utils.helpers;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Photo;
import android.text.TextUtils;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * 获取手机本地通讯录
 * 
 * @author HWJ
 * 
 */
public class ContactLocalHelper {

	/** 获取库Phon表字段 **/
	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };

	/** 联系人显示名称 **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;

	/** 电话号码 **/
	private static final int PHONES_NUMBER_INDEX = 1;

	/** 头像ID **/
	private static final int PHONES_PHOTO_ID_INDEX = 2;

	/** 联系人的ID **/
	private static final int PHONES_CONTACT_ID_INDEX = 3;

	/** 联系人名称 **/
	private ArrayList<String> mContactsName = new ArrayList<String>();

	/** 联系人头像 **/
	private ArrayList<String> mContactsNumber = new ArrayList<String>();

	/** 联系人头像 **/
	private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();

	private Context context;

	public ContactLocalHelper(Context context) {
		this.context = context;
	}

	/** 得到手机通讯录联系人信息 **/
	public void getPhoneContacts() {
		mContactsName.clear();
		mContactsNumber.clear();
		mContactsPhonto.clear();
		ContentResolver resolver = context.getContentResolver();

		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,
				PHONES_PROJECTION, null, null, null);

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {

				// 得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;

				// 得到联系人名称
				String contactName = phoneCursor
						.getString(PHONES_DISPLAY_NAME_INDEX);

				// 得到联系人ID
				Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);

				// 得到联系人头像ID
				Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

				// 得到联系人头像Bitamp
				Bitmap contactPhoto = null;

				// photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
				if (photoid > 0) {
					Uri uri = ContentUris.withAppendedId(
							ContactsContract.Contacts.CONTENT_URI, contactid);
					InputStream input = ContactsContract.Contacts
							.openContactPhotoInputStream(resolver, uri);
					contactPhoto = BitmapFactory.decodeStream(input);
				} else {
					contactPhoto = BitmapFactory.decodeResource(
							context.getResources(),
							android.R.drawable.ic_delete);
				}

				mContactsName.add(contactName);
				mContactsNumber.add(phoneNumber);
				mContactsPhonto.add(contactPhoto);
			}

			phoneCursor.close();
		}

	}

	/** 得到手机SIM卡联系人人信息 **/
	public void getSIMContacts() {
		mContactsName.clear();
		mContactsNumber.clear();
		mContactsPhonto.clear();
		ContentResolver resolver = context.getContentResolver();
		// 获取Sims卡联系人
		Uri uri = Uri.parse("content://icc/adn");
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,
				null);

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {

				// 得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				// 得到联系人名称
				String contactName = phoneCursor
						.getString(PHONES_DISPLAY_NAME_INDEX);

				// Sim卡中没有联系人头像
				mContactsName.add(contactName);
				mContactsNumber.add(phoneNumber);

			}

			phoneCursor.close();
		}
	}

	public ArrayList<String> getContactsName() {
		return mContactsName;
	}

	public ArrayList<String> getContactsNumber() {
		return mContactsNumber;
	}

	public ArrayList<Bitmap> getContactsPhonto() {
		return mContactsPhonto;
	}
}
