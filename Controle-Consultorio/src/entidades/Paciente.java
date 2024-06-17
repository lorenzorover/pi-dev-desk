package entidades;

import java.sql.Date;

public class Paciente {
	private int id;
	private String nome;
	private int cpf;
	private Date data_nasc;
	private int telefone;
	private String email;
	private Endereco endereco;
	private Responsavel responsavel;
	private int deletado;

	public Paciente(int id, String nome, int cpf, Date data_nasc, int telefone, String email, Endereco endereco,
			Responsavel responsavel, int deletado) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.data_nasc = data_nasc;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
		this.responsavel = responsavel;
		this.deletado = deletado;
	}

	public Paciente(String nome, int cpf, Date data_nasc, int telefone, String email, Endereco endereco,
			Responsavel responsavel, int deletado) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.data_nasc = data_nasc;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
		this.responsavel = responsavel;
		this.deletado = deletado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCpf() {
		return cpf;
	}

	public void setCpf(int cpf) {
		this.cpf = cpf;
	}

	public Date getData_nasc() {
		return data_nasc;
	}

	public void setData_nasc(Date data_nasc) {
		this.data_nasc = data_nasc;
	}

	public int getTelefone() {
		return telefone;
	}

	public void setTelefone(int telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public int getDeletado() {
		return deletado;
	}

	public void setDeletado(int deletado) {
		this.deletado = deletado;
	}

	public boolean isDeletadoAsBoolean() {
		return deletado != 0;
	}

	public void setDeletadoAsBoolean(boolean deletado) {
		this.deletado = deletado ? 1 : 0;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Paciente [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", data_nasc=" + data_nasc + ", telefone="
				+ telefone + ", email=" + email + ", endereco=" + endereco + ", responsavel=" + responsavel
				+ ", deletado=" + deletado + "]";
	}

}
