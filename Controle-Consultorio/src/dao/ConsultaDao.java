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
import entidades.Produto;
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
		List<Consulta> consultas = new ArrayList<>();
		String sql = "SELECT * FROM consulta";

		try {
			Connection conn = getConexao();
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				Timestamp dataHora = rs.getTimestamp(2);
				String descricao = rs.getString(3);
				int comparecimento = rs.getInt(4);
				Paciente paciente = (Paciente) rs.getObject(5); // Achar um jeito de adicionar esse objeto
				Tratamento tratamento = (Tratamento) rs.getObject(6); // Achar um jeito de adicionar esse objeto
				Produto produto = (Produto) rs.getObject(7); // Achar um jeito de adicionar esse objeto

				Consulta consulta = new Consulta(id, dataHora, descricao, comparecimento, paciente, tratamento, produto);
				consultas.add(consulta);

			}
			rs.close();
			pst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consultas;
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
			pst.setInt(6, consulta.getProduto().getId());

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
