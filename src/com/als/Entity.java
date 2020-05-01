package com.als;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Entity {
    double screenWidth;
    double screenHeight;
    double x;
    double y;
    public Circle circle;
    public int rad;
    Image image;
    ImageView iv;
    private int yOffset;

    public Entity() {
        this.screenWidth = Simulation.screenWidth;
        this.screenHeight = Simulation.screenHeight;
        this.x = this.screenWidth / 2.0D;
        this.y = this.screenHeight / 2.0D;
        this.yOffset = 25;
    }

    public Circle GetCircle() {
        return this.circle;
    }

    public void RandomTranslate() {
        int maxX = (int)(this.screenWidth - 100.0D);
        int maxY = (int)(this.screenHeight - 100.0D);
        int randX = randPosInt(0, maxX);
        int randY = randPosInt(0, maxY);
        randX = (int)((double)randX - this.screenWidth / 2.0D);
        randY = (int)((double)randY - this.screenHeight / 2.0D);
        this.circle.setTranslateX(this.circle.getTranslateX() + (double)randX + 60.0D);
        this.circle.setTranslateY(this.circle.getTranslateY() + (double)randY + (double)this.yOffset);
    }

    public static int randPosInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt(max - min + 1) + min;
        return randomNum;
    }

    public ImageView GetIV() {
        return this.iv;
    }
}
