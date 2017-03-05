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
public class Cupcake {
    private int cupcakeid;
    private int toppingid;
    private int bottomid;
    private String toppingname;
    private String bottomname;
    private int cartid;
    private int ccprice;
    private String status;
    
    public Cupcake(int ccakeid, int topid, int botid, String topname, String botname, int cartid, int price, String status)
    {
        this.cupcakeid = ccakeid;
        this.toppingid = topid;
        this.bottomid = botid;
        this.toppingname = topname;
        this.bottomname = botname;
        this.cartid = cartid;
        this.ccprice = price;
        this.status = status;
    }

    /**
     * @return the cupcakeid
     */
    public int getCupcakeid() 
    {
        return cupcakeid;
    }

    /**
     * @return the toppingid
     */
    public int getToppingid() 
    {
        return toppingid;
    }

    /**
     * @return the bottomid
     */
    public int getBottomid() 
    {
        return bottomid;
    }

    /**
     * @return the cartid
     */
    public int getCartid() 
    {
        return cartid;
    }

    /**
     * @return the status
     */
    public String getStatus() 
    {
        return status;
    }

    /**
     * @return the ccprice
     */
    public int getCcprice() 
    {
        return ccprice;
    }
    
    @Override
    public String toString() 
    {
        return "Cupcake{" + "cupcakeid=" + cupcakeid + ", toppingid=" + toppingid + ", price= " + ccprice + '}';
    }

    /**
     * @return the toppingname
     */
    public String getToppingname() 
    {
        return toppingname;
    }

    /**
     * @return the bottomname
     */
    public String getBottomname() 
    {
        return bottomname;
    }
    
}
