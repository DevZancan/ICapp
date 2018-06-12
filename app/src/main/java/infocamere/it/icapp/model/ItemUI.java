/*
 * Copyright (c)
 * Created by Luca Visentin - yyi4216
 * 29/05/18 12.14
 */

package infocamere.it.icapp.model;

public class ItemUI {
    private String bkg_color;
    private String itemId;
    private String headerTitle;
    private String title;
    private String subtitle;
    private String bkg_image;

    public ItemUI () {

    }

    public ItemUI(String bkg_color) {
        this.bkg_color = bkg_color;
    }

    public ItemUI (String bkg_color, String itemId, String headerTitle,
                   String title, String subtitle, String bkg_image) {
        this.bkg_color = bkg_color;
        this.itemId = itemId;
        this.headerTitle = headerTitle;
        this.title = title;
        this.subtitle = subtitle;
        this.bkg_image = bkg_image;
    }

    public String getBkg_color() {
        return bkg_color;
    }

    public void setBkg_color(String bkg_color) {
        this.bkg_color = bkg_color;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getBkg_image() {
        return bkg_image;
    }

    public void setBkg_image(String bkg_image) {
        this.bkg_image = bkg_image;
    }

    @Override
    public String toString() {
        return "ItemUI{" +
                "bkg_color='" + bkg_color + '\'' +
                ", itemId='" + itemId + '\'' +
                ", headerTitle='" + headerTitle + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", bkg_image='" + bkg_image + '\'' +
                '}';
    }
}
