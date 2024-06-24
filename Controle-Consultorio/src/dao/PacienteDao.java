package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entidades.Endereco;
import entidades.Paciente;
import entidades.Paciente;
import entidades.Responsavel;

public class PacienteDao {
	
	private EnderecoDao enderecoDao = new EnderecoDao();
	private ResponsavelDao responsavelDao = new ResponsavelDao();
	
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

	public int cadastrarPaciente(Paciente paciente) {

		String insert = "INSERT INTO responsavel(nome,cpf,data_nasc,telefone,email,endereco_id,responsavel_id,deletado) VALUES(?,?,?,?,?,?,?,?)";

		try {
			Connection conn = getConexao();

			PreparedStatement pst = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, paciente.getNome());
			pst.setInt(2, paciente.getCpf());
			pst.setDate(3, paciente.getDataNasc());
			pst.setInt(4, paciente.getTelefone());
			pst.setString(5, paciente.getEmail());
			pst.setInt(6, paciente.getEndereco().getId());
			pst.setInt(7, paciente.getResponsavel().getId());
			pst.setBoolean(8, paciente.isDeletado());

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

	public List<Paciente> listaDePacientes() {
		List<Paciente> pacientes = new ArrayList<>();
		String sql = "SELECT * FROM paciente";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt(1);
				String nome = rs.getString(2);
				int cpf = rs.getInt(3);
				Date dataNasc = rs.getDate(4);
				int telefone = rs.getInt(5);
				String email = rs.getString(6);
				int enderecoId = rs.getInt(7);
				int responsavelId = rs.getInt(8);
				boolean deletado = rs.getBoolean(9);
				
				Endereco endereco = enderecoDao.pesquisarPorId(enderecoId);
				Responsavel responsavel = responsavelDao.pesquisarPorId(responsavelId);

				Paciente paciente = new Paciente(id, nome, cpf, dataNasc, telefone, email, endereco, responsavel, deletado);
				pacientes.add(paciente);
			}
			
			rs.close();
			pst.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pacientes;
	}
	
	public Paciente pesquisarPorId(int id) {
		Paciente paciente = new Paciente();
		String query = "SELECT * FROM paciente WHERE Id = ?";
		try {
			Connection con = getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				id = rs.getInt(1);
				String nome = rs.getString(2);
				int cpf = rs.getInt(3);
				Date dataNasc = rs.getDate(4);
				int telefone = rs.getInt(5);
				String email = rs.getString(6);
				int enderecoId = rs.getInt(7);
				int responsavelId = rs.getInt(8);
				boolean deletado = rs.getBoolean(9);		
				
				Endereco endereco = enderecoDao.pesquisarPorId(enderecoId);
				Responsavel responsavel = responsavelDao.pesquisarPorId(responsavelId);
				
				paciente = new Paciente(id, nome, cpf, dataNasc, telefone, email, endereco, responsavel, deletado);
			}
			pst.close();
			pst.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return paciente;
	}

	public void alterarPaciente(Paciente paciente) {

		String sql = "UPDATE paciente SET nome = ?, cpf = ?, data_nasc = ?, telefone = ?, email = ?, endereco_id = ?, responsavel_id = ?, deletado = ? WHERE id = ?";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);
			
			pst.setString(1, paciente.getNome());
			pst.setInt(2, paciente.getCpf());
			pst.setDate(3, paciente.getDataNasc());
			pst.setInt(4, paciente.getTelefone());
			pst.setString(5, paciente.getEmail());
			pst.setInt(6, paciente.getEndereco().getId());
			pst.setInt(7, paciente.getResponsavel().getId());
			pst.setBoolean(8, paciente.isDeletado());

			pst.executeUpdate();

			pst.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/*public void deletarPaciente(int id) {

		//String sql = "DELETE paciente, endereco FROM paciente INNER JOIN endereco ON paciente.endereco_id = endereco.id WHERE paciente.id = ?";
		
		String sql = "DELETE FROM paciente WHERE id = ?";
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

	}*/

	public void deletarPaciente(int id) {

		String sql = "DELETE paciente, responsavel, endereco FROM paciente INNER JOIN responsavel ON paciente.responsavel_id = responsavel.id "
				+ "INNER JOIN endereco ON paciente.endereco_id = endereco.id WHERE paciente.id = ?";
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
