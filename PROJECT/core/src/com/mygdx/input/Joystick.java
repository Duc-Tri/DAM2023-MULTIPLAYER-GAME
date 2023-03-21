package com.mygdx.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Joystick {
    Circle circle0, circle1;
    boolean positionFixe = false;
    int directionInput = 0;

    public Joystick(float x, float y, float radius) {
        circle0 = new Circle(x, y, radius);
        circle1 = new Circle(x, y, radius / 5.0f);
    }

    public void update(float x, float y) {
        float deltaX0 = x - circle0.x;
        float deltaY0 = y - circle0.y;
        double radius0 = Math.sqrt(deltaX0 * deltaX0 + deltaY0 * deltaY0);
        double deltaRadius0 = circle0.radius - radius0;
        if (deltaRadius0 < 0) {
            double ratio = circle0.radius / radius0;
            x = (float) (circle0.x + deltaX0 * ratio);
            y = (float) (circle0.y + deltaY0 * ratio);
        }
        circle1.setPosition(x, y);
        int tmpDirection;
        double deltaX = circle1.x - circle0.x;
        double deltaY = circle1.y - circle0.y;
        double distDeuxcentre = Math.pow(Math.pow(deltaX,2.0)+Math.pow(deltaY,2.0),1.0/2.0);
        double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
        if(distDeuxcentre < 50){
            directionInput=-1;
        }else {
            if (-45 < angle && angle < 45) {
                directionInput = Input.Keys.RIGHT;
            }
            else if (45 < angle && angle < 135) {
                directionInput = Input.Keys.UP;
            }
            else if (angle < -135 || angle > 135) {
                directionInput = Input.Keys.LEFT;
            }
            else if (-135 < angle && angle < -45) {
                directionInput = Input.Keys.DOWN;
            }
        }
    }

    public void render(ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);
        renderer.circle(circle0.x, circle0.y, circle0.radius);
        renderer.end();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.circle(circle1.x, circle1.y, circle1.radius);
        renderer.end();
    }

    public void setPosition(float x, float y) {
        circle0.setX(x);
        circle0.setY(y);
        circle1.setX(x);
        circle1.setY(y);
    }

    public boolean isPositionFixe() {
        return positionFixe;
    }

    public int getDirectionInput() {
        return directionInput;
    }


    private Circle getCircle0() {
        return circle0;
    }

    private void setCircle0(Circle circle0) {
        this.circle0 = circle0;
    }

    private Circle getCircle1() {
        return circle1;
    }

    private void setCircle1(Circle circle1) {
        this.circle1 = circle1;
    }

    private void setPositionFixe(boolean positionFixe) {
        this.positionFixe = positionFixe;
    }

    private void setDirectionInput(int directionInput) {
        this.directionInput = directionInput;
    }
}
