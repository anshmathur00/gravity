package com.company;

import java.util.ArrayList;
import java.util.List;

public class Force {
    private double distance;
    private double magnitude;
    private List<Double> firstLoc;
    private List<Double> secondLoc;

    //This is the only intrinsic value that needs to exist. This is the value in Newtons of the force in the x,(y),(z)
    // dirs
    private List<Double> fcompList;
    private double firstMass;
    private double secondMass;
   // protected final double GRAVITATIONAL_CONSTANT = 0.0000000000667;
    protected final double GRAVITATIONAL_CONSTANT = 1;

    public Force(object a, object b){
        firstMass = a.getMass();
        secondMass = b.getMass();
        firstLoc = a.getLocation();
        secondLoc = b.getLocation();
        fcompList = calcFComp();
    }

    public Force(List<Double> fMagList){
        fcompList = new ArrayList<>();
        fcompList.addAll(fMagList);
    }


    public List<Double> subtractVectors(List<Double> v1, List<Double> v2){
        List<Double> returnVec = new ArrayList<>();
        Double magAdd = 0.0;
        for (int i = 0; i < v1.size(); i++){
            Double j  = v2.get(i) - v1.get(i);
            //This adds the squares of the vec component to get the distance
            magAdd += Math.pow(j,2);
            returnVec.add(j);
        }
        //kill two birds with one stone, get the magnitude out of the way here.
        distance = Math.sqrt(magAdd);
        magnitude = (GRAVITATIONAL_CONSTANT*firstMass*secondMass)/(Math.pow(distance,firstLoc.size()-1));
        return returnVec;
    }

    //Using the ratio of the mag of the subtracted distance vector to the mag of the force
    //the force vector is calculated for each component.
    private List<Double> calcFComp(){
        List<Double> returnComp = new ArrayList<>();
        List<Double> subtractedVec = subtractVectors(firstLoc,secondLoc);
        double ratio = (magnitude/distance);
        for (double i: subtractedVec){
            returnComp.add(i*ratio);
        }
        return returnComp;
    }
    public List<Double> getFComp(){
        return fcompList;
    }

}
