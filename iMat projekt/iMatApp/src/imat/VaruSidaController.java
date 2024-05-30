package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VaruSidaController {

    private List<Produkter> allProducts = new ArrayList<>();
    private User currentUser;

    ViewSwitcher viewSwitcher = new ViewSwitcher();

    @FXML
    private ScrollPane productScrollPane;

    @FXML
    private GridPane productGrid;

    @FXML
    private Button buttonVegetables;
    @FXML
    private Button buttonDairies;
    @FXML
    private Button buttonFruits;
    @FXML
    private Button buttonBasics;
    @FXML
    private Button buttonMeat;
    @FXML
    private Button buttonFish;
    @FXML
    private Button buttonSnacks;
    @FXML
    private Button buttonSpices;
    @FXML
    private Button buttonNutsAndSeeds;
    @FXML
    private Button buttonPasta;
    @FXML
    private Button buttonPotatoRice;
    @FXML
    private Button buttonRootVegetable;
    @FXML
    private Button buttonBread;
    @FXML
    private Button buttonBerry;
    @FXML
    private Button buttonCitrusFruit;
    @FXML
    private Button buttonHotDrinks;
    @FXML
    private Button buttonColdDrinks;
    @FXML
    private Button buttonExoticFruit;
    @FXML
    private Button buttonMelons;
    @FXML
    private Button profileButton;
    @FXML
    private Button cartButton;
    @FXML
    private AnchorPane cartPane;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;

    private VarukorgController varukorgController;

    public void initialize() {
        loadProductsFromFile("iMat projekt/iMatApp/src/varor.txt");

        // Populate products after loading
        populateProducts(allProducts);

        buttonVegetables.setOnAction(e -> showProductsByCategories(Arrays.asList("VEGETABLE_FRUIT", "CABBAGE")));
        buttonRootVegetable.setOnAction(e -> showProductsByCategory("ROOT_VEGETABLE"));
        buttonFruits.setOnAction(e -> showProductsByCategory("FRUIT"));
        buttonBerry.setOnAction(e -> showProductsByCategory("BERRY"));
        buttonCitrusFruit.setOnAction(e -> showProductsByCategory("CITRUS_FRUIT"));
        buttonExoticFruit.setOnAction(e -> showProductsByCategory("EXOTIC_FRUIT"));
        buttonMelons.setOnAction(e -> showProductsByCategory("MELONS"));
        buttonMeat.setOnAction(e -> showProductsByCategory("MEAT"));
        buttonFish.setOnAction(e -> showProductsByCategory("FISH"));
        buttonDairies.setOnAction(e -> showProductsByCategory("DAIRIES"));
        buttonNutsAndSeeds.setOnAction(e -> showProductsByCategory("NUTS_AND_SEEDS"));
        buttonBasics.setOnAction(e -> showProductsByCategory("FLOUR_SUGAR_SALT"));
        buttonPasta.setOnAction(e -> showProductsByCategory("PASTA"));
        buttonPotatoRice.setOnAction(e -> showProductsByCategory("POTATO_RICE"));
        buttonBread.setOnAction(e -> showProductsByCategory("BREAD"));
        buttonSnacks.setOnAction(e -> showProductsByCategory("SWEET"));
        buttonSpices.setOnAction(e -> showProductsByCategory("HERB"));
        buttonHotDrinks.setOnAction(e -> showProductsByCategory("HOT_DRINKS"));
        buttonColdDrinks.setOnAction(e -> showProductsByCategory("COLD_DRINKS"));
        cartButton.setOnAction(e -> handleOpenCart());

        profileButton.setOnAction(e -> {
            try {
                showProfileViewLoggedIn();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Varukorg.fxml"));
            Parent cartRoot = loader.load();
            varukorgController = loader.getController();
            varukorgController.setParentController(this);

            cartPane.getChildren().add(cartRoot);
            cartPane.setVisible(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleOpenCart() {
        cartPane.setVisible(true);
        varukorgController.refreshCartItems();
    }

    @FXML
    public void handleCloseCart() {
        cartPane.setVisible(false);
    }

    public void showLoginView() throws IOException {
        viewSwitcher.switchTo("iMat_login.fxml");
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        System.out.println("Current user set in VaruSidaController: " + (user != null ? user.getFirstName() + " " + user.getLastName() : "null"));
    }

    public void showProfileViewLoggedIn() throws IOException {
        User currentUser = UserSession.getCurrentUser();
        System.out.println("Navigating to profile view with user: " + (currentUser != null ? currentUser.getFirstName() + " " + currentUser.getLastName() : "null"));
        ProfileViewController profileViewController = ViewSwitcher.switchTo("profile_page.fxml");
        profileViewController.setCurrentUser(currentUser);
    }

    @FXML
    public void escapeHatchButton() throws IOException {
        viewSwitcher.switchTo("VaruSida.fxml");
    }

    void loadProductsFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 7) {
                    int id = Integer.parseInt(parts[0]);
                    String category = parts[1];
                    String name = parts[2];
                    double price = Double.parseDouble(parts[3]);
                    String unit = parts[4];
                    String imagePath = parts[5];
                    allProducts.add(new Produkter(id, category, name, price, unit, imagePath));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateProducts(List<Produkter> products) {
        productGrid.getChildren().clear();
        int column = 0;
        int row = 0;

        for (Produkter product : products) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("productCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ProductCardController controller = fxmlLoader.getController();
                controller.setProduct(product);
                controller.setVarukorgController(varukorgController);

                if (column == 4) {
                    column = 0;
                    row++;
                }

                productGrid.add(anchorPane, column++, row);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showProductsByCategory(String category) {
        List<Produkter> filteredProducts = new ArrayList<>();
        for (Produkter product : allProducts) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                filteredProducts.add(product);
            }
        }
        populateProducts(filteredProducts);
    }

    private void showProductsByCategories(List<String> categories) {
        List<Produkter> filteredProducts = new ArrayList<>();
        for (Produkter product : allProducts) {
            if (categories.contains(product.getCategory().toUpperCase())) {
                filteredProducts.add(product);
            }
        }
        populateProducts(filteredProducts);
    }
}
