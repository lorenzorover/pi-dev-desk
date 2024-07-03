package entidades;

public class PacienteResponsavel {
	private Paciente paciente;
	private Responsavel responsavel;
	
	public PacienteResponsavel(Paciente paciente, Responsavel responsavel) {
		super();
		this.paciente = paciente;
		this.responsavel = responsavel;
	}

	public PacienteResponsavel() {

	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	@Override
	public String toString() {
		return "PacienteResponsavel [paciente=" + paciente + ", responsavel=" + responsavel + "]";
	}
	
}
