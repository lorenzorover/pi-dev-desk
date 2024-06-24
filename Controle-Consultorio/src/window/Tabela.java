package window;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import dao.PessoaDao;
import entidades.Pessoa;

public class Tabela {

	private JFrame frame;
	private JTable table;
	private DefaultTableModel modelo;
	private List<Pessoa> lista = new ArrayList<>();
	private PessoaDao dao = new PessoaDao();
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tabela window = new Tabela();
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
	public Tabela() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 629, 334);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setEnabled(false);
		scrollPane.setBounds(69, 41, 481, 133);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Nome", "Idade", "Email" }));

		JButton btnRemover = new JButton("Deletar");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				removerPessoa();
			}
		});
		btnRemover.setBounds(164, 196, 89, 23);
		frame.getContentPane().add(btnRemover);

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				editarPessoa();
			}
		});
		btnEditar.setBounds(263, 196, 89, 23);
		frame.getContentPane().add(btnEditar);

		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarTabela();
			}
		});
		btnListar.setBounds(362, 196, 89, 23);
		frame.getContentPane().add(btnListar);

		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarPessoa();
			}
		});
		btnPesquisar.setBounds(461, 196, 103, 23);
		frame.getContentPane().add(btnPesquisar);

		// Impedindo edição
		table.setDefaultEditor(Object.class, null);

		// Impedir mexer colunas
		table.getTableHeader().setReorderingAllowed(false);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int linha = table.getSelectedRow();
				int coluna = table.getSelectedColumn();

				System.out.println("Linha selecionada: " + linha);
				System.out.println("Coluna selecionada: " + coluna);

			}
		});

		atualizarTabela();

		textField = new JTextField();
		textField.setBounds(43, 250, 86, 20);

		textField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				consultaDinamica();

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				consultaDinamica();

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				consultaDinamica();

			}
		});

		frame.getContentPane().add(textField);
		textField.setColumns(10);
		// table.setEnabled(false);

	}

	public void atualizarTabela() {

		modelo = (DefaultTableModel) table.getModel();

		// Limpando dados da tabela
		modelo.setRowCount(0);

		// Pegando dados
		lista = dao.listarPessoas();

		for (Pessoa pessoa : lista) {
			modelo.addRow(new Object[] { pessoa.getId(), pessoa.getNome(), pessoa.getIdade(), pessoa.getEmail() });
		}

	}

	public void cadastrarPessoa() {

		// Criando os campos
		JTextField tfNome = new JTextField();
		JTextField tfIdade = new JTextField();
		JTextField tfEmail = new JTextField();

		// Rótulos
		JLabel lblNome = new JLabel("Nome");
		JLabel lblIdade = new JLabel("Idade");
		JLabel lblEmail = new JLabel("Email");

		// Painel
		JPanel painel = new JPanel(new GridLayout(0, 1));
		painel.add(lblNome);
		painel.add(tfNome);
		painel.add(lblEmail);
		painel.add(tfEmail);
		painel.add(lblIdade);
		painel.add(tfIdade);

		int resultado = JOptionPane.showConfirmDialog(null, painel, "Cadastro", JOptionPane.OK_CANCEL_OPTION);

		if (resultado == JOptionPane.OK_OPTION) {

			String nome = tfNome.getText();
			int idade = Integer.parseInt(tfIdade.getText());
			String email = tfEmail.getText();
			Pessoa pessoa = new Pessoa(nome, idade, email); // Objeto pessoa sem o id

			dao.inserirPessoa(pessoa);
			atualizarTabela();
		}
	}

	public void removerPessoa() {

		int linha = table.getSelectedRow();

		if (linha != -1) { // Caso o usuário nao selecione nenhum, a Row fica -1
			int resultado = JOptionPane.showConfirmDialog
					(null, "Deseja sair?","Confirmação",JOptionPane.YES_NO_OPTION);
			
			if (resultado == JOptionPane.YES_OPTION) {
				Object idObj = table.getValueAt(linha, 0);
				int id = (Integer) idObj;
				dao.excluirPessoa(id);
				atualizarTabela();
				frame.dispose();
			}
			

		} else {
			JOptionPane.showMessageDialog(null, "Selecione uma pessoa para usar a função deletar");
		}
	}

	public void editarPessoa() {

		int linha = table.getSelectedRow();

		if (linha != -1) {
			// Pega o id selecionado
			Object idObj = table.getValueAt(linha, 0);
			int id = (Integer) idObj;

			Pessoa pessoa = dao.pesquisarPorId(id);

			// Criando os campos
			JTextField tfNome = new JTextField(pessoa.getNome());
			JTextField tfIdade = new JTextField(String.valueOf(pessoa.getIdade()));
			JTextField tfEmail = new JTextField(pessoa.getEmail());

			// Rótulos
			JLabel lblNome = new JLabel("Nome");
			JLabel lblIdade = new JLabel("Idade");
			JLabel lblEmail = new JLabel("Email");

			// Painel
			JPanel painel = new JPanel(new GridLayout(0, 1));
			painel.add(lblNome);
			painel.add(tfNome);
			painel.add(lblEmail);
			painel.add(tfEmail);
			painel.add(lblIdade);
			painel.add(tfIdade);

			int resultado = JOptionPane.showConfirmDialog(null, painel, "Editar", JOptionPane.OK_CANCEL_OPTION);

			if (resultado == JOptionPane.OK_OPTION) {

				String nome = tfNome.getText();
				int idade = Integer.parseInt(tfIdade.getText());
				String email = tfEmail.getText();
				pessoa = new Pessoa(id, nome, idade, email); // Objeto pessoa sem o id

				dao.alterarPessoa(pessoa);
				atualizarTabela();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Selecione uma linha para editar");
		}
	}

	public void consultaDinamica() {

		String consulta = textField.getText();
		lista = dao.listarPessoas();

		// Limpando dados da tabela
		modelo.setRowCount(0);

		for (Pessoa pessoa : lista) {

			if (pessoa.getNome().toLowerCase().startsWith(consulta.toLowerCase())) {
				modelo.addRow(new Object[] { pessoa.getId(), pessoa.getNome(), pessoa.getIdade(), pessoa.getEmail() });
			}
		}
	}
}
