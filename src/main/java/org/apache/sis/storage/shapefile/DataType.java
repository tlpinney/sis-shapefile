package org.apache.sis.storage.shapefile;


// http://www.clicketyclick.dk/databases/xbase/format/data_types.html

public enum DataType {
	
	Character, // < 254 characters 
	Number, // < 18 characters, can include sign and decimal  
	Logical, // 3 way, ? Y,y,T,t  N,n,F,f
	Date, // YYYYMMDD
	Memo, // Pointer to ASCII text field
	FloatingPoint, // 20 digits
	CharacterNameVariable, // 1-254 Characters
    Picture, // Memo
    Currency, // Foxpro
    DateTime, // 32 bit little-endian Julian date, 32 byte little endian milliseconds since midnight
    Integer, // 4 byte little endian
    VariField, // ???
    Variant, // ???
    TimeStamp, // see url
    Double, //
    AutoIncrement // ???
}
