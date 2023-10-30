package com.pofol.web.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pofol.web.domain.LoginDto;
import com.pofol.web.domain.MemberDto;
import com.pofol.web.domain.OauthToken;
import com.pofol.web.domain.PageHandler;
import com.pofol.web.domain.SearchCondition;
import com.pofol.web.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	@Qualifier("memberServiceImpl")
	private MemberService service;

	// 로그인 폼
	@GetMapping("/login.do")
	public String loginForm() {
		return "member/login";
	}
	
	// 로그인 처리
	@PostMapping("/login.do")
	public String loginForm(LoginDto loginDto, HttpSession session, RedirectAttributes rttr) {
		// 넘어오는 데이터 확인
		System.out.println("MemberController.loginForm().loginDto - " + loginDto);
		
		// 로그인 처리
		if(!loginCheck(loginDto)) {
			rttr.addFlashAttribute("msg", "로그인을 실패하셨습니다.<br>로그인 정보를 확인해 주세요.");
			return "redirect:/member/login.do";
		} else {
			session.setAttribute("loginDto", service.login(loginDto));
			rttr.addFlashAttribute("msg", "로그인이 되었습니다.");
		}		
//		// return "member/login";	
		String goBackURL = (String) session.getAttribute("goBackURL");			
		if(goBackURL == null)
			// return "redirect:/main/main.do";
			return "redirect:/main/home.do";
		else {
			session.removeAttribute("goBackURL");
			return "redirect:" + goBackURL;
		}	
	}
	
	@GetMapping("/logout.do")
	public String logout(HttpSession session, RedirectAttributes rttr) {
		// 로그아웃처리
		session.removeAttribute("loginDto");

		rttr.addFlashAttribute("msg", "로그아웃 되었습니다.<br>땡큐~~~~~");
		
		return "redirect:/board/list.do";
	}
	
	@GetMapping("/list.do")
	//public String list(@ModelAttribute PageObject pageObject, Model model) {
	public String list(SearchCondition sc, Model m) {
		System.out.println("MemberController.list()");
		
		//int totalCnt = service.getTotalRow(sc);
        //m.addAttribute("totalCnt", totalCnt);
        
        PageHandler pageHandler = new PageHandler(service.getTotalRow(sc), sc);
        // DB에서 데이터 가져오기. Model에 담아야 한다.
        m.addAttribute("list", service.list(sc));
        m.addAttribute("ph", pageHandler);

        Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
        m.addAttribute("startOfToday", startOfToday.toEpochMilli());
		
        
        
		//model.addAttribute("list", service.list(pageObject));
		
		return "member/list";
	}
	
	@GetMapping("/view.do")
	public String view(String id, HttpSession session, Model model) {
		System.out.println("MemberController.view().id - " + id);
		
		// id는 받드시 필요한 데이터 입니다. 내정보보기에서는 id 가 넘어오지 않습니다.(null)-> session에서 꺼내야 합니다.
		if(id == null) id = ((LoginDto)session.getAttribute("loginDto")).getId();
		
		// DB에서 데이터 가져오기. Model에 담아야 한다.
		model.addAttribute("dto", service.view(id));
		
		return "member/view";
	}
	
	@GetMapping("/write.do")
	public String write(HttpSession session, Model model) {
		System.out.println("MemberController.write()");
		
		return "member/write";
	}
	
	@PostMapping("/write.do")
	public String write(MemberDto dto, HttpSession session, Model model, RedirectAttributes rattr) {
		System.out.println("MemberController.write()");
		System.out.println("MemberDto : "+ dto);
		
		dto.setEmail(dto.getEmail1()+"@"+dto.getEmail2());
		
		if( service.write(dto) == 1)
			rattr.addFlashAttribute("msg", "WRT_OK");
		else
			rattr.addFlashAttribute("msg", "WRT_ERR");
		return "redirect:/main/home.do";
	}
	
	private boolean loginCheck(LoginDto dto) {
        LoginDto loginDto = null;

        try {
        	loginDto = service.login(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return loginDto!=null && loginDto.getPw().equals(dto.getPw());
        //return "test".equals(id) && "1234".equals(pwd);
    }
	
	@GetMapping("/kakao")
	public @ResponseBody String kakaoCallback(String code) throws Exception {
		// POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)
				// 이 때 필요한 라이브러리가 RestTemplate, 얘를 쓰면 http 요청을 편하게 할 수 있다.
				RestTemplate rt = new RestTemplate();

				// HTTP POST를 요청할 때 보내는 데이터(body)를 설명해주는 헤더도 만들어 같이 보내줘야 한다.
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

				// body 데이터를 담을 오브젝트인 MultiValueMap를 만들어보자
				// body는 보통 key, value의 쌍으로 이루어지기 때문에 자바에서 제공해주는 MultiValueMap 타입을 사용한다.
				MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
				params.add("grant_type", "authorization_code");
				params.add("client_id", "24ceadf6977df8dcd7245a08be24c116");
				params.add("redirect_uri", "http://localhost/member/kakao");
				params.add("code", code);

				// 요청하기 위해 헤더(Header)와 데이터(Body)를 합친다.
				// kakaoTokenRequest는 데이터(Body)와 헤더(Header)를 Entity가 된다.
				HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

				// POST 방식으로 Http 요청한다. 그리고 response 변수의 응답 받는다.
				ResponseEntity<String> response = rt.exchange(
						"https://kauth.kakao.com/oauth/token", // https://{요청할 서버 주소}
						HttpMethod.POST, // 요청할 방식
						kakaoTokenRequest, // 요청할 때 보낼 데이터
						String.class // 요청 시 반환되는 데이터 타입
				);
//				return URLEncoder.encode("카카오 토큰 요청 완료 : 토큰 요청에 대한 응답 : ", "utf-8")+response;
				
				ObjectMapper objectMapper = new ObjectMapper();
				OauthToken oauthToken = null;
				try {
					oauthToken = objectMapper.readValue(response.getBody(), OauthToken.class);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				System.out.println("카카오 엑세스 토큰 : " +oauthToken.getAccess_token());
				
				RestTemplate rt2 = new RestTemplate();

				// HTTP POST를 요청할 때 보내는 데이터(body)를 설명해주는 헤더도 만들어 같이 보내줘야 한다.
				HttpHeaders headers2 = new HttpHeaders();
				headers2.add("Authorization", "Bearer "+ oauthToken.getAccess_token());
				headers2.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

				// 요청하기 위해 헤더(Header)와 데이터(Body)를 합친다.
				// kakaoTokenRequest는 데이터(Body)와 헤더(Header)를 Entity가 된다.
				HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

				// POST 방식으로 Http 요청한다. 그리고 response 변수의 응답 받는다.
				ResponseEntity<String> response2 = rt2.exchange(
						"https://kapi.kakao.com/v2/user/me", // https://{요청할 서버 주소}
						HttpMethod.POST, // 요청할 방식
						kakaoProfileRequest2, // 요청할 때 보낼 데이터
						String.class // 요청 시 반환되는 데이터 타입
				);
				
				return response2.getBody();
	}
}
