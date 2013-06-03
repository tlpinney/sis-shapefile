package org.apache.sis.storage.shapefile;

import java.io.File;
import java.io.IOException;
//import java.io.RandomAccessFile;

import java.nio.channels.Channel;
import java.nio.channels.ReadableByteChannel;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.io.FileInputStream;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.EndianUtils;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;



import org.apache.commons.codec.binary.Hex;




public class ShapeFile {

	public int FileCode;   // big
	public int FileLength;  // big // The value for file length is the total length of the file in 16-bit words
	public int Version; // little
	public ShapeTypeEnum ShapeType; // little
	public double xmin; // little
	public double ymin; // little 
	public double xmax; // little 
	public double ymax; // little 
	public double zmin; // little 
	public double zmax; // little 
	public double mmin; // little
	public double mmax; // little 
	//public int RecordNumber; // big 
	//public int ContentLength; // big
	
	
	// http://ulisse.elettra.trieste.it/services/doc/dbase/DBFstruct.htm
	public byte DbaseVersion;
	public byte[] DbaseLastUpdate = new byte[3];
	public int FeatureCount;
	public short DbaseHeaderBytes;
	public short DbaseRecordBytes;
	// reserve 3 bytes
	public byte[] DbasePlusLanReserved = new byte[13];
	// reserve 4 bytes
	
	
	
	
	
	public ArrayList<FieldDescriptor> FDArray = new ArrayList<FieldDescriptor>();
	public Map<Integer, Feature> FeatureMap = new HashMap<Integer, Feature>();
	
	
	
	
	// Helper functions to get Integers
	
	private int getLittleInt(byte [] data) {
		return EndianUtils.readSwappedInteger(data, 0);
	}
	
	private double getLittleDouble(byte [] data) {
		return EndianUtils.readSwappedDouble(data, 0);
	}
	
