
package cn.dfrz.gyl.UI;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.dfrz.gyl.model.BookType;
import cn.dfrz.gyl.service.BookTypeService;
import cn.dfrz.gyl.serviceimpl.FactoryService;
import cn.dfrz.gyl.utils.JFrameUtils;

/**
 * @Decription 新增书类别
 */
public class AddBookTypeFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	// 字体
	private Font font = new Font("宋体", Font.BOLD, 20);
	// 业务类
	private BookTypeService bookTypeService = FactoryService.getBookTypeService();
	// 父界面对象
	private BookTypeFrame bookTypeFrame = null;
	private JPanel jpanel;
	private JButton commit;
	private JButton reset;
	private JLabel typeName;
	private JLabel typeID;
	private JLabel days;
	private JLabel FK;
	private JLabel title;
	private JTextField addTypeID;
	private JTextField addTypeName;
	private JTextField addDays;
	private JTextField addFK;

	public AddBookTypeFrame(BookTypeFrame bookTypeFrame) {
		this.bookTypeFrame = bookTypeFrame;
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
		addTypeID.setText("");
		addTypeName.setText("");
		addDays.setText("");
		addFK.setText("");

	}

	private void commit_Click(ActionEvent e) {
		String typeName_ = addTypeName.getText().trim();
		String typeID_ = addTypeID.getText().trim();
		String days_ = addDays.getText().trim();
		String FK_ = addFK.getText().trim();

		if (JFrameUtils.StringArrAnyIsEmpty(typeName_, typeID_, days_, FK_)) {
			JOptionPane.showMessageDialog(null, "请输入完整信息!");
			return;
		}
		if (bookTypeService.queryByName(typeName_) != null) {
			JOptionPane.showMessageDialog(null, "您输入的图书类型名已存在!");
			return;
		}

		int typeIDs = JFrameUtils.checkNum(typeID_);
		if (typeIDs < 0) {
			JOptionPane.showMessageDialog(null, "图书类型编号请输入大于0数字!");
			return;
		}
		BookType qbooktype = bookTypeService.queryById(typeIDs);
		if (qbooktype != null) {
			JOptionPane.showMessageDialog(null, "图书类型编号" + typeIDs + "已存在,名字为" + qbooktype.getTypeName());
			return;
		}

		int day = JFrameUtils.checkNum(days_);
		if (typeIDs < 0) {
			JOptionPane.showMessageDialog(null, "图书借阅最大天数请输入大于0的数字!");
			return;
		}
		double Fk = JFrameUtils.checkDoubleNum(FK_);
		if (Fk < 0) {
			JOptionPane.showMessageDialog(null, "付款可借天数请输入数字类型!");
			return;
		}
		BookType bookType = new BookType();
		bookType.setDays(day);
		bookType.setFK(Fk);
		bookType.setTypeName(typeName_);
		bookType.setId(typeIDs);

		if (bookTypeService.insert(bookType) > 0) {
			JOptionPane.showMessageDialog(null, "添加成功!");
		} else {
			JOptionPane.showMessageDialog(null, "出现异常,请重试!");
			return;
		}
		bookTypeFrame.showListData(1);
		this.setVisible(false);

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
		this.setTitle("添加图书订购信息");
		// 可变大小
		this.setResizable(false);
		jpanel = new JPanel();
		jpanel.setVisible(true);
		// 布局
		jpanel.setLayout(null);
		// 添加到Jframe
		this.setContentPane(jpanel);

		addTypeName = new JTextField();
		addTypeName.setBounds(160, 150, 200, 30);
		jpanel.add(addTypeName);
		typeName = new JLabel("图书类型名:");
		typeName.setFont(font);
		typeName.setBounds(20, 145, 140, 40);
		jpanel.add(typeName);
		List<BookType> list = bookTypeService.queryAll();
		int id = list.get(list.size() - 1).getId() + 1;
		addTypeID = new JTextField(id + "");
		addTypeID.setBounds(160, 100, 200, 30);
		jpanel.add(addTypeID);
		typeID = new JLabel("图书类型编号:");
		typeID.setFont(font);
		typeID.setBounds(20, 95, 140, 40);
		jpanel.add(typeID);

		addDays = new JTextField();
		addDays.setBounds(160, 200, 200, 30);
		jpanel.add(addDays);
		days = new JLabel("图书可借天数:");
		days.setFont(font);
		days.setBounds(20, 195, 140, 40);
		jpanel.add(days);

		addFK = new JTextField();
		addFK.setBounds(160, 250, 200, 30);
		jpanel.add(addFK);
		FK = new JLabel("图书超期罚款:");
		FK.setFont(font);
		FK.setBounds(20, 245, 140, 40);
		jpanel.add(FK);

		title = new JLabel("录入图书类型基本信息");
		title.setFont(font);
		title.setBounds(50, 20, 500, 40);
		jpanel.add(title);

		reset = new JButton("重置");
		reset.setBounds(230, 305, 110, 40);
		jpanel.add(reset);

		commit = new JButton("提交");
		commit.setBounds(70, 305, 110, 40);
		jpanel.add(commit);
		this.getRootPane().setDefaultButton(commit);
	}
}
