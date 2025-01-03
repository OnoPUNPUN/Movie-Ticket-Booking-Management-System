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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

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
    private TableColumn<?, ?> availableMovies_col_genre;

    @FXML
    private TableColumn<?, ?> availableMovies_col_movieTitle;

    @FXML
    private TableColumn<?, ?> availableMovies_col_showingDate;

    @FXML
    private Label availableMovies_date;

    @FXML
    private AnchorPane availableMovies_form;

    @FXML
    private Label availableMovies_genre;

    @FXML
    private Label availableMovies_movieTitle;

    @FXML
    private Spinner<?> availableMovies_normalClass;

    @FXML
    private Label availableMovies_normalClass_price;

    @FXML
    private Button availableMovies_receiptBtn;

    @FXML
    private Button availableMovies_selectMovie;

    @FXML
    private Label availableMovies_specialClass_price;

    @FXML
    private Spinner<?> availableMovies_specialClass_quantity;

    @FXML
    private TableView<?> availableMovies_tableView;

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

    /*EDIT SCREENIG PART*/
    private String[] currentList ={"Showing", "End Showing"};
    public void comboBox(){
        List<String> listCurrent = new ArrayList<String>();

        for(String data: currentList){
            listCurrent.add(data);
        }

        ObservableList listC = FXCollections.observableList(listCurrent);
        editScreening_current.setItems(listC);
    }

    public void updateEditScreening(){
        String sql = "UPDATE movie SET current = '"
                + editScreening_current.getSelectionModel()
                + "' WHERE movieTitle = '" + editScreening_title.getText() + "'";

        connect = database.connectDb();
    }

    public void selectEditScreening(){
        movieData movD = addScreening_tableView.getSelectionModel().getSelectedItem();
        int num = addScreening_tableView.getSelectionModel().getFocusedIndex();

        if((num - 1) < - 1){
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
    }
}



























