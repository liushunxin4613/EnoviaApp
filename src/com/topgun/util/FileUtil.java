package com.topgun.util;

import java.io.File;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapPrimitive;

import com.topgun.enoviaapp.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

@SuppressLint("DefaultLocale")
public class FileUtil {

	public static final String TAG = "FileUtil";
	
	public static int SOAP_MAX = 1024;

	public static void isNull(Object o,String s){
		if (o == null) {
			Log.i(TAG,s + " null ");
		}else {
			Log.i(TAG,s + " not null ");
		}
	}

	public static long getFilelength(String filePath){
		File file = new File(filePath);
		if (file.exists()) {
			return file.length();
		}
		return 0;
	}
	
	@SuppressLint("SdCardPath")
	public static String getUriToPath(Uri fileUrl,Context context){
		String filePath = null;
		String key = "";
		Uri filePathUri = fileUrl;
		if (fileUrl != null){
			if (fileUrl.getScheme().toString().compareTo("content") == 0){
				Cursor cursor = context.getContentResolver().query(fileUrl, null, null, null, null);
				if (cursor != null && cursor.moveToFirst()){
					int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					filePath = cursor.getString(column_index);
					if (!filePath.startsWith("/storage") && !filePath.startsWith(key)){
						filePath = key + filePath;
					}
					cursor.close();
				}
			}else if (fileUrl.getScheme().compareTo("file") == 0){
				filePath = filePathUri.toString();
				filePath = filePathUri.toString().replace("file://","");
			}
		}
		return filePath;
	}

	public static SoapPrimitive getSoapPrimitive(String byteStr){
		SoapPrimitive sb = new SoapPrimitive(SoapEnvelope.ENC,"base64Binary",byteStr);
		return sb;
	}
	
	public static String getFileName(String filePath){
		File file = new File(filePath);
		if (file.exists()) {
			Log.i(TAG, file.getName());
			return file.getName();
		}
		return null;
	}

	public static String getFileNameNo(String filePath){
		String s[] = filePath.split("/");
		Log.i(TAG, s[s.length-1]);
		return s[s.length-1];
	}

	/*public static String getUriToPath(String fileUriPath,Context context){
		String filePath = null;
		String key = "";
		String ss = fileUriPath;
		if (ss != null){
			Log.i(TAG, "ss "+ss);
			String sk[] = ss.split("/");
			String s = sk[0].split(":")[0];
			if (s.equals("content")){
				Cursor cursor = context.getContentResolver().query(Uri.parse(fileUriPath), null, null, null, null);
				if (cursor != null && cursor.moveToFirst()){
					int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					filePath = cursor.getString(column_index);
					if (!filePath.startsWith("/storage") && !filePath.startsWith(key)){
						filePath = key + filePath;
					}
					cursor.close();
				}
			}else if (s.equals("file")){
				filePath = ss.toString().replace("file://","");
			}
		}
		return filePath;
	}*/



	@SuppressLint("DefaultLocale")
	public static String getMIMEType(String fileName){
		String type="*";
		String end = getSuffix(fileName);
		for(int i=0;i<MIME_MapTable.length;i++){
			if(end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	@SuppressLint("DefaultLocale")
	public static String getSuffix(String fileName){
		String type="*";
		String fName = fileName;
		int dotIndex = fName.lastIndexOf(".");
		if(dotIndex < 0){
			return type;
		}
		String end = fName.substring(dotIndex,fName.length()).toLowerCase();
		if(end == "") return type;
		return end;
	}

	public static void openFile(String path,Activity activity){
		if (activity == null) return;
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
//		String type = getMIMEType(path);
		String type = getMimeType(path);
		File file = new File(path);
		intent.setDataAndType(Uri.fromFile(file), type);
		try {
			activity.startActivity(intent);
		} catch (Exception e) {
			UIUtil.showToast(activity, R.string.file_select_error);
		}
		
	}
	
	public static String getMimeType(String url){
	    String type = null;
	    String extension = MimeTypeMap.getFileExtensionFromUrl(url);
	    if (extension != null) {
	        MimeTypeMap mime = MimeTypeMap.getSingleton();
	        type = mime.getMimeTypeFromExtension(extension);
	    }
	    return type;
	}

	public static final String[][] MIME_MapTable={
		{".3gp",    "video/3gpp"},
		{".apk",    "application/vnd.android.package-archive"},
		{".asf",    "video/x-ms-asf"},
		{".avi",    "video/x-msvideo"},
		{".bin",    "application/octet-stream"},
		{".bmp",      "image/bmp"},
		{".c",        "text/plain"},
		{".class",    "application/octet-stream"},
		{".conf",    "text/plain"},
		{".cpp",    "text/plain"},
		{".doc",    "application/msword"},
		{".exe",    "application/octet-stream"},
		{".gif",    "image/gif"},
		{".gtar",    "application/x-gtar"},
		{".gz",        "application/x-gzip"},
		{".h",        "text/plain"},
		{".htm",    "text/html"},
		{".html",    "text/html"},
		{".jar",    "application/java-archive"},
		{".java",    "text/plain"},
		{".jpeg",    "image/jpeg"},
		{".jpg",    "image/jpeg"},
		{".js",        "application/x-javascript"},
		{".log",    "text/plain"},
		{".m3u",    "audio/x-mpegurl"},
		{".m4a",    "audio/mp4a-latm"},
		{".m4b",    "audio/mp4a-latm"},
		{".m4p",    "audio/mp4a-latm"},
		{".m4u",    "video/vnd.mpegurl"},
		{".m4v",    "video/x-m4v"},    
		{".mov",    "video/quicktime"},
		{".mp2",    "audio/x-mpeg"},
		{".mp3",    "audio/x-mpeg"},
		{".mp4",    "video/mp4"},
		{".mpc",    "application/vnd.mpohun.certificate"},        
		{".mpe",    "video/mpeg"},    
		{".mpeg",    "video/mpeg"},    
		{".mpg",    "video/mpeg"},    
		{".mpg4",    "video/mp4"},    
		{".mpga",    "audio/mpeg"},
		{".msg",    "application/vnd.ms-outlook"},
		{".ogg",    "audio/ogg"},
		{".pdf",    "application/pdf"},
		{".png",    "image/png"},
		{".pps",    "application/vnd.ms-powerpoint"},
		{".ppt",    "application/vnd.ms-powerpoint"},
		{".prop",    "text/plain"},
		{".rar",    "application/x-rar-compressed"},
		{".rc",        "text/plain"},
		{".rmvb",    "audio/x-pn-realaudio"},
		{".rtf",    "application/rtf"},
		{".sh",        "text/plain"},
		{".tar",    "application/x-tar"},    
		{".tgz",    "application/x-compressed"}, 
		{".txt",    "text/plain"},
		{".wav",    "audio/x-wav"},
		{".wma",    "audio/x-ms-wma"},
		{".wmv",    "audio/x-ms-wmv"},
		{".wps",    "application/vnd.ms-works"},
		{".xml",    "text/xml"},
		{".xml",    "text/plain"},
		{".z",        "application/x-compress"},
		{".zip",    "application/zip"},
		{".xlsx",    "application/zip"},
		{"",        "*/*"}
	};
}
