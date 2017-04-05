package wk.shop.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import net.tsz.afinal.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import wk.shop.R;
import wk.shop.base.BaseActivity;
import wk.shop.listener.MainFragListener;
import wk.shop.method.CommonAdapter;
import wk.shop.method.CommonViewHolder;
import wk.shop.model.GoodModel;
import wk.shop.model.OrderModel;
import wk.shop.view.IOrderDetail;

/**
 * Created by Administrator on 2016/12/13.
 */

public class OrderDetailActivitys extends BaseActivity implements IOrderDetail {
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    String orderid;
    MainFragListener listener;
    int width;
    String btn_state;
    @Bind(R.id.bottom_btn)
    Button bottom_btn;
    @Bind(R.id.sh_user_name_tv)
    TextView shUserNameTv;
    @Bind(R.id.sh_tel_tv)
    TextView shTelTv;
    @Bind(R.id.full_menu_lv)
    ListView fullMenuLv;
    @Bind(R.id.order_id_tv)
    TextView orderIdTv;
    @Bind(R.id.sr_tv)
    TextView srTv;
    @Bind(R.id.sh_address_tv)
    TextView shAddressTv;
    @Bind(R.id.remark_tv)
    TextView remarkTv;
    List<GoodModel> list;
    CommonAdapter<GoodModel> commonAdapter;

    @Override
    public void initViews() {
        setContentView(R.layout.ac_order_detail);
        ButterKnife.bind(this);
        list = new ArrayList<>();
        titleBar.setLeftClick(() -> finish());
        listener = new MainFragListener(this);
        orderid = getIntent().getStringExtra("orderid");
        listener.getOrderDetail(orderid);
        width = getWindowManager().getDefaultDisplay().getWidth();
        if (getIntent().getBooleanExtra("history", false)) {
            bottom_btn.setVisibility(View.GONE);
        }
        commonAdapter = new CommonAdapter<GoodModel>(this, list, R.layout.item_good) {
            @Override
            public void convert(CommonViewHolder holder, GoodModel goodModel, int position) {
                holder.setText(R.id.good_name_tv, goodModel.getName());
                holder.setText(R.id.good_num_tv, goodModel.getCount());
                holder.setText(R.id.good_price_tv, goodModel.getPrice());
            }
        };
        fullMenuLv.setAdapter(commonAdapter);
    }

    @Override
    public void initEvents() {

    }

    @Override
    public void showOrder(OrderModel model) {
        if (model != null) {
            switch (model.getSendstate()) {
                case "0":
                    btn_state = "抢单";
                    break;
                case "1":
                    btn_state = "到商家";
                    break;
            }
            commonAdapter.refresh(model.getList());
            shAddressTv.setText(model.getAddress());
            shUserNameTv.setText(model.getUsername());
            bottom_btn.setText(btn_state);
            shTelTv.setText(model.getPhone());
            bottom_btn.setOnClickListener(v -> {
                        switch (model.getSendstate()) {
                            case "0":
                                listener.changeOrderState("1", model.getOrderid());
                                break;
                            case "1":
                                listener.changeOrderState("5", model.getOrderid());
                                break;
                            case "5":
                                Intent intent = new Intent(OrderDetailActivitys.this, CameraActivity.class);
                                intent.putExtra("orderid", model.getOrderid());
                                startActivityForResult(intent, 99);
                                listener.changeOrderState("2", model.getOrderid());
                                break;
                            case "2":
                                listener.changeOrderState("3", model.getOrderid());
                                break;
                            case "3"://已完成
                                break;
                            case "4"://已取消
                                break;
                        }
                    }
            );
            shAddressTv.setText(model.getAddress());
            orderIdTv.setText(model.getOrderid());
            srTv.setText(model.getSendFee());
            remarkTv.setText(model.getNote());
        }
    }

    @Override
    public void changeResult(boolean result) {
        if (result) {
            listener.getOrderDetail(orderid);
        }
    }
}
