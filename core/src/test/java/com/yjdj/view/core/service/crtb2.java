package com.yjdj.view.core.service;
import java.io.BufferedReader;
import java.io.FileReader;

import com.yjdj.view.core.util.ExcelField;

public class crtb2 {
	
	public  static void main(String[] args){
		StringBuffer sb = new StringBuffer();
	    String str=null;
	        try {
				//构造bufferreader对象
	        	int i=1;
				BufferedReader in = new BufferedReader(new FileReader("E:\\testfile2.txt"));
				String s = null;
				while((s=in.readLine())!=null&&s.contains(",")){
					//先获取=的位置
                    int eqaulindex = s.indexOf(",")+1;
                    String title=s.substring(eqaulindex, s.length());
					//				 	str="INSERT into  THM_FS12_02_tmp_tmp_copy  SELECT SUM(DMBTR),FISCPER,HKONT   FROM THM_FS12_02  where  BUKRS=5003  and HKONT in (" +s+")  and FKBER=6601 and  FISCPER BETWEEN 2016001 and  2016010;  -- "+i;						
//				 	str=i+title;
				 	str=title+",";
					i=i+1;
				 	System.out.println(str);
		        
				}
	        } catch (Exception e) {
				e.printStackTrace();
			} 
		}
}