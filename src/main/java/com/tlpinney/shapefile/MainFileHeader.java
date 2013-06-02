package com.tlpinney.shapefile;

public class MainFileHeader {

	// not sure if this is unsigned or signed for now...
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
	
	
}
