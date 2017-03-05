/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.buscupcake.Modeller;

/**
 *
 * @author Sean
 */
public class Item {
    private int id;
    private String name;
    private int price;
    private String type;
    
    public Item(int id, String name, int price, String type)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }
        
    public int getid()
    {
        return id;
    }
    
    public String getname()
    {
        return name;
    }
    
    public int getprice()
    {
        return price;
    }
    
    public String gettype()
    {
        return type;
    }
    
    @Override
    public String toString() 
    {
        return "Item{" + "ID=" + id + ", name=" + name + ", price= " + price + ", type= " + type + '}';
    }
}
