package cn.ucai.fulicenter.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/9 0009.
 */

public class BoutiqueBean implements Serializable{

    /**
     * id : 262
     * title : 不一样的新妆，不一样的美丽
     * description : 快速增长修护预防脱发洗发水让头发健康快速生长更美丽
     * name : 拯救头发，美丽新妆
     * imageurl : cat_image/boutique1.png
     */

    private int id;
    private String title;
    private String description;
    private String name;
    private String imageurl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    @Override
    public String toString() {
        return "BoutiqueBean{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", imageurl='" + imageurl + '\'' +
                '}';
    }
}
