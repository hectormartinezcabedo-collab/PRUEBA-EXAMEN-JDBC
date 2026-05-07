package farmacia;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;


class ConnectionSingleton {
	private static Connection con;

	public static Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://127.0.0.1:3307/farmacia";
		String user = "alumno";
		String password = "alumno";
		if (con == null || con.isClosed()) {
			con = DriverManager.getConnection(url, user, password);
		}
		return con;
	}
}

public class Medicamento {
	
	public boolean validarCamposConInt( JTextField txt2, JTextField txt3, JFrame frame) {

		   // 1. Comprobar si están vacíos
		   if ( txt2.getText().isEmpty() || txt3.getText().isEmpty()) {
		       JOptionPane.showMessageDialog(frame, "Todos los campos deben estar rellenos");
		       return false;
		   }

		   // 2. Comprobar que txt2 sea solo texto (letras y espacios)
		   if (!txt2.getText().matches("^[a-z,A-Z]+$")) {
		       JOptionPane.showMessageDialog(frame, "El segundo campo solo puede contener letras");
		       return false;
		   }

		   // 3. Comprobar que txt3 sea número
		   try {
		       Integer.parseInt(txt3.getText());
		   } catch (NumberFormatException e) {
		       JOptionPane.showMessageDialog(frame, "El tercer campo debe ser un número");
		       return false;
		   }

		   return true;
		}


		public boolean validarCamposStrings( JTextField txt2, JTextField txt3, JFrame frame) {

		// Comprobar si están vacíos
		if  (txt2.getText().isEmpty() || txt3.getText().isEmpty()) {
		JOptionPane.showMessageDialog(frame, "Todos los campos deben estar rellenos");
		return false;
		}


		if(!txt2.getText().matches("^\\w+$")) {
		JOptionPane.showMessageDialog(frame,"El campo x tiene que ser un String", "Error en x", JOptionPane.ERROR_MESSAGE);
		return false;
		}
		if(!txt3.getText().matches("^\\w+$")) {
		JOptionPane.showMessageDialog(frame,"El campo x tiene que ser un String", "Error en x", JOptionPane.ERROR_MESSAGE);
		return false;
		}

		return true;
		}
	
