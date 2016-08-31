package com.topgun.util;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

public class PhotoFile {

	public static final String TAG = "PhotoFile";
	private File tempFile;
	private Uri cropUri;
	private Activity activity;

	public Uri getCropUri() {
		return cropUri;
	}

	public void setCropUri(Uri cropUri) {
		this.cropUri = cropUri;
	}

	public PhotoFile(Activity activity) {
		this.activity = activity;
	}

	public Intent photoTake(Uri uri){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		return intent;
	}

	public Uri getUri(){
		File dir = activity.getExternalFilesDir(
				Environment.DIRECTORY_DCIM);
		String fileName = System.currentTimeMillis() + ".jpg";
		File tempFile = new File(dir, fileName);
		Uri tempUri = Uri.fromFile(tempFile);
		return tempUri;
	}

	public Intent cropImage(Uri uri) {
		if (null == uri){
			return null;
		}
		File dir = activity.getExternalFilesDir(Environment.DIRECTORY_DCIM);
		String fileName = System.currentTimeMillis() + ".jpg";
		File saveFile = new File(dir, fileName);
		cropUri = Uri.fromFile(saveFile);

		Intent intent = new Intent();

		intent.setAction("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");// mUri���Ѿ�ѡ���ͼƬUri
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 4);// �ü������
		intent.putExtra("aspectY", 3);
		intent.putExtra("outputX", Config.UPLOAD_IMAGE_WIDTH);// ���ͼƬ��С
		intent.putExtra("outputY", Config.UPLOAD_IMAGE_HEIGHT);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);// ���ͼƬ��Uri
		intent.putExtra("return-data", false);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

		return intent;
	}

	public void clean(){
		// ɾ�����ղ�����Ƭ
		if (tempFile != null && tempFile.exists())
			tempFile.delete();
	}
}
