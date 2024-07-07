package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import entidades.Consulta;
import entidades.Paciente;
import entidades.Tratamento;

public class ConsultaDao {
	
	PacienteDao pacienteDao = new PacienteDao();
	TratamentoDao tratamentoDao = new TratamentoDao();
	
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

	public int cadastrarConsulta(Consulta consulta) {

		String insert = "INSERT INTO consulta(data_hora,descricao,comparecimento,paciente_id,tratamento_id) VALUES(?,?,?,?,?)";

		try {
			Connection conn = getConexao();

			PreparedStatement pst = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			pst.setTimestamp(1, consulta.getDataHora());
			pst.setString(2, consulta.getDescricao());
			pst.setBoolean(3, consulta.isComparecimento());
			pst.setInt(4, consulta.getPaciente().getId());
			pst.setInt(5, consulta.getTratamento().getId());
//			pst.setInt(6, consulta.getProduto().getId());

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

	public List<Consulta> listaDeConsultas() {
		
		String sql = " SELECT * FROM consulta";
		
		List<Consulta> lista = new ArrayList<>();

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			while (rs.next() == true) {
				int id = rs.getInt(1);
				Timestamp dataHora = rs.getTimestamp(2);
				String descricao = rs.getString(3);
				boolean comparecimento = rs.getBoolean(4);
				int pacienteId = rs.getInt(5);
				int tratamentoId = rs.getInt(6);
				
				Paciente paciente = pacienteDao.pesquisarPorId(pacienteId);
				Tratamento tratamento = tratamentoDao.pesquisarPorId(tratamentoId);
				
				Consulta consulta = new Consulta(id, dataHora, descricao, comparecimento, paciente, tratamento);
				lista.add(consulta);
			}
			
			rs.close();
			pst.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public Consulta pesquisarPorId(int id) {
		Consulta consulta = new Consulta();
		String query = "SELECT * FROM consulta WHERE id = ?";
		
		try {
			Connection con = getConexao();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				id = rs.getInt(1);
				Timestamp dataHora = rs.getTimestamp(2);
				String descricao = rs.getString(3);
				boolean comparecimento = rs.getBoolean(4);
				int pacienteId = rs.getInt(5);
				int tratamentoId = rs.getInt(6);		
				
				Paciente paciente = pacienteDao.pesquisarPorId(pacienteId);
				Tratamento tratamento = tratamentoDao.pesquisarPorId(tratamentoId);
				
				consulta = new Consulta(id, dataHora, descricao, comparecimento, paciente, tratamento);
			}
			
			pst.close();
			pst.close();
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return consulta;
	}

	public void alterarConsulta(Consulta consulta) {

		String sql = "UPDATE consulta SET data_hora = ?, descricao = ?, comparecimento = ?, paciente_id = ?, tratamento_id = ? WHERE id = ?";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);

			pst.setTimestamp(1, consulta.getDataHora());
			pst.setString(2, consulta.getDescricao());
			pst.setBoolean(3, consulta.isComparecimento());
			pst.setInt(4, consulta.getPaciente().getId());
			pst.setInt(5, consulta.getTratamento().getId());
			pst.setInt(6, consulta.getId());

			pst.executeUpdate();

			pst.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deletarConsulta(int id) {

		String sql = "DELETE consulta FROM consulta WHERE consulta.id = ?";
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
