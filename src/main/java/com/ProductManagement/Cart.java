package com.ProductManagement;

import com.DatabaseFunction.DBConnection;
import com.DatabaseFunction.QueryCart;

import java.sql.*;
import java.util.ArrayList;

public class Cart extends QueryCart {
  private int productID;
  private String productName;
  private float productPrice;
  private int productQty;
  private String productType;
  private int cartID;
  private float total;
  private float totalPrice;
  private ArrayList<Cart> cartProducts = new ArrayList<>();
  private DBConnection con = new DBConnection();
  private Statement statement;
  private PreparedStatement st;
  private Connection connection;

  public Cart() throws Exception {
    super("jdbc:mysql://localhost:3306/possys", "root", "");
    this.connection = con.getConnection("jdbc:mysql://localhost:3306/possys", "root", "");
    this.statement = connection.createStatement();
  }

  // setter

  public void setTotalPrice(float totalPrice) {
    this.totalPrice = totalPrice;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public void setProductPrice(float productPrice) {
    this.productPrice = productPrice;
  }

  public void setProductQty(int productQty) {
    this.productQty = productQty;
  }

  public void setCartID(int cartID) {
    this.cartID = cartID;
  }

  public void setProductID(int productID) {
    this.productID = productID;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public void setTotal(float total) {
    this.total = total;
  }

  // getter

  public float getTotalPrice() {
    return totalPrice;
  }

  public String getProductType() {
    return productType;
  }

  public int getProductID() {
    return productID;
  }

  public String getProductName() {
    return productName;
  }

  public float getProductPrice() {
    return productPrice;
  }

  public int getProductQty() {
    return productQty;
  }

  public int getCartID() {
    return cartID;
  }

  public float getTotal() {
    return total;
  }

  public int readCartID() throws Exception {
    ResultSet resultSet = statement.executeQuery("select cartID from cartproducts order by cartID asc");
    int id = 0;
    while (resultSet.next()) {
      id = resultSet.getInt("cartID");
    }
    System.out.println(id);
    return id;
  }

  public int generateID() throws Exception {
    if (readCartID() == 0) {
      return 10000;
    } else {
      return readCartID() + 1;
    }
  }

  public void saveToDb(ArrayList<Cart> cart) throws Exception {
    String insertStm = "insert into cartProducts(cartID, productID, productName, productPrice, productQty, subPrice, productType) values (?, ?, ?, ?, ?, ?, ?)";
    this.st = connection.prepareStatement(insertStm);
    for (int i = 0; i < cart.size(); i++) {
      st.setInt(1, cart.get(i).getCartID());
      st.setInt(2, cart.get(i).getProductID());
      st.setString(3, cart.get(i).getProductName());
      st.setFloat(4, cart.get(i).getProductPrice());
      st.setInt(5, cart.get(i).getProductQty());
      st.setFloat(6, cart.get(i).getProductQty() * cart.get(i).getProductPrice());
      st.setString(7, cart.get(i).getProductType());
      st.executeUpdate();
    }
    st.close();
  }

  public void addToCart(int productID, String productName, float productPrice, int productQty, float total, float totalPrice, String productType) {
    setProductID(productID);
    setProductName(productName);
    setProductPrice(productPrice);
    setProductQty(productQty);
    setTotal(total);
    setTotalPrice(totalPrice);
    setProductType(productType);
  }

  public void displayItem() {
    System.out.println("Product ID: " + productID);
    System.out.println("Product Name: " + productName);
    System.out.println("Product Price: " + productPrice);
    System.out.println("Product Qty: " + productQty);
    System.out.println("Product Type: " + productType);
  }

  public void displayCartByID(int cartID) {
    try {
      displayCart(cartID);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public Cart searchProduct(int pid) {
    try {
      return searchFromProduct(pid);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public void updateCartItem(int productID, int productQty) {
    try {
      updateCartProduct(productID, productQty);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void removeCart(int cartID) {
    try {
      deleteCart(cartID);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void removeCartProduct(int productID) {
    try {
      deleteCartProduct(productID);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

}
