package sample.spring.yse;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BookController {

	@Autowired
	BookService bookService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() { // create 메소드는 브라우저에서 /create 주소가 GET방식으로 입력되었을 때 book/create 경로의 뷰를 보여준다.
		return new ModelAndView("book/create");
	}

//	책 입력 메소드
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createPost(@RequestParam Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();

		String bookId = this.bookService.create(map);
		if (bookId == null) {
			mav.setViewName("redirect:/create");
		} else {
			mav.setViewName("redirect:/detail?bookId=" + bookId);
		}

		return mav;
	}

	// 책 상세 URL이 입력되면 실행되는 메소드
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam Map<String, Object> map) {
		Map<String, Object> detailMap = this.bookService.detail(map);

		ModelAndView mav = new ModelAndView();
		mav.addObject("data", detailMap);
		String bookId = map.get("bookId").toString();
		mav.addObject("bookId", bookId);
		mav.setViewName("/book/detail"); // 상세화면
		return mav;
	}

	// 책 수정 화면은 책 입력 화면 + 책 상세 화면이다. 책 입력 화면의 화면 형식을 그대로 따라가지만 데이터베이스에 저장된 데이터만 그려주면
	// 된다.
	// 따라서 책 데이터는 상세 화면 서비스에서 가지고 오고, 뷰는 책 임력 화면을 복사한다.
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam Map<String, Object> map) {
		Map<String, Object> detailMap = this.bookService.detail(map);

		ModelAndView mav = new ModelAndView();
		mav.addObject("data", detailMap);
		mav.setViewName("/book/update"); // 업데이트 화면
		return mav;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public ModelAndView updatePost(@RequestParam Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();

		boolean isUpdateSuccess = this.bookService.edit(map);
		if (isUpdateSuccess) {
			String bookId = map.get("bookId").toString();
			mav.setViewName("redirect:/detail?bookId=" + bookId);
		} else {
			mav = this.update(map);
		}

		return mav;
	}

	//삭제 기능 컨트롤러 메소드 
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView deletePost(@RequestParam Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();

		boolean isDeleteSuccess = this.bookService.remove(map);
		if (isDeleteSuccess) {
			mav.setViewName("redirect:/list");
		} else {
			String bookId = map.get("bookId").toString();
			mav.setViewName("redirect:/detail?bookId=" + bookId);
		}

		return mav;
	}
	
	// 책 목록 컨트롤러 메소드 
	@RequestMapping(value = "list")
	public ModelAndView list(@RequestParam Map<String, Object> map) {

		List<Map<String, Object>> list = this.bookService.list(map);

		ModelAndView mav = new ModelAndView();
		mav.addObject("data", list);
		
		if (map.containsKey("keyword")) {  //목록페이지에 HTTP 파라미터가 있는 지 없는 지 검사
			mav.addObject("keyword", map.get("keyword"));
		} 
		
		mav.setViewName("/book/list");
		return mav;
	}

}
