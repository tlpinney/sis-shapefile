package com.tlpinney.shapefile;

public class FieldDescriptor {
	
	public byte[] FieldName = new byte[11];
	public byte FieldType;
	public byte[] FieldAddress = new byte[4];
	public byte FieldLength;
	public byte FieldDecimalCount;
	public byte[] DbasePlusLanReserved2 = new byte[2];
	public byte WorkAreaID; 
	public byte[] DbasePlusLanReserved3 = new byte[2];
	public byte SetFields;

	
	public String toString() { 
		
	StringBuilder s = new StringBuilder();
		
	s.append("FieldName : " + new String(this.FieldName) + "\n");
	s.append("FieldType : " + String.valueOf((char)this.FieldType) + "\n");
	s.append("FieldAddress : " + this.FieldAddress + "\n");		
	s.append("FieldLength : " + this.FieldLength + "\n");
	s.append("FieldDecimalCount: " + this.FieldDecimalCount + "\n");
	s.append("DbasePlusLanReserved2: " + this.DbasePlusLanReserved2 + "\n");
	s.append("WorkAreaID: " + this.WorkAreaID + "\n");
	s.append("DbasePlusLanReserved3: " + this.DbasePlusLanReserved3 + "\n");
	s.append("SetFields: " + this.SetFields + "\n");
	
	return s.toString();
	
	}
	
}
