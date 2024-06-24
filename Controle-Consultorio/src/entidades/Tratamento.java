package entidades;

public class Tratamento {
	private int id;
	private String nome;
	private double preco;
	private String descricao;
	private boolean deletado;

	public Tratamento(int id, String nome, double preco, String descricao, boolean deletado) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.descricao = descricao;
		this.deletado = deletado;
	}

	public Tratamento(String nome, double preco, String descricao, boolean deletado) {
		super();
		this.nome = nome;
		this.preco = preco;
		this.descricao = descricao;
		this.deletado = deletado;
	}

	public Tratamento() {
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
