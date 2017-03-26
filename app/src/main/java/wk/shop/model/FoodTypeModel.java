package wk.shop.model;

import java.util.List;

/**
 * Created by Finder丶畅畅 on 2017/3/26 19:48
 * QQ群481606175
 */

public class FoodTypeModel {
    public FoodTypeModel() {
        SortID = "";
        SortName = "";
        JOrder = "";
        icon = "";
    }

    /**
     * SortID : 3
     * SortName : 快递
     * JOrder : 1
     * icon :
     */

    private String SortID;
    private String SortName;
    private String JOrder;
    private String icon;

    public String getSortID() {
        return SortID;
    }

    public void setSortID(String SortID) {
        this.SortID = SortID;
    }

    public String getSortName() {
        return SortName;
    }

    public void setSortName(String SortName) {
        this.SortName = SortName;
    }

    public String getJOrder() {
        return JOrder;
    }

    public void setJOrder(String JOrder) {
        this.JOrder = JOrder;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
