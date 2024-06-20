package window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class trocar {

	private JFrame frame;
	private JTextField tfNome;
	private JTextField tfCpf;
	private JTextField tfDataNasc;
	private JTextField tfTelefone;
	private JTextField tfEmail;
	private JTextField tfCep;
	private JTextField tfRua;
	private JTextField tfBairro;
	private JTextField tfUf;
	private JTextField tfCidade;
	private JTextField tfNumero;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					trocar window = new trocar();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public trocar() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(new Color(192, 192, 192));
		frame.setBounds(100, 100, 482, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 43, 231, 262);
		frame.getContentPane().add(panel);
		panel.setBackground(new Color(192, 192, 192));
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Paciente");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(85, 11, 58, 20);
		panel.add(lblNewLabel_2);
		
		tfNome = new JTextField();
		tfNome.setBounds(76, 52, 145, 20);
		panel.add(tfNome);
		tfNome.setColumns(10);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Possui responsável?");
		chckbxNewCheckBox.setBackground(Color.LIGHT_GRAY);
		chckbxNewCheckBox.setBounds(10, 202, 121, 23);
		panel.add(chckbxNewCheckBox);
		
		JLabel lblNewLabel_3 = new JLabel("Nome");
		lblNewLabel_3.setBounds(10, 55, 46, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("CPF");
		lblNewLabel_4.setBounds(10, 88, 46, 14);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("<html>Data de<br>nascimento<html>");
		lblNewLabel_5.setBounds(10, 113, 58, 28);
		panel.add(lblNewLabel_5);
		
		tfCpf = new JTextField();
		tfCpf.setBounds(76, 83, 111, 20);
		panel.add(tfCpf);
		tfCpf.setColumns(10);
		
		tfDataNasc = new JTextField();
		tfDataNasc.setBounds(76, 113, 86, 20);
		panel.add(tfDataNasc);
		tfDataNasc.setColumns(10);
		
		tfTelefone = new JTextField();
		tfTelefone.setBounds(76, 144, 86, 20);
		panel.add(tfTelefone);
		tfTelefone.setColumns(10);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(76, 175, 145, 20);
		panel.add(tfEmail);
		tfEmail.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Telefone");
		lblNewLabel_6.setBounds(10, 147, 46, 14);
		panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Email");
		lblNewLabel_7.setBounds(10, 178, 46, 14);
		panel.add(lblNewLabel_7);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(192, 192, 192));
		panel_1.setBounds(0, 0, 466, 44);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cadastro de Paciente");
		lblNewLabel.setBounds(145, 5, 165, 22);
		panel_1.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(192, 192, 192));
		panel_2.setBounds(231, 43, 235, 262);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Endereço");
		lblNewLabel_1.setBounds(89, 11, 65, 20);
		panel_2.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lblNewLabel_8 = new JLabel("CEP");
		lblNewLabel_8.setBounds(10, 53, 46, 14);
		panel_2.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Rua");
		lblNewLabel_9.setBounds(10, 84, 46, 14);
		panel_2.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Bairro");
		lblNewLabel_10.setBounds(10, 115, 46, 14);
		panel_2.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("UF");
		lblNewLabel_11.setBounds(10, 146, 46, 14);
		panel_2.add(lblNewLabel_11);
		
		JLabel lblNewLabel_12 = new JLabel("Cidade");
		lblNewLabel_12.setBounds(10, 177, 46, 14);
		panel_2.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("Número");
		lblNewLabel_13.setBounds(10, 210, 46, 14);
		panel_2.add(lblNewLabel_13);
		
		tfCep = new JTextField();
		tfCep.setBounds(66, 50, 107, 20);
		panel_2.add(tfCep);
		tfCep.setColumns(10);
		
		tfRua = new JTextField();
		tfRua.setBounds(66, 81, 146, 20);
		panel_2.add(tfRua);
		tfRua.setColumns(10);
		
		tfBairro = new JTextField();
		tfBairro.setBounds(66, 112, 146, 20);
		panel_2.add(tfBairro);
		tfBairro.setColumns(10);
		
		tfUf = new JTextField();
		tfUf.setBounds(66, 143, 146, 20);
		panel_2.add(tfUf);
		tfUf.setColumns(10);
		
		tfCidade = new JTextField();
		tfCidade.setBounds(66, 174, 146, 20);
		panel_2.add(tfCidade);
		tfCidade.setColumns(10);
		
		tfNumero = new JTextField();
		tfNumero.setBounds(66, 207, 72, 20);
		panel_2.add(tfNumero);
		tfNumero.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(192, 192, 192));
		panel_3.setBounds(0, 304, 466, 35);
		frame.getContentPane().add(panel_3);
		
		JButton btnNewButton = new JButton("Continuar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		panel_3.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Limpar dados");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		panel_3.add(btnNewButton_1);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.GRAY);
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Cadastrar");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Listas");
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_1 = new JMenu("Consultas");
		mnNewMenu.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Agendadas");
		mnNewMenu_1.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Finalizadas");
		mnNewMenu_1.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Estoque");
		mnNewMenu.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Tela Inicial");
		mnNewMenu.add(mntmNewMenuItem_5);
	}
}
