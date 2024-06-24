package entidades;

import java.sql.Date;

public class Paciente {
	private int id;
	private String nome;
	private int cpf;
	private Date dataNasc;
	private int telefone;
	private String email;
	private Endereco endereco;
	private Responsavel responsavel;
	private boolean deletado;

	public Paciente(int id, String nome, int cpf, Date dataNasc, int telefone, String email, Endereco endereco,
			Responsavel responsavel, boolean deletado) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.dataNasc = dataNasc;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
		this.responsavel = responsavel;
		this.deletado = deletado;
	}

	public Paciente(String nome, int cpf, Date dataNasc, int telefone, String email, Endereco endereco,
			Responsavel responsavel, boolean deletado) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.dataNasc = dataNasc;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
		this.responsavel = responsavel;
		this.deletado = deletado;
	}

	public Paciente() {
		// TODO Auto-generated constructor stub
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

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
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

	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return this.nome;
	}
}
