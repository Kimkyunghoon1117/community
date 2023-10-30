package com.pofol.web.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pofol.web.domain.BoardDto;
import com.pofol.web.domain.PageHandler;
import com.pofol.web.domain.SearchCondition;
import com.pofol.web.service.BoardService;

/*
 * 자동 생성 - @Controller, @Service, @Repository, @Conponent, @Advice, @RestController
 * servlet-context.xml, root-context.xml에 설정되어 있어야만 한다.
 */
@Controller
// URL 매핑 - 클래스 위에 붙인것은 앞에 붙이는 URL
@RequestMapping("/board")
public class BoardController {
	// 자동 DI - @Autowired : spring, @Inject : javax(java라이브러리)
	@Autowired // 스프링 자동DI
	// 동일한 타입의 객체가 있는 경우 설정
	@Qualifier("boardServiceImpl")
	private BoardService boardService;
	
	@GetMapping("/list.do")
	// jsp에 데이터를 전달하려면 Model을 파라메터로 받는다. -> request에 데이터가 담기게 된다.
	public String list(Model m, SearchCondition sc, HttpServletRequest request){
		System.out.println("BoardController.list()"); // syso
		try {
            int totalCnt = boardService.getSearchResultCnt(sc);
            m.addAttribute("totalCnt", totalCnt);
            
            PageHandler pageHandler = new PageHandler(totalCnt, sc);

            List<BoardDto> list = boardService.getSearchResultPage(sc);
            m.addAttribute("list", list);
            m.addAttribute("ph", pageHandler);

            Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
            m.addAttribute("startOfToday", startOfToday.toEpochMilli());
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("msg", "LIST_ERR");
            m.addAttribute("totalCnt", 0);
        }
		// jsp 정보 - servlet-context.xml 설정
		// /WEB-INF/views/ + board/list + .jsp
		return "board/list";
	}
	
	//글보기
	@GetMapping("/view.do")
	public String view(Integer bno, int inc, SearchCondition sc, Model model, RedirectAttributes rattr) throws Exception {
		System.out.println("BoardController.view().no / inc - "+bno+"/"+inc); // syso
		
		try {
			model.addAttribute("boardDto", boardService.read(bno,inc));
			int totalCnt = boardService.getSearchResultCnt(sc);
			model.addAttribute("totalCnt", totalCnt);
			PageHandler pageHandler = new PageHandler(totalCnt, sc);
			model.addAttribute("ph", pageHandler);
		} catch (Exception e) {
			e.printStackTrace();
			rattr.addFlashAttribute("msg", "VIEW_ERR");
			return "redirect:/board/list" + sc.getQueryString();
		}
		return "board/view";
	}

	//글등록 폼
	@GetMapping("/write.do")
	public String write() throws Exception {
		System.out.println("BoardController.write()"); // syso
		return "board/write";
	}
	
	//글등록 처리
	@PostMapping("/write.do")
	public String write(BoardDto boardDto, RedirectAttributes rattr) throws Exception {
		System.out.println("BoardController.write().dto - "+ boardDto); // syso
		boardService.write(boardDto);
		// 처리 결과를 리스트로 보낸다. - session사용. 출력하면 지운다.
		rattr.addFlashAttribute("msg", "일반 게시판 글등록이 되었습니다");
		//이동URL 정보 : 앞에 redirect:
		return "redirect:/board/list.do";
	}
	
	//글수정 폼
	@GetMapping("/update.do")
	public String update(Integer bno, Model model) throws Exception {
		System.out.println("BoardController.update().before"); // syso
		model.addAttribute("boardDto", boardService.read(bno, 0));
		return "board/update";
	}
	
	//글수정 처리
	@PostMapping("/update.do")
	public String update(BoardDto boardDto, SearchCondition sc, RedirectAttributes rattr) throws Exception {
		System.out.println("BoardController.update().after-"+boardDto); // syso
		boardService.update(boardDto);
		// 처리 결과를 리스트로 보낸다. - session사용. 출력하면 지운다.
		rattr.addFlashAttribute("msg", "일반 게시판 "+boardDto.getBno()+"글이 수정되었습니다");
		//이동URL 정보 : 앞에 redirect:
		return "redirect:/board/view.do"+sc.getQueryString()+"&bno="+boardDto.getBno()+"&inc=0";
	}

	//글삭제 처리
	@PostMapping("/delete.do")
	public String delete(BoardDto boardDto, RedirectAttributes rattr) throws Exception {
		System.out.println("BoardController.delete() -"+boardDto); // syso
		
		try{
			int rowCnt = boardService.delete(boardDto.getBno());
			if(rowCnt != 1)
				throw new Exception("board remove error");
			// 처리 결과를 리스트로 보낸다. - session사용. 출력하면 지운다.
			rattr.addFlashAttribute("msg", "DEL_OK");
		} catch (Exception e) {
			e.printStackTrace();
			rattr.addFlashAttribute("msg", "DEL_ERR");
		}
		//이동URL 정보 : 앞에 redirect:
		return "redirect:/board/list.do";
	}
}