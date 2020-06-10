package cn.dfrz.gyl.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import cn.dfrz.gyl.model.Book;
import cn.dfrz.gyl.service.BookService;
import cn.dfrz.gyl.serviceimpl.FactoryService;
import cn.dfrz.gyl.utils.JFrameUtils;

/**
 * @Decription 图书信息主界面
 */
public class BookFrame extends JFrame {
	

	private static final long serialVersionUID = 1L;
	private BookService bookService = FactoryService.getBookService();
	private UpdateBookFrame updateBookFrame = null;
	private JPanel jpanel;
	private JLabel lblTitle;
	private JTable jtable;
	private JScrollPane jspa = null;
	private JButton addBook;
	private JButton delBook;
	private JButton updBook;
	private JButton refresh;
	
	//模糊查询组件
	private JButton findBook;
	private JTextField findBookText;
	
	//分页查询组件
	private int pageIndex =1;
	private JButton nextPage;
	private JButton prePage;

	public BookFrame() {
		initUI();
		bindEvent();
	}

	public void initUI() {
		int width = 800;
		int height = 665;
		// 大小
		this.setSize(width, height);
		// 屏幕居中
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
		this.setLocation(x, y);
		// 标题
		this.setTitle("图书信息管理");
		// 可变大小
		this.setResizable(false);
		jpanel = new JPanel();
		jpanel.setVisible(true);
		// 布局
		jpanel.setLayout(null);
		// 添加到Jframe
		this.setContentPane(jpanel);


		lblTitle = new JLabel();
		lblTitle.setText("图书信息");
		lblTitle.setForeground(Color.red);
		lblTitle.setBounds(300, 15, 200, 100);
		lblTitle.setFont(new Font("楷体", Font.BOLD, 40));
		jpanel.add(lblTitle);

		// 刷新界面使用showlistdata
		inittableUi();
		jtable.setFont(new Font("宋体", Font.PLAIN, 18));// 表格字体
		jtable.setRowHeight(20);// 行宽
		
		nextPage = new JButton("下一页");
		nextPage.setFont(new Font("宋体", Font.PLAIN, 18));
		nextPage.setBounds(600, 510, 120, 30);
		jpanel.add(nextPage);

		findBook = new JButton("查询关键字");
		findBook.setFont(new Font("宋体", Font.PLAIN, 18));
		findBook.setBounds(255, 510, 140, 30);
		jpanel.add(findBook);
		
		findBookText = new JTextField();
		findBookText.setBounds(420, 510, 150, 30);
		jpanel.add(findBookText);
		
		prePage = new JButton("上一页");
		prePage.setFont(new Font("宋体", Font.PLAIN, 18));
		prePage.setBounds(90, 510, 120, 30);
		jpanel.add(prePage);


		updBook = new JButton("修改");
		updBook.setFont(new Font("宋体", Font.PLAIN, 18));
		updBook.setBounds(150, 560, 80, 40);
		jpanel.add(updBook);
		

		delBook = new JButton("删除");
		delBook.setBounds(290, 560, 80, 40);
		delBook.setFont(new Font("宋体", Font.PLAIN, 18));
		jpanel.add(delBook);

		addBook = new JButton("添加");
		addBook.setBounds(430, 560, 80, 40);
		addBook.setFont(new Font("宋体", Font.PLAIN, 18));
		jpanel.add(addBook);

		refresh = new JButton("刷新");
		refresh.setBounds(580, 560, 80, 40);
		refresh.setFont(new Font("宋体", Font.PLAIN, 18));
		jpanel.add(refresh);

		jtable.setFont(new Font("宋体", Font.PLAIN, 18));// 表格字体
		jtable.setRowHeight(20);// 行宽
		// 背景图片
		JLabel bglabel = new JLabel();// 创建JLabel
		getBackgroundPicture(bglabel, jpanel);// 方法体见下面代码块
		this.getLayeredPane().add(bglabel, new Integer(Integer.MIN_VALUE));
	}

	public void getBackgroundPicture(JLabel bglabel, JPanel jpanel) {
		ImageIcon background = new ImageIcon(JFrameUtils.getBackground());
		bglabel.setIcon(background);// 将图片设置到Jlabel中
		bglabel.setBounds(0, -50, background.getIconWidth(), background.getIconHeight());// 设图片显示的区域
		jpanel.setOpaque(false);// 设置面板为透明，在gbLabel之上的JPanel都要设置为透明
	}

	// table初始化
	private void inittableUi() {
		jtable = new JTable();
		jspa = new JScrollPane(jtable);
		jspa.setBounds(50, 100, 700, 400);
		jpanel.add(jspa);
		showListData(1);
	}

