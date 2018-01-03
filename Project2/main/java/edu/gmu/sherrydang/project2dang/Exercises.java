package edu.gmu.sherrydang.project2dang;

/**
 * Created by SherryDang on 10/30/2017.
 * Exercise object class
 */


public class Exercises {
    int _id;
    String _name;
    String _weight;
    String _reps;
    String _sets;
    String _note;

    public Exercises (){

    }

    public Exercises (int id, String name, String weight, String reps, String sets, String note){
        this._id = id;
        this._name = name;
        this._weight = weight;
        this._reps = reps;
        this._sets = sets;
        this._note = note;

    }

    public Exercises (String name, String weight, String reps, String sets, String note){
        this._name = name;
        this._weight = weight;
        this._reps = reps;
        this._sets = sets;
        this._note = note;
    }

    public int getID(){
        return this._id;
    }

    public void setID (int id){
        this._id = id;
    }

    public String getName (){

        return this._name;
    }
    public void setName (String name)
    {
        this._name = name;
    }

    public String getWeight ()
    {
        return this._weight;
    }
    public void setWeight (String weight){

        this._weight = weight;
    }

    public String getReps (){
        return this._reps;
    }

    public void setReps (String reps)
    {
        this._reps = reps;
    }

    public String getSets (){

        return this._sets;
    }

    public void setSets (String sets){

        this._sets = sets;
    }

    public String getNote (){

        return this._note;
    }

    public void setNote (String note) {
        this._note = note;
    }

    public String toString(){
        return this._name;
    }
}

