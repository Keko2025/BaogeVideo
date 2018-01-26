package demo.soho.com.baogevideo.model;

import java.util.List;

/**
 * @author dell
 * @data 2018/1/25.
 */

public class CodeBean {

    /**
     * status : 1
     * msg : 发送短信成功
     * url :
     * data : []
     */

    private int status;
    private String msg;
    private String url;
    private List<?> data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