	static void cargarTablaMedicamentos(DefaultTableModel modelMedicamentos) {
		try {
			Connection con = ConnectionSingleton.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM medicamentos");
			while (rs.next()) {
				Object[] row = new Object[5];
				row[0] = rs.getInt("idMedicamento");
				row[1] = rs.getString("nombre");
				row[2] = rs.getString("formato");
				row[3] = rs.getString("fechaCaducidad");
				row[4] = rs.getBoolean("stock");
				modelMedicamentos.addRow(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void cargarTablaMedicamentosSinStock(DefaultTableModel modelMedicamentosSinStock) {
		try {
			Connection con = ConnectionSingleton.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM medicamentos WHERE stock = 0");
			while (rs.next()) {
				Object[] row = new Object[5];
				row[0] = rs.getInt("idMedicamento");
				row[1] = rs.getString("nombre");
				row[2] = rs.getString("formato");
				row[3] = rs.getString("fechaCaducidad");
				row[4] = rs.getBoolean("stock");
				modelMedicamentosSinStock.addRow(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private JFrame frame;
	private JTable tableMedicamentos;
	private JButton btnAadir;
	private JButton btnActualizar;
	private JButton btnBorrar;
	private JTextField textField_ID;
	private JTextField textField_Nombre;
	private JTextField textField_FechaCaducidad;
	private JScrollPane scrollPaneSinStock;
	private JTable tableSinStock;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Medicamento window = new Medicamento();
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
	public Medicamento() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 960, 601);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		
		String url = "jdbc:mysql://127.0.0.1:3307/farmacia";
		String user = "alumno";
		String password = "alumno";
		
		JScrollPane scrollPaneMedicamentos = new JScrollPane();
		scrollPaneMedicamentos.setBounds(86, 37, 403, 239);
		frame.getContentPane().add(scrollPaneMedicamentos);
		
		DefaultTableModel modelMedicamentos = new DefaultTableModel();
		modelMedicamentos.addColumn("ID Medicamento");
		modelMedicamentos.addColumn("Nombre");
		modelMedicamentos.addColumn("Formato");
		modelMedicamentos.addColumn("Fecha Caducidad");
		modelMedicamentos.addColumn("Stock");
		
		try {
			Connection con = ConnectionSingleton.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM medicamentos");
			while (rs.next()) {
				Object[] row = new Object[5];
				row[0] = rs.getInt("idMedicamento");
				row[1] = rs.getString("nombre");
				row[2] = rs.getString("formato");
				row[3] = rs.getString("fechaCaducidad");
				row[4] = rs.getBoolean("stock");
				modelMedicamentos.addRow(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tableMedicamentos = new JTable(modelMedicamentos);
		scrollPaneMedicamentos.setViewportView(tableMedicamentos);
		 
// 	dwadadwadawdawdawdwadwa


		scrollPaneSinStock = new JScrollPane();
		scrollPaneSinStock.setBounds(629, 37, 319, 239);
		frame.getContentPane().add(scrollPaneSinStock);
		DefaultTableModel modelMedicamentosSinStock = new DefaultTableModel();
		modelMedicamentosSinStock.addColumn("ID Medicamento");
		modelMedicamentosSinStock.addColumn("Nombre");
		modelMedicamentosSinStock.addColumn("Formato");
		modelMedicamentosSinStock.addColumn("Fecha Caducidad");
		modelMedicamentosSinStock.addColumn("Stock");
		
		
		tableSinStock = new JTable(modelMedicamentosSinStock);
		scrollPaneSinStock.setViewportView(tableSinStock);
		
		textField_ID = new JTextField();
		textField_ID.setEditable(false);
		textField_ID.setBounds(86, 306, 114, 21);
		frame.getContentPane().add(textField_ID);
		textField_ID.setColumns(10);
		
		textField_Nombre = new JTextField();
		textField_Nombre.setBounds(86, 339, 114, 21);
		frame.getContentPane().add(textField_Nombre);
		textField_Nombre.setColumns(10);
		
		textField_FechaCaducidad = new JTextField();
		textField_FechaCaducidad.setBounds(86, 411, 114, 21);
		frame.getContentPane().add(textField_FechaCaducidad);
		textField_FechaCaducidad.setColumns(10);
		
		JComboBox comboBox_Formato = new JComboBox();
		comboBox_Formato.setModel(new DefaultComboBoxModel(new String[] {"", "1) pastillas", "2) jarabe", "3) pomada"}));
		comboBox_Formato.setBounds(86, 372, 114, 26);
		frame.getContentPane().add(comboBox_Formato);
		
		JCheckBox chckbxStock = new JCheckBox("Stock");
		chckbxStock.setBounds(86, 440, 57, 25);
		frame.getContentPane().add(chckbxStock);
		
		
		
		btnAadir = new JButton("Añadir");
		btnAadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con;
				boolean valido=false;
				do {
				if(textField_Nombre.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "El nombre debe estar relleno");
				}else if(comboBox_Formato.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(frame, "El formato debe estar relleno");
				}else if(textField_FechaCaducidad.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "La fecha de caducidad debe estar relleno");
				}else if(!textField_FechaCaducidad.getText().matches("^(ENE|FEB|MAR|ABR|MAY|JUN|JUL|AGO|SEP|OCT|NOV|DIC)2[7-9]$")) {
					JOptionPane.showMessageDialog(frame, "La fecha de caducidad debe tener un formato adecuado (EJ: ENE27, año valido solo entre el 2027 y 2029)");
				}else {
					JOptionPane.showMessageDialog(frame, "Medicamento añadido correctamente");
					valido=true;
					try {
						con = DriverManager.getConnection(url, user, password);
						PreparedStatement ins_pstmt = con.prepareStatement(
								"INSERT INTO medicamentos (nombre, formato, fechaCaducidad, stock) VALUES (?, ?, ?, ?)");
						ins_pstmt.setString(1, textField_Nombre.getText()); // Primer “?”
						ins_pstmt.setInt(2, comboBox_Formato.getSelectedIndex()); // Segundo “?”
						ins_pstmt.setString(3, textField_FechaCaducidad.getText()); // Tercer “?”
						ins_pstmt.setBoolean(4, chckbxStock.isSelected()); // Cuarto “?”
						int rowsInserted = ins_pstmt.executeUpdate();
						ins_pstmt.close();
						modelMedicamentos.setRowCount(0);
						cargarTablaMedicamentos(modelMedicamentos);
						modelMedicamentosSinStock.setRowCount(0);
						cargarTablaMedicamentosSinStock(modelMedicamentosSinStock);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				}while(valido=false);
					
				
				
				
			}
		});
		btnAadir.setBounds(86, 495, 105, 27);
		frame.getContentPane().add(btnAadir);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con;
				boolean valido=false;
				do {
				if(textField_Nombre.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "El nombre debe estar relleno");
				}else if(comboBox_Formato.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(frame, "El formato debe estar relleno");
				}else if(textField_FechaCaducidad.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "La fecha de caducidad debe estar relleno");
				}else if(!textField_FechaCaducidad.getText().matches("^(ENE|FEB|MAR|ABR|MAY|JUN|JUL|AGO|SEP|OCT|NOV|DIC)2[7-9]$")) {
					JOptionPane.showMessageDialog(frame, "La fecha de caducidad debe tener un formato adecuado (EJ: ENE27, año valido solo entre el 2027 y 2029)");
				}else {
					JOptionPane.showMessageDialog(frame, "Medicamento añadido correctamente");
					valido=true;
				try {
					con = DriverManager.getConnection(url, user, password);
					PreparedStatement dele_pstmt = con
							.prepareStatement("DELETE FROM medicamentos WHERE idMedicamento = ?");
					dele_pstmt.setInt(1, Integer.parseInt(textField_ID.getText()));
					int rowsDeleted = dele_pstmt.executeUpdate();
					dele_pstmt.close();
					modelMedicamentos.setRowCount(0);
					cargarTablaMedicamentos(modelMedicamentos);
					modelMedicamentosSinStock.setRowCount(0);
					cargarTablaMedicamentosSinStock(modelMedicamentosSinStock);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				}while(valido=false);
			}
		});
		
		btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con;
				boolean valido=false;
				do {
				if(textField_Nombre.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "El nombre debe estar relleno");
				}else if(comboBox_Formato.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(frame, "El formato debe estar relleno");
				}else if(textField_FechaCaducidad.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "La fecha de caducidad debe estar relleno");
				}else if(!textField_FechaCaducidad.getText().matches("^(ENE|FEB|MAR|ABR|MAY|JUN|JUL|AGO|SEP|OCT|NOV|DIC)2[7-9]$")) {
					JOptionPane.showMessageDialog(frame, "La fecha de caducidad debe tener un formato adecuado (EJ: ENE27, año valido solo entre el 2027 y 2029)");
				}else {
					JOptionPane.showMessageDialog(frame, "Medicamento añadido correctamente");
					valido=true;
				try {
					con = DriverManager.getConnection(url, user, password);
					PreparedStatement upd_pstmt = con.prepareStatement(
							"UPDATE medicamentos SET nombre = ? , formato = ? , fechaCaducidad = ? , stock = ? WHERE idMedicamento = ?");
					upd_pstmt.setString(1, textField_Nombre.getText());
					upd_pstmt.setInt(2, comboBox_Formato.getSelectedIndex());
					upd_pstmt.setString(3, textField_FechaCaducidad.getText());
					upd_pstmt.setBoolean(4, chckbxStock.isSelected());
					upd_pstmt.setInt(5, Integer.parseInt(textField_ID.getText()));
					int rowsUpdated = upd_pstmt.executeUpdate();
					upd_pstmt.close();
					modelMedicamentos.setRowCount(0);
					cargarTablaMedicamentos(modelMedicamentos);
					modelMedicamentosSinStock.setRowCount(0);
					cargarTablaMedicamentosSinStock(modelMedicamentosSinStock);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}while(valido=false);
			}
		});
		btnActualizar.setBounds(217, 495, 105, 27);
		frame.getContentPane().add(btnActualizar);
		btnBorrar.setBounds(349, 495, 105, 27);
		frame.getContentPane().add(btnBorrar);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(52, 308, 26, 17);
		frame.getContentPane().add(lblId);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(18, 341, 60, 17);
		frame.getContentPane().add(lblNombre);
		
		JLabel lblFormato = new JLabel("Formato:");
		lblFormato.setBounds(18, 377, 60, 17);
		frame.getContentPane().add(lblFormato);
		
		JLabel lblCaducidad = new JLabel("Caducidad:");
		lblCaducidad.setBounds(12, 413, 75, 17);
		frame.getContentPane().add(lblCaducidad);
		
		
		
		tableMedicamentos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				
				int indexMedicamento= tableMedicamentos.getSelectedRow();
				TableModel modelMedicamento = tableMedicamentos.getModel();
				// ID NAME AGE CITY
				textField_ID.setText(modelMedicamento.getValueAt(indexMedicamento, 0).toString());
				textField_Nombre.setText(modelMedicamento.getValueAt(indexMedicamento, 1).toString());
				String valor = modelMedicamento.getValueAt(indexMedicamento, 2).toString();
				switch (valor) {
				    case "1":
				        comboBox_Formato.setSelectedItem("1) pastillas");
				        break;
				    case "2":
				        comboBox_Formato.setSelectedItem("2) jarabe");
				        break;
				    case "3":
				        comboBox_Formato.setSelectedItem("3) pomada");
				        break;
				}
				textField_FechaCaducidad.setText(modelMedicamento.getValueAt(indexMedicamento, 3).toString());
				chckbxStock.setSelected((boolean) modelMedicamento.getValueAt(indexMedicamento, 4));
				
				
				
				
			}
		});
	}

}
