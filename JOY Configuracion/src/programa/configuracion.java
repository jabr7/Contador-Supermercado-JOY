package programa;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.MaskFormatter;
import java.awt.Toolkit;

public class configuracion {

	private JFrame frame;
	private JTextField textField_cantPromo;
	private JTextField textField_colorLetra;
	private JTextField textField_contador;
	private JTextField textField_tamañoLetra;
	private JTextField textField_teclaAscenso;
	private JTextField textField_teclaDescenso;
	private JTextField textField_teclaReset;
	private JTextField textField_textoCartel;
	private JTextField textField_tiempoNum;
	private JTextField textField_tiempoProm;
	private JTextField textField_tipoLetra;
	private JTextField textField_teclaARapida;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if (!DBConnection.DBPrendida()) {
						JOptionPane.showMessageDialog(null,
								"No se pudo conectar a la base de datos, llamar al administrador");
					} else {
						configuracion window = new configuracion();
						window.frame.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public configuracion() {
		initialize();
	}

	private void initialize() {

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}

		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(configuracion.class.getResource("/archivos/Logo.png")));
		frame.setBounds(100, 100, 486, 673);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		JTextField textField_colorFondo = new JFormattedTextField();
		textField_colorFondo.setEditable(false);
		textField_colorFondo.setText(
				DBConnection.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Color de Fondo';"));
		textField_colorFondo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {

				if (textField_colorFondo.getText().length() > 7) {
					e.consume();
				}
			}
		});
		textField_colorFondo.setBounds(262, 60, 112, 30);
		frame.getContentPane().add(textField_colorFondo);

		JButton btnNewButton = new JButton("Elegir");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Llamo al objeto para elegir el color y lo muestra por la ventana

				Color color = JColorChooser.showDialog(null, "Elija un color", Color.black);

				// Traduzco el valor que vuelve del color picker de RGB a Hexadecimal y lo cargo
				// en el JTextField

				try {
					String red = Integer.toHexString(color.getRed());
					String green = Integer.toHexString(color.getGreen());
					String blue = Integer.toHexString(color.getBlue());

					if (red.length() == 1)
						red = "0" + red;
					if (green.length() == 1)
						green = "0" + green;
					if (blue.length() == 1)
						blue = "0" + blue;

					String hexColor = "#" + red + green + blue;

					textField_colorFondo.setText(hexColor);
				} catch (java.lang.NullPointerException t) {

				}
			}
		});
		btnNewButton.setBounds(384, 64, 85, 21);
		frame.getContentPane().add(btnNewButton);

		JLabel lblNewLabel = new JLabel("Cantidad de Promociones");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(62, 10, 181, 36);
		frame.getContentPane().add(lblNewLabel);

		textField_cantPromo = new JTextField();
		textField_cantPromo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		textField_cantPromo.setTransferHandler(null);

		textField_cantPromo.setBounds(265, 10, 109, 30);
		frame.getContentPane().add(textField_cantPromo);
		textField_cantPromo.setColumns(10);
		textField_cantPromo.setText(DBConnection
				.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Cantidad de Promociones';"));

		JLabel lblNewLabel_1 = new JLabel("Color de Fondo");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(62, 56, 181, 36);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Color de la Letra");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(62, 106, 181, 36);
		frame.getContentPane().add(lblNewLabel_2);

		textField_colorLetra = new JTextField();
		textField_colorLetra.setEditable(false);
		textField_colorLetra.setColumns(10);
		textField_colorLetra.setBounds(265, 106, 109, 30);
		frame.getContentPane().add(textField_colorLetra);
		textField_colorLetra.setText(DBConnection
				.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Color de la Letra';"));

		JLabel lblNewLabel_2_1 = new JLabel("Contador");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2_1.setBounds(62, 152, 181, 36);
		frame.getContentPane().add(lblNewLabel_2_1);

		textField_contador = new JTextField();
		textField_contador.setColumns(10);
		textField_contador.setBounds(265, 152, 109, 30);
		frame.getContentPane().add(textField_contador);
		textField_contador.setText(
				DBConnection.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Contador';"));

		textField_contador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		textField_contador.setTransferHandler(null);

		JLabel lblNewLabel_2_2 = new JLabel("Tama\u00F1o de Letra");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2_2.setBounds(62, 198, 181, 36);
		frame.getContentPane().add(lblNewLabel_2_2);

		textField_tamañoLetra = new JTextField();
		textField_tamañoLetra.setColumns(10);
		textField_tamañoLetra.setBounds(265, 198, 109, 30);
		frame.getContentPane().add(textField_tamañoLetra);
		textField_tamañoLetra.setText(
				DBConnection.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Tamaño de Letra';"));

		textField_tamañoLetra.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		textField_tamañoLetra.setTransferHandler(null);

		JLabel lblNewLabel_2_3 = new JLabel("Tecla de Ascenso");
		lblNewLabel_2_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2_3.setBounds(62, 244, 181, 36);
		frame.getContentPane().add(lblNewLabel_2_3);

		textField_teclaAscenso = new JTextField();
		textField_teclaAscenso.setColumns(10);
		textField_teclaAscenso.setBounds(265, 244, 109, 30);
		frame.getContentPane().add(textField_teclaAscenso);
		textField_teclaAscenso.setText(DBConnection
				.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Tecla de Ascenso';"));

		JLabel lblNewLabel_2_3_1 = new JLabel("Tecla de Descenso");
		lblNewLabel_2_3_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2_3_1.setBounds(62, 290, 181, 36);
		frame.getContentPane().add(lblNewLabel_2_3_1);

		textField_teclaDescenso = new JTextField();
		textField_teclaDescenso.setColumns(10);
		textField_teclaDescenso.setBounds(265, 290, 109, 30);
		frame.getContentPane().add(textField_teclaDescenso);
		textField_teclaDescenso.setText(DBConnection
				.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Tecla de Descenso';"));

		JLabel lblNewLabel_2_3_2 = new JLabel("Tecla de Reset");
		lblNewLabel_2_3_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2_3_2.setBounds(62, 330, 181, 36);
		frame.getContentPane().add(lblNewLabel_2_3_2);

		textField_teclaReset = new JTextField();
		textField_teclaReset.setColumns(10);
		textField_teclaReset.setBounds(265, 330, 109, 30);
		frame.getContentPane().add(textField_teclaReset);
		textField_teclaReset.setText(
				DBConnection.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Tecla de Reset';"));

		JLabel lblNewLabel_2_3_3 = new JLabel("Texto del Cartel");
		lblNewLabel_2_3_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2_3_3.setBounds(62, 376, 181, 36);
		frame.getContentPane().add(lblNewLabel_2_3_3);

		textField_textoCartel = new JTextField();
		textField_textoCartel.setColumns(10);
		textField_textoCartel.setBounds(265, 376, 109, 30);
		frame.getContentPane().add(textField_textoCartel);
		textField_textoCartel.setText(DBConnection
				.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Texto del Cartel';"));

		JLabel lblNewLabel_2_3_4 = new JLabel("Tiempo de Numero");
		lblNewLabel_2_3_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2_3_4.setBounds(62, 422, 181, 36);
		frame.getContentPane().add(lblNewLabel_2_3_4);

		textField_tiempoNum = new JTextField();
		textField_tiempoNum.setColumns(10);
		textField_tiempoNum.setBounds(265, 422, 109, 30);
		frame.getContentPane().add(textField_tiempoNum);
		textField_tiempoNum.setText(DBConnection
				.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Tiempo de Numero';"));

		textField_tiempoNum.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		textField_tiempoNum.setTransferHandler(null);

		JLabel lblNewLabel_2_3_5 = new JLabel("Tiempo de Promocion");
		lblNewLabel_2_3_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2_3_5.setBounds(62, 468, 181, 36);
		frame.getContentPane().add(lblNewLabel_2_3_5);

		textField_tiempoProm = new JTextField();
		textField_tiempoProm.setColumns(10);
		textField_tiempoProm.setBounds(265, 468, 109, 30);
		frame.getContentPane().add(textField_tiempoProm);
		textField_tiempoProm.setText(DBConnection
				.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Tiempo de Promocion';"));

		textField_tiempoProm.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		textField_tiempoProm.setTransferHandler(null);

		JLabel lblNewLabel_2_3_6 = new JLabel("Tipo de Letra");
		lblNewLabel_2_3_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2_3_6.setBounds(62, 514, 181, 36);
		frame.getContentPane().add(lblNewLabel_2_3_6);

		textField_tipoLetra = new JTextField();
		textField_tipoLetra.setEditable(false);
		textField_tipoLetra.setColumns(10);
		textField_tipoLetra.setBounds(265, 514, 109, 30);
		frame.getContentPane().add(textField_tipoLetra);
		textField_tipoLetra.setText(
				DBConnection.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Tipo de Letra';"));

		final JFileChooser fc = new JFileChooser();
		JButton btnNewButton_3 = new JButton("Explorar");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = null;
				try {
					fc.showOpenDialog(frame);
					file = fc.getSelectedFile();
					if (String.valueOf(file) != "null") {
						textField_tipoLetra.setText(String.valueOf(file));
					}

				} catch (java.lang.NullPointerException j) {
					System.out.println("El file devuelto por el explorer esta vacio");
				}

			}
		});
		btnNewButton_3.setBounds(384, 520, 85, 21);
		frame.getContentPane().add(btnNewButton_3);

		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter("?HHHHHH");
			formatter.setPlaceholderCharacter('_');

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		textField_teclaARapida = new JTextField();
		textField_teclaARapida.setText(DBConnection
				.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Tecla Subida Rapida';"));
		textField_teclaARapida.setColumns(10);
		textField_teclaARapida.setBounds(265, 560, 109, 30);
		frame.getContentPane().add(textField_teclaARapida);

		JButton btnNewButton_4 = new JButton("Guardar");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Creo un array de booleans, cada atributo tiene una posicion determinada de 0 a 12 en este array
				//cuando uno de los atributos falla en cargar levanta a true su respectivo lugar
				//al terminar la ejecucion nos fijamos si algun atributo levanto a true, si lo hizo no se hace nada
				//pero si ningun atributo levanto su bandera mostramos el mensaje de que se pudo guardar correctamente
				boolean guardado[] = new boolean[13];
				if (textField_cantPromo.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Por favor rellene el campo cantidad de promociones");
					guardado[0] = true;
				} else {
					DBConnection.DBInsertar("UPDATE `joy`.`settings` SET `settingscol` = '"
							+ textField_cantPromo.getText() + "' WHERE (`idsettings` = 'Cantidad de Promociones');");

				}
				if (textField_colorFondo.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Por favor rellene el campo color de fondo");
					guardado[1] = true;
				} else if (textField_colorFondo.getText().length() != 7) {
					JOptionPane.showMessageDialog(null,
							"Por favor rellene el campo color de fondo con un valor hexadecimal valido");
					guardado[1] = true;
				} else {
					DBConnection.DBInsertar("UPDATE `joy`.`settings` SET `settingscol` = '"
							+ textField_colorFondo.getText() + "' WHERE (`idsettings` = 'Color de Fondo');");
				}
				if (textField_colorLetra.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Por favor rellene el campo color de la letra");
					guardado[2] = true;
				} else {

					DBConnection.DBInsertar("UPDATE `joy`.`settings` SET `settingscol` = '"
							+ textField_colorLetra.getText() + "' WHERE (`idsettings` = 'Color de la Letra');");
				}

				if (textField_contador.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Por favor rellene el campo contador");
					guardado[3] = true;
				} else {

					DBConnection.DBInsertar("UPDATE `joy`.`settings` SET `settingscol` = '"
							+ textField_contador.getText() + "' WHERE (`idsettings` = 'Contador');");
				}

				if (textField_tamañoLetra.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Por favor rellene el campo tamaño de letra");
					guardado[4] = true;
				} else {
					DBConnection.DBInsertar("UPDATE `joy`.`settings` SET `settingscol` = '"
							+ textField_tamañoLetra.getText() + "' WHERE (`idsettings` = 'Tamaño de Letra');");
				}
				if (textField_teclaAscenso.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Por favor rellene el campo tecla de ascenso");
					guardado[5] = true;
				} else {

					DBConnection.DBInsertar("UPDATE `joy`.`settings` SET `settingscol` = '"
							+ textField_teclaAscenso.getText().replaceAll("\\\\", "\\\\\\\\")
							+ "' WHERE (`idsettings` = 'Tecla de Ascenso');");
				}

				if (textField_teclaDescenso.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Por favor rellene el campo tecla de descenso");
					guardado[6] = true;
				} else {

					DBConnection.DBInsertar("UPDATE `joy`.`settings` SET `settingscol` = '"
							+ textField_teclaDescenso.getText() + "' WHERE (`idsettings` = 'Tecla de Descenso');");
				}

				if (textField_teclaReset.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Por favor rellene el campo tecla de reset");
					guardado[7] = true;
				} else {
					DBConnection.DBInsertar("UPDATE `joy`.`settings` SET `settingscol` = '"
							+ textField_teclaReset.getText() + "' WHERE (`idsettings` = 'Tecla de Reset');");
				}

				if (textField_textoCartel.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Por favor rellene el campo texto del cartel");
					guardado[8] = true;
				} else {
					DBConnection.DBInsertar("UPDATE `joy`.`settings` SET `settingscol` = '"
							+ textField_textoCartel.getText() + "' WHERE (`idsettings` = 'Texto del Cartel');");
				}

				if (textField_tiempoNum.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Por favor rellene el campo tiempo de numero");
					guardado[9] = true;
				} else {
					DBConnection.DBInsertar("UPDATE `joy`.`settings` SET `settingscol` = '"
							+ textField_tiempoNum.getText() + "' WHERE (`idsettings` = 'Tiempo de Numero');");
				}

				if (textField_tiempoProm.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Por favor rellene el campo tiempo de promoción");
					guardado[10] = true;
				} else {
					DBConnection.DBInsertar("UPDATE `joy`.`settings` SET `settingscol` = '"
							+ textField_tiempoProm.getText() + "' WHERE (`idsettings` = 'Tiempo de Promocion');");
				}

				if (textField_tipoLetra.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Por favor rellene el campo tipo de letra");
					guardado[11] = true;
				} else {

					DBConnection.DBInsertar("UPDATE `joy`.`settings` SET `settingscol` = '"
							+ textField_tipoLetra.getText().replaceAll("\\\\", "\\\\\\\\")
							+ "' WHERE (`idsettings` = 'Tipo de Letra');");
				}

				if (textField_teclaARapida.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Por favor rellene el campo tecla de ascenso rapida ");
					guardado[12] = true;
				} else {
					DBConnection.DBInsertar("UPDATE `joy`.`settings` SET `settingscol` = '"
							+ textField_teclaARapida.getText() + "' WHERE (`idsettings` = 'Tecla Subida Rapida');");
				}
				boolean correcto = true;
				for (int i = 0; i < guardado.length; i++) {

					if (guardado[i] == true) {
						correcto = false;
					}
				}

				if (correcto) {
					JOptionPane.showMessageDialog(null, "Se guardo correctamente");

				}

			}
		});
		btnNewButton_4.setBounds(179, 606, 85, 21);
		frame.getContentPane().add(btnNewButton_4);

		JLabel lblNewLabel_2_3_2_1 = new JLabel("Tecla de Ascenso Rapida");
		lblNewLabel_2_3_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2_3_2_1.setBounds(62, 560, 181, 36);
		frame.getContentPane().add(lblNewLabel_2_3_2_1);

		JButton btnNewButton_1 = new JButton("Elegir");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Llamo al objeto para elegir el color y lo muestra por la ventana

				Color color = JColorChooser.showDialog(null, "Elija un color", Color.black);

				// Traduzco el valor que vuelve del color picker de RGB a Hexadecimal y lo cargo
				// en el JTextField

				try {
					String red = Integer.toHexString(color.getRed());
					String green = Integer.toHexString(color.getGreen());
					String blue = Integer.toHexString(color.getBlue());

					if (red.length() == 1)
						red = "0" + red;
					if (green.length() == 1)
						green = "0" + green;
					if (blue.length() == 1)
						blue = "0" + blue;

					String hexColor = "#" + red + green + blue;

					textField_colorLetra.setText(hexColor);
				} catch (java.lang.NullPointerException t) {

				}

			}
		});
		btnNewButton_1.setBounds(384, 110, 85, 21);
		frame.getContentPane().add(btnNewButton_1);

	}
}
