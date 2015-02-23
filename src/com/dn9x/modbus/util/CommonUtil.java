package com.dn9x.modbus.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommonUtil {
	public static List<File> getFiles(String path){		
		List<File> files = new ArrayList<File>();
		
		File d = new File(path);
		
		File list[] = d.listFiles();
		
		for(int i = 0; i < list.length; i++){
		    if(list[i].isFile()){
		    	files.add(list[i]);
		    }
		}
		
		return files;
	}
}
