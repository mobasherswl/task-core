package io.task.util;


public class BeanUtil {
	
	public static boolean isConstructorMethod(String className, String methodName) throws ClassNotFoundException
	{
		return isConstructorMethod(ClassUtil.getClass(className), methodName);
	}

	public static boolean isConstructorMethod(Class<?> clazz, String methodName)
	{
		boolean isConstructor = false;

		if(ClassUtil.isPrimitiveDataType(clazz) == false)
		{
			isConstructor = (methodName.equals(clazz.getSimpleName()) || methodName.equals(TaskConstant.VIRTUAL_CONSTRUCTOR_METHOD));
		}
		
		return isConstructor;
	}
	
	public static boolean isConstructorMethodStringOnly(String className, String methodName)
	{
		boolean isConstructor = false;

		if(ClassUtil.isPrimitiveDataType(className) == false)
		{
			String tokens[] = className.split("\\.");
			isConstructor = (tokens[tokens.length - 1].equals(methodName) || methodName.equals(TaskConstant.VIRTUAL_CONSTRUCTOR_METHOD));
		}

		return isConstructor;
	}
}
