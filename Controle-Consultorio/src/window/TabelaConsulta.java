package window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.ConsultaDao;
import dao.PacienteDao;
import dao.TratamentoDao;
import entidades.Consulta;
import entidades.Paciente;
import entidades.Tratamento;

public class TabelaConsulta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private JButton btnMarcarDesmarcar;

	private List<Consulta> consultas = new ArrayList<>();
	private Map<Integer, Integer> mapaConsultas = new HashMap<>();
	
	private ConsultaDao consultaDao = new ConsultaDao();
	private PacienteDao pacienteDao = new PacienteDao();
	private TratamentoDao tratamentoDao = new TratamentoDao();
	

	private SimpleDateFormat formatarData = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat formatarHoraSemSeg = new SimpleDateFormat("HH:mm");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TabelaConsulta frame = new TabelaConsulta();
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
	public TabelaConsulta() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 691, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel pTitulo = new JPanel();
		pTitulo.setLayout(null);
		pTitulo.setBackground(Color.GRAY);
		pTitulo.setBounds(0, 0, 675, 44);
		contentPane.add(pTitulo);

		JLabel lblNewLabel = new JLabel("Lista de Consultas");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(267, 11, 143, 22);
		pTitulo.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(0, 44, 675, 295);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(10, 43, 655, 191);
		panel.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Data", "Hora", "Paciente", "Tratamento", "Descrição", "Comparecimento" }));
		scrollPane.setViewportView(table);
		
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);

		atualizarTabela();

		btnMarcarDesmarcar = new JButton("<html>Marcar/Desmarcar<br>Comparecimento<html>");
		btnMarcarDesmarcar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Consulta consulta = new Consulta();
				consulta = (Consulta) selecionarLinhaPorId(consulta);

				if (consulta != null) {
					marcarOuDesmarcar(consulta);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma linha");
				}
				
			}
		});
		btnMarcarDesmarcar.setBounds(334, 247, 121, 37);
		panel.add(btnMarcarDesmarcar);

		JButton btnNewButton_1 = new JButton("<html>Consultas<br>Agendadas<html>");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				consultasAgendadas();
			}
		});
		btnNewButton_1.setBounds(148, 247, 87, 37);
		panel.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("<html>Consultas<br>Passadas<html>");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				consultasPassadas();
			}
		});
		btnNewButton_2.setBounds(245, 247, 79, 37);
		panel.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Excluir");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Consulta consulta = new Consulta();
				consulta = (Consulta) selecionarLinhaPorId(consulta);

				if (consulta != null) {
					excluirConsulta(consulta);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma linha");
				}

			}
		});
		btnNewButton_3.setBounds(465, 261, 89, 23);
		panel.add(btnNewButton_3);
	}

	public void excluirConsulta(Consulta consulta) {

		int resultado = JOptionPane.showConfirmDialog(null, "Você está prestes a deletar uma consulta ", "ALERTA",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (resultado == JOptionPane.OK_OPTION) {
			consultaDao.deletarConsulta(consulta.getId());
		}

		atualizarTabela();
	}

	public void consultasAgendadas() {

		long agoraMiliSeg = Instant.now().toEpochMilli();
		Timestamp timeStampAgora = new Timestamp(agoraMiliSeg);

		modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);

		consultas = consultaDao.listaDeConsultas();
		mapaConsultas.clear();
		int linha = 0;

		for (Consulta consulta : consultas) {

			Timestamp timeStampConsulta = consulta.getDataHora();

			if (timeStampAgora.before(timeStampConsulta)) {
				mapaConsultas.put(linha, consulta.getId());
				
				String data = formatarData.format(timeStampConsulta);
				String hora = formatarHoraSemSeg.format(timeStampConsulta);
				String comparecimento = consulta.isComparecimento() ? "Comparecido" : "Faltou";

				Paciente paciente = pacienteDao.pesquisarPorId(consulta.getPaciente().getId());
				Tratamento tratamento = tratamentoDao.pesquisarPorId(consulta.getTratamento().getId());

				modelo.addRow(new Object[] { data, hora, paciente.getNome(), tratamento.getNome(),
						consulta.getDescricao(), comparecimento });
				
				linha++;
			}
		}
	}

	public void consultasPassadas() {

		long agoraMiliSeg = Instant.now().toEpochMilli();
		Timestamp timeStampAgora = new Timestamp(agoraMiliSeg);

		modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);

		consultas = consultaDao.listaDeConsultas();
		mapaConsultas.clear();
		int linha = 0;

		for (Consulta consulta : consultas) {

			Timestamp timeStampConsulta = consulta.getDataHora();

			if (timeStampAgora.after(timeStampConsulta)) {
				mapaConsultas.put(linha, consulta.getId());
				
				String data = formatarData.format(timeStampConsulta);
				String hora = formatarHoraSemSeg.format(timeStampConsulta);
				String comparecimento = consulta.isComparecimento() ? "Comparecido" : "Faltou";

				Paciente paciente = pacienteDao.pesquisarPorId(consulta.getPaciente().getId());
				Tratamento tratamento = tratamentoDao.pesquisarPorId(consulta.getTratamento().getId());

				modelo.addRow(new Object[] { data, hora, paciente.getNome(), tratamento.getNome(),
						consulta.getDescricao(), comparecimento });
				
				linha++;
			}
		}
	}

	public void marcarOuDesmarcar(Consulta consulta) {
		
		Timestamp timeStampConsulta = consulta.getDataHora();
		
		long agoraMiliSeg = Instant.now().toEpochMilli();
		Timestamp timeStampAgora = new Timestamp(agoraMiliSeg);

		long diferencaDias = ChronoUnit.DAYS.between(timeStampAgora.toInstant(), timeStampConsulta.toInstant());

		if (diferencaDias < 5) {
			if (consulta.isComparecimento() == true) {
				consulta.setComparecimento(false);
			} else if (consulta.isComparecimento() == false) {
				consulta.setComparecimento(true);
			} else {
				consulta.setComparecimento(false);
			}
			consultaDao.alterarConsulta(consulta);
		} else {
			JOptionPane.showMessageDialog(null, "Não foi possível marcar/desmarcar comparecimento, pois há diferença de 5 dias");
		}

		atualizarTabela();
	}
	
	public void atualizarTabela() {
		modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);

		consultas = consultaDao.listaDeConsultas();
		mapaConsultas.clear();
		int linha = 0;

		for (Consulta consulta : consultas) {
			mapaConsultas.put(linha, consulta.getId());
			
			Timestamp timeStampConsulta = consulta.getDataHora();
			
			String data = formatarData.format(timeStampConsulta);
			String hora = formatarHoraSemSeg.format(timeStampConsulta);
			
			String comparecimento = consulta.isComparecimento() ? "Comparecido" : "Faltou";

			Paciente paciente = pacienteDao.pesquisarPorId(consulta.getPaciente().getId());
			Tratamento tratamento = tratamentoDao.pesquisarPorId(consulta.getTratamento().getId());

			modelo.addRow(new Object[] { data, hora, paciente.getNome(), tratamento.getNome(), consulta.getDescricao(),
					comparecimento });
			
			linha++;
		}

	}
	
	public Object selecionarLinhaPorId(Consulta consulta) {
		int linha = table.getSelectedRow();

		if (linha != -1) {

			int id = mapaConsultas.get(linha);
			consulta = consultaDao.pesquisarPorId(id);

			return consulta;

		} else {
			return null;
		}
	}

}