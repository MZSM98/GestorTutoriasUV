package com.gtuv.controlador;

import com.gtuv.dominio.ProgramaEducativoImpl;
import com.gtuv.dominio.UsuarioImpl;
import com.gtuv.interfaces.IObservador;
import com.gtuv.modelo.pojo.ProgramaEducativo;
import com.gtuv.modelo.pojo.Usuario;
import com.gtuv.utlidad.Encriptacion;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormularioUsuarioController implements Initializable {
    
    private static final int LIMITE_MIN_CONTRASENIA = 6;
    private static final int LIMITE_MIN_NO_TRABAJADOR = 4;
    private static final int LIMITE_CAMPO_NO_TRABAJADOR = 6;
    private static final int LIMITE_CAMPO_CORREO = 100;
    private static final int LIMITE_CAMPO_NOMBRE = 255;
    private static final int LIMITE_CAMPO_AP_PATERNO = 255;
    private static final int LIMITE_CAMPO_AP_MATERNO = 255;
    private static final String CAMPO_OBLIGATORIO = "Campo obligatorio";
            
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
    private ComboBox<ProgramaEducativo> cmbProgramaEducativo;
    
    private IObservador observador;
    private Usuario usuarioEdicion;
    private ObservableList<ProgramaEducativo> programasEducativos;
    @FXML
    private CheckBox chkJefeCarrera;
    @FXML
    private CheckBox chkCoordinador;
    @FXML
    private TextField txtContrasenia;
    @FXML
    private TextField txtConfirmaContrasenia;
    @FXML
    private Label lblErrorContrasena;
    @FXML
    private Label lblErrorConfirmarContrasena;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarProgramasEducativos();
        aplicarRestricciones();
        
    }    

    @FXML
    private void activarOpcionCoordinador(ActionEvent event) {
        if(chkTutor.isSelected()){
            chkCoordinador.setDisable(false);
        }
        if(!chkTutor.isSelected()){
            chkCoordinador.setDisable(true);
            chkCoordinador.setSelected(false);
            activarProgramaEducativo();
        }
    }
    
    public void inicializarDatos(IObservador observador, Usuario usuario){
        this.observador = observador;
        this.usuarioEdicion = usuario;
        chkCoordinador.setDisable(true);
        cmbProgramaEducativo.setDisable(true);

        if(usuario != null){
            txtNoTrabajador.setText(usuario.getNoTrabajador());
            txtNoTrabajador.setEditable(false); 
            txtNombre.setText(usuario.getNombre());
            txtApPaterno.setText(usuario.getApellidoPaterno());
            txtApMaterno.setText(usuario.getApellidoMaterno());
            txtCorreo.setText(usuario.getCorreo());
            chkAdmnistrador.setSelected(usuario.isEsAdministrador());
            chkTutor.setSelected(usuario.isEsTutor());

            if(usuario.isEsTutor()){
                chkCoordinador.setDisable(false);
            }

            cargarRolProgramaEducativo(usuario.getIdUsuario());
        }
    }
    
    @FXML
    private void clicRegistrar(ActionEvent event){
        limpiarMensajesError();
        if(sonCamposValidos()){
            if(usuarioEdicion == null){
                registrarUsuario();
            }else{
                editarUsuario();
            }
        }
    }
    
    private void registrarUsuario() {
        limpiarMensajesError();
        Usuario usuarioNuevo = obtenerUsuario();

        if (!validarResponsabilidad())
            return;
        if (!validarCorreo(usuarioNuevo.getCorreo()))
            return;
        if (!validarNumeroTrabajador(usuarioNuevo.getNoTrabajador()))
            return;
        if (chkJefeCarrera.isSelected() && !esPosibleSustituirJefe(cmbProgramaEducativo.getValue())) 
            return;
        if (chkCoordinador.isSelected() && !esPosibleSustituirCoordinador(cmbProgramaEducativo.getValue())) 
            return;

        HashMap<String, Object> respuesta = UsuarioImpl.registrarUsuario(usuarioNuevo);
        if (!(boolean) respuesta.get("error")) {
            procesarAutoridad((int) respuesta.get("idUsuario"));
            Utilidades.mostrarAlerta("Registro exitoso", (String) respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Registro", usuarioNuevo.getNombre());
            cerrarVentana();
        } else {
            Utilidades.mostrarAlerta("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private void editarUsuario(){
        limpiarMensajesError();
        
        Usuario usuarioEditado = obtenerUsuario();
        usuarioEditado.setIdUsuario(usuarioEdicion.getIdUsuario());
        if (!validarResponsabilidad())
            return;
        if (chkJefeCarrera.isSelected() && !esPosibleSustituirJefe(cmbProgramaEducativo.getValue())) {
            return;
        }
        if (chkCoordinador.isSelected() && !esPosibleSustituirCoordinador(cmbProgramaEducativo.getValue())) {
            return;
        }
        HashMap<String, Object> respuesta = UsuarioImpl.editarUsuario(usuarioEditado);
        if(!(boolean)respuesta.get("error")){
            procesarAutoridad(usuarioEdicion.getIdUsuario());
            Utilidades.mostrarAlerta("Edición exitosa", (String)respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Edicion", usuarioEditado.getNombre());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlerta("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private void procesarAutoridad(int idUsuario) {
        if (cmbProgramaEducativo.getSelectionModel().getSelectedItem() != null) {
            ProgramaEducativo programa = cmbProgramaEducativo.getSelectionModel().getSelectedItem();
            if (chkJefeCarrera.isSelected()) {
                ProgramaEducativoImpl.sustituirJefeCarrera(programa.getIdProgramaEducativo(), idUsuario);
            } else if (chkCoordinador.isSelected()) {
                ProgramaEducativoImpl.asignarCoordinador(programa.getIdProgramaEducativo(), idUsuario);
            }
        }
    }
    
    private Usuario obtenerUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNoTrabajador(txtNoTrabajador.getText());
        usuario.setNombre(txtNombre.getText());
        usuario.setApellidoPaterno(txtApPaterno.getText());
        usuario.setApellidoMaterno(txtApMaterno.getText());
        usuario.setCorreo(txtCorreo.getText());
        
        if (usuarioEdicion != null && txtContrasenia.getText().isEmpty()) {
            usuario.setContrasenia(usuarioEdicion.getContrasenia());
        } else {
            String passEncriptada = Encriptacion.hashPassword(txtContrasenia.getText());
            usuario.setContrasenia(passEncriptada);
        }
        
        usuario.setEsAdministrador(chkAdmnistrador.isSelected());
        usuario.setEsTutor(chkTutor.isSelected());
        return usuario;
    }
    
    private boolean sonCamposValidos(){
        boolean valido = true;
        if(txtNoTrabajador.getText().isEmpty()){
            lblErrorNumTrabajador.setText(CAMPO_OBLIGATORIO);
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
        if(txtApMaterno.getText().isEmpty()){
            lblErrorApMaterno.setText(CAMPO_OBLIGATORIO);
            valido = false;
        }
        if(txtCorreo.getText().isEmpty()){
            lblErrorCorreo.setText(CAMPO_OBLIGATORIO);
            valido = false;
        }
        if (usuarioEdicion == null && txtContrasenia.getText().isEmpty()) {
            lblErrorContrasena.setText(CAMPO_OBLIGATORIO);
            valido = false;
        }
        if (!txtContrasenia.getText().isEmpty() && txtConfirmaContrasenia.getText().isEmpty()) {
            lblErrorConfirmarContrasena.setText(CAMPO_OBLIGATORIO);
            valido = false;
        }
        if((chkJefeCarrera.isSelected() || chkCoordinador.isSelected()) && cmbProgramaEducativo.getSelectionModel().isEmpty()){
            lblErrorProgramaEducativo.setText("Debe elegir un programa educativo");
            valido = false;
        }
        
        if(!formatoValido()){
            valido = false;
        }
        return valido;
    }
    
    private void limpiarMensajesError(){
        lblErrorNumTrabajador.setText("");
        lblErrorNombre.setText("");
        lblErrorApPaterno.setText("");
        lblErrorApMaterno.setText("");
        lblErrorProgramaEducativo.setText("");
        lblErrorConfirmarContrasena.setText("");
        lblErrorCorreo.setText("");
        lblErrorContrasena.setText("");
    }
    
    private void cargarProgramasEducativos(){
        HashMap<String, Object> respuesta = ProgramaEducativoImpl.obtenerProgramasEducativos();
        if(!(boolean)respuesta.get("error")){
            List<ProgramaEducativo> listaPE = (List<ProgramaEducativo>) respuesta.get("programas");
            programasEducativos = FXCollections.observableArrayList();
            programasEducativos.addAll(listaPE);
            cmbProgramaEducativo.setItems(programasEducativos);
        }else{
            Utilidades.mostrarAlerta("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    
    private void cerrarVentana(){
        ((Stage) txtNoTrabajador.getScene().getWindow()).close();
    }
    
    private boolean validarCorreo(String correo){
        boolean valido = false;
        HashMap<String, Object> respuesta = UsuarioImpl.verificarCorreo(correo);
        
        if(!(boolean)respuesta.get("error") && !(boolean)respuesta.get("existe")){
                valido = true;
        }else{
            lblErrorCorreo.setText(respuesta.get("etiqueta").toString());
        }
        return valido;
    }
    
    private boolean validarNumeroTrabajador(String noTrabajador){
        boolean valido = false;
        HashMap<String, Object> respuesta = UsuarioImpl.verificarNumeroTrabajador(noTrabajador);
        
        if(!(boolean)respuesta.get("error") && !(boolean)respuesta.get("existe")){
                valido = true;
        }else{
            lblErrorNumTrabajador.setText(respuesta.get("etiqueta").toString());
        }
        return valido;
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
        
        boolean confirmacion = Utilidades.mostrarAlertaConfirmacion("Confirmar cancelación de registro",
                "¿Está seguro de cancelar el registro?", "Los cambios no se guardarán");
        
        if (confirmacion){
            cerrarVentana();
        }
    }
    
    private void activarProgramaEducativo() {
        
        if(chkJefeCarrera.isSelected() || chkCoordinador.isSelected()){
            cmbProgramaEducativo.setDisable(false);
        }
        if(!chkJefeCarrera.isSelected() && !chkCoordinador.isSelected()){
            cmbProgramaEducativo.setDisable(true);
            cmbProgramaEducativo.setValue(null);
        }
    }

    @FXML
    private void clicCheckJefeCarrera(ActionEvent event) {
        activarProgramaEducativo();
        if(chkJefeCarrera.isSelected()){
            chkCoordinador.setSelected(false);
        }
    }

    @FXML
    private void clicCheckCoordinador(ActionEvent event) {
        activarProgramaEducativo();
        if(chkCoordinador.isSelected()){
            chkJefeCarrera.setSelected(false);
        }
    }
    
    private boolean formatoValido(){
        boolean valido = true;
        String noTrabajador = txtNoTrabajador.getText();
        String correo = txtCorreo.getText();
        String contrasenia = txtContrasenia.getText();
        String contrasenaconfirmacion = txtConfirmaContrasenia.getText();
        
        if(noTrabajador.length() <= LIMITE_MIN_NO_TRABAJADOR && !noTrabajador.isEmpty()){
            valido = false;
            lblErrorNumTrabajador.setText("Formato de No. de trabajador no valido");
        }
        if(!correo.matches("^[a-zA-Z0-9._-]+@uv\\.mx$") && !correo.isEmpty()){
            valido=false;
            lblErrorCorreo.setText("Formato de correo no valido");
        }
        if (!contrasenia.isEmpty() && contrasenia.length() < LIMITE_MIN_CONTRASENIA) {
            lblErrorContrasena.setText("Debe tener al menos 6 caracteres");
            valido = false;
        }
        if (!contrasenia.isEmpty() && !contrasenia.equals(contrasenaconfirmacion)) {
            valido = false;
            lblErrorContrasena.setText("Las contraseñas no coinciden");
        }
        return valido;
    }
    
    private boolean validarResponsabilidad(){
        boolean valido = true;
        if(!chkAdmnistrador.isSelected() && !chkCoordinador.isSelected() & !chkTutor.isSelected() & !chkJefeCarrera.isSelected()){
            valido = false;
            Utilidades.mostrarAlerta("Responsabilidades Obligatorias", "Debe seleccionar al menos una responsabilidad"
                    + " para poder continuar con el registro", Alert.AlertType.WARNING);
        }
        return valido;
    }
    
    private void aplicarRestricciones(){
        RestriccionCampos.limitarCantidadNumeros(txtNoTrabajador, LIMITE_CAMPO_NO_TRABAJADOR);
        RestriccionCampos.limitarLongitud(txtNombre, LIMITE_CAMPO_NOMBRE);
        RestriccionCampos.limitarLongitud(txtCorreo, LIMITE_CAMPO_CORREO);
        RestriccionCampos.limitarLongitud(txtApPaterno, LIMITE_CAMPO_AP_PATERNO);
        RestriccionCampos.limitarLongitud(txtApMaterno, LIMITE_CAMPO_AP_MATERNO);
        RestriccionCampos.soloLetras(txtNombre);
        RestriccionCampos.soloLetras(txtApMaterno);
        RestriccionCampos.soloLetras(txtApPaterno);
    }
    
    private boolean esPosibleSustituirJefe(ProgramaEducativo programa) {
        HashMap<String, Object> resp = ProgramaEducativoImpl.obtenerJefeCarrera(programa.getIdProgramaEducativo());
        if ((boolean) resp.get("error")) return false;

        Usuario actual = (Usuario) resp.get("jefeCarrera");

        if (actual != null && usuarioEdicion != null && actual.getNoTrabajador().equals(usuarioEdicion.getNoTrabajador())) {
            return true; 
        }
        if (actual != null) {
            String mensaje = "El programa ya cuenta con el Jefe de Carrera:\n" + actual.getNombre() + " " + actual.getApellidoPaterno() +
                         "\n Número de trabajador: "+ actual.getNoTrabajador()+
                         "\n¿Desea sustituirlo?";
            return Utilidades.mostrarAlertaConfirmacion("Conflicto de Asignación", "Jefe existente", mensaje);
        }
        return true;
    }
    
    private boolean esPosibleSustituirCoordinador(ProgramaEducativo programa) {
        HashMap<String, Object> resp = ProgramaEducativoImpl.obtenerCoordinador(programa.getIdProgramaEducativo());
        if ((boolean) resp.get("error")) return false;

        Usuario actual = (Usuario) resp.get("coordinador");

        if (actual != null && usuarioEdicion != null && actual.getNoTrabajador().equals(usuarioEdicion.getNoTrabajador())) {
            return true;
        }

        if (actual != null) {
            String mensaje = "El programa ya cuenta con el Coordinador:\n" + actual.getNombre() + " " + actual.getApellidoPaterno() +
                    "\n Número de trabajador: " + actual.getNoTrabajador()+
                         "\n¿Desea sustituirlo?";
            return Utilidades.mostrarAlertaConfirmacion("Conflicto de Asignación", "Coordinador existente", mensaje);
        }
        return true;
    }
    
    private void cargarRolProgramaEducativo(int idUsuario) {
        HashMap<String, Object> respuesta = ProgramaEducativoImpl.obtenerProgramaPorUsuario(idUsuario);
        if (!(boolean) respuesta.get("error")) {
            ProgramaEducativo programa = (ProgramaEducativo) respuesta.get("programa");
            if (programa != null) {
                for(ProgramaEducativo pe : cmbProgramaEducativo.getItems()){
                    if(pe.getIdProgramaEducativo() == programa.getIdProgramaEducativo()){
                        cmbProgramaEducativo.setValue(pe);
                        break;
                    }
                }
                cmbProgramaEducativo.setDisable(false);

                if (programa.getIdJefeCarrera() == idUsuario) {
                    chkJefeCarrera.setSelected(true);
                }
                if (programa.getIdCoordinador() == idUsuario) {
                    chkCoordinador.setSelected(true);
                }
            }
        }
    }
}