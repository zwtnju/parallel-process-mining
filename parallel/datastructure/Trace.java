package cn.edu.nju.software.parallel.datastructure;

import java.util.LinkedHashSet;
import java.util.Set;

public class Trace {
	// 有序的
	private String trace;
	// task集合
	private Set<String> tasks;
	// 无序的
	private String taskSet;

	public Trace(String line) {
		tasks = new LinkedHashSet<String>();
//		String[] splited = line.split(":");
//		this.trace = splited[1];
		this.trace = line;
		String[] traces = trace.split(",");
		
		for(String str : traces) {
			tasks.add(str);
		}
		
		taskSet = "";
		for (String task : tasks) {
			taskSet += task+",";
		}
	}
	
	public String getTrace() {
		return trace;
	}
	
	public String getTaskSet() {
		return taskSet;
	}
}
