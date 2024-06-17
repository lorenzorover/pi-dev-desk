package entidades;

public class Responsavel {
	private int id;
	private String nome;
	private int cpf;
	private int telefone;
	private String email;
	private Endereco endereco;

	public Responsavel(int id, String nome, int cpf, int telefone, String email, Endereco endereco) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
	}

	public Responsavel(String nome, int cpf, int telefone, String email, Endereco endereco) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
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

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Responsavel [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", telefone=" + telefone + ", email="
				+ email + ", endereco=" + endereco + "]";
	}

}
