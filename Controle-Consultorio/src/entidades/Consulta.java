package entidades;

import java.sql.Timestamp;

public class Consulta {
	private int id;
	private Timestamp dataHora;
	private String descricao;
	private boolean comparecimento;
	private Paciente paciente;
	private Tratamento tratamento;

	public Consulta(int id, Timestamp dataHora, String descricao, boolean comparecimento, Paciente paciente,
			Tratamento tratamento) {
		super();
		this.id = id;
		this.dataHora = dataHora;
		this.descricao = descricao;
		this.comparecimento = comparecimento;
		this.paciente = paciente;
		this.tratamento = tratamento;
	}

	public Consulta(Timestamp dataHora, String descricao, boolean comparecimento, Paciente paciente, Tratamento tratamento) {
		super();
		this.dataHora = dataHora;
		this.descricao = descricao;
		this.comparecimento = comparecimento;
		this.paciente = paciente;
		this.tratamento = tratamento;
	}

	public Timestamp getDataHora() {
		return dataHora;
	}

	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isComparecimento() {
		return comparecimento;
	}

	public void setComparecimento(boolean comparecimento) {
		this.comparecimento = comparecimento;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Tratamento getTratamento() {
		return tratamento;
	}

	public void setTratamento(Tratamento tratamento) {
		this.tratamento = tratamento;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Consulta [id=" + id + ", dataHora=" + dataHora + ", descricao=" + descricao + ", comparecimento="
				+ comparecimento + ", paciente=" + paciente + ", tratamento=" + tratamento + "]";
	}

}
