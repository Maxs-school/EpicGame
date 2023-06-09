package org.amorgugus;

import org.amorgugus.Utils.MathUtils;

import java.awt.*;

public class Player {
    private double x;
    private double y;
    private double angle;
    //angle ur facing in degrees
    private int speed;
    private Item heldItem;
    private Graphics graphics;
    public int health;


    public Player(double x, double y, double angle, Graphics g) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.speed = 2;
        this.heldItem = null;
        this.graphics = g;
        this.health = 100;
    }

    // What is your goal here?

    public int getHealth(){
        return this.health;
    }
    public Point getPoint(){
        return new Point(this.x, this.y);
    }

    // Added by max
    public double getAngle() {
        return this.angle;
    }

    public void setAngle(double angle) {this.angle = angle;}


    // Added by Max
    public Line getLine() {
        return new Line(new Point(this.x, this.y), this.angle, Consts.PLAYER_MAX_VIEW_DISTANCE);
    }

    // Also added by Max
    public Line getLine(double angle) {
        return new Line(new Point(this.x, this.y), angle, Consts.PLAYER_MAX_VIEW_DISTANCE);
    }

    // added by Max
    public Line getLine(double angle, double distance) {
        return new Line(new Point(this.x, this.y), angle, distance);
    }


    public void lookAt(Point point){
        this.angle = MathUtils.angleToPoint(this.x, this.y, point.getX(), point.getY());
    }

    public void move(double forwardDist, double sideDist, Drawable[] walls) {

        // Max helped here
        // Collision detection added by Max
        double xcomp = MathUtils.degreeCos(this.angle)*forwardDist*speed;
        double ycomp = MathUtils.degreeSin(this.angle)*forwardDist*speed;

        xcomp += MathUtils.degreeCos(this.angle+90)*sideDist*speed;
        ycomp += MathUtils.degreeSin(this.angle+90)*sideDist*speed;

        Line movingLine = new Line(new Point(this.x, this.y), new Point(this.x +  xcomp, this.y + ycomp));

        if (Consts.DEBUG_RENDERING) {
            this.graphics.setColor(Color.RED);
            movingLine.draw(this.graphics);
        }
        Point intersect = MathUtils.doesIntersectWall(walls, movingLine);

        if (intersect == null) {
            this.x = this.x + xcomp;
            this.y = this.y + ycomp;
        }
    }

    public void render() {
        this.graphics.setColor(Color.GREEN);
        this.graphics.fillOval((int) this.x-5, (int) this.y-5, 10, 10);
        // Line class code done my Max
        Line line = this.getLine();
        line.draw(this.graphics);
    }

}

