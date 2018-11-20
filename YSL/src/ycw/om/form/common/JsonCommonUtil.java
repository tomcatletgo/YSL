package ycw.om.form.common;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * json共通类
 * @author nextseeker.com
 *
 */
public class JsonCommonUtil {
  
	/**
	 * list to json
	 * @param list
	 * @return
	 */
	public static String listToJson(List<?> list) {
	 
		return JSONArray.fromObject(list).toString();
	} 
	
	/**
	 * javabean to json
	 * @param bean
	 * @return
	 */
	public static String beanToJson(Object bean) {
		 
		return JSONObject.fromObject(bean).toString();
	} 
	
	public static Object jsonToBean(String reqJsonStr,Class<?> c){
		
		JSONObject o = JSONObject.fromObject(reqJsonStr);
	 
		Object rObj = JSONObject.toBean(o, c);
		
		return rObj;
		
	}
}
