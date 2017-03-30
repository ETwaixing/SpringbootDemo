package com.waixing.entity.back;

/**
 * 返回信息
 *
 * Created by yonglang on 2017/3/30.
 */
public class BackMessage {
    /**
     * 是否成功
     */
    private boolean isSuccess;
    /**
     * code码
     */
    private String code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private Object data;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 返回成功 构造器
     * @param isSuccess
     * @param code
     * @param msg
     * @param data
     */
    public BackMessage(boolean isSuccess, String code, String msg, Object data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回失败构造器
     * @param isSuccess
     * @param code
     * @param msg
     */
    public BackMessage(boolean isSuccess, String code, String msg) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BackMessage{" +
                "isSuccess=" + isSuccess +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
