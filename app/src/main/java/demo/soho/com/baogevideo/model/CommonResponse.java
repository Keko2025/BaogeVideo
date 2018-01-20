package demo.soho.com.baogevideo.model;

/**
 * Created by dell on 2017/6/29.
 */

public class CommonResponse<T> {
    //结果码
    private int status = 1;
    /*错误信息:msg, error, message*/
    private String msg,url;
    /*真实数据 data或者result*/
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
