package cn.edu.nju.software.parallel.algorithm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import cn.edu.nju.software.parallel.datastructure.JavaUtils;

public class MineSubModel {
	// 挖掘子结构 保存对应的子结构和其中的loop信息
	private HashMap<String, ArrayList<String>> submodel_subloop;
	// 并行挖掘子模型时间
	private long mineModelTime = 0;
	// 清除重复信息时间
	private long clearModelTime = 0;
	
	public long getMineModelTime() {
		return mineModelTime;
	}

	public void setMineModelTime(long mineModelTime) {
		this.mineModelTime = mineModelTime;
	}

	public long getClearModelTime() {
		return clearModelTime;
	}

	public void setClearModelTime(long clearModelTime) {
		this.clearModelTime = clearModelTime;
	}
	
	

	public MineSubModel(Set<Entry<String, ArrayList<String>>> sng, DecomposeEventLog del) {
		submodel_subloop = new HashMap<String, ArrayList<String>>();
		// sng中的元素仅代表traceset中的相对顺序 不代表trace中的相对顺序 即 sng的key值是无序的
		for(Entry<String, ArrayList<String>> pairs : sng) {
			long startTime = System.nanoTime();
			// 每循环一次 把一些循环结构添加进model中
			//List<String> subModel = JavaUtils.string2list(del.getsTaskSet().get(pairs.getKey()).get(0), ",");
			List<String> subModel = JavaUtils.string2list(pairs.getKey(), ",");
			//  loop_tasks 代表含有循环的路径的所有的tasks的集合
			for(String loop_tasks : pairs.getValue()){
				// 这里添加loop的出口和入口
				// 返回对应的taskSet中的traces
				List<String> traces = del.getsTaskSet().get(loop_tasks);
				// 对应firstng的submodel的list形式
				List<String> tasks = JavaUtils.string2list(loop_tasks, ",");
				tasks.removeAll(subModel);
				String loop = JavaUtils.list2string(tasks, ",");
				if(loop != "" && loop != null) {
					// 循环出现之前重复出现的第一个是出口，最后一个是入口
					String starterAndEnder = findStartandEnd(traces, tasks);
					//System.out.println(starterAndEnder);
					//System.out.println("----");
					if(!starterAndEnder.equals(",")) {
						//System.out.println(starterAndEnder);
						//System.out.println("*****");
						String starter = starterAndEnder.split(",")[0];
						String ender = starterAndEnder.split(",")[1];
						
						loop = starter + "," + loop +  "," + ender;
					}

					for(String str : tasks) {
						subModel.add(str);
					}
					// 如果 取第一个元素有问题的话 就 遍历 list 寻找所有的元素只出现一次的情况
					List<String> subModelTraces = del.getsTaskSet().get(pairs.getKey());					
					List<String> subModelList = JavaUtils.string2list(findSubModel(subModelTraces), ",");					
					String subModStr = JavaUtils.list2string(subModelList, ",");
					if(submodel_subloop.isEmpty()) {
						ArrayList<String> subloops = new ArrayList<String>();
						subloops.add(loop);
						submodel_subloop.put(subModStr, subloops);
					}
					else {
						if(submodel_subloop.containsKey(subModStr)) {
							submodel_subloop.get(subModStr).add(loop);
						} else {
							ArrayList<String> subloops = new ArrayList<String>();
							subloops.add(loop);
							submodel_subloop.put(subModStr, subloops);
						}
					}
				}
			}
			long endTime = System.currentTimeMillis();
			long total = endTime - startTime;
			mineModelTime = Math.max(mineModelTime, total);
		}
		long startTime = System.currentTimeMillis();
		clearLoop(submodel_subloop);
		long endTime = System.currentTimeMillis();
		long total = endTime - startTime;
		clearModelTime = total;
	}
	
	private String findSubModel(List<String> subModelTraces) {
		return subModelTraces.get(0);
		
	}

	// 清理出干净的loop 即前面的 排除special group 操作
	// 使得没有循环是其他分支中
	private void clearLoop(HashMap<String, ArrayList<String>> submodel) {
		HashMap<String, ArrayList<String>> copySng = clone(submodel);
		for(Entry<String, ArrayList<String>> pairs : copySng.entrySet()) {
			for(String str : pairs.getValue()) {
				for(String firstng : copySng.keySet()) {
					if(firstng != pairs.getKey()) {
						List<String> fngList =  JavaUtils.string2list(firstng, ",");
						fngList.removeAll(JavaUtils.string2list(pairs.getKey(), ","));
						List<String> tempList = JavaUtils.string2list(str, ",");
						for(String task : fngList) {
							if(tempList.contains(task)) {
								submodel.get(pairs.getKey()).remove(str);
								break;
							}
						}
					}
				}
			}
		}
	}
	
	// 序列化数据深拷贝代码
	@SuppressWarnings("unchecked")
	private <T extends Serializable> T clone(T obj) {
		T clonedObj = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.close();
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			clonedObj = (T) ois.readObject();
			ois.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return clonedObj;
	}
	
	
	
	// 找出循环结构的出入
	// trace就是包含这个循环的所有的路径   tasks是循环内部结构
	// 需要解决循环嵌套的问题
	private String findStartandEnd(List<String> traces, List<String> tasks) {
		String starter = "";
		String ender = "";
		List<String> freTasks = new ArrayList<String>();
		// flag1 用来标识 是否读入循环
		// flag2 用来标识 是否是 start
		int flag1 = 0, flag2 = 0;
		// 暂时只用每个traces中的第一个trace
		// 应该是使用让每个元素都出现一次的trace
		
//		for(String str : tasks) {
//			System.out.println(str);
//		}
//		System.out.println("&&&&&&&");
		
		List<String> traceList = JavaUtils.string2list(traces.get(0), ",");
		for(String task : traceList) {
			//System.out.println(task);
			if(!tasks.contains(task)) {
				if(flag1 == 0) {
					freTasks.add(task);
				} else if (freTasks.contains(task)){
					if(flag2 == 0) {
						ender = task;
						flag2 = 1;
					} else {
						starter = task;
					}
				}
			} else {
				flag1 = 1;
			}
		}
		return starter+","+ender;
	}
	
	public HashMap<String, ArrayList<String>> getSubmodel_subloop() {
		return submodel_subloop;
	}
	public void setSubmodel_subloop(HashMap<String, ArrayList<String>> submodel_subloop) {
		this.submodel_subloop = submodel_subloop;
	}
}