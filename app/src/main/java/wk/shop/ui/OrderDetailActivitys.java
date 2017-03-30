package wk.shop.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.tsz.afinal.view.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import wk.shop.R;
import wk.shop.base.BaseActivity;
import wk.shop.listener.MainFragListener;
import wk.shop.model.OrderModel;
import wk.shop.view.IOrderDetail;

/**
 * Created by Administrator on 2016/12/13.
 */

public class OrderDetailActivitys extends BaseActivity implements IOrderDetail {
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.left_iv)
    ImageView leftIv;
    @Bind(R.id.qh_address_tv)
    TextView qhAddressTv;
    @Bind(R.id.tel1_iv)
    ImageView tel1Iv;
    @Bind(R.id.left1_iv)
    ImageView left1Iv;
    @Bind(R.id.tel2_iv)
    ImageView tel2Iv;
    @Bind(R.id.order_id_tv)
    TextView orderIdTv;
    @Bind(R.id.lc_tv)
    TextView lcTv;
    @Bind(R.id.sr_tv)
    TextView srTv;
    @Bind(R.id.remark_tv)
    TextView remarkTv;
    @Bind(R.id.qh_tv)
    TextView qhTv;
    @Bind(R.id.sh_address_tv)
    TextView shAddressTv;
    String orderid;
    MainFragListener listener;
    int width;
    String btn_state;
    @Bind(R.id.bottom_btn)
    Button bottom_btn;

    @Override
    public void initViews() {
        setContentView(R.layout.ac_order_detail);
        ButterKnife.bind(this);
        titleBar.setLeftClick(() -> finish());
        listener = new MainFragListener(this);
        orderid = getIntent().getStringExtra("orderid");
        listener.getOrderDetail(orderid);
        width = getWindowManager().getDefaultDisplay().getWidth();
        if (getIntent().getBooleanExtra("history", false)) {
            bottom_btn.setVisibility(View.GONE);
        }
    }

    boolean isRemark = false;//true,已经提示

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
            bottom_btn.setText(btn_state);
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
            qhTv.setText(model.getShopname());
            qhAddressTv.setText(model.getShopaddress());
            shAddressTv.setText(model.getAddress());
            tel1Iv.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + model.getShoptel()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            });
            tel2Iv.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + model.getOrderComm()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            });
            orderIdTv.setText(model.getOrderid());
            lcTv.setText(model.getSJDaoYH());
            srTv.setText(model.getSendFee());
            remarkTv.setText(model.getNote());
//            shTv.setText(model.getSentTime());
        }
    }

    @Override
    public void changeResult(boolean result) {
        if (result) {
            listener.getOrderDetail(orderid);
        }
    }
}
