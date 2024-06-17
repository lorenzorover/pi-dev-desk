package entidades;

import java.sql.Date;

public class Compra {
	private int id;
	private double preco;
	private Date validade;
	private Date dataCompra;
	private int quantidade;

	public Compra(int id, double preco, Date validade, Date dataCompra, int quantidade) {
		super();
		this.id = id;
		this.preco = preco;
		this.validade = validade;
		this.dataCompra = dataCompra;
		this.quantidade = quantidade;
	}

	public Compra(double preco, Date validade, Date dataCompra, int quantidade) {
		super();
		this.preco = preco;
		this.validade = validade;
		this.dataCompra = dataCompra;
		this.quantidade = quantidade;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public Date getValidade() {
		return validade;
	}

	public void setValidade(Date validade) {
		this.validade = validade;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Compra [id=" + id + ", preco=" + preco + ", validade=" + validade + ", dataCompra=" + dataCompra
				+ ", quantidade=" + quantidade + "]";
	}

}
