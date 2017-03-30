package wk.shop.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import wk.shop.R;
import wk.shop.listener.MainFragListener;
import wk.shop.method.CommonAdapter;
import wk.shop.method.CommonViewHolder;
import wk.shop.method.Utils;
import wk.shop.model.ListModel;
import wk.shop.model.OrderModel;
import wk.shop.view.IMainFragView;

/**
 * Created by Administrator on 2016/10/14.
 */

public class OrderFragment extends Fragment implements IMainFragView {
    ListView listView;
    CommonAdapter<OrderModel> mAdapter;
    View view;
    MainFragListener listener;
    int tab_index = 0;
    private List<OrderModel> mOrders;
    LinearLayout no_data_ll;
    SwipeRefreshLayout item_refresh_sw;
    int pageNum = 1;
    int totalPage = 1;
    boolean isLoad = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = new MainFragListener(this);
        mOrders = new ArrayList<>();
        mAdapter = new CommonAdapter<OrderModel>(MyOrderActivity.mInstance,
                mOrders, R.layout.item_order) {
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
                holder.setVisible(R.id.get_order_btn,false);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) view.findViewById(R.id.list_frag);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(MyOrderActivity.mInstance, OrderDetailActivitys.class);
            if (mOrders.size() > 0) {
                intent.putExtra("orderid", mOrders.get(position).getOrderid());
                intent.putExtra("foodno", mOrders.get(position).getFoodNo());
                intent.putExtra("btn_state", mOrders.get(position).getSendstate());
            }
            startActivityForResult(intent, 44);
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 当不滚动时
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        if (!isLoading && totalPage > pageNum) {
                            isLoading = true;
                            Toast.makeText(MyOrderActivity.mInstance, pageNum + "/" + totalPage, Toast.LENGTH_SHORT).show();
                            pageNum = pageNum + 1;
                            if (tab_index == 2) {
                                listener.loadOrder(pageNum, "2,5");
                            } else {
                                listener.loadOrder(pageNum, tab_index + "");
                            }
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        item_refresh_sw = (SwipeRefreshLayout) view.findViewById(R.id.item_refresh_sw);
        no_data_ll = (LinearLayout) view.findViewById(R.id.no_data_ll);
        item_refresh_sw.setOnRefreshListener(() -> {
            pageNum = 1;
            refreshList(tab_index);
        });
        return view;
    }

    boolean isLoading = false;

    /**
     * 刷新当前列表
     *
     * @param position 当前选中的item位置
     */
    public void refreshList(int position) {
        isLoad = true;
        if (item_refresh_sw != null) {
            item_refresh_sw.setRefreshing(true);
        }
        tab_index = position;
        if (listener == null) {
            listener = new MainFragListener(this);
        }
        mOrders = new ArrayList<>();
        if (position == 2) {
            listener.loadOrder(1, "2,5");
        } else {
            listener.loadOrder(1, position + "");
        }
    }


    @Override
    public void refreshOrder(List<OrderModel> list) {
        item_refresh_sw.setRefreshing(false);
        if (list != null) {
            mOrders = list;
        } else {
            mOrders = new ArrayList<>();
        }
        if (list != null) {
            mAdapter.refresh(list);
            if (list.size() > 0) {
                no_data_ll.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            } else {
                listView.setVisibility(View.GONE);
                no_data_ll.setVisibility(View.VISIBLE);
            }
        } else {
            mAdapter.refresh(new ArrayList<>());
            listView.setVisibility(View.GONE);
            no_data_ll.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void refresh(ListModel list) {
        isLoading = false;
        item_refresh_sw.setRefreshing(false);
        pageNum = Integer.parseInt(list.getPage());
        totalPage = Integer.parseInt(list.getTotal());
        if (pageNum > 1) {
            List<OrderModel> order = list.getOrderlist();
            for (OrderModel model : order) {
                mOrders.add(model);
            }
        } else {
            mOrders = list.getOrderlist();
        }
        if (list != null) {
            mAdapter.refresh(mOrders);
            if (mOrders.size() > 0) {
                no_data_ll.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            } else {
                listView.setVisibility(View.GONE);
                no_data_ll.setVisibility(View.VISIBLE);
            }
        } else {
            mAdapter.refresh(new ArrayList<>());
            listView.setVisibility(View.GONE);
            no_data_ll.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void loadMoreOrder(List<OrderModel> list) {

    }

    @Override
    public void changeStateResult(boolean result) {
        if (result) {
//            refreshList(tab_index);
            MyOrderActivity.mInstance.ToastShort("更新成功~~");
        } else {
            MyOrderActivity.mInstance.ToastShort("更新失败~~");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 99:
                refreshList(tab_index);
                break;
            case 44:
                refreshList(tab_index);
                break;
        }
    }
}
