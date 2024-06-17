package entidades;

public class Tratamento {
	private int id;
	private String nome;
	private double preco;
	private String descricao;
	private int deletado;

	public Tratamento(int id, String nome, double preco, String descricao, int deletado) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.descricao = descricao;
		this.deletado = deletado;
	}

	public Tratamento(String nome, double preco, String descricao, int deletado) {
		super();
		this.nome = nome;
		this.preco = preco;
		this.descricao = descricao;
		this.deletado = deletado;
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
		return "Tratamento [id=" + id + ", nome=" + nome + ", preco=" + preco + ", descricao=" + descricao
				+ ", deletado=" + deletado + "]";
	}

}
