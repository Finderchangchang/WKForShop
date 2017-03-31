package wk.shop.model;

/**
 * Created by Administrator on 2017/3/31.
 */

public class MenuModel {
    /// <summary>
    /// 10:00:00-12:00:00/14:00:00-23:00:00;
    /// </summary>
    public String OpenTime;
    /// <summary>
    /// 属性数量
    /// </summary>
    public int isauth;

    /// <summary>
    /// 统计是否热门菜
    /// </summary>
    public int IsRecommend;
    /// <summary>
    /// 规格数量：
    /// </summary>
    public int IsSpecial;

    /// <summary>
    /// 商品排序
    /// </summary>
    public int OrderNum;

    /// <summary>
    /// 商家名
    /// </summary>
    public String TogoName;

    /// <summary>
    /// 类型名
    /// </summary>
    public String SortName;

    /// <summary>
    /// 
    /// </summary>
    public String Unid;

    /// <summary>
    /// y 上架，n 下架，d  删除
    /// </summary>
    public String InUse;

    /// <summary>
    /// 菜品号 暂未使用 未知功能
    /// </summary>
    public String FoodNo;;

    /// <summary>
    /// 菜品隶属餐馆编号
    /// </summary>
    public int  FPMaster;
    /// <summary>
    /// 菜品名
    /// </summary>
    public String FoodName;

    /// <summary>
    /// 拼音
    /// </summary>
    public String FoodNamePy;

    /// <summary>
    /// 打包费
    /// </summary>
    public double FullPrice;
    /// <summary>
    ///  剩余数量
    /// </summary>
    public int Remains;

    /// <summary>
    /// 每日最大供应量 库存
    /// </summary>
    public int MaxPerDay;
    /// <summary>
    /// 口味
    /// </summary>
    public String Taste;

    /// <summary>
    /// 
    /// </summary>
    public String  Picture;
    /// <summary>
    /// 类型
    /// </summary>
    public int FoodType;

    public String getOpenTime() {
        return OpenTime;
    }

    public void setOpenTime(String openTime) {
        OpenTime = openTime;
    }

    public int getIsauth() {
        return isauth;
    }

    public void setIsauth(int isauth) {
        this.isauth = isauth;
    }

    public int getIsRecommend() {
        return IsRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        IsRecommend = isRecommend;
    }

    public int getIsSpecial() {
        return IsSpecial;
    }

    public void setIsSpecial(int isSpecial) {
        IsSpecial = isSpecial;
    }

    public int getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(int orderNum) {
        OrderNum = orderNum;
    }

    public String getTogoName() {
        return TogoName;
    }

    public void setTogoName(String togoName) {
        TogoName = togoName;
    }

    public String getSortName() {
        return SortName;
    }

    public void setSortName(String sortName) {
        SortName = sortName;
    }

    public String getUnid() {
        return Unid;
    }

    public void setUnid(String unid) {
        Unid = unid;
    }

    public String getInUse() {
        return InUse;
    }

    public void setInUse(String inUse) {
        InUse = inUse;
    }

    public String getFoodNo() {
        return FoodNo;
    }

    public void setFoodNo(String foodNo) {
        FoodNo = foodNo;
    }

    public int getFPMaster() {
        return FPMaster;
    }

    public void setFPMaster(int FPMaster) {
        this.FPMaster = FPMaster;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodNamePy() {
        return FoodNamePy;
    }

    public void setFoodNamePy(String foodNamePy) {
        FoodNamePy = foodNamePy;
    }

    public double getFullPrice() {
        return FullPrice;
    }

    public void setFullPrice(double fullPrice) {
        FullPrice = fullPrice;
    }

    public int getRemains() {
        return Remains;
    }

    public void setRemains(int remains) {
        Remains = remains;
    }

    public int getMaxPerDay() {
        return MaxPerDay;
    }

    public void setMaxPerDay(int maxPerDay) {
        MaxPerDay = maxPerDay;
    }

    public String getTaste() {
        return Taste;
    }

    public void setTaste(String taste) {
        Taste = taste;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public int getFoodType() {
        return FoodType;
    }

    public void setFoodType(int foodType) {
        FoodType = foodType;
    }
}
