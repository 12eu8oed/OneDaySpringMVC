package sample.spring.yse;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 서비스 클래스는 비즈니스 클래스가 위치하는 곳이다. 
// 스프링MVC 구조에서 서비스 클래스는 컨트롤러와 DAO를 연결하는 역할을 한다.

@Service
public class BookServiceImpl implements BookService {  //인터페이스를 구현한 클래스
	@Autowired
	BookDao bookDao;

	@Override
	public String create(Map<String, Object> map) {
		int affectRowCount = this.bookDao.insert(map);
		if (affectRowCount == 1) {
			return map.get("book_id").toString();
		}
		return null;

	}
	
	//서비스는 DAO를 호출한 결과를 바로 리턴하는 일만 한다.
	@Override
	public Map<String, Object> detail(Map<String, Object> map) { 
		return this.bookDao.selectDetail(map);
	}
	
	
	// 책 수정 기능 서비스 클래스 메소드
	@Override
	public boolean edit(Map<String, Object> map) {
		int affectRowCount = this.bookDao.update(map);
		return affectRowCount == 1;
	}

	//책 삭제 기능 서비스 클래스 메소드
	@Override
	public boolean remove(Map<String, Object> map) {
		int affectRowCount = this.bookDao.delete(map);
		return affectRowCount == 1;

	}

	// 책 목록 서비스 클래스 메소드
	@Override
	public List<Map<String, Object>> list(Map<String, Object> map) {
		return this.bookDao.selectList(map);
	}

}