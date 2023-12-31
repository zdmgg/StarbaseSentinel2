/********************
 *
 * This is the representation of a bullet within the game.
 * It contains code for moving toward the enemy it was originally aimed at.
 *
 * @author: Zach Dwayne Glindro, Kervin Ralph Samson
 * @date: 2023-12-21 11:59
 *
 *
 */

package com.sentinel.starbasesentinel.towers;

import com.sentinel.starbasesentinel.Grid;
import com.sentinel.starbasesentinel.enemies.Enemy;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bullet {
    private final Image image;
    private Point2D position;
    private final Enemy target;
    private final double speed;
    private boolean markForDeletion;

    private int damage;

    public Bullet(Point2D position, Enemy target, int damage) {
        this.position = position; // is initially the starting position, but is moved by this.move()
        this.target = target; // enemy to shoot

        this.speed = 0.5;
        this.image = new Image("file:src/main/resources/towers/bulletbasic.gif");
        this.markForDeletion = false;
        this.damage = damage;
    }

    // Moves a bullet based on its current location to its target enemy
    private void move() {
        if (position.distance(target.getPosition()) < 1) {
            return;
        }

        double angle = Math.atan2(target.getPosition().getY() - position.getY(),
                target.getPosition().getX() - position.getX());

        position = position.add(speed * Math.cos(angle), speed * Math.sin(angle));
    }

    // Mark this bullet for deletion
    public boolean isMarkedForDeletion() {
        return markForDeletion;
    }

    public void update() {
        move();

        if (position.distance(target.getPosition()) < 1) {
            markForDeletion = true;
            target.updateHealth((-1)*damage);
        }
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, position.getX()- Grid.translateToGrid(0.5), position.getY()-Grid.translateToGrid(0.5));
    }
}
