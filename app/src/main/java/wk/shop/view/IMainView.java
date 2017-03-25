package wk.shop.view;

import wk.shop.model.MessageModel;
import wk.shop.model.ShopInfoModel;
import wk.shop.model.ShopOrderModel;

/**
 * Created by Administrator on 2016/10/13.
 */

public interface IMainView {
    /**
     * 检查更新
     *
     * @param url     不需要更新返回“”，否则返回地址
     * @param content 显示更新的内容
     */
    void checkVersion(String url, String content);

    void changeResult(double lat, double lng);

    /**
     * 改变订单状态处理结果
     *
     * @param result
     */
    void changeStateResult(boolean result);

    //改变骑士状态
    void changeQsState(String result);

    void userDetail(ShopInfoModel model);

    void orderList(ShopOrderModel orders);
}
