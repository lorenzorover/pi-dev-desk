package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import entidades.Consulta;
import entidades.Endereco;
import entidades.Paciente;
import entidades.Tratamento;

public class PacienteDao {

	private EnderecoDao enderecoDao = new EnderecoDao();
	private TratamentoDao tratamentoDao = new TratamentoDao();

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

		String insert = "INSERT INTO paciente(nome,cpf,data_nasc,telefone,email,endereco_id,deletado) VALUES(?,?,?,?,?,?,?)";

		try {
			Connection conn = getConexao();

			PreparedStatement pst = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, paciente.getNome());
			pst.setString(2, paciente.getCpf());
			pst.setDate(3, paciente.getDataNasc());
			pst.setString(4, paciente.getTelefone());
			pst.setString(5, paciente.getEmail());
			pst.setInt(6, paciente.getEndereco().getId());
			pst.setBoolean(7, paciente.isDeletado());

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

	public boolean possuiConsulta(int pacienteId) {
		PacienteDao pacienteDao = new PacienteDao();
		List<Consulta> lista = new ArrayList<>();
		
		String query = "SELECT * FROM consulta WHERE paciente_id = ?";

		try {
			Connection con = getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, pacienteId);
			ResultSet rs = pst.executeQuery();

			while (rs.next() == true) {
				int id = rs.getInt(1);
				Timestamp dataHora = rs.getTimestamp(2);
				String descricao = rs.getString(3);
				boolean comparecimento = rs.getBoolean(4);
				pacienteId = rs.getInt(5);
				int tratamentoId = rs.getInt(6);

				Paciente paciente = pacienteDao.pesquisarPorId(pacienteId);
				Tratamento tratamento = tratamentoDao.pesquisarPorId(tratamentoId);

				Consulta consulta = new Consulta(id, dataHora, descricao, comparecimento, paciente, tratamento);
				lista.add(consulta);
			}

			pst.close();
			pst.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		
		if (lista.isEmpty() == false) {
			return true;
		}

		return false;
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
				String cpf = rs.getString(3);
				Date dataNasc = rs.getDate(4);
				String telefone = rs.getString(5);
				String email = rs.getString(6);
				int enderecoId = rs.getInt(7);
				boolean deletado = rs.getBoolean(8);

				Endereco endereco = enderecoDao.pesquisarPorId(enderecoId);

				Paciente paciente = new Paciente(id, nome, cpf, dataNasc, telefone, email, endereco, deletado);
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
		String query = "SELECT * FROM paciente WHERE id = ?";
		try {
			Connection con = getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				id = rs.getInt(1);
				String nome = rs.getString(2);
				String cpf = rs.getString(3);
				Date dataNasc = rs.getDate(4);
				String telefone = rs.getString(5);
				String email = rs.getString(6);
				int enderecoId = rs.getInt(7);
				boolean deletado = rs.getBoolean(8);

				Endereco endereco = enderecoDao.pesquisarPorId(enderecoId);

				paciente = new Paciente(id, nome, cpf, dataNasc, telefone, email, endereco, deletado);
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

		String sql = "UPDATE paciente SET nome = ?, cpf = ?, data_nasc = ?, telefone = ?, email = ?, endereco_id = ?, deletado = ? WHERE id = ?";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);

			pst.setString(1, paciente.getNome());
			pst.setString(2, paciente.getCpf());
			pst.setDate(3, paciente.getDataNasc());
			pst.setString(4, paciente.getTelefone());
			pst.setString(5, paciente.getEmail());
			pst.setInt(6, paciente.getEndereco().getId());
			pst.setBoolean(7, paciente.isDeletado());
			pst.setInt(8, paciente.getId());

			pst.executeUpdate();

			pst.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deletarPaciente(int id) {

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

	}

}
