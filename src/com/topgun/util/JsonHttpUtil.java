package com.topgun.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.alibaba.fastjson.JSON;
import com.topgun.model.Json;
import com.topgun.model.SoapMessage;

import Decoder.BASE64Decoder;


/**
 * 跟获取网络上Json数据有关的相关类
 * 
 * @author liusx
 *
 */
public class JsonHttpUtil {

	public static final String TAG = "JsonHttpUtil";
	public static final String CLASSNAME_SOAPPRIMITIVE = "org.ksoap2.serialization.SoapPrimitive";
	public static final String CLASSNAME_SOAPOBJECT = "org.ksoap2.serialization.SoapObject";

	/**
	 * 从网络上获取json数据
	 * @param methedName
	 * @param map
	 * @return
	 */
	public synchronized static Json queryJson(String methedName,Map<String,Object> map){
		
		UIUtil.showLogI(TAG, "savdsav", null);

		String sj = "";

		SoapObject request = new SoapObject(Config.nameSpace, methedName);

		HashMap<String,Object> hashMap = (HashMap<String, Object>) map;

		Iterator<Entry<String,Object>> iter = hashMap.entrySet().iterator();

		while (iter.hasNext()) {
			Entry<String,Object> entry = (Entry<String, Object>) iter.next();
			request.addProperty(entry.getKey(),entry.getValue());
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

		envelope.bodyOut = request;  

		envelope.setOutputSoapObject(request);   

		HttpTransportSE ht = new HttpTransportSE(Config.endPoint);  

		ht.debug = true;  

		try {  
			ht.call(Config.nameSpace+methedName,envelope); 
			if(envelope.getResponse() != null){
				Object object = envelope.getResponse();
				if (object.getClass().getName().equals(CLASSNAME_SOAPPRIMITIVE)) {
					SoapPrimitive result = (SoapPrimitive) object;
					return JSON.parseObject(result.toString(),Json.class);
				}else if(object.getClass().getName().equals(CLASSNAME_SOAPOBJECT)){
					SoapObject soapObject = (SoapObject) object;
					if (IOUtil.isSdCardExist()) {
						String rootPath = IOUtil.getDefaultFileStr();
						for (int i = 0; i < soapObject.getPropertyCount(); i++) {
							SoapObject so = (SoapObject) soapObject.getProperty(i);
							String s1 = so.getProperty("key").toString();
							if (sj.length() > 0) {
								sj = sj + "," + s1;
							}else {
								sj = s1;
							}
							
							String filePath = rootPath + "/" + s1;
							
							String s2 = so.getProperty("value").toString();
							
							File file = new File(filePath);
							if (!file.exists()) {
								BASE64Decoder decoder = new BASE64Decoder();
								try {
									//Base64解码
									byte [] b = decoder.decodeBuffer(s2);
									for(int j = 0; j < b.length; ++j) {
										if(b[j]<0) {//调整异常数据
											b[j] += 256;
										}
									}
									OutputStream out = new FileOutputStream(filePath);    
									out.write(b);
									out.flush();
									out.close();
								} catch (Exception e){
									e.printStackTrace();
								}
							}else {
							}
						}
						return new Json(Config.SUCCESS, sj);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return new Json(Config.ERROR_SERVICE, null);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return new Json(Config.ERROR_SERVICE, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Json(Config.ERROR_SERVICE, null);
		}
		return null;
	}

	/**
	 * 返回一个将User对象转换好之后的hashmap对象
	 * @param user
	 * @return
	 */
	public synchronized static HashMap<String,Object> getUserHashMap(SoapMessage soapMessage){

		UIUtil.showLogI(TAG, "d", null);
		
		HashMap<String,Object> map = new HashMap<String, Object>();

		if (null != soapMessage.getIns0()) {
			map.put("in0",soapMessage.getIns0());
		}
		if (null != soapMessage.getIns1()) {
			map.put("in1",soapMessage.getIns1());
		}
		if (null != soapMessage.getIns2()) {
			map.put("in2",soapMessage.getIns2());
		}
		if (null != soapMessage.getIns3()) {
			map.put("in3",soapMessage.getIns3());
		}
		if (null != soapMessage.getIni3()) {
			map.put("in3",soapMessage.getIni3());
		}
		if (null != soapMessage.getIns4()) {
			map.put("in4",soapMessage.getIns4());
		}
		if (null != soapMessage.getIni4()) {
			map.put("in4",soapMessage.getIni4());
		}
		if (null != soapMessage.getInsb5()) {
			map.put("in5",soapMessage.getInsb5());
		}
		
		for (int i = 0; i < map.size(); i++) {
			UIUtil.showLogI(TAG,"in" + i, map.get("in"+i));
		}
		
		return map;
	}

//	@SuppressWarnings("unchecked")
//	public static List<JSONObject> getArrayList(Context context,String json){
//		List<JSONObject> data = null;
//		if (null != json) {
//			try {
//				data = (List<JSONObject>) JSONArray.parse(json);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return data;
//	} 

}
