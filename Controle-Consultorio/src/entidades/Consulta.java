package entidades;

import java.sql.Timestamp;

public class Consulta {
	private int id;
	private Timestamp dataHora;
	private String descricao;
	private int comparecimento;
	private Paciente paciente;
	private Tratamento tratamentos;
	private Produto produtos;

	public Consulta(int id, Timestamp dataHora, String descricao, int comparecimento, Paciente paciente,
			Tratamento tratamentos, Produto produtos) {
		super();
		this.id = id;
		this.dataHora = dataHora;
		this.descricao = descricao;
		this.comparecimento = comparecimento;
		this.paciente = paciente;
		this.tratamentos = tratamentos;
		this.produtos = produtos;
	}

	public Consulta(Timestamp dataHora, String descricao, int comparecimento, Paciente paciente, Tratamento tratamentos,
			Produto produtos) {
		super();
		this.dataHora = dataHora;
		this.descricao = descricao;
		this.comparecimento = comparecimento;
		this.paciente = paciente;
		this.tratamentos = tratamentos;
		this.produtos = produtos;
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

	public boolean isComparecimentoAsBoolean() {
		return comparecimento != 0;
	}

	public void setComparecimentoAsBoolean(boolean comparecimento) {
		this.comparecimento = comparecimento ? 1 : 0;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Tratamento getTratamentos() {
		return tratamentos;
	}

	public void setTratamentos(Tratamento tratamentos) {
		this.tratamentos = tratamentos;
	}

	public Produto getProdutos() {
		return produtos;
	}

	public void setProdutos(Produto produtos) {
		this.produtos = produtos;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Consulta [id=" + id + ", dataHora=" + dataHora + ", descricao=" + descricao + ", comparecimento="
				+ comparecimento + ", paciente=" + paciente + ", tratamentos=" + tratamentos + ", produtos=" + produtos
				+ "]";
	}

}
