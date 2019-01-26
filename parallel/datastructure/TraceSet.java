package cn.edu.nju.software.parallel.datastructure;

import java.util.LinkedList;


public class TraceSet {
	private LinkedList<Trace> traces;
	private long readFileTime;
	
	public long getReadFileTime() {
		return readFileTime;
	}

	public void setReadFileTime(long readFileTime) {
		this.readFileTime = readFileTime;
	}

	public TraceSet(){
		this.traces = new LinkedList<Trace>();
	}	
	
	public LinkedList<Trace> getTraces(){
		return this.traces;
	}

	public void addTrace(Trace trace) {
		traces.add(trace);
	}
}
