package org.fansxnet.common.config;

import lombok.extern.slf4j.Slf4j;
import org.fansxnet.common.R;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/16 12:10 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlers  {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R serverExceptionHandler(Exception ex) {
        log.error(ex.getMessage(),ex);
        return R.error(ex.getLocalizedMessage());
    }
}
