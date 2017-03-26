package wk.shop.model;

import java.util.List;

/**
 * Created by Finder丶畅畅 on 2017/3/26 19:12
 * QQ群481606175
 */

public class PageModel {

    /**
     * page : 1
     * total : 2
     * record : 11
     * orderlist : [{"orderid":"kd17031512562498760","shopname":"小李","addtime":"2017-03-15 12:56:15","DispatchTime":"","totalmoney":"4.0","state":"2","address":"","sendstate":"0","ordertype":"1","IsShopSet":"1","FoodNo":"","JuLi":"0","SendFee":"2","DeliverName":"","DeliverTel":"","OrderComm":"18612789876"},{"orderid":"kd17031512472298760","shopname":"小李","addtime":"2017-03-15 12:47:15","DispatchTime":"","totalmoney":"18.0","state":"2","address":"","sendstate":"0","ordertype":"1","IsShopSet":"1","FoodNo":"","JuLi":"0","SendFee":"0","DeliverName":"","DeliverTel":"","OrderComm":"18612789876"},{"orderid":"kd17031512244498760","shopname":"小李","addtime":"2017-03-15 12:24:15","DispatchTime":"","totalmoney":"10.0","state":"1","address":"","sendstate":"0","ordertype":"1","IsShopSet":"1","FoodNo":"","JuLi":"0","SendFee":"0","DeliverName":"","DeliverTel":"","OrderComm":"18612789876"},{"orderid":"kd17031512225398760","shopname":"小李","addtime":"2017-03-15 12:22:15","DispatchTime":"","totalmoney":"6.0","state":"1","address":"","sendstate":"0","ordertype":"1","IsShopSet":"1","FoodNo":"","JuLi":"0","SendFee":"0","DeliverName":"","DeliverTel":"","OrderComm":"18612789876"},{"orderid":"kd17031511592298760","shopname":"小李","addtime":"2017-03-15 11:59:15","DispatchTime":"","totalmoney":"2.0","state":"2","address":"","sendstate":"0","ordertype":"1","IsShopSet":"1","FoodNo":"","JuLi":"0","SendFee":"0","DeliverName":"","DeliverTel":"","OrderComm":"18612789876"},{"orderid":"kd17031511552298760","shopname":"小李","addtime":"2017-03-15 11:55:15","DispatchTime":"","totalmoney":"2.0","state":"2","address":"","sendstate":"0","ordertype":"1","IsShopSet":"1","FoodNo":"","JuLi":"0","SendFee":"0","DeliverName":"","DeliverTel":"","OrderComm":"18612789876"},{"orderid":"kd17031511524698760","shopname":"小李","addtime":"2017-03-15 11:52:15","DispatchTime":"","totalmoney":"6.0","state":"2","address":"","sendstate":"0","ordertype":"1","IsShopSet":"1","FoodNo":"","JuLi":"0","SendFee":"0","DeliverName":"","DeliverTel":"","OrderComm":"18612789876"},{"orderid":"17031511431998760","shopname":"小李","addtime":"2017-03-15 11:43:15","DispatchTime":"","totalmoney":"2.0","state":"2","address":"","sendstate":"0","ordertype":"1","IsShopSet":"1","FoodNo":"","JuLi":"0","SendFee":"0","DeliverName":"","DeliverTel":"","OrderComm":"18612789876"}]
     */

    private String page;
    private String total;
    private String record;
    private List<OrderModel> orderlist;
    private List<FoodModel> foodlist;
    private List<FoodTypeModel> foodtypelist;

    public List<FoodTypeModel> getFoodtypelist() {
        return foodtypelist;
    }

    public void setFoodtypelist(List<FoodTypeModel> foodtypelist) {
        this.foodtypelist = foodtypelist;
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

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public List<OrderModel> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<OrderModel> orderlist) {
        this.orderlist = orderlist;
    }

    public List<FoodModel> getFoodlist() {
        return foodlist;
    }

    public void setFoodlist(List<FoodModel> foodlist) {
        this.foodlist = foodlist;
    }
}
