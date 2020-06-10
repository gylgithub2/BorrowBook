package cn.dfrz.gyl.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import cn.dfrz.gyl.model.Reader;
import cn.dfrz.gyl.service.ReaderService;
import cn.dfrz.gyl.serviceimpl.FactoryService;
import cn.dfrz.gyl.utils.JFrameUtils;

/**
 * @Decription 读者信息界面
 */
public class ReaderFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private ReaderService readerService = FactoryService.getReaderService();
	private UpdateReaderFrame updateReaderFrame = null;
	private JPanel jpanel;
	private JLabel lblTitle;
	private JLabel lab;
	private JTable jtable;
	private JScrollPane jspa = null;
	private JButton addReader;
	
	private JButton delReader;
	private JButton updReader;
	private JButton refresh;
	
	// 模糊查询组件
	private JButton dimQuery;
	private JTextField dimQueryText;


	private int pageIndex = 1;;
	private JButton nextPage;
	private JButton prePage;

	public ReaderFrame() {
		initUI();
		bindEvent();
	}

	public void initUI() {
		int width = 1500;
		int height = 800;
		// 大小
		this.setSize(width, height);
		// 屏幕居中
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
		this.setLocation(x, y);
		// 标题
		this.setTitle("读者信息管理");
		// 可变大小
		this.setResizable(false);
		jpanel = new JPanel();
		jpanel.setVisible(true);
		// 布局
		jpanel.setLayout(null);
		// 添加到Jframe
		this.setContentPane(jpanel);

		lab = new JLabel();
		lab.setText("读者信息");
		lab.setFont(new Font("宋体", Font.BOLD, 15));

		lblTitle = new JLabel();
		lblTitle.setText("读者信息");
		lblTitle.setForeground(Color.red);
		lblTitle.setBounds(650, 15, 200, 100);
		lblTitle.setFont(new Font("楷体", Font.BOLD, 40));
		jpanel.add(lblTitle);

		nextPage = new JButton("下一页");
		nextPage.setFont(new Font("宋体", Font.PLAIN, 18));
		nextPage.setBounds(1280, 620, 120, 30);
		jpanel.add(nextPage);

		prePage = new JButton("上一页");
		prePage.setFont(new Font("宋体", Font.PLAIN, 18));
		prePage.setBounds(110, 620, 120, 30);
		jpanel.add(prePage);

		// 刷新界面使用showlistdata
		inittableUi();
		jtable.setFont(new Font("宋体", Font.PLAIN, 18));// 表格字体
		jtable.setRowHeight(20);// 行宽

		updReader = new JButton("修改");
		updReader.setFont(new Font("宋体", Font.PLAIN, 18));
		updReader.setBounds(200, 680, 80, 40);
		jpanel.add(updReader);

		delReader = new JButton("删除");
		delReader.setBounds(500, 680, 80, 40);
		delReader.setFont(new Font("宋体", Font.PLAIN, 18));
		jpanel.add(delReader);

		dimQuery = new JButton("关键字查询");
		dimQuery.setFont(new Font("宋体", Font.PLAIN, 18));
		dimQuery.setBounds(680, 620, 140, 30);
		jpanel.add(dimQuery);
		dimQueryText = new JTextField();
		dimQueryText.setBounds(830, 620, 120, 30);
		jpanel.add(dimQueryText);

		
		addReader = new JButton("添加");
		addReader.setBounds(800, 680, 80, 40);
		addReader.setFont(new Font("宋体", Font.PLAIN, 18));
		jpanel.add(addReader);

		refresh = new JButton("刷新");
		refresh.setBounds(1100, 680, 80, 40);
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

	// table初始化
	private void inittableUi() {
		jtable = new JTable();
		jspa = new JScrollPane(jtable);
		jspa.setBounds(50, 100, 1400, 500);
		jpanel.add(jspa);
		showListData(1);
	}

	// 事件绑定区
	public void bindEvent() {
		addReader.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addReader_Click();
			}

		});
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				refresh_Click();
			}

		});
		dimQuery.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dimQuery_Click();
			}

		});
		delReader.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				delReader_Click();
			}
		});
		updReader.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				updReader_Click();
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

	// 添加按钮
	public void addReader_Click() {
		AddReaderFrame addReaderFrame = new AddReaderFrame(this);
		addReaderFrame.setVisible(true);
	}
	/**
	 * @Decription 模糊查询
	 */
	private void dimQuery_Click() {
		String dimString = dimQueryText.getText().trim();
		List<Reader> dimBookList = readerService.dimQuery(dimString);
		// list数据传给表格
		DefaultTableModel tableModel = getDefaultTableModel(dimBookList);
		jtable.setModel(tableModel);
	}

	// 批量删除选择的所有行
	private void delReader_Click() {
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
			obj[i] = model.getValueAt(index[i], 1);
		}
		String[] pk = new String[obj.length];
		for (int i = 0; i < pk.length; i++) {
			pk[i] = obj[i] + "";
		}
		int count = 0;
		for (int i = 0; i < pk.length; i++) {
			int k = readerService.delete(pk[i]);
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

	public void shows() {
		new ReaderFrame().setVisible(true);
		this.setVisible(false);
	}

	private void refresh_Click() {
		shows();
	}

	private void updReader_Click() {
		Reader reader = getRowData();
		if (reader != null) {
			updateReaderFrame = new UpdateReaderFrame(this, reader);
			updateReaderFrame.setVisible(true);
		} else {
			// 如果没有选中任何行，提示用户
			JOptionPane.showMessageDialog(null, "请选中需要修改的学生信息");
			return;
		}
	}

	// 获取当前选择的学生对象
	public Reader getRowData() {
		Reader reader = null;
		int index = jtable.getSelectedRow();
		// 如果下标不为-1，则选中行为数据行
		if (index != -1) {
			// 取得表格对象的数据模型
			TableModel model = jtable.getModel();
			// 在表格对象模型中，根据选中的行和列，获取相应的数据值,(index,0)对应第一列的学号
			String name = model.getValueAt(index, 0) + "";
			String ISBN = model.getValueAt(index, 1) + "";
			boolean sex = (model.getValueAt(index, 2) + "").equals("男");
			int age = Integer.parseInt(model.getValueAt(index, 3) + "");
			String IdentityCard = model.getValueAt(index, 4) + "";
			String date = model.getValueAt(index, 5) + "";
			int maxNum = Integer.parseInt(model.getValueAt(index, 6) + "");
			String tel = model.getValueAt(index, 7) + "";
			BigDecimal keepMoney = new BigDecimal(Double.parseDouble(model.getValueAt(index, 8) + ""));
			int zj = Integer.parseInt((model.getValueAt(index, 9) + "").equals("学生")?0+"":1+"");
			String zy = model.getValueAt(index, 10) + "";
			String bztime = model.getValueAt(index, 11) + "";
			reader = new Reader(name, ISBN, sex, age, IdentityCard, date, maxNum, tel, keepMoney, zj, zy, bztime);
		}
		// 如果没有选中任何一行 就意味着student为null 可以提供给外部判断
		return reader;
	}

	public void showListData(int pageIndex) {
		List<Reader> list = null;
		list = readerService.queryAllInPage(pageIndex);
		if (list.size() < 1) {
			this.pageIndex--;
			return;
		}
		// list数据传给表格
		DefaultTableModel tableModel = getDefaultTableModel(list);
		jtable.setModel(tableModel);
	}

	public DefaultTableModel getDefaultTableModel(List<Reader> list) {
		Vector<String> col = new Vector<String>();
		col.add("读者名字");
		col.add("编号");
		col.add("性别");
		col.add("年龄");
		col.add("身份卡号");
		col.add("会员到期时间");
		col.add("最大借书数");
		col.add("电话号码");
		col.add("押金");
		col.add("证件类型");
		col.add("职业");
		col.add("办证日期");
		Vector<Object> data = new Vector<>();
		for (Reader Reader : list) {
			Vector<Object> v = new Vector<Object>();
			v.add(Reader.getName());
			v.add(Reader.getISBN());
			v.add(Reader.getSex() ? "男" : "女");
			v.add(Reader.getAge());
			v.add(Reader.getIdentityCard());
			v.add(Reader.getDate());
			v.add(Reader.getMaxNum());
			v.add(Reader.getTel());
			v.add(Reader.getKeepMoney());
			v.add(Reader.getZj()==0?"学生":"身份证");
			v.add(Reader.getZy());

			v.add(Reader.getBztime());

			data.add(v);
		}
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(data, col);
		return dm;
	}
}
