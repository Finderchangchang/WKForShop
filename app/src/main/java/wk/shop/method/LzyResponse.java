package wk.shop.method;

import java.io.Serializable;
import java.util.List;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：16/9/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LzyResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;
    public int total;
    public int code;
    public String msg;
    public String state;
    public List<T> rows;
    public String RandomNumber;
    public boolean Success;
    public String Message;
    public String Time;
    public String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRandomNumber() {
        return RandomNumber;
    }

    public void setRandomNumber(String randomNumber) {
        RandomNumber = randomNumber;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}