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


	
	public String getName() {
		return new String(this.FieldName);
	}
	
	public int getLength() {
		return this.FieldLength & 0xFF;
	}
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
