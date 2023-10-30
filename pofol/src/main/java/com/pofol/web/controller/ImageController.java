package com.pofol.web.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pofol.web.domain.FileUtil;
import com.pofol.web.domain.ImageDto;
import com.pofol.web.domain.ImageFileDto;
import com.pofol.web.domain.PageHandler;
import com.pofol.web.domain.SearchCondition;
import com.pofol.web.service.ImageService;

/*
 * 자동 생성 - @Controller, @Service, @Repository, @Conponent, @Advice, @RestController
 * servlet-context.xml, root-context.xml에 설정되어 있어야만 한다.
 */
@Controller
// URL 매핑 - 클래스 위에 붙인것은 앞에 붙이는 URL
@RequestMapping("/image")
public class ImageController {
	// 자동 DI - @Autowired : spring, @Inject : javax(java라이브러리)
	@Autowired // 스프링 자동DI
	// 동일한 타입의 객체가 있는 경우 설정
	@Qualifier("imageServiceImpl")
	private ImageService imageService;
	//저장하려는 파일의 위치
	String path = "/upload/image";
	
	@GetMapping("/list.do")
	// jsp에 데이터를 전달하려면 Model을 파라메터로 받는다. -> request에 데이터가 담기게 된다.
	public String list(Model m, SearchCondition sc, HttpServletRequest request) throws Exception {
		System.out.println("ImageController.list()"); // syso
		
		try {
			sc.setPageSize(8);
            int totalCnt = imageService.getSearchResultCnt(sc);
            m.addAttribute("totalCnt", totalCnt);
            
            PageHandler pageHandler = new PageHandler(totalCnt, sc);

            List<ImageDto> list = imageService.getSearchResultPage(sc);
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
		// /WEB-INF/views/ + image/list + .jsp
		return "image/list";
	}
	
	//글보기
	@GetMapping("/view.do")
	public String view(Integer no, SearchCondition sc, Model model, RedirectAttributes rattr) throws Exception {
		System.out.println("ImageController.view().no = "+no); // syso
			
		//이미지 텍스트 데이터 가져오기
		// 엔터 공백 <> 특수문자 처리하기
		ImageDto dto = imageService.view(no);
		dto.setTitle(toStr(dto.getTitle()));
		dto.setContent(toStr(dto.getContent()));
		try {
			model.addAttribute("imageDto", dto);
			model.addAttribute("imageFileList", imageService.getImageFile(no));
			
			int totalCnt = imageService.getSearchResultCnt(sc);
			model.addAttribute("totalCnt", totalCnt);
			PageHandler pageHandler = new PageHandler(totalCnt, sc);
			model.addAttribute("ph", pageHandler);
			
			Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
			model.addAttribute("startOfToday", startOfToday.toEpochMilli());
		} catch (Exception e) {
			e.printStackTrace();
			rattr.addFlashAttribute("msg", "VIEW_ERR");
			return "redirect:/image/list" + sc.getQueryString();
		}
		return "image/view";
	}

	//글등록 폼
	@GetMapping("/write.do")
	public String write() throws Exception {
		System.out.println("ImageController.write()"); // syso
		return "image/write";
	}
	
	//글등록 처리
	@PostMapping("/write.do")
	public String write(ImageDto imageDto, HttpServletRequest request, RedirectAttributes rattr) throws Exception {
		System.out.println("ImageController.write().dto - "+ imageDto); // syso
		
		//imageDto.setId((session.getAttribute("id")));
		imageDto.setId("test");
		
		// DB에 저장할 file 정보가 여러개 들어있는 변수 선언
		List<ImageFileDto> imgFileList = new ArrayList<ImageFileDto>(); 
		
		System.out.println("ImageController.write() - 대표 이미지 : "+imageDto.getMainImageFile().getOriginalFilename());
		//대표 이미지 업로드
		String mainFileName = FileUtil.upload(path, imageDto.getMainImageFile(), request);
		ImageFileDto mainFileDto = new ImageFileDto();
		mainFileDto.setFileName(mainFileName);
		mainFileDto.setPr(1);
		
		imgFileList.add(mainFileDto);
		
		System.out.println("ImageController.write() - 서브 이미지 : ");
		if(imageDto.getImageFile()!=null)
			for(MultipartFile mf : imageDto.getImageFile()) {
				//추가 파일 업로드
				String fileName = FileUtil.upload(path, mf, request);
				ImageFileDto fileDto = new ImageFileDto();
				fileDto.setFileName(fileName);
				imgFileList.add(fileDto);
		}
		//업로드된 정보 출력
		//System.out.println("ImageController.write() - 업로드 파일 정보 : " + imgFileList);
		//DB 처리
		imageService.write(imageDto, imgFileList);
		//imageService.write(imageDto);
		// 파일이 저자되는 시간에 이미지가 안보이는 경우
		Thread.sleep(1000);
		// 처리 결과를 리스트로 보낸다. - session사용. 출력하면 지운다.
		rattr.addFlashAttribute("msg", "일반 게시판 글등록이 되었습니다");
		//이동URL 정보 : 앞에 redirect:
		return "redirect:/image/list.do";
	}
	
	//글수정 폼
	@GetMapping("/update.do")
	public String update(Integer no, Model model) throws Exception {
		System.out.println("ImageController.update().before"); // syso
		model.addAttribute("imageDto", imageService.view(no));
		return "image/update";
	}
	
	//글수정 처리
	@PostMapping("/update.do")
	public String update(ImageDto imageDto, SearchCondition sc, Model model, RedirectAttributes rattr) throws Exception {
		System.out.println("ImageController.update().after-"+imageDto); // syso
		imageDto.setId("test");
		
		try {
			int rowCnt = imageService.update(imageDto);
			if (rowCnt != 1)
				throw new Exception("update failed");
			rattr.addFlashAttribute("msg", "MOD_OK");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(imageDto);
			model.addAttribute("msg", "MOD_ERR");
			return "image/view";
		}
		
		// 처리 결과를 리스트로 보낸다. - session사용. 출력하면 지운다.
		rattr.addFlashAttribute("msg", "일반 게시판 "+imageDto.getNo()+"글이 수정되었습니다");
		//이동URL 정보 : 앞에 redirect:
		return "redirect:/image/view.do"+sc.getQueryString()+"&no="+imageDto.getNo();
	}

	//글삭제 처리
	@GetMapping("/delete.do")
	public String delete(ImageDto imageDto, SearchCondition sc, Model model, HttpServletRequest request, RedirectAttributes rattr) throws Exception {
		System.out.println("ImageController.delete() -"+imageDto); // syso
		imageDto.setId("test");
		
		try {
			// image 삭제전에 imageFile 데이터 얻기
			List<ImageFileDto> fileList = imageService.getImageFile(imageDto.getNo());
			
			int rowCnt = imageService.delete(imageDto);
			if (rowCnt != 1)
				throw new Exception("delete failed");
			// 삭제된 테이블의 이미지 파일을 지운다
			for (ImageFileDto imageFileDto : fileList) {
				FileUtil.remove(FileUtil.getRealPath("", imageFileDto.getFileName(), request));
			}
			int totalCnt = imageService.getSearchResultCnt(sc);
			model.addAttribute("totalCnt", totalCnt);
			PageHandler pageHandler = new PageHandler(totalCnt, sc);
			model.addAttribute("ph", pageHandler);
			// 처리 결과를 리스트로 보낸다. - session사용. 출력하면 지운다.
			rattr.addFlashAttribute("msg", "일반 게시판 "+imageDto.getNo()+"글이 삭제되었습니다");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(imageDto);
			model.addAttribute("msg", "MOD_ERR");
		}
		//이동URL 정보 : 앞에 redirect:
		return "redirect:/image/list.do";
	}
	@PostMapping("/changeImage.do")
	public String changeImage(Integer fno, MultipartFile changeImage, Model model , String deleteFileName,
			String query, HttpServletRequest request , RedirectAttributes rattr) throws Exception {
		// 데이터 확인
		//System.out.println("ImageController.changeImage()");
		//System.out.println("fno : "+fno);
		//System.out.println("changeImage : "+changeImage.getOriginalFilename());
		//System.out.println("deleteFileName : "+deleteFileName);
		
		try {
			// 서버에 파일 올리기
			String fileName = FileUtil.upload(path, changeImage, request);
			// DB에 파일 바꾸기 - fno, fileName
			ImageFileDto fileDto = new ImageFileDto();
			fileDto.setFno(fno);
			fileDto.setFileName(fileName);
			int rowCnt = imageService.changeImage(fileDto);
			if (rowCnt != 1)
				throw new Exception("changeImage failed");
			// 이전 파일 삭제
			FileUtil.remove(FileUtil.getRealPath("", deleteFileName, request));
			rattr.addFlashAttribute("msg", "이미지 게시판 이미지가 변경 되었습니다");
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "CHANGEIMAGE_ERR");
		}
		
		Thread.sleep(1000);
		return "redirect:/image/view.do?"+query;
	}
	
