package cn.dfrz.gyl.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import cn.dfrz.gyl.model.Operator;
import cn.dfrz.gyl.utils.JFrameUtils;

/**
 * @Decription 管理员权限的全局操作界面
 */
public class AdminFrame extends JFrame {
	private static final long serialVersionUID = 1456295784844L;
	// 从login界面过来的登录操作员
	private Operator operator;

	private JPanel jpanel;
	private JLabel lblTitle;
	private JMenuBar jMenubar;
	private JMenu jMenu1, jMenu2, jMenu3, jMenu4, jMenu5, jMenu6;
//	
	private JMenuItem jItem1, jItem2, jItem3, jItem4_1, jItem4_2, jItem5, jItem6_1, jItem6_2;

	public AdminFrame(Operator operator) {
		this.operator = operator;
		initUI();
		bindEvent();

	}

	private void bindEvent() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		jItem1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jItem1_Click(e);
			}

		});

		jItem2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jItem2_Click(e);
			}

		});

		jItem3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jItem3_Click(e);
			}

		});
		jItem4_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jItem4_1_Click(e);
			}

		});
		jItem4_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jItem4_2_Click(e);
			}

		});
		jItem5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jItem5_Click(e);
			}
		});

		jItem6_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jItem6_1Click(e);
			}

		});
		jItem6_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jItem6_2Click(e);
			}

		});
	}

	// 界面
	public void initUI() {
		int width = 800;
		int height = 665;
		this.setSize(width, height);
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
		this.setLocation(x, y);
		this.setTitle("管理员界面");
		this.setResizable(false);

		jpanel = new JPanel();
		jpanel.setVisible(true);
		jpanel.setLayout(null);
		this.setContentPane(jpanel);

		lblTitle = new JLabel();
		lblTitle.setText("系统管理");
		lblTitle.setForeground(Color.blue);
		lblTitle.setBounds(280, 50, 400, 200);
		lblTitle.setFont(new Font("楷体", Font.BOLD, 60));
		jpanel.add(lblTitle);

		jMenubar = new JMenuBar();
		jMenu1 = new JMenu("图书类别管理");
		jMenu2 = new JMenu("图书信息管理");
		jMenu3 = new JMenu("读者信息管理");
		jMenu4 = new JMenu("新书订购管理");
		jMenu5 = new JMenu("图书借阅管理");
		jMenu6 = new JMenu("系统维护");
		jItem1 = new JMenuItem("图书类别信息");
		jItem2 = new JMenuItem("图书信息");
		jItem3 = new JMenuItem("读者信息");
		jItem4_1 = new JMenuItem("待购新书");
		jItem4_2 = new JMenuItem("已购新书");
		jItem5 = new JMenuItem("图书借阅");
		jItem6_1 = new JMenuItem("更改口令");
		jItem6_2 = new JMenuItem("用户管理");
		jMenu1.add(jItem1);
		jMenu2.add(jItem2);
		jMenu3.add(jItem3);
		jMenu4.add(jItem4_1);
		jMenu4.add(jItem4_2);

		jMenu5.add(jItem5);
		jMenu6.add(jItem6_1);
		jMenu6.add(jItem6_2);
		jMenubar.add(jMenu1);
		jMenubar.add(jMenu2);
		jMenubar.add(jMenu3);
		jMenubar.add(jMenu4);
		jMenubar.add(jMenu5);
		jMenubar.add(jMenu6);
		this.setJMenuBar(jMenubar);

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

	private void jItem1_Click(ActionEvent e) {
		BookTypeFrame BookTypeFrame = new BookTypeFrame();
		BookTypeFrame.setVisible(true);

	}

	private void jItem2_Click(ActionEvent e) {
		BookFrame bookFrame = new BookFrame();
		bookFrame.setVisible(true);

	}

	private void jItem3_Click(ActionEvent e) {
		ReaderFrame readerFrame = new ReaderFrame();
		readerFrame.setVisible(true);

	}

	private void jItem4_1_Click(ActionEvent e) {
		OrderFrame orderFrame = new OrderFrame(operator.getName());
		orderFrame.setVisible(true);

	}

	private void jItem4_2_Click(ActionEvent e) {
		OrderedFrame orderedFrame = new OrderedFrame();
		orderedFrame.setVisible(true);

	}

	private void jItem5_Click(ActionEvent e) {
		OperatorFrame operatorFrame = new OperatorFrame(operator.getId(), 1);
		operatorFrame.setVisible(true);

	}

	private void jItem6_1Click(ActionEvent e) {
		UpdatePasswordJFrame updatePasswordJFrame = new UpdatePasswordJFrame(operator.getId());
		updatePasswordJFrame.setVisible(true);

	}

	private void jItem6_2Click(ActionEvent e) {

		AdminOperatorFrame adminOperatorFrame = new AdminOperatorFrame();
		adminOperatorFrame.setVisible(true);
	}

}
