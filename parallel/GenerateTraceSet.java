package cn.edu.nju.software.parallel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.edu.nju.software.parallel.datastructure.Trace;
import cn.edu.nju.software.parallel.datastructure.TraceSet;


public class GenerateTraceSet {
	//将TImport插件导入的数据流转化为一个traceSet类
	public static TraceSet generateTraceSet(InputStream input){
		TraceSet traceSet = new TraceSet();
		long executeTime = 0;
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(input, "utf-8"));
			String line = "";
			// 读取需要并行的个数
			int parts  = Integer.parseInt(br.readLine());
			// 读取文件行数
			int lineNums  = Integer.parseInt(br.readLine());
			long startTime = System.nanoTime();
			int diviFlag = lineNums/parts;
			int count = 1;
			// 然后可以多线程操作读取文件 此处为了方便 就单线程处理
			while ((line = br.readLine()) != null) {
				count ++;
				if(count > diviFlag){
					long endTime = System.nanoTime();
					executeTime = Math.max(executeTime, (endTime - startTime)/100);
					count = 1;
					startTime = System.nanoTime();
				}
				processLine(traceSet, line);
			}
			long endTime = System.nanoTime();
			executeTime = Math.max(executeTime, (endTime - startTime)/100);
			traceSet.setReadFileTime(executeTime);
			return traceSet;
		}catch(IOException e){
			//do nothing
			return null;
		}
		
	}
	
	//读入input流的每一行，并转化为trace存进traceSet中
	private static void processLine(TraceSet traceSet, String line){
		Trace trace = new Trace(line);
		traceSet.addTrace(trace);
	}
}
