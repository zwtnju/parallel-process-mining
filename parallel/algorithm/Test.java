package cn.edu.nju.software.parallel.algorithm;

// 1. 并行计算  分开统计时间
// 2. 做一些view
public class Test {
	public static void main(String[] args) throws Exception {
//		File file = new File("C:/Users/ZouWentao/Desktop/Hospital_log.xes");
//		InputStream inputStream = new FileInputStream(file);
//		XLogDFAXmlParser dfaParser = new XLogDFAXmlParser();
//		XLog log = dfaParser.parse(inputStream);	
		
		String test = "sgot - asat kinetisch$sgpt - alat kinetisch";
		System.out.println(test.split("\\$")[0]);
//		for(XAttribute str : log.getGlobalEventAttributes()) {
//			System.out.println(str.toString());
//		}
//		XEventClassifier classifier = MiningParameters.getDefaultClassifier();
//		XLogInfo logInfo = XLogInfoFactory.createLogInfo(log, classifier);
//		System.out.println(logInfo.getNumberOfEvents());
//		System.out.println(logInfo.getNumberOfTraces());
//		for(XAttribute str : logInfo.getTraceAttributeInfo().getAttributes()) {
//			System.out.println(str.toString());
//		}
//		for (XEventClass activity : logInfo.getEventClasses().getClasses()) {
//			System.out.println(activity.toString());
//		}
//	
//		for(Entry<String, XAttribute> pairs : log.getAttributes().entrySet()) {
//			System.out.print(pairs.getKey() + "\t" + pairs.getValue().toString());
//			System.out.println("***");
//		}
//		concept:name	Hospital log
//		for (XTrace trace : log) {
//			for (XEvent event : trace) {
//				for (XAttribute str : event.getAttributes().values()) {
//					System.out.print(str.toString() + "-->");
//				}
//			}
//			System.out.println();
//		}
//		System.out.println("**************************");
//		InputStream is = new FileInputStream(new File("C:/Users/ZouWentao/Desktop/test2.mxml"));
//		DecomposeEventLog del = new DecomposeEventLog(is);
//		for(String str : del.getsTaskSet().keySet()) {
//			System.out.println(str);
//		}
//		System.out.println("**************************");
//		GroupTaskSet gts = new GroupTaskSet(del.getsTaskSet().keySet());
//		for(Entry<String, ArrayList<String>> pairs : gts.getSng().entrySet()) {
//			System.out.print(pairs.getKey()+ " : ");
//			for (String str : pairs.getValue()) {
//				System.out.print(str + "| ");
//			}
//			System.out.println();
//		}
//		System.out.println("**************************");
//		MineSubModel mism = new MineSubModel(gts.getSng().entrySet(), del);
//		for(Entry<String, ArrayList<String>> pairs : mism.getSubmodel_subloop().entrySet()) {
//			System.out.print(pairs.getKey()+ " : ");
//			for (String str : pairs.getValue()) {
//				System.out.print(str + "| ");
//			}
//			System.out.println();
//		}
//		System.out.println("**************************");
//		MergeSubModel mesm = new MergeSubModel(mism.getSubmodel_subloop());
//		for (String str : mesm.getPlaces()) {
//			System.out.println(str);
//		}
//		System.out.println("**************************");
//		**************************
//		a,b,l,m,n,p,y,j,
//		a,b,c,d,f,e,g,h,y,j,
//		a,b,l,m,n,w,x,p,y,j,
//		a,b,l,m,n,p,y,t,u,v,w,x,j,
	}
}
