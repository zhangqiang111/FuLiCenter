package cn.ucai.fulicenter.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/9 0009.
 */

public class CollectBean implements Serializable{
    /**
     * id : 7672
     * userName : 7672
     * goodsId : 7672
     * goodsName : 趣味煮蛋模具
     * goodsEnglishName : Kotobuki
     * goodsThumb : http:121.197.1.20/images/201507/thumb_img/6372_thumb_G_1437108490316.jpg
     * goodsImg : http:121.197.1.20/images/201507/1437108490034171398.jpg
     * addTime : 1442419200000
     */

    private int id;
    private String userName;
    private int goodsId;
    private String goodsName;
    private String goodsEnglishName;
    private String goodsThumb;
    private String goodsImg;
    private long addTime;

    public CollectBean(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsEnglishName() {
        return goodsEnglishName;
    }

    public void setGoodsEnglishName(String goodsEnglishName) {
        this.goodsEnglishName = goodsEnglishName;
    }

    public String getGoodsThumb() {
        return goodsThumb;
    }

    public void setGoodsThumb(String goodsThumb) {
        this.goodsThumb = goodsThumb;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "CollectBean{" +
                "id=" + id +
                ", userName=" + userName +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsEnglishName='" + goodsEnglishName + '\'' +
                ", goodsThumb='" + goodsThumb + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", addTime=" + addTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollectBean)) return false;

        CollectBean that = (CollectBean) o;

        if (getId() != that.getId()) return false;
        if (getGoodsId() != that.getGoodsId()) return false;
        if (getAddTime() != that.getAddTime()) return false;
        if (getUserName() != null ? !getUserName().equals(that.getUserName()) : that.getUserName() != null)
            return false;
        if (getGoodsName() != null ? !getGoodsName().equals(that.getGoodsName()) : that.getGoodsName() != null)
            return false;
        if (getGoodsEnglishName() != null ? !getGoodsEnglishName().equals(that.getGoodsEnglishName()) : that.getGoodsEnglishName() != null)
            return false;
        if (getGoodsThumb() != null ? !getGoodsThumb().equals(that.getGoodsThumb()) : that.getGoodsThumb() != null)
            return false;
        return getGoodsImg() != null ? getGoodsImg().equals(that.getGoodsImg()) : that.getGoodsImg() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getUserName() != null ? getUserName().hashCode() : 0);
        result = 31 * result + getGoodsId();
        result = 31 * result + (getGoodsName() != null ? getGoodsName().hashCode() : 0);
        result = 31 * result + (getGoodsEnglishName() != null ? getGoodsEnglishName().hashCode() : 0);
        result = 31 * result + (getGoodsThumb() != null ? getGoodsThumb().hashCode() : 0);
        result = 31 * result + (getGoodsImg() != null ? getGoodsImg().hashCode() : 0);
        result = 31 * result + (int) (getAddTime() ^ (getAddTime() >>> 32));
        return result;
    }
}
