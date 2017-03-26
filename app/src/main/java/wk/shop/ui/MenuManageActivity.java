package wk.shop.ui;

import android.text.TextUtils;
import android.widget.ListView;

import net.tsz.afinal.view.TitleBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import wk.shop.model.FoodTypeModel;

/**
 * 菜单管理
 * Created by Finder丶畅畅 on 2017/3/26 10:36
 * QQ群481606175
 */

public class MenuManageActivity extends BaseActivity {

    List<FoodTypeModel> foodtypes;
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.main_lv)
    ListView mainLv;

    @Override
    public void initViews() {
        setContentView(R.layout.ac_menu_manage);
        ButterKnife.bind(this);
        titleBar.setLeftClick(() -> finish());
        commonAdapter = new CommonAdapter<FoodTypeModel>(this, foodtypes, R.layout.item_menu_type) {
            @Override
            public void convert(CommonViewHolder holder, FoodTypeModel foodTypeModel, int position) {
                if (TextUtils.isEmpty(foodTypeModel.getSortID())) {
                    holder.setVisible(R.id.delete_btn, false);
                }
                holder.setEditText(R.id.name_tv, foodTypeModel.getSortName());
                holder.setEditText(R.id.sort_id_tv, foodTypeModel.getJOrder());
                holder.setOnClickListener(R.id.save_btn, view -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("sortname", holder.getEditText(R.id.name_tv));
                    map.put("jorder", holder.getEditText(R.id.sort_id_tv));
                    if (!TextUtils.isEmpty(foodTypeModel.getSortID())) {
                        map.put("foodid", foodTypeModel.getSortID());
                    }
                    map.put("shopid", Utils.getCache(Config.user_id));
                    HttpUtil.load()
                            .addFoodSort(map)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(model -> {
                                if (model != null) {
                                    if (("1").equals(model.getState())) {
                                        loadData();
                                    }
                                    ToastShort(model.getMsg());
                                } else {
                                    ToastShort("请检查网络连接");
                                }
                            }, error -> {
                                ToastShort("请检查网络连接");
                            });
                });
                holder.setOnClickListener(R.id.delete_btn, view -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", foodTypeModel.getSortID());
                    map.put("shopid", Utils.getCache(Config.user_id));
                    HttpUtil.load()
                            .deleteFoodSort(map)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(model -> {
                                if (model != null) {
                                    if (("1").equals(model.getState())) {
                                        loadData();
                                    }
                                    ToastShort(model.getMsg());
                                } else {
                                    ToastShort("请检查网络连接");
                                }
                            }, error -> {
                                ToastShort("请检查网络连接");
                            });
                });
            }
        };
        mainLv.setAdapter(commonAdapter);
    }

    CommonAdapter<FoodTypeModel> commonAdapter;

    void loadData() {
        HttpUtil.load()
                .getFoodListByShopId(Utils.getCache(Config.user_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (model != null) {
                        foodtypes = model.getFoodtypelist();
                        foodtypes.add(0, new FoodTypeModel());
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
