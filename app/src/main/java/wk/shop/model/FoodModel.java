package wk.shop.model;

import java.util.List;

/**
 * Created by Finder丶畅畅 on 2017/3/26 19:40
 * QQ群481606175
 */

public class FoodModel {
    /**
     * FoodID : 3
     * Name : 取快递
     * MaxPerDay : QKD
     * isDelete : y
     * publicgood : 0
     * intro : 快递
     * note :
     * FullPrice : 0
     * icon : http://www.dakedaojia.com//upload/201703/201703141052389762.gif
     * sale : 0
     * istuan : 0
     * Weekday : 0
     * SortNum : 0
     * Remains : 帮忙取快递
     * FoodType : 3
     * foodstylelist : [{"DataId":"0","nprice":"0","Price":"2","Foodcurrentprice":"0"}]
     */

    private String FoodID;
    private String Name;
    private String MaxPerDay;
    private String isDelete;
    private String publicgood;
    private String intro;
    private String note;
    private String FullPrice;
    private String icon;
    private String sale;
    private String istuan;
    private String Weekday;
    private String SortNum;
    private String Remains;
    private String FoodType;
    private List<FoodstylelistBean> foodstylelist;

    public String getFoodID() {
        return FoodID;
    }

    public void setFoodID(String FoodID) {
        this.FoodID = FoodID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getMaxPerDay() {
        return MaxPerDay;
    }

    public void setMaxPerDay(String MaxPerDay) {
        this.MaxPerDay = MaxPerDay;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getPublicgood() {
        return publicgood;
    }

    public void setPublicgood(String publicgood) {
        this.publicgood = publicgood;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFullPrice() {
        return FullPrice;
    }

    public void setFullPrice(String FullPrice) {
        this.FullPrice = FullPrice;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getIstuan() {
        return istuan;
    }

    public void setIstuan(String istuan) {
        this.istuan = istuan;
    }

    public String getWeekday() {
        return Weekday;
    }

    public void setWeekday(String Weekday) {
        this.Weekday = Weekday;
    }

    public String getSortNum() {
        return SortNum;
    }

    public void setSortNum(String SortNum) {
        this.SortNum = SortNum;
    }

    public String getRemains() {
        return Remains;
    }

    public void setRemains(String Remains) {
        this.Remains = Remains;
    }

    public String getFoodType() {
        return FoodType;
    }

    public void setFoodType(String FoodType) {
        this.FoodType = FoodType;
    }

    public List<FoodstylelistBean> getFoodstylelist() {
        return foodstylelist;
    }

    public void setFoodstylelist(List<FoodstylelistBean> foodstylelist) {
        this.foodstylelist = foodstylelist;
    }

    public static class FoodstylelistBean {
        /**
         * DataId : 0
         * nprice : 0
         * Price : 2
         * Foodcurrentprice : 0
         */

        private String DataId;
        private String nprice;
        private String Price;
        private String Foodcurrentprice;

        public String getDataId() {
            return DataId;
        }

        public void setDataId(String DataId) {
            this.DataId = DataId;
        }

        public String getNprice() {
            return nprice;
        }

        public void setNprice(String nprice) {
            this.nprice = nprice;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public String getFoodcurrentprice() {
            return Foodcurrentprice;
        }

        public void setFoodcurrentprice(String Foodcurrentprice) {
            this.Foodcurrentprice = Foodcurrentprice;
        }
    }
}
