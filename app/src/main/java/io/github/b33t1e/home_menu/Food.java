package io.github.b33t1e.home_menu;

import android.graphics.Bitmap;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/12/6.
 */

public class Food extends DataSupport {
    private String name;
    private int imageId;
    private byte [] imageBitmap;
    private String intro;
    private String notTogether;

    /*public Food(String name, int imageId, String intro, String notTogether) {
        this.name = name;
        this.imageId = imageId;
        this.intro = intro;
        this.notTogether = notTogether;
    }*/

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() { return imageId; }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public byte[] getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(byte[] imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getIntro() {return intro; }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getNotTogether() { return notTogether; }

    public void setNotTogether(String notTogether) {
        this.notTogether = notTogether;
    }
}
