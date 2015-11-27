package com.example.arpit.nm;

/**
 * Created by arpit on 26/11/15.
 */
public class Places {

    int id;
    double lat;
    double longl;
    double rate;
    String name;
    int total;

    public Places(int id,double lat,double longl,double rate, String name,int total){
        this.id=id;
        this.lat=lat;
        this.longl=longl;
        this.rate=rate;
        this.name=name;
        this.total=total;
    }
    public Places(){

    }
    public double getLat()
    {
        return this.lat;
    }
    public double getLongl()
    {
        return this.longl;
    }
    public int getId()
    {
        return this.id;
    }
    public double getRate()
    {
        return  this.rate;
    }
    public String getName()
    {
        return this.name;
    }
    public int getTotal()
    {
        return  this.total;
    }
   public void setId(int id)
   {
       this.id=id;
   }
    public void setLat(double lat)
    {
        this.lat=lat;
    }
    public void setLongl(double longl)
    {
        this.longl=longl;
    }
    public void setRate(double rate)
    {
        this.rate=rate;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public void setTotal(int total)
    {
        this.total=total;
    }

}
