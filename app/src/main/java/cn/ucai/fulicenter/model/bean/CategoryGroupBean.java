package cn.ucai.fulicenter.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/9 0009.
 */

public class CategoryGroupBean implements Serializable{

    /**
     * id : 344
     * name : 最IN
     * imageUrl : muying/2.jpg
     */

    private int id;
    private String name;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
