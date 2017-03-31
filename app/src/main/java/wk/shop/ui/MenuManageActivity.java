package wk.shop.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import net.tsz.afinal.view.TitleBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;
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
import wk.shop.model.FoodTypeModel;
import wk.shop.model.MenuModel;

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
    String food_id;//餐品号
    @Bind(R.id.add_menu_btn)
    Button addMenuBtn;
    AlertDialog.Builder builder;
    String[] items;
    int click_position;
    int tag_click[];
    List<FoodTypeModel> foodTypeModels;

    @Override
    public void initViews() {
        setContentView(R.layout.ac_menu_manage);
        ButterKnife.bind(this);
        builder = new AlertDialog.Builder(this);
        titleBar.setLeftClick(() -> finish());
        HttpUtil.load()
                .getFoodTypeListByShopId(Utils.getCache(Config.user_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (model != null) {
                        if (model.getFoodtypelist() != null) {
                            items = new String[model.getFoodtypelist().size()];
                            for (int i = 0; i < model.getFoodtypelist().size(); i++) {
                                items[i] = model.getFoodtypelist().get(i).getSortName();
                            }
                            foodTypeModels = model.getFoodtypelist();
                        }
                    }
                }, error -> {

                });
        commonAdapter = new CommonAdapter<FoodModel>(this, foodtypes, R.layout.item_menu) {
            @Override
            public void convert(CommonViewHolder holder, FoodModel foodTypeModel, int position) {
                if (foodTypeModel != null) {
                    holder.setEditText(R.id.name_tv, foodTypeModel.getName());
                    holder.setText(R.id.menu_type_tv, foodTypeModel.getIntro());
                    holder.setEditText(R.id.price_tv, foodTypeModel.getFullPrice());
                    holder.setEditText(R.id.sort_id_tv, foodTypeModel.getSortNum());
                    holder.setImg(R.id.cai_iv, foodTypeModel.getIcon());
                    holder.setOnClickListener(R.id.cai_iv, v -> {
                        PhotoPicker.builder()
                                .setPhotoCount(1)
                                .setShowCamera(true)
                                .setShowGif(true)
                                .setPreviewEnabled(false)
                                .start(MenuManageActivity.this, PhotoPicker.REQUEST_CODE);
                        food_id = foodTypeModel.getFoodID();

                    });
                    holder.setOnClickListener(R.id.menu_type_tv, v -> {
                        click_position = position;
                        builder.setTitle("请选择菜品类型");
                        builder.setItems(items, (dialog, which) -> {
                            ToastShort(which + "");
                            tag_click[click_position] = which;
                            FoodModel foodModel = foodtypes.get(click_position);
                            foodModel.setIntro(items[which]);
                            //foodtypes.remove(click_position);
                            //foodtypes.add(click_position, foodModel);
                            commonAdapter.refresh(foodtypes);
                        });
                        builder.show();
                    });
                    click_position = position;
                    holder.setOnClickListener(R.id.add_btn, view -> {
                        /**
                         * {FPMaster:1,--shopid
                         * FullPrice:10,--菜品价格
                         * OrderNum:10,--排序号
                         * FoodName:666,--菜品名称
                         * FoodType:11,--菜品分类
                         * FoodNamePy:1,--菜品拼音
                         * Taste:1--口味}
                         * */
                        MenuModel menuModel = new MenuModel();
                        if (click_position != 0) {
                            menuModel.setUnid(foodTypeModel.getFoodID());
                        }
                        menuModel.setFPMaster(Integer.parseInt(Utils.getCache(Config.user_id)));
                        String price = holder.getText(R.id.price_tv);
                        if (!TextUtils.isEmpty(price)) {
                            menuModel.setFullPrice(Double.parseDouble(price));
                        } else {
                            menuModel.setFullPrice(0);
                        }
                        if(!TextUtils.isEmpty(holder.getText(R.id.sort_id_tv))) {
                            menuModel.setOrderNum(Integer.parseInt(holder.getText(R.id.sort_id_tv)));
                        }else{
                            menuModel.setOrderNum(0);
                        }
                        menuModel.setFoodName(holder.getText(R.id.name_tv));
                        menuModel.setFoodType(Integer.parseInt(foodTypeModels.get(tag_click[click_position]).getSortID()));
                        menuModel.setFoodNamePy("1");
                        menuModel.setTaste("1");
                        String order = new Gson().toJson(menuModel);
                        Log.i("tag", "~~~~~~~~~~~~~~~~~~~~~~~~");
                        Log.i("tag", order);
                        Log.i("tag", "~~~~~~~~~~~~~~~~~~~~~~~~");
                        HttpUtil.load()
                                .editFood(order)
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
                        map.put("id", foodTypeModel.getFoodID());
                        map.put("shopid", Utils.getCache(Config.user_id));
                        HttpUtil.load()
                                .deleteFood(map)
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
            }
        };
        mainLv.setAdapter(commonAdapter);
    }

    String path;//当前选择的图片路径
    CommonAdapter<FoodModel> commonAdapter;
    Bitmap bitmap;//当前选择的图片

    /**
     * 获得指定文件的byte数组
     */
    private byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                path = photos.get(0);
                Map<String, String> map = new HashMap<>();
                map.put("userid", food_id);
                map.put("icon", getBytes(path).toString());
                HttpUtil.load()
                        .putFoodImg(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(model -> {
                            if (model != null) {

                            } else {
                                ToastShort("请检查网络连接");
                            }
                        }, error -> {
                            ToastShort("请检查网络连接");
                        });
                //ivHeader.setImageBitmap(Utils.getBitmapByFile(photos.get(0)));
            }
        }
    }

    void loadData() {
        HttpUtil.load()
                .getFoodListByShopId(Utils.getCache(Config.user_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (model != null) {
                        foodtypes = model.getFoodlist();
                        FoodModel foodModel = new FoodModel();
                        foodtypes.add(0, foodModel);
                        commonAdapter.refresh(foodtypes);
                        tag_click = new int[foodtypes.size()];
                    }
                }, error -> {

                });
    }

    @Override
    public void initEvents() {
        loadData();
    }
}
