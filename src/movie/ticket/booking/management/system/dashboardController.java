package movie.ticket.booking.management.system;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class dashboardController implements Initializable {


    @FXML
    private AnchorPane topForm;

    @FXML
    private ImageView addMovies_ImageView;

    @FXML
    private Button addMovies_Import;

    @FXML
    private TextField addMovies_MovieTitle;

    @FXML
    private Button addMovies_btn;

    @FXML
    private Button addMovies_clearBtn;

    @FXML
    private TableColumn<movieData, String> addMovies_col_date;

    @FXML
    private TableColumn<movieData, String> addMovies_col_duration;

    @FXML
    private TableColumn<movieData, String> addMovies_col_genre;

    @FXML
    private TableColumn<movieData, String> addMovies_col_movieTItle;

    @FXML
    private DatePicker addMovies_date;

    @FXML
    private Button addMovies_deleteBtn;

    @FXML
    private TextField addMovies_duration;

    @FXML
    private AnchorPane addMovies_form;

    @FXML
    private TextField addMovies_genre;

    @FXML
    private Button addMovies_insertBtn;

    @FXML
    private TextField addMovies_search;

    @FXML
    private TableView<movieData> addMovies_tabelView;

    @FXML
    private Button addMovies_updateBtn;

    @FXML
    private TableView<movieData> addScreening_tableView;

    @FXML
    private ImageView availableMovies_ImageView;

    @FXML
    private Button availableMovies_btn;

    @FXML
    private Button availableMovies_buyBtn;

    @FXML
    private Button availableMovies_clearBtn;

    @FXML
    private TableColumn<movieData, String> availableMovies_col_genre;

    @FXML
    private TableColumn<movieData, String> availableMovies_col_movieTitle;

    @FXML
    private TableColumn<movieData, String> availableMovies_col_showingDate;

    @FXML
    private Label availableMovies_date;

    @FXML
    private AnchorPane availableMovies_form;

    @FXML
    private Label availableMovies_genre;

    @FXML
    private Label availableMovies_movieTitle;

    @FXML
    private Spinner<Integer> availableMovies_normalClass;

    @FXML
    private Label availableMovies_normalClass_price;

    @FXML
    private Button availableMovies_receiptBtn;

    @FXML
    private Button availableMovies_selectMovie;

    @FXML
    private Label availableMovies_specialClass_price;

    @FXML
    private Spinner<Integer> availableMovies_specialClass_quantity;

    @FXML
    private TableView<movieData> availableMovies_tableView;

    @FXML
    private Label availableMovies_title;

    @FXML
    private Label availableMovies_total;

    @FXML
    private Button customer_btn;

    @FXML
    private Button customer_clearBtn;

    @FXML
    private TableColumn<?, ?> customer_col_date;

    @FXML
    private TableColumn<?, ?> customer_col_genre;

    @FXML
    private TableColumn<?, ?> customer_col_movieTitle;

    @FXML
    private TableColumn<?, ?> customer_col_ticketNumber;

    @FXML
    private TableColumn<?, ?> customer_col_time;

    @FXML
    private Label customer_date;

    @FXML
    private Button customer_deleteBtn;

    @FXML
    private Label customer_genre;

    @FXML
    private Label customer_movieTitle;

    @FXML
    private TextField customer_search;

    @FXML
    private Label customer_ticketNumber;

    @FXML
    private Label customer_time;

    @FXML
    private AnchorPane customers_form;

    @FXML
    private Label dashboard_availableMovies;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_totalEarnToday;

    @FXML
    private Label dashboard_totalSoldTicket;

    @FXML
    private ImageView editScreening_ImageView;

    @FXML
    private Button editScreening_btn;

    @FXML
    private TableColumn<movieData, String> editScreening_col_current;

    @FXML
    private TableColumn<movieData, String> editScreening_col_duration;

    @FXML
    private TableColumn<movieData, String> editScreening_col_genre;

    @FXML
    private TableColumn<movieData, String> editScreening_col_movieTitle;

    @FXML
    private ComboBox<?> editScreening_current;

    @FXML
    private Button editScreening_delete;

    @FXML

    private AnchorPane editScreening_form;

    @FXML
    private TextField editScreening_search;

    @FXML
    private Label editScreening_title;

    @FXML
    private Button editScreening_update;

    @FXML
    private Button singout;

    @FXML
    private Label username;

    @FXML
    private Button minimize;


//    @FXML
//    void close(MouseEvent event) {
//
//    }
    private Image image;
    private double x = 0;
    private double y = 0;

    // DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepared;
    private ResultSet result;
    private Statement statement;

    /*AVAILABLE MOVIE PART*/
    private SpinnerValueFactory<Integer> spinner1;
    private SpinnerValueFactory<Integer> spinner2;
    private float price1 = 0;
    private float price2 = 0;
    private float total = 0;
    private int qty1 = 0;
    private int qty2 = 0;


    // CUSTOMER INFO
    public void buy(){
        String sql = "INSERT INTO customer (type,total,date) VALUES (?,?,?)";
        connect = database.connectDb();
        String type = "";
        if(price1 > 0 && price2 == 0){
            type = "Special Class";
        } else if(price2 > 0 && price1 ==0){
            type = "Normal Class";
        } else if(price2 > 0 && price1 > 0){
            type = "Special & Normal Class";
        }

        Date date = new Date();
        java.sql.Date setDate = new java.sql.Date(date.getTime());

        try{

            prepared = connect.prepareStatement(sql);
            prepared.setString(1, type);
            prepared.setString(2, String.valueOf(total));
            prepared.setString(3, String.valueOf(setDate));

            Alert alert;

            // Check first if the user select the movie before click buy
            if(availableMovies_ImageView.getImage() == null
                    || availableMovies_title.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("You need to select a movie");
                alert.showAndWait();
            } // Check if quantity of special class or Normal class is zero
            else if(price1 == 0 && price2 == 0){
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Please indicate the quantity of Ticket you want");
                alert.showAndWait();
            } else{
                prepared.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("You have successfully purchase!");
                alert.showAndWait();

                String sql1 = "SELECT * FROM customer";

                prepared = connect.prepareStatement(sql1);
                result = prepared.executeQuery();

                int num = 0;

                while(result.next()){
                    // GEt the last Id we Inseted
                    num = result.getInt("id");
                }

                String sql2 = "INSERT INTO customer_info (customer_id,type,total) VALUES (?,?,?)";

                prepared = connect.prepareStatement(sql2);
                prepared.setString(1, String.valueOf(num));
                prepared.setString(2, type);
                prepared.setString(3, String.valueOf(total));
                prepared.execute();

                // To clear
                clearPurchaseTicketInfo();
            }

        } catch(Exception e){e.printStackTrace();}

    }

    public void clearPurchaseTicketInfo(){

        spinner1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        spinner2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);

        availableMovies_specialClass_quantity.setValueFactory(spinner1);
        availableMovies_normalClass.setValueFactory(spinner2);
        availableMovies_specialClass_price.setText("$0.0");
        availableMovies_normalClass_price.setText("$0.0");
        availableMovies_total.setText("$0.0");
        availableMovies_ImageView.setImage(null);
        availableMovies_title.setText("");
    }


    public void showSpinnerValue(){
        spinner1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        spinner2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);

        availableMovies_specialClass_quantity.setValueFactory(spinner1);
        availableMovies_normalClass.setValueFactory(spinner2);
    }

    public void getSpinnerValue(MouseEvent mouseEvent){

        qty1 = availableMovies_specialClass_quantity.getValue();
        qty2 = availableMovies_normalClass.getValue();

        price1 = (qty1 * 25); // 25 per each ticket for special class
        price2 = (qty2 * 10); // 10 per each ticket for normal class

        total = (price1 + price2);

        availableMovies_specialClass_price.setText("$" + String.valueOf(price1));
        availableMovies_normalClass_price.setText("$" + String.valueOf(price2));

        availableMovies_total.setText("$" + String.valueOf(total));
    }


    public ObservableList<movieData> availableMoviesList(){
        ObservableList<movieData> listAvMovies = FXCollections.observableArrayList();

        String sql = "SELECT * FROM movie WHERE current = 'Showing'";
        connect = database.connectDb();

        try{
            prepared = connect.prepareStatement(sql);
            result = prepared.executeQuery();

            movieData movD;

            while (result.next()) {
                movD = new movieData(result.getInt("id")
                        , result.getString("movieTitle")
                        , result.getString("genre")
                        , result.getString("duration")
                        , result.getString("image")
                        , result.getDate("date")
                        , result.getString("current"));
                listAvMovies.add(movD);
            }
        } catch (Exception e){e.printStackTrace();}
        return listAvMovies;
    }

    private ObservableList<movieData> availableMoviesList;
    public void showAvailableMovies(){
        availableMoviesList = availableMoviesList();

        availableMovies_col_movieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        availableMovies_col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        availableMovies_col_showingDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        availableMovies_tableView.setItems(availableMoviesList);
    }

    public void selectAvailableMovies(){

        movieData movD = availableMovies_tableView.getSelectionModel().getSelectedItem();
        int num = availableMovies_tableView.getSelectionModel().getSelectedIndex();

        if((num - 1)<- 1){
            return;
        }

        availableMovies_movieTitle.setText(movD.getTitle());
        availableMovies_genre.setText(movD.getGenre());
        availableMovies_date.setText(String.valueOf(movD.getDate()));

        getData.path = movD.getImage();
        getData.title = movD.getTitle();
    }

    public void selectMovie(){

        Alert alert;

        if(availableMovies_movieTitle.getText().isEmpty()
                || availableMovies_genre.getText().isEmpty()
                || availableMovies_date.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a movie first");
            alert.showAndWait();
        } else {

            String uri = "file:" + getData.path;
            image = new Image(uri, 136, 180, false, true);
            availableMovies_ImageView.setImage(image);

            availableMovies_title.setText(getData.title);

            availableMovies_movieTitle.setText("");
            availableMovies_genre.setText("");
            availableMovies_date.setText("");
        }
    }

    /*EDIT SCREENIG PART*/
    private String[] currentList ={"Showing", "End Showing"};

    public void comboBox(){
        List<String> listCurrent = new ArrayList<>();

        for(String data : currentList){
            listCurrent.add(data);
        }

        ObservableList listC = FXCollections.observableArrayList(listCurrent);
        editScreening_current.setItems(listC);
    }

    public void updateEditScreening(){
        String sql = "UPDATE movie SET current = '"
                + editScreening_current.getSelectionModel().getSelectedItem()
                + "' WHERE movieTitle = '" + editScreening_title.getText() + "'";


        connect = database.connectDb();

        try{

            statement = connect.createStatement();

            Alert alert;

            if(editScreening_title.getText().isEmpty()
                    || editScreening_ImageView.getImage() == null
                    || editScreening_current.getSelectionModel().isEmpty()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select the movie first");
                alert.showAndWait();
            } else{
                statement.executeUpdate(sql);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Successfully updated");
                alert.showAndWait();

                showEditScreening();
                clearEditScreening();
            }
        }catch (Exception e){e.printStackTrace();}
    }



    public void clearEditScreening(){
        editScreening_title.setText("");
        editScreening_ImageView.setImage(null);
//        editScreening_current.setSelectionModel(null);


    }

    public void searchEditScreening(){

        FilteredList <movieData> filter = new FilteredList<>(editScreeningL,  e-> true);
        editScreening_search.textProperty().addListener((observable, oldValue, newValue) ->{

            filter.setPredicate(predicateMoviesData ->{
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if(predicateMoviesData.getTitle().toLowerCase().contains(searchKey)){
                    return true;
                } else if(predicateMoviesData.getGenre().toLowerCase().contains(searchKey)){
                    return true;
                } else if(predicateMoviesData.getDuration().toLowerCase().contains(searchKey)){
                    return true;
                } else if(predicateMoviesData.getCurrent().toLowerCase().contains(searchKey)){
                    return true;
                }
                return  false;
            });
            SortedList<movieData> sortData = new SortedList<>(filter);
            sortData.comparatorProperty().bind(addScreening_tableView.comparatorProperty());
            addScreening_tableView.setItems(sortData);
        });
    }

    public void selectEditScreening(){
        movieData movD = addScreening_tableView.getSelectionModel().getSelectedItem();
        int num = addScreening_tableView.getSelectionModel().getFocusedIndex();

        if((num - 1) < -1){
            return;
        }

        String uri = "file:" + movD.getImage();
        image = new Image(uri, 138, 183, false, true);
        editScreening_ImageView.setImage(image);

        editScreening_title.setText(movD.getTitle());
    }

    public ObservableList<movieData> editScreeningList(){
       ObservableList<movieData> editSList = FXCollections.observableArrayList();
       String sql = "SELECT * FROM movie";
       connect = database.connectDb();

       try{

           prepared = connect.prepareStatement(sql);
           result = prepared.executeQuery();

           movieData movD;

           while(result.next()){
               movD = new movieData(result.getInt("id")
                       , result.getString("movieTitle")
                       , result.getString("genre"), result.getString("duration")
                       , result.getString("image"), result.getDate("date")
                       , result.getString("current"));

               editSList.add(movD);
           }

       } catch (Exception e) {e.printStackTrace();}
       return editSList;
    }

    private ObservableList<movieData> editScreeningL;
    public void showEditScreening(){
        editScreeningL = editScreeningList();

        editScreening_col_movieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        editScreening_col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        editScreening_col_duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        editScreening_col_current.setCellValueFactory(new PropertyValueFactory<>("current"));

        addScreening_tableView.setItems(editScreeningL);
    }
    /*EDIT SCREENING PART*/

    /*ADD MOVIE PART*/
    public void searchAdddMovies() {
        FilteredList<movieData> filter = new FilteredList<>(ListAddMovies, e -> true);
        addMovies_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(predicatedMoviesData ->{
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                String keySearch = newValue.toLowerCase();
                if(predicatedMoviesData.getTitle().toLowerCase().contains(keySearch)){
                    return true;
                } else if (predicatedMoviesData.getGenre().toLowerCase().contains(keySearch)) {
                    return true;
                } else if (predicatedMoviesData.getDuration().toLowerCase().contains(keySearch)) {
                    return true;
                } else if (predicatedMoviesData.getDate().toString().contains(keySearch)) {
                    return true;
                }

                return false;
            });
            SortedList<movieData> sortedList = new SortedList<>(filter);
            sortedList.comparatorProperty().bind(addMovies_tabelView.comparatorProperty());
            addMovies_tabelView.setItems(sortedList);
        });
    }

    public void importImage(){
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*.png", "*.jpg"));

        Stage stage = (Stage)addMovies_form.getScene().getWindow();
        File file = open.showOpenDialog(stage);

        if(file != null){
            image = new Image(file.toURI().toString(), 127, 167, false, true);
            addMovies_ImageView.setImage(image);

            getData.path = file.getAbsolutePath();
        }
    }


    public void movieId() {
        String sql = "SELECT count(id) FROM movie";

        connect = database.connectDb();

        try{
            prepared = connect.prepareStatement(sql);
            result = prepared.executeQuery();

            if(result.next()){
                getData.movieId = result.getInt("count(id)");
            }


        } catch (Exception e) {e.printStackTrace();}
    }

    public void insertAddMovies(){

        String sql1 = "SELECT * FROM movie WHERE movieTitle = '"
                + addMovies_MovieTitle.getText() + "'";

        connect = database.connectDb();

        Alert alert;

        try{

            statement = connect.createStatement();
            result = statement.executeQuery(sql1);

            if(result.next()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(addMovies_MovieTitle.getText() +" was already added to the database");
                alert.showAndWait();
            } else{
                if(addMovies_MovieTitle.getText().isEmpty()
                        || addMovies_genre.getText().isEmpty()
                        || addMovies_duration.getText().isEmpty()
                        || addMovies_ImageView.getImage() == null
                        || addMovies_date.getValue() == null)
                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter all the fields correctly");
                    alert.showAndWait();
                } else{


                    String sql =
                            "INSERT INTO movie (id,movieTitle, genre, duration, image, date) VALUES (?,?,?,?,?,?)";
                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");

                    movieId();

                    String mID = String.valueOf(getData.movieId + 1);
                    prepared = connect.prepareStatement(sql);
                    prepared.setString(1, mID);
                    prepared.setString(2, addMovies_MovieTitle.getText());
                    prepared.setString(3, addMovies_genre.getText());
                    prepared.setString(4, addMovies_duration.getText());
                    prepared.setString(5, uri);
                    prepared.setString(6, String.valueOf(addMovies_date.getValue()));

                    prepared.execute();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully added to the database");
                    alert.showAndWait();
                    clearAddMoviesList();
                    showAddMoviesList();
                }
            }

        } catch (Exception e) {e.printStackTrace();}
    }

    public void updateAddMovies(){

        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");
        String sql = "UPDATE movie SET movieTitle = '"+ addMovies_MovieTitle.getText()
                + "', genre = '"+ addMovies_genre.getText()
                + "', duration = '"+ addMovies_duration.getText()
                + "', image = '"+ uri
                + "', date = '"+ addMovies_date.getValue()
                + "' WHERE id = '" + String.valueOf(getData.movieId) + "'";

        connect = database.connectDb();

        try{
            statement = connect.createStatement();

            Alert alert;

            if(addMovies_MovieTitle.getText().isEmpty()
                    || addMovies_genre.getText().isEmpty()
                    || addMovies_duration.getText().isEmpty()
                    || addMovies_ImageView.getImage() == null
                    || addMovies_date.getValue() == null){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select the movie first");
                alert.showAndWait();
            } else{

                statement.executeUpdate(sql);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Successfully updated " + addMovies_MovieTitle.getText());
                alert.showAndWait();
                showAddMoviesList();
                clearAddMoviesList();
            }

        } catch (Exception e) {e.printStackTrace();}
    }

    public void deleteAddMovies(){
        String sql = "DELETE FROM movie WHERE movieTitle = '"
                + addMovies_MovieTitle.getText()+ "'";

        connect = database.connectDb();

        try {

            statement = connect.createStatement();

            Alert alert;

            if(addMovies_MovieTitle.getText().isEmpty()
                    || addMovies_genre.getText().isEmpty()
                    || addMovies_duration.getText().isEmpty()
                    || addMovies_ImageView.getImage() == null
                    || addMovies_date.getValue() == null){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select the movie first");
                alert.showAndWait();
            } else{

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete "
                        + addMovies_MovieTitle.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if(ButtonType.OK.equals(option.get())){

                    statement.executeUpdate(sql);
                    showAddMoviesList();
                    clearAddMoviesList();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully deleted " + addMovies_MovieTitle.getText());
                    alert.showAndWait();

                } else {
                    return;
                }
            }

        } catch (Exception e) {e.printStackTrace();}
    }

    public void clearAddMoviesList(){
        addMovies_MovieTitle.setText("");
        addMovies_genre.setText("");
        addMovies_duration.setText("");
        addMovies_ImageView.setImage(null);
        addMovies_date.setValue(null);
    }

    public ObservableList<movieData> addMovieList(){
        ObservableList<movieData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM movie";

        connect = database.connectDb();

        try{
            prepared = connect.prepareStatement(sql);
            result = prepared.executeQuery();
            movieData movD;
            while (result.next()) {
                movD = new movieData(result.getInt("id")
                        , result.getString("movieTitle")
                        , result.getString("genre"), result.getString("duration")
                        , result.getString("image"), result.getDate("date")
                        , result.getString("current"));

                listData.add(movD);
            }

        } catch (Exception e){e.printStackTrace();}
        return listData;
    }

    private ObservableList <movieData> ListAddMovies;
    public void showAddMoviesList() {
        ListAddMovies = addMovieList();

        addMovies_col_movieTItle.setCellValueFactory(new PropertyValueFactory<>("title"));
        addMovies_col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        addMovies_col_duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        addMovies_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        addMovies_tabelView.setItems(ListAddMovies);
    }

    public void selectAddMovieList(){
        movieData movD = addMovies_tabelView.getSelectionModel().getSelectedItem();
        int num = addMovies_tabelView.getSelectionModel().getSelectedIndex();

        if((num - 1) < - 1){
            return;
        }

        getData.path = movD.getImage();
        getData.movieId = movD.getId();

        addMovies_MovieTitle.setText(movD.getTitle());
        addMovies_genre.setText(movD.getGenre());
        addMovies_duration.setText(movD.getDuration());

        String getDate = String.valueOf(movD.getDate());


        String uri = "file:" + movD.getImage();

        image = new Image(uri, 127, 167, false, true);
        addMovies_ImageView.setImage(image);

        addMovies_date.setValue(movD.getDate().toLocalDate());
    }

    public void logout(){
        singout.getScene().getWindow().hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });

            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {ex.printStackTrace();}
    }
    public void switchForm(ActionEvent event) {

        if(event.getSource() == dashboard_btn) {

            dashboard_form.setVisible(true);
            addMovies_form.setVisible(false);
            availableMovies_form.setVisible(false);
            editScreening_form.setVisible(false);
            customers_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: #ae2d3c;");
            addMovies_btn.setStyle("-fx-background-color: transparent;");
            availableMovies_btn.setStyle("-fx-background-color: transparent;");
            editScreening_btn.setStyle("-fx-background-color: transparent;");
            customer_btn.setStyle("-fx-background-color: transparent;");

        } else if(event.getSource() == addMovies_btn) {

            dashboard_form.setVisible(false);
            addMovies_form.setVisible(true);
            availableMovies_form.setVisible(false);
            editScreening_form.setVisible(false);
            customers_form.setVisible(false);

            addMovies_btn.setStyle("-fx-background-color: #ae2d3c;");
            dashboard_btn.setStyle("-fx-background-color: transparent;");
            availableMovies_btn.setStyle("-fx-background-color: transparent;");
            editScreening_btn.setStyle("-fx-background-color: transparent;");
            customer_btn.setStyle("-fx-background-color: transparent;");

            showAddMoviesList();

        } else if (event.getSource() == availableMovies_btn) {

            dashboard_form.setVisible(false);
            addMovies_form.setVisible(false);
            availableMovies_form.setVisible(true);
            editScreening_form.setVisible(false);
            customers_form.setVisible(false);

            availableMovies_btn.setStyle("-fx-background-color: #ae2d3c;");
            dashboard_btn.setStyle("-fx-background-color: transparent;");
            addMovies_btn.setStyle("-fx-background-color: transparent;");
            editScreening_btn.setStyle("-fx-background-color: transparent;");
            customer_btn.setStyle("-fx-background-color: transparent;");

            showAvailableMovies();

        } else if (event.getSource() == editScreening_btn) {

            dashboard_form.setVisible(false);
            addMovies_form.setVisible(false);
            availableMovies_form.setVisible(false);
            editScreening_form.setVisible(true);
            customers_form.setVisible(false);

            editScreening_btn.setStyle("-fx-background-color: #ae2d3c;");
            dashboard_btn.setStyle("-fx-background-color: transparent;");
            addMovies_btn.setStyle("-fx-background-color: transparent;");
            availableMovies_btn.setStyle("-fx-background-color: transparent;");
            customer_btn.setStyle("-fx-background-color: transparent;");
            showEditScreening();

        } else if (event.getSource() == customer_btn) {

            dashboard_form.setVisible(false);
            addMovies_form.setVisible(false);
            availableMovies_form.setVisible(false);
            editScreening_form.setVisible(false);
            customers_form.setVisible(true);

            customer_btn.setStyle("-fx-background-color: #ae2d3c;");
            dashboard_btn.setStyle("-fx-background-color: transparent;");
            addMovies_btn.setStyle("-fx-background-color: transparent;");
            availableMovies_btn.setStyle("-fx-background-color: transparent;");
            editScreening_btn.setStyle("-fx-background-color: transparent;");

        }
    }


    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    public void displayUsername(){
        username.setText(getData.username);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        showAddMoviesList();
        showEditScreening();
        comboBox();
        // To Show The Available Movies
        showAvailableMovies();
        // To use the spinner
        showSpinnerValue();
    }
}



























