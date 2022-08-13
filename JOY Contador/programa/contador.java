package programa;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

class Variables {

	// Numero principal que se muestra
	static int contador;

	static int numero_contador;
}

public class contador {

	private JFrame frame;
	private JTextField textField;

	// Debo de hacer el label del contador global para poder modificarlo en el
	// keypress
	JLabel Label_Contador_Chico = new JLabel();

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
						contador window = new contador();
						window.frame.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public contador() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// Reinicio la variable de numero de foto
		Variables.numero_contador = 1;

		JLabel Label_Imagenes = new JLabel("");
		Label_Imagenes.setBounds(37, 31, 1470, 794);

		JPanel panel2 = new JPanel();
		panel2.setVisible(false);
		class ChangeImage extends TimerTask {
			public void run() {

				// Solo dejo cambiar la imagen si no se esta mostrando el numero, sino pierdo el
				// focus
				if (panel2.isVisible() == false) {
					// Para cargar la promocion debo de hacer un resize de la imagen original
					BufferedImage img = null;
					try {
						img = ImageIO.read(new File("archivos\\" + Variables.numero_contador + ".png"));
						// Creo la nueva imagen a partir del resize del path del logo

						Image dimg = img.getScaledInstance(Label_Imagenes.getWidth(), Label_Imagenes.getHeight(),
								Image.SCALE_SMOOTH);
						// Creo un ImageIcon a partir de la imagen con el nuevo tamaño
						ImageIcon imageIcon = new ImageIcon(dimg);

						// Agrego la imagen nueva al label_Imagenes
						Label_Imagenes.setIcon(imageIcon);
						Variables.numero_contador++;
						panel2.setVisible(false);
						if (Variables.numero_contador == Integer.valueOf(DBConnection.DBSeleccionar(
								"SELECT settingscol FROM joy.settings WHERE idsettings='Cantidad de Promociones';"))
								+ 1) {
							Variables.numero_contador = 1;

						}
					} catch (IOException | java.lang.NullPointerException e) {
						JOptionPane.showMessageDialog(null,
								"Se ha ingresado un numero mayor en la cantidad de promociones que fotos de promociones");
						frame.dispose();
					}

				}

			}
		}

