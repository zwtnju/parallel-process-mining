package cn.edu.nju.software.parallel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections15.CollectionUtils;
import org.deckfour.xes.model.XAttribute;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.models.graphbased.directed.petrinet.elements.Place;
import org.processmining.models.graphbased.directed.petrinet.elements.Transition;
import org.processmining.models.graphbased.directed.petrinet.impl.PetrinetFactory;

import cn.edu.nju.software.parallel.algorithm.DecomposeEventLog;
import cn.edu.nju.software.parallel.algorithm.GroupTaskSet;
import cn.edu.nju.software.parallel.algorithm.MergeSubModel;
import cn.edu.nju.software.parallel.algorithm.MineSubModel;
import cn.edu.nju.software.parallel.datastructure.Trace;

// 并行结构
// 循环嵌套

public class MinimizeTraceSet {	
	
	//标签定义
	@Plugin(name = "Minimize TraceSet", parameterLabels = {"TraceSet"}, returnLabels = {"Output Petri Net", "Group Task Set", "Sub Model", "Execute Time"}, 
			returnTypes = {Petrinet.class, GroupTaskSet.class, MineSubModel.class, ExecuteTime.class}, userAccessible = true, 
			help = "Input a mxml file as the source")
	@UITopiaVariant(affiliation = "njusoftware", author = "zwtnju", email = "zwt@software.nju.edu.cn")
	
