package com.tlpinney.shapefile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.apache.commons.io.EndianUtils;
import java.util.Arrays;
import java.util.Map;
import com.esri.core.geometry.Point;

import com.esri.core.geometry.OperatorImportFromESRIShape;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	
    	ShapeFile shp;
    	int count;
    	
    	
    	
    //	shp = new ShapeFile("data/SignedBikeRoute_4326.shp");
    	
	//	print(shp);
		
//		count = 0;
//		for (Integer i: shp.FeatureMap.keySet()) {
//			print(i);
//			print(shp.FeatureMap.get(i));
//			print("-----------------");
//			count++;
			//System.exit(0);
//		}
    	
		
//		print(count);
//		print(shp.FeatureCount);
//		print(shp.FeatureMap.get(1).geom.toString());
		
		
 //   	System.exit(0);
    	
    	shp = new ShapeFile("data/ANC90Ply_4326.shp");
		print(shp);
		
		count = 0;
		for (Integer i: shp.FeatureMap.keySet()) {
			print(i);
			print(shp.FeatureMap.get(i));
			print("-----------------");
			count++;
			//System.exit(0);
		}
		
		print(count);
		print(shp.FeatureCount);
		
		System.exit(0);
		
		shp = new ShapeFile("data/ABRALicenseePt_4326.shp");
		//print(shp);
		
		count = 0;
		for (Integer i: shp.FeatureMap.keySet()) {
			print(i);
			print(shp.FeatureMap.get(i));
			print("-----------------");
			count++;
			//System.exit(0);
		}
		
		print(count);
		print(shp.FeatureCount);
		
		
               
            
    }
    
    static void print(Object o){
    	System.out.println(o);
    }
}
