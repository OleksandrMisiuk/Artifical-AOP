package com.als;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Tree extends Obstacle {
    public Tree() {
        this.rad = 10;
        this.circle = new Circle(this.x, this.y, (double)this.rad);
        this.circle.setFill(Color.TRANSPARENT);
        this.RandomTranslate();
        this.image = new Image("file:C:/Developing/ALS_AOP/resources/tree.png");
        this.iv = new ImageView(this.image);
        this.iv.setTranslateX(this.circle.getTranslateX());
        this.iv.setTranslateY(this.circle.getTranslateY());
        this.iv.setFitWidth(30.0D);
        this.iv.setPreserveRatio(true);
    }
}