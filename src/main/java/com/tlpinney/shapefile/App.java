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
    	
    	// Load up a shapefile from disk and turn it into esri geometries 
    	// Based on http://www.esri.com/library/whitepapers/pdfs/shapefile.pdf
    	// and http://en.wikipedia.org/wiki/Shapefile
    	
    	
        
		RandomAccessFile rf = new RandomAccessFile("/Users/user/Documents/workspace-shp/shapefile-api/data/ABRALicenseePt_4326.shp","r");
       
		MainFileHeader mfh = new MainFileHeader();
		
		mfh.FileCode = rf.readInt();
		rf.readInt(); rf.readInt(); rf.readInt(); rf.readInt(); rf.readInt();
		mfh.FileLength = rf.readInt() * 2;
		
		byte [] data = new byte[4];
		rf.read(data);
		mfh.Version = EndianUtils.readSwappedInteger(data, 0);
		Arrays.fill(data, (byte)0x00);
		rf.read(data);
		mfh.ShapeType = ShapeTypeEnum.get(EndianUtils.readSwappedInteger(data, 0));
		Arrays.fill(data, (byte)0x00);
		
		data = new byte[8];
		
		rf.read(data);
		mfh.xmin = EndianUtils.readSwappedDouble(data, 0);
		Arrays.fill(data, (byte)0x00);
		
		rf.read(data);
		mfh.ymin = EndianUtils.readSwappedDouble(data, 0);
		Arrays.fill(data, (byte)0x00);
		
		rf.read(data);
		mfh.xmax = EndianUtils.readSwappedDouble(data, 0);
		Arrays.fill(data, (byte)0x00);

		rf.read(data);
		mfh.ymax = EndianUtils.readSwappedDouble(data, 0);
		Arrays.fill(data, (byte)0x00);

		rf.read(data);
		mfh.zmin = EndianUtils.readSwappedDouble(data, 0);
		Arrays.fill(data, (byte)0x00);

		rf.read(data);
		mfh.zmax = EndianUtils.readSwappedDouble(data, 0);
		Arrays.fill(data, (byte)0x00);

		rf.read(data);
		mfh.mmin = EndianUtils.readSwappedDouble(data, 0);
		Arrays.fill(data, (byte)0x00);

		rf.read(data);
		mfh.mmax = EndianUtils.readSwappedDouble(data, 0);
		Arrays.fill(data, (byte)0x00);

		
		
		
		
		//print(ShapeTypeEnum.valueOf("0"));
		
		print(mfh.ShapeType);	
		print(mfh.FileCode);
		print(mfh.FileLength);
		print(mfh.Version);
		print(mfh.xmin);
		print(mfh.ymin);
		print(mfh.xmax);
		print(mfh.ymax);
		print(mfh.zmin);
		print(mfh.zmax);
		print(mfh.mmin);
		print(mfh.mmax);
			
		
		// read in the record header
		
		int RecordNumber = rf.readInt();
		int ContentLength = rf.readInt();
		
		print(RecordNumber);
		print(ContentLength);
		
		// actual record
		
		data = new byte[4];
		
		rf.read(data);
		int ShapeType = EndianUtils.readSwappedInteger(data, 0);
		Arrays.fill(data, (byte)0x00);
		
		data = new byte[8];
		
		rf.read(data);
		double x = EndianUtils.readSwappedDouble(data, 0);
		Arrays.fill(data, (byte)0x00);
		
		rf.read(data);
		double y = EndianUtils.readSwappedDouble(data, 0);
		Arrays.fill(data, (byte)0x00);
		
		// read in all the data
		// calculate how many records are needed to be read
		
		
		
		
		
		print(ShapeType);
		print(x);
		print(y);
		
		Point pnt = new Point(x,y);
		print(pnt);
		
		
		
		
		
		
		
		
		rf.close();
        
		ShapeFile shp = new ShapeFile("/Users/user/Documents/workspace-shp/shapefile-api/data/ABRALicenseePt_4326.shp");
		print(shp);
		
        
               
            
    }
    
    static void print(Object o){
    	System.out.println(o);
    }
}
