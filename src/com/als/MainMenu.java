package com.als;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.Component;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class MainMenu extends Application {
    Text aliveBugsText;
    Text aliveBirdsText;
    Text mapInfo;
    Text configText;
    Scene scene;
    Stage stage;
    Simulation sim;
    int screenHeight = 1000;
    int screenWidth = 800;
    private String[] configContent = new String[4];
    int numBugs = 50;
    int numVeg = 35;
    int numBirds = 15;
    int numObs = 8;
    FileHandler fileHandler = new FileHandler();
    private boolean isPause = true;
    private static int gridSize = 25;
    private boolean quitMenu = false;
    private boolean earlyTerminate = false;
    private boolean isShowInfo = false;
    private boolean isShowMapInfo = false;
    private boolean isShowConfig = false;
    int[] lifeArray = new int[2];
    static MainMenu menu;
    boolean simRunning = false;
    boolean isQuit = false;
    Service<Void> service = new Service<Void>() {
        protected Task<Void> createTask() {
            return new Task<Void>() {
                protected Void call() throws Exception {
                    while(!MainMenu.this.isQuit) {
                        if (MainMenu.this.isShowConfig) {
                            MainMenu.this.configText.setText("Max Bugs: " + MainMenu.this.numBugs + " Max Birds: " + MainMenu.this.numBirds + "\nNum Veg: " + MainMenu.this.numVeg + " Num Obs: " + MainMenu.this.numObs * 2);
                            MainMenu.this.configText.setLayoutX((double)(MainMenu.this.screenWidth / 2));
                            MainMenu.this.configText.setLayoutY((double)(MainMenu.this.screenHeight / 2));
                        } else {
                            MainMenu.this.configText.setText("\n");
                        }

                        while(MainMenu.this.simRunning) {
                            MainMenu.this.lifeArray = MainMenu.this.sim.CheckWhoIsAlive();
                            if (MainMenu.this.isShowInfo) {
                                MainMenu.this.aliveBugsText.setText("#Bugs Alive: " + MainMenu.this.lifeArray[0] + " / " + MainMenu.this.numBugs + "\n#BirdsAlive = " + MainMenu.this.lifeArray[1] + " / " + MainMenu.this.numBirds);
                                MainMenu.this.aliveBugsText.setLayoutX((double)(MainMenu.this.screenWidth / 2 - 400));
                                MainMenu.this.aliveBugsText.setLayoutY((double)(MainMenu.this.screenHeight / 2 - 100));
                            } else {
                                MainMenu.this.aliveBugsText.setText("\n");
                            }

                            if (MainMenu.this.isShowMapInfo) {
                                MainMenu.this.mapInfo.setText("Total Obstacles: " + MainMenu.this.numObs * 2);
                                MainMenu.this.mapInfo.setLayoutX((double)(MainMenu.this.screenWidth / 2 - 400));
                                MainMenu.this.mapInfo.setLayoutY((double)(MainMenu.this.screenHeight / 2 - 125));
                            } else {
                                MainMenu.this.mapInfo.setText("");
                            }
                        }
                    }

                    return null;
                }
            };
        }
    };

    public MainMenu() {
        try {
            this.SetConfig("C:/Developing/ALS_AOP/resources/config");
        } catch (FileNotFoundException var2) {
            var2.printStackTrace();
        }

        this.aliveBugsText = new Text(10.0D, 50.0D, "");
        this.mapInfo = new Text(10.0D, 50.0D, "");
        this.configText = new Text(10.0D, 50.0D, "");
    }

    public static void main(String[] args) {
        menu = new MainMenu();
        launch(args);
    }

    public void start(final Stage stage) {
        MenuBar menuBar = new MenuBar();
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10.0D);
        vbox.setPadding(new Insets(200.0D, 10.0D, 200.0D, 10.0D));
        stage.setTitle("Menu Sample");
        this.scene = new Scene(new VBox(), (double)this.screenWidth, (double)this.screenHeight);
        vbox.getChildren().addAll(new Node[]{this.aliveBugsText, this.mapInfo, this.configText});
        this.scene.setFill(Color.LIGHTGREEN);
        MenuItem open = new MenuItem("Open");
        open.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                String config = JOptionPane.showInputDialog((Component)null, "Please enter file name", "Open File", -1);

                try {
                    MainMenu.this.fileHandler.GetConfig(config);
                    JOptionPane.showMessageDialog((Component)null, "File loaded successfully", "Load Success", 0);
                } catch (FileNotFoundException var4) {
                    JOptionPane.showMessageDialog((Component)null, "File not found", "Error", 0);
                }

            }
        });
        MenuItem newC = new MenuItem("New Config");
        newC.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                MainMenu.this.numBugs = 1;
                MainMenu.this.numBirds = 1;
                MainMenu.this.numObs = 1;
                MainMenu.this.numVeg = 1;
            }
        });
        MenuItem save = new MenuItem("Save");
        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                MainMenu.this.fileHandler.SaveConfig(MainMenu.this.numBugs, MainMenu.this.numBirds, MainMenu.this.numVeg, MainMenu.this.numObs, "config");
                JOptionPane.showMessageDialog((Component)null, "Saved as default settings", "Save Success", 0);
            }
        });
        MenuItem saveAs = new MenuItem("Save As");
        saveAs.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                String config = JOptionPane.showInputDialog((Component)null, "Enter file name", "Save As", -1);
                MainMenu.this.fileHandler.SaveConfig(MainMenu.this.numBugs, MainMenu.this.numBirds, MainMenu.this.numVeg, MainMenu.this.numObs, config);
                JOptionPane.showMessageDialog((Component)null, "Saved as custom settings file: " + config, "Save Success", 0);
            }
        });
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        MenuItem displayConfig = new MenuItem("Display Config");
        displayConfig.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if (!MainMenu.this.simRunning) {
                    if (!MainMenu.this.isShowConfig) {
                        MainMenu.this.isShowConfig = true;
                    } else {
                        MainMenu.this.isShowConfig = false;
                    }
                }

            }
        });
        MenuItem editConfig = new MenuItem("Edit Config");
        editConfig.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                MainMenu.this.EditConfig();
                JOptionPane.showMessageDialog((Component)null, "Auto saved as default settings", "Save Success", 0);
            }
        });
        MenuItem togglePrint = new MenuItem("Toggle Lifeform Info");
        togglePrint.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if (!MainMenu.this.isShowInfo) {
                    MainMenu.this.isShowInfo = true;
                } else {
                    MainMenu.this.isShowInfo = false;
                }

            }
        });
        MenuItem mapInfo = new MenuItem("Show Map Info");
        mapInfo.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if (!MainMenu.this.isShowMapInfo) {
                    MainMenu.this.isShowMapInfo = true;
                } else {
                    MainMenu.this.isShowMapInfo = false;
                }

            }
        });
        MenuItem modLifeParam = new MenuItem("Modify Life Form Params");
        modLifeParam.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.out.println("Toggled bug scan distance");
            }
        });
        MenuItem decBugs = new MenuItem("Decrement Bugs");
        decBugs.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                MainMenu.this.sim.KillCreature("bug");
            }
        });
        MenuItem incBugs = new MenuItem("Increment Bugs");
        incBugs.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                MainMenu.this.sim.ReviveCreature("bug");
            }
        });
        MenuItem run = new MenuItem("Run");
        run.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if (!MainMenu.this.simRunning) {
                    MainMenu.this.sim = new Simulation(stage, MainMenu.this.scene, MainMenu.this.screenHeight, MainMenu.this.screenWidth, MainMenu.this.numBugs, MainMenu.this.numBirds, MainMenu.this.numVeg, MainMenu.this.numObs);
                    MainMenu.this.simRunning = true;
                }

            }
        });
        MenuItem pause = new MenuItem("Pause / Resume");
        pause.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if (MainMenu.this.simRunning) {
                    if (!MainMenu.this.sim.isPause) {
                        MainMenu.this.sim.isPause = true;
                    } else {
                        MainMenu.this.sim.isPause = false;
                    }
                }

            }
        });
        MenuItem stop = new MenuItem("End Sim");
        stop.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if (MainMenu.this.simRunning) {
                    MainMenu.this.isShowInfo = false;
                    MainMenu.this.isShowMapInfo = false;
                    MainMenu.this.simRunning = false;
                    MainMenu.this.sim.ClearStack();
                    JOptionPane.showMessageDialog((Component)null, "Sim Over\n#Bugs Alive: " + MainMenu.this.lifeArray[0] + "\n#Birds Alive: " + MainMenu.this.lifeArray[1], "Sim Info", 0);
                }

            }
        });
        MenuItem appInfo = new MenuItem("Application");
        appInfo.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                JOptionPane.showMessageDialog((Component)null, "Life simulator v2 \nCurrent Status \nPred / Prey\nCats added", "Application Info", 0);
            }
        });
        MenuItem authorInfo = new MenuItem("Author");
        authorInfo.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                JOptionPane.showMessageDialog((Component)null, "Made by Misiuk Oleksandr 2020", "Author Info", 0);
            }
        });
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        Menu menuSim = new Menu("Simulation");
        Menu menuAbout = new Menu("About");
        menuFile.getItems().addAll(new MenuItem[]{newC, open, save, saveAs, exit, new SeparatorMenuItem()});
        menuView.getItems().addAll(new MenuItem[]{displayConfig, editConfig, togglePrint, mapInfo, new SeparatorMenuItem()});
        menuEdit.getItems().addAll(new MenuItem[]{modLifeParam, incBugs, decBugs, new SeparatorMenuItem()});
        menuSim.getItems().addAll(new MenuItem[]{run, pause, stop, new SeparatorMenuItem()});
        menuAbout.getItems().addAll(new MenuItem[]{appInfo, authorInfo, new SeparatorMenuItem()});
        menuBar.getMenus().addAll(new Menu[]{menuFile, menuView, menuEdit, menuSim, menuAbout});
        ((VBox)this.scene.getRoot()).getChildren().addAll(new Node[]{menuBar, vbox});
        stage.setScene(this.scene);
        stage.show();
        this.service.start();
    }

    public int GetGridSize() {
        return gridSize;
    }

    public void NewConfig() {
    }

    public void EditConfig() {
        boolean exit = false;

        String config;
        int configVal;
        while(!exit) {
            config = JOptionPane.showInputDialog((Component)null, "Please enter Max number of bugs < 100", "Edit Config", -1);
            configVal = Integer.parseInt(config);
            if (configVal < 100) {
                this.numBugs = configVal;
                exit = true;
            }
        }

        exit = false;

        while(!exit) {
            config = JOptionPane.showInputDialog((Component)null, "Please enter Max number of birds < 100", "Edit Config", -1);
            configVal = Integer.parseInt(config);
            if (configVal < 100) {
                this.numBirds = configVal;
                exit = true;
            }
        }

        exit = false;
        exit = false;

        while(!exit) {
            config = JOptionPane.showInputDialog((Component)null, "Please enter Max number of Vegetables < 100", "Edit Config", -1);
            configVal = Integer.parseInt(config);
            if (configVal < 100) {
                this.numVeg = configVal;
                exit = true;
            }
        }

        exit = false;

        while(!exit) {
            config = JOptionPane.showInputDialog((Component)null, "Please enter number of obstacles < 100", "Edit Config", -1);
            configVal = Integer.parseInt(config);
            if (configVal < 100) {
                this.numObs = configVal / 2;
                exit = true;
            }
        }

        exit = false;
        this.fileHandler.SaveConfig(this.numBugs, this.numBirds, this.numVeg, this.numObs, "config");
    }

    public void RunSim() {
        this.service.reset();
        this.service.start();
    }

    public int GetNumBugs() {
        return this.numBugs;
    }

    public int GetNumBirds() {
        return this.numBirds;
    }

    public int GetNumVeg() {
        return this.numVeg;
    }

    public void SetConfig(String fileName) throws FileNotFoundException {
        this.configContent = this.fileHandler.GetConfig(fileName);
        this.numBugs = Integer.parseInt(this.configContent[0]);
        this.numBirds = Integer.parseInt(this.configContent[1]);
        this.numVeg = Integer.parseInt(this.configContent[2]);
        this.numObs = Integer.parseInt(this.configContent[3]);
    }
}