		Timer timer_principal = new Timer();
		timer_principal.schedule(new ChangeImage(), 0, (1000 * Integer.valueOf(DBConnection
				.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Tiempo de Promocion';"))));

		// create the font
		Font customFont = null;
		try {
			// Creo la Font y le paso el path del tipo de letra y luego le paso el tamaño
			// sacado de la bdd
			customFont = Font
					.createFont(Font.TRUETYPE_FONT,
							new File(DBConnection.DBSeleccionar(
									"SELECT settingscol FROM joy.settings WHERE idsettings='Tipo de Letra';")))
					.deriveFont(Float.parseFloat(DBConnection.DBSeleccionar(
							"SELECT settingscol FROM joy.settings WHERE idsettings='Tamaño de Letra';")));

		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}

		// Creo el color que sera aplicado a los labels, el mismo es traido como valor
		// hexadecimal desdela bdd
		Color myColor = Color.decode(DBConnection
				.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Color de la Letra';"));

		// Cargo la variable principal con lo que devuelva la bdd
		Variables.contador = Integer.valueOf(
				DBConnection.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Contador';"));
		Label_Contador_Chico.setHorizontalAlignment(SwingConstants.CENTER);

		Label_Contador_Chico.setForeground(myColor);
		Label_Contador_Chico.setText(Integer.toString(Variables.contador));
		Label_Contador_Chico.setBounds(1022, 0, 504, 390);
		Font biggerFont = customFont.deriveFont(300f);
		Label_Contador_Chico.setFont(biggerFont);

		// Crea el frame y lo maximisa a la resolucion de la pantalla
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.getContentPane().setLayout(null);

		// Le da el icono al programa
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(contador.class.getResource("/archivos/Logo.png")));

		// Se encarga de aplicar el theme al programa
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

		panel2.setBackground(Color.decode(
				DBConnection.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Color de Fondo';")));
		panel2.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.getContentPane().add(panel2);
		panel2.setVisible(false);
		panel2.setLayout(null);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setText(DBConnection
				.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Texto del Cartel';"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.decode(
				DBConnection.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Color de la Letra';")));
		Font textFont_grande = customFont.deriveFont(130f);
		lblNewLabel.setFont(textFont_grande);
		lblNewLabel.setBounds(336, 49, 914, 249);
		panel2.add(lblNewLabel);

		JLabel Label_NGrande = new JLabel("0");
		Label_NGrande.setHorizontalAlignment(SwingConstants.CENTER);
		Label_NGrande.setFont(customFont);
		Label_NGrande.setForeground(myColor);

		Label_NGrande.setBounds(10, 0, 1516, 867);
		panel2.add(Label_NGrande);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(0, 0, 1536, 864);
		panel2.add(lblNewLabel_1);

		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.decode(
				DBConnection.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Color de Fondo';")));
		panel1.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.getContentPane().add(panel1);
		panel1.setLayout(null);

		JLabel Label_Texto = new JLabel("");
		Label_Texto.setText(DBConnection
				.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Texto del Cartel';"));
		Label_Texto.setHorizontalAlignment(SwingConstants.CENTER);
		Label_Texto.setForeground(Color.decode(
				DBConnection.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Color de la Letra';")));
		Font textFont_chico = customFont.deriveFont(customFont.BOLD, 70f);
		Label_Texto.setFont(textFont_chico);
		Label_Texto.setBounds(1034, 400, 513, 76);
		panel1.add(Label_Texto);

		panel1.add(Label_Contador_Chico);

		JLabel label_logo = new JLabel("");
		label_logo.setBounds(1022, 442, 504, 411);

		// Para cargar el logo debo de hacer un resize de la imagen original
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("archivos\\Logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Creo la nueva imagen a partir del resize del path del logo
		Image dimg = img.getScaledInstance(label_logo.getWidth(), label_logo.getHeight(), Image.SCALE_SMOOTH);

		// Creo un ImageIcon a partir de la imagen con el nuevo tamaño
		ImageIcon imageIcon = new ImageIcon(dimg);

		// Agrego la imagen nueva al label_logo
		label_logo.setIcon(imageIcon);

		panel1.add(label_logo);

		panel1.add(Label_Imagenes);

		textField = new JTextField();
		textField.setBounds(-39, 39, 25, 19);
		panel1.add(textField);
		textField.setColumns(10);

		// Decidi no meter esto en un metodo por que si lo pongo en un metodo
		// no se como acceder al panel2 para poder mostrarlo al apretar una tecla

		KeyListener eventoTeclado = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

				// Se activa cuando la persona apreta la Tecla de Ascenso pero solo cuando la
				// pantalla
				// emergente ya no sea visible
				// Asi se termina de mostrar el numero antes de que suba o baje de vuelta
				// Hago lo mismo con todos los inputs

				String letra = DBConnection
						.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Tecla de Ascenso';");
				letra = letra.trim();
				
				char entrada = 0;
				if (letra.length() > 1) {
					if (letra.charAt(1) == 'n') {
						entrada = '\n';
					}
				} else {
					entrada = letra.charAt(0);
				}

				if (e.getKeyChar() == entrada && panel2.isVisible() == false) {

					// Aumento el contador en 1
					Variables.contador++;
					if (Variables.contador == 100) {
						Variables.contador = 0;
					}

					// Muestro la pantalla emergente
					panel2.setVisible(true);
					// Modificado la label de la pantalla emergente para que muestre el nuevo numero
					Label_NGrande.setText(Integer.toString(Variables.contador));

					// Modifico la label del contador
					Label_Contador_Chico.setText(Integer.toString(Variables.contador));

					// Guarda el numero del contador correspondiente en la bdd
					DBConnection.DBInsertar("UPDATE `joy`.`settings` SET `settingscol` = '"
							+ Integer.toString(Variables.contador) + "' WHERE (`idsettings` = 'Contador');");

					// Espero un tiempo y lanzo el sonido
					new Timer().schedule(new TimerTask() {
						public void run() {
							// Llamo al sonido al recibir una keypress
							String audioFilePath = "archivos\\Sonido.wav";
							AudioPlayerExample1 player = new AudioPlayerExample1();
							player.play(audioFilePath);

						}
					}, 5);

					// Espero un tiempo y escondo la pantalla emergente
					new Timer().schedule(new TimerTask() {
						public void run() {
							panel2.setVisible(false);
						}
					}, 1000 * Integer.valueOf(DBConnection.DBSeleccionar(
							"SELECT settingscol FROM joy.settings WHERE idsettings='Tiempo de Numero';")));

				}

				// Configuracion para la Tecla de subida rapida
				letra = DBConnection
						.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Tecla Subida Rapida';");

				entrada = 0;
				if (letra.length() > 1) {
					if (letra.charAt(1) == 'n') {
						entrada = '\n';
					}
				} else {
					entrada = letra.charAt(0);
				}

				if (e.getKeyChar() == entrada && panel2.isVisible() == false) {

					// Aumento el contador en 1
					Variables.contador++;
					if (Variables.contador == 100) {
						Variables.contador = 0;
					}

					// Modifico la label del contador
					Label_Contador_Chico.setText(Integer.toString(Variables.contador));

				}

				// Configuracion para la Tecla de Descenso
				letra = DBConnection
						.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Tecla de Descenso';");

				entrada = 0;
				if (letra.length() > 1) {
					if (letra.charAt(1) == 'n') {
						entrada = '\n';
					}
				} else {
					entrada = letra.charAt(0);
				}

				if (e.getKeyChar() == entrada && panel2.isVisible() == false) {

					// Bajo el contador en 1
					Variables.contador--;

					if (Variables.contador == -1) {
						Variables.contador = 99;
					}

					// Modifico la label del contador
					Label_Contador_Chico.setText(Integer.toString(Variables.contador));// Guarda el numero del contador
																						// correspondiente en la bdd
					DBConnection.DBInsertar("UPDATE `joy`.`settings` SET `settingscol` = '" + Variables.contador
							+ "' WHERE (`idsettings` = 'Contador');");

				}
				
				//Esto solia ser la funcion de reset pero la quite ya que no lo vi necesaria en su uso
/*
				if (e.getKeyChar() == DBConnection
						.DBSeleccionar("SELECT settingscol FROM joy.settings WHERE idsettings='Tecla de Reset';")
						.charAt(0) && panel2.isVisible() == false) {

					// Confirmo que la persona desea resetear el contador y luego lo hago
					int returnValue = 0;

					String[] options = { "SI", "NO" };

					returnValue = JOptionPane.showOptionDialog(null, "Esta seguro que desea resetear?",
							"Eliga un boton", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
							options, options[1]);

					if (returnValue == JOptionPane.YES_OPTION) {
						Variables.contador = 0;

						// Modifico la label del contador
						Label_Contador_Chico.setText(Integer.toString(Variables.contador));

						// Guarda el numero del contador correspondiente en la bdd
						DBConnection.DBInsertar("UPDATE `joy`.`settings` SET `settingscol` = '"
								+ Integer.toString(Variables.contador) + "' WHERE (`idsettings` = 'Contador');");
					}

				}
*/
				if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
					frame.dispose();
				}

				// Reseteo el textfield escondido
				textField.setText("");

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		};
		textField.addKeyListener(eventoTeclado);

		frame.setVisible(true);

	}
}
