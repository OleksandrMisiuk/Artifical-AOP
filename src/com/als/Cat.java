package com.als;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Cat extends GLifeForm {
    protected double scanRange = 150.0D;

    public Cat() {
        this.rad = 10;
        this.circle = new Circle(this.x, this.y, (double)this.rad);
        this.circle.setFill(Color.TRANSPARENT);
        this.energy = (int)(Math.random() * 10.0D + 5.0D);
        this.RandomMoveX();
        this.RandomMoveY();
        this.RandomTranslate();
        this.image = new Image("file:C:/Developing/ALS_AOP/resources/cat.png");
        this.iv = new ImageView(this.image);
        this.iv.setTranslateX(this.circle.getTranslateX());
        this.iv.setTranslateY(this.circle.getTranslateY());
        this.iv.setFitWidth(60.0D);
        this.iv.setPreserveRatio(true);
    }

    public void IncEnergy(int nEnergy) {
        this.energy += nEnergy;
    }

    public boolean IsRandMove() {
        return this.isRandMove;
    }

    public void RandomMoveX() {
        float randVel = (float)Math.random();
        if (this.deltaX < 0.0F) {
            this.deltaX = -randVel;
        } else {
            this.deltaX = randVel;
        }

    }

    public void RandomMoveY() {
        float randVel = (float)Math.random();
        if (this.deltaY < 0.0F) {
            this.deltaY = -randVel;
        } else {
            this.deltaY = randVel;
        }

    }
}