package org.fansxnet.common.resolver

import java.util

import org.apache.commons.lang3.StringUtils
import org.fansxnet.common.annotation.PathVariable
import org.fansxnet.common.util.ReflectionUtils
import org.springframework.core.MethodParameter
import org.springframework.core.convert.{ConversionService, TypeDescriptor}
import org.springframework.web.bind.annotation.ValueConstants
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver
import org.springframework.web.servlet.mvc.method.annotation.PathVariableMethodArgumentResolver
import org.springframework.web.util.UriComponentsBuilder

/**
  *
  *
  * @Description: <br>
  * @Project: hades <br>
  * @CreateDate: Created in 2019/3/12 00:01 <br>
  * @Author: <a href="xiaofancn@qq.com">abc</a>
  */
class RichMethodParameter(val methodParameter: MethodParameter) {
  @unchecked
  def getInheritedParameterAnnotation[A >: Null](annotationType: Class[A]): A = {
    println("配置",annotationType)
    val anns = ReflectionUtils.getInheritedParamAnnotations(methodParameter.getMethod, methodParameter.getParameterIndex);
    for (ann <- anns) {
      if (ann.isInstanceOf[A])
        return ann.asInstanceOf[A]
    }
    return null
  }
}

object Context {
  implicit def methodParameter2RichMethodParameter(methodParameter: MethodParameter) = new RichMethodParameter(methodParameter)
}

import Context.methodParameter2RichMethodParameter


class SuperPathVariableMethodArgumentResolver extends PathVariableMethodArgumentResolver {
  override def supportsParameter(methodParameter: MethodParameter): Boolean = {
    var result = super.supportsParameter(methodParameter)
    if (result) false
    methodParameter.getInheritedParameterAnnotation(classOf[PathVariable]) != null
  }

  override def contributeMethodArgument(parameter: MethodParameter, value: AnyRef, builder: UriComponentsBuilder, uriVariables: util.Map[String, AnyRef], conversionService: ConversionService): Unit = {
    val ann = parameter.getInheritedParameterAnnotation(classOf[PathVariable])
    val name = if (ann != null && StringUtils.isNoneBlank(ann.name)) ann.name() else parameter.getParameterName
    val formatted = formatUriValue(conversionService, new TypeDescriptor(parameter.nestedIfOptional), value)
    uriVariables.put(name, formatted)
  }

  override def createNamedValueInfo(parameter: MethodParameter): AbstractNamedValueMethodArgumentResolver.NamedValueInfo = {
    var ann = parameter.getInheritedParameterAnnotation(classOf[PathVariable]);
    return new PathVariableNamedValueInfo(ann)
  }

  class PathVariableNamedValueInfo(val annotation: PathVariable) extends AbstractNamedValueMethodArgumentResolver.NamedValueInfo(annotation.name, annotation.required, ValueConstants.DEFAULT_NONE) {
  }

}



