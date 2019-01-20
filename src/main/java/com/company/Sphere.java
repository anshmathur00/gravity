package com.company;

import processing.core.PApplet;

/*
    only works for 3d
 */
public class Sphere extends PApplet {
    double x,y,z;
    private object planet;
    public Sphere(object planetIn){
        planet = planetIn;
    }

    public object getPlanet() {
        return planet;
    }

    public double getR() {
        return planet.getRadius();
    }

    /*
    public void move(){
        pushMatrix();
            x  = planet.getLocation().get(0);
            y  = planet.getLocation().get(1);
            z  = planet.getLocation().get(2);
            translate((float)x, (float)y, (float)z);
        popMatrix();
    }*/

    public static void main(String[] args){
       /* String s = "-5.5, 6.5, 7.5";
        double n = 0.0;
        Pattern pattern = Pattern.compile("-?[0-9]*[.][0-9]*");
        //Pattern pattern  = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        Matcher matcher = pattern.matcher(s);
        while(matcher.find()){
            double a = Double.valueOf(matcher.group());
            System.out.println(a);
            n += a;
        }
        System.out.println(n);*/

    }
}
