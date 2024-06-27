package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entidades.Endereco;

public class EnderecoDao {
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

	public int cadastrarEndereco(Endereco endereco) {

		String insert = "INSERT INTO endereco(cep,rua,bairro,uf,cidade,numero) VALUES(?,?,?,?,?,?)";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			
			pst.setInt(1, endereco.getCep());
			pst.setString(2, endereco.getRua());
			pst.setString(3, endereco.getBairro());
			pst.setString(4, endereco.getUf());
			pst.setString(5, endereco.getCidade());
			pst.setInt(6, endereco.getNumero());

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

	public List<Endereco> listaDeEnderecos() {
		List<Endereco> enderecos = new ArrayList<>();
		String sql = "SELECT * FROM endereco";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int cep = rs.getInt(2);
				String rua = rs.getString(3);
				String bairro = rs.getString(4);
				String uf = rs.getString(5);
				String cidade = rs.getString(6);
				int numero = rs.getInt(7);

				Endereco endereco = new Endereco(id, cep, rua, bairro, uf, cidade, numero);
				enderecos.add(endereco);

			}
			rs.close();
			pst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enderecos;
	}
	
	public Endereco pesquisarPorId(int id) {
		Endereco endereco = new Endereco();
		String query = "SELECT * FROM endereco WHERE id = ?";
		try {
			Connection con = getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				id = rs.getInt(1);
				int cep = rs.getInt(2);
				String rua = rs.getString(3);
				String bairro = rs.getString(4);
				String uf = rs.getString(5);
				String cidade = rs.getString(6);
				int numero = rs.getInt(7);			
				endereco = new Endereco(id, cep, rua, bairro, uf, cidade, numero);
			}
			pst.close();
			pst.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return endereco;
	}

	public void alterarEndereco(Endereco endereco) {

		String sql = "UPDATE endereco SET cep = ?, rua = ?, bairro = ?, uf = ?, cidade = ?, numero = ? WHERE id = ?";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);

			pst.setInt(1, endereco.getCep());
			pst.setString(2, endereco.getRua());
			pst.setString(3, endereco.getBairro());
			pst.setString(4, endereco.getUf());
			pst.setString(5, endereco.getCidade());
			pst.setInt(6, endereco.getNumero());

			pst.executeUpdate();

			pst.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deletarEndereco(int id) {

		String sql = "DELETE FROM endereco WHERE id = ?";
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
