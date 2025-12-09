package com.gtuv.controlador;

import com.gtuv.dominio.CatalogoImpl;
import com.gtuv.dominio.ProgramaEducativoImpl;
import com.gtuv.dominio.TutoradoImpl;
import com.gtuv.interfaces.IObservador;
import com.gtuv.modelo.pojo.ProgramaEducativo;
import com.gtuv.modelo.pojo.Semestre;
import com.gtuv.modelo.pojo.Tutorado;
import com.gtuv.utlidad.RestriccionCampos;
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

    private static final int LIMITE_MATRICULA = 9; 
    private static final int LIMITE_CAMPO_NOMBRES = 255;
    private static final String CAMPO_OBLIGATORIO = "Campo obligatorio";
    private static final int LIMITE_CAMPO_CORREO = 100;

    @FXML
    private Button btnRegistrar;
    @FXML
    private Label lblErrorMatricula;
    @FXML
    private TextField txtMatricula; 
    @FXML
    private ComboBox<ProgramaEducativo> cmbProgramaEducativo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApPaterno;
    @FXML
    private TextField txtApMaterno;
    @FXML
    private TextField txtCorreo;
    @FXML
    private ComboBox<Semestre> cmbSemestre;
    
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
    private Label lblErrorSemestre;
    
    private IObservador observador;
    private Tutorado tutoradoEdicion;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCombos();
        aplicarRestricciones();
    }    
    
    public void inicializarDatos(IObservador observador, Tutorado tutorado){
        this.observador = observador;
        this.tutoradoEdicion = tutorado;
        
        if(tutorado != null){
            btnRegistrar.setText("Actualizar");
            cargarDatosEdicion();
        }
    }
    
    private void cargarDatosEdicion(){
        txtMatricula.setText(tutoradoEdicion.getMatricula());
        txtMatricula.setEditable(false); 
        txtNombre.setText(tutoradoEdicion.getNombre());
        txtApPaterno.setText(tutoradoEdicion.getApellidoPaterno());
        txtApMaterno.setText(tutoradoEdicion.getApellidoMaterno());
        txtCorreo.setText(tutoradoEdicion.getCorreo());
        
        for(ProgramaEducativo pe : cmbProgramaEducativo.getItems()){
            if(pe.getIdProgramaEducativo() == tutoradoEdicion.getIdProgramaEducativo()){
                cmbProgramaEducativo.setValue(pe);
                break;
            }
        }
        
        for(Semestre sem : cmbSemestre.getItems()){
            if(sem.getIdSemestre() == tutoradoEdicion.getIdSemestre()){
                cmbSemestre.setValue(sem);
                break;
            }
        }
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
        boolean confirmacion = Utilidades.mostrarAlertaConfirmacion("Confirmar cancelación", 
                "¿Está seguro de cancelar el registro?", 
                "Los cambios no se guardarán");
        if(confirmacion){
            cerrarVentana();
        }
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        limpiarMensajesError();
        if(sonCamposValidos()){
            if(tutoradoEdicion == null){
                registrarTutorado();
            }else{
                editarTutorado();
            }
        }
    }
    
    private void registrarTutorado(){
        Tutorado nuevoTutorado = obtenerTutoradoVista();
        
        if(!validarMatricula(nuevoTutorado.getMatricula()))
            return;
        if(!validarCorreo(nuevoTutorado.getCorreo()))
            return;
        
        HashMap<String, Object> respuesta = TutoradoImpl.registrarTutorado(nuevoTutorado);
        if(!(boolean)respuesta.get("error")){
            Utilidades.mostrarAlerta("Registro exitoso", (String)respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Registro", nuevoTutorado.getNombre());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlerta("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private void editarTutorado(){
        Tutorado tutoradoEditado = obtenerTutoradoVista();
        tutoradoEditado.setIdTutorado(tutoradoEdicion.getIdTutorado());
        
        if(!tutoradoEditado.getCorreo().equals(tutoradoEdicion.getCorreo())){
            if(!validarCorreo(tutoradoEditado.getCorreo())) return;
        }
        
        HashMap<String, Object> respuesta = TutoradoImpl.editarTutorado(tutoradoEditado);
        if(!(boolean)respuesta.get("error")){
            Utilidades.mostrarAlerta("Edición exitosa", (String)respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Edicion", tutoradoEditado.getNombre());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlerta("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private Tutorado obtenerTutoradoVista(){
        Tutorado tutorado = new Tutorado();
        tutorado.setMatricula(txtMatricula.getText().toUpperCase());
        tutorado.setNombre(txtNombre.getText().trim());
        tutorado.setApellidoPaterno(txtApPaterno.getText().trim());
        tutorado.setApellidoMaterno(txtApMaterno.getText().trim());
        tutorado.setCorreo(txtCorreo.getText());
        
        if(cmbProgramaEducativo.getValue() != null){
            tutorado.setIdProgramaEducativo(cmbProgramaEducativo.getValue().getIdProgramaEducativo());
        }
        
        if(cmbSemestre.getValue() != null){
            tutorado.setIdSemestre(cmbSemestre.getValue().getIdSemestre());
        }
        
        return tutorado;
    }
    
    private boolean sonCamposValidos(){
        boolean valido = true;
        
        if(txtMatricula.getText().isEmpty()){
            lblErrorMatricula.setText(CAMPO_OBLIGATORIO);
            valido = false;
        }
        if(txtNombre.getText().isEmpty()){
            lblErrorNombre.setText(CAMPO_OBLIGATORIO);
            valido = false;
        }
        if(txtApPaterno.getText().isEmpty()){
            lblErrorApPaterno.setText(CAMPO_OBLIGATORIO);
            valido = false;
        }
        if(txtCorreo.getText().isEmpty()){
            lblErrorCorreo.setText(CAMPO_OBLIGATORIO);
            valido = false;
        }
        if(cmbProgramaEducativo.getSelectionModel().isEmpty()){
            lblErrorProgramaEducativo.setText(CAMPO_OBLIGATORIO);
            valido = false;
        }
        if(cmbSemestre.getSelectionModel().isEmpty()){
            lblErrorSemestre.setText(CAMPO_OBLIGATORIO);
            valido = false;
        }
        
        if(txtCorreo.getText().isEmpty()){ 
             lblErrorCorreo.setText(CAMPO_OBLIGATORIO);
             valido = false;
        }
        if(!esFormatoValido()){
            valido = false;
        }
        
        return valido;
    }
    
    private boolean validarMatricula(String matricula){
        HashMap<String, Object> respuesta = TutoradoImpl.verificarMatricula(matricula);
        if(!(boolean)respuesta.get("error") && (boolean)respuesta.get("existe")){
            lblErrorMatricula.setText(respuesta.get("etiqueta").toString());
            return false;
        }
        return true;
    }
    
    private boolean validarCorreo(String correo){
        HashMap<String, Object> respuesta = TutoradoImpl.verificarCorreo(correo);
        if(!(boolean)respuesta.get("error") && (boolean)respuesta.get("existe")){
            lblErrorCorreo.setText(respuesta.get("etiqueta").toString());
            return false;
        }
        return true;
    }
    
    private boolean esFormatoValido(){
        boolean valido = true;
        String matricula = txtMatricula.getText();
        String correo = txtCorreo.getText();
        String correoValido = "z"+ matricula + "@estudiantes.uv.mx";
        
        if(!matricula.matches("^[sS]\\d{8}$") && !matricula.isEmpty()){
            valido = false;
            lblErrorMatricula.setText("Formato de Matrícula no válido");
        }
        if(!correo.equalsIgnoreCase(correoValido) && !correo.isEmpty()){
            valido=false;
            lblErrorCorreo.setText("Formato de correo no valido");
        }
        return valido;
    }
    
    private void limpiarMensajesError(){
        lblErrorMatricula.setText("");
        lblErrorNombre.setText("");
        lblErrorApPaterno.setText("");
        lblErrorApMaterno.setText("");
        lblErrorCorreo.setText("");
        lblErrorProgramaEducativo.setText("");
        lblErrorSemestre.setText("");
    }
    
    private void cargarCombos(){
        ObservableList<ProgramaEducativo> listaProgramas;
        ObservableList<Semestre> listaSemestres;
        HashMap<String, Object> respuestaProgramaEducativo = ProgramaEducativoImpl.obtenerProgramasEducativos();
        if(!(boolean)respuestaProgramaEducativo.get("error")){
            listaProgramas = FXCollections.observableArrayList((List<ProgramaEducativo>) respuestaProgramaEducativo.get("programas"));
            cmbProgramaEducativo.setItems(listaProgramas);
        }
        
        HashMap<String, Object> respuestaSemestre = CatalogoImpl.obtenerSemestres();
        if(!(boolean)respuestaSemestre.get("error")){
            listaSemestres = FXCollections.observableArrayList((List<Semestre>) respuestaSemestre.get("semestres"));
            cmbSemestre.setItems(listaSemestres);
        }
    }
    
    private void aplicarRestricciones(){
        RestriccionCampos.limitarLongitud(txtMatricula, LIMITE_MATRICULA);
        RestriccionCampos.limitarLongitud(txtNombre, LIMITE_CAMPO_NOMBRES);
        RestriccionCampos.limitarLongitud(txtApPaterno, LIMITE_CAMPO_NOMBRES);
        RestriccionCampos.limitarLongitud(txtApMaterno, LIMITE_CAMPO_NOMBRES);
        RestriccionCampos.limitarLongitud(txtCorreo, LIMITE_CAMPO_CORREO);
        RestriccionCampos.soloLetras(txtNombre);
        RestriccionCampos.soloLetras(txtApPaterno);
        RestriccionCampos.soloLetras(txtApMaterno);
        RestriccionCampos.soloCaracteresValidosCorreo(txtCorreo);
        
    }
    
    private void cerrarVentana(){
        ((Stage) txtMatricula.getScene().getWindow()).close();
    }
}