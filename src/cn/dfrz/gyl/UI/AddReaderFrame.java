package cn.dfrz.gyl.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import cn.dfrz.gyl.model.Reader;
import cn.dfrz.gyl.service.ReaderService;
import cn.dfrz.gyl.serviceimpl.FactoryService;
import cn.dfrz.gyl.utils.JFrameUtils;

/**
 * @Decription 增加阅读者的界面
 *
 */
public class AddReaderFrame extends JFrame {


	private static final long serialVersionUID = 1L;
	private Font font = new Font("宋体", Font.BOLD, 20);
	private ReaderFrame readerFrame = null;
	private ReaderService readerService = FactoryService.getReaderService();
	private JPanel jpanel;
	private JButton commit;
	private JButton reset;
	
	private JLabel name;
	private JLabel ISBN;
	private JLabel sex;
	private JLabel age;
	private JLabel identityCard;
	private JLabel date;
	private JLabel dateTail;
	private JLabel maxNum;
	private JLabel tel;
	private JLabel keepMoney;
	private JLabel zj;
	private JLabel zy;
	private JLabel title;

	private JTextField addName;
	private JTextField addISBN;
	
	boolean sex_ = false;
	private ButtonGroup sexGroup;
	private JRadioButton sex1;
	private JRadioButton sex2;
	
	private JTextField addAge;
	private JTextField addIdentityCard;
	private JTextField addDate;
	private JTextField addMaxNum;
	private JTextField addTel;
	private JTextField addKeepMoney;
	private JTextField addZj;
	private JTextField addZy;

	public AddReaderFrame(ReaderFrame readerFrame) {
		initView();
		this.readerFrame = readerFrame;
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
		addName.setText("");
		addISBN.setText("");
		addAge.setText("");
		addDate.setText("");
		addIdentityCard.setText("");
		addMaxNum.setText("");
		addTel.setText("");
		addKeepMoney.setText("");
		addZj.setText("");
		addZy.setText("");
	}

