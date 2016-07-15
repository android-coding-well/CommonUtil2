package com.gosuncn.core.utils;

import android.annotation.SuppressLint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 集合工具类
 * @author HWJ
 *
 */
@SuppressLint("UseSparseArrays")
public class CollectionUtil {

	public static boolean isNull(Collection<?> collection) {
		if (collection != null && collection.size() > 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * list转map
	 * @param list
	 * @return
	 */
	public static <T> Map<Integer,T> list2map(List<T> list){
		Map<Integer,T> friends = new HashMap<Integer, T>();
		int i=0;
		for(T user : list){
			friends.put(i, user);
			i++;
		}
		return friends;
	}
	
	
	/**
	 * map转list
	 * @param maps
	 * @return
	 */
	public static <T> List<T> map2list(Map<String,T> maps){
		List<T> users = new ArrayList<T>();
		Iterator<Entry<String, T>> iterator = maps.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, T> entry = iterator.next();
			users.add(entry.getValue());
		}
		return users;
	}
}
