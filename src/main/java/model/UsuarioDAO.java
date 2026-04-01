/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connection.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author João Paulo
 */
public class UsuarioDAO {

    public void cadastrar(UsuarioBean usuario) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "insert into usuarios (nome, email, senha, admin) values (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            // Hardcoded como 'false' para garantir que ninguém se cadastre como admin por conta própria
            stmt.setBoolean(4, false);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UsuarioBean logar(String usuario, String senha) {
        UsuarioBean user = new UsuarioBean();
        try {
            Connection conn = Conexao.conectar();
            // Busca um registro que coincida exatamente com o e-mail E a senha informados
            String sql = "select * from usuarios where email = ? and senha = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, usuario);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            // Se o banco retornar uma linha, as credenciais são válidas
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setNome(rs.getString("nome"));
                user.setEmail(rs.getString("email"));
                user.setSenha(rs.getString("senha"));
                user.setAdmin(rs.getBoolean("admin")); // Define se o usuário tem poderes de gerente
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean nomeExiste(String nome) {
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement("SELECT id FROM usuarios WHERE nome = ?");
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            // rs.next() retorna verdadeiro se a consulta encontrou pelo menos um registro
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean emailExiste(String email) {
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement("SELECT id FROM usuarios WHERE email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
