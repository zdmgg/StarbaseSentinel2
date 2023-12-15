package com.sentinel.starbasesentinel.levels;

import com.sentinel.starbasesentinel.enemies.Enemy;
import com.sentinel.starbasesentinel.towers.Bullet;
import com.sentinel.starbasesentinel.towers.Tower;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class Level {
    protected final static int WIDTH = 1280;
    protected final static int HEIGHT = 720;
    protected long startTime;
    protected Image bg;
    protected ArrayList<Enemy> enemies;
    protected ArrayList<Tower> towers;

    public Level(Image bg) {
        this.startTime = System.currentTimeMillis();
        this.bg = bg;

        this.enemies = new ArrayList<>();
        this.towers = new ArrayList<>();
    }

    public abstract void render(GraphicsContext gc);
}
