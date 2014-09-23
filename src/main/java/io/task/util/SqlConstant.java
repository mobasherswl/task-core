package io.task.util;

public interface SqlConstant {

	String TBL_BEAN_PROPERTY = "bean_property";
	String TBL_BEAN = "bean";

	String COL_ID = "id";
	String COL_BEAN_ID = "bean_id";
	String COL_CLASS_NAME = "class_name";
	String COL_PROPERTY_ORDER = "property_order";
	String COL_PROPERTY_VALUE_TYPE = "property_value_type";
	String COL_PROPERTY_NAME = "property_name";
	String COL_PROPERTY_VALUE = "property_value";
	String COL_PROPERTY_VALUE_ORDER = "property_value_order";
	String COL_TYPE = "type";
	
	String QRY_SEL_TBL_BEAN = "select " + COL_ID + "," + COL_CLASS_NAME + "," + COL_TYPE + " from " + TBL_BEAN;
	String QRY_SEL_TBL_BEAN_PROPERTY = StringUtil.mergeStrings("select ",COL_BEAN_ID, ",", COL_CLASS_NAME, ",", COL_TYPE , ",", COL_PROPERTY_ORDER, ",", COL_PROPERTY_VALUE_TYPE, ",", COL_PROPERTY_NAME, ",", COL_PROPERTY_VALUE, ",", COL_PROPERTY_VALUE_ORDER, " from ", TBL_BEAN, ",", TBL_BEAN_PROPERTY, " where ", COL_BEAN_ID , "=", COL_ID, " order by ", COL_BEAN_ID, ",", COL_PROPERTY_NAME, ",", COL_PROPERTY_ORDER, ",", COL_PROPERTY_VALUE_ORDER);

}
