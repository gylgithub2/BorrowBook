
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

import cn.dfrz.gyl.model.Operator;
import cn.dfrz.gyl.service.OperatorService;
import cn.dfrz.gyl.serviceimpl.FactoryService;
import cn.dfrz.gyl.utils.JFrameUtils;

/**
 * @Decription 修改密码模块
 */
public class UpdatePasswordJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private OperatorService operatorService = FactoryService.getOperatorService();
	private JPanel jpanel;
	private JButton commit;
	private Integer id;
	private JLabel oldPassword;
	private JLabel newPassword;
	private JLabel checkPassword;
	private JLabel title;
	private JTextField addOldPassword;
	private JTextField addNewPassword;
	private JTextField addCheckPassword;

	public UpdatePasswordJFrame(int id) {
		this.id = id;
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
	this.setTitle("修改密码");
	// 可变大小
	this.setResizable(false);
	jpanel = new JPanel();
	jpanel.setVisible(true);
	// 布局
	jpanel.setLayout(null);
	// 添加到Jframe
	this.setContentPane(jpanel);
	addOldPassword = new JTextField();
	addOldPassword.setBounds(140, 100, 200, 30);
	jpanel.add(addOldPassword);
	oldPassword = new JLabel("旧密码:");
	oldPassword.setFont(new Font("宋体", Font.BOLD, 20));
	oldPassword.setBounds(30, 95, 110, 40);
	jpanel.add(oldPassword);

	addNewPassword = new JTextField();
	addNewPassword.setBounds(140, 150, 200, 30);
	jpanel.add(addNewPassword);
	newPassword = new JLabel("新密码:");
	newPassword.setFont(new Font("宋体", Font.BOLD, 20));
	newPassword.setBounds(30, 145, 110, 40);
	jpanel.add(newPassword);

	addCheckPassword = new JTextField();
	addCheckPassword.setBounds(140, 200, 200, 30);
	jpanel.add(addCheckPassword);
	checkPassword = new JLabel("确认密码:");
	checkPassword.setFont(new Font("宋体", Font.BOLD, 20));
	checkPassword.setBounds(30, 195, 150, 40);
	jpanel.add(checkPassword);

	title = new JLabel("修改密码");
	title.setFont(new Font("楷体", Font.BOLD, 30));
	title.setBounds(100, 20, 500, 40);
	jpanel.add(title);

	commit = new JButton("提交");
	commit.setBounds(150, 235, 100, 40);
	jpanel.add(commit);
	this.getRootPane().setDefaultButton(commit);
}
	private void commit_Click(ActionEvent e) {
		String oldPassword_ = addOldPassword.getText().trim();
		String newPassword_ = addNewPassword.getText().trim();
		String checkPassword = addCheckPassword.getText().trim();
		
		Operator operator  =operatorService.queryById(id);

		if (JFrameUtils.StringArrAnyIsEmpty(oldPassword_,newPassword_,checkPassword)){
			JOptionPane.showMessageDialog(null, "请输入所有信息!");
			return;
		}
		
		if(!JFrameUtils.checkPasswordStandard(newPassword_)) {
			JOptionPane.showMessageDialog(null, "密码不符合规范，应该同时包含字母数字，且长度大于8");
			return;
		}
		
		if (!oldPassword_.equals(operator.getPassword())) {
			JOptionPane.showMessageDialog(null, "您输入的旧密码不正确");
			return;
		}
		if (!newPassword_.equals(checkPassword)) {
			JOptionPane.showMessageDialog(null, "您输入的新密码和旧密码不同!");
			return;
		}
		operator.setPassword(newPassword_);
		operatorService.update(operator);
		JOptionPane.showMessageDialog(null, "密码修改成功");
		this.setVisible(false);
	}

}
