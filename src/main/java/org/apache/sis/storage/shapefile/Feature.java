package org.apache.sis.storage.shapefile;
import java.util.HashMap;


import com.esri.core.geometry.Geometry;


public class Feature {
	
	HashMap<String, String> record;  
	Geometry geom;
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		//print(this.record.size());
		for (String s : this.record.keySet()) {
			sb.append(s + ": " + this.record.get(s) + "\n");
		}

		return sb.toString();
	}
	
	static void print(Object o){
    	System.out.println(o);
    }

}
