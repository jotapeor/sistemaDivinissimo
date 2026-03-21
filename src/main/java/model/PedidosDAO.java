/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import conexao.Conexao;
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
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT u.nome, p.tipoLanche, p.quantidade, p.formaPagamento, p.valorTotal, p.statusPedido "
                    + "FROM pedidos p INNER JOIN usuarios u ON p.idUsuario = u.id"
            );
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PedidosBean p = new PedidosBean();
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

    public void entregarPedidos() {
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement("update pedidos set statusPedido = 'Entregue' where statusPedido = 'Pendente' ");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
