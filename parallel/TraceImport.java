package cn.edu.nju.software.parallel;

import java.io.InputStream;

import org.processmining.contexts.uitopia.annotations.UIImportPlugin;
import org.processmining.framework.abstractplugins.AbstractImportPlugin;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;

import cn.edu.nju.software.parallel.algorithm.DecomposeEventLog;


//插件定义
@Plugin(name = "Import Trace Log", parameterLabels = { "Filename" }, 
returnLabels = { "TraceSet" }, returnTypes = { DecomposeEventLog.class })
//导入插件定义
@UIImportPlugin(
//表明传入的文件会被转化为TraceSet类型在ProM中进行传输
description = "Trace Log",
//表明识别的自定义文件后缀名为trace
extensions = { "mxml" })

public class TraceImport extends AbstractImportPlugin{	
	@Override
	protected DecomposeEventLog importFromStream(PluginContext context, InputStream input, String filename, long fileSizeInBytes){
//		// 把文件行数一起返回
//        int count = 0;
//		try {
//			BufferedReader br =  new BufferedReader(new InputStreamReader(input));  
//			while (br.readLine()!= null) {  
//				 count ++;
//			}
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
//		DecomposeEventLog traceSet = new DecomposeEventLog(input);
//		//traceSet = GenerateTraceSet.generateTraceSet(input);
//		try{
//			context.getFutureResult(0).setLabel("Trace file imported from " + filename);
//			return traceSet;
//		}catch(Exception e){
//			// Don't care if this fails
//			System.out.println("shit!");
//			e.printStackTrace();
			return null;
//		}
	}
	
}