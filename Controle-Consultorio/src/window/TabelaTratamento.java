package window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import dao.TratamentoDao;
import entidades.Tratamento;

public class TabelaTratamento extends JFrame {

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
	private JButton btnNewButton_3;

	private List<Tratamento> tratamentos = new ArrayList<>();
	private Map<Integer, Integer> mapaTratamentos = new HashMap<>();
	
	private TratamentoDao tratamentoDao = new TratamentoDao();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TabelaTratamento frame = new TabelaTratamento();
					frame.setVisible(true);
					frame.setResizable(false); // Impede redimensionamento
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TabelaTratamento() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 553, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(0, 44, 537, 295);
		contentPane.add(panel);
		panel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(10, 43, 517, 191);
		panel.add(scrollPane);

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Nome", "Preco", "Descrição" }));
		scrollPane.setViewportView(table);

		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);

		atualizarTabela();

		JLabel lblNewLabel_1 = new JLabel("Pesquisar:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(355, 14, 54, 15);
		panel.add(lblNewLabel_1);

		textField = new JTextField();
		textField.setBounds(419, 12, 108, 20);

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
				
				Tratamento tratamento = new Tratamento();
				tratamento = (Tratamento) selecionarLinhaPorId(tratamento);

				if (tratamento != null) {
					
							editarTratamento(tratamento);
						
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma linha");
				}
			}
		});
		btnNewButton.setBounds(171, 261, 89, 23);
		panel.add(btnNewButton);

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
				Tratamento tratamento = new Tratamento();
				tratamento = (Tratamento) selecionarLinhaPorId(tratamento);

				if (tratamento != null) {
					excluirTratamento(tratamento);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma linha");
				}
			}
		});
		btnNewButton_3.setBounds(270, 261, 89, 23);
		panel.add(btnNewButton_3);

		JPanel pTitulo = new JPanel();
		pTitulo.setBounds(0, 0, 675, 44);
		contentPane.add(pTitulo);
		pTitulo.setLayout(null);
		pTitulo.setBackground(Color.GRAY);

		JLabel lblNewLabel = new JLabel("Lista de Tratamentos");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(185, 11, 166, 22);
		pTitulo.add(lblNewLabel);

	}

	public void pesquisaDinamica() {
		String pesquisa = textField.getText();
		tratamentos = tratamentoDao.listaDeTratamentos();

		modelo.setRowCount(0);

		mapaTratamentos.clear();
		int linha = 0;

		for (Tratamento tratamento : tratamentos) {
			if (tratamento.getNome().toLowerCase().startsWith(pesquisa.toLowerCase())) {
				if (tratamento.isDeletado() != true) {
					mapaTratamentos.put(linha, tratamento.getId());

					modelo.addRow(new Object[] { tratamento.getNome(), tratamento.getPreco(), tratamento.getDescricao() });

					linha++;
				}
			}
		}
	}

	public void editarTratamento(Tratamento tratamento) {
		int id = tratamento.getId();

		JTextField tfNome = new JTextField(tratamento.getNome());
		JTextField tfPreco = new JTextField(String.valueOf(tratamento.getPreco()));
		JTextField tfDescricao = new JTextField(tratamento.getDescricao());

		JLabel lblNome = new JLabel("Nome");
		JLabel lblPreço = new JLabel("Preço");
		JLabel lblDescricao = new JLabel("Descrição");

		JPanel painel = new JPanel(new GridLayout(0, 1));
		painel.add(lblNome);
		painel.add(tfNome);
		painel.add(lblPreço);
		painel.add(tfPreco);
		painel.add(lblDescricao);
		painel.add(tfDescricao);

		int resultado = JOptionPane.showConfirmDialog(null, painel, "Editar tratamento", JOptionPane.OK_CANCEL_OPTION);

		if (resultado == JOptionPane.OK_OPTION) {
			
			String nome = tfNome.getText();
			double preco = Double.parseDouble(tfPreco.getText());
			String descricao = tfDescricao.getText();
			
			tratamento = new Tratamento(id, nome, preco, descricao, false);
			tratamentoDao.alterarTratamento(tratamento);

			atualizarTabela();
		}

	}

	public void excluirTratamento(Tratamento tratamento) {

		int resultado = JOptionPane.showConfirmDialog(null,
				"Você está prestes a deletar o tratamento " + tratamento.getNome(), "ALERTA", JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);

		if (resultado == JOptionPane.OK_OPTION) {
			// Puxa uma lista para conferir se possui consultas antes de deletar
			if (tratamentoDao.possuiConsulta(tratamento.getId()) == true) {
				JOptionPane.showMessageDialog(null,
						"O tratamento ainda possui consultas registradas no histórico do sistema e apenas ficará oculto",
						"Aviso", JOptionPane.DEFAULT_OPTION);

				tratamento.setDeletado(true);
				tratamentoDao.alterarTratamento(tratamento);

			} else {
				
				tratamentoDao.deletarTratamento(tratamento.getId());
			}
		}

		atualizarTabela();
	}

	public void atualizarTabela() {
		modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);

		tratamentos = tratamentoDao.listaDeTratamentos();
		mapaTratamentos.clear();
		int linha = 0;

		for (Tratamento tratamento : tratamentos) {
			if (tratamento.isDeletado() != true) {
				mapaTratamentos.put(linha, tratamento.getId());

				modelo.addRow(new Object[] { tratamento.getNome(), tratamento.getPreco(), tratamento.getDescricao() });

				linha++;
			}
		}

	}

	public Object selecionarLinhaPorId(Tratamento tratamento) {
		int linha = table.getSelectedRow();
		if (linha != -1) {

			int id = mapaTratamentos.get(linha);
			tratamento = tratamentoDao.pesquisarPorId(id);

			return tratamento;
		} else {
			return null;
		}
	}

}
