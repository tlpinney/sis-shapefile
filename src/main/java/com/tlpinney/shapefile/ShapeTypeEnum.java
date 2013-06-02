package com.tlpinney.shapefile;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum ShapeTypeEnum  {

	NullShape (0),
	Point(1),
	PolyLine(3),
	Polygon(5),
	MultiPoint(8),
	PointZ(11),
	PolyLineZ(13),
	PolygonZ(15),
	MultiPointZ(18),
	PointM(21),
	PolyLineM(23),
	PolygonM(25),
	MultiPointM(28),
	MultiPatch(31);
	
	private int value;
	
	private ShapeTypeEnum (int value ) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	private static final Map<Integer, ShapeTypeEnum> lookup = new HashMap<Integer, ShapeTypeEnum>();
	

    static {
        for(ShapeTypeEnum ste : EnumSet.allOf(ShapeTypeEnum.class)) {
            lookup.put(ste.getValue(), ste);
        }
    }

    public static ShapeTypeEnum get(int value) { 
        return lookup.get(value); 
    }
	
}
