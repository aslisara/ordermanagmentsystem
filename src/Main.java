import dao.ProductDAO;
import entity.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/ordermanagament";
        String user = "postgres";
        String password = "1234";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            ProductDAO productDAO = new ProductDAO(conn);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n*** CRUD Uygulaması ***");
                System.out.println("1. Kayıt Ekle");
                System.out.println("2. Kayıtları Listele");
                System.out.println("3. Kayıt Güncelle");
                System.out.println("4. Kayıt Sil");
                System.out.println("5. Kayıt Ara");
                System.out.println("6. Çıkış");
                System.out.print("Seçiminiz (1-6): ");

                int secim = scanner.nextInt();
                scanner.nextLine(); // Dummy nextLine to consume newline character

                switch (secim) {
                    case 1:
                        System.out.println("Ürün Bilgilerini Girin:");
                        System.out.print("Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Description: ");
                        String description = scanner.nextLine();
                        System.out.print("Category: ");
                        String category = scanner.nextLine();
                        System.out.print("Price: ");
                        double price = scanner.nextDouble();
                        System.out.print("Stock Quantity: ");
                        int stockQuantity = scanner.nextInt();
                        System.out.print("Is Active (true/false): ");
                        boolean isActive = scanner.nextBoolean();

                        Product newProduct = new Product(0, name, description, category, price, stockQuantity, isActive);
                        productDAO.insert(newProduct);
                        break;
                    case 2:
                        List<Product> productList = productDAO.getAllProducts();
                        System.out.println("\n--- Tüm Ürünler ---");
                        for (Product product : productList) {
                            System.out.println(product);
                        }
                        break;
                    case 3:
                        System.out.print("Güncellenecek Ürünün ID'sini Girin: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine(); // Dummy nextLine to consume newline character

                        Product existingProduct = productDAO.getProductById(updateId);
                        if (existingProduct != null) {
                            System.out.println("Güncellenecek Ürün Bilgilerini Girin:");
                            System.out.print("Name: ");
                            existingProduct.setName(scanner.nextLine());
                            System.out.print("Description: ");
                            existingProduct.setDescription(scanner.nextLine());
                            System.out.print("Category: ");
                            existingProduct.setCategory(scanner.nextLine());
                            System.out.print("Price: ");
                            existingProduct.setPrice(scanner.nextDouble());
                            System.out.print("Stock Quantity: ");
                            existingProduct.setStockQuantity(scanner.nextInt());
                            System.out.print("Is Active (true/false): ");
                            existingProduct.setActive(scanner.nextBoolean());

                            productDAO.update(existingProduct);
                        } else {
                            System.out.println("Ürün bulunamadı.");
                        }
                        break;
                    case 4:
                        System.out.print("Silinecek Ürünün ID'sini Girin: ");
                        int deleteId = scanner.nextInt();
                        productDAO.delete(deleteId);
                        break;
                    case 5:
                        System.out.print("Aranacak Ürünün ID'sini Girin: ");
                        int searchId = scanner.nextInt();
                        Product searchedProduct = productDAO.getProductById(searchId);
                        if (searchedProduct != null) {
                            System.out.println("\n--- Aranan Ürün ---");
                            System.out.println(searchedProduct);
                        } else {
                            System.out.println("Ürün bulunamadı.");
                        }
                        break;
                    case 6:
                        System.out.println("Programdan çıkılıyor...");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Geçersiz seçim. Tekrar deneyin.");
                        break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
