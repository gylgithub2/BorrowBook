
package cn.dfrz.gyl.UI;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.dfrz.gyl.model.Book;
import cn.dfrz.gyl.model.Order;
import cn.dfrz.gyl.service.BookService;
import cn.dfrz.gyl.service.OrderService;
import cn.dfrz.gyl.serviceimpl.FactoryService;
import cn.dfrz.gyl.utils.JFrameUtils;

/**
 * @Decription 添加订购信息界面
 */
public class AddOrderFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	//增加订购记录时,创建一个订购记录对象
	private Order order = new Order();
	//父界面
	private OrderFrame orderFrame;
	private OrderService orderService = FactoryService.getOrderService();
	private BookService bookService = FactoryService.getBookService();
	//操作员名字
	private String operatorName;
	//新增订单的单个图书价格，如果在该界面新增图书,要求新增过后返回它的价格为这个属性赋值;
	private Double newBookPrice;
	
	public void setNewBookPrice(double newBookPrice) {
		this.newBookPrice = newBookPrice;
	}
	private JPanel jpanel;
	private JButton commit;
	private JButton reset;
	private JLabel BookISBN;
	private JLabel number;
	private JLabel zk;
	private JLabel title;

	private JTextField addBookISBN;
	private JTextField addNumber;
	private JTextField addZk;

	// 带参构造器,传入操作员以及母界面,分别用于存储和刷新界面
	public AddOrderFrame(String operatorName, OrderFrame orderFrame) {
//		//订购记录是否订购新书籍,默认为false
//		this.order.setNewBookFlag(false);
		this.orderFrame = orderFrame;
		this.operatorName = operatorName;
		initView();
		bindEvent();
	}

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

	private void reset_Click(ActionEvent e) {
		addBookISBN.setText("");
		addNumber.setText("");
		addZk.setText("");

	}

	private void commit_Click(ActionEvent e) {
		String bookISBN_ = addBookISBN.getText().trim();
		String number_ = addNumber.getText().trim();
		String zk_ = addZk.getText().trim();

		if (JFrameUtils.StringArrAnyIsEmpty(bookISBN_, number_, zk_)) {
			JOptionPane.showMessageDialog(null, "请输入完整信息!");
			return;
		}

		int numbers = JFrameUtils.checkNum(number_);
		if(numbers<0) {
			JOptionPane.showMessageDialog(null, "订购的数量请输入正整数类型!");
			return;
		}

		double zks = JFrameUtils.checkDoubleNum(zk_);
		if(zks < 0) {
			JOptionPane.showMessageDialog(null, "请输入0-1的小数表示折扣!");
			return;
		}
		if (zks < 0 || zks > 1) {
			JOptionPane.showMessageDialog(null, "请输入0-1的小数表示折扣!");
			return;
		}
		// 找不到这个图书信息,则要求先插入到book表内一条记录
		//第一个if是判断第二次执行时，不会进入数据库进行判断图书信息是否存在
		Book queryByISBN = bookService.queryByISBN(bookISBN_);
		if (!order.getNewBookFlag()) {
			if ( queryByISBN == null) {
				JOptionPane.showMessageDialog(null, "该图书不存在,请先添加图书信息!");
				AddBookFrame addBookFrame = new AddBookFrame(this,bookISBN_);
				order.setNewBookFlag(true);
				addBookFrame.setVisible(true);
				return;
			}else{
				this.newBookPrice=queryByISBN.getPrice().doubleValue();
			}
		}
		
		
		order.setBookISBN(bookISBN_);
		order.setDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		order.setNumber(numbers);
		order.setOperator(operatorName);
		order.setZk(zks);
		order.setCheakAndAccept(false);
		order.setPay(newBookPrice*zks*numbers);
		if (orderService.insert(order) > 0) {
			JOptionPane.showMessageDialog(null, "添加成功!");
		} else {
			JOptionPane.showMessageDialog(null, "出现异常,请重新添加!");
			return;
		}
		orderFrame.shows();
		this.setVisible(false);

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
		this.setTitle("添加图书借阅信息");
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
		BookISBN = new JLabel("图书编号:");
		BookISBN.setFont(new Font("宋体", Font.BOLD, 20));
		BookISBN.setBounds(20, 95, 140, 40);
		jpanel.add(BookISBN);

		addNumber = new JTextField();
		addNumber.setBounds(160, 200, 200, 30);
		jpanel.add(addNumber);
		number = new JLabel("订购图书数量:");
		number.setFont(new Font("宋体", Font.BOLD, 20));
		number.setBounds(20, 195, 140, 40);
		jpanel.add(number);

		addZk = new JTextField();
		addZk.setBounds(160, 300, 200, 30);
		jpanel.add(addZk);
		zk = new JLabel("折扣:");
		zk.setFont(new Font("宋体", Font.BOLD, 20));
		zk.setBounds(20, 295, 140, 40);
		jpanel.add(zk);

		title = new JLabel("录入订购图书信息");
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
