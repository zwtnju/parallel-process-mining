package cn.edu.nju.software.parallel.algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.nju.software.parallel.datastructure.TraceWithTraceLog;

public class DecomposeEventLogWithTraceLog {
	// 
	private HashMap<String, List<String>> sTaskSet;
	//
	private HashMap<String, Integer> tasks;
	//
	public DecomposeEventLogWithTraceLog(String fileName) throws IOException {
		this(new FileInputStream(new File(fileName)));		
	}
	
	public DecomposeEventLogWithTraceLog(InputStream input) {
		sTaskSet = new HashMap<String, List<String>>();
		tasks = new HashMap<String, Integer>();

		BufferedReader br = null;
		try {  
            String str = "";
            br =  new BufferedReader(new InputStreamReader(input));  

            while ((str = br.readLine()) != null) {  
            	TraceWithTraceLog trace = new TraceWithTraceLog(str);
            	if(tasks.isEmpty()) {
            		tasks.put(trace.getTrace(), 1);
            	}
            	else {
                	if(tasks.containsKey(trace.getTrace())) {
                		tasks.put(trace.getTrace(), tasks.get(trace.getTrace())+1);
                	} else {
                		tasks.put(trace.getTrace(), 1);
                	}
            	}      
            	if(tasks.get(trace.getTrace()) >= 2) {
            		if(sTaskSet.containsKey(trace.getTaskSet())) {
            			sTaskSet.get(trace.getTaskSet()).add(trace.getTrace());
            		}
            		else {
            			List<String> taskList = new ArrayList<String>();
            			taskList.add(trace.getTrace());
            			sTaskSet.put(trace.getTaskSet(), taskList);
            		}
            	}
            }  
         } catch (FileNotFoundException e) {
		   System.out.println("FileNotFoundException");
		 } catch (IOException e) {
		   System.out.println("IOException");
		 } finally {
		   try {
		     br.close();
		     //
		     
		   } catch (IOException e) {
		    e.printStackTrace();
		   }
		 }
	}

	public HashMap<String, List<String>> getsTaskSet() {
		return sTaskSet;
	}

	public void setsTaskSet(HashMap<String, List<String>> sTaskSet) {
		this.sTaskSet = sTaskSet;
	}

}