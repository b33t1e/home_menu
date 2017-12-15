package io.github.b33t1e.home_menu;

import android.graphics.Bitmap;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/12/6.
 */

public class Dish extends DataSupport {
    private String name;
    private int imageId;
    private byte [] imageBitmap;
    private String intro;
    private String content;

    /*public Dish(String name, int imageId, String intro, String content) {
        this.name = name;
        this.imageId = imageId;
        this.intro = intro;
        this.content = content;
    }
    */

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public byte[] getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(byte[] imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getContent() { return content; }

    public void setContent(String content) {
        this.content = content;
    }
}
