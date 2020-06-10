package cn.dfrz.gyl.UI;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
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
import cn.dfrz.gyl.serviceimpl.FactoryService;
import cn.dfrz.gyl.utils.JFrameUtils;

/**
 * @Decription 图书归还类
 */
public class ReturnBookFrame extends JFrame {

	private static final long serialVersionUID = 515454541L;

	private OperatorFrame operatorFrame;
	private BorrowService borrowService = FactoryService.getBorrowService();
	private BookService bookService = FactoryService.getBookService();
	private ReaderService readerService = FactoryService.getReaderService();
	private BookTypeService bookTypeService = FactoryService.getBookTypeService();
	private JPanel jpanel;
	private JButton commit;
	private JLabel backBookISBN;
	private JLabel title;
	private JTextField addBackBookISBN;

	public ReturnBookFrame(OperatorFrame operatorFrame) {
		this.operatorFrame = operatorFrame;
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
		this.setTitle("归还图书");
		// 可变大小
		this.setResizable(false);
		jpanel = new JPanel();
		jpanel.setVisible(true);
		// 布局
		jpanel.setLayout(null);
		// 添加到Jframe
		this.setContentPane(jpanel);

		addBackBookISBN = new JTextField();
		addBackBookISBN.setBounds(140, 100, 200, 30);
		jpanel.add(addBackBookISBN);
		backBookISBN = new JLabel("图书编号:");
		backBookISBN.setFont(new Font("宋体", Font.BOLD, 20));
		backBookISBN.setBounds(30, 95, 110, 40);
		jpanel.add(backBookISBN);

		title = new JLabel("录入归还图书编号");
		title.setFont(new Font("楷体", Font.BOLD, 30));
		title.setBounds(50, 20, 500, 40);
		jpanel.add(title);

		commit = new JButton("提交");
		commit.setBounds(150, 235, 100, 40);
		jpanel.add(commit);
		operatorFrame.showListData(1);
		this.getRootPane().setDefaultButton(commit);
	}

	private void commit_Click(ActionEvent e) {
		String backBookISBN_ = addBackBookISBN.getText().trim();

		if (JFrameUtils.StringArrAnyIsEmpty(backBookISBN_)) {
			JOptionPane.showMessageDialog(null, "请输入归还图书的ISBN!");
			return;
		}
		// 获取该书号的借阅记录;
		Borrow nowBorrow = borrowService.queryByBookIsBack(backBookISBN_);
		// 判断最后一次借阅信息
		if (nowBorrow == null) {
			JOptionPane.showMessageDialog(null, "该图书未被借出!");
			return;
		}
		// 获取当前系统时间作为归还时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long currentms = System.currentTimeMillis();
		String realBackDate = simpleDateFormat.format(new Date(currentms));
		// 应还日期获取
		long overDateMills = 0l;
		try {
			overDateMills = currentms - simpleDateFormat.parse(nowBorrow.getBackDate()).getTime();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		// 判断是否缴纳罚款,以及超期天数,不足一天,按一天计算
		if (overDateMills > 0) {
			long fday = overDateMills / 1000 / 60 / 60 / 24;
			int day = (int) fday;
			// 不足一天按一天计算
			if (day * 24 * 60 * 60 * 1000 - fday > 0) {
				day += 1;
			}
			// 计算此次超期最终罚款
			double bookFK = bookTypeService.queryById(bookService.queryByISBN(backBookISBN_).getTypeId()).getFK();
			BigDecimal fk = new BigDecimal(bookFK * day);

			// 获取押金，扣除罚款，修改读者押金shu
			Reader reader = readerService.queryByISBN(nowBorrow.getReaderISBN());
			BigDecimal keepMoney = reader.getKeepMoney();
			BigDecimal remainderMoney = keepMoney.subtract(fk);
			reader.setKeepMoney(remainderMoney);
			readerService.update(reader);
			// 比较押金是否足以负担罚款
			if (remainderMoney.doubleValue() > 0) {
				JOptionPane.showMessageDialog(null, "逾期还书，罚款金额为：" + fk + "元\n扣除后剩余押金:" + remainderMoney+"元\n");
	
			} else {
				int key = JOptionPane.showConfirmDialog(null, "押金不足,是否现在跳转至缴费界面?", " ", JOptionPane.YES_NO_OPTION);// key=0/1
				if (key == 0) {
					// 押金不足,则跳至缴费窗口,完成缴费后退出
					AddKeepMoneyFrame addKeepMoneyFrame = new AddKeepMoneyFrame(reader, -remainderMoney.doubleValue());
					addKeepMoneyFrame.setVisible(true);
				} else {
					// 现在暂缓缴费,但读者缴费前无法借阅
		
					JOptionPane.showMessageDialog(null,
							"逾期还书，罚款金额为：" + fk + "元\n当前余额" + remainderMoney.doubleValue() + "元\n请及时缴费!");
				}
			}
		}
		nowBorrow.setRealBackDate(realBackDate);
		nowBorrow.setIsBack(true);

		borrowService.update(nowBorrow);
		operatorFrame.showListData(1);
		this.setVisible(false);
	}

}
