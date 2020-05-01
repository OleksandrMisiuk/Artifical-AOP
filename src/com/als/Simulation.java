package com.als;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.WritableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Simulation {
    GBug[] bugArray;
    Vegetable[] vegArray;
    GBird[] birdArray;
    Rock[] rockArray;
    Tree[] treeArray;
    Cat[] catArray;
    int yTopOff = 0;
    int yBotOff = -40;
    double startTime;
    double startFoodTime;
    Timeline anim;
    int numBugs = 20;
    int numVeg = 40;
    int numBirds = 10;
    int numRocks = 3;
    int numTrees = 3;
    int numCats = 3;
    static double screenWidth;
    static double screenHeight;
    boolean isPause = false;
    KeyFrame frame;
    StackPane root;
    int aliveBugs;
    int aliveBirds;
    int[] lifeArray;
    final Button btnPausePlay = new Button("Pause / Play");
    final Button btnRemBug = new Button("Remove Bug");
    final Button btnAddBug = new Button("Add Bug");
    final Button btnAddBird = new Button("Add Bird");
    final Button btnRemBird = new Button("Remove Bird");
    Scene aScene;

    public Simulation(Stage stage, Scene scene, int sHeight, int sWidth, int _numBugs, int _numBirds, int _numVeg, int numObs) {
        this.numBugs = _numBugs;
        this.numBirds = _numBirds;
        this.numRocks = numObs;
        this.numTrees = numObs;
        this.numVeg = _numVeg;
        this.bugArray = new GBug[100];
        this.vegArray = new Vegetable[100];
        this.birdArray = new GBird[100];
        this.rockArray = new Rock[100];
        this.treeArray = new Tree[100];
        this.catArray = new Cat[this.numCats];
        screenWidth = (double)sWidth;
        screenHeight = (double)sHeight;
        this.startTime = (double)System.currentTimeMillis();
        this.startFoodTime = (double)System.currentTimeMillis();
        this.lifeArray = new int[2];
        this.aScene = scene;
        this.start(stage, scene);
    }

    public void start(Stage primaryStage, Scene scene) {
        this.btnPausePlay.setLayoutX(screenWidth / 2.0D - 155.0D);
        this.btnPausePlay.setLayoutY(screenHeight / 2.0D + 45.0D);
        this.btnAddBug.setLayoutX(screenWidth / 2.0D - 155.0D);
        this.btnAddBug.setLayoutY(screenHeight / 2.0D + 15.0D);
        this.btnRemBug.setLayoutX(screenWidth / 2.0D - 155.0D);
        this.btnRemBug.setLayoutY(screenHeight / 2.0D - 15.0D);
        this.btnAddBird.setLayoutX(screenWidth / 2.0D - 155.0D);
        this.btnAddBird.setLayoutY(screenHeight / 2.0D - 45.0D);
        this.btnRemBird.setLayoutX(screenWidth / 2.0D - 155.0D);
        this.btnRemBird.setLayoutY(screenHeight / 2.0D - 75.0D);
        this.root = new StackPane();
        this.Populate();
        this.btnPausePlay.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent click) {
                if (!Simulation.this.isPause) {
                    Simulation.this.isPause = true;
                } else {
                    Simulation.this.isPause = false;
                }

            }
        });
        this.btnAddBug.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent click) {
                Simulation.this.ReviveCreature("bug");
            }
        });
        this.btnRemBug.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent click) {
                Simulation.this.KillCreature("bug");
            }
        });
        this.btnAddBird.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent click) {
                Simulation.this.ReviveCreature("bird");
            }
        });
        this.btnRemBird.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent click) {
                Simulation.this.KillCreature("bird");
            }
        });
        ((VBox)scene.getRoot()).getChildren().addAll(new Node[]{this.root, this.btnPausePlay, this.btnAddBug, this.btnRemBug, this.btnAddBird, this.btnRemBird});
        this.frame = new KeyFrame(Duration.millis(23.0D), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if (!Simulation.this.isPause) {
                    this.CheckObsCollision();

                    int i;
                    float temp;
                    for(i = 0; i < Simulation.this.numBugs; ++i) {
                        if (Simulation.this.bugArray[i].GetCircle().getCenterX() + Simulation.this.bugArray[i].GetCircle().getTranslateX() < Simulation.this.bugArray[i].GetCircle().getRadius() || Simulation.this.bugArray[i].GetCircle().getCenterX() + Simulation.this.bugArray[i].GetCircle().getTranslateX() > Simulation.screenWidth - Simulation.this.bugArray[i].GetCircle().getRadius()) {
                            temp = Simulation.this.bugArray[i].GetDX();
                            temp *= -1.0F;
                            Simulation.this.bugArray[i].SetDX(temp);
                        }

                        if (Simulation.this.bugArray[i].GetCircle().getCenterY() + Simulation.this.bugArray[i].GetCircle().getTranslateY() < Simulation.this.bugArray[i].GetCircle().getRadius() + (double)Simulation.this.yTopOff || Simulation.this.bugArray[i].GetCircle().getCenterY() + Simulation.this.bugArray[i].GetCircle().getTranslateY() > Simulation.screenHeight - Simulation.this.bugArray[i].GetCircle().getRadius() + (double)Simulation.this.yBotOff) {
                            temp = Simulation.this.bugArray[i].GetDY();
                            temp *= -1.0F;
                            Simulation.this.bugArray[i].SetDY(temp);
                        }
                    }

                    for(i = 0; i < Simulation.this.numBirds; ++i) {
                        if (Simulation.this.birdArray[i].GetCircle().getCenterX() + Simulation.this.birdArray[i].GetCircle().getTranslateX() < Simulation.this.birdArray[i].GetCircle().getRadius() || Simulation.this.birdArray[i].GetCircle().getCenterX() + Simulation.this.birdArray[i].GetCircle().getTranslateX() > Simulation.screenWidth - Simulation.this.birdArray[i].GetCircle().getRadius()) {
                            temp = Simulation.this.birdArray[i].GetDX();
                            temp *= -1.0F;
                            Simulation.this.birdArray[i].SetDX(temp);
                        }

                        if (Simulation.this.birdArray[i].GetCircle().getCenterY() + Simulation.this.birdArray[i].GetCircle().getTranslateY() < Simulation.this.birdArray[i].GetCircle().getRadius() + (double)Simulation.this.yTopOff || Simulation.this.birdArray[i].GetCircle().getCenterY() + Simulation.this.birdArray[i].GetCircle().getTranslateY() > Simulation.screenHeight - Simulation.this.birdArray[i].GetCircle().getRadius() + (double)Simulation.this.yBotOff) {
                            temp = Simulation.this.birdArray[i].GetDY();
                            temp *= -1.0F;
                            Simulation.this.birdArray[i].SetDY(temp);
                        }
                    }

                    for(i = 0; i < Simulation.this.numCats; ++i) {
                        if (Simulation.this.catArray[i].GetCircle().getCenterX() + Simulation.this.catArray[i].GetCircle().getTranslateX() < Simulation.this.birdArray[i].GetCircle().getRadius() || Simulation.this.catArray[i].GetCircle().getCenterX() + Simulation.this.catArray[i].GetCircle().getTranslateX() > Simulation.screenWidth - Simulation.this.catArray[i].GetCircle().getRadius()) {
                            temp = Simulation.this.catArray[i].GetDX();
                            temp *= -1.0F;
                            Simulation.this.catArray[i].SetDX(temp);
                        }

                        if (Simulation.this.catArray[i].GetCircle().getCenterY() + Simulation.this.catArray[i].GetCircle().getTranslateY() < Simulation.this.catArray[i].GetCircle().getRadius() + (double)Simulation.this.yTopOff || Simulation.this.catArray[i].GetCircle().getCenterY() + Simulation.this.catArray[i].GetCircle().getTranslateY() > Simulation.screenHeight - Simulation.this.catArray[i].GetCircle().getRadius() + (double)Simulation.this.yBotOff) {
                            temp = Simulation.this.catArray[i].GetDY();
                            temp *= -1.0F;
                            Simulation.this.catArray[i].SetDY(temp);
                        }
                    }

                    GBug var10000;
                    double Y;
                    int j;
                    double predX;
                    double Y2;
                    double X;
                    for(i = 0; i < Simulation.this.numBugs; ++i) {
                        X = Simulation.this.bugArray[i].GetCircle().getTranslateX() + Simulation.this.bugArray[i].GetCircle().getCenterX();
                        Y = Simulation.this.bugArray[i].GetCircle().getTranslateY() + Simulation.this.bugArray[i].GetCircle().getCenterY();

                        for(j = 0; j < Simulation.this.numVeg; ++j) {
                            predX = Simulation.this.vegArray[j].GetCircle().getTranslateX() + Simulation.this.vegArray[j].GetCircle().getCenterX();
                            Y2 = Simulation.this.vegArray[j].GetCircle().getTranslateY() + Simulation.this.vegArray[j].GetCircle().getCenterY();
                            if (this.dist(X, predX, Y, Y2) < (double)Simulation.this.bugArray[i].scanRange && Simulation.this.vegArray[j].isAlive) {
                                Simulation.this.bugArray[i].isHunt = true;
                                Simulation.this.bugArray[i].isRandMove = false;
                                if (X < predX) {
                                    if (Simulation.this.bugArray[i].deltaX < 1.0F) {
                                        var10000 = Simulation.this.bugArray[i];
                                        var10000.deltaX += 0.05F;
                                    }
                                } else if (Simulation.this.bugArray[i].deltaX > -1.0F) {
                                    var10000 = Simulation.this.bugArray[i];
                                    var10000.deltaX -= 0.05F;
                                }

                                if (Y < Y2) {
                                    if (Simulation.this.bugArray[i].deltaY < 1.0F) {
                                        var10000 = Simulation.this.bugArray[i];
                                        var10000.deltaY += 0.05F;
                                    }
                                } else if (Simulation.this.bugArray[i].deltaY > -1.0F) {
                                    var10000 = Simulation.this.bugArray[i];
                                    var10000.deltaY -= 0.05F;
                                }
                            } else {
                                Simulation.this.bugArray[i].isRandMove = true;
                            }

                            if (this.dist(X, predX, Y, Y2) < Simulation.this.bugArray[i].GetCircle().getRadius() + (double)Simulation.this.vegArray[0].rad && Simulation.this.vegArray[j].isAlive) {
                                Simulation.this.vegArray[j].isAlive = false;
                                Simulation.this.vegArray[j].GetCircle().setFill(Color.TRANSPARENT);
                                var10000 = Simulation.this.bugArray[i];
                                var10000.energy += Simulation.this.vegArray[j].energy;
                            }
                        }
                    }

                    for(i = 0; i < Simulation.this.numBugs; ++i) {
                        X = Simulation.this.bugArray[i].GetCircle().getTranslateX() + Simulation.this.bugArray[i].GetCircle().getCenterX();
                        Y = Simulation.this.bugArray[i].GetCircle().getTranslateY() + Simulation.this.bugArray[i].GetCircle().getCenterY();

                        for(j = 0; j < Simulation.this.numBirds; ++j) {
                            predX = Simulation.this.birdArray[j].GetCircle().getTranslateX() + Simulation.this.birdArray[j].GetCircle().getCenterX();
                            Y2 = Simulation.this.birdArray[j].GetCircle().getTranslateY() + Simulation.this.birdArray[j].GetCircle().getCenterY();
                            if (this.dist(X, predX, Y, Y2) < 50.0D && Simulation.this.birdArray[j].isAlive) {
                                Simulation.this.bugArray[i].isRandMove = false;
                                if (X > predX) {
                                    var10000 = Simulation.this.bugArray[i];
                                    var10000.deltaX += 0.05F;
                                } else {
                                    var10000 = Simulation.this.bugArray[i];
                                    var10000.deltaX -= 0.05F;
                                }

                                if (Y > Y2) {
                                    var10000 = Simulation.this.bugArray[i];
                                    var10000.deltaY += 0.05F;
                                } else {
                                    var10000 = Simulation.this.bugArray[i];
                                    var10000.deltaY -= 0.05F;
                                }
                            } else {
                                Simulation.this.bugArray[i].isRandMove = true;
                            }
                        }
                    }

                    for(i = 0; i < Simulation.this.numBirds; ++i) {
                        X = Simulation.this.birdArray[i].GetCircle().getTranslateX() + Simulation.this.birdArray[i].GetCircle().getCenterX();
                        Y = Simulation.this.birdArray[i].GetCircle().getTranslateY() + Simulation.this.birdArray[i].GetCircle().getCenterY();

                        GBird var14;
                        for(j = 0; j < Simulation.this.numBugs; ++j) {
                            predX = Simulation.this.bugArray[j].GetCircle().getTranslateX() + Simulation.this.bugArray[j].GetCircle().getCenterX();
                            Y2 = Simulation.this.bugArray[j].GetCircle().getTranslateY() + Simulation.this.bugArray[j].GetCircle().getCenterY();
                            if (this.dist(X, predX, Y, Y2) < (double)Simulation.this.birdArray[i].scanRange && Simulation.this.bugArray[j].isAlive) {
                                if (X < predX) {
                                    if (Simulation.this.birdArray[i].deltaX < 1.5F) {
                                        var14 = Simulation.this.birdArray[i];
                                        var14.deltaX += 0.05F;
                                    }
                                } else if (Simulation.this.birdArray[i].deltaX > -1.5F) {
                                    var14 = Simulation.this.birdArray[i];
                                    var14.deltaX -= 0.05F;
                                }

                                if (Y < Y2) {
                                    if (Simulation.this.birdArray[i].deltaY < 1.5F) {
                                        var14 = Simulation.this.birdArray[i];
                                        var14.deltaY += 0.05F;
                                    }
                                } else if (Simulation.this.birdArray[i].deltaY > -1.5F) {
                                    var14 = Simulation.this.birdArray[i];
                                    var14.deltaY -= 0.05F;
                                }
                            } else {
                                Simulation.this.birdArray[i].isRandMove = true;
                            }

                            if (this.dist(X, predX, Y, Y2) < Simulation.this.bugArray[i].GetCircle().getRadius() * 2.0D && Simulation.this.bugArray[j].isAlive) {
                                Simulation.this.bugArray[j].isAlive = false;
                                Simulation.this.root.getChildren().removeAll(new Node[]{Simulation.this.bugArray[j].GetCircle(), Simulation.this.bugArray[j].GetIV()});
                                var14 = Simulation.this.birdArray[i];
                                var14.energy += Simulation.this.bugArray[j].energy;
                            }
                        }

                        for(j = 0; j < Simulation.this.numBirds; ++j) {
                            if (i != j) {
                                predX = Simulation.this.birdArray[j].GetCircle().getTranslateX() + Simulation.this.birdArray[j].GetCircle().getCenterX();
                                Y2 = Simulation.this.birdArray[j].GetCircle().getTranslateY() + Simulation.this.birdArray[j].GetCircle().getCenterY();
                                if (this.dist(X, predX, Y, Y2) > (double)((float)Simulation.this.birdArray[i].scanRange * 2.5F) && Simulation.this.birdArray[j].isAlive) {
                                    if (X < predX) {
                                        if (Simulation.this.birdArray[i].deltaX < 1.0F) {
                                            var14 = Simulation.this.birdArray[i];
                                            var14.deltaX += 0.05F;
                                        }
                                    } else if (Simulation.this.birdArray[i].deltaX > -1.0F) {
                                        var14 = Simulation.this.birdArray[i];
                                        var14.deltaX -= 0.05F;
                                    }

                                    if (Y < Y2) {
                                        if (Simulation.this.birdArray[i].deltaY < 1.0F) {
                                            var14 = Simulation.this.birdArray[i];
                                            var14.deltaY += 0.05F;
                                        }
                                    } else if (Simulation.this.birdArray[i].deltaY > -1.0F) {
                                        var14 = Simulation.this.birdArray[i];
                                        var14.deltaY -= 0.05F;
                                    }
                                }
                            }
                        }

                        for(j = 0; j < Simulation.this.numCats; ++j) {
                            predX = Simulation.this.catArray[j].GetCircle().getTranslateX() + Simulation.this.catArray[j].GetCircle().getCenterX();
                            Y2 = Simulation.this.catArray[j].GetCircle().getTranslateY() + Simulation.this.catArray[j].GetCircle().getCenterY();
                            if (this.dist(X, predX, Y, Y2) >= 100.0D) {
                                Simulation.this.birdArray[i].isRandMove = true;
                            } else {
                                Simulation.this.birdArray[i].isRandMove = false;
                                if (X > predX && Simulation.this.birdArray[i].deltaX < 2.0F) {
                                    var14 = Simulation.this.birdArray[i];
                                    var14.deltaX += 0.05F;
                                } else if (Simulation.this.birdArray[i].deltaX > -2.0F) {
                                    var14 = Simulation.this.birdArray[i];
                                    var14.deltaX -= 0.05F;
                                }

                                if (Y > Y2 && Simulation.this.birdArray[i].deltaY < 2.0F) {
                                    var14 = Simulation.this.birdArray[i];
                                    var14.deltaY += 0.05F;
                                } else if (Simulation.this.birdArray[i].deltaY > -2.0F) {
                                    var14 = Simulation.this.birdArray[i];
                                    var14.deltaY -= 0.05F;
                                }
                            }
                        }
                    }

                    for(i = 0; i < Simulation.this.numCats; ++i) {
                        X = Simulation.this.catArray[i].GetCircle().getTranslateX() + Simulation.this.catArray[i].GetCircle().getCenterX();
                        Y = Simulation.this.catArray[i].GetCircle().getTranslateY() + Simulation.this.catArray[i].GetCircle().getCenterY();

                        for(j = 0; j < Simulation.this.numBirds; ++j) {
                            predX = Simulation.this.birdArray[j].GetCircle().getTranslateX() + Simulation.this.birdArray[j].GetCircle().getCenterX();
                            Y2 = Simulation.this.birdArray[j].GetCircle().getTranslateY() + Simulation.this.birdArray[j].GetCircle().getCenterY();
                            if (this.dist(X, predX, Y, Y2) < Simulation.this.catArray[i].scanRange && Simulation.this.birdArray[j].isAlive) {
                                Cat var15;
                                if (X < predX) {
                                    if (Simulation.this.catArray[i].deltaX < 2.0F) {
                                        var15 = Simulation.this.catArray[i];
                                        var15.deltaX += 0.05F;
                                    }
                                } else if (Simulation.this.catArray[i].deltaX > -2.0F) {
                                    var15 = Simulation.this.catArray[i];
                                    var15.deltaX -= 0.05F;
                                }

                                if (Y < Y2) {
                                    if (Simulation.this.catArray[i].deltaY < 2.0F) {
                                        var15 = Simulation.this.catArray[i];
                                        var15.deltaY += 0.05F;
                                    }
                                } else if (Simulation.this.catArray[i].deltaY > -2.0F) {
                                    var15 = Simulation.this.catArray[i];
                                    var15.deltaY -= 0.05F;
                                }
                            } else {
                                Simulation.this.catArray[i].isRandMove = true;
                            }

                            if (this.dist(X, predX, Y, Y2) < Simulation.this.birdArray[i].GetCircle().getRadius() * 2.0D && Simulation.this.birdArray[j].isAlive) {
                                Simulation.this.birdArray[j].isAlive = false;
                                Simulation.this.root.getChildren().removeAll(new Node[]{Simulation.this.birdArray[j].GetCircle(), Simulation.this.birdArray[j].GetIV()});
                            }
                        }
                    }

                    if ((double)System.currentTimeMillis() > Simulation.this.startTime + 3000.0D) {
                        Simulation.this.startTime = (double)System.currentTimeMillis();

                        for(i = 0; i < Simulation.this.numBugs; ++i) {
                            if (Simulation.this.bugArray[i].isAlive && Simulation.this.bugArray[i].isRandMove) {
                                Simulation.this.bugArray[i].RandomMoveX();
                                Simulation.this.bugArray[i].RandomMoveY();
                                --Simulation.this.bugArray[i].energy;
                                if (Simulation.this.bugArray[i].energy < 1) {
                                    Simulation.this.bugArray[i].isAlive = false;
                                    Simulation.this.root.getChildren().removeAll(new Node[]{Simulation.this.bugArray[i].GetCircle(), Simulation.this.bugArray[i].GetIV()});
                                }
                            }
                        }

                        for(i = 0; i < Simulation.this.numBirds; ++i) {
                            if (Simulation.this.birdArray[i].isAlive && Simulation.this.birdArray[i].isRandMove) {
                                Simulation.this.birdArray[i].RandomMoveX();
                                Simulation.this.birdArray[i].RandomMoveY();
                                --Simulation.this.birdArray[i].energy;
                                System.out.println(Simulation.this.birdArray[i].energy);
                                if (Simulation.this.birdArray[i].energy < 1) {
                                    Simulation.this.birdArray[i].isAlive = false;
                                    Simulation.this.root.getChildren().removeAll(new Node[]{Simulation.this.birdArray[i].GetCircle(), Simulation.this.birdArray[i].GetIV()});
                                }
                            }
                        }

                        for(i = 0; i < Simulation.this.numCats; ++i) {
                            if (Simulation.this.catArray[i].isAlive && Simulation.this.catArray[i].isRandMove) {
                                Simulation.this.catArray[i].RandomMoveX();
                                Simulation.this.catArray[i].RandomMoveY();
                            }
                        }
                    }

                    if ((double)System.currentTimeMillis() > Simulation.this.startFoodTime + 500.0D) {
                        Simulation.this.startFoodTime = (double)System.currentTimeMillis();
                        double tempRand = Math.random() * (double)Simulation.this.numVeg;
                        if (!Simulation.this.vegArray[(int)tempRand].isAlive) {
                            Simulation.this.vegArray[(int)tempRand].isAlive = true;
                            Simulation.this.vegArray[(int)tempRand].GetCircle().setFill(Color.GREEN);
                        }
                    }

                    for(i = 0; i < Simulation.this.numBugs; ++i) {
                        if (Simulation.this.bugArray[i].isAlive) {
                            Simulation.this.bugArray[i].GetCircle().setTranslateX(Simulation.this.bugArray[i].GetCircle().getTranslateX() + (double)Simulation.this.bugArray[i].GetDX());
                            Simulation.this.bugArray[i].GetCircle().setTranslateY(Simulation.this.bugArray[i].GetCircle().getTranslateY() + (double)Simulation.this.bugArray[i].GetDY());
                            Simulation.this.bugArray[i].UpdateIV();
                        }
                    }

                    for(i = 0; i < Simulation.this.numBirds; ++i) {
                        if (Simulation.this.birdArray[i].isAlive) {
                            Simulation.this.birdArray[i].GetCircle().setTranslateX(Simulation.this.birdArray[i].GetCircle().getTranslateX() + (double)Simulation.this.birdArray[i].GetDX());
                            Simulation.this.birdArray[i].GetCircle().setTranslateY(Simulation.this.birdArray[i].GetCircle().getTranslateY() + (double)Simulation.this.birdArray[i].GetDY());
                            Simulation.this.birdArray[i].UpdateIV();
                        }
                    }

                    for(i = 0; i < Simulation.this.numCats; ++i) {
                        if (Simulation.this.catArray[i].isAlive) {
                            Simulation.this.catArray[i].GetCircle().setTranslateX(Simulation.this.catArray[i].GetCircle().getTranslateX() + (double)Simulation.this.catArray[i].GetDX());
                            Simulation.this.catArray[i].GetCircle().setTranslateY(Simulation.this.catArray[i].GetCircle().getTranslateY() + (double)Simulation.this.catArray[i].GetDY());
                            Simulation.this.catArray[i].UpdateIV();
                        }
                    }
                }

            }

            private double dist(double posBall1x, double posBall2x, double posBall1y, double posBall2y) {
                double diffX = posBall1x - posBall2x;
                double diffY = posBall1y - posBall2y;
                double distance = diffX * diffX + diffY * diffY;
                distance = Math.sqrt(distance);
                return distance;
            }

            private void CheckObsCollision() {
                GBug var10000;
                int i;
                double X;
                double Y;
                int j;
                double bX;
                double bY;
                double tempRand;
                GBird var13;
                for(i = 0; i < Simulation.this.numRocks; ++i) {
                    X = Simulation.this.rockArray[i].GetCircle().getTranslateX() + Simulation.this.rockArray[i].GetCircle().getCenterX();
                    Y = Simulation.this.rockArray[i].GetCircle().getTranslateY() + Simulation.this.rockArray[i].GetCircle().getCenterY();

                    for(j = 0; j < Simulation.this.numBugs; ++j) {
                        bX = Simulation.this.bugArray[j].GetCircle().getTranslateX() + Simulation.this.bugArray[j].GetCircle().getCenterX();
                        bY = Simulation.this.bugArray[j].GetCircle().getTranslateY() + Simulation.this.bugArray[j].GetCircle().getCenterY();
                        if (this.dist(bX, X, bY, Y) < (double)(Simulation.this.rockArray[i].rad + Simulation.this.bugArray[j].rad) && Simulation.this.bugArray[j].isAlive && Simulation.this.bugArray[j].energy > 10) {
                            tempRand = Math.random() * (double)Simulation.this.numBugs;
                            if (!Simulation.this.bugArray[(int)tempRand].isAlive) {
                                var10000 = Simulation.this.bugArray[j];
                                var10000.energy /= 2;
                                Simulation.this.bugArray[(int)tempRand].isAlive = true;
                                Simulation.this.root.getChildren().addAll(new Node[]{Simulation.this.bugArray[(int)tempRand].GetCircle(), Simulation.this.bugArray[(int)tempRand].GetIV()});
                                Simulation.this.bugArray[(int)tempRand].energy = Simulation.this.bugArray[j].energy;
                                Simulation.this.bugArray[(int)tempRand].GetCircle().setTranslateX(Simulation.this.rockArray[i].GetCircle().getTranslateX());
                                Simulation.this.bugArray[(int)tempRand].GetCircle().setTranslateY(Simulation.this.rockArray[i].GetCircle().getTranslateY());
                                Simulation.this.bugArray[(int)tempRand].SetDX(0.0F);
                                Simulation.this.bugArray[(int)tempRand].SetDY(0.0F);
                            }
                        }
                    }

                    for(j = 0; j < Simulation.this.numBirds; ++j) {
                        bX = Simulation.this.birdArray[j].GetCircle().getTranslateX() + Simulation.this.birdArray[j].GetCircle().getCenterX();
                        bY = Simulation.this.birdArray[j].GetCircle().getTranslateY() + Simulation.this.birdArray[j].GetCircle().getCenterY();
                        if (this.dist(bX, X, bY, Y) < (double)(Simulation.this.rockArray[i].rad + Simulation.this.birdArray[j].rad) && Simulation.this.birdArray[j].isAlive) {
                            Simulation.this.birdArray[j].SetDX(Simulation.this.birdArray[j].GetDX() * -1.0F);
                            Simulation.this.birdArray[j].SetDY(Simulation.this.birdArray[j].GetDY() * -1.0F);
                        }
                    }

                    for(j = 0; j < Simulation.this.numBirds; ++j) {
                        bX = Simulation.this.birdArray[j].GetCircle().getTranslateX() + Simulation.this.birdArray[j].GetCircle().getCenterX();
                        bY = Simulation.this.birdArray[j].GetCircle().getTranslateY() + Simulation.this.birdArray[j].GetCircle().getCenterY();
                        if (this.dist(bX, X, bY, Y) < (double)(50 + Simulation.this.birdArray[j].scanRange) && Simulation.this.birdArray[j].isAlive && this.dist(X, bX, Y, bY) < (double)((float)Simulation.this.rockArray[i].rad * 1.5F) && Simulation.this.birdArray[j].isAlive) {
                            if (bX > X && Simulation.this.birdArray[j].deltaX < 1.0F) {
                                var13 = Simulation.this.birdArray[j];
                                var13.deltaX += 0.05F;
                            } else if (Simulation.this.birdArray[j].deltaX > -1.0F) {
                                var13 = Simulation.this.birdArray[j];
                                var13.deltaX -= 0.05F;
                            }

                            if (bY > Y && Simulation.this.birdArray[j].deltaY < 1.0F) {
                                var13 = Simulation.this.birdArray[j];
                                var13.deltaY += 0.05F;
                            } else if (Simulation.this.birdArray[j].deltaY > -1.0F) {
                                var13 = Simulation.this.birdArray[j];
                                var13.deltaY -= 0.05F;
                            }
                        }
                    }
                }

                for(i = 0; i < Simulation.this.numTrees; ++i) {
                    X = Simulation.this.treeArray[i].GetCircle().getTranslateX() + Simulation.this.treeArray[i].GetCircle().getCenterX();
                    Y = Simulation.this.treeArray[i].GetCircle().getTranslateY() + Simulation.this.treeArray[i].GetCircle().getCenterY();

                    for(j = 0; j < Simulation.this.numBirds; ++j) {
                        bX = Simulation.this.birdArray[j].GetCircle().getTranslateX() + Simulation.this.birdArray[j].GetCircle().getCenterX();
                        bY = Simulation.this.birdArray[j].GetCircle().getTranslateY() + Simulation.this.birdArray[j].GetCircle().getCenterY();
                        if (this.dist(bX, X, bY, Y) < (double)(Simulation.this.treeArray[i].rad + Simulation.this.birdArray[j].rad) && Simulation.this.birdArray[j].energy > 50) {
                            System.out.println("Collision bird and tree");
                            tempRand = Math.random() * (double)Simulation.this.numBirds;
                            if (!Simulation.this.birdArray[(int)tempRand].isAlive) {
                                var13 = Simulation.this.birdArray[j];
                                var13.energy /= 2;
                                Simulation.this.birdArray[(int)tempRand].isAlive = true;
                                Simulation.this.root.getChildren().addAll(new Node[]{Simulation.this.birdArray[(int)tempRand].GetCircle(), Simulation.this.birdArray[(int)tempRand].GetIV()});
                                Simulation.this.birdArray[(int)tempRand].energy = Simulation.this.birdArray[j].energy;
                                Simulation.this.birdArray[(int)tempRand].GetCircle().setTranslateX(Simulation.this.treeArray[i].GetCircle().getTranslateX());
                                Simulation.this.birdArray[(int)tempRand].GetCircle().setTranslateY(Simulation.this.treeArray[i].GetCircle().getTranslateY());
                                Simulation.this.birdArray[(int)tempRand].SetDX(0.0F);
                                Simulation.this.birdArray[(int)tempRand].SetDY(0.0F);
                            }
                        }
                    }

                    for(j = 0; j < Simulation.this.numBugs; ++j) {
                        bX = Simulation.this.bugArray[j].GetCircle().getTranslateX() + Simulation.this.bugArray[j].GetCircle().getCenterX();
                        bY = Simulation.this.bugArray[j].GetCircle().getTranslateY() + Simulation.this.bugArray[j].GetCircle().getCenterY();
                        if (this.dist(bX, X, bY, Y) < (double)(Simulation.this.treeArray[i].rad + Simulation.this.bugArray[j].rad) && Simulation.this.bugArray[j].isAlive) {
                            if (!Simulation.this.bugArray[j].isColliding) {
                                Simulation.this.bugArray[j].SetDX(Simulation.this.bugArray[j].GetDX() * -1.0F);
                                Simulation.this.bugArray[j].SetDY(Simulation.this.bugArray[j].GetDY() * -1.0F);
                                Simulation.this.bugArray[j].isColliding = true;
                            } else {
                                Simulation.this.bugArray[j].isColliding = false;
                            }
                        }

                        if (this.dist(X, bX, Y, bY) < (double)((float)Simulation.this.treeArray[i].rad * 1.5F) && Simulation.this.bugArray[j].isAlive) {
                            if (bX > X && Simulation.this.bugArray[j].deltaX < 1.0F) {
                                var10000 = Simulation.this.bugArray[j];
                                var10000.deltaX += 0.05F;
                            } else if (Simulation.this.bugArray[j].deltaX > -1.0F) {
                                var10000 = Simulation.this.bugArray[j];
                                var10000.deltaX -= 0.05F;
                            }

                            if (bY > Y && Simulation.this.bugArray[j].deltaY < 1.0F) {
                                var10000 = Simulation.this.bugArray[j];
                                var10000.deltaY += 0.05F;
                            } else if (Simulation.this.bugArray[j].deltaY > -1.0F) {
                                var10000 = Simulation.this.bugArray[j];
                                var10000.deltaY -= 0.05F;
                            }
                        }
                    }
                }

            }
        }, new KeyValue[0]);
        WritableValue<Number> startXVal = new SimpleDoubleProperty(100.0D);
        this.anim = ((TimelineBuilder)((TimelineBuilder)TimelineBuilder.create().autoReverse(false)).keyFrames(new KeyFrame[]{this.frame, new KeyFrame(new Duration(13.0D), new KeyValue[]{new KeyValue(startXVal, 300.0D, Interpolator.LINEAR)})}).cycleCount(-1)).build();
        this.anim.play();
        primaryStage.setTitle("Simulation Running");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void ClearStack() {
        int i;
        for(i = 0; i < this.numBugs; ++i) {
            this.root.getChildren().removeAll(new Node[]{this.bugArray[i].GetCircle(), this.bugArray[i].GetIV()});
        }

        for(i = 0; i < this.numVeg; ++i) {
            this.root.getChildren().removeAll(new Node[]{this.vegArray[i].GetCircle(), this.vegArray[i].GetIV()});
        }

        for(i = 0; i < this.numBirds; ++i) {
            this.root.getChildren().removeAll(new Node[]{this.birdArray[i].GetCircle(), this.birdArray[i].GetIV()});
        }

        for(i = 0; i < this.numRocks; ++i) {
            this.root.getChildren().removeAll(new Node[]{this.rockArray[i].GetCircle(), this.rockArray[i].GetIV()});
            this.root.getChildren().removeAll(new Node[]{this.treeArray[i].GetCircle(), this.treeArray[i].GetIV()});
        }

        for(i = 0; i < this.numCats; ++i) {
            this.root.getChildren().removeAll(new Node[]{this.catArray[i].GetCircle(), this.catArray[i].GetIV()});
        }

        ((VBox)this.aScene.getRoot()).getChildren().removeAll(new Node[]{this.root, this.btnPausePlay, this.btnAddBug, this.btnRemBug, this.btnAddBird, this.btnRemBird});
    }

    public int[] CheckWhoIsAlive() {
        int aliveBugs = 0;

        int aliveBirds;
        for(aliveBirds = 0; aliveBirds < this.numBugs; ++aliveBirds) {
            if (this.bugArray[aliveBirds].isAlive) {
                ++aliveBugs;
            }
        }

        aliveBirds = 0;

        for(int i = 0; i < this.numBirds; ++i) {
            if (this.birdArray[i].isAlive) {
                ++aliveBirds;
            }
        }

        this.lifeArray[0] = aliveBugs;
        this.lifeArray[1] = aliveBirds;
        return this.lifeArray;
    }

    public void Populate() {
        int i;
        for(i = 0; i < this.numBugs; ++i) {
            this.bugArray[i] = new GBug();
            this.root.getChildren().addAll(new Node[]{this.bugArray[i].GetCircle(), this.bugArray[i].GetIV()});
        }

        for(i = 0; i < this.numVeg; ++i) {
            this.vegArray[i] = new Vegetable();
            this.root.getChildren().addAll(new Node[]{this.vegArray[i].GetCircle(), this.vegArray[i].GetIV()});
        }

        for(i = 0; i < this.numBirds; ++i) {
            this.birdArray[i] = new GBird();
            this.root.getChildren().addAll(new Node[]{this.birdArray[i].GetCircle(), this.birdArray[i].GetIV()});
        }

        for(i = 0; i < this.numCats; ++i) {
            this.catArray[i] = new Cat();
            this.root.getChildren().addAll(new Node[]{this.catArray[i].GetCircle(), this.catArray[i].GetIV()});
        }

        for(i = 0; i < this.numRocks; ++i) {
            this.rockArray[i] = new Rock();
            this.root.getChildren().addAll(new Node[]{this.rockArray[i].GetCircle(), this.rockArray[i].GetIV()});
        }

        for(i = 0; i < this.numTrees; ++i) {
            this.treeArray[i] = new Tree();
            this.root.getChildren().addAll(new Node[]{this.treeArray[i].GetCircle(), this.treeArray[i].GetIV()});
        }

    }

    public void SetPopSize() {
    }

    public void ReviveCreature(String lifeF) {
        boolean isRevive;
        double rand;
        if (lifeF == "bug" && this.lifeArray[0] < this.numBugs - 1) {
            isRevive = true;

            while(isRevive) {
                rand = Math.random() * (double)this.numBugs;
                if (!this.bugArray[(int)rand].isAlive) {
                    isRevive = false;
                    this.bugArray[(int)rand].isAlive = true;
                    this.bugArray[(int)rand].deltaX = 0.0F;
                    this.bugArray[(int)rand].deltaY = 0.0F;
                    this.bugArray[(int)rand].energy = 15;
                    this.root.getChildren().add(this.bugArray[(int)rand].GetIV());
                }
            }
        }

        if (lifeF == "bird" && this.lifeArray[1] < this.numBirds - 1) {
            isRevive = true;

            while(isRevive) {
                rand = Math.random() * (double)this.numBirds;
                if (!this.birdArray[(int)rand].isAlive) {
                    isRevive = false;
                    this.birdArray[(int)rand].isAlive = true;
                    this.birdArray[(int)rand].deltaX = 0.0F;
                    this.birdArray[(int)rand].deltaY = 0.0F;
                    this.birdArray[(int)rand].energy = 25;
                    this.root.getChildren().add(this.birdArray[(int)rand].GetIV());
                }
            }
        }

    }

    public void KillCreature(String lifeF) {
        boolean isRevive;
        double rand;
        if (lifeF == "bug" && this.lifeArray[0] > 0) {
            isRevive = true;

            while(isRevive) {
                rand = Math.random() * (double)this.numBugs;
                if (this.bugArray[(int)rand].isAlive) {
                    isRevive = false;
                    this.bugArray[(int)rand].isAlive = false;
                    this.root.getChildren().remove(this.bugArray[(int)rand].GetIV());
                }
            }
        }

        if (lifeF == "bird" && this.lifeArray[1] > 0) {
            isRevive = true;

            while(isRevive) {
                rand = Math.random() * (double)this.numBirds;
                if (this.birdArray[(int)rand].isAlive) {
                    isRevive = false;
                    this.birdArray[(int)rand].isAlive = false;
                    this.root.getChildren().remove(this.birdArray[(int)rand].GetIV());
                }
            }
        }

    }
}
