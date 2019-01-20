package com.company;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class Sketch extends PApplet{
    List<object> spheres = new ArrayList<>();
    /*double timeElapsed = 0;
    int l = 0;*/
    public void settings() {
        size(1500,900,P3D);
    }

    /**
     * Test setup.
     * TODO: Automatically take inputs from excel file. Add dialogue to visualisation.
     */
    public void setup(){
        spheres.add(new object(2));
        spheres.add(new object(3));
        spheres.add(new object(4));
       // spheres.add(new object(4));
        //spheres.add(new object(5));
        /*
        ArrayList<Double> loc1List = new ArrayList<>();
        loc1List.add(750.0);
        loc1List.add(450.0);
        loc1List.add(0.0);

        ArrayList<Double> loc2List = new ArrayList<>();
        loc2List.add(850.0);
        loc2List.add(450.0);
        loc2List.add(0.0);

        spheres.add(new object(3, 1000000, loc1List, (float) 50));
        spheres.add(new object(3, 1, loc2List, (float) 20));
        spheres.get(1).setVelocityArr(125.0);
     /*   spheres.add(new object(3, (float)50.0));
        spheres.add(new object(3, (float)30.0));*/
      //  spheres.add(new object(3, (float)20.0));
        /*List<Double> test1 = new ArrayList<>();
        test1.add(10.0);
        test1.add(0.0);
        test1.add(0.0);
        List<Double> test2 = new ArrayList<>();
        test2.add(-10.0);
        test2.add(0.0);
        test2.add(0.0);
        spheres.add(new object(3,10,test1));

        spheres.add(new object(3,10,test2));*/

    }

    /**
     * sums two forces
     * @param first
     * @param second
     * @return sum of the forces
     */
    public static Force netForce(Force first, Force second){
        List<Double> newForceMags = new ArrayList<>();
        for (int i = 0; i < first.getFComp().size(); i++){
            double sum = first.getFComp().get(i) + second.getFComp().get(i);
            newForceMags.add(sum);
        }
        return new Force(newForceMags);
    }

    /**
     * Multiples the component vec of input by -1.
     * @param force
     * @return Opposite force
     */
    static Force getOppositeForce(Force force){
        List<Double> reverseFMagList = new ArrayList<>();
        for (Double i: force.getFComp()){
            reverseFMagList.add(-1*i);
        }
        return new Force(reverseFMagList);
    }

    /**
     * Iterates through list of objects and computes the force between each one.
     * Updates the accArr of each object with the net Force.
     */

    public void updateAccs(){
        List<Double> unitList = new ArrayList<>();
        for (int i = 0; i < 3; i++){unitList.add(0.0);}
        for (int i = 0; i < spheres.size(); i++){
            spheres.get(i).clearAccArr();
        }

        for (int i = 0; i < spheres.size()-1; i++){
            Force newForce = new Force(unitList);
            for (int j = i+1; j < spheres.size(); j++){
                System.out.println("Force: ["+i+","+j+"]");
                Force forceBetweenIJ = new Force(spheres.get(i), spheres.get(j));
                newForce = netForce(newForce, forceBetweenIJ);
                spheres.get(j).updateAccArr(getOppositeForce(forceBetweenIJ));

            }
            System.out.println(i+" "+newForce.getFComp());
            spheres.get(i).updateAccArr(newForce);
        }
    }

    /**
     * Initiates all the calculations for the backend and updates all the sphere's property values.
     */
    public void movePlanets(){
        for (int i = 0; i < spheres.size(); i++){
            updateAccs();
            spheres.get(i).move();
        }
    }

    /**
     * Shows the spheres at a certain location.
     */
    void displaySphere(float x, float y, float z, float r){// sphere

        System.out.println(r);
        pushMatrix();
        translate(x, y, z);
        sphere(r);
        popMatrix();
    }

    public void draw(){
       /* if (l == 0){
            updateAccs();
            System.out.println("passed");
            l++;
        }*/
       /* timeElapsed += 0.01;
        System.out.println("timeElapsed: "+timeElapsed);*/
       /* updateAccs();
        spheres.get(0).printProps();
        spheres.get(1).printProps();*/

        for (int i = 0; i < spheres.size(); i++){
            object planet = spheres.get(i);
            float x  = (float)(double)planet.getLocation().get(0);
            float y  = (float)(double)planet.getLocation().get(1);
            float z  = (float)(double)planet.getLocation().get(2);
            spheres.get(i).printProps();
            displaySphere(x,y,z, spheres.get(i).getRadius());
            movePlanets();
        }
    }

    public static void main (String... args) {
        Sketch pt = new Sketch();
        PApplet.runSketch(new String[]{"ProcessingTest"}, pt);

    }
}
