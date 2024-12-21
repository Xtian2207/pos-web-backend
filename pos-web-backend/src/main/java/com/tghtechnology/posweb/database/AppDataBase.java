package com.tghtechnology.posweb.database;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class AppDataBase {

    private static AppDataBase instance;

    private Connection conexion = null;

    // Parametros de la conexion
    private String user = "root";
    private String pass = "12345";
    private String bd = "POS";
    private String ip = "127.0.0.1";
    private String puerto ="3306";

    private String cadena = "jdbc:mysql://"+ ip + ":" + puerto + "/" + bd;

    private AppDataBase(){}

    public static AppDataBase getInstance(){
        if (instance == null){
            instance = new AppDataBase();
        }
        return instance;
    }

    public Connection startConnection(){
        try{
            if (conexion == null || conexion.isClosed()){
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(cadena, user, pass);
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error en la conexion a la bd: "+e.getMessage());
        }
        return conexion;
    }

    public void closeConnection(){
        try{
            if (conexion != null && !conexion.isClosed()){
                conexion.close();
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al cerrar sesion con la bd: "+e.getMessage());
        }
    }   
}
