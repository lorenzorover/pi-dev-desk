package window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import dao.EnderecoDao;
import dao.PacienteDao;
import dao.ResponsavelDao;
import entidades.Paciente;
import entidades.Responsavel;

public class CadastroPaciente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JTextField tfCep;
	private JTextField tfRua;
	private JTextField tfBairro;
	private JTextField tfUf;
	private JTextField tfCidade;
	private JTextField tfNumero;
	private JTextField tfNomePaciente;
	private JTextField tfDataNasc;
	private JTextField tfTelefonePaciente;
	private JTextField tfEmailPaciente;
	private JTextField tfNomeResponsavel;
	private JTextField tfCpfResponsavel;
	private JTextField tfTelefoneResponsavel;
	private JTextField tfEmailResponsavel;
	
	private PacienteDao pacienteDao = new PacienteDao();
	private ResponsavelDao responsavelDao = new ResponsavelDao();
	private EnderecoDao enderecoDao = new EnderecoDao();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroPaciente frame = new CadastroPaciente();
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
	public CadastroPaciente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 402, 396);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Cadastro");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Listagem");
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_1 = new JMenu("Consulta");
		mnNewMenu.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Agendadas");
		mnNewMenu_1.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Finalizadas");
		mnNewMenu_1.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Produtos");
		mnNewMenu.add(mntmNewMenuItem_2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pTitulo = new JPanel();
		pTitulo.setBackground(new Color(128, 128, 128));
		pTitulo.setBounds(0, 0, 386, 44);
		contentPane.add(pTitulo);
		pTitulo.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cadastro de Paciente");
		lblNewLabel.setBounds(116, 11, 165, 22);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pTitulo.add(lblNewLabel);
		
		JPanel pEndereco = new JPanel();
		pEndereco.setBackground(new Color(192, 192, 192));
		pEndereco.setBounds(0, 43, 386, 292);
		contentPane.add(pEndereco);
		pEndereco.setLayout(null);
		//Deixar falso a visualização
		pEndereco.setVisible(false);
		
		JLabel lblNewLabel_12 = new JLabel("Endereço");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_12.setBounds(157, 11, 65, 20);
		pEndereco.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("CEP");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_13.setBounds(28, 48, 46, 14);
		pEndereco.add(lblNewLabel_13);
		
		tfCep = new JTextField();
		tfCep.setBounds(105, 46, 117, 20);
		pEndereco.add(tfCep);
		tfCep.setColumns(10);
		
		JLabel lblNewLabel_14 = new JLabel("Rua");
		lblNewLabel_14.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_14.setBounds(28, 73, 46, 14);
		pEndereco.add(lblNewLabel_14);
		
		tfRua = new JTextField();
		tfRua.setBounds(105, 76, 198, 20);
		pEndereco.add(tfRua);
		tfRua.setColumns(10);
		
		tfBairro = new JTextField();
		tfBairro.setBounds(105, 107, 117, 20);
		pEndereco.add(tfBairro);
		tfBairro.setColumns(10);
		
		tfUf = new JTextField();
		tfUf.setBounds(105, 138, 117, 20);
		pEndereco.add(tfUf);
		tfUf.setColumns(10);
		
		tfCidade = new JTextField();
		tfCidade.setBounds(105, 169, 198, 20);
		pEndereco.add(tfCidade);
		tfCidade.setColumns(10);
		
		tfNumero = new JTextField();
		tfNumero.setBounds(105, 200, 54, 20);
		pEndereco.add(tfNumero);
		tfNumero.setColumns(10);
		
		JLabel lblNewLabel_15 = new JLabel("Bairro");
		lblNewLabel_15.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_15.setBounds(10, 110, 46, 14);
		pEndereco.add(lblNewLabel_15);
		
		JLabel lblNewLabel_16 = new JLabel("UF");
		lblNewLabel_16.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_16.setBounds(10, 141, 46, 14);
		pEndereco.add(lblNewLabel_16);
		
		JLabel lblNewLabel_17 = new JLabel("Cidade");
		lblNewLabel_17.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_17.setBounds(10, 172, 46, 14);
		pEndereco.add(lblNewLabel_17);
		
		JLabel lblNewLabel_18 = new JLabel("Número");
		lblNewLabel_18.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_18.setBounds(10, 203, 46, 14);
		pEndereco.add(lblNewLabel_18);
		
		JPanel pPaciente = new JPanel();
		pPaciente.setLayout(null);
		pPaciente.setBackground(Color.LIGHT_GRAY);
		pPaciente.setBounds(0, 43, 386, 292);
		contentPane.add(pPaciente);
		
		tfNomePaciente = new JTextField();
		tfNomePaciente.setColumns(10);
		tfNomePaciente.setBounds(98, 66, 202, 20);
		pPaciente.add(tfNomePaciente);
		
		JLabel lblNewLabel_1 = new JLabel("Paciente");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(168, 11, 58, 20);
		pPaciente.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nome");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(42, 68, 32, 15);
		pPaciente.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("CPF");
		lblNewLabel_3.setToolTipText("");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(52, 99, 20, 15);
		pPaciente.add(lblNewLabel_3);
		
		JLabel lblNewLabel_5 = new JLabel("<html>Data de<br>Nascimento<html>");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(11, 125, 63, 30);
		pPaciente.add(lblNewLabel_5);
		
		tfDataNasc = new JTextField();
		tfDataNasc.setColumns(10);
		tfDataNasc.setBounds(98, 128, 86, 20);
		pPaciente.add(tfDataNasc);
		
		tfTelefonePaciente = new JTextField();
		tfTelefonePaciente.setColumns(10);
		tfTelefonePaciente.setBounds(98, 159, 86, 20);
		pPaciente.add(tfTelefonePaciente);
		
		tfEmailPaciente = new JTextField();
		tfEmailPaciente.setColumns(10);
		tfEmailPaciente.setBounds(98, 190, 202, 20);
		pPaciente.add(tfEmailPaciente);
		
		JLabel lblNewLabel_4 = new JLabel("Telefone");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(27, 162, 49, 15);
		pPaciente.add(lblNewLabel_4);
		
		JLabel lblNewLabel_6 = new JLabel("Email");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_6.setBounds(47, 192, 27, 15);
		pPaciente.add(lblNewLabel_6);
		
		JCheckBox chckbxResponsavel = new JCheckBox("Possui responsável?");
		chckbxResponsavel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxResponsavel.setBackground(Color.LIGHT_GRAY);
		chckbxResponsavel.setBounds(95, 217, 131, 23);
		pPaciente.add(chckbxResponsavel);
		
		MaskFormatter mascaraCpf;
		
		try {
            mascaraCpf = new MaskFormatter("###.###.###-##");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
		
		JFormattedTextField ftfCpfPaciente = new JFormattedTextField(mascaraCpf);
		ftfCpfPaciente.setBounds(98, 97, 128, 20);
		pPaciente.add(ftfCpfPaciente);
		
		JPanel pResponsavel = new JPanel();
		pResponsavel.setLayout(null);
		pResponsavel.setBackground(Color.LIGHT_GRAY);
		pResponsavel.setBounds(0, 43, 386, 290);
		contentPane.add(pResponsavel);
		//Deixar falso a visualização
		pResponsavel.setVisible(false);
		
		JLabel lblNewLabel_7 = new JLabel("Responsável");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_7.setBounds(150, 11, 87, 20);
		pResponsavel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Nome");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_8.setBounds(45, 54, 46, 14);
		pResponsavel.add(lblNewLabel_8);
		
		tfNomeResponsavel = new JTextField();
		tfNomeResponsavel.setColumns(10);
		tfNomeResponsavel.setBounds(101, 52, 202, 20);
		pResponsavel.add(tfNomeResponsavel);
		
		tfCpfResponsavel = new JTextField();
		tfCpfResponsavel.setColumns(10);
		tfCpfResponsavel.setBounds(101, 83, 86, 20);
		pResponsavel.add(tfCpfResponsavel);
		
		tfTelefoneResponsavel = new JTextField();
		tfTelefoneResponsavel.setColumns(10);
		tfTelefoneResponsavel.setBounds(101, 114, 86, 20);
		pResponsavel.add(tfTelefoneResponsavel);
		
		tfEmailResponsavel = new JTextField();
		tfEmailResponsavel.setColumns(10);
		tfEmailResponsavel.setBounds(101, 145, 86, 20);
		pResponsavel.add(tfEmailResponsavel);
		
		JLabel lblNewLabel_9 = new JLabel("CPF");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_9.setBounds(55, 85, 20, 15);
		pResponsavel.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Telefone");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_10.setBounds(26, 116, 49, 15);
		pResponsavel.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("Email");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_11.setBounds(48, 147, 27, 15);
		pResponsavel.add(lblNewLabel_11);
		
		JButton btnContinuarPaciente = new JButton("Continuar");
		btnContinuarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pPaciente.setVisible(false);
				if (chckbxResponsavel.isSelected()) {
					pResponsavel.setVisible(true);
				} else {
					pEndereco.setVisible(true);
				}
			}
		});
		btnContinuarPaciente.setBounds(287, 258, 89, 23);
		pPaciente.add(btnContinuarPaciente);
		
		JButton btnContinuarResponsavel = new JButton("Continuar");
		btnContinuarResponsavel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pResponsavel.setVisible(false);
				pEndereco.setVisible(true);
			}
		});
		btnContinuarResponsavel.setBounds(287, 256, 89, 23);
		pResponsavel.add(btnContinuarResponsavel);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean possuiResponsavel = false;
				
				if (chckbxResponsavel.isSelected()) {
					possuiResponsavel = true;
				}
				
				cadastrarEndereco();
				cadastrarPaciente(possuiResponsavel);
			}
		});
		btnSalvar.setBounds(287, 258, 89, 23);
		pEndereco.add(btnSalvar);
		
	}
	
	public void cadastrarPaciente(boolean possuiResponsavel) {
		String dataNasc = tfDataNasc.getText();
		DateTimeFormatter dataFormatar = DateTimeFormatter.ofPattern("dd/mm/yyyy");
		
		Responsavel responsavel = new Responsavel(null, 0, 0, null);

		String nomePaciente = tfNomePaciente.getText();
		int cpfPaciente = Integer.parseInt();
		LocalDate dataNascFormatada = LocalDate.parse(dataNasc, dataFormatar);
		int telefonePaciente = Integer.parseInt(tfTelefonePaciente.getText());
		String emailPaciente = tfEmailPaciente.getText();
		
		if (possuiResponsavel == true) {
			String nomeResponsavel = tfNomeResponsavel.getText();
			int cpfResponsavel = Integer.parseInt(tfCpfResponsavel.getText());
			int telefoneResponsavel = Integer.parseInt(tfTelefonePaciente.getText());
			String emailResponsavel = tfEmailResponsavel.getText();
			
			responsavel = new Responsavel(nomeResponsavel, cpfResponsavel, telefoneResponsavel, emailResponsavel);
			responsavelDao.cadastrarResponsavel(responsavel);
		} else {
			responsavel = null;
		}
		
		//Perguntar pro professor se eu faço uma pesquisa por id e adiciono pelo id ou pelo objeto
		
		int cep = Integer.parseInt(tfCep.getText());
		String rua = tfRua.getText();
		String bairro = tfBairro.getText();
		String uf = tfUf.getText();
		String cidade = tfCidade.getText();
		int numero = Integer.parseInt(tfNumero.getText());

		Paciente paciente = new Paciente(nomePaciente, cpfPaciente, dataNascFormatada, telefonePaciente, emailPaciente, null, responsavel, 0);
		pacienteDao.cadastrarPaciente(paciente);
	}
}
