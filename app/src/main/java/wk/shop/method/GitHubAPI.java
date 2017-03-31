package wk.shop.method;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;
import wk.shop.model.FoodTypeModel;
import wk.shop.model.GDToBD;
import wk.shop.model.ListModel;
import wk.shop.model.MessageModel;
import wk.shop.model.NormalMessageModel;
import wk.shop.model.OrderModel;
import wk.shop.model.PageModel;
import wk.shop.model.SaveModel;
import wk.shop.model.ShopInfoModel;
import wk.shop.model.ShopOrderModel;
import wk.shop.model.UpdateModel;

/**
 * Created by Administrator on 2016/9/20.
 */

public interface GitHubAPI {
    String url = "app/Android/deliver/";
    String normal_url = "app/shop/";

    //根据用户id获得用户信息
    @GET(normal_url + "getShopInfo.aspx")
    Observable<ShopInfoModel> getUserDetailById(@Query("shopid") String map);

    //获得今日订单量以及产生的总钱数
    @GET(normal_url + "GetSaleMoney.aspx")
    Observable<ShopOrderModel> getOrderList(@QueryMap Map<String, String> map);

    /**
     * @param map sortname	菜品类别名称	是
     *            jorder	排序编号 数字
     *            foodid	菜品类别编号	是
     *            shopid	商家编号	是
     * @return
     */
    //添加菜品分类
    @GET(normal_url + "addfoodsort.aspx")
    Observable<SaveModel> addFoodSort(@QueryMap Map<String, String> map);

    //删除商品 shopid,id 商品编号（多个用,分隔）
    @GET(normal_url + "deletefood.aspx")
    Observable<SaveModel> deleteFood(@QueryMap Map<String, String> map);

    //删除商品分类 shopid,id 分类编号（多个用,分隔）
    @GET(normal_url + "deletefoodsort.aspx")
    Observable<SaveModel> deleteFoodSort(@QueryMap Map<String, String> map);

    //添加更新商品
    @GET(normal_url + "EditorFoodDetail.aspx")
    Observable<SaveModel> editFood(@Query("ordermodel")String map);

    //提现记录
    @GET(normal_url + "GetCashOutList.aspx")
    Observable<ShopOrderModel> getCashOutList(@QueryMap Map<String, String> map);

    //当前余额，及可提现金额
    @GET(normal_url + "cashoutmoney.aspx")
    Observable<ShopOrderModel> getCashoutmoney(@QueryMap Map<String, String> map);

    //获得餐品详情
    @GET(normal_url + "GetFoodDetailByFoodId.aspx")
    Observable<ShopOrderModel> getFoodDetail(@QueryMap Map<String, String> map);

    //上传商品图片
    @GET(normal_url + "GetFoodImg.aspx")
    Observable<PageModel> putFoodImg(@QueryMap Map<String, String> map);

    //获得餐品列表
    @GET(normal_url + "GetFoodListByShopId.aspx")
    Observable<PageModel> getFoodListByShopId(@Query("shopid") String map);

    //订单统计
    @GET(normal_url + "DingDanTongJi.aspx")
    Observable<ShopOrderModel> getDingDanTongJi(@QueryMap Map<String, String> map);

    //根据用户ID获得订单信息
    @GET(normal_url + "GetOrderListByUserId.aspx")
    Observable<PageModel> getOrders(@QueryMap Map<String, String> map);
    //获得今日订单
    @GET(normal_url + "GetShopCustorderList.aspx")
    Observable<PageModel> getShopCustorderList(@QueryMap Map<String, String> map);

    //根据商家编号获取餐品分类
    @GET(normal_url + "GetFoodTypeListByShopId.aspx")
    Observable<PageModel> getFoodTypeListByShopId(@Query("shopid") String map);

    @GET("app/shop/shoplogin.aspx")
    Observable<NormalMessageModel> userInfo(@QueryMap Map<String, String> map);

    @GET("download/version.aspx?c=3")
    Observable<UpdateModel> checkUpdate();

    //获得订单列表
    @GET("App/Cpaotui/GetCityList.aspx")
    Observable<MessageModel> getCityList();

    //获得审核详情
    @GET("App/Android/Deliver/DeliverGeRenZiLiao.aspx")
    Observable<MessageModel> getSHDetail(@Query("DataId") String id);

    //修改骑士状态
    @GET(url + "GaiBianDeliverStatus.aspx")
    Observable<MessageModel> changeUserState(@QueryMap Map<String, String> map);

    //上传图片
    @GET(url + "AFanHui.aspx")
    Observable<MessageModel> checkUser(@QueryMap Map<String, String> map);

    //修改骑士密码
    @GET(url + "UpdatePassword.aspx")
    Observable<OrderModel> changePwd(@QueryMap Map<String, String> map);

    //获得验证码 phone手机号  type类型（0注册手机号 1找回密码）
    @GET(url + "DeliverZhuCeCode.aspx")
    Observable<MessageModel> getCode(@QueryMap Map<String, String> map);

    //骑士举报
    @GET(url + "DeliverJuBao.aspx")
    Observable<MessageModel> qs_jb(@QueryMap Map<String, String> map);

    //骑士注册 username手机号账号  password密码  type类型（0注册手机号  1找回密码）返回参数  dataID骑士id
    @GET(url + "DeliverZhuCe.aspx")
    Observable<MessageModel> regUser(@QueryMap Map<String, String> map);

    //抢单操作
    @GET(url + "deliverreceiveorder.aspx")
    Observable<OrderModel> qiangDan(@QueryMap Map<String, String> map);

    //根据订单编号获得订单详细信息
    @GET(url + "GetOrderDetailByOrderId.aspx")
    Observable<OrderModel> getOrderByOrderId(@Query("orderid") String map);

    //根据时间获得历史订单
    @GET(url + "DeliverLiShiCustorderList.aspx")
    Observable<ListModel> getHistoryByTime(@QueryMap Map<String, String> map);

    //更改订单状态
    @GET(url + "saveorderstate.aspx")
    Observable<OrderModel> changeOrderState(@QueryMap Map<String, String> map);

    //提交骑士信息到后台
    @GET(url + "saveorderstate.aspx")
    Observable<OrderModel> putDeliverMsg(@QueryMap Map<String, String> map);

    //?coords=115.4958,38.8869&from=1&to=5&ak=fNa5uQ9a7q8ygX2PVeG8BLRTACsFGffy
    @GET("geoconv/v1/")
    Observable<GDToBD> gdToBd(@QueryMap Map<String, String> map);

    @GET(url + "AddLatLng.aspx")
    Observable<MessageModel> pushLatLng(@QueryMap Map<String, String> map);
}
