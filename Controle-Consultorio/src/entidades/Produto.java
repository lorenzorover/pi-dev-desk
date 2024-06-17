package entidades;

public class Produto {
	private int id;
	private String nome;
	private String categoria;
	private String descricao;
	private Compra compra;
	private int deletado;

	public Produto(int id, String nome, String categoria, String descricao, Compra compra, int deletado) {
		super();
		this.id = id;
		this.nome = nome;
		this.categoria = categoria;
		this.descricao = descricao;
		this.compra = compra;
		this.deletado = deletado;
	}

	public Produto(String nome, String categoria, String descricao, Compra compra, int deletado) {
		super();
		this.nome = nome;
		this.categoria = categoria;
		this.descricao = descricao;
		this.compra = compra;
		this.deletado = deletado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
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
		return "Produto [id=" + id + ", nome=" + nome + ", categoria=" + categoria + ", descricao=" + descricao
				+ ", compra=" + compra + ", deletado=" + deletado + "]";
	}

}
