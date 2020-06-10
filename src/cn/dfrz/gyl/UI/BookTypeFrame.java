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

import cn.dfrz.gyl.model.BookType;
import cn.dfrz.gyl.service.BookTypeService;
import cn.dfrz.gyl.serviceimpl.FactoryService;
import cn.dfrz.gyl.utils.JFrameUtils;

/**
 * @Decription 图书类别
 */
public class BookTypeFrame extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private BookTypeService bookTypeService = FactoryService.getBookTypeService();
	private UpdateBookTypeFrame updateBookTypeFrame = null;

	private JPanel jpanel;
	private JLabel lblTitle;
	private JTable jtable;
	private JScrollPane jspa = null;
	private JButton addBookType;
	private JButton delBookType;
	private JButton updBookType;
	private JButton refresh;
	
	private int pageIndex = 1;;
	private JButton nextPage;
	private JButton prePage;

	public BookTypeFrame() {
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
		this.setTitle("图书信息管理");
		// 可变大小
		this.setResizable(false);
		jpanel = new JPanel();
		jpanel.setVisible(true);
		// 布局
		jpanel.setLayout(null);
		// 添加到Jframe
		this.setContentPane(jpanel);

		lblTitle = new JLabel();
		lblTitle.setText("图书类别信息");
		lblTitle.setForeground(Color.red);
		lblTitle.setBounds(300, 15, 300, 100);
		lblTitle.setFont(new Font("楷体", Font.BOLD, 40));
		jpanel.add(lblTitle);

		// 刷新界面使用showlistdata
		inittableUi();
	

		nextPage = new JButton("下一页");
		nextPage.setFont(new Font("宋体", Font.PLAIN, 18));
		nextPage.setBounds(580, 510, 120, 30);
		jpanel.add(nextPage);

		prePage = new JButton("上一页");
		prePage.setFont(new Font("宋体", Font.PLAIN, 18));
		prePage.setBounds(110, 510, 120, 30);
		jpanel.add(prePage);

		updBookType = new JButton("修改");
		updBookType.setFont(new Font("宋体", Font.PLAIN, 18));
		updBookType.setBounds(150, 570, 80, 40);
		jpanel.add(updBookType);

		delBookType = new JButton("删除");
		delBookType.setBounds(290, 570, 80, 40);
		delBookType.setFont(new Font("宋体", Font.PLAIN, 18));
		jpanel.add(delBookType);

		addBookType = new JButton("添加");
		addBookType.setBounds(430, 570, 80, 40);
		addBookType.setFont(new Font("宋体", Font.PLAIN, 18));
		jpanel.add(addBookType);

		refresh = new JButton("刷新");
		refresh.setBounds(580, 570, 80, 40);
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

	// 事件绑定区
	public void bindEvent() {
		addBookType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addBookType_Click();
			}

		});
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				refresh_Click();
			}

		});
		delBookType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				delBookType_Click();
			}
		});
		updBookType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				updBookType_Click();
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
	public void addBookType_Click() {
		AddBookTypeFrame addBookTypeFrame = new AddBookTypeFrame(this);
		addBookTypeFrame.setVisible(true);
	}

	// 批量删除选择的所有行
	private void delBookType_Click() {
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
			int k = 0;
				k = bookTypeService.delete(pk[i]);
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
		// 当前选中行改为上一行
		if (jtable.getRowCount() - 1 > 0) {
			this.jtable.addRowSelectionInterval(jtable.getRowCount() - 1, jtable.getRowCount() - 1);
		}
	}

	public void shows() {
		this.setVisible(false);
		new BookTypeFrame().setVisible(true);;
	}
	private void refresh_Click() {
		shows();
	}

	private void updBookType_Click() {
		BookType bookType = getRowData();
		if (bookType != null) {
			updateBookTypeFrame = new UpdateBookTypeFrame(this,bookType);
			updateBookTypeFrame.setVisible(true);
		} else {
			// 如果没有选中任何行，提示用户
			JOptionPane.showMessageDialog(null, "请选中需要修改的学生信息");
			return;
		}
	}

	// 获取当前选择的学生对象
	public BookType getRowData() {
		BookType bookType = null;
		int index = jtable.getSelectedRow();
		// 如果下标不为-1，则选中行为数据行
		if (index != -1) {
			// 取得表格对象的数据模型
			TableModel model = jtable.getModel();
			// 在表格对象模型中，根据选中的行和列，获取相应的数据值,(index,0)对应第一列的学号
			int id = Integer.parseInt(model.getValueAt(index, 0) + "");
			String name = model.getValueAt(index, 1) + "";
			int days = Integer.parseInt(model.getValueAt(index, 2) + "");
			double FK = Double.parseDouble((model.getValueAt(index, 3) + "").split("元")[0]);
			bookType = new BookType(id, name, days, FK);
		}
		// 如果没有选中任何一行 就意味着student为null 可以提供给外部判断
		return bookType;
	}

	// table初始化
	private void inittableUi() {
		jtable = new JTable();
		jspa = new JScrollPane(jtable);
		jspa.setBounds(50, 100, 700, 400);
		jpanel.add(jspa);
		showListData(1);
	}

	public void showListData(int pageIndex) {
		List<BookType> list = null;
		list = bookTypeService.queryAllInPage(pageIndex);
		if(list.size()<1) {
			this.pageIndex--;
			return;
		}
		// list数据传给表格
		DefaultTableModel tableModel = getDefaultTableModel(list);
		jtable.setModel(tableModel);
	}

	public DefaultTableModel getDefaultTableModel(List<BookType> list) {
		Vector<String> col = new Vector<String>();
		col.add("图书编号");
		col.add("图书类型");
		col.add("图书可借天数");
		col.add("图书罚款");
		Vector<Object> data = new Vector<>();
		for (BookType bookType : list) {
			Vector<Object> v = new Vector<Object>();
			v.add(bookType.getId());
			v.add(bookType.getTypeName());
			v.add(bookType.getDays());
			v.add(bookType.getFK()+"元/天");
			data.add(v);
		}
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(data, col);
		return dm;
	}

}
