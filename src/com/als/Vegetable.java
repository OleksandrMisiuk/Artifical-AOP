package com.als;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Vegetable extends GLifeForm {
    int vegRad = 10;

    public Vegetable() {
        this.circle = new Circle(this.x, this.y, (double)this.vegRad);
//        this.circle.setFill(Color.GREEN);
        this.energy = (int)(Math.random() * 5.0D + 20.0D);
        this.rad = 10;
        this.RandomTranslate();
        this.image = new Image("file:C:/Developing/ALS_AOP/resources/veg.png");
        this.iv = new ImageView(this.image);
        this.iv.setTranslateX(this.circle.getTranslateX());
        this.iv.setTranslateY(this.circle.getTranslateY());
        this.iv.setFitWidth(25.0D);
        this.iv.setPreserveRatio(true);
    }
}