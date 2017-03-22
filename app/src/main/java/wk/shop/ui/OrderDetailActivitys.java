package wk.shop.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.navi.AMapNavi;

import net.tsz.afinal.view.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import wk.shop.R;
import wk.shop.base.BaseActivity;
import wk.shop.listener.MainFragListener;
import wk.shop.method.Utils;
import wk.shop.model.OrderModel;
import wk.shop.view.IOrderDetail;

/**
 * Created by Administrator on 2016/12/13.
 */

public class OrderDetailActivitys extends BaseActivity implements AMap.OnMapLoadedListener, IOrderDetail {
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
    @Bind(R.id.map)
    MapView mapView;
    @Bind(R.id.xd_tv)
    TextView xdTv;
    @Bind(R.id.jd_tv)
    TextView jdTv;
    @Bind(R.id.sh_tv)
    TextView shTv;
    @Bind(R.id.wc_tv)
    TextView wcTv;
    @Bind(R.id.qh_tv)
    TextView qhTv;
    @Bind(R.id.sh_address_tv)
    TextView shAddressTv;
    @Bind(R.id.iv1)
    ImageView iv1;
    @Bind(R.id.iv2)
    ImageView iv2;
    @Bind(R.id.iv3)
    ImageView iv3;
    @Bind(R.id.bottom_iv_ll)
    LinearLayout bottomIvLl;
    private AMap aMap;
    AMapNavi mAMapNavi;
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
        mapView.onCreate(savedInstanceState);// 必须要写
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        if (aMap == null) {
            aMap = mapView.getMap();
        }
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


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    @Override
    public void onMapLoaded() {

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
                case "5":
                    btn_state = "拍照并送货";
                    bottomIvLl.setVisibility(View.VISIBLE);
                    break;
                case "2":
                    btn_state = "完成配送";
                    bottomIvLl.setVisibility(View.VISIBLE);
                    break;
                case "3":
                    btn_state = "已完成";
                    bottomIvLl.setVisibility(View.VISIBLE);
                    break;
                case "4":
                    btn_state = "已取消";
                    bottomIvLl.setVisibility(View.VISIBLE);
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
            LatLng latLng = new LatLng(Float.parseFloat(Utils.getCache("lat")), Float.parseFloat(Utils.getCache("lon")));
            MarkerOptions otMarkerOptions = new MarkerOptions();
            otMarkerOptions.position(latLng);
            otMarkerOptions.icon(BitmapDescriptorFactory.fromBitmap(
                    BitmapFactory.decodeResource(getResources(), R.mipmap.qs_dian)));
            aMap.addMarker(otMarkerOptions);
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
            xdTv.setText(model.getAddtime());
            jdTv.setText(model.getJieDanTime());
            shTv.setText(model.getSentTime());
            wcTv.setText(model.getReachTime());
            otMarkerOptions = new MarkerOptions();
            otMarkerOptions.icon(
                    BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(),
                                    R.mipmap.sh_map)));
            otMarkerOptions.position(new LatLng(Float.parseFloat(model.getUlat()), Float.parseFloat(model.getUlng())));
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Float.parseFloat(model.getUlat()), Float.parseFloat(model.getUlng())), 13));
            otMarkerOptions.draggable(true);
            aMap.addMarker(otMarkerOptions);
            MarkerOptions otMarkerOption = new MarkerOptions();
            otMarkerOption.icon(
                    BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(),
                                    R.mipmap.qh_map)));
            otMarkerOption.position(new LatLng(Float.parseFloat(model.getSlat()), Float.parseFloat(model.getSlng())));
            otMarkerOption.draggable(true);
            aMap.addMarker(otMarkerOption);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        listener.getOrderDetail(orderid);
    }

    @Override
    public void changeResult(boolean result) {
        if (result) {
            listener.getOrderDetail(orderid);
        }
    }
}
