package cn.lfy.common.page;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.FastResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
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
import org.springframework.jdbc.support.JdbcUtils;

@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})  
public class PageResultSetInterceptor implements Interceptor {  
  
    private final static Logger log = LoggerFactory.getLogger(PageResultSetInterceptor.class);  
  
    private String dialect;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override  
    public Object intercept(Invocation invocation) throws Throwable {  
  
    	FastResultSetHandler resultSetHandler = (FastResultSetHandler) invocation.getTarget();  
        MetaObject metaResultSetHandler = MetaObject.forObject(resultSetHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory());  
        try {  
            ParameterHandler parameterHandler = (ParameterHandler) metaResultSetHandler.getValue("parameterHandler");  
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
      
                    BoundSql boundSql = (BoundSql) metaResultSetHandler.getValue("parameterHandler.boundSql");  
                    Connection connection = (Connection) metaResultSetHandler.getValue("executor.transaction.connection");  
      
                    String originalSql = boundSql.getSql();  
      
                    Dialect.Type databaseType = Dialect.Type.valueOf(dialect.toUpperCase()); 
                    
                    Dialect dialect = null;  
      
                    switch (databaseType) {  
                        case MYSQL:  
                            dialect = new MySql5Dialect();  
                            break;  
                    }  
      
                    // 修改sql，用于返回总记录数  
                    String sql = dialect.getCountString(originalSql);  
                    Integer totalRecord = getTotalRecord(connection, sql, parameterHandler);  
      
                    Object result = invocation.proceed();  
                    Page<?> page = new Page((List)result, pageIndex, pageSize, totalRecord);  
                    log.info("{}", page);
                    List<Page> pageList = new ArrayList<Page>();  
                    pageList.add(page);  
                    return pageList;  
                }  
            }
        } catch (Exception e) {  
            throw new Exception("Overwrite SQL : Fail!");  
        }  
  
        return invocation.proceed();  
    }  
  
    /** 
     * 执行 count 操作 
     * @param connection  数据库连接 
     * @param sql          sql 
     * @param parameterHandler  参数设置处理器 
     * @return 
     */  
    private Integer getTotalRecord(Connection connection,String sql,ParameterHandler parameterHandler){  
        PreparedStatement preparedStatement = null;  
        ResultSet resultSet = null;  
        try {  
  
            preparedStatement = connection.prepareStatement(sql);  
            parameterHandler.setParameters(preparedStatement);  
            resultSet = preparedStatement.executeQuery();  
            resultSet.next();  
  
            return (Integer) JdbcUtils.getResultSetValue(resultSet, 1, Integer.class);  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }finally {  
            JdbcUtils.closeResultSet(resultSet);  
            JdbcUtils.closeStatement(preparedStatement);  
        }  
        return 0;  
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
