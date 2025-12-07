package com.gtuv.controlador;



import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FormularioUsuarioController implements Initializable {

    @FXML
    private Label lblErrorNumTrabajador;
    @FXML
    private Label lblErrorNombre;
    @FXML
    private Label lblErrorApPaterno;
    @FXML
    private Label lblErrorApMaterno;
    @FXML
    private Label lblErrorCorreo;
    @FXML
    private Label lblErrorProgramaEducativo;
    @FXML
    private ToggleGroup puestoAutoridad;
    @FXML
    private TextField txtNoTrabajador;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApPaterno;
    @FXML
    private TextField txtApMaterno;
    @FXML
    private TextField txtCorreo;
    @FXML
    private CheckBox chkTutor;
    @FXML
    private CheckBox chkAdmnistrador;
    @FXML
    private Button btnRegistrar;
    @FXML
    private ComboBox<?> cmbProgramaEducativo;
    @FXML
    private RadioButton rbJefeCarrera;
    @FXML
    private RadioButton rbCoordinador;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void activarOpcionCoordinador(ActionEvent event) {
    }

}
