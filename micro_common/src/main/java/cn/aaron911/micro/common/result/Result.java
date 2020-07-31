package cn.aaron911.micro.common.result;

import lombok.Data;

/**
 * 返回结果实体类
 */
@Data
public class Result<T> {

    private boolean flag;
    private Integer code;
    private String message;
    private T data;

    public Result() {}

    public Result(boolean flag, Integer code, String message, T data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;

        this.message = message;
    }
}
