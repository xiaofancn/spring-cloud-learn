package org.fansxnet.common.util;

import org.springframework.util.ConcurrentReferenceHashMap;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/10 01:53 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
public class ReflectionUtils extends org.springframework.util.ReflectionUtils {
    private static final Map<Method, Annotation[][]> inheritedParamAnnotationsCache = new ConcurrentReferenceHashMap<>(256);
    /**
     * This will get parameter's annotations from super class or interface
     * The order of result is current class, interfaces, super class
     * @param method which method's param want to get
     * @param paramIndex param index of method
     * @return
     */
    public static Annotation[] getInheritedParamAnnotations(final Method method, int paramIndex){
        Annotation[][] cachedResult = inheritedParamAnnotationsCache.get(method);
        if(cachedResult == null){
            inheritedParamAnnotationsCache.put(method, new Annotation[method.getParameterCount()][]);

            // Recursion call it again make sure cache is available
            return getInheritedParamAnnotations(method, paramIndex);
        }
        else{
            if(cachedResult[paramIndex] == null){
                List<Annotation> annotations = getInheritedParamAnnotations(method.getDeclaringClass(), method, paramIndex);

                // Remove duplicated annotation in result
                Set<Annotation> annotationSet = new LinkedHashSet<>(annotations);
                Annotation[] result = annotationSet.toArray(new Annotation[0]);

                // Add result to cache
                cachedResult[paramIndex] = result;
            }

            return cachedResult[paramIndex];
        }
    }

    @SuppressWarnings("unchecked call")
    private static List<Annotation> getInheritedParamAnnotations(@Nullable final Class clazz, final Method method, final int paramIndex) {
        if(clazz == null){
            return new ArrayList<>();
        }

        Annotation[][] paramAnnotations = method.getParameterAnnotations();

        final int index;
        if (paramIndex >= paramAnnotations.length || paramIndex< 0) {
            index = 0; // Should we throw a exception?
        }
        else{
            index = paramIndex;
        }

        List<Annotation> annotations = new ArrayList<>();

        // Add all annotations from current clazz
        try{
            // Get method with same signature
            Method currentMethod = clazz.getDeclaredMethod(method.getName(), method.getParameterTypes());
            Annotation[] currentParamAnnotations = currentMethod.getParameterAnnotations()[paramIndex];
            annotations.addAll(Arrays.asList(currentParamAnnotations));
        }catch (NoSuchMethodException ignore){
            // Ignored 'NoSuchMethodException' for class which has no method
        }

        // Then add all annotations from interfaces(recursion call)
        annotations.addAll(Arrays.stream(clazz.getInterfaces())
                .map(item-> getInheritedParamAnnotations(item, method, index))
                .flatMap(Collection::stream).collect(Collectors.toList()));

        // Add all annotations from super class finally(recursion call)
        annotations.addAll(getInheritedParamAnnotations(clazz.getSuperclass(), method, index));

        return annotations;
    }
    public static void clearCache() {
        inheritedParamAnnotationsCache.clear();
    }
}
