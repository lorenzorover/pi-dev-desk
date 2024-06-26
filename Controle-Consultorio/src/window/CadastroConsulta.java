package window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private JComboBox cbPaciente;
	private JComboBox cbTratamento;
	private JFormattedTextField ftfHora;
	private JFormattedTextField ftfData1;
	
	private MaskFormatter mascaraData;
	private MaskFormatter mascaraHora;
	private SimpleDateFormat formatarDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	private DefaultComboBoxModel<Paciente> modeloPaciente;
	private DefaultComboBoxModel<Tratamento> modeloTratamento;
	
	private PacienteDao pacienteDao;
	private TratamentoDao tratamentoDao;
	private ConsultaDao consultaDao;
	private List<Paciente> listaPaciente = new ArrayList<>();
	private List<Tratamento> listaTratamento = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroConsulta frame = new CadastroConsulta();
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
		
		JComboBox cbPaciente = new JComboBox();
		cbPaciente.setBounds(139, 102, 152, 22);
		panel.add(cbPaciente);
		inserirCBPaciente(cbPaciente);
		
		JComboBox cbTratamento = new JComboBox();
		cbTratamento.setBounds(139, 135, 152, 22);
		panel.add(cbTratamento);
		inserirCBTratamento(cbTratamento);
		
		try {
            mascaraData = new MaskFormatter("##/##/####");
            mascaraHora = new MaskFormatter("##:##");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
		
		JFormattedTextField ftfHora = new JFormattedTextField(mascaraHora);
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
		
		JFormattedTextField ftfData1 = new JFormattedTextField(mascaraData);
		ftfData1.setBounds(139, 40, 79, 20);
		panel.add(ftfData1);
		
		//DateTime localDateTime = LocalDateTime.of(localDate, localTime);
		
		
	}
	
	public void inserirCBPaciente(JComboBox<Paciente> cbPaciente) {
		modeloPaciente = (DefaultComboBoxModel<Paciente>) cbPaciente.getModel();
		
		listaPaciente = pacienteDao.listaDePacientes();
		for (Paciente paciente : listaPaciente) {
			modeloPaciente.addElement(paciente);
		}
	}
	
	public void inserirCBTratamento(JComboBox<Tratamento> cbTratamento) {
		modeloTratamento = (DefaultComboBoxModel<Tratamento>) cbTratamento.getModel();
		
		listaTratamento = tratamentoDao.listaDeTratamentos();
		for (Tratamento tratamento : listaTratamento) {
			modeloTratamento.addElement(tratamento);
		}
	}
	
	public void cadastrarConsulta() {
		String dataString = ftfData1.getText();
		Date data = Date.valueOf(dataString);
		String horaString = ftfHora.getText();
		Time hora = Time.valueOf(horaString);
		
		Timestamp timeStamp = new Timestamp(data.getTime() + hora.getTime());
		String timeStampString = formatarDataHora.format(timeStamp);
		java.util.Date dataHoraJava = null;
		try {
			dataHoraJava = formatarDataHora.parse(timeStampString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Timestamp dataHora = new java.sql.Timestamp(dataHoraJava.getTime());
		
		String descricao = tfDescricao.getText();
		Paciente paciente = (Paciente) cbPaciente.getSelectedItem();
		Tratamento tratamento = (Tratamento) cbTratamento.getSelectedItem();
		
		Consulta consulta = new Consulta(dataHora, descricao, false, paciente, tratamento);
		consultaDao.cadastrarConsulta(consulta);
	}
}
