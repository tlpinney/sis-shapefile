/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package org.apache.sis.storage.shapefile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.apache.commons.io.EndianUtils;
import java.util.Arrays;
import java.util.Map;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.OperatorFactory;
import com.esri.core.geometry.OperatorExportToWkt;
import com.esri.core.geometry.Operator;




public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	
    	ShapeFile shp;
    	int count;
    	
    	
    	
    	shp = new ShapeFile("data/SignedBikeRoute_4326_clipped.shp");
    	
		//print(shp);
		
		count = 0;
		for (Integer i: shp.FeatureMap.keySet()) {
			//print(i);
			//print(shp.FeatureMap.get(i));
			//print("-----------------");
			count++;
			//System.exit(0);
	}
    	
		
		print(count);
		print(shp.FeatureCount);
		//Polyline poly = (Polyline) shp.FeatureMap.get(1).geom;
		
		
    	
    	shp = new ShapeFile("data/ANC90Ply_4326.shp");
		//print(shp);
		
		count = 0;
		for (Integer i: shp.FeatureMap.keySet()) {
			//print(i);
			//print(shp.FeatureMap.get(i));
			//print("-----------------");
			count++;
			//System.exit(0);
		}
		
		print(count);
		print(shp.FeatureCount);
		
		
		shp = new ShapeFile("data/ABRALicenseePt_4326_clipped.shp");
		//print(shp);
		
		count = 0;
		for (Integer i: shp.FeatureMap.keySet()) {
			//print(i);
			//print(shp.FeatureMap.get(i));
			//print("-----------------");
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
