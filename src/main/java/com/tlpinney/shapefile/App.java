package com.tlpinney.shapefile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.apache.commons.io.EndianUtils;
import java.util.Arrays;
import java.util.Map;
import com.esri.core.geometry.Point;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	
    
        
		ShapeFile shp = new ShapeFile("/Users/user/Documents/workspace-shp/shapefile-api/data/ABRALicenseePt_4326.shp");
		print(shp);
		
               
            
    }
    
    static void print(Object o){
    	System.out.println(o);
    }
}
