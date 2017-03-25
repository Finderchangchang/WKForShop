package wk.shop.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.tsz.afinal.view.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import wk.shop.R;
import wk.shop.base.BaseActivity;
import wk.shop.method.GlideCircleTransform;
import wk.shop.model.ShopInfoModel;

/**
 * Created by Finder丶畅畅 on 2017/3/25 22:24
 * QQ群481606175
 */

public class ShopInfoActivity extends BaseActivity {
    ShopInfoModel model;
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.shop_iv)
    ImageView shopIv;
    @Bind(R.id.shop_name_tv)
    TextView shopNameTv;
    @Bind(R.id.shop_address_tv)
    TextView shopAddressTv;
    @Bind(R.id.comm_person_tv)
    TextView commPersonTv;
    @Bind(R.id.open_time1_tv)
    TextView openTime1Tv;
    @Bind(R.id.open_time2_tv)
    TextView openTime2Tv;
    @Bind(R.id.send_time_tv)
    TextView sendTimeTv;
    @Bind(R.id.email_tv)
    TextView emailTv;
    @Bind(R.id.tel_tv)
    TextView telTv;

    @Override
    public void initViews() {
        setContentView(R.layout.ac_shop_info);
        ButterKnife.bind(this);
        model = (ShopInfoModel) getIntent().getSerializableExtra("model");
        titleBar.setLeftClick(() -> finish());
    }

    @Override
    public void initEvents() {
        if (model != null) {
            telTv.setText("联系电话：" + model.getComm());
            emailTv.setText("邮箱：" + model.getEmail());
            sendTimeTv.setText("配送时间：" + model.getSenttime());
            openTime1Tv.setText("配送时间1：" + model.getOpentimes1() + "-" + model.getClosetimes1());
            openTime2Tv.setText("配送时间2：" + model.getOpentimes2() + "-" + model.getClosetimes2());
            commPersonTv.setText("负责人：" + model.getCommPerson());
            shopAddressTv.setText("店铺地址：" + model.getAddress());
            shopNameTv.setText("店铺名称：" + model.getTogoname());
            Glide.with(this)
                    .load(model.getPicture())
                    .error(R.mipmap.tag_ren)
                    .transform(new GlideCircleTransform(this))
                    .into(shopIv);
        }
    }
}
