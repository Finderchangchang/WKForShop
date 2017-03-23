package wk.shop.listener;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wk.shop.method.HttpUtil;
import wk.shop.method.Utils;
import wk.shop.model.Config;
import wk.shop.view.ICitys;
import wk.shop.view.ILoginView;
import wk.shop.view.IReg;
import wk.shop.view.ISHView;

/**
 * 登录逻辑处理
 * Created by Administrator on 2016/9/19.
 */
interface UserMView {
    //登录方法
    void doLogin(String name, String pwd);

    //请求验证码(type:0注册。1，找回)
    void getCode(String type, String tel);

    //注册用户信息(type:0注册。1，找回)
    void regUser(String type, String tel, String pwd, String cityid);

    //获得城市列表
    void getCitys();

    //获得审核
    void getSHDetail();
}

public class UserListener implements UserMView {
    private ILoginView view;
    private IReg reg;
    private ICitys citys;
    private ISHView ishView;

    public UserListener(ILoginView view) {
        this.view = view;
    }

    public UserListener(IReg reg) {
        this.reg = reg;
    }

    public UserListener(ICitys citys) {
        this.citys = citys;
    }

    public UserListener(ISHView ishView) {
        this.ishView = ishView;
    }

    @Override
    public void doLogin(String name, String pwd) {
        Map<String, String> map = new HashMap<>();
        map.put("username", name);
        map.put("password", pwd);
//        map.put("token", "");
//        map.put("type", "1");
//        map.put("clientid", Utils.getCache(Config.cid));
        HttpUtil.load()
                .userInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    switch (model.getState()) {
                        case "0"://登录失败
                            view.loginResult(model.getMsg());
                            break;
                        case "1"://登录成功
                            view.loginResult("");
                            Map<String, String> map_cache = new HashMap<String, String>();
                            map_cache.put(Config.user_id, model.getUserid());
                            map_cache.put(Config.gid, model.getGid());
                            map_cache.put(Config.city_id, model.getCityid());
                            Utils.putCache(map_cache);
                            break;
                    }
                }, throwable -> {
                    view.loginResult("登录失败~~");
                });
    }

    @Override
    public void getCode(String type, String tel) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", tel);
        map.put("type", type);
        HttpUtil.load().getCode(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (model != null) {
                        switch (model.getSuccess()) {
                            case "0"://发送失败
                                reg.getCodeResult(false, model.getErrorMsg());
                                break;
                            case "1"://发送验证码成功
                                if (model.getData() != null) {
                                    reg.getCodeResult(true, model.getData().getCode());
                                } else {
                                    reg.getCodeResult(false, "验证码发送失败");
                                }
                                break;
                        }
                    } else {
                        reg.getCodeResult(false, "验证码发送失败");
                    }
                }, throwable -> {
                    reg.getCodeResult(false, "验证码发送失败");
                });
    }

    @Override
    public void regUser(String type, String tel, String pwd, String cityid) {
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        map.put("password", pwd);
        if (("0").equals(type)) {
            map.put("cityid", cityid);
        }
        map.put("type", type);
        HttpUtil.load().regUser(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (model != null) {
                        switch (model.getSuccess()) {
                            case "0"://发送失败
                                reg.getRegUserResult(false, model.getErrorMsg());
                                break;
                            case "1"://注册成功
                                reg.getRegUserResult(true, "注册成功");
                                Utils.putCache(Config.user_id, model.getData().getDataID());
                                Utils.putCache(Config.city_id, cityid);
                                break;
                        }
                    } else {
                        reg.getRegUserResult(false, "注册成功");
                    }
                }, throwable -> {
                    reg.getRegUserResult(false, "请检查网络连接");
                });
    }

    @Override
    public void getCitys() {

    }

    @Override
    public void getSHDetail() {
        HttpUtil.load()
                .getSHDetail(Utils.getCache(Config.user_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> ishView.getUser(model)
                        , error -> ishView.getUser(null));
    }
}
