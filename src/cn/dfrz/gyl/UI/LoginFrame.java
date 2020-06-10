
package cn.dfrz.gyl.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cn.dfrz.gyl.model.Operator;
import cn.dfrz.gyl.service.OperatorService;
import cn.dfrz.gyl.serviceimpl.OperatorServiceimpl;
import cn.dfrz.gyl.utils.JFrameUtils;

/**
 * 
 * @Decription 登陆界面
 */
public class LoginFrame extends JFrame {
	private static final long serialVersionUID = 1456295784844L;
	private OperatorService operatorService = new OperatorServiceimpl();
	private JPanel jpanel;
	private JLabel lblTitle;
	private JLabel lblLoginName;
	private JLabel lblLoginPassword;
	// 输入框
	private JTextField txtLoginName;
	private JPasswordField txtLoginPassword;
	private JButton btnLogin;
	private JButton btnReset;
	// 首次登录
	private String adminName;

	public LoginFrame() {
		this.adminName = getName();
		initUI();
		bindEvent();
	}

	
	/**
	 * 
	 * @Decription 登陆上次登录保存的账号名
	 * 
	 */
	public String getName() {
		File file = new File(JFrameUtils.getTemporaryFileLocation() + "\\adminName.dat");
		if(!file.exists()) {
			return "";
		}
		ObjectInputStream ois = null;
		String name = "";
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			name = (String) ois.readObject();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return name;
	}

	/**
	 * 
	 * @Decription 登陆成功保存输入的账号名
	 * 
	 */
	public void loadName(String adminName) {
		ObjectOutputStream oos = null;
		File file = new File(JFrameUtils.getTemporaryFileLocation() + "\\adminName.dat");
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(adminName);
			oos.flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	// 界面
	public void initUI() {
		int width = 800;
		int height = 665;
		// 大小
		this.setSize(width, height);
		// 屏幕居中
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
		this.setLocation(x, y);
		// 标题
		this.setTitle("账号登录");
		// 可变大小
		this.setResizable(false);
		jpanel = new JPanel();
		jpanel.setVisible(true);
		// 布局
		jpanel.setLayout(null);
		// 添加到Jframe
		this.setContentPane(jpanel);
		// JPanel添加组件
		lblTitle = new JLabel();
		lblTitle.setText("图书管理");
		lblTitle.setForeground(Color.blue);
		lblTitle.setBounds(280, 50, 400, 200);
		lblTitle.setFont(new Font("楷体", Font.BOLD, 60));
		jpanel.add(lblTitle);

		lblLoginName = new JLabel();
		lblLoginName.setText("账号:");
		lblLoginName.setBounds(200, 200, 200, 200);
		lblLoginName.setFont(new Font("黑体", Font.BOLD, 20));
		jpanel.add(lblLoginName);

		txtLoginName = new JTextField(adminName);
		txtLoginName.setBounds(270, 285, 300, 30);
		jpanel.add(txtLoginName);

		lblLoginPassword = new JLabel();
		lblLoginPassword.setText("密码:");
		lblLoginPassword.setBounds(200, 250, 200, 200);
		lblLoginPassword.setFont(new Font("黑体", Font.BOLD, 20));
		jpanel.add(lblLoginPassword);

		txtLoginPassword = new JPasswordField();
		txtLoginPassword.setBounds(270, 335, 300, 30);
		jpanel.add(txtLoginPassword);

		btnLogin = new JButton();
		btnLogin.setText("登录");
		btnLogin.setOpaque(false);
		btnLogin.setBounds(200, 420, 100, 50);
		jpanel.add(btnLogin);
		btnReset = new JButton();
		btnReset.setText("重置");
		btnReset.setBounds(500, 420, 100, 50);
		jpanel.add(btnReset);

		this.getRootPane().setDefaultButton(btnLogin);
		JLabel bglabel = new JLabel();// 创建JLabel
		getBackgroundPicture(bglabel, jpanel);// 方法体见下面代码块
		this.getLayeredPane().add(bglabel, new Integer(Integer.MIN_VALUE));
	}

	public void getBackgroundPicture(JLabel bglabel, JPanel jpanel) {
		String path = JFrameUtils.getBackground();// 获取路径
		ImageIcon background = new ImageIcon(path);
		bglabel.setIcon(background);// 将图片设置到Jlabel中
		bglabel.setBounds(0, -50, background.getIconWidth(), background.getIconHeight());// 设图片显示的区域
		jpanel.setOpaque(false);// 设置面板为透明，在gbLabel之上的JPanel都要设置为透明
	}

	public void bindEvent() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnLoginClick(e);
			}
		});
		btnReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnResetClick(e);
			}
		});
	}

	@SuppressWarnings("deprecation")
	public void btnLoginClick(ActionEvent e) {
		String loginName = txtLoginName.getText().trim();
		String loginPassword = txtLoginPassword.getText().trim();

		if (JFrameUtils.StringArrAnyIsEmpty(loginName)) {
			JOptionPane.showMessageDialog(null, "请输入账号!");
			return;
		}
		if (JFrameUtils.StringArrAnyIsEmpty(loginPassword)) {
			JOptionPane.showMessageDialog(null, "请输入密码!");
			return;
		}
		int login = operatorService.loginOperator(loginName, loginPassword);
		Operator operator = operatorService.queryByName(loginName);
		if (login == 1) {
			this.setVisible(false);
			AdminFrame adminFrame = new AdminFrame(operator);
			adminFrame.setVisible(true);
		} else if (login == 0) {
			this.setVisible(false);
			OperatorFrame operatorFrame = new OperatorFrame(operator.getId(), 0);
			operatorFrame.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "检查账号和密码是否正确!");
			return;
		}
		//判断账号是否和之前的一致,不一致重新记录当前账号为默认账号
		if (!adminName.equals(loginName)) {
			loadName(loginName);
		}

	}

	public void btnResetClick(ActionEvent e) {
		txtLoginName.setText("");
		txtLoginPassword.setText("");
	}
}
