package window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.TratamentoDao;
import entidades.Tratamento;

public class CadastroTratamento extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNome;
	private JTextField tfPreco;

	private TratamentoDao tratamentoDao = new TratamentoDao();
	private JTextField tfDescricao;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroTratamento frame = new CadastroTratamento();
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
	public CadastroTratamento() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 402, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pTitulo = new JPanel();
		pTitulo.setBackground(new Color(128, 128, 128));
		pTitulo.setBounds(0, 0, 386, 44);
		contentPane.add(pTitulo);
		pTitulo.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cadastro de Tratamento");
		lblNewLabel.setBounds(100, 11, 191, 22);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pTitulo.add(lblNewLabel);
		
		JPanel pEndereco = new JPanel();
		pEndereco.setBackground(new Color(192, 192, 192));
		pEndereco.setBounds(0, 43, 386, 292);
		contentPane.add(pEndereco);
		pEndereco.setLayout(null);
		pEndereco.setVisible(true);
		
		JLabel lblNewLabel_12 = new JLabel("Tratamento");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_12.setBounds(150, 11, 82, 20);
		pEndereco.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("Nome");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_13.setBounds(63, 48, 32, 15);
		pEndereco.add(lblNewLabel_13);
		
		tfNome = new JTextField();
		tfNome.setBounds(105, 46, 198, 20);
		pEndereco.add(tfNome);
		tfNome.setColumns(10);
		
		JLabel lblNewLabel_14 = new JLabel("Preço");
		lblNewLabel_14.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_14.setBounds(64, 78, 31, 15);
		pEndereco.add(lblNewLabel_14);
		
		tfPreco = new JTextField();
		tfPreco.setBounds(105, 76, 95, 20);
		pEndereco.add(tfPreco);
		tfPreco.setColumns(10);
		
		JLabel lblNewLabel_15 = new JLabel("Descrição");
		lblNewLabel_15.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_15.setBounds(44, 115, 51, 15);
		pEndereco.add(lblNewLabel_15);
		
		JLabel lblNewLabel_16 = new JLabel("");
		lblNewLabel_16.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_16.setBounds(10, 141, 46, 14);
		pEndereco.add(lblNewLabel_16);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrarTratamento();
			}
		});
		btnSalvar.setBounds(287, 258, 89, 23);
		pEndereco.add(btnSalvar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(105, 108, 198, 77);
		pEndereco.add(scrollPane);
		
		tfDescricao = new JTextField();
		scrollPane.setViewportView(tfDescricao);
		tfDescricao.setColumns(10);
		
	}
	
	public void cadastrarTratamento() {
		String nome = tfNome.getText();
		double preco = Double.parseDouble(tfPreco.getText());
		String descricao = tfDescricao.getText();
		
		Tratamento tratamento = new Tratamento(nome, preco, descricao, false);
		
		try {
			tratamentoDao.cadastrarTratamento(tratamento);
			JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso.", "Cadastro", JOptionPane.DEFAULT_OPTION);
			this.dispose();
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Ocorreu um erro ao tentar cadastrar o tratamento", "Erro", JOptionPane.OK_OPTION);
	        return;
	    }
		
	}
}
