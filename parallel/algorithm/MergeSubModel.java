package cn.edu.nju.software.parallel.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import cn.edu.nju.software.parallel.datastructure.JavaUtils;

public class MergeSubModel {
	// 合并子模型  保存place信息 格式为a_b前后为2个库所信息
	private Set<String> places;

	public MergeSubModel(HashMap<String, ArrayList<String>> subModel) {
		places = new HashSet<String>();
		
		for(Entry<String, ArrayList<String>> pairs : subModel.entrySet()) {
			List<String> sub_model = JavaUtils.string2list(pairs.getKey(), ",");
			Iterator<String> sub_models = sub_model.iterator();  
			String smt1 = sub_models.next();
			while (sub_models.hasNext()) {
			  String smt2 = sub_models.next();
			  places.add(smt1+"$"+smt2);
			  smt1 = smt2;
			}			
			for(ArrayList<String> strArr : subModel.values()) {
				for(String str : strArr) {
					List<String> sub_loop = JavaUtils.string2list(str, ",");
					Iterator<String> sub_loops = sub_loop.iterator();  
					String slt1 = sub_loops.next();
					while (sub_loops.hasNext()) {
					  String slt2 = sub_loops.next();
					  places.add(slt1+"$"+slt2);
					  slt1 = slt2;
					}
				}

			}
		}
	}
	
	public Set<String> getPlaces() {
		return places;
	}

	public void setPlaces(Set<String> places) {
		this.places = places;
	}
}