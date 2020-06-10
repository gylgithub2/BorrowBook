package cn.dfrz.gyl.UI;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.dfrz.gyl.model.Book;
import cn.dfrz.gyl.model.Order;
import cn.dfrz.gyl.service.OrderService;
import cn.dfrz.gyl.serviceimpl.FactoryService;
import cn.dfrz.gyl.utils.JFrameUtils;

/**
 * @Decription 更新订购信息类 
 */
public class UpdateOrderFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private OrderService orderService = FactoryService.getOrderService();
	private OrderFrame orderFrame = null;
	boolean isNewBook;
	//修改的实际id
	private int id;
	
	private Book book;
	
	private JPanel jpanel;
	private JButton commit;
	private JButton reset;

	private JLabel bookISBN;
	private JLabel date;
	private JLabel number;
	private JLabel operator;
	private JLabel zk;
	private JLabel title;

	private JTextField addBookISBN;
	private JTextField addDate;
	private JTextField addNumber;
	private JTextField addOperator;
	private JTextField addZk;

	public UpdateOrderFrame(OrderFrame orderFrame,Order order,Book book) {
		this.book = book;
		this.orderFrame = orderFrame;
		this.id = order.getId();
		this.isNewBook=order.getNewBookFlag();
		initView();
		loadData(order);
		bindEvent();
	}

	// 获取当前父界面当前选择行
	public void loadData(Order order) {
		addBookISBN.setText(order.getBookISBN());
		addDate.setText(order.getDate());
		addNumber.setText(order.getNumber() + "");
		addOperator.setText(order.getOperator());
		addZk.setText(order.getZk() + "");
	}

	// 事件绑定
	public void bindEvent() {
		commit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				commit_Click(e);

			}

		});
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reset_Click(e);

			}

		});
	}

	// 重置按钮
	private void reset_Click(ActionEvent e) {
		addNumber.setText("");
		addZk.setText("");
		addBookISBN.setText("");

	}

	// 提交
	private void commit_Click(ActionEvent e) {
		String bookISBN_ = addBookISBN.getText().trim();
		String date_ = addDate.getText().trim();
		String number_ = addNumber.getText().trim();
		String operator_ = addOperator.getText().trim();
		String zk_ = addZk.getText().trim();

		if (JFrameUtils.StringArrAnyIsEmpty(bookISBN_, date_, number_, operator_, zk_)) {
			JOptionPane.showMessageDialog(null, "请输入完整信息!");
			return;
		}
		int numbers = JFrameUtils.checkNum(number_);
		if(numbers <0 ) {
			JOptionPane.showMessageDialog(null, "订购数量应该为正整数!");
			return;
		}
			
		double zks = JFrameUtils.checkDoubleNum(zk_);
		if(numbers <0 ) {
			JOptionPane.showMessageDialog(null, "请输入0-1的数表示折扣");
			return;
		}
		if (zks < 0 || zks > 1) {
			JOptionPane.showMessageDialog(null, "请输入0-1的数表示折扣");
			return;
		}
		double pay=book.getPrice().doubleValue()*zks*numbers;
		Order order = new Order(id,isNewBook,bookISBN_, date_, numbers, operator_, false, zks,pay);
		if (orderService.update(order) > 0) {
			JOptionPane.showMessageDialog(null, "修改成功!");
		} else {
			JOptionPane.showMessageDialog(null, "出现异常,请重试");
			return;
		}
		this.setVisible(false);
		orderFrame.showListData(1);

	}

	public void initView() {
		int width = 400;
		int height = 500;
		// 大小
		this.setSize(width, height);
		// 屏幕居中
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
		this.setLocation(x, y);
		// 标题
		this.setTitle("修改图书订购信息");
		// 可变大小
		this.setResizable(false);
		jpanel = new JPanel();
		jpanel.setVisible(true);
		// 布局
		jpanel.setLayout(null);
		// 添加到Jframe
		this.setContentPane(jpanel);

		addBookISBN = new JTextField();
		addBookISBN.setBounds(160, 100, 200, 30);
		jpanel.add(addBookISBN);
		addBookISBN.setEditable(false);
		bookISBN = new JLabel("图书编号:");
		bookISBN.setFont(new Font("宋体", Font.BOLD, 20));
		bookISBN.setBounds(20, 95, 140, 40);
		jpanel.add(bookISBN);

		addDate = new JTextField();
		addDate.setBounds(160, 150, 200, 30);
		jpanel.add(addDate);
		addDate.setEditable(false);
		date = new JLabel("图书订购时间:");
		date.setFont(new Font("宋体", Font.BOLD, 20));
		date.setBounds(20, 145, 140, 40);
		jpanel.add(date);

		addNumber = new JTextField();
		addNumber.setBounds(160, 200, 200, 30);
		jpanel.add(addNumber);
		number = new JLabel("订购图书数量:");
		number.setFont(new Font("宋体", Font.BOLD, 20));
		number.setBounds(20, 195, 140, 40);
		jpanel.add(number);

		addOperator = new JTextField();
		addOperator.setBounds(160, 250, 200, 30);
		jpanel.add(addOperator);
		operator = new JLabel("操作员:");
		addOperator.setEditable(false);
		operator.setFont(new Font("宋体", Font.BOLD, 20));
		operator.setBounds(20, 245, 140, 40);
		jpanel.add(operator);
		
		addZk = new JTextField();
		addZk.setBounds(160, 300, 200, 30);
		jpanel.add(addZk);
		zk = new JLabel("折扣:");
		zk.setFont(new Font("宋体", Font.BOLD, 20));
		zk.setBounds(20, 295, 140, 40);
		jpanel.add(zk);

		title = new JLabel("修改订购图书信息");
		title.setFont(new Font("楷体", Font.BOLD, 30));
		title.setBounds(50, 20, 500, 40);
		jpanel.add(title);

		reset = new JButton("重置");
		reset.setBounds(230, 405, 110, 40);
		jpanel.add(reset);

		commit = new JButton("提交");
		commit.setBounds(70, 405, 110, 40);
		jpanel.add(commit);
		this.getRootPane().setDefaultButton(commit);
	}

}
