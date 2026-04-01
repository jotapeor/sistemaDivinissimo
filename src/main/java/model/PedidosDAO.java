/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connection.Conexao;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author João Paulo
 */
public class PedidosDAO {

    public void efetuarPedido(PedidosBean pedidos) {
        try {
            // Abre a conexão com o banco de dados através da classe Conexao
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;

            // O uso de '?' (placeholders) impede ataques de SQL Injection
            String sql = "insert into pedidos (idUsuario, tipoLanche, quantidade, formaPagamento, valorTotal, statusPedido) values (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);

            // Substitui cada '?' pelo valor real vindo do objeto PedidosBean
            stmt.setInt(1, pedidos.getIdUsuario());
            stmt.setString(2, pedidos.getTipoLanche());
            stmt.setInt(3, pedidos.getQuantidade());
            stmt.setString(4, pedidos.getFormaPagamento());
            stmt.setDouble(5, pedidos.getValorTotal());
            stmt.setString(6, pedidos.getStatusPedido());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao registrar pedido: " + e.getMessage());
        }
    }

    /**
     * Retorna uma lista de todos os pedidos realizados no sistema. Utiliza
     * INNER JOIN para buscar o nome do usuário que pertence a outra tabela.
     */
    public List<PedidosBean> listarPedidos() {
        List<PedidosBean> lista = new ArrayList<>();
        try {
            Connection conn = Conexao.conectar();
            // A consulta une a tabela pedidos (p) com usuarios (u) usando o ID como vínculo
            String sql = "SELECT p.id, u.nome, p.tipoLanche, p.quantidade, p.formaPagamento, p.valorTotal, p.statusPedido "
                    + "FROM pedidos p INNER JOIN usuarios u ON p.idUsuario = u.id";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(); // Executa a busca e guarda o resultado no ResultSet

            // Enquanto houver linhas no resultado da consulta, cria um novo objeto e adiciona na lista
            while (rs.next()) {
                PedidosBean p = new PedidosBean();
                p.setId(rs.getInt("id"));
                p.setNomeCliente(rs.getString("nome")); // Nome vindo da tabela de usuários
                p.setTipoLanche(rs.getString("tipoLanche"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setFormaPagamento(rs.getString("formaPagamento"));
                p.setValorTotal(rs.getDouble("valorTotal"));
                p.setStatusPedido(rs.getString("statusPedido"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar lista: " + e.getMessage());
        }
        return lista;
    }

    public void atualizarStatus(int id, String status) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "UPDATE pedidos SET statusPedido = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, status);
            stmt.setInt(2, id);

            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePedido(int id) {
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM pedidos WHERE id = ?");
            stmt.setInt(1, id);

            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PedidosBean> listarPedidosCliente() {
        List<PedidosBean> lista = new ArrayList<>();
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT p.id, p.tipoLanche, p.quantidade, p.formaPagamento, p.statusPedido FROM pedidos p WHERE idUsuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Pega o ID do usuário que logou no sistema
            stmt.setInt(1, SessaoUsuario.usuarioLogado.getId());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PedidosBean p = new PedidosBean();
                p.setId(rs.getInt("id"));
                p.setTipoLanche(rs.getString("tipoLanche"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setFormaPagamento(rs.getString("formaPagamento"));
                p.setStatusPedido(rs.getString("statusPedido"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao buscar seus pedidos: " + e.getMessage());
        }
        return lista;
    }

    public void atualizarPedido(int id, String tipoLanche, int quantidade, String formaPagamento) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "UPDATE pedidos SET tipoLanche = ?, quantidade = ?, formaPagamento = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, tipoLanche);
            stmt.setInt(2, quantidade);
            stmt.setString(3, formaPagamento);
            stmt.setInt(4, id);

            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
