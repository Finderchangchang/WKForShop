package wk.shop.ui;

import android.Manifest;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import net.tsz.afinal.view.TitleBar;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.trinea.android.common.util.PreferencesUtils;
import okhttp3.Call;
import okhttp3.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wk.shop.R;
import wk.shop.base.BaseActivity;
import wk.shop.base.BaseApplication;
import wk.shop.listener.MainFragListener;
import wk.shop.listener.MainListener;
import wk.shop.method.CommonAdapter;
import wk.shop.method.CommonViewHolder;
import wk.shop.method.GlideCircleTransform;
import wk.shop.method.HttpUtil;
import wk.shop.method.Utils;
import wk.shop.model.Config;
import wk.shop.model.ListModel;
import wk.shop.model.OrderModel;
import wk.shop.model.ShopInfoModel;
import wk.shop.model.ShopOrderModel;
import wk.shop.view.IMainFragView;
import wk.shop.view.IMainView;

/**
 * Created by Administrator on 2016/12/12.
 */

public class MainActivity extends BaseActivity implements IMainView, IMainFragView {
    public static MainActivity mInstance;
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.order_lv)
    ListView orderLv;
    @Bind(R.id.my_order_btn)
    Button myOrderBtn;
    @Bind(R.id.refresh_ll)
    LinearLayout refresh_ll;
    @Bind(R.id.main_srl)
    SwipeRefreshLayout main_srl;
    CommonAdapter<OrderModel> mAdapter;
    List<OrderModel> mList;
    MainFragListener listener;
    @Bind(R.id.no_data_ll)
    LinearLayout no_data_ll;
    MainListener mainListener;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = null;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @Bind(R.id.nav_view)
    NavigationView nav_view;
    View top_view;
    LinearLayout user_center_ll;
    BroadcastReceiver mItemViewListClickReceiver;
    IntentFilter intentFilter;
    private NotificationManager manger;
    public static final String DOWNLOAD_ID = "download_id";
    private long lastDownloadId = 0;
    public static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");
    private MainActivity.DownloadChangeObserver downloadObserver;
    List<File> files;

    @Override
    public void initViews() {
        setContentView(R.layout.ac_total_main);
        ButterKnife.bind(this);
        mInstance = this;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);//自定义的code
        }
        top_view = nav_view.getHeaderView(0);
        LinearLayout btn = (LinearLayout) top_view.findViewById(R.id.auc_ll7);
        btn.setOnClickListener(v -> {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("确定要退出当前账号？");
            builder.setNegativeButton("确定", (dialog, which) -> {
                Utils.putCache(Config.user_id, "");
                if (broadcastManager != null) {
                    broadcastManager.unregisterReceiver(mItemViewListClickReceiver);
                }
                finish();
            });
            builder.setPositiveButton("取消", null);
            builder.show();
        });
        LinearLayout auc_ll3 = (LinearLayout) top_view.findViewById(R.id.auc_ll3);//菜品管理
        auc_ll3.setOnClickListener(v -> Utils.IntentPost(MenuManageActivity.class));
        LinearLayout auc_ll2 = (LinearLayout) top_view.findViewById(R.id.auc_ll2);//菜单分类
        auc_ll2.setOnClickListener(v -> Utils.IntentPost(MenuTypeActivity.class));
        LinearLayout auc_ll6 = (LinearLayout) top_view.findViewById(R.id.auc_ll6);//关于
        auc_ll6.setOnClickListener(v -> Utils.IntentPost(WebActivity.class, intent -> intent.putExtra("web", "关于")));
        LinearLayout auc_ll4 = (LinearLayout) top_view.findViewById(R.id.auc_ll4);
        user_center_ll = (LinearLayout) top_view.findViewById(R.id.user_center_ll);
        user_center_ll.setOnClickListener(view -> Utils.IntentPost(ShopInfoActivity.class, intent -> intent.putExtra("model", shopInfoModel)));
        auc_ll4.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4001663779"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(30000);
        BaseApplication.mLocationClient.setLocationOption(mLocationOption);
        mLocationListener = model -> {
            Map<String, String> map_cache = new HashMap<>();
            map_cache.put("address", model.getAddress());
            map_cache.put("lat", model.getLatitude() + "");
            map_cache.put("lon", model.getLongitude() + "");
            Map<String, String> mpp = new HashMap<String, String>();
            mpp.put("did", Utils.getCache(Config.user_id));
            mpp.put("lat", model.getLatitude() + "");
            mpp.put("lng", model.getLongitude() + "");
            mpp.put("glat", model.getLatitude() + "");
            mpp.put("glng", model.getLongitude() + "");
            HttpUtil.load().pushLatLng(mpp)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mm -> {
                        if (("-1").equals(mm.getState())) {
                            ToastShort(mm.getMsg());
                        }
                    }, error -> {

                    });
            map_cache.put("cityName", model.getCity());
            Utils.putCache(map_cache);
        };
        //设置定位回调监听
        BaseApplication.mLocationClient.setLocationListener(mLocationListener);
        BaseApplication.mLocationClient.startLocation();
        listener = new MainFragListener(this);
        mainListener = new MainListener(this);
        mainListener.getUserCenter();
        mainListener.checkVersion();//检查版本
        builder = new AlertDialog.Builder(this);
        myOrderBtn.setOnClickListener(v -> {
            Utils.IntentPost(MyOrderActivity.class);
        });
        titleBar.setLeftClick(() -> leftManager());
        titleBar.setRightSWClick(() -> {
            builder.setTitle("提示");
            builder.setMessage("确定要" + (!right_click ? "接单" : "关闭") + "吗？");
            builder.setNegativeButton("确定", (dialog, which) -> {
                mainListener.changeQsState(right_click);
            });
            builder.setPositiveButton("取消", null);
            builder.show();
        });
        mAdapter = new CommonAdapter<OrderModel>(this, mList, R.layout.item_order) {
            @Override
            public void convert(CommonViewHolder holder, OrderModel model, int position) {
                holder.setText(R.id.fb_time_tv, model.getAddtime());
                holder.setText(R.id.sr_tv, model.getSendFee() + "元");
                holder.setText(R.id.order_id_tv, model.getOrderid());
                String state = "";
                switch (model.getSendstate()) {
                    case "0":
                        if (("2").equals(model.getState())) {
                            switch (model.getIsShopSet()) {
                                case "0":
                                    state = "未接单";
                                    break;
                                case "1":
                                    state = "已接单";
                                    break;
                                default:
                                    state = "订单已取消";
                                    break;
                            }
                        } else {
                            switch (model.getState()) {
                                case "7":
                                    state = "正在匹配骑手";
                                    break;
                                case "4":
                                    state = "订单已取消";
                                    break;
                                case "3":
                                    state = "完成订单";
                                    break;
                                default:
                                    state = "位置情况的订单";
                                    break;
                            }
                        }
                        break;
                    case "1":
                        state = "取货中";
                        break;
                    case "2":
                        state = "配送中";
                        break;
                    case "3":
                        state = "已送达";
                        break;
                }
                holder.setText(R.id.state_tv, state);
                holder.setOnClickListener(R.id.get_order_btn, v -> {
                    builder.setTitle("提示");
                    builder.setMessage("确定要接此单？");
                    builder.setNegativeButton("确定", (dialog, which) -> {
                        mainListener.qiangDan("1", model.getOrderid());
                    });
                    builder.setPositiveButton("取消", null);
                    builder.show();
                });
            }
        };
        orderLv.setAdapter(mAdapter);
        orderLv.setOnItemClickListener((parent, view, position, id) -> {
            Utils.IntentPost(OrderDetailActivitys.class, listener -> {
                listener.putExtra("orderid", mList.get(position).getOrderid());
            });
        });
