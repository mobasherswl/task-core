package io.task.util;

import java.util.HashMap;
import java.util.Map;

public class ClassUtil {

	private static Map<String,Class<?>> builtinDataTypeMap = new HashMap<String,Class<?>>();
	private static Map<String, Class<?>> primitiveDataTypeMap = new HashMap<String, Class<?>>();

	static {
		primitiveDataTypeMap.put("int",int.class);
		primitiveDataTypeMap.put("long",long.class);
		primitiveDataTypeMap.put("byte",byte.class);
		primitiveDataTypeMap.put("float",float.class);
		primitiveDataTypeMap.put("short",short.class);
		primitiveDataTypeMap.put("double",double.class);
		primitiveDataTypeMap.put("char",char.class);
		primitiveDataTypeMap.put("boolean",boolean.class);
		primitiveDataTypeMap.put("void",void.class);
		
		builtinDataTypeMap.putAll(primitiveDataTypeMap);

		builtinDataTypeMap.put(Integer.class.getSimpleName(), Integer.class);
		builtinDataTypeMap.put(Long.class.getSimpleName(), Long.class);
		builtinDataTypeMap.put(Byte.class.getSimpleName(), Byte.class);
		builtinDataTypeMap.put(Float.class.getSimpleName(), Float.class);
		builtinDataTypeMap.put(Short.class.getSimpleName(), Short.class);
		builtinDataTypeMap.put(Double.class.getSimpleName(), Double.class);
		builtinDataTypeMap.put(Character.class.getSimpleName(), Character.class);
		builtinDataTypeMap.put(Boolean.class.getSimpleName(), Boolean.class);
		builtinDataTypeMap.put(String.class.getSimpleName(), String.class);
		
		builtinDataTypeMap.put(Integer.class.getName(), Integer.class);
		builtinDataTypeMap.put(Long.class.getName(), Long.class);
		builtinDataTypeMap.put(Byte.class.getName(), Byte.class);
		builtinDataTypeMap.put(Float.class.getName(), Float.class);
		builtinDataTypeMap.put(Short.class.getName(), Short.class);
		builtinDataTypeMap.put(Double.class.getName(), Double.class);
		builtinDataTypeMap.put(Character.class.getName(), Character.class);
		builtinDataTypeMap.put(Boolean.class.getName(), Boolean.class);
		builtinDataTypeMap.put(String.class.getName(), String.class);
	}

	public static boolean isPrimitiveDataType(String className)
	{
		return primitiveDataTypeMap.containsKey(className);
	}
	
	public static boolean isPrimitiveDataType(Class<?> clazz)
	{
		return primitiveDataTypeMap.containsKey(clazz.getName());
	}

	public static Class<?> getClass(String className) throws ClassNotFoundException
	{
		if(builtinDataTypeMap.containsKey(className))
		{
			return builtinDataTypeMap.get(className);
		}
		else
		{
			return Class.forName(className);
		}
	}

	public static Object toJavaTypes( Class<?> clazz, String value ) {
	    if( Boolean.class == clazz || boolean.class == clazz ) return Boolean.parseBoolean( value );
	    if( Byte.class == clazz || byte.class == clazz) return Byte.parseByte( value );
	    if( Short.class == clazz || short.class == clazz) return Short.parseShort( value );
	    if( Integer.class == clazz || int.class == clazz) return Integer.parseInt( value );
	    if( Long.class == clazz || long.class == clazz) return Long.parseLong( value );
	    if( Float.class == clazz || float.class == clazz) return Float.parseFloat( value );
	    if( Double.class == clazz || double.class == clazz) return Double.parseDouble( value );
	    if( (Character.class == clazz || char.class == clazz) && value.length() == 1) return value.charAt(0);
	    return value;
	}
	
	public static boolean isBuiltInDataType(Class<?> clazz)
	{
		return builtinDataTypeMap.containsKey(clazz.getName());
	}

	public static boolean isBuiltInDataType(String className)
	{
		return builtinDataTypeMap.containsKey(className);
	}
}
