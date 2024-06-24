package window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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

	private List<Consulta> listaConsulta = new ArrayList<>();
	private List<Paciente> listaPaciente = new ArrayList<>();
	private List<Tratamento> listaTratamento = new ArrayList<>();
	private ConsultaDao consultaDao;
	private PacienteDao pacienteDao;
	private TratamentoDao tratamentoDao;

	DateTimeFormatter formatar = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
		scrollPane.setBounds(10, 43, 655, 191);
		panel.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Data", "Hora", "Paciente", "Tratamento", "Descrição", "Comparecimento" }));
		scrollPane.setViewportView(table);

		atualizarTabela();

		JButton btnMarcarDesmarcar = new JButton("<html>Marcar/Desmarcar<br>Comparecimento<html>");
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

		table.setDefaultEditor(Object.class, null);

		table.getTableHeader().setReorderingAllowed(false);
	}

	public void atualizarTabela() {
		modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);

		listaConsulta = consultaDao.listaDeConsultas();

		for (Consulta consulta : listaConsulta) {
			String timeStamp = String.valueOf(consulta.getDataHora());
			long timeStampLong = Long.parseLong(timeStamp);
			LocalDateTime dataHora = LocalDateTime.ofInstant(Instant.ofEpochSecond(timeStampLong),
					ZoneId.systemDefault());

			String data = dataHora.toLocalDate().toString();
			String hora = dataHora.toLocalTime().toString();
			String comparecimento = consulta.isComparecimento() ? "Comparecido" : "Faltou";

			Paciente paciente = pacienteDao.pesquisarPorId(consulta.getPaciente().getId());
			Tratamento tratamento = tratamentoDao.pesquisarPorId(consulta.getTratamento().getId());

			modelo.addRow(new Object[] { data, hora, paciente.getNome(), tratamento.getNome(), consulta.getDescricao(),
					comparecimento });
		}

	}

	public Object selecionarLinhaPorId(Consulta consulta) {
		int linha = table.getSelectedRow();

		if (linha != -1) {
			Object idObj = table.getValueAt(linha, 0);
			int id = (Integer) idObj;
			consulta = consultaDao.pesquisarPorId(id);

			LocalDateTime agora = LocalDateTime.now();
			LocalDateTime agoraSemSegundos = agora.withSecond(0).withNano(0);

			String timeStamp = String.valueOf(consulta.getDataHora());
			long timeStampLong = Long.parseLong(timeStamp);
			LocalDateTime dataHora = LocalDateTime.ofInstant(Instant.ofEpochSecond(timeStampLong),
					ZoneId.systemDefault());
			LocalDateTime dataHoraSemSegundos = dataHora.withSecond(0).withNano(0);

			long diferencaDias = ChronoUnit.DAYS.between(agoraSemSegundos, dataHoraSemSegundos);

			if (diferencaDias >= 5) {
				btnMarcarDesmarcar.setEnabled(false);
			} else {
				btnMarcarDesmarcar.setEnabled(true);
			}

			return consulta;

		} else {
			return null;
		}
	}

	public void excluirConsulta(Consulta consulta) {

		int resultado = JOptionPane.showConfirmDialog(null, "Você está prestes a deletar uma consulta ", "ALERTA",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (resultado == JOptionPane.OK_OPTION) {
			consultaDao.deletarConsulta(consulta.getId());
		}

		listaConsulta = consultaDao.listaDeConsultas();

		// Outro meio utilizado pelo chatGPT

//		Set<Integer> consultaPacienteIds = listaConsulta.stream().map(c -> c.getPaciente().getId())
//				.collect(Collectors.toSet());
//		Set<Integer> consultaTratamentoIds = listaConsulta.stream().map(c -> c.getTratamento().getId())
//				.collect(Collectors.toSet());
//
//		for (Paciente paciente : listaPaciente) {
//			if (paciente.isDeletado() && !consultaPacienteIds.contains(paciente.getId())) {
//				pacienteDao.deletarPaciente(paciente.getId());
//			}
//		}
//
//		for (Tratamento tratamento : listaTratamento) {
//			if (tratamento.isDeletado() && !consultaTratamentoIds.contains(tratamento.getId())) {
//				tratamentoDao.deletarTratamento(tratamento.getId());
//			}
//		}

		for (Consulta consulta1 : listaConsulta) {
			int pacienteId = consulta.getPaciente().getId();
			int tratamentoId = consulta.getTratamento().getId();

			for (Paciente paciente : listaPaciente) {
				if (paciente.isDeletado() == true && pacienteId != paciente.getId()) {
					pacienteDao.deletarPaciente(paciente.getId());
				}
			}

			for (Tratamento tratamento : listaTratamento) {
				if (tratamento.isDeletado() == true && tratamentoId != tratamento.getId()) {
					tratamentoDao.deletarTratamento(tratamento.getId());
				}
			}
		}

		atualizarTabela();
	}

	public void consultasAgendadas() {

		LocalDateTime agora = LocalDateTime.now();
		LocalDateTime agoraSemSegundos = agora.withSecond(0).withNano(0);

		modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);

		listaConsulta = consultaDao.listaDeConsultas();

		for (Consulta consulta : listaConsulta) {

			String timeStamp = String.valueOf(consulta.getDataHora());
			long timeStampLong = Long.parseLong(timeStamp);
			LocalDateTime dataHora = LocalDateTime.ofInstant(Instant.ofEpochSecond(timeStampLong),
					ZoneId.systemDefault());
			LocalDateTime dataHoraSemSegundos = dataHora.withSecond(0).withNano(0);

			if (agoraSemSegundos.isBefore(dataHoraSemSegundos)) {

				String data = dataHora.toLocalDate().toString();
				String hora = dataHora.toLocalTime().toString();
				String comparecimento = consulta.isComparecimento() ? "Comparecido" : "Faltou";

				Paciente paciente = pacienteDao.pesquisarPorId(consulta.getPaciente().getId());
				Tratamento tratamento = tratamentoDao.pesquisarPorId(consulta.getTratamento().getId());

				modelo.addRow(new Object[] { data, hora, paciente.getNome(), tratamento.getNome(),
						consulta.getDescricao(), comparecimento });
			}
		}
	}

	public void consultasPassadas() {

		LocalDateTime agora = LocalDateTime.now();
		LocalDateTime agoraSemSegundos = agora.withSecond(0).withNano(0);

		modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);

		listaConsulta = consultaDao.listaDeConsultas();

		for (Consulta consulta : listaConsulta) {

			String timeStamp = String.valueOf(consulta.getDataHora());
			long timeStampLong = Long.parseLong(timeStamp);
			LocalDateTime dataHora = LocalDateTime.ofInstant(Instant.ofEpochSecond(timeStampLong),
					ZoneId.systemDefault());
			LocalDateTime dataHoraSemSegundos = dataHora.withSecond(0).withNano(0);

			if (agoraSemSegundos.isAfter(dataHoraSemSegundos)) {

				String data = dataHora.toLocalDate().toString();
				String hora = dataHora.toLocalTime().toString();
				String comparecimento = consulta.isComparecimento() ? "Comparecido" : "Faltou";

				Paciente paciente = pacienteDao.pesquisarPorId(consulta.getPaciente().getId());
				Tratamento tratamento = tratamentoDao.pesquisarPorId(consulta.getTratamento().getId());

				modelo.addRow(new Object[] { data, hora, paciente.getNome(), tratamento.getNome(),
						consulta.getDescricao(), comparecimento });
			}
		}
	}

	public void marcarOuDesmarcar(Consulta consulta) {

		if (consulta.isComparecimento() == true) {
			consulta.setComparecimento(false);
		} else {
			consulta.setComparecimento(true);
		}
	}

}
