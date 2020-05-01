package com.als;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GBug extends GLifeForm {
    int bugRad = 5;
    int scanRange = 75;
    boolean isHunt = false;

    public GBug() {
        this.rad = 5;
        this.circle = new Circle(this.x, this.y, (double)this.bugRad);
        this.circle.setFill(Color.TRANSPARENT);
        this.RandomMoveX();
        this.RandomMoveY();
        this.RandomTranslate();
        this.image = new Image("file:C:/Developing/ALS_AOP/resources/bug.png");
        this.iv = new ImageView(this.image);
        this.iv.setTranslateX(this.circle.getTranslateX());
        this.iv.setTranslateY(this.circle.getTranslateY());
        this.iv.setFitWidth(25.0D);
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
