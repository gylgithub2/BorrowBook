package cn.dfrz.gyl.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import cn.dfrz.gyl.model.Borrow;
import cn.dfrz.gyl.service.BorrowService;
import cn.dfrz.gyl.serviceimpl.FactoryService;

/**
 * @Decription 普通操作员界面,涉及图书的借阅和归还
 */
public class OperatorFrame extends JFrame {
	private static final long serialVersionUID = 112210011L;
	private int isAdmin;
	private BorrowService borrowService = FactoryService.getBorrowService();
	private int operatorId;
	private JPanel jpanel;
	private JLabel lblTitle;
	private int pageIndex = 1;
	private JButton borrowBook;
	private JButton returnBook;
	private JButton refresh;
	private JButton overDue;
	private JButton nextPage;
	private JButton prePage;

	// 模糊查询组件
	private JButton dimQuery;
	private JTextField dimQueryText;

	private JTable jtable;
	private JScrollPane jspa = null;

	public OperatorFrame(int operatorId, int isAdmin) {
		this.isAdmin = isAdmin;
		this.operatorId = operatorId;
		initUI();
		bindEvent();
	}

	private void bindEvent() {
		if (this.isAdmin == 0) {
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		dimQuery.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dimQuery_Click();
			}

		});
		overDue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				overDue_Click();
			}

		});
		refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				refresh_Click();
			}

		});
		borrowBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				borrowBook_Click();
			}

		});
		returnBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				returnBook_Click();
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

	private void returnBook_Click() {
		ReturnBookFrame returnBookFrame = new ReturnBookFrame(this);
		returnBookFrame.setVisible(true);

	}

	private void borrowBook_Click() {
		BorrowBookFrame borrowBookFrame = new BorrowBookFrame(operatorId, this);
		borrowBookFrame.setVisible(true);

	}

	private void overDue_Click() {
		this.showOverDueListData();

	}
	/**
	 * 
	 * @Decription 模糊查询
	 */
	private void dimQuery_Click() {
		String dimString = dimQueryText.getText().trim();
		List<Borrow> dimBookList = borrowService.dimQuery(dimString);
		// list数据传给表格
		DefaultTableModel tableModel = getDefaultTableModel(dimBookList);
		jtable.setModel(tableModel);
	}

	private void initUI() {
		int width = 800;
		int height = 665;
		this.setSize(width, height);
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
		this.setLocation(x, y);
		this.setTitle("图书借阅界面");
		this.setResizable(false); // 可变大小

		jpanel = new JPanel();
		jpanel.setVisible(true);
		jpanel.setLayout(null);
		this.setContentPane(jpanel);
		nextPage = new JButton("下一页");
		nextPage.setFont(new Font("宋体", Font.PLAIN, 18));
		nextPage.setBounds(620, 530, 120, 30);
		jpanel.add(nextPage);

		refresh = new JButton("刷新");
		refresh.setFont(new Font("宋体", Font.PLAIN, 18));
		refresh.setBounds(450, 565, 120, 40);
		jpanel.add(refresh);

		prePage = new JButton("上一页");
		prePage.setFont(new Font("宋体", Font.PLAIN, 18));
		prePage.setBounds(90, 530, 120, 30);
		jpanel.add(prePage);

		lblTitle = new JLabel();
		lblTitle.setText("图书借阅管理");
		lblTitle.setForeground(Color.blue);
		lblTitle.setBounds(280, 20, 400, 100);
		lblTitle.setFont(new Font("楷体", Font.BOLD, 40));
		jpanel.add(lblTitle);
		borrowBook = new JButton("借书");
		borrowBook.setBounds(100, 565, 100, 40);
		borrowBook.setFont(new Font("宋体", Font.PLAIN, 18));
		jpanel.add(borrowBook);
		returnBook = new JButton("还书");
		returnBook.setBounds(600, 565, 100, 40);
		returnBook.setFont(new Font("宋体", Font.PLAIN, 18));
		jpanel.add(returnBook);
		overDue = new JButton("逾期未还");
		overDue.setFont(new Font("宋体", Font.PLAIN, 18));
		overDue.setBounds(270, 565, 120, 40);
		jpanel.add(overDue);

		dimQuery = new JButton("关键字查询");
		dimQuery.setFont(new Font("宋体", Font.PLAIN, 18));
		dimQuery.setBounds(225, 530, 140, 30);
		jpanel.add(dimQuery);
		dimQueryText = new JTextField();
		dimQueryText.setBounds(380, 530, 150, 30);
		jpanel.add(dimQueryText);

		inittableUi();
		jtable.setFont(new Font("宋体", Font.PLAIN, 18));// 表格字体
		jtable.setRowHeight(20);// 行宽
	}

	private void inittableUi() {
		jtable = new JTable();
		jspa = new JScrollPane(jtable);
		jspa.setBounds(50, 120, 700, 400);
		jpanel.add(jspa);
		showListData(1);
	}

	private void refresh_Click() {
		this.setVisible(false);
		new OperatorFrame(operatorId, isAdmin).setVisible(true);

	}

	public void showListData(int pageIndex) {
		List<Borrow> list = null;
		// 输入页数分页查询
		list = borrowService.queryAllInPage(pageIndex);
		// 没有记录了,边界条件判断,点击无效返回
		if (list.size() < 1) {
			this.pageIndex--;
			return;
		}
		// list数据传给表格
		DefaultTableModel tableModel = getDefaultTableModel(list);
		jtable.setModel(tableModel);
	}

	public void showOverDueListData() {
		Long currentLongTime = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Borrow> list = borrowService.queryAll();
		List<Borrow> overDueList = new ArrayList<Borrow>();
		try {
			for (Borrow borrow : list) {
				String backsDate = borrow.getBackDate();
				Date backdate = sdf.parse(backsDate);
				if (borrow.getIsBack()) {
					continue;
				}
				if ((currentLongTime - backdate.getTime()) > 0) {
					overDueList.add(borrow);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// list数据传给表格
		DefaultTableModel tableModel = getOverDueTableModel(overDueList);
		jtable.setModel(tableModel);
	}

	public DefaultTableModel getOverDueTableModel(List<Borrow> list) {
		Vector<String> col = new Vector<String>();
		col.add("借阅编号");
		col.add("图书编号");
		col.add("操作员");
		col.add("读者编号");
		col.add("借阅日期");
		col.add("应还日期");
		Vector<Object> data = new Vector<>();
		for (Borrow borrow : list) {
			Vector<Object> v = new Vector<Object>();
			v.add(borrow.getId());
			v.add(borrow.getBookISBN());
			v.add(borrow.getOperatorId());
			v.add(borrow.getReaderISBN());
			v.add(borrow.getBorrowDate());
			v.add(borrow.getBackDate());
			data.add(v);
		}
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(data, col);
		return dm;
	}

	public DefaultTableModel getDefaultTableModel(List<Borrow> list) {
		Vector<String> col = new Vector<String>();
		col.add("借阅编号");
		col.add("图书编号");
		col.add("操作员");
		col.add("读者编号");
		col.add("是否归还");
		col.add("借阅日期");
		col.add("应还日期");
		col.add("实际归还日期");
		Vector<Object> data = new Vector<>();
		for (Borrow borrow : list) {
			Vector<Object> v = new Vector<Object>();
			v.add(borrow.getId());
			v.add(borrow.getBookISBN());
			v.add(borrow.getOperatorId());
			v.add(borrow.getReaderISBN());
			v.add(borrow.getIsBack());
			v.add(borrow.getBorrowDate());
			v.add(borrow.getBackDate());
			v.add(borrow.getRealBackDate());
			data.add(v);
		}
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(data, col);
		return dm;
	}

}