	private short getLittleShort(byte [] data) {
		return EndianUtils.readSwappedShort(data, 0);
	}
	
	
	public ShapeFile(String shpfile) throws IOException {
		// load the shapefile in fill in corresponding metadata 
		// RandomAccessFile rf = new RandomAccessFile(shpfile,"r");
		
		// should extend RandomAccessFile (or other Class)
		// and have function and have it to deal with different 
		// endian data
			
		FileInputStream fis = new FileInputStream(shpfile);
		FileChannel fc = fis.getChannel();
		int fsize = (int) fc.size();
		MappedByteBuffer rf = fc.map(FileChannel.MapMode.READ_ONLY, 0, fsize);
				
		this.FileCode = rf.getInt();
		rf.getInt(); rf.getInt(); rf.getInt(); rf.getInt(); rf.getInt();
		this.FileLength = rf.getInt() * 2;
		
		byte [] data = new byte[4];
		
		rf.get(data);
		this.Version = getLittleInt(data);
		
		rf.get(data);
		this.ShapeType = ShapeTypeEnum.get(getLittleInt(data));
		
		data = new byte[8];
		
		rf.get(data);
		this.xmin = getLittleDouble(data);
		
		rf.get(data);
		this.ymin = getLittleDouble(data);
		
		rf.get(data);
		this.xmax = getLittleDouble(data);

		rf.get(data);
		this.ymax = getLittleDouble(data);
		
		rf.get(data);
		this.zmin = getLittleDouble(data);
	

		rf.get(data);
		this.zmax = getLittleDouble(data);
		
		rf.get(data);
		this.mmin = getLittleDouble(data);

		rf.get(data);
		this.mmax = getLittleDouble(data);
		
		StringBuilder b = new StringBuilder(shpfile);
		b.replace(shpfile.length() - 3, shpfile.length() , "dbf");
		String file_base = b.toString();
		
			
		// http://ulisse.elettra.trieste.it/services/doc/dbase/DBFstruct.htm
			
		//RandomAccessFile df = new RandomAccessFile(file_base,"r");
		FileInputStream fis2 = new FileInputStream(file_base);
		FileChannel fc2 = fis2.getChannel();
		int fsize2 = (int) fc2.size();
		MappedByteBuffer df = fc2.map(FileChannel.MapMode.READ_ONLY, 0, fsize2);
		
		
		
		this.DbaseVersion = df.get();
		df.get(this.DbaseLastUpdate);
		data = new byte[4];
		df.get(data);
		this.FeatureCount = getLittleInt(data);
		data = new byte[2];
		df.get(data);
		this.DbaseHeaderBytes = getLittleShort(data);
		df.get(data);
		this.DbaseRecordBytes = getLittleShort(data);
		df.getShort(); // reserved 
		df.get(); // reserved
		df.get(DbasePlusLanReserved);
		df.getInt();
		
		
		
		while(df.position() <  this.DbaseHeaderBytes - 1) {
			FieldDescriptor fd = new FieldDescriptor();
			df.get(fd.FieldName);
			//print("FieldName: " + new String(fd.FieldName));
			fd.FieldType = df.get();	
			df.get(fd.FieldAddress);
			fd.FieldLength = df.get();
			fd.FieldDecimalCount = df.get();
			df.getShort(); // reserved
			df.get(fd.DbasePlusLanReserved2);
			fd.WorkAreaID = df.get();
			df.get(fd.DbasePlusLanReserved3);  
			fd.SetFields = df.get();
			data = new byte[6];
			df.get(data); // reserved
			
			this.FDArray.add(fd);
		// loop until you hit the 0Dh field terminator 
		}
		
		
		df.get(); // should be 0d for field terminator 
		
			
		
		// read in the record header
		
		
		
		
		//print(this);
		
		
		
		for (Integer i = 0; i < this.FeatureCount; i++) {
			// insert points into some type of list
			int RecordNumber = rf.getInt();
			int ContentLength = rf.getInt();
			
			//print("RecordNumber: " + RecordNumber);
			
			data = new byte[4];	
			rf.get(data);
			int ShapeType = getLittleInt(data);
			Feature f = new Feature();
			f.record = new HashMap<String, String>();
			//print(ShapeType);
			
			if (ShapeType == ShapeTypeEnum.Point.getValue()) {
				data = new byte[8];
			
				rf.get(data);
				double x = getLittleDouble(data);
			
				rf.get(data);
				double y = getLittleDouble(data);
						
				Point pnt = new Point(x,y);
				f.geom = pnt;
				
			
			} else if (ShapeType == ShapeTypeEnum.Polygon.getValue()) {
				data = new byte[8];
				rf.get(data);
				double xmin = getLittleDouble(data);
				rf.get(data);
				double ymin = getLittleDouble(data);
				rf.get(data);
				double xmax = getLittleDouble(data);
				rf.get(data);
				double ymax = getLittleDouble(data);
				
				data = new byte[4];
				rf.get(data);
				int NumParts = getLittleInt(data);
				rf.get(data);
				int NumPoints = getLittleInt(data);
				
				if (NumParts > 1) {
					// not implemented yet
					print("Not implemented yet.");
					System.exit(-1);
				}
				
				// Depending on num parts and num points 
				
				//print("NumParts: " + NumParts);
				//print("NumPoints: " + NumPoints);
				
				// read the one part 
				rf.get(data);
				int Part = getLittleInt(data);
				//print(Part);
				
				
				Polygon poly = new Polygon();
				
				// create a line from the points
				data = new byte[8];
				rf.get(data);
				double xpnt = getLittleDouble(data);
				rf.get(data);
				double ypnt = getLittleDouble(data);
				//Point oldpnt = new Point(xpnt, ypnt);
				poly.startPath(xpnt, ypnt);
				for (int j=0; j < NumPoints-1; j++) {
					rf.get(data);
					xpnt = getLittleDouble(data);
					rf.get(data);
					ypnt = getLittleDouble(data);
					poly.lineTo(xpnt, ypnt);	
					//print(xpnt + " " + ypnt);
				}
				f.geom = poly;
				
			} else if (ShapeType == ShapeTypeEnum.PolyLine.getValue()) {
				data = new byte[8];
				rf.get(data);
				double xmin = getLittleDouble(data);
				rf.get(data);
				double ymin = getLittleDouble(data);
				rf.get(data);
				double xmax = getLittleDouble(data);
				rf.get(data);
				double ymax = getLittleDouble(data);
				
				data = new byte[4];
				rf.get(data);
				int NumParts = getLittleInt(data);
				rf.get(data);
				int NumPoints = getLittleInt(data);
				
				//print("NumParts: " + NumParts);
				//print("NumPoints: " + NumPoints);
				int [] NumPartArr = new int[NumParts+1];
				
				for (int n=0; n < NumParts; n++) {
					rf.get(data);
					int idx = getLittleInt(data);
					//print("idx: " + idx);
					NumPartArr[n] = idx;
				}
				NumPartArr[NumParts] = NumPoints;
						
				data = new byte[8];
			
				
				double xpnt, ypnt;
				Polyline ply = new Polyline();
				
			
				for (int m=0; m < NumParts; m++) {
					//print("m: " + m);
					
					rf.get(data);
					xpnt = getLittleDouble(data);
					rf.get(data);
					ypnt = getLittleDouble(data);
					ply.startPath(xpnt, ypnt);
					
					for (int j=NumPartArr[m]; j < NumPartArr[m+1] - 1; j++) {
						//print("j: " + j);
						//print("NumPartArr[m+1]: " + NumPartArr[m+1]);
						rf.get(data);
						xpnt = getLittleDouble(data);
						rf.get(data);
						ypnt = getLittleDouble(data);
						ply.lineTo(xpnt, ypnt);
					}
				}
				
				
				//print("PathCount: " + ply.getPathCount());
				f.geom = ply;

				
				
			} else {
				print("Unsupported shapefile type");
				print(this.ShapeType);
				System.exit(-1);
			}
			
						
			// read in each Record and Populate the Feature
		
			df.get(); // denotes whether deleted or current
			
			// read first part of record 	
			for (FieldDescriptor fd: this.FDArray) {
				data = new byte[fd.getLength()];
				df.get(data);
				String value = new String(data);
				f.record.put(fd.getName(), value);				
			}
			
			
			this.FeatureMap.put(RecordNumber, f);
					
			
		}
	
		//df.close();
		//rf.close();	
		fc.close();
		fc2.close();
		
	}
	
