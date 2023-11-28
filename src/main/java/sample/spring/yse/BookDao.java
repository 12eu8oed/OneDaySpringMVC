package sample.spring.yse;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao { // DAO(Data Access Object) 클래스
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	public int insert(Map<String, Object> map) {
		return this.sqlSessionTemplate.insert("book.insert", map);  //입력
	}

	public Map<String, Object> selectDetail(Map<String, Object> map) {  //선택
		return this.sqlSessionTemplate.selectOne("book.select_detail", map);
	}
	
	public int update(Map<String, Object> map) { //수정
		return this.sqlSessionTemplate.update("book.update", map);
	}
	
	public int delete(Map<String, Object> map) {  //삭제
		return this.sqlSessionTemplate.delete("book.delete", map);
	}
	
	public List<Map<String, Object>> selectList(Map<String, Object> map) {  //책 목록데이터 접속
		return this.sqlSessionTemplate.selectList("book.select_list", map);
	}

}
