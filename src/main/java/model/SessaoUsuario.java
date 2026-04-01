/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author João Paulo
 */
/**
 * Classe utilizada para manter o estado do usuário durante a execução do
 * programa. O uso do 'static' permite acessar o usuário logado de qualquer tela
 * sem precisar passar objetos por parâmetro.
 */
public class SessaoUsuario {

    // Variável estática que armazena os dados do usuário que fez login com sucesso
    public static UsuarioBean usuarioLogado;
}
