package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entidades.Paciente;
import entidades.PacienteResponsavel;
import entidades.Responsavel;

public class PacienteResponsavelDao {

	private PacienteDao pacienteDao = new PacienteDao();
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

	public void cadastrarPacienteResponsavel(PacienteResponsavel pacienteResponsavel) {

		String insert = "INSERT INTO paciente_responsavel(paciente_id,responsavel_id) VALUES(?,?)";

		try {
			Connection conn = getConexao();

			PreparedStatement pst = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, pacienteResponsavel.getPaciente().getId());
			pst.setInt(2, pacienteResponsavel.getResponsavel().getId());

			pst.executeUpdate();

			pst.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<PacienteResponsavel> listaDePacientesResponsaveis() {
		List<PacienteResponsavel> pacientesResponsaveis = new ArrayList<>();
		String sql = "SELECT * FROM paciente_responsavel";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int pacienteId = rs.getInt(1);
				int responsavelId = rs.getInt(2);

				Paciente paciente = pacienteDao.pesquisarPorId(pacienteId);
				Responsavel responsavel = responsavelDao.pesquisarPorId(responsavelId);

				PacienteResponsavel pacienteResponsavel = new PacienteResponsavel(paciente, responsavel);
				pacientesResponsaveis.add(pacienteResponsavel);
			}

			rs.close();
			pst.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pacientesResponsaveis;
	}

	public PacienteResponsavel pesquisarPorPacienteId(int pacienteId) {
		PacienteResponsavel pacienteResponsavel = new PacienteResponsavel();
		String query = "SELECT * FROM paciente_responsavel WHERE paciente_id = ?";
		try {
			Connection con = getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, pacienteId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				pacienteId = rs.getInt(1);
				int responsavelId = rs.getInt(2);

				Paciente paciente = pacienteDao.pesquisarPorId(pacienteId);
				Responsavel responsavel = responsavelDao.pesquisarPorId(responsavelId);
				
				pacienteResponsavel = new PacienteResponsavel(paciente, responsavel);
			}
			pst.close();
			pst.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return pacienteResponsavel;
	}

	public void deletarPacienteResponsavel(int id) {

		String sql = "DELETE FROM paciente_responsavel WHERE paciente_id = ?";
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