	public Object[] processMultset(UIPluginContext context, XLog log){	//DecomposeEventLog del, 
		//int count = 0;
		List<Trace> traceSet= new ArrayList<Trace>();
		for (XTrace xtrace : log) {
			String traceStr = "";
			for (XEvent event : xtrace) {
				for (Entry<String, XAttribute> pairs : event.getAttributes().entrySet()) {
					if(pairs.getKey().equals("concept:name")) {
						//System.out.print(pairs.getValue().toString() + "-->");
						traceStr += pairs.getValue().toString() + ",";
						
					}
				}
			}
			Trace trace = new Trace(traceStr);
			traceSet.add(trace);
			//count ++;
			//System.out.println(trace.getTrace());
		}
		//System.out.println(count);
//		System.out.println("**************************");
		
		
		// 模型构建部分
		// 日志分解得到 taskset的集合
		//count = 0;
		ExecuteTime executeTime = new ExecuteTime();
		DecomposeEventLog del = new DecomposeEventLog(traceSet);
		//int flag = 0;
//		for(String str : del.getsTaskSet().keySet()) {
//			//System.out.println(str);//6/11
////			for(Trace tr : traceSet) {
////				if(tr.getTrace().equals()){
////					//System.out.println("OK");
////				}
////			}
////			if(flag == 0) {
////				System.out.println("Error");
////			}
//			//
////			for(String tr : del.getsTaskSet().get(str)) {
////				//Create Application,Fill in the Form,Superior Approval,Purchase Products,Reinbursement Form,Financial Check,Superior Review,Application Over,
////				
////				if(tr.equals("Create Application,Fill in the Form,Superior Approval,Failed4_loop,Fill in the Additional Form,Superior Approval,Purchase Products,Reinbursement Form,Financial Check,Superior Review,Application Over,")) {
////					System.out.println(str);
////					break;
////				}
////			}
//			//count ++;
//		}
		//System.out.println(count);
//		System.out.println("**************************");
		//System.out.println(del.getReadFileTime());
//		executeTime.setDecomposeTime(del.getReadFileTime());
	
		long startTime = System.currentTimeMillis();
		GroupTaskSet gts = new GroupTaskSet(del.getsTaskSet().keySet());
		long endTime = System.currentTimeMillis();
		executeTime.setGroupTime(endTime - startTime);
		
//		for(Entry<String, ArrayList<String>> pairs : gts.getSng().entrySet()) {
//			System.out.print(pairs.getKey()+ " : ");
//			for (String str : pairs.getValue()) {
//				System.out.print(str + "| ");
//			}
//			System.out.println();
//		}
//		System.out.println("**************************");
		
		
		
		
		MineSubModel mism = new MineSubModel(gts.getSng().entrySet(), del);
		executeTime.setMineTime(mism.getMineModelTime());
		executeTime.setClearTime(mism.getClearModelTime());
		
		
//		for(Entry<String, ArrayList<String>> pairs : mism.getSubmodel_subloop().entrySet()) {
//			System.out.print(pairs.getKey()+ " : ");
//			for (String str : pairs.getValue()) {
//				System.out.print(str + "| ");
//			}
//			System.out.println();
//		}
//		System.out.println("**************************");
		
		
		startTime = System.currentTimeMillis();
		MergeSubModel mesm = new MergeSubModel(mism.getSubmodel_subloop());
		endTime = System.currentTimeMillis();
		executeTime.setMergeTime(endTime - startTime);
		
//		for(String pairs : mesm.getPlaces()) {
//			System.out.println(pairs);
//		}
//		System.out.println("**************************");
		
		
		
		// trans之间的list结构转化为String数组
		// 传出的数据结构格式
		//		a_b
		//		b_c
		//		k_q
		//		l_m
		
		
//		ordertarief$+$thorax
//		albumine$+$screening antistoffen erytrocyten
		
		
		// 画图部分
		String[] petriNet = String.join(",", mesm.getPlaces()).split(",");
//		for(String str : petriNet) {
//			System.out.println(str);
//		}
//		System.out.println(petriNet[0]);
		Petrinet resultNet = null;
		resultNet = PetrinetFactory.newPetrinet("Output Petrinet");
		List<Transition> transitions = new ArrayList<Transition>();
		Map<Place, List<String>> arcs = new HashMap<Place, List<String>>();
		Map<String, Integer> trans_index = new HashMap<String, Integer>();
		List<String> trans_pre = new ArrayList<String>();
		List<String> trans_post = new ArrayList<String>();
		int transitionIndex = 0;
		int placeNum = 0;
		
		for(int i = 0; i < petriNet.length; i++) {
			String key = petriNet[i].split("\\$")[0];
			String value = petriNet[i].split("\\$")[1];
			trans_pre.add(key);
			trans_post.add(value);
			Transition transition1 = null;
			Transition transition2 = null;
			if(trans_index.containsKey(key)) {
				transition1 = transitions.get(trans_index.get(key));
			} else {
				transition1 = resultNet.addTransition(key);
				transitions.add(transition1);
				trans_index.put(key, transitionIndex ++);
			}
			
			if(trans_index.containsKey(value)) {
				transition2 = transitions.get(trans_index.get(value));
			} else {
				transition2 = resultNet.addTransition(value);
				transitions.add(transition2);
				trans_index.put(value, transitionIndex ++);
			}
			
			
			// 合并 并行结构
			if(arcs.isEmpty()) {
				List<String> preTrans_postTrans = new ArrayList<String>();
				preTrans_postTrans.add(key+"$"+value);
				Place place = resultNet.addPlace("p"+ placeNum++);
				arcs.put(place, preTrans_postTrans);
				resultNet.addArc(transition1, place);
				resultNet.addArc(place, transition2);
			}
			else {
				// 标志 是否需要添加新的place
				int flag1 = 0;
				for(Entry<Place, List<String>> pairs : arcs.entrySet()) {
					// 标志 是否是并行结构
					int flag2 = 0;
					for(String str : pairs.getValue()) {
						String pre = str.split("\\$")[0];
						String post = str.split("\\$")[1];
						if(pre.equals(key) && !post.equals(value)) {
							resultNet.addArc(pairs.getKey(), transition2);
							flag2 = 1;
							flag1 = 1;
							break;
						} else if(!pre.equals(key) && post.equals(value)) {
							resultNet.addArc(transition1, pairs.getKey());
							flag2 = 2;
							flag1 = 1;
							break;
						}
					}
					if(flag2 == 1){
						pairs.getValue().add(pairs.getKey()+"$"+value);
					} else if(flag2 == 2){
						pairs.getValue().add(key+"$"+pairs.getKey());
					}
					if(flag1 == 1) {
						break;
					}
				}
				if(flag1 == 0) {
					List<String> preTrans_postTrans = new ArrayList<String>();
					preTrans_postTrans.add(key+"$"+value);
					Place place = resultNet.addPlace("p"+ placeNum++);
					arcs.put(place, preTrans_postTrans);
					resultNet.addArc(transition1, place);
					resultNet.addArc(place, transition2);
				}
			}
		}
		// 找出口和入口 有入无出 为出口 有出无入 为入口
		List<String> temp = new ArrayList<String>();
		CollectionUtils.addAll(temp, new String[trans_pre.size()]);
		Collections.copy(temp, trans_pre);
		trans_pre.removeAll(trans_post);
		trans_post.removeAll(temp);
		for(String str : trans_pre) {
			resultNet.addArc(resultNet.addPlace("i"), transitions.get(trans_index.get(str)));
		}
		for(String str : trans_post) {
			resultNet.addArc(transitions.get(trans_index.get(str)), resultNet.addPlace("o"));
		}

		return new Object[] {resultNet, gts, mism, executeTime};

	}
}
