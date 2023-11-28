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
	public ModelAndView create() { // create �޼ҵ�� ���������� /create �ּҰ� GET������� �ԷµǾ��� �� book/create ����� �並 �����ش�.
		return new ModelAndView("book/create");
	}

//	å �Է� �޼ҵ�
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

	// å �� URL�� �ԷµǸ� ����Ǵ� �޼ҵ�
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam Map<String, Object> map) {
		Map<String, Object> detailMap = this.bookService.detail(map);

		ModelAndView mav = new ModelAndView();
		mav.addObject("data", detailMap);
		String bookId = map.get("bookId").toString();
		mav.addObject("bookId", bookId);
		mav.setViewName("/book/detail"); // ��ȭ��
		return mav;
	}

	// å ���� ȭ���� å �Է� ȭ�� + å �� ȭ���̴�. å �Է� ȭ���� ȭ�� ������ �״�� �������� �����ͺ��̽��� ����� �����͸� �׷��ָ�
	// �ȴ�.
	// ���� å �����ʹ� �� ȭ�� ���񽺿��� ������ ����, ��� å �ӷ� ȭ���� �����Ѵ�.
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam Map<String, Object> map) {
		Map<String, Object> detailMap = this.bookService.detail(map);

		ModelAndView mav = new ModelAndView();
		mav.addObject("data", detailMap);
		mav.setViewName("/book/update"); // ������Ʈ ȭ��
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

	//���� ��� ��Ʈ�ѷ� �޼ҵ� 
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
	
	// å ��� ��Ʈ�ѷ� �޼ҵ� 
	@RequestMapping(value = "list")
	public ModelAndView list(@RequestParam Map<String, Object> map) {

		List<Map<String, Object>> list = this.bookService.list(map);

		ModelAndView mav = new ModelAndView();
		mav.addObject("data", list);
		
		if (map.containsKey("keyword")) {  //����������� HTTP �Ķ���Ͱ� �ִ� �� ���� �� �˻�
			mav.addObject("keyword", map.get("keyword"));
		} 
		
		mav.setViewName("/book/list");
		return mav;
	}

}
