package com.sentinel.starbasesentinel;

import com.sentinel.starbasesentinel.levels.Level;
import com.sentinel.starbasesentinel.towers.Plot;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameTimer extends AnimationTimer {
    private final Level level;
    private Scene scene;
    private final GraphicsContext gc;
    private final Player player;

    public GameTimer(Level level, Scene scene, GraphicsContext gc) {
        this.level = level;
        this.scene = scene;
        this.gc = gc;
        this.player = new Player();
    }

    @Override
    public void handle(long l) {
        level.update(player);
        level.render(gc);
        this.handleMouseEvent();
        level.checkEnemiesInfiltrated(player);

        if (player.getEnemiesInfiltrated() >= Player.getMaxEnemiesInfiltrated()) {
            player.setGameOver("lost");
            this.stop();
        }

        if (player.won() || player.lost()) {
            this.stop();
        }
    }

    // Logic for placing of towers
//    private void handleMouseEvent() {
//        ArrayList<Plot> plots = level.getPlots();
//
//        scene.setOnMouseClicked(mouseEvent -> {
//            double x = mouseEvent.getX();
//            double y = mouseEvent.getY();
//
//            for (Plot plot : plots) {
//                if (plot.clicked(x, y) && !plot.isOccupied() && player.getCoins() >= 100) {
//                    double plotX = plot.getGridPosition().getX();
//                    double plotY = plot.getGridPosition().getY();
//                    player.removeCoins(100);
//
//                    level.addTower(plotX, plotY);
//                    plot.markAsOccupied();
//                }
//            }
//        });
//    }
    // left-click add basic tower; right-click add speed tower
    private void handleMouseEvent() {
        ArrayList<Plot> plots = level.getPlots();

        scene.setOnMousePressed(mouseEvent -> { // ginawa kong onMousePressed imbis na mouseclicked kasi sa mouseclicked kelangan pa irelease yung press
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();

            if (mouseEvent.isPrimaryButtonDown()) {  // Left-click
                for (Plot plot : plots) {
                    if (plot.clicked(x, y) && !plot.isOccupied() && player.getCoins() >= 100) {
                        double plotX = plot.getGridPosition().getX();
                        double plotY = plot.getGridPosition().getY();
                        player.removeCoins(100);

                        level.addTower(plotX, plotY);  // Add basic tower
                        plot.markAsOccupied();
                    }
                }
            } else if (mouseEvent.isSecondaryButtonDown()) {  // Right-click
                for (Plot plot : plots) {
                    if (plot.clicked(x, y) && !plot.isOccupied() && player.getCoins() >= 150) {
                        double plotX = plot.getGridPosition().getX();
                        double plotY = plot.getGridPosition().getY();
                        player.removeCoins(150);

                        level.addSpeedTower(plotX, plotY);  // Add speed tower
                        plot.markAsOccupied();
                    }
                }
            }
        });
    }



    public Player getPlayer() {
        return this.player;
    }

    public void stop() {
        super.stop();
        MainMenu mainMenu = new MainMenu();
        if (player.won()) {
            MainMenu.showEndScreen("You won!");
        } else if (player.lost()) {
            MainMenu.showEndScreen("You lose.");
        }
    }
}
