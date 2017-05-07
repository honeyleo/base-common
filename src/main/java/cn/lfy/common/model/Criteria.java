package cn.lfy.common.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 公用条件查询类
 */
public class Criteria {
    /**
     * 存放条件查询值
     */
    private Map<String, Object> condition;

    /**
     * 是否相异
     */
    protected boolean distinct;

    /**
     * 排序字段
     */
    protected LinkedHashMap<String, Boolean> orderMap;

    private Integer offset;

    private Integer rows;

    protected Criteria(Criteria example) {
        this.condition = example.condition;
        this.distinct = example.distinct;
        this.offset = example.offset;
        this.rows = example.rows;
        this.orderMap = example.orderMap;
    }

    public Criteria() {
        condition = new HashMap<String, Object>();
        orderMap = new LinkedHashMap<String, Boolean>();
    }

    public void clear() {
        condition.clear();
        distinct = false;
        this.offset = null;
        this.rows = null;
    }

    /**
     * @param condition 
	 *            查询的条件名称
	 * @param value
	 *            查询的值
     */
    public Criteria put(String condition, Object value) {
        this.condition.put(condition, value);
        return (Criteria) this;
    }

    public Criteria putOrder(String condition, boolean isDesc) {
    	this.orderMap.put(condition, isDesc);
    	return (Criteria) this;
    }

	/**
     * @param distinct 
	 *            是否相异
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public void setCondition(Map<String, Object> condition) {
        this.condition = condition;
    }

    public Map<String, Object> getCondition() {
        return condition;
    }
    
    public LinkedHashMap<String, Boolean> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(LinkedHashMap<String, Boolean> orderMap) {
		this.orderMap = orderMap;
	}

	/**
     * @param mysqlOffset 
	 *            指定返回记录行的偏移量<br>
	 *            mysqlOffset= 5,mysqlLength=10;  // 检索记录行 6-15
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * @param mysqlLength 
	 *            指定返回记录行的最大数目<br>
	 *            mysqlOffset= 5,mysqlLength=10;  // 检索记录行 6-15
     */
    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
