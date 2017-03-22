package wk.shop.view;

import wk.shop.model.OrderModel;

/**
 * Created by Administrator on 2016/12/16.
 */

public interface IOrderDetail {
    void showOrder(OrderModel model);

    void changeResult(boolean result);
}
