package entidades;

public class Produto {
	private int id;
	private String nome;
	private String categoria;
	private String descricao;
	private Compra compra;
	private boolean deletado;

	public Produto(int id, String nome, String categoria, String descricao, Compra compra, boolean deletado) {
		super();
		this.id = id;
		this.nome = nome;
		this.categoria = categoria;
		this.descricao = descricao;
		this.compra = compra;
		this.deletado = deletado;
	}

	public Produto(String nome, String categoria, String descricao, Compra compra, boolean deletado) {
		super();
		this.nome = nome;
		this.categoria = categoria;
		this.descricao = descricao;
		this.compra = compra;
		this.deletado = deletado;
	}

	public Produto() {
		// TODO Auto-generated constructor stub
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
