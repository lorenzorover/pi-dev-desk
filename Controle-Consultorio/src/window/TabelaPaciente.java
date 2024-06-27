package window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import dao.ConsultaDao;
import dao.EnderecoDao;
import dao.PacienteDao;
import dao.ResponsavelDao;
import entidades.Consulta;
import entidades.Endereco;
import entidades.Paciente;
import entidades.Responsavel;

public class TabelaPaciente extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private JScrollPane scrollPane;
	private JTextField textField;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_3;

	private List<Paciente> lista = new ArrayList<>();
	private List<Consulta> listaConsulta = new ArrayList<>();
	private PacienteDao pacienteDao = new PacienteDao();
	private MaskFormatter mascaraData;
	private MaskFormatter mascaraCpf;
	private DateTimeFormatter dataFormatar = DateTimeFormatter.ofPattern("dd/mm/yyyy");

	private EnderecoDao enderecoDao;
	private ResponsavelDao responsavelDao;
	private ConsultaDao consultaDao;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TabelaPaciente frame = new TabelaPaciente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TabelaPaciente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 691, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(0, 44, 675, 295);
		contentPane.add(panel);
		panel.setLayout(null);

//		table.setDefaultEditor(Object.class, null);
//
//		table.getTableHeader().setReorderingAllowed(false);

		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(10, 43, 655, 191);
		panel.add(scrollPane);

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Nome", "Data de nascimento", "CPF", "Email", "Telefone" }));
		scrollPane.setViewportView(table);

		try {
			mascaraData = new MaskFormatter("##/##/####");
			mascaraData.setPlaceholderCharacter('_');
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		try {
			mascaraCpf = new MaskFormatter("###.###.###-##");
			mascaraCpf.setPlaceholderCharacter('_');
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		JLabel lblNewLabel_1 = new JLabel("Pesquisar:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(493, 14, 54, 15);
		panel.add(lblNewLabel_1);

		atualizarTabela();

		textField = new JTextField();
		textField.setBounds(557, 12, 108, 20);

		textField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				pesquisaDinamica();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				pesquisaDinamica();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				pesquisaDinamica();
			}
		});

		panel.add(textField);
		textField.setColumns(10);

		btnNewButton = new JButton("Editar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Confirmar se posso instanciar um paciente zerado aqui, sendo que ele recebe
				// outros parametros adiante
				Paciente paciente = new Paciente();
				String opcao = null;
				paciente = (Paciente) selecionarLinhaPorId(paciente);

				if (paciente.getNome() != null) {
					String opcaoRetornada = selecionarOpcaoEditar(opcao, paciente);
					if (opcaoRetornada.equals("paciente")) {
						editarPaciente(paciente);
					} else if (opcaoRetornada.equals("responsavel")) {
						editarResponsavel(paciente);
					} else if (opcaoRetornada.equals("endereco")) {
						editarEndereco(paciente);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma linha");
				}
			}
		});
		btnNewButton.setBounds(294, 261, 89, 23);
		panel.add(btnNewButton);

		btnNewButton_1 = new JButton("<html>Informações<br>adicionais<html>");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Paciente paciente = new Paciente();
				String opcao = null;
				paciente = (Paciente) selecionarLinhaPorId(paciente);

				if (paciente != null) {
					String opcaoRetornada = selecionarResponsavelOuEndereco(opcao, paciente);
					if (opcaoRetornada == "responsavel") {
						informacoesAdicionaisResponsavel(paciente);
					} else if (opcaoRetornada == "endereco") {
						informacoesAdicionaisEndereco(paciente);
					} 
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma linha");
				}
			}
		});
		btnNewButton_1.setBounds(191, 247, 93, 37);
		panel.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Atualizar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarTabela();
			}
		});
		btnNewButton_2.setBounds(10, 11, 75, 23);
		panel.add(btnNewButton_2);

		btnNewButton_3 = new JButton("Excluir");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Paciente paciente = new Paciente();
				paciente = (Paciente) selecionarLinhaPorId(paciente);
				
				if (paciente != null) {
					excluirPaciente(paciente);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma linha");
				}
			}
		});
		btnNewButton_3.setBounds(393, 261, 89, 23);
		panel.add(btnNewButton_3);

		JPanel pTitulo = new JPanel();
		pTitulo.setBounds(0, 0, 675, 44);
		contentPane.add(pTitulo);
		pTitulo.setLayout(null);
		pTitulo.setBackground(Color.GRAY);

		JLabel lblNewLabel = new JLabel("Lista de Pacientes");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(275, 11, 140, 22);
		pTitulo.add(lblNewLabel);

	}

	public void atualizarTabela() {
		modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);

		lista = pacienteDao.listaDePacientes();

		for (Paciente paciente : lista) {
			if (paciente.isDeletado() != true) {
				modelo.addRow(new Object[] { paciente.getNome(), paciente.getDataNasc(), paciente.getCpf(),
						paciente.getEmail(), paciente.getTelefone() });
			}
		}

	}

	public void pesquisaDinamica() {
		String pesquisa = textField.getText();
		lista = pacienteDao.listaDePacientes();

		modelo.setRowCount(0);

		for (Paciente paciente : lista) {
			if (paciente.getNome().toLowerCase().startsWith(pesquisa.toLowerCase())) {
				if (paciente.isDeletado() != true) {
					modelo.addRow(new Object[] { paciente.getNome(), paciente.getDataNasc(), paciente.getCpf(),
							paciente.getEmail(), paciente.getTelefone() });
				}
			}
		}
	}

	public String selecionarResponsavelOuEndereco(String opcao, Paciente paciente) {

		if (paciente.getResponsavel() != null) {
			JRadioButton responsavel = new JRadioButton("Responsável");
			JRadioButton endereco = new JRadioButton("Endereço");

			ButtonGroup grupo = new ButtonGroup();
			grupo.add(responsavel);
			grupo.add(endereco);

			JPanel painel = new JPanel(new GridLayout(4, 2));
			painel.add(responsavel);
			painel.add(endereco);

			int resultado = JOptionPane.showConfirmDialog(null, painel, "Opções", JOptionPane.OK_CANCEL_OPTION);

			if (resultado == JOptionPane.OK_OPTION) {
				if (responsavel.isSelected()) {
					return opcao = "responsavel";
				} else if (endereco.isSelected()) {
					return opcao = "endereco";
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma opção");
				}
			}
		} else if (paciente.getResponsavel().equals(null)) {
			return opcao = "endereco";
		}
		return null;
	}

	public String selecionarOpcaoEditar(String opcao, Paciente paciente) {
		JRadioButton buttonPaciente = new JRadioButton("Paciente");
		JRadioButton buttonEndereco = new JRadioButton("Endereço");
		JRadioButton buttonResponsavel = new JRadioButton("Responsável");
		buttonResponsavel.setEnabled(false);

		ButtonGroup grupo = new ButtonGroup();
		grupo.add(buttonEndereco);
		grupo.add(buttonPaciente);

		JPanel painel = new JPanel(new GridLayout(4, 2));
		painel.add(buttonPaciente);
		painel.add(buttonEndereco);

		if (paciente.getResponsavel() != null) {
			grupo.add(buttonResponsavel);
			painel.add(buttonResponsavel);
			buttonResponsavel.setEnabled(true);
		}

		int resultado = JOptionPane.showConfirmDialog(null, painel, "Opções", JOptionPane.OK_CANCEL_OPTION);

		if (resultado == JOptionPane.OK_OPTION) {
			if (buttonPaciente.isSelected()) {
				return opcao = "paciente";
			} else if (buttonEndereco.isSelected()) {
				return opcao = "endereco";
			} else if (buttonResponsavel.isEnabled() == true && buttonResponsavel.isSelected()) {
				return opcao = "responsavel";
			}
		} else {
			JOptionPane.showMessageDialog(null, "Selecione uma opção");
		}
		return null;
	}

	public void informacoesAdicionaisEndereco(Paciente paciente) {

		Endereco endereco = enderecoDao.pesquisarPorId(paciente.getEndereco().getId());

		JTextField tfCep = new JTextField(String.valueOf(endereco.getCep()));
		JTextField tfRua = new JTextField(endereco.getRua());
		JTextField tfBairro = new JTextField(endereco.getBairro());
		JTextField tfUf = new JTextField(endereco.getUf());
		JTextField tfCidade = new JTextField(endereco.getCidade());
		JTextField tfNumero = new JTextField(String.valueOf(endereco.getNumero()));

		tfCep.setEditable(false);
		tfRua.setEditable(false);
		tfBairro.setEditable(false);
		tfUf.setEditable(false);
		tfCidade.setEditable(false);
		tfNumero.setEditable(false);

		JLabel lblCep = new JLabel("CEP");
		JLabel lblRua = new JLabel("Rua");
		JLabel lblBairro = new JLabel("Bairro");
		JLabel lblUf = new JLabel("UF");
		JLabel lblCidade = new JLabel("Cidade");
		JLabel lblNumero = new JLabel("Número");

		JPanel painel = new JPanel(new GridLayout(0, 1));
		painel.add(lblCep);
		painel.add(tfCep);
		painel.add(lblRua);
		painel.add(tfRua);
		painel.add(lblBairro);
		painel.add(tfBairro);
		painel.add(lblUf);
		painel.add(tfUf);
		painel.add(lblCidade);
		painel.add(tfCidade);
		painel.add(lblNumero);
		painel.add(tfNumero);

		JOptionPane.showMessageDialog(null, painel, "Informações do paciente " + paciente.getNome(),
				JOptionPane.NO_OPTION);
	}

	public void informacoesAdicionaisResponsavel(Paciente paciente) {

		Responsavel responsavel = responsavelDao.pesquisarPorId(paciente.getResponsavel().getId());

		JTextField tfNome = new JTextField(responsavel.getNome());
		JTextField tfCpf = new JTextField(String.valueOf(responsavel.getCpf()));
		JTextField tfTelefone = new JTextField(String.valueOf(responsavel.getTelefone()));
		JTextField tfEmail = new JTextField(responsavel.getEmail());

		tfNome.setEditable(false);
		tfCpf.setEditable(false);
		tfTelefone.setEditable(false);
		tfEmail.setEditable(false);

		JLabel lblNome = new JLabel("Nome");
		JLabel lblCpf = new JLabel("CPF");
		JLabel lblTelefone = new JLabel("Telefone");
		JLabel lblEmail = new JLabel("Email");

		JPanel painel = new JPanel(new GridLayout(0, 1));
		painel.add(lblNome);
		painel.add(tfNome);
		painel.add(lblCpf);
		painel.add(tfCpf);
		painel.add(lblTelefone);
		painel.add(tfTelefone);
		painel.add(lblEmail);
		painel.add(tfEmail);

		JOptionPane.showMessageDialog(null, painel, "Informações do paciente " + paciente.getNome(),
				JOptionPane.NO_OPTION);
	}

	public void editarPaciente(Paciente paciente) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada = dateFormat.format(paciente.getDataNasc());

		JTextField tfNome = new JTextField(paciente.getNome());
		JFormattedTextField ftfCpf = new JFormattedTextField(mascaraCpf);
		ftfCpf.setValue(String.valueOf(paciente.getCpf()));
		JFormattedTextField ftfDataNasc = new JFormattedTextField(mascaraData);
		ftfDataNasc.setValue(dataFormatada);
		JTextField tfTelefone = new JTextField(String.valueOf(paciente.getTelefone()));
		JTextField tfEmail = new JTextField(paciente.getEmail());

		JLabel lblNome = new JLabel("Nome");
		JLabel lblCpf = new JLabel("CPF");
		JLabel lblDataNasc = new JLabel("Data de nascimento");
		JLabel lblTelefone = new JLabel("Telefone");
		JLabel lblEmail = new JLabel("Email");

		JPanel painel = new JPanel(new GridLayout(0, 1));
		painel.add(lblNome);
		painel.add(tfNome);
		painel.add(lblCpf);
		painel.add(ftfCpf);
		painel.add(lblDataNasc);
		painel.add(ftfDataNasc);
		painel.add(lblTelefone);
		painel.add(tfTelefone);
		painel.add(lblEmail);
		painel.add(tfEmail);

		int resultado = JOptionPane.showConfirmDialog(null, painel, "Editar paciente", JOptionPane.OK_CANCEL_OPTION);

		if (resultado == JOptionPane.OK_OPTION) {

			String data = ftfDataNasc.getText();
			LocalDate dataFormatadaEditada = LocalDate.parse(data, dataFormatar);

			Responsavel responsavel = responsavelDao.pesquisarPorId(paciente.getResponsavel().getId());
			Endereco endereco = enderecoDao.pesquisarPorId(paciente.getEndereco().getId());

			String nome = tfNome.getText();
			int cpf = Integer.parseInt(ftfCpf.getText());
			Date dataNasc = Date.valueOf(dataFormatadaEditada);
			int telefone = Integer.parseInt(tfTelefone.getText());
			String email = tfEmail.getText();

			paciente = new Paciente(nome, cpf, dataNasc, telefone, email, endereco, responsavel, false);
			pacienteDao.alterarPaciente(paciente);

			atualizarTabela();
		}

	}

	public void editarResponsavel(Paciente paciente) {

		Responsavel responsavel = responsavelDao.pesquisarPorId(paciente.getResponsavel().getId());

		JTextField tfNome = new JTextField(responsavel.getNome());
		JFormattedTextField ftfCpf = new JFormattedTextField(mascaraCpf);
		ftfCpf.setValue(String.valueOf(responsavel.getCpf()));
		JTextField tfTelefone = new JTextField(String.valueOf(responsavel.getTelefone()));
		JTextField tfEmail = new JTextField(responsavel.getEmail());

		JLabel lblNome = new JLabel("Nome");
		JLabel lblCpf = new JLabel("CPF");
		JLabel lblTelefone = new JLabel("Telefone");
		JLabel lblEmail = new JLabel("Email");

		JPanel painel = new JPanel(new GridLayout(0, 1));
		painel.add(lblNome);
		painel.add(tfNome);
		painel.add(lblCpf);
		painel.add(ftfCpf);
		painel.add(lblTelefone);
		painel.add(tfTelefone);
		painel.add(lblEmail);
		painel.add(tfEmail);

		int resultado = JOptionPane.showConfirmDialog(null, painel, "Editar responsavel de " + paciente.getNome(),
				JOptionPane.OK_CANCEL_OPTION);

		if (resultado == JOptionPane.OK_OPTION) {

			String nome = tfNome.getText();
			int cpf = Integer.parseInt(ftfCpf.getText());
			int telefone = Integer.parseInt(tfTelefone.getText());
			String email = tfEmail.getText();

			responsavel = new Responsavel(nome, cpf, telefone, email);
			responsavelDao.alterarResponsavel(responsavel);

			atualizarTabela();
		}

	}

	public void editarEndereco(Paciente paciente) {

		Endereco endereco = enderecoDao.pesquisarPorId(paciente.getEndereco().getId());

		JTextField tfCep = new JTextField(String.valueOf(endereco.getCep()));
		JTextField tfRua = new JTextField(endereco.getRua());
		JTextField tfBairro = new JTextField(endereco.getBairro());
		JTextField tfUf = new JTextField(endereco.getUf());
		JTextField tfCidade = new JTextField(endereco.getCidade());
		JTextField tfNumero = new JTextField(String.valueOf(endereco.getNumero()));

		JLabel lblCep = new JLabel("CEP");
		JLabel lblRua = new JLabel("Rua");
		JLabel lblBairro = new JLabel("Bairro");
		JLabel lblUf = new JLabel("UF");
		JLabel lblCidade = new JLabel("Cidade");
		JLabel lblNumero = new JLabel("Número");

		JPanel painel = new JPanel(new GridLayout(0, 1));
		painel.add(lblCep);
		painel.add(tfCep);
		painel.add(lblRua);
		painel.add(tfRua);
		painel.add(lblBairro);
		painel.add(tfBairro);
		painel.add(lblUf);
		painel.add(tfUf);
		painel.add(lblCidade);
		painel.add(tfCidade);
		painel.add(lblNumero);
		painel.add(tfNumero);

		int resultado = JOptionPane.showConfirmDialog(null, painel, "Editar endereço de " + paciente.getNome(),
				JOptionPane.OK_CANCEL_OPTION);

		if (resultado == JOptionPane.OK_OPTION) {

			int cep = Integer.parseInt(tfCep.getText());
			String rua = tfRua.getText();
			String bairro = tfBairro.getText();
			String uf = tfUf.getText();
			String cidade = tfCidade.getText();
			int numero = Integer.parseInt(tfNumero.getText());

			endereco = new Endereco(cep, rua, bairro, uf, cidade, numero);
			enderecoDao.alterarEndereco(endereco);

			atualizarTabela();
		}

	}

	public void excluirPaciente(Paciente paciente) {

		int resultado = JOptionPane.showConfirmDialog(null,
				"Você está prestes a deletar o paciente " + paciente.getNome(), "ALERTA", JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);

		if (resultado == JOptionPane.OK_OPTION) {
			paciente.setDeletado(true);
			
			
			//Se a pessoa criou algum paciente errado e quer excluir
			
			boolean possuiConsulta = false;
			listaConsulta = consultaDao.listaDeConsultas();
			
			for (Consulta consulta : listaConsulta) {
				if (possuiConsulta = consulta.getPaciente().getId() == paciente.getId()) {
					possuiConsulta = true;
					break;
				} else {
					possuiConsulta = false;
				}
			}
			
			if (paciente.isDeletado() == true && possuiConsulta == false) {
				pacienteDao.deletarPaciente(paciente.getId());
			}
		}

		atualizarTabela();
	}

	public Object selecionarLinhaPorId(Paciente paciente) {
		int linha = table.getSelectedRow();

		if (linha != -1) {
			Object idObj = table.getValueAt(linha, 0);
			int id = (Integer) idObj;
			paciente = pacienteDao.pesquisarPorId(id);
			return paciente;
		} else {
			return null;
		}
	}

}