	// 한개 이미지 삭제
	@GetMapping("/deleteImage.do")
	public String deleteImage(Integer fno, Model model , String deleteFile,
			String query, HttpServletRequest request , RedirectAttributes rattr) throws Exception {
		// 데이터 확인
		System.out.println("ImageController.deleteImage()");
		String id = "test";
		//System.out.println("fno : "+fno);
		//System.out.println("id : "+id);
		//System.out.println("deleteFile : "+deleteFile);
		
		try {
			// DB 데이터 삭제
			int rowCnt = imageService.deleteImage(fno,id);
			if (rowCnt != 1)
				throw new Exception("deleteImage failed");
			// 이전 파일 삭제
			FileUtil.remove(FileUtil.getRealPath("", deleteFile, request));
			rattr.addFlashAttribute("msg", "이미지 게시판 이미지가 삭제 되었습니다");
		}
		catch (Exception e) {
			e.printStackTrace();
			rattr.addFlashAttribute("msg", "DELETE_IMAGE_ERR"
					+"<br>이유 1. 삭제하려는 이미지가 존재하지 않음"
					+"<br>이유 2. 본인의 이미지가 아님"
					+"<br>이유 3. 주 이미지는 삭제 안됨");
		}
		
		//Thread.sleep(1000);
		return "redirect:/image/view.do?"+query;
	}
	
	private String toStr(String origin) {
		return origin.replace(" ", "&nbsp;")
				.replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>");
	}
}