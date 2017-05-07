package cn.lfy.common.page;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})  
public class PageInterceptor implements Interceptor {  
  
    private final static Logger log = LoggerFactory.getLogger(PageInterceptor.class);  
  
    private String dialect;
    
    @SuppressWarnings("rawtypes")
	@Override  
    public Object intercept(Invocation invocation) throws Throwable {  
  
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();  
  
        ParameterHandler parameterHandler = statementHandler.getParameterHandler();  
        Object parameterObject = parameterHandler.getParameterObject();  
  
        if(parameterObject != null) {
        	Integer pageIndex = null;
            Integer pageSize = null;
            if(parameterObject instanceof MapperMethod.ParamMap) {  
      
                MapperMethod.ParamMap paramMapObject = (MapperMethod.ParamMap)parameterObject ;  
      
                if(paramMapObject != null) {  
                    for(Object key : paramMapObject.keySet()) {  
                        if("pageIndex".equals(key)) {
                        	pageIndex = (Integer)paramMapObject.get(key);
                        } else if("pageSize".equals(key)) {
                        	pageSize = (Integer)paramMapObject.get(key);
                        }
                    }  
                }  
            } else {
            	Field pageIndexField = ReflectHelper.getFieldByFieldName(parameterObject, "pageIndex");
    			if(pageIndexField != null) {
    				pageIndex = (Integer)ReflectHelper.getValueByFieldName(parameterObject, "pageIndex");
    			}
    			Field pageSizeField = ReflectHelper.getFieldByFieldName(parameterObject, "pageSize");
    			if(pageSizeField != null) {
    				pageSize = (Integer)ReflectHelper.getValueByFieldName(parameterObject, "pageSize");
    			}
            }
            
            if (pageIndex != null && pageSize != null) {  
      
                MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory());  
                Dialect.Type databaseType = null;  
      
                try {  
                    databaseType = Dialect.Type.valueOf(dialect.toUpperCase());  
                } catch (Exception e) {  
                    throw new Exception("Generate SQL: Obtain DatabaseType Failed!");  
                } 
      
                Dialect dialect = null;  
                switch (databaseType) {  
                    case MYSQL:  
                        dialect = new MySql5Dialect();  
                        break;  
                }  
      
                String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");  
                metaStatementHandler.setValue("delegate.boundSql.sql", dialect.getLimitString(originalSql, pageIndex * pageSize, pageSize));  
      
                if (log.isDebugEnabled()) {  
                    BoundSql boundSql = statementHandler.getBoundSql();
                    log.debug("Generate SQL : " + boundSql.getSql());
                }  
                return invocation.proceed();
            }
        }
        return invocation.proceed();
    }  
  
    @Override  
    public Object plugin(Object target) {  
        return Plugin.wrap(target, this);  
    }  
  
    @Override  
    public void setProperties(Properties properties) {  
    	dialect = properties.getProperty("dialect");
    }  
  
} 
