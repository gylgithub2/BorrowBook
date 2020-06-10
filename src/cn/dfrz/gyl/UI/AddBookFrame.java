
package cn.dfrz.gyl.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
 * @Decription 新增图书界面,订购一个原本不存在的图书时,创建一个临时图书对象
 */
public class AddBookFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private Font font = new Font("宋体", Font.BOLD, 20);
	private BookFrame bookFrame = null;
	private BookService bookService = FactoryService.getBookService();
	private BookTypeService bookTypeService = FactoryService.getBookTypeService();
	private AddOrderFrame addOrderFrame;

	//默认为空,从订购信息传入时赋值
	private String inBookISBN = "";
	private JPanel jpanel;
	private JButton commit;
	private JButton reset;
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

	public AddBookFrame(BookFrame bookFrame) {
		this.bookFrame = bookFrame;
		initView();
		bindEvent();
	}
	

	/**
	 * @param inBookISBN 新增图书ISBN
	 */
	public AddBookFrame(AddOrderFrame addOrderFrame,String inBookISBN) {
		this.inBookISBN = inBookISBN;
		this.addOrderFrame = addOrderFrame;
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
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reset_Click(e);

			}

		});
	}

	private void reset_Click(ActionEvent e) {
		addISBN.setText("");
		addTypeId.setText("");
		addBookName.setText("");
		addWriter.setText("");
		addTranslator.setText("");
		addPublisher.setText("");
		addDate.setText("");
		addPrice.setText("");

	}

	private void commit_Click(ActionEvent e) {
		String ISBN_ = addISBN.getText().trim();
		String typeId_ = addTypeId.getText().trim();
		String bookName_ = addBookName.getText().trim();
		String writer_ = addWriter.getText().trim();
		String translator_ = addTranslator.getText().trim();
		String publisher_ = addPublisher.getText().trim();
		String date = addDate.getText().trim();
		String price_ = addPrice.getText().trim();

		if (JFrameUtils.StringArrAnyIsEmpty(ISBN_, typeId_, bookName_, writer_, translator_, date,publisher_, price_)) {
			JOptionPane.showMessageDialog(null, "请输入完整信息!");
			return;
		}
		if (bookService.queryByISBN(ISBN_) != null) {
			JOptionPane.showMessageDialog(null, "您输入的图书编号已存在!");
			return;
		}
		
		int typeId = JFrameUtils.checkNum(typeId_);
		if(typeId < 0) {		
			JOptionPane.showMessageDialog(null, "图书类型编号应该为数字类型");
			return;
		}
		if (bookTypeService.queryById(typeId) == null) {
			JOptionPane.showMessageDialog(null, "您输入的图书类型编号不存在!");
			return;
		}
		double prc = JFrameUtils.checkDoubleNum(price_);
		if(prc < 0) {
			JOptionPane.showMessageDialog(null, "图书价格应该为数字类型");
			return;
		} 
		Date dates = JFrameUtils.checkDate(date);
		if(dates == null) {
			JOptionPane.showMessageDialog(null, "请输入正确的出版日期:例如:2000-1-1");
			return;
		}
		Book book = new Book();
		book.setISBN(ISBN_);
		book.setBookName(bookName_);
		book.setPrice(new BigDecimal(prc));
		book.setDate(dates);
		book.setPublisher(publisher_);
		book.setTranslator(translator_);
		book.setTypeId(typeId);
		book.setWriter(writer_);
		
		if (JFrameUtils.StringArrAnyIsEmpty(inBookISBN)) {
			if (bookService.insert(book) > 0) {
				JOptionPane.showMessageDialog(null, "添加成功!");
			} else {
				JOptionPane.showMessageDialog(null, "添加失败请重试!");
				return;
			}
		} else {
			addOrderFrame.setNewBookPrice(prc);
			keepBook(book);
		}
		if (bookFrame != null) {
			bookFrame.shows();
		}

		
		this.setVisible(false);

	}
	
	/**
	 * @Decription 此book不能进入数据库,持久化Book当本地,确认订购时,才会取出对象使用
	 */
	public void keepBook(Book book) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(JFrameUtils.getTemporaryFileLocation()+"\\"+book.getISBN()+".dat"));
			oos.writeObject(book);
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

		addISBN = new JTextField(inBookISBN);
		addISBN.setBounds(160, 100, 200, 30);
		if(!JFrameUtils.StringArrAnyIsEmpty(inBookISBN)) {	
			addISBN.setEditable(false);
		}
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
		
		addPublisher = new JTextField();
		addPublisher.setBounds(160, 400, 200, 30);
		addPublisher.setSelectedTextColor(Color.blue);
		jpanel.add(addPublisher);
		publisher = new JLabel("图书出版社:");
		publisher.setFont(font);
		publisher.setBounds(20, 395, 140, 40);
		jpanel.add(publisher);

		addPrice = new JTextField();
		addPrice.setBounds(160, 450, 200, 30);
		jpanel.add(addPrice);
		price = new JLabel("图书价格:");
		price.setFont(font);
		price.setBounds(20, 455, 140, 40);
		jpanel.add(price);

		title = new JLabel("新图书信息录入");
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