	// 事件绑定区
	public void bindEvent() {
		addBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addBook_Click();
			}

		});
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				refresh_Click();
			}

		});
		delBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				delBook_Click();
			}
		});
		updBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				updBook_Click();
			}

		});
		findBook.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				findBook_Click();
			}

		
			
		});
		nextPage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				nextPage_Click();
			}

		});
		prePage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				prePage_Click();
			}
		});
	}

	private void prePage_Click() {
		if (pageIndex > 1) {
			pageIndex--;
			showListData(pageIndex);
		}

	}

	private void nextPage_Click() {
		pageIndex++;
		showListData(pageIndex);

	}

	// 添加按钮
	public void addBook_Click() {
		AddBookFrame addBookFrame = new AddBookFrame(this);
		addBookFrame.setVisible(true);
	}

	/**
	 * 
	 * @Decription 模糊查询
	 */
	private void findBook_Click() {
		String dimString = findBookText.getText().trim();
		List<Book> dimBookList = bookService.dimQuery(dimString);
		// list数据传给表格
		DefaultTableModel tableModel = getDefaultTableModel(dimBookList);
		jtable.setModel(tableModel);
	}
	// 批量删除选择的所有行
	private void delBook_Click() {
		int[] index = jtable.getSelectedRows();
		if (index.length < 1) {
			JOptionPane.showMessageDialog(null, "没有选择行");
			return;
		}
		int key = JOptionPane.showConfirmDialog(null, "你确认删除当前选中的" + index.length + "行?", "确认删除",
				JOptionPane.YES_NO_OPTION);// key=0/1
		if (key == 1) {
			return;
		}

		// 当前选中的所有行索引
		TableModel model = jtable.getModel();
		Object[] obj = new Object[index.length];
		for (int i = 0; i < obj.length; i++) {
			obj[i] = model.getValueAt(index[i], 0);
		}
		String[] pk = new String[obj.length];
		for (int i = 0; i < pk.length; i++) {
			pk[i] = obj[i] + "";
		}
		int count = 0;
		for (int i = 0; i < pk.length; i++) {
			int k = bookService.delete(pk[i]);
			if (k >= 1) {
				count++;
			}
		}
		if (count == 1) {
			JOptionPane.showMessageDialog(null, "删除成功");
		} else if (count > 1) {
			JOptionPane.showMessageDialog(null, "删除成功" + count + "个" + "删除失败" + (pk.length - count) + "个");
		} else {
			JOptionPane.showMessageDialog(null, "删除失败!");
			return;
		}
		shows();
		if (jtable.getRowCount() - 1 > 0) {
			this.jtable.addRowSelectionInterval(jtable.getRowCount() - 1, jtable.getRowCount() - 1);
		}
	}

	public void shows() {
		this.setVisible(false);
		new BookFrame().setVisible(true);
	}

	private void refresh_Click() {
		shows();
	}

	private void updBook_Click() {
		Book book = getRowData();
		if (book != null) {
			updateBookFrame = new UpdateBookFrame(this,book);
			updateBookFrame.setVisible(true);
		} else {
			// 如果没有选中任何行，提示用户
			JOptionPane.showMessageDialog(null, "请选中需要修改的图书信息");
			return;
		}
	}

	// 获取当前选择的学生对象
	public Book getRowData() {
		Book book = null;
		int index = jtable.getSelectedRow();
		// 如果下标不为-1，则选中行为数据行
		if (index != -1) {
			// 取得表格对象的数据模型
			TableModel model = jtable.getModel();
			// 在表格对象模型中，根据选中的行和列，获取相应的数据值,(index,0)对应第一列的学号
			String ISBN = model.getValueAt(index, 0) + "";
			int typeId = Integer.parseInt(model.getValueAt(index, 1) + "");
			String bookName = model.getValueAt(index, 2) + "";
			String writer = model.getValueAt(index, 3) + "";
			String translater = model.getValueAt(index, 4) + "";
			Date date =JFrameUtils.checkDate(model.getValueAt(index, 5) + "");
			String publisher = model.getValueAt(index, 6) + "";
			BigDecimal price = new BigDecimal(Double.parseDouble(model.getValueAt(index, 7) + ""));
			book = new Book(ISBN, typeId, bookName, writer, translater,date, publisher, price);
		}
		// 如果没有选中任何一行 就意味着student为null 可以提供给外部判断
		return book;
	}

	public void showListData(int pageIndex) {
		List<Book> list = null;
		list = bookService.queryAllInPage(pageIndex);
		if(list.size()<1) {
			this.pageIndex--;
			return;
		}
		// list数据传给表格
		DefaultTableModel tableModel = getDefaultTableModel(list);
		jtable.setModel(tableModel);
	}

	public DefaultTableModel getDefaultTableModel(List<Book> list) {
		
		if(list ==null) {
			return new DefaultTableModel();
		}	
		Vector<String> col = new Vector<String>();
		col.add("图书编号");
		col.add("图书类型编号");
		col.add("图书名字");
		col.add("图书作者");
		col.add("图书译者");
		col.add("图书出版日期");
		col.add("图书出版社");
		col.add("图书价格");
		Vector<Object> data = new Vector<>();
		for (Book book : list) {
			Vector<Object> v = new Vector<Object>();
			v.add(book.getISBN());
			v.add(book.getTypeId());
			v.add(book.getBookName());
			v.add(book.getWriter());
			v.add(book.getTranslator());
			v.add(book.getDate());
			v.add(book.getPublisher().toString());
			v.add(book.getPrice());
			data.add(v);
		}
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(data, col);
		return dm;
	}
}
