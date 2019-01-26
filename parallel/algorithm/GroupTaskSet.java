package cn.edu.nju.software.parallel.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import cn.edu.nju.software.parallel.datastructure.JavaUtils;

public class GroupTaskSet {
	// 保存taskset的集合 key是firstng, value是包含firstng作为子集的taskset的集合
	// key 是无序的traceset
	private HashMap<String, ArrayList<String>> sng;

	public GroupTaskSet(Set<String> tasks) {
		sng = new HashMap<String, ArrayList<String>>();
		
		for (String str : tasks) {
			if(sng.isEmpty()) {
				ArrayList<String> tsList = new ArrayList<String>();
				tsList.add(str);
				sng.put(str, tsList);
				
			} else {
				int count = 0;
				for (String firstng : sng.keySet()) { 
					List<String> fngList = JavaUtils.string2list(firstng, ",");
					List<String> strList = JavaUtils.string2list(str, ",");
					if (strList.containsAll(fngList)) {
						// 对ng里面的元素按顺序插入
						ArrayList<String> tempList = sng.get(firstng);
						for (int i = 0; i < tempList.size(); i++) {
							if(JavaUtils.string2list(tempList.get(i), ",").containsAll(strList)) {
								tempList.add(i, str);
								count++;
								break;
							}
						}
						tempList.add(str);
						count++;
						break;

					} else if (fngList.containsAll(strList)) {
						ArrayList<String> tempList = sng.remove(firstng);
						// 把first element插入ng的第一个元素中
						tempList.add(0, str);
						sng.put(str, tempList);
						count++;
						break;
					}
				}
				if(count == 0) {
					ArrayList<String> tsList = new ArrayList<String>();
					tsList.add(str);
					sng.put(str, tsList);
				} 
			}
		}
		// 排除special group 操作
//		HashMap<String, ArrayList<String>> copySng = clone(sng);
//		for(Entry<String, ArrayList<String>> pairs : copySng.entrySet()) {
//			for(String str : pairs.getValue()) {
//				for(String firstng : copySng.keySet()) {
//					if(firstng != pairs.getKey()) {
//						List<String> fngList =  JavaUtils.string2list(firstng, ",");
//						fngList.removeAll(JavaUtils.string2list(pairs.getKey(), ","));
//						List<String> tempList = JavaUtils.string2list(str, ",");
//						for(String task : fngList) {
//							if(tempList.contains(task)) {
//								sng.get(pairs.getKey()).remove(str);
//								break;
//							}
//						}
////						if(JavaUtils.string2list(str, ",").containsAll(JavaUtils.string2list(firstng, ","))) {
////							sng.get(pairs.getKey()).remove(str);
////						}
//					}
//				}
//			}
//		}
		
	}
	// 序列化数据深拷贝代码
//	@SuppressWarnings("unchecked")
//	public <T extends Serializable> T clone(T obj) {
//		T clonedObj = null;
//		try {
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ObjectOutputStream oos = new ObjectOutputStream(baos);
//			oos.writeObject(obj);
//			oos.close();
//			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
//			ObjectInputStream ois = new ObjectInputStream(bais);
//			clonedObj = (T) ois.readObject();
//			ois.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return clonedObj;
//	}
	
//	public static HashMap<String, ArrayList<String>> copyHashMap(HashMap<String, ArrayList<String>> original) {
//		HashMap<String, ArrayList<String>> copy = new HashMap<String, ArrayList<String>>();
//		for (Entry<String, ArrayList<String>> entry : original.entrySet()) {
//			copy.put(entry.getKey(), new ArrayList<String>(entry.getValue()));
//		}
//		return copy;
//	}
	
	public HashMap<String, ArrayList<String>> getSng() {
		return sng;
	}
	public void setSng(HashMap<String, ArrayList<String>> sng) {
		this.sng = sng;
	}
}