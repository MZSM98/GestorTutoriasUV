import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GestionUsuarioController implements Initializable {

    @FXML
    private TableView<?> tblTutorados;
    @FXML
    private TableColumn<?, ?> colMatricula;
    @FXML
    private TableColumn<?, ?> colApPaterno;
    @FXML
    private TableColumn<?, ?> colApMaterno;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colProgramaEducativo;
    @FXML
    private TableColumn<?, ?> colAcciones;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnRegresar;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}