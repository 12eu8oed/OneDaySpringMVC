package sample.spring.yse;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// ���� Ŭ������ ����Ͻ� Ŭ������ ��ġ�ϴ� ���̴�. 
// ������MVC �������� ���� Ŭ������ ��Ʈ�ѷ��� DAO�� �����ϴ� ������ �Ѵ�.

@Service
public class BookServiceImpl implements BookService {  //�������̽��� ������ Ŭ����
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
	
	//���񽺴� DAO�� ȣ���� ����� �ٷ� �����ϴ� �ϸ� �Ѵ�.
	@Override
	public Map<String, Object> detail(Map<String, Object> map) { 
		return this.bookDao.selectDetail(map);
	}
	
	
	// å ���� ��� ���� Ŭ���� �޼ҵ�
	@Override
	public boolean edit(Map<String, Object> map) {
		int affectRowCount = this.bookDao.update(map);
		return affectRowCount == 1;
	}

	//å ���� ��� ���� Ŭ���� �޼ҵ�
	@Override
	public boolean remove(Map<String, Object> map) {
		int affectRowCount = this.bookDao.delete(map);
		return affectRowCount == 1;

	}

	// å ��� ���� Ŭ���� �޼ҵ�
	@Override
	public List<Map<String, Object>> list(Map<String, Object> map) {
		return this.bookDao.selectList(map);
	}

}