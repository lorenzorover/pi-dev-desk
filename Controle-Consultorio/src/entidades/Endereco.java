package entidades;

public class Endereco {
	private int id;
	private int cep;
	private String rua;
	private String bairro;
	private String uf;
	private String cidade;
	private int numero;

	public Endereco(int id, int cep, String rua, String bairro, String uf, String cidade, int numero) {
		super();
		this.id = id;
		this.cep = cep;
		this.rua = rua;
		this.bairro = bairro;
		this.uf = uf;
		this.cidade = cidade;
		this.numero = numero;
	}

	public Endereco(int cep, String rua, String bairro, String uf, String cidade, int numero) {
		super();
		this.cep = cep;
		this.rua = rua;
		this.bairro = bairro;
		this.uf = uf;
		this.cidade = cidade;
		this.numero = numero;
	}

	public int getCep() {
		return cep;
	}

	public void setCep(int cep) {
		this.cep = cep;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Endereco [id=" + id + ", cep=" + cep + ", rua=" + rua + ", bairro=" + bairro + ", uf=" + uf
				+ ", cidade=" + cidade + ", numero=" + numero + "]";
	}

}
