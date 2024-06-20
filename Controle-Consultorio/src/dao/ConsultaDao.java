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
import entidades.Paciente;
import entidades.Tratamento;

public class ConsultaDao {
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

		String insert = "INSERT INTO responsavel(data_hora,descricao,comparecimento,paciente_id,tratamento,_id,produto_id) VALUES(?,?,?,?,?,?)";

		try {
			Connection conn = getConexao();

			PreparedStatement pst = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			pst.setTimestamp(1, consulta.getDataHora());
			pst.setString(2, consulta.getDescricao());
			pst.setInt(3, consulta.getComparecimento());
			pst.setInt(4, consulta.getPaciente().getId());
			pst.setInt(5, consulta.getTratamento().getId());
			pst.setInt(6, consulta.getProduto().getId());

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
		
		String sql = " SELECT * FROM consulta INNER JOIN paciente ON consulta.paciente_id = paciente.id "
				+ "INNER JOIN tratamento ON consulta.tratamento_id = tratamento.id";
		
		List<Consulta> lista = new ArrayList<>();

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			PacienteDao pacienteDao = new PacienteDao();
			TratamentoDao tratamentoDao = new TratamentoDao();

			while (rs.next() == true) {
				int id = rs.getInt(1);
				Timestamp dataHora = rs.getTimestamp(2);
				String descricao = rs.getString(3);
				int comparecimento = rs.getInt(4);
				
				int pacienteId = rs.getInt(5);
				String nomePaciente = rs.getString(8);
				int cpf = rs.getInt(9);
				Date dataNasc = rs.getDate(10);
				int telefone = rs.getInt(11);
				String email = rs.getString(12);
				int enderecoId = rs.getInt(13);
				int responsavelId = rs.getInt(14);
				int deletadoPaciente = rs.getInt(15);
				Paciente paciente = new Paciente(pacienteId, nomePaciente, cpf, dataNasc, telefone, email, null, null, deletadoPaciente);

				int tratamentoId = rs.getInt(6);
				String nome = rs.getString(17);
				double preco = rs.getDouble(18);
				String descricaoTratamento = rs.getString(19);
				int deletadoTratamento = rs.getInt(20);
				Tratamento tratamento = new Tratamento(tratamentoId, nome, preco, descricaoTratamento, deletadoTratamento);
				
				Consulta consulta = new Consulta(id, dataHora, descricao, comparecimento, paciente, tratamento, null);
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

	public void alterarConsulta(Consulta consulta) {

		String sql = "UPDATE consulta SET data_hora = ?, descricao = ?, comparecimento = ?, paciente_id = ?, tratamento_id = ?, produto_id = ? WHERE id = ?";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);

			pst.setTimestamp(1, consulta.getDataHora());
			pst.setString(2, consulta.getDescricao());
			pst.setInt(3, consulta.getComparecimento());
			pst.setInt(4, consulta.getPaciente().getId());
			pst.setInt(5, consulta.getTratamento().getId());
			// pst.setInt(6, consulta.getProduto().getId());

			pst.executeUpdate();

			pst.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deletarConsulta(int id) {

		String sql = "DELETE FROM consulta WHERE consulta.id = ?";
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
