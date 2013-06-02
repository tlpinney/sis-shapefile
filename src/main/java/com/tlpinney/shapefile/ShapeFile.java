package com.tlpinney.shapefile;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.EndianUtils;

import com.esri.core.geometry.Point;




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
	
	public byte DbaseVersion;
	public byte[] DbaseLastUpdate = new byte[3];
	public int FeatureCount;
	public short HeaderBytes;
	public short RecordBytes;
	// reserve 2 bytes
	public byte DbaseTransactionState;
	public byte DbaseEncryption;
	public byte[] MultiUserReserved = new byte[12];
	public byte ProductionMDX;
	public byte LangDriverID;
	// reserved 2 bytes
	public byte[] LangDriverName = new byte[32];
	// reserved 4 bytes
	public byte[] FieldDescriptorArray = new byte[48];
	
	public Map<Integer, Object> FeatureMap = new HashMap<Integer, Object>();
	
	
	
	
	// Helper functions to get Integers
	
	private int getLittleInt(byte [] data) {
		return EndianUtils.readSwappedInteger(data, 0);
	}
	
	private double getLittleDouble(byte [] data) {
		return EndianUtils.readSwappedDouble(data, 0);
	}
			
	public ShapeFile(String shpfile) throws IOException {
		// load the shapefile in fill in corresponding metadata 
		RandomAccessFile rf = new RandomAccessFile(shpfile,"r");
		
		// should extend RandomAccessFile (or other Class)
		// and have function and have it to deal with different 
		// endian data
			
		this.FileCode = rf.readInt();
		rf.readInt(); rf.readInt(); rf.readInt(); rf.readInt(); rf.readInt();
		this.FileLength = rf.readInt() * 2;
		
		byte [] data = new byte[4];
		rf.read(data);
		this.Version = getLittleInt(data);
		
		rf.read(data);
		this.ShapeType = ShapeTypeEnum.get(getLittleInt(data));
		
		data = new byte[8];
		
		rf.read(data);
		this.xmin = getLittleDouble(data);
		
		rf.read(data);
		this.ymin = getLittleDouble(data);
		
		rf.read(data);
		this.xmax = getLittleDouble(data);

		rf.read(data);
		this.ymax = getLittleDouble(data);
		
		rf.read(data);
		this.zmin = getLittleDouble(data);
	

		rf.read(data);
		this.zmax = getLittleDouble(data);
		
		rf.read(data);
		this.mmin = getLittleDouble(data);

		rf.read(data);
		this.mmax = getLittleDouble(data);
		
		//print(ShapeTypeEnum.valueOf("0"));
		
		// read in the dbf file to get the record count 
		
		// needs to be portable
		//String[] path_split = shpfile.split("/");
		//String fname  = path_split[path_split.length - 1];
		//print(fname);
		//String [] file_split = fname.split("\\.");
		// this will mess up if there is more than one . 
		//print(file_split[0]);
		//String file_base = file_split[0];
		//file_base += ".dbf";
		//print(file_base);
		
		// replace last 3 characters of shp with dbf
		//String file_base = shpfile.replace("shp", "dbf");
		//print(file_base);
		
		StringBuilder b = new StringBuilder(shpfile);
		b.replace(shpfile.length() - 3, shpfile.length() , "dbf");
		String file_base = b.toString();
		
		
		
		
		RandomAccessFile df = new RandomAccessFile(file_base,"r");
		this.DbaseVersion = df.readByte();
		df.read(this.DbaseLastUpdate);
		data = new byte[4];
		df.read(data);
		this.FeatureCount = getLittleInt(data);
		
		
		df.close();
		
		
		
		
			
		
		// read in the record header
		
		//this.RecordNumber = rf.readInt();
		//this.ContentLength = rf.readInt();
		
		
		
		
		
		
		for (Integer i = 0; i < this.FeatureCount; i++) {
			// insert points into some type of list
			data = new byte[4];	
			rf.read(data);
			int ShapeType = getLittleInt(data);
			
			data = new byte[8];
			
			rf.read(data);
			double x = getLittleInt(data);
			
			rf.read(data);
			double y = getLittleInt(data);
						
			Point pnt = new Point(x,y);
			
			this.FeatureMap.put(i, pnt);
			
			//print(pnt);
			
			// read next record header 
			
			//print(rf.readInt());
			//print(rf.readInt());
			
			
			
		}
		
		print(this.FeatureMap.size());
		
		
		// read in all the data
		// calculate how many records are needed to be read
		
		
		
		
		
		
		
		
		
		
		rf.close();
		
		
		
		
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
		s.append("DbaseLastUpdate: " + this.DbaseLastUpdate +"\n");
		s.append("FeatureCount: " + this.FeatureCount +"\n");
		
		
		
		return s.toString();
	}
	
	
	
	 static void print(Object o){
	    	System.out.println(o);
	    }
	
}
