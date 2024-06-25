package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.Tratamento;
import entidades.Tratamento;

public class TratamentoDao {
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

	public void cadastrarTratamento(Tratamento tratamento) {

		String insert = "INSERT INTO tratamento(nome,preco,descricao,deletado) VALUES(?,?,?,?)";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(insert);

			pst.setString(1, tratamento.getNome());
			pst.setDouble(2, tratamento.getPreco());
			pst.setString(3, tratamento.getDescricao());
			pst.setBoolean(4, tratamento.isDeletado());
			
			pst.executeUpdate();

			pst.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Tratamento> listaDeTratamentos() {
		List<Tratamento> tratamentos = new ArrayList<>();
		String sql = "SELECT * FROM tratamento";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String nome = rs.getString(2);
				Double preco = rs.getDouble(3);
				String descricao = rs.getString(4);
				boolean deletado = rs.getBoolean(5);

				Tratamento tratamento = new Tratamento(id, nome, preco, descricao, deletado);
				tratamentos.add(tratamento);

			}
			rs.close();
			pst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tratamentos;
	}
	
	public Tratamento pesquisarPorId(int id) {
		Tratamento tratamento = new Tratamento();
		String query = "SELECT * FROM tratamentos WHERE id = ?";
		try {
			Connection con = getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				id = rs.getInt(1);
				String nome = rs.getString(2);
				Double preco = rs.getDouble(3);
				String descricao = rs.getString(4);
				boolean deletado = rs.getBoolean(5);
				
				tratamento = new Tratamento(id, nome, preco, descricao, deletado);
			}
			pst.close();
			pst.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return tratamento;
	}

	public void alterarTratamento(Tratamento tratamento) {

		String sql = "UPDATE tratamento SET nome = ?, preco = ?, descricao = ?, deletado = ? WHERE id = ?";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);

			pst.setString(1, tratamento.getNome());
			pst.setDouble(2, tratamento.getPreco());
			pst.setString(3, tratamento.getDescricao());
			pst.setBoolean(4, tratamento.isDeletado());

			pst.executeUpdate();

			pst.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deletarTratamento(int id) {

		String sql = "DELETE FROM tratamento WHERE id = ?";
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
