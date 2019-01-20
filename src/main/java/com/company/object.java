package com.company;


import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class object {

    private ArrayList<Double> locationArr;
    private double mass;
    private ArrayList<Double> velocityArr;
    private ArrayList<Double> accArr;
    private float radius;

    /**
     * Best constructor out of all. Input the mass, radius, locs, and vels into an xlsx. Row num tells
     * which row each one has with it.
     * @param rowNum
     */
    public object(int rowNum){
        DataFormatter dataFormatter = new DataFormatter();
        locationArr = new ArrayList<>();
        velocityArr = new ArrayList<>();
        accArr =  new ArrayList<>();
        try{
            String PATH = "/Users/AnshulMathur/Documents/Java_Workspace/inputFolder/input.xlsx";
            Workbook wb = WorkbookFactory.create(new File(PATH));
            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.getRow(rowNum-1);
            List<String> inputs = new ArrayList<>();
            for (Cell cell: row){
                inputs.add(dataFormatter.formatCellValue(cell));
            }
            System.out.println(inputs.get(0).substring(1,inputs.get(0).length()-1));
            mass = (Double.valueOf(inputs.get(0).substring(1,inputs.get(0).length()-1)));
            radius = (Float.valueOf(inputs.get(1).substring(1,inputs.get(1).length()-1)));
            Pattern pattern  = Pattern.compile("-?[0-9]*[.][0-9]*");
            String locCell = inputs.get(2);
            Matcher matcher = pattern.matcher(locCell);
            while(matcher.find()){
                double locVal = Double.valueOf(matcher.group());
                locationArr.add(locVal);
            }

            String velCell = inputs.get(3);
            matcher = pattern.matcher(velCell);
            while(matcher.find()){
                velocityArr.add(Double.valueOf(matcher.group()));
            }

            for (int i = 0; i < 3; i++){accArr.add(0.0);}
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Dim and radius controlled. Acc and Vel set to 0. Mass dependent on R.
     * @param dim
     * @param r
     */
    public object(int dim, float r){                    //testValue here
        locationArr = new ArrayList<>();
        velocityArr = new ArrayList<>();
        accArr =  new ArrayList<>();
        Random gen = new Random();
        for (int i = 0; i < dim; i++){
            //double value = gen.nextInt(800)+100;
         //   double value = testValue;                              //testValue here
            //locationArr.add(value);
            accArr.add(0.0);
            velocityArr.add(0.0);
        }
        double ht = gen.nextInt(800);
        double wth = gen.nextInt(1400);
        radius = r;
        locationArr.add(wth+100);
        locationArr.add(ht+100);
        locationArr.add(0.0);
        mass = ((4/3)*3.14159*(Math.pow(radius,3)));
        //mass = (long)Math.pow(10,11);
    }

    /**
     * Mass controlled. Dim controlled. Loc controlled. Acc and Vel set to 0
     * @param dim
     * @param massVal
     * @param locVal
     */
    public object(int dim, double massVal, ArrayList<Double> locVal, float r){                    //testValue here
        locationArr = new ArrayList<>();
        velocityArr = new ArrayList<>();
        accArr =  new ArrayList<>();
        for (int i = 0; i < dim; i++){
            double value = locVal.get(i);
            //   double value = testValue;                              //testValue here
            locationArr.add(value);
            accArr.add(0.0);
            velocityArr.add(0.0);
        }
        mass = massVal;
        radius = r;
        //mass = (long)Math.pow(10,11);
    }


    /**
     * Mass controlled, radius controlled. Acc and vel set to 0. Location random.
     * @param r radius
     * @param dim dimension
     * @param massVal mass of object
     */
    public object(int dim, double massVal, float r){                    //testValue here
        locationArr = new ArrayList<>();
        velocityArr = new ArrayList<>();
        accArr =  new ArrayList<>();
        Random gen = new Random();
        for (int i = 0; i < dim; i++){
            //double value = gen.nextInt(800)+100;
            //   double value = testValue;                              //testValue here
            //locationArr.add(value);
            accArr.add(0.0);
            velocityArr.add(0.0);
        }
        double ht = gen.nextInt(800);
        double wth = gen.nextInt(1400);
        double z = gen.nextInt(100);
        radius = r;
        locationArr.add(wth+100);
        locationArr.add(ht+100);
        locationArr.add(z);
        mass = massVal;
        //mass = (long)Math.pow(10,11);
    }

    float getRadius(){return radius;}

    void printProps(){
        System.out.println("-----------------------------------------------------------");
        System.out.println("radius: "+radius);
        System.out.println("-----------------------------------------------------------");
        System.out.println("\nlocationArr: "+locationArr+"\n"+"mass: "+mass);
        System.out.println("accArr: "+accArr);
        System.out.println("velArr: "+velocityArr);
    }

    double getMass(){return mass;}

    public void setVelocityArr(double in){
        velocityArr.set(1, in);
    }

    public ArrayList<Double> getVelocity(){return velocityArr;}
    public ArrayList<Double> getAccArr(){return accArr;}
    public void clearAccArr(){
        for (int i = 0; i < 3; i++){
            accArr.set(i,0.0);
        }
    }

    ArrayList<Double> getLocation(){return locationArr;}

    void updateAccArr(Force netForce){
        for (int i = 0; i < locationArr.size(); i++) {
            double newAcc = ((netForce.getFComp().get(i))/mass)+ accArr.get(i) ;
            accArr.set(i, newAcc);
        }
    }

    //set arr, vel, new post, loc
    void move(){
        for (int i = 0; i < locationArr.size(); i++) {
            double acc = accArr.get(i);
            double timeStep = 0.01;
            double newPos = locationArr.get(i) + (velocityArr.get(i)* timeStep) + (0.5 * (Math.pow(timeStep, 2)) * acc);
            locationArr.set(i, newPos);
            velocityArr.set(i, velocityArr.get(i)+(accArr.get(i) * timeStep));
        }
    }
}
