package app;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Categoria;
import model.Producto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class FrmManteProd extends JFrame {

	private JPanel contentPane;
	private JTextField txtCódigo;
	JComboBox<String> cboCategorias;
	private JTextField txtDescripcion;
	private JTextField txtStock;
	private JTextField txtPrecio;
	private JTable txtSalida;
	DefaultTableModel modelo= new DefaultTableModel() {
		private static final long serialVersionUID = 1L;

		/**
		 * Launch the application.
		 */
		public boolean isCellEditable(int row, int column) {
			return false;
	}
	};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmManteProd frame = new FrmManteProd();
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
	public FrmManteProd() {
		setTitle("Mantenimiento de Productos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Registrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrar();
				txtCódigo.setText("");
				txtDescripcion.setText("");
				txtPrecio.setText("");
				txtStock.setText("");
				cboCategorias.setSelectedIndex(0);
				txtCódigo.requestFocus();
				listado();
			}
		});
		btnNewButton.setBounds(324, 29, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnListado = new JButton("Listado");
		btnListado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listado();
			}
		});
		btnListado.setBounds(177, 322, 89, 23);
		contentPane.add(btnListado);
		
		txtCódigo = new JTextField();
		txtCódigo.setBounds(122, 11, 86, 20);
		contentPane.add(txtCódigo);
		txtCódigo.setColumns(10);
		
		JLabel lblCodigo = new JLabel("Id. Producto :");
		lblCodigo.setBounds(10, 14, 102, 14);
		contentPane.add(lblCodigo);
		
		cboCategorias = new JComboBox<String>();
		cboCategorias.setBounds(122, 70, 117, 22);
		contentPane.add(cboCategorias);
		
		JLabel lblCategora = new JLabel("Categor\u00EDa");
		lblCategora.setBounds(10, 74, 102, 14);
		contentPane.add(lblCategora);
		
		JLabel lblNomProducto = new JLabel("Nom. Producto :");
		lblNomProducto.setBounds(10, 45, 102, 14);
		contentPane.add(lblNomProducto);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(122, 42, 144, 20);
		contentPane.add(txtDescripcion);
		
		JLabel lblStock = new JLabel("Stock:");
		lblStock.setBounds(10, 106, 102, 14);
		contentPane.add(lblStock);
		
		txtStock = new JTextField();
		txtStock.setColumns(10);
		txtStock.setBounds(122, 103, 77, 20);
		contentPane.add(txtStock);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(10, 134, 102, 14);
		contentPane.add(lblPrecio);
		
		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(122, 131, 77, 20);
		contentPane.add(txtPrecio);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 174, 416, 137);
		contentPane.add(scrollPane);
		
		txtSalida = new JTable();
		scrollPane.setViewportView(txtSalida);
		txtSalida.setModel(modelo);
		llenaCombo();
		modelo.addColumn("Codigo");
		modelo.addColumn("Nombre");
		modelo.addColumn("Categoria");
		modelo.addColumn("Stock");
		modelo.addColumn("Precio");
		scrollPane.setViewportView(txtSalida);
		txtSalida.getColumnModel().getColumn(0).setWidth(80);
		txtSalida.getColumnModel().getColumn(0).setPreferredWidth(60);
		txtSalida.getColumnModel().getColumn(1).setPreferredWidth(110);
		txtSalida.getColumnModel().getColumn(2).setPreferredWidth(60);
		txtSalida.getColumnModel().getColumn(3).setPreferredWidth(80);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		txtSalida.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		txtSalida.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		txtSalida.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		txtSalida.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
		txtSalida.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
	}
	EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
	// 2 . Obtener el DAO
	EntityManager em=fabrica.createEntityManager();

	void llenaCombo() {
		TypedQuery<Categoria>query = em.createQuery("Select c from Categoria c",Categoria.class);
		List<Categoria> lstCat = query.getResultList();
		System.out.println("Cantidad de categorias " +lstCat);
		if(lstCat == null) {
			JOptionPane.showMessageDialog(this,"Combo no tiene datos");
		}else {
			cboCategorias.addItem("Seleccione");
			for (Categoria c : lstCat) {
				cboCategorias.addItem(c.getIdcategoria()+".-"+c.getDescripcion());
			}
			}
	}
	
	void listado() {
		TypedQuery<Producto>query2=em.createQuery("Select p from Producto p",Producto.class);
		List<Producto> lstProd = query2.getResultList();
		for(Producto p : lstProd) {
			Object fila[]= {p.getCodigo(),p.getDescrip(),p.getIdcat(),p.getStock(),p.getPrecio()};
		modelo.addRow(fila);
		}
	}
	
	void registrar() {
		String codigo = leerCodigo();
		String nombre = leerNombre();
		int categoria=leerCategoria();
		int stock = leerStock();
		double precio = leerPrecio();
		
		Producto p = new Producto();
		p.setCodigo(codigo);
		p.setDescrip(nombre);
		p.setIdcat(categoria);
		p.setStock(stock);
		p.setPrecio(precio);
		try {
			em.getTransaction().begin();
			em.persist(p);
			em.getTransaction().commit();
			System.out.println("Registro de nuevo Producto : " + p.getDescrip());
		} catch (Exception e) {
			System.out.println("Error : " +e.getClass().getName());
		}	
	}
	String leerCodigo() {
		String codigo=null;
		codigo=txtCódigo.getText();
		if(codigo==null) {
			JOptionPane.showMessageDialog(this,"Rellene el codigo");
			txtCódigo.setText("");
			txtCódigo.requestFocus();
		}
		return codigo;
	}
	String leerNombre() {
		String nombre=null;
		nombre=txtDescripcion.getText();
		if(nombre==null) {
			JOptionPane.showMessageDialog(this,"Rellene el nombre");
			txtDescripcion.setText("");
			txtDescripcion.requestFocus();
		}
		return nombre;
	}
	int leerCategoria() {
		int categoria=-1;
		categoria=cboCategorias.getSelectedIndex();
		if(categoria==0) {
			JOptionPane.showMessageDialog(this,"Escoja la categoria");
			cboCategorias.setSelectedIndex(0);
			cboCategorias.requestFocus();
		}
		return categoria;
	}
	int leerStock() {
		int stock=-1;
		stock=Integer.parseInt(txtStock.getText());
		if(stock==-1) {
			JOptionPane.showMessageDialog(this,"Rellene el Stock del Producto");
			txtStock.setText("");
			txtStock.requestFocus();
		}
		return stock;
	}
	double leerPrecio() {
		Double precio=0.0;
		precio=Double.parseDouble(txtPrecio.getText());
		if(precio==0.0) {
			JOptionPane.showMessageDialog(this,"Rellene el Precio del Producto");
			txtPrecio.setText("");
			txtPrecio.requestFocus();
		}
		return precio;
	}
}
