/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author João Paulo
 */
public class Conexao {

    private static final String url = "jdbc:mysql://localhost:3306/db_divinissimo";
    private static final String usuario = "root";
    private static final String senha = "joaopauloor21";

    public static Connection conectar() {
        Connection conn = null;
        try {
            conn = (Connection) DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void testarConexao() {
        Connection conn = conectar();
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados!");
        }  else {
            //JOptionPane.showMessageDialog(null, "Conectado com sucesso!");
            System.out.println("Conectado com sucesso!");
        } 
    }
}
