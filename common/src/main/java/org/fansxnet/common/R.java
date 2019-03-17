package org.fansxnet.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/3 03:27 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Data
public class R <T> implements Serializable {
    public static final int OK_CODE = 0;
    public static final int ERROR_CODE = -1;

    private static final R OK = new R(OK_CODE, "成功");
    private static final R ERROR = new R(ERROR_CODE, "失败");

    private Integer code = 0;
    private String msg = "成功";
    @ApiModelProperty(value = "响应数据")
    private T data;

    public R() {
    }

    public R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R(int statusCode, T data) {
        this.code = statusCode;
        this.data = data;
    }

    public static R error(String msg) {
        return new R(ERROR_CODE, msg);
    }
    public static R error() {
        return error("失败");
    }
    public static R ok(Object data) {
        return new R(OK_CODE, data);
    }
    public static R ok() {
        return new R();
    }

    public static R just(Integer code,String msg) {
        return new R(code, msg);
    }














}
