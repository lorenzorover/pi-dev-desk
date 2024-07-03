package entidades;

import java.sql.Date;

public class Paciente {
	private int id;
	private String nome;
	private String cpf;
	private Date dataNasc;
	private String telefone;
	private String email;
	private Endereco endereco;
	private boolean deletado;

	public Paciente(int id, String nome, String cpf, Date dataNasc, String telefone, String email, Endereco endereco, boolean deletado) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.dataNasc = dataNasc;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
		this.deletado = deletado;
	}

	public Paciente(String nome, String cpf, Date dataNasc, String telefone, String email, Endereco endereco, boolean deletado) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.dataNasc = dataNasc;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
		this.deletado = deletado;
	}

	public Paciente() {
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
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
