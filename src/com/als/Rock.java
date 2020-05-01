package com.als;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Rock extends Obstacle {
    public Rock() {
        this.rad = 10;
        this.circle = new Circle(this.x, this.y, (double)this.rad);
        this.circle.setFill(Color.TRANSPARENT);
        this.RandomTranslate();
        this.image = new Image("file:C:/Developing/ALS_AOP/resources/rock.png");
        this.iv = new ImageView(this.image);
        this.iv.setTranslateX(this.circle.getTranslateX());
        this.iv.setTranslateY(this.circle.getTranslateY());
        this.iv.setFitWidth(30.0D);
        this.iv.setPreserveRatio(true);
    }
}
