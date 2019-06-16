package com.insight.axiswevservice.enumpojo;


public enum  AssessmentItem {
		Caprini("Caprini评分", "1"),
		Padua("Padua评分", "2"),
		w("外科出血风险评估", "3"),
		n("内科出血风险评估", "4"),
		ma("药物预防禁忌评估", "5"),
		mo("机械预防禁忌评估", "6"),
		Wells("Wells评分", "7"),
		Wells2("简化Wells评分", "8"),
		Geneva("修订版Geneva评分", "9");
		
	    private final String key;
	    
	    private final String value;
	    
	    private AssessmentItem(String key, String value) {
	        this.key = key;
	        this.value = value;
	    }
	    
	    public static String getValue(String key) {  
	    	AssessmentItem[] assessmentTypes = values();  
	        for (AssessmentItem assessmentType : assessmentTypes) {  
	            if (assessmentType.getKey().equals(key)) {  
	                return assessmentType.getValue();  
	            }  
	        }  
	        return null;  
	    }   
	      
	    public static String getKey(String value) {  
	    	AssessmentItem[] assessmentTypes = values();  
	        for (AssessmentItem assessmentType : assessmentTypes) {  
	            if (assessmentType.getValue().equals(value)) {  
	                return assessmentType.getKey();  
	            }  
	        }  
	        return null;  
	    }  
	    
	    public String getKey() {
	    	
	        return this.key;
	    }
	    
	    public String getValue() {
	        return value;
	    }
}
