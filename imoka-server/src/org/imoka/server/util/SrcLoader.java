/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imoka.server.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * <h1>SrcLoader</h1>
 * <p>
 * This class coverts
 * </p>
 *
 *
 * @author r.hendrick
 */
public class SrcLoader {

    private Double defaultImageSize = 16.0;

    public SrcLoader() {
    }

    public SrcLoader(Double defaultImageSize) {
        this.defaultImageSize = defaultImageSize;
    }

    public ImageView project(Double width, Double height) {
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream(
                JfxUtil.IMGPATH + "pjt/16/projects_folder_badged.png")));
        img.setFitHeight(height);
        img.setFitWidth(width);
        return img;
    }

    public ImageView project(Double size) {
        return project(size, size);
    }

    public ImageView project() {
        return project(defaultImageSize, defaultImageSize);
    }

    public ImageView machines(Double width, Double height) {
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream(
                JfxUtil.IMGPATH + "pjt/32/ic_pc_link.png")));
        img.setFitHeight(height);
        img.setFitWidth(width);
        return img;
    }

    public ImageView machines(Double size) {
        return machines(size, size);
    }

    public ImageView machines() {
        return machines(defaultImageSize, defaultImageSize);
    }

    public ImageView plc(Double width, Double height) {
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream(
                JfxUtil.IMGPATH + "pjt/64/pc_32.png")));
        img.setFitHeight(height);
        img.setFitWidth(width);
        return img;
    }

    public ImageView plc(Double size) {
        return plc(size, size);
    }

    public ImageView plc() {
        return plc(defaultImageSize, defaultImageSize);
    }

    public ImageView datas(Double width, Double height) {
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream(
                JfxUtil.IMGPATH + "pjt/16/database-icon.png")));
        img.setFitHeight(height);
        img.setFitWidth(width);
        return img;
    }

    public ImageView datas(Double size) {
        return datas(size, size);
    }

    public ImageView datas() {
        return datas(defaultImageSize, defaultImageSize);
    }
}
