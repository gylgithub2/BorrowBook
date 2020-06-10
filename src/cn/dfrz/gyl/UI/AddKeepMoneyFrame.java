package cn.dfrz.gyl.UI;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.dfrz.gyl.model.Reader;
import cn.dfrz.gyl.service.ReaderService;
import cn.dfrz.gyl.serviceimpl.FactoryService;
import cn.dfrz.gyl.utils.JFrameUtils;

/**
 * @Decription 交押金界面
 */
public class AddKeepMoneyFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private Reader reader= null;
	private ReaderService readerService = FactoryService.getReaderService();
	private JPanel jpanel;
	private JButton commit;
	private double money;
	

	private JLabel keepMoney;
	private JLabel title;

	private JTextField addKeepMoney;
	

	/**
	 * 
	 * @param reader 正在还书的读者
	 * @param money 正在还书的读者用押金抵扣后,还剩余的付款数额,
	 */
	public AddKeepMoneyFrame(Reader reader,double money) {
		initView();
		this.reader = reader;
		this.money = money;
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

/**
 * 
 * @Decription 缴纳罚款,缴纳金额+原有押金至少应当等于罚款金额
 */
	private void commit_Click(ActionEvent e) {

		String keepMoney_= addKeepMoney.getText().trim();

		if (JFrameUtils.StringArrAnyIsEmpty(keepMoney_)) {
			JOptionPane.showMessageDialog(null, "请输入缴费金额!");
			return;
		}
		double keepMoneys = JFrameUtils.checkDoubleNum(keepMoney_);
		if(keepMoneys<0) {
			JOptionPane.showMessageDialog(null, "缴费金额应该为大于0的数字");
			return;
		}
		if (keepMoneys < money) {
			JOptionPane.showMessageDialog(null, "押金至少缴纳:"+money+"才能付清罚款");
			return;
		}
		
		//修改数据库读者信息
		reader.setKeepMoney(new BigDecimal(keepMoneys+ reader.getKeepMoney().doubleValue()));

		if (readerService.update(reader) > 0) {
			JOptionPane.showMessageDialog(null, "添加成功!");
		} else {
			JOptionPane.showMessageDialog(null, "出现异常,请重新添加!");
			return;
		}
		this.setVisible(false);

	}

	public void initView() {
		int width = 400;
		int height = 200;
		// 大小
		this.setSize(width, height);
		// 屏幕居中
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
		this.setLocation(x, y);
		// 标题
		this.setTitle("押金缴纳");
		// 可变大小
		this.setResizable(false);
		jpanel = new JPanel();
		jpanel.setVisible(true);
		// 布局
		jpanel.setLayout(null);
		// 添加到Jframe
		this.setContentPane(jpanel);

		
		addKeepMoney = new JTextField();
		addKeepMoney.setBounds(160, 50, 200, 30);
		jpanel.add(addKeepMoney);
		keepMoney = new JLabel("缴纳金额:");
		keepMoney.setFont(new Font("宋体", Font.BOLD, 20));
		keepMoney.setBounds(20, 45, 140, 40);
		jpanel.add(keepMoney);
		

		title = new JLabel("押金缴纳");
		title.setFont(new Font("楷体", Font.BOLD, 30));
		title.setBounds(140, 10, 500, 30);
		jpanel.add(title);
		
		commit = new JButton("提交");
		commit.setBounds(140, 90, 110, 40);
		jpanel.add(commit);
		this.getRootPane().setDefaultButton(commit);
	}

}


