package main.dataservice.bookdataservice;

import main.PO.*;
import main.vo.BookVO;

public interface BookDataService {
	public boolean initBook(BookPO book);
	public BookVO showBook();
	public String[] findBooks();
	public boolean saveDraft();
	public void cancelChange();
	public void creatingBook();
}
