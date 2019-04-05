package org.fansxnet.common.annotation;

import java.lang.annotation.*;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/4/5 09:49 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Model {
    String json() default "";
}
