package cn.ucai.fulicenter.model.bean;

/**
 * Created by Administrator on 2017/1/9 0009.
 */

public class MessageBean {
    /**
     * success : true
     * msg : 添加收藏成功
     */

    private boolean success;
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }

}
