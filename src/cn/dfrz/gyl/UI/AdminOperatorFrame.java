
package cn.dfrz.gyl.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import cn.dfrz.gyl.model.Operator;
import cn.dfrz.gyl.service.OperatorService;
import cn.dfrz.gyl.serviceimpl.FactoryService;
import cn.dfrz.gyl.utils.JFrameUtils;

/**
 * @Decription  超级管理员管理操作员界面
 */
public class AdminOperatorFrame extends JFrame {


	private static final long serialVersionUID = 1L;
	private OperatorService operatorService =FactoryService.getOperatorService();
	private UpdateOperatorFrame updateOperatorFrame = null;
	private JPanel jpanel;
	private JLabel lblTitle;
	private JTable jtable;
	private JScrollPane jspa = null;
	private JButton addOperator;
	private JButton delOperator;
	private JButton updOperator;
	private JButton refresh;
	
	private int pageIndex = 1;;
	private JButton nextPage;
	private JButton prePage;
	
	public AdminOperatorFrame() {
		initUI();
		bindEvent();
	}

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
		this.setTitle("操作员信息管理");
		// 可变大小
		this.setResizable(false);
		jpanel = new JPanel();
		jpanel.setVisible(true);
		// 布局
		jpanel.setLayout(null);
		// 添加到Jframe
		this.setContentPane(jpanel);
		
//		lab = new JLabel();
//		lab.setText("操作员信1息");
//		lab.setFont(new Font("宋体", Font.BOLD, 15));

		lblTitle = new JLabel();
		lblTitle.setText("操作员信息");
		lblTitle.setForeground(Color.red);
		lblTitle.setBounds(300, 15, 300, 100);
		lblTitle.setFont(new Font("楷体", Font.BOLD, 40));
		jpanel.add(lblTitle);
		

		nextPage = new JButton("下一页");
		nextPage.setFont(new Font("宋体", Font.PLAIN, 18));
		nextPage.setBounds(580, 510, 120, 30);
		jpanel.add(nextPage);

		prePage = new JButton("上一页");
		prePage.setFont(new Font("宋体", Font.PLAIN, 18));
		prePage.setBounds(110, 510, 120, 30);
		jpanel.add(prePage);

		// 刷新界面使用showlistdata
		inittableUi();
		
		jtable.setFont(new Font("宋体", Font.PLAIN, 18));// 表格字体
		jtable.setRowHeight(20);// 行宽


		updOperator = new JButton("修改");
		updOperator.setFont(new Font("宋体", Font.PLAIN, 18));
		updOperator.setBounds(150, 560, 80, 40);
		jpanel.add(updOperator);

		delOperator= new JButton("删除");
		delOperator.setBounds(290, 560, 80, 40);
		delOperator.setFont(new Font("宋体", Font.PLAIN, 18));
		jpanel.add(delOperator);

		addOperator = new JButton("添加");
		addOperator.setBounds(430, 560, 80, 40);
		addOperator.setFont(new Font("宋体", Font.PLAIN, 18));
		jpanel.add(addOperator);

		refresh = new JButton("刷新");
		refresh.setBounds(580, 560, 80, 40);
		refresh.setFont(new Font("宋体", Font.PLAIN, 18));
		jpanel.add(refresh);
		
		
		jtable.setFont(new Font("宋体", Font.PLAIN, 18));// 表格字体
		jtable.setRowHeight(20);// 行宽
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

	//设置默认table选择行
	public void setJtableSlet() {
		this.jtable.addRowSelectionInterval(jtable.getRowCount() - 1, jtable.getRowCount() - 1);
	}

	//table初始化
	private void inittableUi() {
		jtable = new JTable();
		jspa = new JScrollPane(jtable);
		jspa.setBounds(50, 100, 700, 400);
		jpanel.add(jspa);
		showListData(1);
	}

