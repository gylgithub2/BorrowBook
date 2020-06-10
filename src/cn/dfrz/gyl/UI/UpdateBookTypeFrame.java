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

import cn.dfrz.gyl.model.BookType;
import cn.dfrz.gyl.service.BookTypeService;
import cn.dfrz.gyl.serviceimpl.FactoryService;

/**
 * @Decription 更新图书类别
 */
public class UpdateBookTypeFrame extends JFrame{


	private static final long serialVersionUID = 1L;
	private BookTypeService bookTypeService = FactoryService.getBookTypeService();
	private BookTypeFrame bookTypeFrame = null;
	private BookType  bookType= null;
	private JPanel jpanel;
	private JButton commit;
	private JButton reset;
	private JLabel id;
	private JLabel typeName;
	private JLabel days;
	private JLabel FK;
	private JLabel title;
	private int indexId;
	private JTextField insertId;
	private JTextField updateBookType;
	private JTextField updateDays;
	private JTextField updateFK;
	public UpdateBookTypeFrame(BookTypeFrame bookTypeFrame, BookType bookType) {
		this.bookTypeFrame = bookTypeFrame;
		this.bookType=bookType;
		this.indexId = bookType.getId();
		this.bookType = bookType;
		initView();
		loadData(bookType);
		bindEvent();
	}

	//获取当前父界面当前选择行
	public void loadData(BookType bookType) {
		insertId.setText(indexId + "");
		updateBookType.setText(bookType.getTypeName());
		updateFK.setText(bookType.getFK()+"");
		updateDays.setText(bookType.getDays()+"");
	}
	//事件绑定
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

	//重置按钮
	private void reset_Click(ActionEvent e) {
		updateBookType.setText("");
		updateFK.setText("");
		updateDays.setText("");
		
	}
	//提交
	private void commit_Click(ActionEvent e) {
		String TypeName_ = updateBookType.getText().trim();
		String days_ = updateDays.getText().trim();
		String FK_ = updateFK.getText().trim();

		if (TypeName_ == null || "".equals(TypeName_) || days_ == null || "".equals(days_) || FK_ == null
				|| "".equals(FK_)) {
			JOptionPane.showMessageDialog(null, "请输入完整信息!");
			return;
		}
		if (bookTypeService.queryByName(TypeName_) != null && !TypeName_.equals(bookType.getTypeName())) {
			JOptionPane.showMessageDialog(null, "您输入的图书类型名已存在!");
			return;
		}
		int day = 0;
		try {
			day = Integer.parseInt(days_);
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "可借天数请输入数字类型!");
			return;
		}
		if (day < 1) {
			JOptionPane.showMessageDialog(null, "请输入正整数天数!");
			return;
		}
		double Fk = 0;
		try {
			Fk = Double.parseDouble(FK_);
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "罚款可借天数请输入数字类型!");
			return;
		}
		if (Fk < 0) {
			JOptionPane.showMessageDialog(null, "罚款请输入大于0的数!");
			return;
		}
		
		BookType bookType1 = new BookType();
		bookType1.setId(bookType.getId());
		bookType1.setDays(day);
		bookType1.setFK(Fk);
		bookType1.setTypeName(TypeName_);	
		if(bookTypeService.update(bookType1)>0) {
		JOptionPane.showMessageDialog(null, "修改成功!");
		this.setVisible(false);
		bookTypeFrame.showListData(1);
		}else {
			JOptionPane.showMessageDialog(null, "出现异常,请重新修改!");
			return;
		}	
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

		typeName = new JLabel("图书类型名:");
		typeName.setFont(new Font("宋体", Font.BOLD, 20));
		typeName.setBounds(20, 125, 140, 40);
		jpanel.add(typeName);
		id = new JLabel("图书编号:");
		id.setFont(new Font("宋体", Font.BOLD, 20));
		id.setBounds(20, 75, 140, 40);
		jpanel.add(id);
		days = new JLabel("图书可借天数:");
		days.setFont(new Font("宋体", Font.BOLD, 20));
		days.setBounds(20, 175, 140, 40);
		jpanel.add(days);
		FK = new JLabel("图书超期罚款:");
		FK.setFont(new Font("宋体", Font.BOLD, 20));
		FK.setBounds(20, 225, 140, 40);
		jpanel.add(FK);

		title = new JLabel("修改图书类型基本信息");
		title.setFont(new Font("楷体", Font.BOLD, 30));
		title.setBounds(50, 20, 500, 40);
		jpanel.add(title);

		reset = new JButton("重置");
		reset.setBounds(230, 265, 110, 40);
		jpanel.add(reset);
	
		//输入框
		insertId = new JTextField();
		insertId.setBounds(160, 80, 200, 30);
		insertId.setFont(new Font("宋体", Font.BOLD, 15));
		insertId.setEditable(false);
		updateDays = new JTextField();
		updateDays.setBounds(160, 130, 200, 30);
		updateDays.setFont(new Font("宋体", Font.BOLD, 15));
		updateBookType = new JTextField();
		updateBookType.setBounds(160, 180, 200, 30);
		updateBookType.setFont(new Font("宋体", Font.BOLD, 15));
		updateFK = new JTextField();
		updateFK.setBounds(160, 230, 200, 30);
		updateFK.setFont(new Font("宋体", Font.BOLD, 15));
		jpanel.add(insertId);
		jpanel.add(updateDays);
		jpanel.add(updateBookType);
		jpanel.add(updateFK);
		//按钮
		commit = new JButton("提交");
		commit.setBounds(70, 265, 110, 40);
		jpanel.add(commit);
		this.getRootPane().setDefaultButton(commit);
	}
}