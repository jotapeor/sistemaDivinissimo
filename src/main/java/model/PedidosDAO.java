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
import model.PedidosBean;

/**
 *
 * @author João Paulo
 */
public class PedidosDAO {

    public void efetuarPedido(PedidosBean pedidos) {
        try {

            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;

            stmt = conn.prepareStatement("insert into pedidos (idUsuario, tipoLanche, quantidade, formaPagamento, valorTotal, statusPedido) values (?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, pedidos.getIdUsuario());
            stmt.setString(2, pedidos.getTipoLanche());
            stmt.setInt(3, pedidos.getQuantidade());
            stmt.setString(4, pedidos.getFormaPagamento());
            stmt.setDouble(5, pedidos.getValorTotal());
            stmt.setString(6, pedidos.getStatusPedido());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    public List<PedidosBean> listarPedidos() {
        List<PedidosBean> lista = new ArrayList<>();
        try {

            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement("SELECT p.id, u.nome, p.tipoLanche, p.quantidade, p.formaPagamento, p.valorTotal, p.statusPedido " + "FROM pedidos p INNER JOIN usuarios u ON p.idUsuario = u.id");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PedidosBean p = new PedidosBean();
                p.setId(rs.getInt("id"));
                p.setNomeCliente(rs.getString("nome"));
                p.setTipoLanche(rs.getString("tipoLanche"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setFormaPagamento(rs.getString("formaPagamento"));
                p.setValorTotal(rs.getDouble("valorTotal"));
                p.setStatusPedido(rs.getString("statusPedido"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
        return lista;
    }

    public void atualizarStatus(int id, String status) {
        try {

            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;

            stmt = conn.prepareStatement("UPDATE pedidos SET statusPedido = ? WHERE id = ?");
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
            PreparedStatement stmt = null;

            stmt = conn.prepareStatement("DELETE FROM pedidos WHERE id = ?");
            stmt.setInt(1, id);

            stmt.executeUpdate();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