	//事件绑定区
	public void bindEvent() {
		addOperator.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addOperator_Click();
			}

		});
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				refresh_Click();
			}

		});
		delOperator.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				delOperator_Click();
			}
		});
		updOperator.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				updOperator_Click();
			}

		});
		nextPage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				nextPage_Click();
			}

		});
		prePage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				prePage_Click();
			}
		});
	}
	private void prePage_Click() {
		if (pageIndex > 1) {
			pageIndex--;
			showListData(pageIndex);
		}

	}

	private void nextPage_Click() {
		pageIndex++;
		showListData(pageIndex);

	}

	//添加按钮
	public void addOperator_Click() {
		AddOperatorFrame addOperatorFrame = new AddOperatorFrame(this);
		addOperatorFrame.setVisible(true);
	}

	// 批量删除选择的所有行
	private void delOperator_Click() {
		int[] index = jtable.getSelectedRows();
		if (index.length < 1) {
			JOptionPane.showMessageDialog(null, "没有选择行");
			return;
		}
		int key = JOptionPane.showConfirmDialog(null, "你确认删除当前选中的" + index.length + "行?", "确认删除",
				JOptionPane.YES_NO_OPTION);// key=0/1
		// 当前选中的所有行索引
		if (key == 1) {
			return;
		}

			TableModel model = jtable.getModel();
			Object[] obj = new Object[index.length];
			for (int i = 0; i < obj.length; i++) {
				obj[i] = model.getValueAt(index[i], 0);
			}
			int[] pk = new int[obj.length];
			for (int i = 0; i < pk.length; i++) {
				pk[i] = Integer.parseInt(obj[i] + "");
			}
			int count = 0;
			for (int i = 0; i < pk.length; i++) {
				int k = operatorService.delete(pk[i]);
				if (k >= 1) {
					count++;
				}
			}
			if (count == 1) {
				JOptionPane.showMessageDialog(null, "删除成功");
			} else if (count > 1) {
				JOptionPane.showMessageDialog(null, "删除成功" + count + "个" + "删除失败" + (pk.length - count) + "个");
			} else {
				JOptionPane.showMessageDialog(null, "删除失败!");
				return;
			}
		shows();
		if (jtable.getRowCount() - 1 > 0) {
			this.jtable.addRowSelectionInterval(jtable.getRowCount() - 1, jtable.getRowCount() - 1);
		}
	}

	private void shows() {
		this.setVisible(false);
		new AdminOperatorFrame().setVisible(true);;
	}

	private void refresh_Click() {
		shows();
	}
	private void updOperator_Click() {
		Operator operator = getRowData();
		if (operator != null) {
			updateOperatorFrame = new UpdateOperatorFrame(this,operator);
			updateOperatorFrame.setVisible(true);
		} else {
			// 如果没有选中任何行，提示用户
			JOptionPane.showMessageDialog(null, "请选中需要修改的管理员");
			return;
		}
	}

	public Operator getRowData() {
		Operator operator = null;
		int index = jtable.getSelectedRow();
		if (index != -1) {
			TableModel model = jtable.getModel();
			int Id = Integer.parseInt(model.getValueAt(index, 0) + "");
			String name = model.getValueAt(index, 1) + "";
			boolean sex = (model.getValueAt(index, 2)+"").trim().equals("男");
			int age = Integer.parseInt(model.getValueAt(index, 3) + "");
			String identityCard = model.getValueAt(index,4)+"";
			String workDate = model.getValueAt(index, 5)+"";
			String tel = model.getValueAt(index, 6)+"";
			boolean isAdmin = (model.getValueAt(index, 7)+"").trim().equals("是");
			String password = model.getValueAt(index, 8)+"";
			operator = new Operator(Id, name,sex,age,identityCard,workDate,tel,isAdmin,password);
		}
		return operator;
	}
	
	public void showListData(int pageIndex) {
		List<Operator> list = null;
		list = operatorService.queryAllInPage(pageIndex);
		if(list.size()<1) {
			this.pageIndex--;
			return;
		}
		// list数据传给表格
		DefaultTableModel tableModel = getDefaultTableModel(list);
		jtable.setModel(tableModel);
	}

	public DefaultTableModel getDefaultTableModel(List<Operator> list) {
		Vector<String> col = new Vector<String>();
		col.add("操作员编号");
		col.add("用户名");
		col.add("性别");
		col.add("年龄");
		col.add("证件号码");
		col.add("工作时间");
		col.add("电话号码");
		col.add("是否为管理员");
		col.add("密码");
		Vector<Object> data = new Vector<>();
		for (Operator operator : list) {
			Vector<Object> v = new Vector<Object>();
			v.add(operator.getId());
			v.add(operator.getName());
			v.add(operator.getSex()?"男":"女");
			v.add(operator.getAge());
			v.add(operator.getIdentityCard());
			v.add(operator.getWorkDate());
			v.add(operator.getTel());
			v.add(operator.getIsAdmin()?"是":"否");
			v.add(operator.getPassword());
			data.add(v);
		}
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(data, col);
		return dm;
	}

}
