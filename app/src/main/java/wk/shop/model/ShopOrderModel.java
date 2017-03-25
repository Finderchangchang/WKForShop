package wk.shop.model;

import java.util.List;

/**
 * Created by Finder丶畅畅 on 2017/3/25 22:03
 * QQ群481606175
 */

public class ShopOrderModel {

    /**
     * OrderCount : 11
     * OrderTotal : 58.0
     * page : 1
     * total : 2
     * orderlist : [{"orderid":"kd17031512562498760","shopname":"快递商家","addtime":"2017-03-15 12:56:15","totalmoney":"4.0","state":"2","address":"保定市新市区茂业中心111111","PayMode":"5","paystate":"1","eattype":"0","sendmoney":"0"},{"orderid":"kd17031512472298760","shopname":"快递商家","addtime":"2017-03-15 12:47:15","totalmoney":"18.0","state":"2","address":"保定市新市区茂业中心111111","PayMode":"5","paystate":"1","eattype":"0","sendmoney":"0"},{"orderid":"kd17031512244498760","shopname":"快递商家","addtime":"2017-03-15 12:24:15","totalmoney":"10.0","state":"1","address":"保定市新市区茂业中心111111","PayMode":"5","paystate":"1","eattype":"0","sendmoney":"0"},{"orderid":"kd17031512225398760","shopname":"快递商家","addtime":"2017-03-15 12:22:15","totalmoney":"6.0","state":"1","address":"保定市新市区茂业中心111111","PayMode":"5","paystate":"1","eattype":"0","sendmoney":"0"},{"orderid":"kd17031511592298760","shopname":"快递商家","addtime":"2017-03-15 11:59:15","totalmoney":"2.0","state":"2","address":"保定市新市区茂业中心111111","PayMode":"5","paystate":"1","eattype":"0","sendmoney":"0"},{"orderid":"kd17031511552298760","shopname":"快递商家","addtime":"2017-03-15 11:55:15","totalmoney":"2.0","state":"2","address":"保定市新市区茂业中心111111","PayMode":"5","paystate":"1","eattype":"0","sendmoney":"0"},{"orderid":"kd17031511524698760","shopname":"快递商家","addtime":"2017-03-15 11:52:15","totalmoney":"6.0","state":"2","address":"保定市新市区茂业中心111111","PayMode":"5","paystate":"1","eattype":"0","sendmoney":"0"},{"orderid":"17031511431998760","shopname":"快递商家","addtime":"2017-03-15 11:43:15","totalmoney":"2.0","state":"2","address":"保定市新市区茂业中心111111","PayMode":"5","paystate":"1","eattype":"0","sendmoney":"0"},{"orderid":"17031511364798760","shopname":"快递商家","addtime":"2017-03-15 11:36:15","totalmoney":"2.0","state":"2","address":"保定市新市区茂业中心111111","PayMode":"5","paystate":"1","eattype":"0","sendmoney":"0"},{"orderid":"17031511295698760","shopname":"快递商家","addtime":"2017-03-15 11:29:15","totalmoney":"2.0","state":"2","address":"保定市新市区茂业中心111111","PayMode":"5","paystate":"1","eattype":"0","sendmoney":"0"}]
     */

    private String OrderCount;
    private String OrderTotal;
    private String page;
    private String total;
    private List<OrderInfoModel> orderlist;

    public String getOrderCount() {
        return OrderCount;
    }

    public void setOrderCount(String OrderCount) {
        this.OrderCount = OrderCount;
    }

    public String getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(String OrderTotal) {
        this.OrderTotal = OrderTotal;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<OrderInfoModel> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<OrderInfoModel> orderlist) {
        this.orderlist = orderlist;
    }
}
