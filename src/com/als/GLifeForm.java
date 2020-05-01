package com.als;

public abstract class GLifeForm extends Entity {
    float deltaX = -1.5F;
    float deltaY = -1.5F;
    boolean isRandMove = true;
    double startX;
    double startY;
    int energy;
    boolean isAlive = true;
    boolean isColliding = false;

    public GLifeForm() {
        this.x = this.screenWidth / 2.0D;
        this.y = this.screenHeight / 2.0D;
        this.energy = (int)(Math.random() * 10.0D + 10.0D);
    }

    public void UpdateIV() {
        this.iv.setTranslateX(this.circle.getTranslateX());
        this.iv.setTranslateY(this.circle.getTranslateY());
    }

    public float GetDX() {
        return this.deltaX;
    }

    public float GetDY() {
        return this.deltaY;
    }

    public void SetDX(float DX) {
        this.deltaX = DX;
    }

    public void SetDY(float DY) {
        this.deltaY = DY;
    }
}