//        refresh_ll.setOnClickListener(v -> {
//            if (right_click) {
//                listener.loadOrder(1, "0");
//            }
//        });
        main_srl.setOnRefreshListener(() -> {
                    if (right_click) {
                        listener.loadOrder(1, "0");
                    } else {
                        main_srl.setRefreshing(false);
                    }
                }
        );
        listener.loadOrder(1, "0");
        broadcastManager = LocalBroadcastManager.getInstance(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra("data");
                if ("refresh".equals(msg)) {
                    listener.loadOrder(1, "0");
                    simpleNotify();//提示音
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);

        refresh_ll.setOnClickListener(v -> {
            File file = new File("/storage/emulated/0/Pictures/Screenshots/Screenshot_2017-01-22-10-56-13.png");
            OkGo.post("http://s-352911.gotocdn.com/APP/shop/GetFoodImg.aspx")//
                    .tag(this)//
                    .params("userid", "5")
                    .params("file1", file)    // 这里支持一个key传多个文件
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            //上传成功
                            String a = "";
                        }


                        @Override
                        public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                            //这里回调上传进度(该回调在主线程,可以直接更新ui)
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                        }
                    });
        });
    }

    String foodId = "5";

    // Drawable转换成Bitmap
    public Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    // Drawable转换成byte[]
    public byte[] Drawable2Bytes(Drawable d) {
        Bitmap bitmap = this.drawable2Bitmap(d);
        return this.Bitmap2Bytes(bitmap);
    }

    // Bitmap转换成byte[]
    byte[] bytes;

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public String communication01(String urlString) {
        Drawable drawable = getResources().getDrawable(R.mipmap.ac_weixin);
//        bitmap=drawable2Bitmap(drawable);
        bytes = Drawable2Bytes(drawable);
        HashMap<String, String> localHashMap = new HashMap<String, String>();
        // / type=1 表示上传商家图片 id 表示商家编号
        // / type=2 表示上传菜品图片 id 表示菜品编号
        // / ext=jpg 表示后缀名
        localHashMap.put("id", String.valueOf(foodId));
        localHashMap.put("type", "2");
        localHashMap.put("ext", "jpg");
        String result = "";

        String end = "\r\n";
        if (!urlString.equals("")) {
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setDoInput(true);// 允许输入
                conn.setDoOutput(true);// 允许输出
                conn.setUseCaches(false);// 不使用Cache
                conn.setConnectTimeout(6000);// 6秒钟连接超时
                conn.setReadTimeout(6000);// 6秒钟读数据超时
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Charset", "UTF-8");
                //StringBuilder localStringBuilder1 = new StringBuilder();
                Iterator localIterator = localHashMap.entrySet().iterator();

                while (true) {
                    if (!localIterator.hasNext()) {
                        break;
                    }

                    Map.Entry localEntry = (Map.Entry) localIterator.next();
                    conn.setRequestProperty((String) localEntry.getKey(), URLEncoder.encode((String) localEntry.getValue(),
                            "UTF-8"));
                }
                /*
                 * conn.setRequestProperty("id", "1");
				 * conn.setRequestProperty("type", "1");
				 * conn.setRequestProperty("ext", "jpg");
				 */
                // / type=1 表示上传商家图片 id 表示商家编号
                // / type=2 表示上传菜品图片 id 表示菜品编号
                // / ext=jpg 表示后缀名
                DataOutputStream dos = new DataOutputStream(
                        conn.getOutputStream());
                dos.write(bytes, 0, bytes.length);
                dos.writeBytes(end);
                dos.flush();
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "utf-8");
                BufferedReader br = new BufferedReader(isr);
                result = br.readLine();
                String s = "";
            } catch (Exception e) {
                result = "{\"ret\":\"898\"}";
            }
        }
        return result;
    }

    ShopInfoModel shopInfoModel;

    private void simpleNotify() {
        if (MainActivity.mInstance != null) {
            manger = (NotificationManager) MainActivity.mInstance.getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.mInstance);
            builder.setTicker("简单Notification");
            builder.setContentTitle("提示");
            builder.setContentText("您有新订单，请注意查收~~~");
            builder.setAutoCancel(true);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            Intent intent = new Intent(MainActivity.mInstance, MainActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(MainActivity.mInstance, 1, intent, 0);
            //点击跳转的intent
            builder.setContentIntent(pIntent);
            builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setSound(Uri.parse("android.resource://" + BaseApplication.getContext().getPackageName() + "/" + R.raw.order));
            Notification notification = builder.build();
            manger.notify(1, notification);
        }
    }

    LocalBroadcastManager broadcastManager;

    private void leftManager() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            drawer_layout.openDrawer(GravityCompat.START);
        }
    }

    AlertDialog.Builder builder;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                break;
        }
    }

    @Override
    public void initEvents() {

    }

    @Override
    public void refreshOrder(List<OrderModel> list) {
        main_srl.setRefreshing(false);
        if (list != null) {
            mAdapter.refresh(list);
            if (list.size() > 0) {
                no_data_ll.setVisibility(View.GONE);
                orderLv.setVisibility(View.VISIBLE);
                mList = list;
            } else {
                orderLv.setVisibility(View.GONE);
                no_data_ll.setVisibility(View.VISIBLE);
            }
        } else {
            mAdapter.refresh(new ArrayList<>());
            orderLv.setVisibility(View.GONE);
            no_data_ll.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void refresh(ListModel list) {

    }

    boolean right_click = true;

    @Override
    public void loadMoreOrder(List<OrderModel> list) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (11 == resultCode) {
            mainListener.getUserCenter();
        }
    }

    @Override
    public void checkVersion(String url, String content) {
        if (!("").equals(content)) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
            builder.setTitle("提示");
            builder.setMessage(content);
            builder.setNegativeButton("确定", (dialog, which) -> {
                DownloadManager dowanloadmanager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                //2.创建下载请求对象，并且把下载的地址放进去
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                //3.给下载的文件指定路径
                request.setDestinationInExternalFilesDir(MainActivity.this, Environment.DIRECTORY_DOWNLOADS, "weixin.apk");
                //4.设置显示在文件下载Notification（通知栏）中显示的文字。6.0的手机Description不显示
                request.setTitle("更新");
                request.setDescription(content);
                //5更改服务器返回的minetype为android包类型
                request.setMimeType("application/vnd.android.package-archive");
                //6.设置在什么连接状态下执行下载操作
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                //7. 设置为可被媒体扫描器找到
                request.allowScanningByMediaScanner();
                //8. 设置为可见和可管理
                request.setVisibleInDownloadsUi(true);
                lastDownloadId = dowanloadmanager.enqueue(request);
                //9.保存id到缓存
                PreferencesUtils.putLong(MainActivity.this, DOWNLOAD_ID, lastDownloadId);
                //10.采用内容观察者模式实现进度
                downloadObserver = new DownloadChangeObserver(null);
                getContentResolver().registerContentObserver(CONTENT_URI, true, downloadObserver);
            });
            builder.setPositiveButton("取消", null);
            builder.show();
        }
    }

    @Override
    public void changeResult(double lat, double lng) {

    }

    @Override
    public void changeStateResult(boolean result) {
        if (result) {
            listener.loadOrder(1, "0");
        }
    }

    @Override
    public void changeQsState(String result) {
        if (("成功").equals(result)) {
            right_click = !right_click;
            no_data_ll.setVisibility(right_click ? View.GONE : View.VISIBLE);
            orderLv.setVisibility(right_click ? View.VISIBLE : View.GONE);
            titleBar.setRightButtonClick(right_click);
            if (right_click) {
                broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
            } else {
                broadcastManager.unregisterReceiver(mItemViewListClickReceiver);
            }
        } else {
            ToastShort(result);
        }
    }


    //用于显示下载进度
    class DownloadChangeObserver extends ContentObserver {

        public DownloadChangeObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(lastDownloadId);
            DownloadManager dManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            final Cursor cursor = dManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                final int totalColumn = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                final int currentColumn = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                int totalSize = cursor.getInt(totalColumn);
                int currentSize = cursor.getInt(currentColumn);
                float percent = (float) currentSize / (float) totalSize;
                int progress = Math.round(percent * 100);
            }
        }
    }

    @Override
    public void userDetail(ShopInfoModel model) {
        if (model != null) {
            TextView user_name_tv = (TextView) top_view.findViewById(R.id.user_name_tv);
            TextView user_tel_tv = (TextView) top_view.findViewById(R.id.user_tel_tv);
            user_name_tv.setText(model.getTogoname());
            user_tel_tv.setText(model.getAddress());
            ImageView iv = (ImageView) top_view.findViewById(R.id.user_iv);
            Glide.with(this)
                    .load(model.getPicture())
                    .error(R.mipmap.tag_ren)
                    .transform(new GlideCircleTransform(this))
                    .into(iv);
            shopInfoModel = model;
        }
    }


    @Override
    public void orderList(ShopOrderModel orders) {
        if (orders != null) {
            TextView sr_tv = (TextView) top_view.findViewById(R.id.sr_tv);
            TextView ds_tv = (TextView) top_view.findViewById(R.id.ds_tv);
            sr_tv.setText(orders.getOrderCount());
            ds_tv.setText(orders.getOrderTotal());
        }
    }
}
