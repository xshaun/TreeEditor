package org.treeEditor.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Hashtable;


public class ConvertFile {
	protected static HashMap<String, String> tagMap = new HashMap<String, String>();
	protected static HashMap<String, String> attrMap = new HashMap<String, String>();
	protected static Hashtable<String, String> textMap = new Hashtable<String, String>();
	
	public static boolean preToXML(File preFlie, File conFile){
		tagMap.clear();
		attrMap.clear();
		textMap.clear();
		try {
			FileInputStream in = new FileInputStream(preFlie);
			BufferedReader br = new BufferedReader(new UnicodeReader(in, "utf-8"));
			FileOutputStream out = new FileOutputStream(conFile);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out,"utf-8"));
			
			if(readDataToMap(br)){
				bw.write("<tagroot>"+System.getProperty("line.separator"));
				for (String key : textMap.keySet()) {
					bw.write(parseTag(key));
					
					if(textMap.get(key)!=null && !textMap.get(key).isEmpty()){
						String[] ats = textMap.get(key).split(",");
						for(int i=0; i<ats.length; i++){
							bw.write(parseAttr(ats[i]));
						}
					}
					
					bw.write("\t</tag>"+System.getProperty("line.separator"));
				}
				bw.write("</tagroot>"+System.getProperty("line.separator"));
			}
			
			br.close();
			in.close();
			bw.flush();
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private static boolean readDataToMap (BufferedReader br){
		String line;
		try {
			while ((line = br.readLine()) != null) {
				line = line.replaceAll("["+System.getProperty("line.separator")+"\t\\s]", "");
				if(line.equals(".tag")){
					while(!(line = br.readLine().replaceAll("["+System.getProperty("line.separator")+"\t\\s]", "")).equals(".tagend")){
						String[] temp = line.split(":");
						if(temp.length != 2)return false;
						tagMap.put(temp[0],temp[1]);
					}
				}
				if(line.equals(".attr")){
					while(!(line = br.readLine().replaceAll("["+System.getProperty("line.separator")+"\t\\s]", "")).equals(".attrend")){
						String[] temp = line.split(":");
						if(temp.length != 2)return false;
						attrMap.put(temp[0],temp[1]);
					}
				}
				if(line.equals(".text")){
					while(!(line = br.readLine().replaceAll("["+System.getProperty("line.separator")+"\t\\s]", "")).equals(".textend")){
						String[] temp = line.split(":");
						if(temp.length == 1){
							textMap.put(temp[0], "");
						} else if(temp.length == 2){
							textMap.put(temp[0], temp[1]);
						} else{
							return false;
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private static String parseTag(String tag) {
		String t = tagMap.get(tag);
		return "\t<tag name=\"" + t + "\">"+System.getProperty("line.separator");
	}
	
	private static String parseAttr(String attr) {
		String at = attrMap.get(attr);
		String temp[] = at.split("=");
		if(temp.length == 1){
			return "\t\t<attr name=\"" + temp[0] + "\" value=\"\" />"+System.getProperty("line.separator");
		}else if(temp.length == 2){
			return "\t\t<attr name=\"" + temp[0] + "\" value=\"" + temp[1].replaceAll(",", " ") + "\" />"+System.getProperty("line.separator");
		}
		return null;
	}
	
	
}
