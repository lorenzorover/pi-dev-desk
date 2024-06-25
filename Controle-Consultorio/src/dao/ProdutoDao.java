package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entidades.Compra;
import entidades.Produto;

public class ProdutoDao {
	
	CompraDao compraDao = new CompraDao();

	public Connection getConexao() throws ClassNotFoundException {

		String driver = "com.mysql.cj.jdbc.Driver";
		Class.forName(driver);

		String url = "jdbc:mysql://localhost:3306/controle_consultorio";
		String user = "root";
		String password = "root";
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public int cadastrarProduto(Produto produto) {

		String insert = "INSERT INTO produto(nome,categoria,descricao,compra_id,deletado) VALUES (?,?,?,?,?)";

		try {
			Connection conn = getConexao();

			PreparedStatement pst = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, produto.getNome());
			pst.setString(2, produto.getCategoria());
			pst.setString(3, produto.getDescricao());
			pst.setInt(4, produto.getCompra().getId());
			pst.setBoolean(5, produto.isDeletado());

			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();
			int chaveGerada;
			if (rs.next() == true) {
				chaveGerada = rs.getInt(1);
				return chaveGerada;
			}

			rs.close();
			pst.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;

	}

	public List<Produto> listaDeProdutos() {
		List<Produto> produtos = new ArrayList<>();
		String sql = "SELECT * FROM produto";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String nome = rs.getString(2);
				String categoria = rs.getString(3);
				String descricao = rs.getString(4);
				int compraId = rs.getInt(5);
				boolean deletado = rs.getBoolean(6);
				
				Compra compra = compraDao.pesquisarPorId(compraId);

				Produto produto = new Produto(id, nome, categoria, descricao, compra, deletado);
				produtos.add(produto);

			}
			rs.close();
			pst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return produtos;
	}
	
	public Produto pesquisarPorId(int id) {
		Produto produto = new Produto();
		String query = "SELECT * FROM produto WHERE id = ?";
		try {
			Connection con = getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				id = rs.getInt(1);
				String nome = rs.getString(2);
				String categoria = rs.getString(3);
				String descricao = rs.getString(4);
				int compraId = rs.getInt(5);
				boolean deletado = rs.getBoolean(6);
				
				Compra compra = compraDao.pesquisarPorId(compraId);		
				
				produto = new Produto(id, nome, categoria, descricao, compra, deletado);
			}
			pst.close();
			pst.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return produto;
	}

	public void alterarProduto(Produto produto) {

		String sql = "UPDATE produto SET nome = ?, categoria = ?, descricao = ?, compra_id = ?, deletado = ? WHERE id = ?";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);

			pst.setString(1, produto.getNome());
			pst.setString(2, produto.getCategoria());
			pst.setString(3, produto.getDescricao());
			pst.setInt(4, produto.getCompra().getId());
			pst.setBoolean(5, produto.isDeletado());

			pst.executeUpdate();

			pst.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deletarProduto(int id) {

		String sql = "DELETE produto, compra FROM produto INNER JOIN compra ON produto.compra_id = compra.id WHERE produto.id = ?";
		
		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, id);

			pst.executeUpdate();

			pst.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
