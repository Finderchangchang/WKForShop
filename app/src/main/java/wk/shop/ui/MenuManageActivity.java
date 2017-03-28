package wk.shop.ui;

import android.widget.ListView;
import net.tsz.afinal.view.TitleBar;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wk.shop.R;
import wk.shop.base.BaseActivity;
import wk.shop.method.CommonAdapter;
import wk.shop.method.CommonViewHolder;
import wk.shop.method.HttpUtil;
import wk.shop.method.Utils;
import wk.shop.model.Config;
import wk.shop.model.FoodModel;

/**
 * 菜单管理
 * Created by Finder丶畅畅 on 2017/3/26 10:36
 * QQ群481606175
 */

public class MenuManageActivity extends BaseActivity {

    List<FoodModel> foodtypes;
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.main_lv)
    ListView mainLv;

    @Override
    public void initViews() {
        setContentView(R.layout.ac_menu_manage);
        ButterKnife.bind(this);
        titleBar.setLeftClick(() -> finish());
        commonAdapter = new CommonAdapter<FoodModel>(this, foodtypes, R.layout.item_menu) {
            @Override
            public void convert(CommonViewHolder holder, FoodModel foodTypeModel, int position) {
                holder.setEditText(R.id.name_tv, foodTypeModel.getName());
                holder.setImg(R.id.cai_iv,foodTypeModel.getIcon());
            }
        };
        mainLv.setAdapter(commonAdapter);
    }

    CommonAdapter<FoodModel> commonAdapter;

    void loadData() {
        HttpUtil.load()
                .getFoodListByShopId(Utils.getCache(Config.user_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (model != null) {
                        foodtypes = model.getFoodlist();
                        foodtypes.add(0, new FoodModel());
                        commonAdapter.refresh(foodtypes);
                    }
                }, error -> {

                });
    }

    @Override
    public void initEvents() {
        loadData();
    }
}
