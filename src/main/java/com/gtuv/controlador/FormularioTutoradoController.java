package com.gtuv.controlador;

import com.gtuv.dominio.CatalogoImpl;
import com.gtuv.dominio.ProgramaEducativoImpl;
import com.gtuv.dominio.TutoradoImpl;
import com.gtuv.interfaces.IObservador;
import com.gtuv.modelo.pojo.ProgramaEducativo;
import com.gtuv.modelo.pojo.Semestre;
import com.gtuv.modelo.pojo.Tutorado;
import com.gtuv.utlidad.Utilidades;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormularioTutoradoController implements Initializable {

    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Label lblErrorMatricula;
    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox<ProgramaEducativo> cmbProgramaEducativo;
    @FXML
    private TextField txtApPaterno;
    @FXML
    private TextField txtApMaterno;
    @FXML
    private TextField txtCorreo;
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
    private ComboBox<Semestre> cmbSemestre;
    @FXML
    private Label lblErrorSemestre;
    @FXML
    private TextField txtMatricula;
    
    private IObservador observador;
    private Tutorado tutoradoEdicion;
    private ObservableList<ProgramaEducativo> programas;
    private ObservableList<Semestre> semestres;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCatalogos();
        ocultarMensajesError();
    }    
    
    public void inicializarDatos(IObservador observador, Tutorado tutorado) {
        this.observador = observador;
        this.tutoradoEdicion = tutorado;
        if (tutorado != null) {
            txtMatricula.setText(tutorado.getMatricula());
            txtMatricula.setEditable(false);
            txtNombre.setText(tutorado.getNombre());
            txtApPaterno.setText(tutorado.getApellidoPaterno());
            txtApMaterno.setText(tutorado.getApellidoMaterno());
            txtCorreo.setText(tutorado.getCorreo());
            seleccionarPrograma(tutorado.getIdProgramaEducativo());
            seleccionarSemestre(tutorado.getIdSemestre());
        }
    }
    
    @FXML
    private void clicRegistrar(ActionEvent event) {
        ocultarMensajesError();
        if (sonCamposValidos()) {
            if (tutoradoEdicion == null) {
                registrarTutorado();
            } else {
                editarTutorado();
            }
        }
    }
    
    @FXML
    private void clicRegresar(ActionEvent event) {
        cerrarVentana();
    }
    
    private void registrarTutorado() {
        Tutorado nuevo = obtenerTutoradoModelo();
        if (TutoradoImpl.verificarMatricula(nuevo.getMatricula()) || TutoradoImpl.verificarCorreo(nuevo.getCorreo())) {
            Utilidades.mostrarAlerta("Duplicidad", "La matrícula o correo ya están registrados", Alert.AlertType.WARNING);
            return;
        }
        HashMap<String, Object> respuesta = TutoradoImpl.registrarTutorado(nuevo);
        if (!(boolean) respuesta.get("error")) {
            Utilidades.mostrarAlerta("Registro exitoso", (String) respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Registro", nuevo.getNombre());
            cerrarVentana();
        } else {
            Utilidades.mostrarAlerta("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private void editarTutorado() {
        Tutorado editado = obtenerTutoradoModelo();
        editado.setIdTutorado(tutoradoEdicion.getIdTutorado());
        HashMap<String, Object> respuesta = TutoradoImpl.editarTutorado(editado);
        if (!(boolean) respuesta.get("error")) {
            Utilidades.mostrarAlerta("Edición exitosa", (String) respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Edicion", editado.getNombre());
            cerrarVentana();
        } else {
            Utilidades.mostrarAlerta("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private Tutorado obtenerTutoradoModelo() {
        Tutorado t = new Tutorado();
        t.setMatricula(txtMatricula.getText());
        t.setNombre(txtNombre.getText());
        t.setApellidoPaterno(txtApPaterno.getText());
        t.setApellidoMaterno(txtApMaterno.getText());
        t.setCorreo(txtCorreo.getText());
        t.setIdProgramaEducativo(cmbProgramaEducativo.getValue().getIdProgramaEducativo());
        t.setIdSemestre(cmbSemestre.getValue().getIdSemestre());
        return t;
    }
    
    private boolean sonCamposValidos() {
        boolean valido = true;
        if (txtMatricula.getText().isEmpty()) {
            lblErrorMatricula.setVisible(true);
            valido = false;
        }
        if (txtNombre.getText().isEmpty()) {
            lblErrorNombre.setVisible(true);
            valido = false;
        }
        if (txtApPaterno.getText().isEmpty()) {
            lblErrorApPaterno.setVisible(true);
            valido = false;
        }
        if (txtApMaterno.getText().isEmpty()) {
            lblErrorApMaterno.setVisible(true);
            valido = false;
        }
        if (txtCorreo.getText().isEmpty()) {
            lblErrorCorreo.setVisible(true);
            valido = false;
        }
        if (cmbProgramaEducativo.getSelectionModel().isEmpty()) {
            lblErrorProgramaEducativo.setVisible(true);
            valido = false;
        }
        if (cmbSemestre.getSelectionModel().isEmpty()) {
            lblErrorSemestre.setVisible(true);
            valido = false;
        }
        return valido;
    }
    
    private void ocultarMensajesError() {
        lblErrorMatricula.setText("");
        lblErrorNombre.setText("");
        lblErrorApPaterno.setText("");
        lblErrorApMaterno.setText("");
        lblErrorCorreo.setText("");
        lblErrorProgramaEducativo.setText("");
        lblErrorSemestre.setText("");
    }
    
    private void cargarCatalogos() {
        HashMap<String, Object> respPE = ProgramaEducativoImpl.obtenerProgramasEducativos();
        if (!(boolean) respPE.get("error")) {
            programas = FXCollections.observableArrayList((List<ProgramaEducativo>) respPE.get("programas"));
            cmbProgramaEducativo.setItems(programas);
        }
        
        HashMap<String, Object> respSem = CatalogoImpl.obtenerSemestres();
        if (!(boolean) respSem.get("error")) {
            semestres = FXCollections.observableArrayList((List<Semestre>) respSem.get("semestres"));
            cmbSemestre.setItems(semestres);
        }
    }
    
    private void seleccionarPrograma(int id) {
        for (ProgramaEducativo pe : cmbProgramaEducativo.getItems()) {
            if (pe.getIdProgramaEducativo() == id) {
                cmbProgramaEducativo.getSelectionModel().select(pe);
                break;
            }
        }
    }
    
    private void seleccionarSemestre(int id) {
        for (Semestre s : cmbSemestre.getItems()) {
            if (s.getIdSemestre() == id) {
                cmbSemestre.getSelectionModel().select(s);
                break;
            }
        }
    }
    
    private void cerrarVentana() {
        ((Stage) txtMatricula.getScene().getWindow()).close();
    }
}