package window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import dao.ConsultaDao;
import dao.PacienteDao;
import dao.TratamentoDao;
import entidades.Consulta;
import entidades.Paciente;
import entidades.Tratamento;

public class CadastroConsulta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfDescricao;
	private JComboBox<Paciente> cbPaciente;
	private JComboBox<Tratamento> cbTratamento;
	private JFormattedTextField ftfHora;
	private JFormattedTextField ftfData;
	
	private MaskFormatter mascaraData;
	private MaskFormatter mascaraHora;
	
	private PacienteDao pacienteDao = new PacienteDao();
	private TratamentoDao tratamentoDao = new TratamentoDao();
	private ConsultaDao consultaDao = new ConsultaDao();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroConsulta frame = new CadastroConsulta();
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
	public CadastroConsulta() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 419, 373);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pTitulo = new JPanel();
		pTitulo.setLayout(null);
		pTitulo.setBackground(Color.GRAY);
		pTitulo.setBounds(0, 0, 403, 44);
		contentPane.add(pTitulo);
		
		JLabel lblNewLabel = new JLabel("Cadastro de Consulta");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(121, 11, 168, 22);
		pTitulo.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(0, 43, 403, 291);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Data da consulta");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(38, 42, 91, 15);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Hora");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(104, 73, 25, 15);
		panel.add(lblNewLabel_2);
		
		cbPaciente = new JComboBox<>();
		cbPaciente.setBounds(139, 102, 152, 22);
		panel.add(cbPaciente);
		try {
		List<Paciente> pacientes = pacienteDao.listaDePacientes();
		for (Paciente paciente : pacientes) {
            cbPaciente.addItem(paciente);
        }
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		cbTratamento = new JComboBox<>();
		cbTratamento.setBounds(139, 135, 152, 22);
		panel.add(cbTratamento);
		try {
		List<Tratamento> tratamentos = tratamentoDao.listaDeTratamentos();
		for (Tratamento tratamento : tratamentos) {
			cbTratamento.addItem(tratamento);
		}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		try {
            mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');
            mascaraHora = new MaskFormatter("##:##");
            mascaraHora.setPlaceholderCharacter('_');
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
		
		ftfHora = new JFormattedTextField(mascaraHora);
		ftfHora.setBounds(139, 71, 79, 20);
		panel.add(ftfHora);
		
		JLabel lblNewLabel_3 = new JLabel("Paciente");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(82, 105, 47, 15);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Tratamento");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(64, 138, 65, 15);
		panel.add(lblNewLabel_4);
		
		JButton btnNewButton = new JButton("Salvar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cadastrarConsulta();
			}
		});
		btnNewButton.setBounds(304, 257, 89, 23);
		panel.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(139, 168, 152, 69);
		panel.add(scrollPane);
		
		tfDescricao = new JTextField();
		scrollPane.setViewportView(tfDescricao);
		tfDescricao.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Descrição");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(78, 173, 51, 15);
		panel.add(lblNewLabel_5);
		
		ftfData = new JFormattedTextField(mascaraData);
		ftfData.setBounds(139, 40, 79, 20);
		panel.add(ftfData);
		
		
	}
	
	public void cadastrarConsulta() {
		SimpleDateFormat formatarDataHoraEntrada = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		SimpleDateFormat formatoTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String dataString = ftfData.getText();
		String horaString = ftfHora.getText() + ":00";
		
		Timestamp timeStamp = null;
		
		try {
		    String dataHoraString = dataString + " " + horaString;
		    java.util.Date dataHoraJava = formatarDataHoraEntrada.parse(dataHoraString);
		    String timeStampString = formatoTimestamp.format(dataHoraJava);
		    timeStamp = Timestamp.valueOf(timeStampString);

		} catch (ParseException e) {
		    e.printStackTrace();
		}
		
		java.sql.Timestamp dataHora = new java.sql.Timestamp(timeStamp.getTime());
		String descricao = tfDescricao.getText();
		Paciente pacienteSelecionado = (Paciente) cbPaciente.getSelectedItem();
		Tratamento tratamentoSelecionado = (Tratamento) cbTratamento.getSelectedItem();
		
		if (pacienteSelecionado == null || tratamentoSelecionado == null) {
	        JOptionPane.showMessageDialog(null, "Selecione um paciente e um tratamento.");
	        return;
	    }
		
		Paciente paciente = pacienteDao.pesquisarPorId(pacienteSelecionado.getId());
		Tratamento tratamento = tratamentoDao.pesquisarPorId(tratamentoSelecionado.getId());
		
		Consulta consulta = new Consulta(dataHora, descricao, false, paciente, tratamento);
		
		try {
			consultaDao.cadastrarConsulta(consulta);
			JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso.", "Cadastro", JOptionPane.DEFAULT_OPTION);
			this.dispose();
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Ocorreu um erro ao tentar cadastrar a consulta", "Erro", JOptionPane.OK_OPTION);
	        return;
	    }
		
	}
}
