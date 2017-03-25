package wk.shop.model;

/**
 * Created by Finder丶畅畅 on 2017/3/25 22:04
 * QQ群481606175
 */

public class OrderInfoModel {
    /**
     * orderid : kd17031512562498760
     * shopname : 快递商家
     * addtime : 2017-03-15 12:56:15
     * totalmoney : 4.0
     * state : 2
     * address : 保定市新市区茂业中心111111
     * PayMode : 5
     * paystate : 1
     * eattype : 0
     * sendmoney : 0
     */

    private String orderid;
    private String shopname;
    private String addtime;
    private String totalmoney;
    private String state;
    private String address;
    private String PayMode;
    private String paystate;
    private String eattype;
    private String sendmoney;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(String totalmoney) {
        this.totalmoney = totalmoney;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayMode() {
        return PayMode;
    }

    public void setPayMode(String PayMode) {
        this.PayMode = PayMode;
    }

    public String getPaystate() {
        return paystate;
    }

    public void setPaystate(String paystate) {
        this.paystate = paystate;
    }

    public String getEattype() {
        return eattype;
    }

    public void setEattype(String eattype) {
        this.eattype = eattype;
    }

    public String getSendmoney() {
        return sendmoney;
    }

    public void setSendmoney(String sendmoney) {
        this.sendmoney = sendmoney;
    }
}