	public String toString() { 
		
		StringBuilder s = new StringBuilder();
		
		s.append("FileCode: " + this.FileCode + "\n");
		s.append("FileLength: " + this.FileLength + "\n");
		s.append("Version: " + this.Version + "\n");
		s.append("ShapeType: " + this.ShapeType+ "\n");
		s.append("xmin: " + this.xmin+ "\n");
		s.append("ymin: "+ this.ymin+ "\n");
		s.append("xmax: " + this.xmax+ "\n");
		s.append("ymax: " + this.ymax+ "\n");
		s.append("zmin: " + this.zmin+ "\n");
		s.append("zmax: " + this.zmax+ "\n");
		s.append("mmin: " + this.mmin+ "\n");
		s.append("mmax: " + this.mmax+ "\n");	
		s.append("------------------------\n");
		s.append("DbaseVersion: " + this.DbaseVersion+ "\n");
		s.append("DbaseLastUpdate: " + new String(this.DbaseLastUpdate) +"\n");
		s.append("FeatureCount: " + this.FeatureCount +"\n");
		s.append("DbaseHeaderBytes: " + this.DbaseHeaderBytes +"\n");
		s.append("DbaseRecordBytes: " + this.DbaseRecordBytes +"\n");
		s.append("DbasePlusLanReserved: " + this.DbasePlusLanReserved +"\n");
		
		
			
		
		
		return s.toString();
	}
	
	
	
	  static void print(Object o){
	    	System.out.println(o);
	    }
	
}