	private void commit_Click(ActionEvent e) {
		String name_ = addName.getText().trim();
		String ISBN_ = addISBN.getText().trim();
		String age_= addAge.getText().trim();
		String date_= addDate.getText().trim();
		String identityCard_= addIdentityCard.getText().trim();
		String maxNum_= addMaxNum.getText().trim();
		String tel_= addTel.getText().trim();
		String keepMoney_= addKeepMoney.getText().trim();
		String zj_= addZj.getText().trim();
		String zy_= addZy.getText().trim();

		if (JFrameUtils.StringArrAnyIsEmpty(name_,ISBN_,age_,date_,identityCard_,maxNum_,tel_,keepMoney_,zj_,zy_)) {
			JOptionPane.showMessageDialog(null, "请输入完整信息!");
			return;
		}
		if (readerService.queryByISBN(ISBN_) != null) {
			JOptionPane.showMessageDialog(null, "您输入的读者编号已存在!");
			return;
		}
		
		int ages = JFrameUtils.checkNum(age_);
		if(ages < 0) {
			JOptionPane.showMessageDialog(null, "年龄请输入正整数!");
			return;
		}
		
		int maxNums = JFrameUtils.checkNum(maxNum_);
		if (maxNums < 0) {
			JOptionPane.showMessageDialog(null, "最大借书量请输入正数!");
			return;
		}
		
		int zjs = JFrameUtils.checkNum(zj_);
		if (zjs < 0) {
			JOptionPane.showMessageDialog(null, "证件类型请输入正数!");
			return;
		}
		
		double keepMoneys = JFrameUtils.checkDoubleNum(keepMoney_);
		if (keepMoneys < 0) {
			JOptionPane.showMessageDialog(null, "押金请输入正数!");
			return;
		}
		
		int dates = JFrameUtils.checkNum(date_);
		if(dates < 0) {
			JOptionPane.showMessageDialog(null, "会员天数请输入正数!");
			return;
		}
		Reader reader = new Reader();
		reader.setName(name_);
		reader.setISBN(ISBN_);
		reader.setSex(sex_);
		reader.setAge(ages);
		//会员有效期计算,并注入对象属性
		reader.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()+dates*24*60*60*1000)));
		reader.setIdentityCard(identityCard_);
		reader.setMaxNum(maxNums);
		reader.setTel(tel_);
		reader.setKeepMoney(new BigDecimal(keepMoneys));
		reader.setZj(zjs);
		reader.setZy(zy_);
		reader.setBztime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
		
		if (readerService.insert(reader) > 0) {
			JOptionPane.showMessageDialog(null, "添加成功!");
		} else {
			JOptionPane.showMessageDialog(null, "出现异常,请重新添加!");
			return;
		}
		this.setVisible(false);
		readerFrame.showListData(1);

	}

	public void initView() {
		int width = 450;
		int height = 800;
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

		
		addName = new JTextField();
		addName.setBounds(160, 50, 200, 30);
		jpanel.add(addName);
		name = new JLabel("读者名字:");
		name.setFont(new Font("宋体", Font.BOLD, 20));
		name.setBounds(20, 45, 140, 40);
		jpanel.add(name);

		addISBN = new JTextField();
		addISBN.setBounds(160, 100, 200, 30);
		jpanel.add(addISBN);
		ISBN = new JLabel("读者编号:");
		ISBN.setFont(new Font("宋体", Font.BOLD, 20));
		ISBN.setBounds(20, 95, 140, 40);
		jpanel.add(ISBN);
		
		
		sex = new JLabel("性别");
		sex.setFont(new Font("宋体", Font.BOLD, 20));
		sex.setBounds(20, 145, 140, 40);
		jpanel.add(sex);
		sex1 = new JRadioButton("男", false);
		sex2 = new JRadioButton("女", true);
		sex1.setFont(font);
		sex2.setFont(font);
		sex1.setBounds(160, 150, 50, 30);
		sex2.setBounds(300, 150, 50, 30);
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
		
		addAge = new JTextField();
		addAge.setBounds(160, 200, 200, 30);
		jpanel.add(addAge);
		age = new JLabel("年龄:");
		age.setFont(new Font("宋体", Font.BOLD, 20));
		age.setBounds(20, 195, 140, 40);
		jpanel.add(age);
		
		
		addDate = new JTextField();
		addDate.setBounds(160, 250, 200, 30);
		addDate.setSelectedTextColor(Color.blue);
		jpanel.add(addDate);
		date = new JLabel("会员天数:");
		date.setFont(new Font("宋体", Font.BOLD, 20));
		date.setBounds(20, 245, 140, 40);
		jpanel.add(date);
		dateTail = new JLabel("天");
		dateTail.setFont(new Font("宋体", Font.BOLD, 20));
		dateTail.setBounds(370, 245, 50, 40);
		jpanel.add(dateTail);

		addIdentityCard = new JTextField();
		addIdentityCard.setBounds(160, 300, 200, 30);
		jpanel.add(addIdentityCard);
		identityCard = new JLabel("身份卡号:");
		identityCard.setFont(new Font("宋体", Font.BOLD, 20));
		identityCard.setBounds(20, 295, 140, 40);
		jpanel.add(identityCard);
		
		addMaxNum = new JTextField();
		addMaxNum.setBounds(160, 350, 200, 30);
		jpanel.add(addMaxNum);
		maxNum = new JLabel("最大借阅量:");
		maxNum.setFont(new Font("宋体", Font.BOLD, 20));
		maxNum.setBounds(20, 345, 140, 40);
		jpanel.add(maxNum);

		addTel = new JTextField();
		addTel.setBounds(160, 400, 200, 30);
		jpanel.add(addTel);
		tel = new JLabel("电话号码:");
		tel.setFont(new Font("宋体", Font.BOLD, 20));
		tel.setBounds(20, 395, 140, 40);
		jpanel.add(tel);
		
		addKeepMoney = new JTextField();
		addKeepMoney.setBounds(160, 450, 200, 30);
		jpanel.add(addKeepMoney);
		keepMoney = new JLabel("押金:");
		keepMoney.setFont(new Font("宋体", Font.BOLD, 20));
		keepMoney.setBounds(20, 445, 140, 40);
		jpanel.add(keepMoney);
		
		addZj = new JTextField();
		addZj.setBounds(160, 500, 200, 30);
		jpanel.add(addZj);
		zj = new JLabel("证件类型:");
		zj.setFont(new Font("宋体", Font.BOLD, 20));
		zj.setBounds(20, 495, 140, 40);
		jpanel.add(zj);
		
		addZy = new JTextField();
		addZy.setBounds(160, 550, 200, 30);
		jpanel.add(addZy);
		zy = new JLabel("职业:");
		zy.setFont(new Font("宋体", Font.BOLD, 20));
		zy.setBounds(20, 545, 140, 40);
		jpanel.add(zy);

		title = new JLabel("新读者信息录入");
		title.setFont(new Font("楷体", Font.BOLD, 30));
		title.setBounds(100, 10, 500, 30);
		jpanel.add(title);

		reset = new JButton("重置");
		reset.setBounds(230, 655, 110, 40);
		jpanel.add(reset);

		commit = new JButton("提交");
		commit.setBounds(70, 655, 110, 40);
		jpanel.add(commit);
		this.getRootPane().setDefaultButton(commit);
	}
}


