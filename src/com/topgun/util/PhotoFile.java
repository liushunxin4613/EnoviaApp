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
		intent.setDataAndType(uri, "image/*");// mUriÊÇÒÑ¾­Ñ¡ÔñµÄÍ¼Æ¬Uri
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 4);// ²Ã¼ô¿ò±ÈÀý
		intent.putExtra("aspectY", 3);
		intent.putExtra("outputX", Config.UPLOAD_IMAGE_WIDTH);// Êä³öÍ¼Æ¬´óÐ¡
		intent.putExtra("outputY", Config.UPLOAD_IMAGE_HEIGHT);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);// Êä³öÍ¼Æ¬µÄUri
		intent.putExtra("return-data", false);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

		return intent;
	}

	public void clean(){
		// É¾³ýÅÄÕÕ²ÐÓàÕÕÆ¬
		if (tempFile != null && tempFile.exists())
			tempFile.delete();
	}
}
