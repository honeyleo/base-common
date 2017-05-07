package cn.lfy.common.page;

public class MySql5Dialect extends Dialect {  
	  
    public String getLimitString(String querySqlString, int offset, int limit) {  
        return querySqlString + " limit " + offset + " ," + limit;  
    }  
  
    @Override  
    public String getCountString(String querySqlString) {  
  
        int limitIndex = querySqlString.lastIndexOf("limit");  
  
        if(limitIndex != -1){  
            querySqlString = querySqlString.substring(0, limitIndex != -1 ? limitIndex : querySqlString.length() - 1);  
        }  
  
                // 用的过程中会发现这里对原有sql进行包装一层select count会有SQL效率低的问题  
                // 等待优化  
        return "SELECT COUNT(*) FROM (" + querySqlString + ") tem";  
    }  
  
    public boolean supportsLimit() {  
        return true;  
    }  
}  
