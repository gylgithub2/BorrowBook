
package cn.dfrz.gyl.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import cn.dfrz.gyl.model.Book;
import cn.dfrz.gyl.model.Order;
import cn.dfrz.gyl.service.BookService;
import cn.dfrz.gyl.service.OrderService;
import cn.dfrz.gyl.serviceimpl.FactoryService;
import cn.dfrz.gyl.utils.JFrameUtils;

/**
 * @Decription 待采购的图书界面
 */
public class OrderFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private String operatorName;
	private Font font = new Font("宋体", Font.BOLD, 18);
	private OrderService orderService = FactoryService.getOrderService();
	private BookService bookService = FactoryService.getBookService();
	private UpdateOrderFrame updateOrderFrame = null;
	private List<Order> list = null;
	private JPanel jpanel;
	private JLabel lblTitle;
	private JLabel lab;
	private JTable jtable;
	private JScrollPane jspa = null;
	private JButton addOrder;
	private JButton delOrder;
	private JButton updOrder;
	private JButton refresh;
	private JButton order;

	private int pageIndex = 1;;
	private JButton nextPage;
	private JButton prePage;

	public OrderFrame(String operatorName) {
		this.operatorName = operatorName;
		initUI();
		bindEvent();
	}

	public void initUI() {
		int width = 1000;
		int height = 665;
		// 大小
		this.setSize(width, height);
		// 屏幕居中
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
		this.setLocation(x, y);
		// 标题
		this.setTitle("图书订购管理");
		// 可变大小
		this.setResizable(false);
		jpanel = new JPanel();
		jpanel.setVisible(true);
		// 布局
		jpanel.setLayout(null);
		// 添加到Jframe
		this.setContentPane(jpanel);

		lab = new JLabel();
		lab.setText("图书订购信息");
		lab.setFont(font);

		lblTitle = new JLabel();
		lblTitle.setText("图书订购信息");
		lblTitle.setForeground(Color.red);
		lblTitle.setBounds(350, 15, 400, 100);
		lblTitle.setFont(new Font("楷体", Font.BOLD, 40));
		jpanel.add(lblTitle);

		// 刷新界面使用showlistdata
		inittableUi();
		jtable.setFont(font);// 表格字体
		jtable.setRowHeight(20);// 行宽

		updOrder = new JButton("修改");
		updOrder.setFont(font);
		updOrder.setBounds(150, 560, 80, 40);
		jpanel.add(updOrder);

		delOrder = new JButton("删除");
		delOrder.setBounds(290, 560, 80, 40);
		delOrder.setFont(font);
		jpanel.add(delOrder);

		addOrder = new JButton("添加");
		addOrder.setBounds(430, 560, 80, 40);
		addOrder.setFont(font);
		jpanel.add(addOrder);

		nextPage = new JButton("下一页");
		nextPage.setFont(font);
		nextPage.setBounds(680, 510, 120, 30);
		jpanel.add(nextPage);

		prePage = new JButton("上一页");
		prePage.setFont(font);
		prePage.setBounds(110, 510, 120, 30);
		jpanel.add(prePage);

		refresh = new JButton("刷新");
		refresh.setBounds(580, 560, 80, 40);
		refresh.setFont(font);
		jpanel.add(refresh);

		order = new JButton("确认订购");
		order.setBounds(395, 510, 150, 40);
		order.setFont(font);
		jpanel.add(order);

		jtable.setFont(font);// 表格字体
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
		jspa.setBounds(50, 100, 900, 400);
		jpanel.add(jspa);
		list = showListData(1);
	}

	// 事件绑定区
	public void bindEvent() {
		addOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addOrder_Click();
			}

		});
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				refresh_Click();
			}

		});
		delOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				delOrder_Click();
			}
		});
		updOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				updOrder_Click();
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
		order.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				order_Click();
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

	// 用等待采购的图书,确认采购
	private void order_Click() {
		int[] index = jtable.getSelectedRows();
		if (index.length < 1) {
			JOptionPane.showMessageDialog(null, "没有选择行");
			return;
		}
		int key = JOptionPane.showConfirmDialog(null, "你确认订购当前选中的" + index.length + "行?", "确认订购",
				JOptionPane.YES_NO_OPTION);// key=0/1
		// 当前选中的所有行索引
		if (key == 1) {
			return;
		}
		// 创建一个长度为选中的行数量相同的数组
		int count = 0;
		for (int i = 0; i < index.length; i++) {
			Order order = list.get(index[i]);
			order.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			order.setCheakAndAccept(true);
			if (addInLibrary(order.getNewBookFlag(), order.getBookISBN(), order.getNumber(), order.getDate()) > 0) {
				if (orderService.update(order) > 0) {
					// 订购成功,修改当前时间为实际订购日期
					File file = new File(JFrameUtils.getTemporaryFileLocation() + "\\" + order.getBookISBN() + ".dat");
					file.delete();
					count++;
				}
			}
		}
		if (count == 1) {
			JOptionPane.showMessageDialog(null, "采购成功");
		} else if (count > 1) {
			JOptionPane.showMessageDialog(null, "采购成功" + count + "条" + "采购失败" + (index.length - count) + "个");
		} else {
			JOptionPane.showMessageDialog(null, "采购失败!");
			return;
		}
		shows();
	}

	// 取出指定ISBN的图书
	public Book getBook(String bookISBN) {
		ObjectInputStream ois = null;
		File file = null;
		Book oneBook = null;
		try {
			file = new File(JFrameUtils.getTemporaryFileLocation() + "\\" + bookISBN + ".dat");
			ois = new ObjectInputStream(new FileInputStream(file));
			oneBook = (Book) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return oneBook;
	}

	// 取出服务器保存的临时图书对象,插入数据库一定的新增图书
	public int setBooks(String bookISBN, int num, String date) {
		int isSuccess = -1;
		Book book = getBook(bookISBN);
		// 调用服务层方法处理这次业务,返回0失败插入,1则成功插入
		isSuccess = bookService.batchInsert(book, num, date);
		return isSuccess;
	}

	// 将确认订购的图书,按数量添加至数据库
	public int addInLibrary(boolean newBookFlag, String bookISBN, int num, String date) {
		// 图书信息原本不存在,添加了一个临时的图书信息,添加完删除原有的ISBN上删除末尾的日期等信息,再加上新的日期信息
		if (newBookFlag) {
			// 新增图书转发这次请求,返回0失败插入,1则成功插入
			return setBooks(bookISBN, num, date);
		} else {
			// 原有图书新增数量，直接截取选择的ISBN父字符串，新添加字串即可
			String[] splitISBN = bookISBN.split("_");
			String FatherBookISBN = splitISBN[0];
			Book book = bookService.queryByISBN(bookISBN);
			for (int i = 0; i < num; i++) {
				book.setISBN(FatherBookISBN + "_" + date.split(" ")[0].replaceAll("-", "") + "_" + (i + 1));
				bookService.insert(book);
			}
			return 2;
		}
	}

	// 添加按钮
	public void addOrder_Click() {
		AddOrderFrame addOrderFrame = new AddOrderFrame(operatorName, this);
		addOrderFrame.setVisible(true);

	}

	public void shows() {
		new OrderFrame(this.operatorName).setVisible(true);
		this.setVisible(false);
	}

	// 批量删除选择的所有行
	private void delOrder_Click() {
		int[] index = jtable.getSelectedRows();
		if (index.length < 1) {
			JOptionPane.showMessageDialog(null, "没有选择行");
			return;
		}

		int key = JOptionPane.showConfirmDialog(null, "你确认删除当前选中的" + index.length + "行?", "确认删除",
				JOptionPane.YES_NO_OPTION);// key=0/1
		// 当前选中的所有行索引
		if (key == 1) {
			return;
		}
		int count = 0;
		for (int i = 0; i < index.length; i++) {
			int k = orderService.delete(list.get(index[i]).getId());
			if (k >= 1) {
				count++;
			}
		}
		if (count == 1) {
			JOptionPane.showMessageDialog(null, "删除成功");
		} else if (count > 1) {
			JOptionPane.showMessageDialog(null, "删除成功" + count + "个" + "删除失败" + (index.length - count) + "个");
		} else {
			JOptionPane.showMessageDialog(null, "删除失败!");
			return;
		}

		shows();
		// 当前选中行改为上一行
		return;
	}

	private void refresh_Click() {
		shows();
	}

	private void updOrder_Click() {
		Order order = getRowData();
		if (order != null) {
			Book book = bookService.queryByISBN(order.getBookISBN());
			if (book == null) {
				book = getBook(order.getBookISBN());
			}
			updateOrderFrame = new UpdateOrderFrame(this, order, book);
			updateOrderFrame.setVisible(true);
		} else {
			// 如果没有选中任何行，提示用户
			JOptionPane.showMessageDialog(null, "请选中需要修改的图书订购信息");
			return;
		}
	}

	// 获取当前选择的学生对象
	public Order getRowData() {
		Order order = null;
		int index = jtable.getSelectedRow();
		// 如果下标不为-1，则选中行为数据行
		if (index != -1) {
			// 取得表格对象的数据模型
			TableModel model = jtable.getModel();
			// 在表格对象模型中，根据选中的行和列，获取相应的数据值,(index,0)对应第一列的学号
			String ISBN = model.getValueAt(index, 1) + "";
			String date = model.getValueAt(index, 2) + "";
			int number = Integer.parseInt(model.getValueAt(index, 3) + "");
			String operator = model.getValueAt(index, 4) + "";
			double zk = Double.parseDouble(model.getValueAt(index, 5) + "");
			Order order2 = list.get(index);
			int id = order2.getId();
			double pay = order2.getPay();
			boolean newBookFlag = list.get(index).getNewBookFlag();
			order = new Order(id, newBookFlag, ISBN, date, number, operator, false, zk, pay);
		}
		// 如果没有选中任何一行 就意味着student为null 可以提供给外部判断
		return order;
	}

	public List<Order> showListData(int pageIndex) {
		List<Order> list = null;

		list = orderService.queryOrderInPage(pageIndex);
		if (list.size() < 1) {
			this.pageIndex--;
			return null;
		}
		DefaultTableModel tableModel = getDefaultTableModel(list);
		jtable.setModel(tableModel);
		return list;
	}

	public DefaultTableModel getDefaultTableModel(List<Order> list) {

		if (list == null) {
			return new DefaultTableModel();
		}
		Vector<String> col = new Vector<String>();
		col.add("序号");
		col.add("图书编号");
		col.add("申请日期");
		col.add("订购数量");
		col.add("操作员");
		col.add("折扣");
		col.add("总价");
		Vector<Object> data = new Vector<>();
		int i = 1;
		for (Order order : list) {
			Vector<Object> v = new Vector<Object>();
			v.add(i++);
			v.add(order.getBookISBN());
			v.add(order.getDate());
			v.add(order.getNumber());
			v.add(order.getOperator());
			v.add(order.getZk());
			v.add(order.getPay());
			data.add(v);
		}
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(data, col);
		return dm;
	}
}
