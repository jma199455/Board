package board.controller;

import board.vo.FileDto;
import board.vo.FileVO;
import board.vo.LoginForm;
import board.vo.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
public class FileController {

    @GetMapping("/fileForm")
    public String loginForm(@ModelAttribute("data") FileVO data) {
        log.info("=========fileForm=========");
        return "file/fileUploadForm";
    }

    @PostMapping("/upload/fileUpload")
    public String fileUpload(@RequestParam("uploadFile") MultipartFile file, Model model) throws IOException {
        log.info("=========확인=========");
        log.info("file : {} ",file);
        String savedFileName = "";
        // 1. 파일 저장 경로 설정 : 실제 서비스되는 위치(프로젝트 외부에 저장)
        String uploadPath = "C:\\develop\\test2\\";
        // 2. 원본 파일 이름 알아오기
        String originalFileName = file.getOriginalFilename();
        // 3. 파일 이름 중복되지 않게 이름 변경(서버에 저장할 이름) UUID 사용
        UUID uuid = UUID.randomUUID();
        savedFileName = uuid.toString() + "_" + originalFileName;

        File fileDir = new File(uploadPath);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        // 4. 파일 생성
        File file1 = new File(uploadPath + savedFileName);
        // 5. 서버로 전송
        file.transferTo(file1);
        // model로 저장
        //FileVO data = new FileVO();
        //data.setOriginalFileName(originalFileName);
        //model.addAttribute("data", data);

        return "redirect:/fileForm";

    }




    @GetMapping("/fileForm2")
    public String loginForm2() {
        log.info("=========fileForm=========");
        return "file/fileUploadForm2";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile[] uploadfile, Model model) throws IOException {

        List<FileDto> list = new ArrayList<>();
        String uploadPath = "C:\\develop\\test3\\";

        for (MultipartFile file : uploadfile) {

            if (!file.isEmpty()){
                FileDto dto = new FileDto(UUID.randomUUID().toString(),
                                            file.getOriginalFilename(),
                                            file.getContentType());
                list.add(dto);

                File newFileName = new File(uploadPath, dto.getUuid() + "_" + dto.getFileName());
                file.transferTo(newFileName);
            }
        }
        model.addAttribute("files", list);
        return "file/result";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@ModelAttribute FileDto dto) throws IOException{
        String filePath = "C:\\develop\\test3";
        Path path = Paths.get(filePath + "/" + dto.getUuid() + "_" + dto.getFileName());
        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(dto.getFileName(), StandardCharsets.UTF_8)
                .build());

        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }





}
