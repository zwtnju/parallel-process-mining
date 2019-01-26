package cn.edu.nju.software.parallel.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cn.edu.nju.software.parallel.datastructure.Trace;

public class DecomposeEventLog {
	// save taskset
	// key是无序的 但是 value集合是有序的trace实体
	private HashMap<String, List<String>> sTaskSet;
	// save tasks
	private HashMap<String, Integer> traces;
	
	public DecomposeEventLog(List<Trace> traceSet) {
		HashMap<String, List<String>> taskSetTemp = new HashMap<String, List<String>>();
		traces = new HashMap<String, Integer>();
		// save trace
		for (Trace trace : traceSet) {
			if(traces.isEmpty()) {
        		traces.put(trace.getTrace(), 1);
        	}
        	else {
            	if(traces.containsKey(trace.getTrace())) {
            		traces.put(trace.getTrace(), traces.get(trace.getTrace())+1);
            	} else {
            		traces.put(trace.getTrace(), 1);
            	}
        	}      
        	if(traces.get(trace.getTrace()) >= 2) {
        		if(taskSetTemp.containsKey(trace.getTaskSet())) {
        			taskSetTemp.get(trace.getTaskSet()).add(trace.getTrace());
        		}
        		else {
        			List<String> taskList = new ArrayList<String>();
        			taskList.add(trace.getTrace());
        			taskSetTemp.put(trace.getTaskSet(), taskList);
        		}
        	}
		}
		// save tasks
		sTaskSet = new HashMap<String, List<String>>();
	     for(Entry<String, List<String>> pairs : taskSetTemp.entrySet()) {
	    	 if(pairs.getValue().size() >= 2) {
	    		 sTaskSet.put(pairs.getKey(), pairs.getValue());
	    	 }
	    	 sTaskSet.put(pairs.getKey(), pairs.getValue());
	     }
		
//		HashMap<String, List<String>> taskSetTemp = new HashMap<String, List<String>>();
//		tasks = new HashMap<String, Integer>();
//		BufferedReader br = null;
//		try {  
//            String str = "";
//            br =  new BufferedReader(new InputStreamReader(traceSet));  
//            // 
//			//int parts = 128;//= Integer.parseInt(br.readLine());
//			// 
//			//int lineNums = fileLines;//= Integer.parseInt(br.readLine());
//			long startTime = System.currentTimeMillis();
//			// 
//			int diviFlag = 128;//lineNums/parts;
//			int count = 1;
//            int flag = 0;
//            String traceStr = "";
//            while ((str = br.readLine()) != null) {  
//				count ++;
//				if(count > diviFlag){
//					long endTime = System.currentTimeMillis();
//					readFileTime = Math.max(readFileTime, endTime - startTime);
//					count = 1;
//					startTime =System.currentTimeMillis();
//				}
//            	if(str.startsWith("\t\t<ProcessInstance")) {
//            		flag = 1;
//            	}
//            	if(flag == 1) {
//            		//  the flag to decide if we need 
//                	if(str.startsWith("\t\t\t\t<WorkflowModelElement>")) {
//                        Pattern p = Pattern.compile(">([^</]+)</");
//                        Matcher m = p.matcher(str);//
//                        if (m.find()) {    
//                        	traceStr += m.group(1) + ",";
//                        }
//                	}  
//                	if(str.startsWith("\t\t</ProcessInstance>")) {
//                		flag = 0;
//                	}
//            	}
//            	if(flag == 0 && traceStr != "") {
//            		Trace trace = new Trace(traceStr);
//            		traceStr = "";
//                	if(tasks.isEmpty()) {
//                		tasks.put(trace.getTrace(), 1);
//                	}
//                	else {
//                    	if(tasks.containsKey(trace.getTrace())) {
//                    		tasks.put(trace.getTrace(), tasks.get(trace.getTrace())+1);
//                    	} else {
//                    		tasks.put(trace.getTrace(), 1);
//                    	}
//                	}      
//                	if(tasks.get(trace.getTrace()) >= 2) {
//                		if(taskSetTemp.containsKey(trace.getTaskSet())) {
//                			taskSetTemp.get(trace.getTaskSet()).add(trace.getTrace());
//                		}
//                		else {
//                			List<String> taskList = new ArrayList<String>();
//                			taskList.add(trace.getTrace());
//                			taskSetTemp.put(trace.getTaskSet(), taskList);
//                		}
//                	}
//            	}
//            }
//         } catch (FileNotFoundException e) {
//		   System.out.println("FileNotFoundException");
//		 } catch (IOException e) {
//		   System.out.println("IOException");
//		 } finally {
//		   try {
//		     br.close();
//		     // clear no use traces
//		     sTaskSet = new HashMap<String, List<String>>();
//		     for(Entry<String, List<String>> pairs : taskSetTemp.entrySet()) {
////		    	 if(pairs.getValue().size() >= 2) {
////		    		 sTaskSet.put(pairs.getKey(), pairs.getValue());
////		    	 }
//		    	 sTaskSet.put(pairs.getKey(), pairs.getValue());
//		     }
//		   } catch (IOException e) {
//		    e.printStackTrace();
//		   }
//		 }
	}

	public HashMap<String, List<String>> getsTaskSet() {
		return sTaskSet;
	}

	public void setsTaskSet(HashMap<String, List<String>> sTaskSet) {
		this.sTaskSet = sTaskSet;
	}

}