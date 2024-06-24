package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.Compra;
import entidades.Compra;

public class CompraDao {

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

	public void cadastrarCompra(Compra compra) {

		String insert = "INSERT INTO compra(preco,validade,data_compra,quantidade) VALUES(?,?,?,?)";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(insert);

			pst.setDouble(1, compra.getPreco());
			pst.setDate(2, compra.getDataCompra());
			pst.setDate(3, compra.getValidade());
			pst.setInt(4, compra.getQuantidade());

			pst.executeUpdate();

			pst.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Compra> listaDeCompras() {
		List<Compra> compras = new ArrayList<>();
		String sql = "SELECT * FROM compra";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				Double preco = rs.getDouble(2);
				Date validade = rs.getDate(3);
				Date dataCompra = rs.getDate(4);
				int quantidade = rs.getInt(5);

				Compra compra = new Compra(id, preco, validade, dataCompra, quantidade);
				compras.add(compra);

			}
			rs.close();
			pst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return compras;
	}
	
	public Compra pesquisarPorId(int id) {
		Compra compra = new Compra();
		String query = "SELECT * FROM compra WHERE Id = ?";
		try {
			Connection con = getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				id = rs.getInt(1);
				Double preco = rs.getDouble(2);
				Date validade = rs.getDate(3);
				Date dataCompra = rs.getDate(4);
				int quantidade = rs.getInt(5);		
				
				compra = new Compra(id, preco, validade, dataCompra, quantidade);
			}
			pst.close();
			pst.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return compra;
	}

	public void alterarCompra(Compra compra) {

		String sql = "UPDATE compra SET preco = ?, data_compra = ?, validade = ?, quantidade = ? WHERE id = ?";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);

			pst.setDouble(1, compra.getPreco());
			pst.setDate(2, compra.getDataCompra());
			pst.setDate(3, compra.getValidade());
			pst.setInt(4, compra.getQuantidade());

			pst.executeUpdate();

			pst.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deletarCompra(int id) {

		String sql = "DELETE FROM compra WHERE id = ?";
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
