
package cn.dfrz.gyl.UI;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.dfrz.gyl.model.Borrow;
import cn.dfrz.gyl.model.Reader;
import cn.dfrz.gyl.service.BookService;
import cn.dfrz.gyl.service.BookTypeService;
import cn.dfrz.gyl.service.BorrowService;
import cn.dfrz.gyl.service.ReaderService;
import cn.dfrz.gyl.serviceimpl.BorrowServiceimpl;
import cn.dfrz.gyl.serviceimpl.FactoryService;
import cn.dfrz.gyl.utils.JFrameUtils;

/**
 * @Decription 图书借阅业务类
 */
public class BorrowBookFrame extends JFrame {
	private static final long serialVersionUID = 515454541L;
	private BorrowService borrowService = new BorrowServiceimpl();
	private static BookService bookService = FactoryService.getBookService();
	private static ReaderService readerService = FactoryService.getReaderService();
	private static BookTypeService bookTypeService = FactoryService.getBookTypeService();
	// 父界面
	private OperatorFrame operatorFrame;
	// 操作员id
	private int operatorId;
	private JPanel jpanel;
	private JButton commit;
	private JLabel bookISBN;
	private JLabel readerISBN;
	private JLabel borrowDays;
	private JLabel title;
	private JTextField addBookISBN;
	private JTextField addReaderISBN;
	private JTextField addBorrowDays;

	public BorrowBookFrame(int operatorId, OperatorFrame operatorFrame) {
		this.operatorId = operatorId;
		this.operatorFrame = operatorFrame;
		initView();
		bindEvent();
	}

	public BorrowBookFrame() {
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
	}

	public void initView() {
		int width = 400;
		int height = 400;
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
		addBookISBN.setBounds(140, 100, 200, 30);
		jpanel.add(addBookISBN);
		bookISBN = new JLabel("图书编号:");
		bookISBN.setFont(new Font("宋体", Font.BOLD, 20));
		bookISBN.setBounds(30, 95, 110, 40);
		jpanel.add(bookISBN);

		addReaderISBN = new JTextField();
		addReaderISBN.setBounds(140, 150, 200, 30);
		jpanel.add(addReaderISBN);
		readerISBN = new JLabel("读者编号:");
		readerISBN.setFont(new Font("宋体", Font.BOLD, 20));
		readerISBN.setBounds(30, 145, 110, 40);
		jpanel.add(readerISBN);

		addBorrowDays = new JTextField();
		addBorrowDays.setBounds(140, 200, 200, 30);
		jpanel.add(addBorrowDays);
		borrowDays = new JLabel("借书天数:");
		borrowDays.setFont(new Font("宋体", Font.BOLD, 20));
		borrowDays.setBounds(30, 195, 110, 40);
		jpanel.add(borrowDays);

		title = new JLabel("录入图书借阅基本信息");
		title.setFont(new Font("楷体", Font.BOLD, 30));
		title.setBounds(50, 20, 500, 40);
		jpanel.add(title);

		commit = new JButton("提交");
		commit.setBounds(150, 235, 100, 40);
		jpanel.add(commit);

		this.getRootPane().setDefaultButton(commit);
	}

	private void commit_Click(ActionEvent e) {
		String bookISBN_ = addBookISBN.getText().trim();
		String readerISBN_ = addReaderISBN.getText().trim();
		String addBorrowDay = addBorrowDays.getText().trim();

		if (JFrameUtils.StringArrAnyIsEmpty(bookISBN_, readerISBN_, addBorrowDay)) {
			JOptionPane.showMessageDialog(null, "请输入完整信息!");
			return;
		}
		// 判断图书是否存在
		if (bookService.queryByISBN(bookISBN_) == null) {
			JOptionPane.showMessageDialog(null, "您输入的图书编号不存在!");
			return;
		}
		Borrow queryByBookISBN = borrowService.queryByBookIsBack(bookISBN_);
		if (queryByBookISBN != null) {
			JOptionPane.showMessageDialog(null, "您输入的图书还未归还!");
			return;
		}
		Reader reader = readerService.queryByISBN(readerISBN_);
		if (reader == null) {
			JOptionPane.showMessageDialog(null, "您输入的读者编号不存在!");
			return;
		}
		double keepMoney = reader.getKeepMoney().doubleValue();
		if (keepMoney <= 0) {
			JOptionPane.showMessageDialog(null, "该读者押金不足!");
			return;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long subTime = 0;
		try {
			subTime = simpleDateFormat.parse(reader.getDate()).getTime() - new Date().getTime();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		if (subTime < 0) {
			JOptionPane.showMessageDialog(null, "该读者会员到期!");
			return;
		}

		int maxnum = reader.getMaxNum();
		if (maxnum <= borrowService.queryReaderBorrowNum(readerISBN_).size()) {
			JOptionPane.showMessageDialog(null, "超过该读者的最大借阅数量！该读者最大借阅数为：" + maxnum);
			return;
		}

		int backDays = JFrameUtils.checkNum(addBorrowDay);
		if (backDays < 0) {
			JOptionPane.showMessageDialog(null, "借阅天数请输入正整数");
			return;
		}
		int maxDays = bookTypeService.queryById(bookService.queryByISBN(bookISBN_).getTypeId()).getDays();
		if (backDays > maxDays) {
			JOptionPane.showMessageDialog(null, "超过该书的最大借阅天数！该书籍最大借阅天数为：" + maxDays);
			return;

		}

		long currentms = System.currentTimeMillis();
		Date date = new Date();
		String currentTime = simpleDateFormat.format(date);

		long backCurrenTime = (long) (currentms + (backDays * 24 * 60 * 60 * 1000));
		Date backdate = new Date(backCurrenTime);
		String backTime = simpleDateFormat.format(backdate);

		Borrow borrow = new Borrow();
		borrow.setBookISBN(bookISBN_);
		borrow.setReaderISBN(readerISBN_);
		borrow.setBorrowDate(currentTime);
		borrow.setOperatorId(operatorId);
		borrow.setBackDate(backTime);
		borrow.setIsBack(false);

		if (borrowService.insertBorrow(borrow) > 0) {
			JOptionPane.showMessageDialog(null, "添加成功");
		} else {
			JOptionPane.showMessageDialog(null, "添加失败，请重试");
			return;
		}
		operatorFrame.showListData(1);
		this.setVisible(false);
	}
}
