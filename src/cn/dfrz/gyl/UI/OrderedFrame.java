
package cn.dfrz.gyl.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cn.dfrz.gyl.model.Order;
import cn.dfrz.gyl.service.OrderService;
import cn.dfrz.gyl.serviceimpl.FactoryService;

/**
 * 
 * @Decription 历史图书订单界面
 *
 */
public class OrderedFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private OrderService orderService = FactoryService.getOrderService();
	private JPanel jpanel;
	private JLabel lblTitle;
	private JTable jtable;
	private JScrollPane jspa = null;
	private JButton refresh;
	private int pageIndex = 1;;
	private JButton nextPage;
	private JButton prePage;

	public OrderedFrame() {
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
		this.setTitle("图书采购记录");
		// 可变大小
		this.setResizable(false);
		jpanel = new JPanel();
		jpanel.setVisible(true);
		// 布局
		jpanel.setLayout(null);
		// 添加到Jframe
		this.setContentPane(jpanel);

		lblTitle = new JLabel();
		lblTitle.setText("图书采购记录");
		lblTitle.setForeground(Color.red);
		lblTitle.setBounds(350, 15, 400, 100);
		lblTitle.setFont(new Font("楷体", Font.BOLD, 40));
		jpanel.add(lblTitle);

		// 刷新界面使用showlistdata
		inittableUi();
		jtable.setFont(new Font("宋体", Font.PLAIN, 18));// 表格字体
		jtable.setRowHeight(20);// 行宽

		
		nextPage = new JButton("下一页");
		nextPage.setFont(new Font("宋体", Font.PLAIN, 18));
		nextPage.setBounds(680, 510, 120, 30);
		jpanel.add(nextPage);

		prePage = new JButton("上一页");
		prePage.setFont(new Font("宋体", Font.PLAIN, 18));
		prePage.setBounds(110, 510, 120, 30);
		jpanel.add(prePage);

		refresh = new JButton("刷新");
		refresh.setBounds(395, 510, 80, 40);
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
		ImageIcon background = new ImageIcon("E:\\javatext\\BorrowBook\\src\\beij.jpg");
		bglabel.setIcon(background);// 将图片设置到Jlabel中
		bglabel.setBounds(0, -50, background.getIconWidth(), background.getIconHeight());// 设图片显示的区域
		jpanel.setOpaque(false);// 设置面板为透明，在gbLabel之上的JPanel都要设置为透明
	}

	// 设置默认table选择行
	public void setJtableSlet() {
		System.out.println(jtable.getRowCount());
		this.jtable.addRowSelectionInterval(jtable.getRowCount() - 1, jtable.getRowCount() - 1);
	}

	// table初始化
	private void inittableUi() {
		jtable = new JTable();
		jspa = new JScrollPane(jtable);
		jspa.setBounds(50, 100, 900, 400);
		jpanel.add(jspa);
		showListData(1);
	}

	// 事件绑定区
	public void bindEvent() {
	
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				refresh_Click();
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




	private void refresh_Click() {
		showListData(1);
	}

//	// 获取当前选择的学生对象
//	public Order getRowData() {
//		Order Order = null;
//		int index = jtable.getSelectedRow();
//		// 如果下标不为-1，则选中行为数据行
//		if (index != -1) {
//			// 取得表格对象的数据模型
//			TableModel model = jtable.getModel();
//			// 在表格对象模型中，根据选中的行和列，获取相应的数据值,(index,0)对应第一列的学号
//			String ISBN = model.getValueAt(index, 0) + "";
//			String date = model.getValueAt(index, 1) + "";
//			int number = Integer.parseInt(model.getValueAt(index, 2) + "");
//			String operator = model.getValueAt(index, 3) + "";
//			double zk = Double.parseDouble(model.getValueAt(index, 5) + "");
//			Order = new Order(ISBN, date, number, operator, true, zk);
//		}
//		// 如果没有选中任何一行 就意味着student为null 可以提供给外部判断
//		return Order;
//	}
	public void showListData(int pageIndex) {
		List<Order> list = null;
		list = orderService.queryOrderedInPage(pageIndex);
		if(list.size()<1) {
			this.pageIndex--;
			return;
		}
		DefaultTableModel tableModel = getDefaultTableModel(list);
		jtable.setModel(tableModel);
	}

	public DefaultTableModel getDefaultTableModel(List<Order> list) {
		Vector<String> col = new Vector<String>();
		col.add("图书编号");
		col.add("订购日期");
		col.add("订购数量");
		col.add("操作员");
		col.add("折扣");
		col.add("总价");
		Vector<Object> data = new Vector<>();
		for (Order order : list) {
			Vector<Object> v = new Vector<Object>();
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
