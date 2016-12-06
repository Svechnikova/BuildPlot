package com.example.tatyana.buildplot.graph;

import com.example.tatyana.buildplot.Constants;

/**
 * Class for coordinates X and Y
 */
public class Point {
    private Double x, y;

    public Point(){
        x = Constants.NULL_COORDINATE;
        y = Constants.NULL_COORDINATE;
    }

    public Point(Double x, Double y){
        this.x = x;
        this.y = y;
    }

    // Get
    public Double getX(){
        return x;
    }
    public Double getY(){
        return y;
    }

    // Set from String
    public void setX(String x){
        try {
            this.x = Double.parseDouble(x);
        } catch (NumberFormatException e) {
            // X did not contain a valid double
            System.out.println("Could not parse " + e);
        }
    }
    public void setY(String y){
        try {
            this.y = Double.parseDouble(y);
        } catch (NumberFormatException e) {
            // Y did not contain a valid double
            System.out.println("Could not parse " + e);
        }
    }
    // Set from Double
    public void setX(Double x){
        this.x = x;
    }
    public void setY(Double y){
        this.y = y;
    }
}