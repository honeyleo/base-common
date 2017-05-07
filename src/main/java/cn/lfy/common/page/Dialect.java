package cn.lfy.common.page;

public abstract class Dialect {

	public static enum Type {  
        MYSQL,
        ;
    }  
  
    public abstract String getLimitString(String querySqlString, int offset, int limit);  
  
    public abstract String getCountString(String querySqlString);
}
