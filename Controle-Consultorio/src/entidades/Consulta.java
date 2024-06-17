package entidades;

import java.sql.Timestamp;

public class Consulta {
	private int id;
	private Timestamp dataHora;
	private String descricao;
	private int comparecimento;
	private Paciente paciente;
	private Tratamento tratamento;
	private Produto produto;

	public Consulta(int id, Timestamp dataHora, String descricao, int comparecimento, Paciente paciente,
			Tratamento tratamento, Produto produto) {
		super();
		this.id = id;
		this.dataHora = dataHora;
		this.descricao = descricao;
		this.comparecimento = comparecimento;
		this.paciente = paciente;
		this.tratamento = tratamento;
		this.produto = produto;
	}

	public Consulta(Timestamp dataHora, String descricao, int comparecimento, Paciente paciente, Tratamento tratamento,
			Produto produto) {
		super();
		this.dataHora = dataHora;
		this.descricao = descricao;
		this.comparecimento = comparecimento;
		this.paciente = paciente;
		this.tratamento = tratamento;
		this.produto = produto;
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

	public int getComparecimento() {
		return comparecimento;
	}

	public void setComparecimento(int comparecimento) {
		this.comparecimento = comparecimento;
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

	public Tratamento getTratamento() {
		return tratamento;
	}

	public void setTratamento(Tratamento tratamento) {
		this.tratamento = tratamento;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Consulta [id=" + id + ", dataHora=" + dataHora + ", descricao=" + descricao + ", comparecimento="
				+ comparecimento + ", paciente=" + paciente + ", tratamento=" + tratamento + ", produto=" + produto
				+ "]";
	}

}
