
package cn.dfrz.gyl.serviceimpl;

import cn.dfrz.gyl.service.BookService;
import cn.dfrz.gyl.service.BookTypeService;
import cn.dfrz.gyl.service.BorrowService;
import cn.dfrz.gyl.service.OperatorService;
import cn.dfrz.gyl.service.OrderService;
import cn.dfrz.gyl.service.ReaderService;


/**
 * @Decription 工厂类,进一步实现Service与UI层的解耦,获得对应的Service实现类
 */
public class FactoryService {
	public static  BookService getBookService() {
		return new BookServiceimpl();
	}
	public static BookTypeService getBookTypeService() {
		return new BookTypeServiceimpl();
	}
	public static BorrowService getBorrowService() {
		return new BorrowServiceimpl();
	}
	public static OperatorService getOperatorService() {
		return new OperatorServiceimpl();
	}
	public static OrderService getOrderService() {
		return new OrderServiceimpl();
	}
	public static ReaderService getReaderService() {
		return new ReaderServiceimpl();
	}

}
