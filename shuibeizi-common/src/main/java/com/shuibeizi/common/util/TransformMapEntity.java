package com.shuibeizi.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;


/**
 * @Description:Map entity相互转化
 * @author:wanqing
 * @date:2016-9-19
 */
public class TransformMapEntity {

	/**
	 * @Description:实体间填充
	 * @param resource
	 * @param tag
	 * @throws Exception
	 * @author:wanqing
	 * @date:2016-9-19 下午01:30:40
	 */
	public static void entityToEntity(Object resource, Object tag){
		if(null == resource) return;
		try {
			Object value = null;
			Field[] fields = null;
			Class tagClass = tag.getClass();
			for(;tagClass != Object.class;tagClass = tagClass.getSuperclass()){
				Field[] fields1 = tagClass.getDeclaredFields();
				fields = (Field[]) ArrayUtils.addAll(fields,fields1);
			}
			for(Field field : fields){
				field.setAccessible(true);
				try {
					Field sourceField = null;
					Class sourceClass = resource.getClass();
					for (;sourceClass != Object.class;sourceClass = sourceClass.getSuperclass()){
						try {
							sourceField = sourceClass.getDeclaredField(field.getName());
						}catch (Exception e){
							continue;
						}
					}
					if(sourceField == null) continue;
					sourceField.setAccessible(true);
					value = sourceField.get(resource);
					if(null != value){
						field.set(tag, value);
						value = null;
					}
				}catch (Exception e){
					continue;
				}

			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * @Description:map转实体类
	 * @param <T>
	 * @param resource
	 * @param tagClass
	 * @return
	 * @throws Exception
	 * @author:wanqing
	 * @date:2016-9-19 下午03:15:07
	 */
	public static <T> T mapToEntity(Map<String, Object> resource, Class<T> tagClass) throws Exception{
		if(null == resource) return null;
		T resultTag = tagClass.newInstance();
		Class superClass = tagClass;
		for(String key : resource.keySet()){
			Field field = null;
			superClass = tagClass;
			for(;superClass != Object.class;superClass = superClass.getSuperclass()){
				try {
					field = superClass.getDeclaredField(key);
					if(null != field){
						break;
					}
				} catch (Exception e) {
					continue;
				}
			}
			if(null != field && null != resource.get(key)){
				field.setAccessible(true);
				Method method = null;
				Class fieldClass = field.getType();
				Class valueClass = resource.get(key).getClass();
				if(fieldClass.getName().equals("java.math.BigDecimal")){
					field.set(resultTag, new BigDecimal(resource.get(key).toString()));
					continue;
				}
				if(!valueClass.getName().equals(fieldClass.getName())){
					method = fieldClass.getMethod("valueOf",String.class);
					Object val = resource.get(key);
					if(val.toString().equals("")){
						val = null;
						field.set(resultTag, val);
					}else {
						field.set(resultTag, method.invoke(fieldClass,val));
					}
				}else {
					field.set(resultTag, resource.get(key));
				}
			}
		}
		return resultTag;
	}

	/**
	 * 实体转Map
	 * @param entity
	 * @return
	 * @author: Administrator
	 * @date: 2017/7/17 17:45
	 * @modify: modify describe
	 */
	public static Map<String, Object> entityToMap(Object entity){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Class sourceClass = entity.getClass();
		Field[] fields = null;
		Class tagClass = entity.getClass();
		for(;tagClass != Object.class;tagClass = tagClass.getSuperclass()){
			Field[] fields1 = tagClass.getDeclaredFields();
			fields = (Field[]) ArrayUtils.addAll(fields,fields1);
		}
		for(Field field : fields){
			try {
				field.setAccessible(true);
				resultMap.put(field.getName(), field.get(entity));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return resultMap;
	}

	/**
	 * List<<Map>>转实体List
	 * @param resourList
	 * @param tagClass
	 * @return
	 * @author wanqing
	 * @date 2016/9/26 0026 13:31
	*/
	public static <T> List<T> listMapToEntity(List<Map<String, Object>> resourList, Class<T> tagClass) throws Exception{
		if(null == resourList) return null;
		List<T> resultList = new ArrayList<T>();
		for (Map<String, Object> resourMap : resourList){
			T tagT = TransformMapEntity.mapToEntity(resourMap,tagClass);
			resultList.add(tagT);
		}
		return resultList;
	}

	/**
	 * 筛选指定字段
	 * @param fields
	 * @param entity
	 * @return HashMap
	 * @author wanqing
	 * @date 2016/10/19 0019 16:33
	*/
	public static Map<String, Object> getSpecifiedFieldMap(String fields,Object entity) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(null == entity) return resultMap;
		Class obClass = null;
		String[] specifiedFields = fields.split(",");
		for(String fieldName : specifiedFields){
			try {
				Field field = null;
				obClass = entity.getClass();
				for(;obClass != Object.class; obClass = obClass.getSuperclass()){
					try {
						field = obClass.getDeclaredField(fieldName.trim());
					}catch (Exception e){
						continue;
					}
					break;
				}
				field.setAccessible(true);
//				resultMap.put(fieldName, null != field.get(entity) ? field.get(entity) : "");
				resultMap.put(fieldName,field.get(entity));
				field.setAccessible(false);
			}catch (Exception e){
				resultMap.put(fieldName, null);
				continue;
			}
		}
		return resultMap;
	}

	public static Map<String, Object> getSpecifiedFieldMap(String fields, Object entity, Map<String, String> dateFormatMap) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(null == entity) return resultMap;
		Class obClass = null;
		String[] specifiedFields = fields.split(",");
		for(String fieldName : specifiedFields){
			try {
				Field field = null;
				obClass = entity.getClass();
				for(;obClass != Object.class; obClass = obClass.getSuperclass()){
					try {
						field = obClass.getDeclaredField(fieldName.trim());
					}catch (Exception e){
						continue;
					}
					break;
				}
				field.setAccessible(true);
				if(null != dateFormatMap && dateFormatMap.containsKey(fieldName)){
					String s = "";
					if(field.getGenericType().toString().indexOf("String") > 0){
						Date date = DateTimeUtils.getInstance().convertDate(field.get(entity).toString());
						s = DateTimeUtils.getInstance().getFormatDate(dateFormatMap.get(fieldName), date);
					}else {
						s = DateTimeUtils.getInstance().getFormatDate(dateFormatMap.get(fieldName), (Date)field.get(entity));
					}
					resultMap.put(fieldName, null != field.get(entity) ? s : "");
				}else {
					resultMap.put(fieldName, field.get(entity) );
				}
				field.setAccessible(false);
			}catch (Exception e){
				resultMap.put(fieldName, null);
				continue;
			}
		}
		return resultMap;
	}

	/**
	 * 筛选指定字段
	 * @param fields
	 * @param entitys
	 * @return HashMap
	 * @author wanqing
	 * @date 2016/10/19 0019 16:33
	 */
	public static List<Map<String, Object>> getSpecifiedFieldMap(String fields,List<? extends Object> entitys) throws Exception{
		return TransformMapEntity.getSpecifiedFieldMap(fields, entitys, null);
	}

	/**
	 * 筛选指定字段
	 * @param fields
	 * @param entitys
	 * @return HashMap
	 * @author wanqing
	 * @date 2016/10/19 0019 16:33
	 */
	public static List<Map<String, Object>> getSpecifiedFieldMap(String fields,List<? extends Object> entitys, Map<String, String> dateFormatMap) throws Exception{
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if(null != entitys && entitys.size() >0){
			for(Object entity : entitys){
				Map<String, Object> resultMap = null;
				if(entity.getClass().getName().indexOf("Map") > 0){
					Map<String, Object> sourceMap = (Map<String, Object>) entity;
					resultMap = TransformMapEntity.getSpecifiedFieldMap(fields, sourceMap);
				}else {
					resultMap = TransformMapEntity.getSpecifiedFieldMap(fields, entity, dateFormatMap);
				}

				resultList.add(resultMap);
			}
		}
		return resultList;
	}

	public static Map<String, Object> getSpecifiedFieldMap(String fields, Map<String, Object> map){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String[] specifiedFields = fields.split(",");
        for(String fieldName : specifiedFields){
            if(map.containsKey(fieldName)){
                resultMap.put(fieldName,map.get(fieldName));
            }
        }
        return resultMap;
    }

	public static <T> List<T> strToList(String str, Class<T> clazz) {
		JSONArray json = JSONArray.fromObject(str);
		JSONObject object = null;
		T t = null;
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < json.size(); i++) {
			object = JSONObject.fromObject(json.get(i));
			t = (T) JSONObject.toBean(object, clazz);
			list.add(t);
		}
		return list;
	}

//    public static List<Map<String, Object>> getSpecifiedFieldMap(String fields, List<Map<String, Object>> list){
//        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
//        for(Map<String, Object> map : list){
//            Map<String, Object> resultMap = TransformMapEntity.getSpecifiedFieldMap(fields,map);
//            resultList.add(resultMap);
//        }
//        return resultList;
//    }
}
