/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uranus;

/**
 *
 * @author takina
 */
public class Intento {

    public Intento(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.error = (float)(100.0 - this.z);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getError() {
        return error;
    }

    public void setError(float error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Intento {" + "x=" + x + ", y=" + y + ", z=" + z + ", error=" + error + '}';
    }

    private float x;
    private float y;
    private float z;
    private float error;

}