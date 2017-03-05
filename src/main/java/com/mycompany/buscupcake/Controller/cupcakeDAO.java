/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.buscupcake.Controller;

import com.mycompany.buscupcake.Modeller.Cupcake;
import com.mycompany.buscupcake.Modeller.Item;
import com.mycompany.buscupcake.Modeller.Purchase;
import com.mycompany.buscupcake.Modeller.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sean
 */
public class cupcakeDAO {
    private final DBConnector conn;
    
    public cupcakeDAO () throws Exception
    {
        this.conn = new DBConnector();
    }
    
//    returns arraylist of cupcake toppings
    public List<Item> getToppings() throws SQLException
    {
        Statement stmt = conn.getConnection().createStatement();
        List<Item> toppings = new ArrayList<>();
        String sql = "SELECT * FROM item\n" +
            "WHERE type = 'topping';";
        Item item = null;
        try 
        {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) 
            {
                int itid = rs.getInt("itemid");
                String itname = rs.getString("name");
                int itprice = rs.getInt("price");
                String ittype = rs.getString("type");
                item = new Item(itid, itname, itprice, ittype);
                toppings.add(item); 
            }    
        }     
        catch (SQLException ex) 
        {
            Logger.getLogger(cupcakeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return toppings;
    }
    
//    returns arraylist of cupcake bottoms
    public List<Item> getBottoms() throws SQLException
    {
        Statement stmt = conn.getConnection().createStatement();
        List<Item> bottoms = new ArrayList<>();
        String sql = "SELECT * FROM item\n" +
        "WHERE type = 'bottom';";
        Item item = null;
        try 
        {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) 
            {
                int itid = rs.getInt("itemid");
                String itname = rs.getString("name");
                int itprice = rs.getInt("price");
                String ittype = rs.getString("type");
                item = new Item(itid, itname, itprice, ittype);
                bottoms.add(item); 
            }    
        }     
        catch (SQLException ex) 
        {
            Logger.getLogger(cupcakeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bottoms;
    }
    
//    returns list with users cupcakes objects currently pending
    public List<Cupcake> getPendingCupcakes(int userid) throws SQLException
    {
        Statement stmt = conn.getConnection().createStatement();
        List<Cupcake> cupcakes = new ArrayList<>();
        String sql = "SELECT *, I1.price + I2.price as ccprice, I1.name as topname, I2.name as botname\n" +
        "from cart \n" +
        "left join cupcake\n" +
        " on cart.cupcakeid = cupcake.cupcakeid\n" +
        " left join item I1 \n" +
        " on I1.itemid = cupcake.toptype\n" +
        " left join item I2\n" +
        " on I2.itemid = cupcake.bottomtype\n" +
        " WHERE userid = "+userid+"\n" +
        " AND status = 'Pending';";

        Cupcake cupcake = null;
        try 
        {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) 
            {
                int ccakeid = rs.getInt("cupcakeid");
                int toppingid = rs.getInt("toptype");
                int bottomid = rs.getInt("bottomtype");
                String topname = rs.getString("topname");
                String botname = rs.getString("botname");
                int cartid = rs.getInt("cartid");
                int ccprice = rs.getInt("ccprice");
                String status = rs.getString("status");
                cupcake = new Cupcake(ccakeid, toppingid, bottomid, topname, botname, cartid, ccprice, status);
                cupcakes.add(cupcake); 
            }    
        }     
        catch (SQLException ex) 
        {
            Logger.getLogger(cupcakeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cupcakes;
    }
    
//    Creates amt of cupcake record(s) and gives cupcake id to cart to create record each loop
    public void createCupcakeRecord(int topid, int botid, int amt, int userid)
    {
     Statement statement = null;
     int genKey = 0;
        String insertTableSQL = "insert into cupcake values\n" +
        "  (NULL, CURDATE(), "+topid+", "+botid+");";
        for (int i = 0; i < amt; i++)
        {
            try
            {
                statement = conn.getConnection().createStatement();
                System.out.println(insertTableSQL);
                // execute insert SQL stetement
                statement.executeUpdate(insertTableSQL, Statement.RETURN_GENERATED_KEYS);
                System.out.println("Record is inserted into Cupcake table!");
                     try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                genKey = (int) generatedKeys.getLong(1);
            }
            
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
            createCartRecord(genKey, userid);
        }
            } catch (SQLException e) {
                    System.out.println(e.getMessage());
            }    
        }
    }
    
//    Create a cart record
    public void createCartRecord(int cupcakeid, int userid)
    {
        Statement statement = null;
        String insertTableSQL = "insert into cart values\n" +
        "  (NULL, "+userid+", "+cupcakeid+", 'Pending');";
        try {
                statement = conn.getConnection().createStatement();
                System.out.println(insertTableSQL);
                // execute insert SQL stetement
                statement.executeUpdate(insertTableSQL);
                System.out.println("Record is inserted into Cart table!");
        } catch (SQLException e) {
                System.out.println(e.getMessage());
        } 
    }
    
//    Set cart record status to aborted
    public void setCartStatusAborted(int cartid)
    {
        Statement statement = null;
        String insertTableSQL = "UPDATE cart "
                        + "SET status = 'Aborted' "
                        + "WHERE cartid = "+cartid;
        try {
		statement = conn.getConnection().createStatement();
		System.out.println(insertTableSQL);
		// execute insert SQL stetement
		statement.executeUpdate(insertTableSQL);
		System.out.println("Changed cart status to aborted");
	} catch (SQLException e) 
        {
            System.out.println(e.getMessage());
	} 
    }
            
//    Set all cart records to aborted
    public void setAllCartStatusAborted(int userid)
    {
        Statement statement = null;
        String insertTableSQL = "UPDATE cart "
                        + "SET status = 'Aborted' "
                        + "WHERE userid = "+userid
                        + " AND status = 'Pending'";
        try {
		statement = conn.getConnection().createStatement();
		System.out.println(insertTableSQL);
		// execute insert SQL stetement
		statement.executeUpdate(insertTableSQL);
		System.out.println("Changed all pending cart status to aborted");
	} catch (SQLException e) 
        {
            System.out.println(e.getMessage());
	} 
    }
    
//    Set cart record status to purchased
    public void setCartStatusPurchased(int cartid)
    {
        Statement statement = null;
        String insertTableSQL = "UPDATE cart "
                        + "SET status = 'Purchased' "
                        + "WHERE cartid = "+cartid;
        try {
		statement = conn.getConnection().createStatement();
		System.out.println(insertTableSQL);
		// execute insert SQL stetement
		statement.executeUpdate(insertTableSQL);
		System.out.println("Changed cart status to purchased");
	} catch (SQLException e) 
        {
            System.out.println(e.getMessage());
	} 
    }
    
//    Set all cart records purchased
    public void setAllCartStatusPurchased(int userid)
    {
        Statement statement = null;
        String insertTableSQL = "UPDATE cart "
                        + "SET status = 'Purchased' "
                        + "WHERE userid = "+userid
                        + " AND status = 'Pending'";
        try {
		statement = conn.getConnection().createStatement();
		System.out.println(insertTableSQL);
		// execute insert SQL stetement
		statement.executeUpdate(insertTableSQL);
		System.out.println("Changed all pending cart status to aborted");
	} catch (SQLException e) 
        {
            System.out.println(e.getMessage());
	} 
    }
    
//    Purchase cupcakes in cart: Purchases items, Returns array of newly purchased items,
//    creates purchase and purchasecupcake record in DB.
    public List<Cupcake> purchaseCart(int userid) throws SQLException
    {
        
        List<Cupcake> pendingCCakes = getPendingCupcakes(userid);
        if (pendingCCakes.size() > 0)
        {
            int pendingcost = 0;
            for (Cupcake d : pendingCCakes)
            {
                pendingcost += d.getCcprice();
            }
            if (getUserCredit(userid) >= pendingcost)
            {
                int purchaseid = createPurchase(userid);
                for (Cupcake c : pendingCCakes)
                {
                    createPurchaseCupcake(purchaseid, c.getCupcakeid());
                    removeUserCredit(userid, c.getCcprice());
                }
                setAllCartStatusPurchased(userid);
            }
            else
            {
                System.out.println("Not enough userbalance for this purchase");
            }
        }
        else
        {
            System.out.println("Nothing in cart");
        }
        return pendingCCakes;
    }
    
    // gets total cost in receipt
    public int receiptCost(int userid) throws SQLException
    {
        int totalcost= 0;
        List<Cupcake> pendingCCakes = getPendingCupcakes(userid);
        for (Cupcake d : pendingCCakes)
            {
                totalcost += d.getCcprice();
            }
        return totalcost;
    }
    
//    Creates new purchase for user, returns purchase id
    public int createPurchase(int userid)
    {
        int genKey = 0;
        Statement statement = null;
        String insertTableSQL = "INSERT INTO purchase "
                        + "VALUES"
                        + "(NULL, "+userid+", CURDATE())";
        try {
                statement = conn.getConnection().createStatement();
                System.out.println(insertTableSQL);
                // execute insert SQL stetement
                statement.executeUpdate(insertTableSQL, Statement.RETURN_GENERATED_KEYS);
                System.out.println("Record is inserted into Purchase table!");
                try (ResultSet generatedKeys = statement.getGeneratedKeys())
                {
                    if (generatedKeys.next()) 
                    {
                        genKey = (int) generatedKeys.getLong(1);
                    }

                    else 
                    {
                        throw new SQLException("Creating purchase failed, no ID obtained.");
                    }

                }
            } catch (SQLException e) {
                    System.out.println(e.getMessage());
            } 
        return genKey;
    }
    
//    Create purchasecupcake record
    public void createPurchaseCupcake(int purchaseid, int cupcakeid)
    {
        Statement statement = null;
        String insertTableSQL = "INSERT INTO purchasecupcake "
                        + "VALUES"
                        + "("+purchaseid+", "+cupcakeid+")";
        try {
                statement = conn.getConnection().createStatement();
                System.out.println(insertTableSQL);
                // execute insert SQL stetement
                statement.executeUpdate(insertTableSQL);
                System.out.println("purchasecupcake record is inserted into purchasecupcake table!");
        } catch (SQLException e) {
                System.out.println(e.getMessage());
        } 
    }
    
//    Updates Users inhouse credit after purchase
    public void removeUserCredit(int userid, int amt)
    {
        Statement statement = null;
        String insertTableSQL = "UPDATE user "
                        + "SET balance = balance - "+amt+" "
                        + "WHERE userid = "+userid;
        try {
		statement = conn.getConnection().createStatement();
		System.out.println(insertTableSQL);
		// execute insert SQL stetement
		statement.executeUpdate(insertTableSQL);
		System.out.println("Removed "+amt+" from users balance");
	} catch (SQLException e) 
        {
            System.out.println(e.getMessage());
	} 
    }
    
    //    gets current credit amount in user account
    public int getUserCredit(int userid) throws SQLException
    {
        Statement stmt = conn.getConnection().createStatement();
        String sql = "select * from user where userid = "+userid;
        User user = null;
        int userbalance = 0;
        try 
        {
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
            {
                int balc = rs.getInt("balance");
                userbalance = balc;
            }
            } catch (SQLException ex) 
            {
                Logger.getLogger(loginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        return userbalance;
    }
    
    //    Returns all purchase history for user
    public List<Purchase> getPurchaseHistory(int userid) throws SQLException
    {
        Statement stmt = conn.getConnection().createStatement();
        List<Purchase> purchases = new ArrayList<>();
        String sql = "SELECT *, I1.price + I2.price as ccprice, I1.name as topname, I2.name as botname\n" +
        "from purchasecupcake \n" +
        "left join purchase\n" +
        "on purchasecupcake.purchaseid = purchase.purchaseid\n" +
        " left join item I1 \n" +
        " on I1.itemid = purchasecupcake.cupcakeid\n" +
        " left join item I2\n" +
        " on I2.itemid = purchasecupcake.cupcakeid\n" +
        " WHERE userid = "+userid+";";

        Purchase purchase = null;
        try 
        {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) 
            {
                String pdate = rs.getString("purchasedate");
                String topname = rs.getString("topname");
                String botname = rs.getString("botname");
                int tcost = rs.getInt("ccprice");
                purchase = new Purchase(pdate, topname, botname, tcost );
                purchases.add(purchase); 
            }    
        }     
        catch (SQLException ex) 
        {
            Logger.getLogger(cupcakeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return purchases;
    }
}
