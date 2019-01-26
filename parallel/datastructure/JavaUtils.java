package cn.edu.nju.software.parallel.datastructure;

import java.util.ArrayList;
import java.util.List;

public class JavaUtils {

	public static List<String> string2list(String str, String sep) {
		List<String> ls = new ArrayList<String>();
		for(String s : str.split(sep)) {
			if(null != s) {
				ls.add(s);
			}
		}
		return ls;
	}

	public static String list2string(List<String> ls, String sep) {
		String str = "";
		for (int i = 0; i < ls.size(); i++) {
        	if (i < ls.size() - 1) {
        		str += ls.get(i) + sep;
            } else {
            	str += ls.get(i);
            }
        }
		return str;
	}
}
