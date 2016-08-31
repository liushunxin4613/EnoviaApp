package com.topgun.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.kobjects.base64.Base64;

import android.app.Activity;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;

public class IOUtil {

	public static final String TAG = "IOUtil";
	public static final int FILE_SELECT_CODE = 0X111;
	public static final String ERROR_ONE = "IO ERROR";

	//获得指定文件的byte数组 
	public static byte[] getBytes(String filePath){  
		byte[] buffer = null;  
		try {  
			File file = new File(filePath);  
			FileInputStream fis = new FileInputStream(file);  
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
			byte[] b = new byte[1000];  
			int n;  
			while ((n = fis.read(b)) != -1) {  
				bos.write(b, 0, n);  
			}  
			fis.close();  
			bos.close();  
			buffer = bos.toByteArray();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return buffer;  
	}  


	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	//根据byte数组，生成文件 
	public static void getFile(byte[] bfile, String filePath,String fileName) {  
		BufferedOutputStream bos = null;  
		FileOutputStream fos = null;  
		File file = null;  
		try {  
			File dir = new File(filePath);  
			if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
				dir.mkdirs();  
			}  
			file = new File(filePath+"\\"+fileName);  
			fos = new FileOutputStream(file);  
			bos = new BufferedOutputStream(fos);  
			bos.write(bfile);  
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally {  
			if (bos != null) {  
				try {  
					bos.close();  
				} catch (IOException e1) {  
					e1.printStackTrace();  
				}  
			}  
			if (fos != null) {  
				try {  
					fos.close();  
				} catch (IOException e1) {  
					e1.printStackTrace();  
				}  
			}  
		}  
	} 

	/** 
	 * 判断SDCard是否存在 [当没有外挂SD卡时，内置ROM也被识别为存在sd卡] 
	 *  
	 * @return 
	 */  
	public static boolean isSdCardExist() {  
		return Environment.getExternalStorageState().equals(  
				Environment.MEDIA_MOUNTED);  
	}  

	/** 
	 * 获取SD卡根目录路径 
	 *  
	 * @return 
	 */  
	public static String getSdCardPath() {  
		boolean exist = isSdCardExist();  
		String sdpath = "";  
		if (exist) {  
			sdpath = Environment.getExternalStorageDirectory()  
					.getAbsolutePath();  
		} else {  
			sdpath = ERROR_ONE;  
		}  
		return sdpath;  
	}

	/** 
	 * 获取默认的文件路径 
	 *  
	 * @return 
	 */  
	public static String getDefaultFilePath(String fileName) {  
		String filepath = null;  
		File file = new File(Environment.getExternalStorageDirectory(),  
				fileName);  
		if (file.exists()) {  
			filepath = file.getAbsolutePath();  
		} else {  
			filepath = ERROR_ONE;  
		}  
		return filepath;  
	} 

	/** 
	 * 获取默认的文件路径 
	 *  
	 * @return 
	 */  
	public static File getDefaultFile() {  
		File file = Environment.getExternalStorageDirectory();  
		return file;  
	} 
	public static String getDefaultFileStr() {  
		String filepath = null;  
		File file = new File(Environment.getExternalStorageDirectory(), "/EnoviaApp");  
		if (!file.exists()) {  
			file.mkdirs();
		}
		filepath = file.getAbsolutePath();
		return filepath; 
	} 

	/** 
	 * 获取默认的文件路径 
	 *  
	 * @return 
	 */  
	public static String getFilePath(File file,String log) { 
		String filePath = null;
		if (log == null) {
			log = "file";
		}
		if (file.exists()) {
			filePath = file.getPath();
			Log.i(TAG, log + " filePath : " + file.getPath());
		}else {
			Log.i(TAG, log + " error");			
		}
		return filePath;
	}

	public static String OpenSystemFile(Activity activity,String s) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		try {
			activity.startActivityForResult(Intent.createChooser(intent,s),
					FILE_SELECT_CODE);
		} catch (android.content.ActivityNotFoundException ex) {
			return "";
		}
		return null;
	}

	@SuppressWarnings("unused")
	public static String fileToString(String filePath) {
		try{
			FileInputStream fis = new FileInputStream(filePath);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[2048];
			int count = 0;
			int index = 0;
			String s = "";
			String uploadBuffer = "";
			boolean is = true;
			while(((count = fis.read(buffer))>=0) && is){
				baos.write(buffer,0,count);
				index++;
				if (index >= FileUtil.SOAP_MAX) {
					is = false;
				}
			}
			if (is) {
				uploadBuffer = new String(Base64.encode(baos.toByteArray()));
			}else {
				uploadBuffer = "errorMax";
			}
			Log.i(TAG, " *******************     " + uploadBuffer.length() + "        ***************************** ");
			fis.close();
			return uploadBuffer;
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;

	}
	
	public static String getBase64(String s){
		if (s == null) return null;
		byte[] b = s.getBytes();
		return Base64.encode(b);
	}

}
