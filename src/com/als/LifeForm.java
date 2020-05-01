package com.als;

public abstract class LifeForm {
    String species;
    char symbol;
    int energy;
    int ID;
    boolean isDead = false;
    int gridSize;
    int x;
    int y;
    boolean nBlock = false;
    boolean eBlock = false;
    boolean sBlock = false;
    boolean wBlock = false;
    boolean skipMove = false;
    public boolean isScanTwo = false;

    public LifeForm(int _gridSize, int _id) {
        this.gridSize = _gridSize;
    }

    public void RandomStart() {
    }

    public char GetSymbol() {
        return this.symbol;
    }

    public void SetSymbol(char sym) {
        this.symbol = sym;
    }

    public void ResetBools() {
        this.nBlock = false;
        this.eBlock = false;
        this.sBlock = false;
        this.wBlock = false;
        this.skipMove = false;
    }
}
