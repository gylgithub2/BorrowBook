package cn.dfrz.gyl.UI;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import cn.dfrz.gyl.model.Operator;
import cn.dfrz.gyl.service.OperatorService;
import cn.dfrz.gyl.serviceimpl.FactoryService;
import cn.dfrz.gyl.utils.JFrameUtils;

/**
 * @Decription 更新操作员的界面 
 */
public class UpdateOperatorFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private Font font = new Font("宋体", Font.BOLD, 20);
	private AdminOperatorFrame operatorFrame = null;
	private OperatorService operatorService = FactoryService.getOperatorService();
	private JPanel jpanel;
	private JButton commit;
	private JButton reset;
	private JLabel ID;
	private JLabel name;
	private JLabel sex;
	private JLabel age;
	private JLabel identityCard;
	private JLabel workDate;
	private JLabel tel;
	private JLabel admin;
	private JLabel password;
	private JLabel title;

	private JTextField addID;
	private JTextField addName;
	boolean sex_;
	private ButtonGroup sexGroup;
	private JRadioButton sex1;
	private JRadioButton sex2;
	
	boolean isAdmin;
	private ButtonGroup adminGroup;
	private JRadioButton admin1;
	private JRadioButton admin2;
	
	private JTextField addAge;
	private JTextField addIdentityCard;
	private JTextField addWorkDate;
	private JTextField addTel;
	private JTextField addPassword;
	private Integer kpID;

	public UpdateOperatorFrame(AdminOperatorFrame operatorFrame,Operator operator) {
		sex_ = operator.getSex();
		isAdmin= operator.getIsAdmin();
		this.operatorFrame = operatorFrame;
		initView();
		loadData(operator);
		bindEvent();
	}
	public void loadData(Operator operator) {
		this.kpID = operator.getId();
		addID.setText(kpID+"");
		addName.setText(operator.getName());
		addAge.setText(operator.getAge()+"");
		addIdentityCard.setText(operator.getIdentityCard());
		addWorkDate.setText(operator.getWorkDate());
		addTel.setText(operator.getTel());
		addPassword.setText(operator.getPassword());
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
		addName.setText("");
		addAge.setText("");
		addIdentityCard.setText("");
		addWorkDate.setText("");
		addTel.setText("");
		addPassword.setText("");

	}
	//提交
	private void commit_Click(ActionEvent e) {

		String ID_ = addID.getText().trim();
		String name_ = addName.getText().trim();
		String age_= addAge.getText().trim();
		String identityCard_= addIdentityCard.getText().trim();
		String workDate_= addWorkDate.getText().trim();
		String tel_= addTel.getText().trim();
		String password_= addPassword.getText().trim();

		if (JFrameUtils.StringArrAnyIsEmpty(ID_,name_,age_,identityCard_,workDate_,tel_,password_)) {
			JOptionPane.showMessageDialog(null, "请输入完整信息!");
			return;
		}
		int ages = JFrameUtils.checkNum(age_);
		if (ages < 0) {
			JOptionPane.showMessageDialog(null, "年龄请输入正整数!");
			return;
		}

		
		if(!JFrameUtils.checkPhoneNum(tel_)) {
			JOptionPane.showMessageDialog(null, "无效电话号码!");
			return;
		}
		
		Operator operator = new Operator(kpID,name_,sex_,ages,identityCard_,workDate_,tel_,isAdmin,password_);
		
		if (operatorService.update(operator) > 0) {
			JOptionPane.showMessageDialog(null, "修改成功!");
		} else {
			JOptionPane.showMessageDialog(null, "出现异常,请重新添加!");
			return;
		}
		this.setVisible(false);
		operatorFrame.showListData(1);

	}

	public void initView() {
		int width = 480;
		int height = 700;
		// 大小
		this.setSize(width, height);
		// 屏幕居中
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
		this.setLocation(x, y);
		// 标题
		this.setTitle("修改操作员信息");
		// 可变大小
		this.setResizable(false);
		jpanel = new JPanel();
		jpanel.setVisible(true);
		// 布局
		jpanel.setLayout(null);
		// 添加到Jframe
		this.setContentPane(jpanel);

		addID = new JTextField(kpID+"");
		addID.setBounds(160, 100, 200, 30);
		addID.setEditable(false);
		jpanel.add(addID);
		ID = new JLabel("操作员编号:");
		ID.setFont(font);
		ID.setBounds(20, 95, 140, 40);
		jpanel.add(ID);

		addName = new JTextField();
		addName.setBounds(160, 150, 200, 30);
		jpanel.add(addName);
		name = new JLabel("名字:");
		name.setFont(font);
		name.setBounds(20, 145, 140, 40);
		jpanel.add(name);

		sex = new JLabel("性别:");
		sex.setFont(font);
		sex.setBounds(20, 195, 140, 40);
		jpanel.add(sex);
		// 创建单选按钮，并且默认已选中
		sex1 = new JRadioButton("男", sex_);
		sex2 = new JRadioButton("女", !sex_);
		sex1.setFont(font);
		sex2.setFont(font);
		sex1.setBounds(160, 200, 50, 30);
		sex2.setBounds(300, 200, 50, 30);
		jpanel.add(sex1); // 在顶部面板上添加单选按钮
		jpanel.add(sex2); // 在顶部面板上添加单选按钮
		sexGroup = new ButtonGroup(); // 创建一个按钮小组
		sexGroup.add(sex1); // 把单选按钮1加入到按钮小组
		sexGroup.add(sex2); // 把单选按钮2加入到按钮小组
		
		sex1.addItemListener(new ItemListener() { // 给单选按钮添加一个点击监听器
			public void itemStateChanged(ItemEvent e) { // 单选按钮被选中
				sex_ = true;
			}
		});
		sex2.addItemListener(new ItemListener() { // 给单选按钮添加一个点击监听器
			public void itemStateChanged(ItemEvent e) { // 单选按钮被选中
				sex_ = false;
			}
		});
		
		addAge = new JTextField();
		addAge.setBounds(160, 250, 200, 30);
		jpanel.add(addAge);
		age = new JLabel("年龄:");
		age.setFont(font);
		age.setBounds(20, 245, 140, 40);
		jpanel.add(age);
		
		addIdentityCard = new JTextField();
		addIdentityCard.setBounds(160, 300, 200, 30);
		jpanel.add(addIdentityCard);
		identityCard = new JLabel("证件号码:");
		identityCard.setFont(font);
		identityCard.setBounds(20, 295, 140, 40);
		jpanel.add(identityCard);
		
		
		addWorkDate = new JTextField();
		addWorkDate.setBounds(160, 350, 200, 30);
		jpanel.add(addWorkDate);
		workDate = new JLabel("工作时间:");
		workDate.setFont(font);
		workDate.setBounds(20, 345, 140, 40);
		jpanel.add(workDate);
		
		addTel = new JTextField();
		addTel.setBounds(160, 400, 200, 30);
		jpanel.add(addTel);
		tel = new JLabel("电话号码:");
		tel.setFont(font);
		tel.setBounds(20, 395, 140, 40);
		jpanel.add(tel);
		
		admin = new JLabel("是否为管理员:");
		admin.setFont(new Font("宋体", Font.BOLD, 20));
		admin.setBounds(20, 445, 140, 40);
		jpanel.add(admin);
		// 创建单选按钮，根据传过来的operator动态的进行设定
		admin1 = new JRadioButton("是", isAdmin);
		admin2 = new JRadioButton("否", !isAdmin);
		admin1.setFont(font);
		admin2.setFont(font);
		admin1.setBounds(160, 445, 50, 30);
		admin2.setBounds(300, 445, 50, 30);
		jpanel.add(admin1); // 在顶部面板上添加单选按钮
		jpanel.add(admin2); // 在顶部面板上添加单选按钮
		adminGroup = new ButtonGroup(); // 创建一个按钮小组
		adminGroup.add(admin1); // 把单选按钮1加入到按钮小组
		adminGroup.add(admin2); // 把单选按钮2加入到按钮小组
		admin1.addItemListener(new ItemListener() { // 给单选按钮添加一个点击监听器
			public void itemStateChanged(ItemEvent e) { // 单选按钮被选中
				isAdmin = true;
			}
		});
		admin2.addItemListener(new ItemListener() { // 给单选按钮添加一个点击监听器
			public void itemStateChanged(ItemEvent e) { // 单选按钮被选中
				isAdmin = false;
			}
		});
		
		addPassword = new JTextField();
		addPassword.setBounds(160, 500, 200, 30);
		jpanel.add(addPassword);
		password = new JLabel("用户密码:");
		password.setFont(font);
		password.setBounds(20, 495, 140, 40);
		jpanel.add(password);

		title = new JLabel("操作员信息修改");
		title.setFont(font);
		title.setBounds(100, 20, 500, 40);
		jpanel.add(title);

		reset = new JButton("重置");
		reset.setBounds(230, 555, 110, 40);
		jpanel.add(reset);

		commit = new JButton("提交");
		commit.setBounds(70, 555, 110, 40);
		jpanel.add(commit);
		this.getRootPane().setDefaultButton(commit);
	}
}
