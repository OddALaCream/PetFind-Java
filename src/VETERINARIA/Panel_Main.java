package VETERINARIA;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Panel_Main extends JFrame {

    private CardLayout card;
    private JPanel panelContenedor;

    // Variables de sesión
    private int idPersonaActual;
    private String usuarioActual;
    private int tipoActual; // 1 = veterinario, 2 = recepcionista

    // Sistema de datos compartidos entre paneles
    private Map<String, Object> datosCompartidos;
    
    // Referencias a los paneles (para poder llamar sus métodos)
    private Panel_Ficha panelFicha;

    public Panel_Main() {

        setTitle("Sistema Veterinaria - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);

        // Inicializar el mapa de datos compartidos
        datosCompartidos = new HashMap<>();

        card = new CardLayout();
        panelContenedor = new JPanel(card);

        // Paneles principales
        Panel_Login panelLogin = new Panel_Login(this);
        panelContenedor.add(panelLogin.Crear_Panel(), "login");

        Panel_Cuenta panelCuenta = new Panel_Cuenta(this);
        panelContenedor.add(panelCuenta.Crear_Panel(), "cuenta");

        Panel_Recepcionista_Opciones panelRecepcionistaOpciones = new Panel_Recepcionista_Opciones(this);
        panelContenedor.add(panelRecepcionistaOpciones.Crear_Panel(), "recepcionista");

        Panel_Busqueda panelBusqueda2 = new Panel_Busqueda(this);
        panelContenedor.add(panelBusqueda2.Crear_Panel(), "buscar");
        
        Panel_Agendar2 panelAgendar2 = new Panel_Agendar2(this);
        panelContenedor.add(panelAgendar2.Crear_Panel(), "agendar2");
        Panel_Agendar3 panelAgendar3 = new Panel_Agendar3(this);
        panelContenedor.add(panelAgendar3.Crear_Panel(), "agendar3");
        Panel_Agendar4 panelAgendar4 = new Panel_Agendar4(this);
        panelContenedor.add(panelAgendar4.Crear_Panel(), "agendar4");
        Panel_Agendar5 panelAgendar5 = new Panel_Agendar5(this);
		panelContenedor.add(panelAgendar5.Crear_Panel(), "agendar5");

        Panel_Registrar1 panelRegistrar1 = new Panel_Registrar1(this);
        panelContenedor.add(panelRegistrar1.Crear_Panel(), "registrar1");
        
        // Guardar referencia a Panel_Ficha para poder actualizar sus datos
        panelFicha = new Panel_Ficha(this);
        panelContenedor.add(panelFicha.Crear_Panel(), "ficha");

        Panel_Medico_Opciones panelMedicoOpciones = new Panel_Medico_Opciones(this);
        panelContenedor.add(panelMedicoOpciones.Crear_Panel(), "veterinario");

        Panel_Diagnostico1 panelDiagnostico1 = new Panel_Diagnostico1(this);
        panelContenedor.add(panelDiagnostico1.Crear_Panel(), "diagnostico1");
        Panel_Diagnostico2 panelDiagnostico2 = new Panel_Diagnostico2(this);
        panelContenedor.add(panelDiagnostico2.Crear_Panel(), "diagnostico2");

        Panel_Historial1 panelHistorial1 = new Panel_Historial1(this);
        panelContenedor.add(panelHistorial1.Crear_Panel(), "historial1");
        Panel_Historial2 panelHistorial2 = new Panel_Historial2(this);
        panelContenedor.add(panelHistorial2.Crear_Panel(), "historial2");
        Panel_Historial3 panelHistorial3 = new Panel_Historial3(this);
        panelContenedor.add(panelHistorial3.Crear_Panel(), "historial3");
        Panel_Historial4 panelHistorial4 = new Panel_Historial4(this);
        panelContenedor.add(panelHistorial4.Crear_Panel(), "historial4");

        Panel_Horario panelHorario = new Panel_Horario(this);
        panelContenedor.add(panelHorario.Crear_Panel(), "horario");

        add(panelContenedor);

        mostrarPanel("login");
        setVisible(true);
    }

    // Mostrar panel por nombre
    public void mostrarPanel(String nombre) {
        System.out.println("mostrarPanel: " + nombre);
        
        // Si vamos a mostrar el panel ficha, actualizar sus datos primero
        if (nombre.equals("ficha")) {
            System.out.println("Actualizando Panel_Ficha antes de mostrarlo...");
            panelFicha.actualizarDatos();
        }
        
        card.show(panelContenedor, nombre);
    }

    // --- Métodos de sesión ---
    public void setSesion(int idPersona, String usuario, int tipo) {
        this.idPersonaActual = idPersona;
        this.usuarioActual = usuario;
        this.tipoActual = tipo;
    }

    public int getIdPersonaActual() {
        return idPersonaActual;
    }

    public String getUsuarioActual() {
        return usuarioActual;
    }

    public int getTipoActual() {
        return tipoActual;
    }

    public void cerrarSesion() {
        this.idPersonaActual = -1;
        this.usuarioActual = null;
        this.tipoActual = -1;
        datosCompartidos.clear(); // Limpiar datos al cerrar sesión
        mostrarPanel("login");
    }

    // ===== MÉTODOS PARA COMPARTIR DATOS ENTRE PANELES =====
    
    /**
     * Guarda un dato con una clave específica
     * @param clave Identificador del dato (ej: "idPropietario", "codigoFicha")
     * @param valor El valor a guardar (puede ser cualquier tipo de objeto)
     */
    public void setDatoCompartido(String clave, Object valor) {
        datosCompartidos.put(clave, valor);
    }

    /**
     * Obtiene un dato guardado
     * @param clave Identificador del dato
     * @return El valor guardado, o null si no existe
     */
    public Object getDatoCompartido(String clave) {
        return datosCompartidos.get(clave);
    }

    /**
     * Obtiene un dato como entero
     * @param clave Identificador del dato
     * @param valorPorDefecto Valor a devolver si no existe o no es entero
     * @return El valor como entero
     */
    public int getDatoCompartidoInt(String clave, int valorPorDefecto) {
        Object valor = datosCompartidos.get(clave);
        if (valor instanceof Integer) {
            return (Integer) valor;
        }
        return valorPorDefecto;
    }

    /**
     * Obtiene un dato como String
     * @param clave Identificador del dato
     * @param valorPorDefecto Valor a devolver si no existe
     * @return El valor como String
     */
    public String getDatoCompartidoString(String clave, String valorPorDefecto) {
        Object valor = datosCompartidos.get(clave);
        if (valor instanceof String) {
            return (String) valor;
        }
        return valorPorDefecto;
    }

    /**
     * Elimina un dato específico
     * @param clave Identificador del dato a eliminar
     */
    public void eliminarDatoCompartido(String clave) {
        datosCompartidos.remove(clave);
    }

    /**
     * Limpia todos los datos compartidos
     */
    public void limpiarDatosCompartidos() {
        datosCompartidos.clear();
    }

    /**
     * Verifica si existe un dato con la clave especificada
     * @param clave Identificador del dato
     * @return true si existe, false si no
     */
    public boolean existeDatoCompartido(String clave) {
        return datosCompartidos.containsKey(clave);
    }

    // Getters y setters originales
    public CardLayout getCard() {
        return card;
    }

    public void setCard(CardLayout card) {
        this.card = card;
    }

    public JPanel getPanelContenedor() {
        return panelContenedor;
    }

    public void setPanelContenedor(JPanel panelContenedor) {
        this.panelContenedor = panelContenedor;
    }

    public void setIdPersonaActual(int idPersonaActual) {
        this.idPersonaActual = idPersonaActual;
    }

    public void setUsuarioActual(String usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public void setTipoActual(int tipoActual) {
        this.tipoActual = tipoActual;
    }
}