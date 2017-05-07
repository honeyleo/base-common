package cn.lfy.common.binder;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

public class RenamingProcessor extends ServletModelAttributeMethodProcessor {

	@Autowired
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

	// Rename cache
	private final Map<Class<?>, Map<String, String>> replaceMap = new ConcurrentHashMap<Class<?>, Map<String, String>>();

	public RenamingProcessor(boolean annotationNotRequired) {
		super(annotationNotRequired);
	}

	@Override
	protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest nativeWebRequest) {
		Object target = binder.getTarget();
		Class<?> targetClass = target.getClass();
		if (!replaceMap.containsKey(targetClass)) {
			Map<String, String> mapping = analyzeClass(targetClass);
			replaceMap.put(targetClass, mapping);
		}
		Map<String, String> mapping = replaceMap.get(targetClass);
		ParamNameDataBinder paramNameDataBinder = new ParamNameDataBinder(target, binder.getObjectName(), mapping);
		requestMappingHandlerAdapter.getWebBindingInitializer().initBinder(paramNameDataBinder, nativeWebRequest);
		super.bindRequestParameters(paramNameDataBinder, nativeWebRequest);
	}

	private static Map<String, String> analyzeClass(Class<?> targetClass) {
		Field[] fields = targetClass.getDeclaredFields();
		Map<String, String> renameMap = new HashMap<String, String>();
		for (Field field : fields) {
			ParamName paramNameAnnotation = field.getAnnotation(ParamName.class);
			if (paramNameAnnotation != null && !paramNameAnnotation.value().isEmpty()) {
				renameMap.put(paramNameAnnotation.value(), field.getName());
			}
		}
		if (renameMap.isEmpty())
			return Collections.emptyMap();
		return renameMap;
	}
}