package cn.dfrz.gyl.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.dfrz.gyl.model.Book;
import cn.dfrz.gyl.service.BookService;
import cn.dfrz.gyl.service.BookTypeService;
import cn.dfrz.gyl.serviceimpl.FactoryService;
import cn.dfrz.gyl.utils.JFrameUtils;

/**
 * @Decription 更新书本信息界面
 */
public class UpdateBookFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private Font font = new Font("宋体", Font.BOLD, 20);
	private BookService bookService = FactoryService.getBookService();
	private BookTypeService bookTypeService = FactoryService.getBookTypeService();
	private BookFrame bookFrame = null;
	private JPanel jpanel;
	private JButton commit;
	private JButton reset;
	private String kpISBN;
	private JLabel ISBN;
	private JLabel typeId;
	private JLabel bookName;
	private JLabel writer;
	private JLabel translator;
	private JLabel date;
	private JLabel publisher;
	private JLabel price;
	private JLabel title;

	private JTextField addISBN;
	private JTextField addTypeId;
	private JTextField addBookName;
	private JTextField addWriter;
	private JTextField addTranslator;
	private JTextField addDate;
	private JTextField addPublisher;
	private JTextField addPrice;

	public UpdateBookFrame(BookFrame bookFrame,Book book) {
		this.bookFrame = bookFrame;
		this.kpISBN = book.getISBN();
		initView();
		loadData(book);
		bindEvent();
	}

	//获取当前父界面当前选择行
	public void loadData(Book book) {
		addISBN.setText(kpISBN);
		addTypeId.setText(book.getTypeId()+"");
		addBookName.setText(book.getBookName());
		addWriter.setText(book.getWriter());
		addTranslator.setText(book.getTranslator());
		addPublisher.setText(book.getPublisher());
		addDate.setText(book.getDate().toString());
		addPrice.setText(book.getPrice()+"");
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
		addTypeId.setText("");
		addBookName.setText("");
		addWriter.setText("");
		addTranslator.setText("");
		addPublisher.setText("");
		addDate.setText("");
		addPrice.setText("");
	}
	//提交
	private void commit_Click(ActionEvent e) {
		String ISBN_ = addISBN.getText().trim();
		String typeId_ = addTypeId.getText().trim();
		String bookName_= addBookName.getText().trim();
		String writer_= addWriter.getText().trim();
		String translator_= addTranslator.getText().trim();
		String publisher_= addPublisher.getText().trim();
		String date_= addDate.getText().trim();
		String price_= addPrice.getText().trim();

		if (JFrameUtils.StringArrAnyIsEmpty(ISBN_,typeId_,bookName_,publisher_,writer_,translator_,date_,price_)) {
			JOptionPane.showMessageDialog(null, "请输入完整信息!");
			return;
		}
		int typeid = 0;
		typeid =Integer.parseInt(typeId_);
		if(bookTypeService.queryById(typeid)==null) {
			JOptionPane.showMessageDialog(null, "您输入的图书类型编号不存在!");
			return;
		}
		double prc = 0;
		try {
			prc = Double.parseDouble(price_);
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "图书价格请输入数字类型!");
			return;
		}
		if (prc < 0) {
			JOptionPane.showMessageDialog(null, "图书价格请输入正数!");
			return;
		}
		Date date = JFrameUtils.checkDate(date_);
		if(date == null) {
			JOptionPane.showMessageDialog(null, "请输入正确的出版日期:例如:2000-1-1");
			return;
		}
		Book book = new Book();
		book.setISBN(ISBN_);
		book.setPublisher(publisher_);
		book.setBookName(bookName_);
		book.setPrice(new BigDecimal(prc));
		book.setDate(date);
		book.setTranslator(translator_);
		book.setTypeId(typeid);
		book.setWriter(writer_);	
		if(bookService.update(book)>0) {
		JOptionPane.showMessageDialog(null, "修改成功!");
		this.setVisible(false);
		bookFrame.showListData(1);
		}else {
			JOptionPane.showMessageDialog(null, "出现异常,请重新修改!");
			return;
		}	
	}

	public void initView() {
		int width = 500;
		int height = 700;
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

		addISBN = new JTextField();
		addISBN.setBounds(160, 100, 200, 30);
		addISBN.setEditable(false);
		jpanel.add(addISBN);
		ISBN = new JLabel("图书编号:");
		ISBN.setFont(font);
		ISBN.setBounds(20, 95, 140, 40);
		jpanel.add(ISBN);

		addTypeId = new JTextField();
		addTypeId.setBounds(160, 150, 200, 30);
		jpanel.add(addTypeId);
		typeId = new JLabel("图书类型编号:");
		typeId.setFont(font);
		typeId.setBounds(20, 145, 140, 40);
		jpanel.add(typeId);

		addBookName = new JTextField();
		addBookName.setBounds(160, 200, 200, 30);
		jpanel.add(addBookName);
		bookName = new JLabel("图书名字:");
		bookName.setFont(font);
		bookName.setBounds(20, 195, 140, 40);
		jpanel.add(bookName);
		
		addWriter = new JTextField();
		addWriter.setBounds(160, 250, 200, 30);
		jpanel.add(addWriter);
		writer = new JLabel("图书作者:");
		writer.setFont(font);
		writer.setBounds(20, 245, 140, 40);
		jpanel.add(writer);
		
		addTranslator = new JTextField("无");
		addTranslator.setBounds(160, 300, 200, 30);
		jpanel.add(addTranslator);
		translator = new JLabel("图书译者:");
		translator.setFont(font);
		translator.setBounds(20, 295, 140, 40);
		jpanel.add(translator);
		
		
		addDate = new JTextField();
		addDate.setBounds(160, 350, 200, 30);
		addDate.setSelectedTextColor(Color.blue);
		jpanel.add(addDate);
		date = new JLabel("图书出版日期:");
		date.setFont(font);
		date.setBounds(20, 345, 140, 40);
		jpanel.add(date);
		
		addPrice = new JTextField();
		addPrice.setBounds(160, 400, 200, 30);
		jpanel.add(addPrice);
		price = new JLabel("图书价格:");
		price.setFont(font);
		price.setBounds(20, 395, 140, 40);
		jpanel.add(price);

		addPublisher = new JTextField();
		addPublisher.setBounds(160, 450, 200, 30);
		jpanel.add(addPublisher);
		publisher = new JLabel("图书出版社");
		publisher.setFont(font);
		publisher.setBounds(20, 445, 140, 40);
		jpanel.add(publisher);
		
		
		title = new JLabel("图书信息修改");
		title.setFont(font);
		title.setBounds(50, 20, 500, 40);
		jpanel.add(title);

		reset = new JButton("重置");
		reset.setBounds(230, 495, 110, 40);
		jpanel.add(reset);

		commit = new JButton("提交");
		commit.setBounds(70, 495, 110, 40);
		jpanel.add(commit);
		this.getRootPane().setDefaultButton(commit);
	}


}

