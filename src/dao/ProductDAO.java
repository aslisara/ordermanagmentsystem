package dao;

import entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection conn;

    public ProductDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(Product product) {
        String query = "INSERT INTO product(name, description, category, price, stock_quantity, is_active) VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setString(3, product.getCategory());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setInt(5, product.getStockQuantity());
            preparedStatement.setBoolean(6, product.isActive());
            preparedStatement.executeUpdate();
            System.out.println("Ürün başarıyla eklendi.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM product";
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                double price = resultSet.getDouble("price");
                int stockQuantity = resultSet.getInt("stock_quantity");
                boolean isActive = resultSet.getBoolean("is_active");

                Product product = new Product(id, name, description, category, price, stockQuantity, isActive);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public void update(Product product) {
        String query = "UPDATE product SET name=?, description=?, category=?, price=?, stock_quantity=?, is_active=? WHERE id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setString(3, product.getCategory());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setInt(5, product.getStockQuantity());
            preparedStatement.setBoolean(6, product.isActive());
            preparedStatement.setInt(7, product.getId());
            preparedStatement.executeUpdate();
            System.out.println("Ürün başarıyla güncellendi.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int productId) {
        String query = "DELETE FROM product WHERE id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
            System.out.println("Ürün başarıyla silindi.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product getProductById(int productId) {
        Product product = null;
        String query = "SELECT * FROM product WHERE id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String category = resultSet.getString("category");
                    double price = resultSet.getDouble("price");
                    int stockQuantity = resultSet.getInt("stock_quantity");
                    boolean isActive = resultSet.getBoolean("is_active");

                    product = new Product(id, name, description, category, price, stockQuantity, isActive);
                } else {
                    System.out.println("Ürün bulunamadı.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
}
