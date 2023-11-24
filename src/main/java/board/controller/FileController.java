package board.controller;

import board.vo.FileDto;
import board.vo.FileVO;
import board.vo.LoginForm;
import board.vo.Member;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.util.MapUtil;
import org.json.JSONArray;
import org.json.JSONObject;
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
import org.springframework.web.util.UriUtils;
import org.thymeleaf.util.MapUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

@Controller
@Slf4j
public class FileController {

    /*

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

    */


    /*
    // 파일 업로드 다운로드
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
                FileDto dto = new FileDto(UUID.randomUUID().toString(), file.getOriginalFilename(),file.getContentType());
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
    */





    /*
    // 파일 업로드 다운로드
    @GetMapping("/fileForm3")
    public String loginForm3() {
        log.info("=========fileForm=========");
        return "file/fileUploadForm3";
    }

    @PostMapping("/fileUpload")
    public String upload2(@RequestParam MultipartFile[] uploadfile, Model model) throws IOException {

        List<FileDto> list = new ArrayList<>();
        File filePath = new File("C:\\develop\\test4\\");
        String originalName = "";

        if (!filePath.exists()) {
            filePath.mkdir();
        }

        for (MultipartFile file : uploadfile) {
            if (!file.isEmpty()){
                FileDto dto = new FileDto(UUID.randomUUID().toString(), file.getOriginalFilename(),file.getContentType());
                list.add(dto);

                //File newFileName = new File(filePath, dto.getUuid() + "_" + dto.getFileName());
                File newFileName = new File(filePath, dto.getFileName());
                file.transferTo(newFileName);

                originalName = file.getOriginalFilename();
            }
        }
        model.addAttribute("files", list);
        model.addAttribute("originalName", originalName);

        return "file/result2";
    }
    */






    /*
    // 파일 다운로드  PrintWriter out = response.getWriter(); 사용
    @GetMapping("/fileDownload")
    public String download3(@ModelAttribute FileDto dto,HttpServletResponse response){

        String fileName = dto.getFileName();
        log.info("fileName ==> {} ", fileName);

        *//*
        String encodingUploadFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);
        int index = encodingUploadFileName.indexOf(".");
        String rsFileName = encodingUploadFileName.substring(0,index);
        log.info("resultFileName ==> {}",rsFileName);
        *//*

        String encodingUploadFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);
        int index = encodingUploadFileName.indexOf(".");
        String rsFileName = encodingUploadFileName.substring(0,index);
        log.info("resultFileName ==> {}",rsFileName);

        response.setContentType("application/octet-stream");
        //response.setHeader("Content-Disposition","attachment; filename=\"" + dto.getUuid() + "_" + fileName + "\"");
        response.setHeader("Content-Disposition","attachment; filename=\"" + rsFileName + ".txt\"");
        response.setCharacterEncoding("UTF-8");

        //PrintWriter out = null;
        //BufferedReader bf = null;
        // 읽어오기
        try {

            PrintWriter out = response.getWriter();

            //File file = new File("C:\\develop\\test4\\" + dto.getUuid() + "_" + fileName);
            File file = new File("C:\\develop\\test4\\" + fileName);


            //BufferedReader bf = new BufferedReader(new FileReader(file));
            BufferedReader bf = new BufferedReader(new FileReader(file));

            log.info("filePath => " + file.getAbsolutePath());

            String line = null;
            while ((line = bf.readLine()) != null) {
                System.out.println(line);
                out.println(line);
            }

            out.flush();
            out.close();
            bf.close();

        } catch (IOException ex){
            log.info("file not exist IOException => {} ",dto.getUuid()+ fileName);
        }finally {
            log.info("=======finally=======");
        }

        return null;
    }
*/

    /*
    // BufferedWriter 사용해서 읽어온 내용 test파일에 출력하기
    @GetMapping("/fileDownload")
    public String download3(@ModelAttribute FileDto dto){

        String fileName = dto.getFileName();
        log.info("fileName ==> {} ", fileName);

        //PrintWriter out = null;
        BufferedReader bf = null;
        BufferedWriter bw = null;
        try {

            //==============파일읽기==============
            File file = new File("C:\\develop\\test4\\" + fileName);
            bf = new BufferedReader(new FileReader(file));
            //====================================

            //==============파일쓰기==============
            File outFile = new File("C:\\develop\\test5\\test.txt"); // 해당 폴더에 파일은 들어가있어야함!
            if (!outFile.exists()){
                outFile.mkdir();
                log.info("======mkdir======");
            }
            OutputStream os = new FileOutputStream(outFile,true); // true 설정 기존 폴더 내용에 덮어쓰기
            bw = new BufferedWriter(new OutputStreamWriter(os));
            //====================================

            log.info("filePath => " + file.getAbsolutePath());
            String line = null;
            while ((line = bf.readLine()) != null) { // 읽기
                System.out.println(line);
                bw.write(line); // 쓰기
                bw.newLine();
            }

            //여기다 사용해도 됨
            //bw.flush();
            //bw.close();
            //bf.close();

        } catch (IOException ex){
            log.info("file not exist IOException => {} ", fileName);
        }finally { // 무조건 finally에서 close해야하는지 다시 찾아보기
            log.info("=======finally=======");
            try {
                if (bw != null){
                    bw.flush();
                    bw.close();
                    log.info("=======bw.close=======");
                }
                if (bf != null){
                    bf.close();
                    log.info("=======bw.close=======");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return "file/result4";
    }
    */


    /*
    // 파일 다운로드 OutputStream os = response.getOutputStream(); 사용 출력
    //             PrintWriter out = response.getWriter(); 사용 확인 차이 : Stream byte data , writer Character data
    @GetMapping("/fileDownload")
    public String download3(@ModelAttribute FileDto dto,HttpServletResponse response){

        String fileName = dto.getFileName();
        log.info("fileName ==> {} ", fileName);

        String encodingUploadFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);
        int index = encodingUploadFileName.indexOf(".");
        String rsFileName = encodingUploadFileName.substring(0,index);
        log.info("resultFileName ==> {}",rsFileName);

        response.setContentType("application/octet-stream");
        //response.setHeader("Content-Disposition","attachment; filename=\"" + dto.getUuid() + "_" + fileName + "\"");
        response.setHeader("Content-Disposition","attachment; filename=\"" + rsFileName + ".txt\"");
        response.setCharacterEncoding("UTF-8");

        // 읽어오기
        try {
            OutputStream os = response.getOutputStream();
            //풀기PrintWriter out = response.getWriter(); // 정상으로 읽어옴

            File file = new File("C:\\develop\\test4\\" + fileName);

            BufferedReader bf = new BufferedReader(new FileReader(file));
            log.info("filePath => " + file.getAbsolutePath());

            int line = 0;
            //풀기String line = null;
            while ((line = bf.read()) != -1) {
            //풀기while ((line = bf.readLine()) != null) {
                System.out.println((char)line); // 문자타입
                char cha = (char)line;
                String tt = String.valueOf(cha);
                os.write(tt.getBytes()); // 문자열을 바이트로 넘김
                //풀기System.out.println(line);
                //풀기out.println(line);
            }

            os.close();
            //풀기out.close();
            bf.close();

        } catch (IOException ex){
            log.info("file not exist IOException => {} ",dto.getUuid()+ fileName);
        }finally {
            log.info("=======finally=======");
        }

        return null;
    }
    */


    // 파일 업로드 마지막으로 해보기
    @GetMapping("/fileForm5")
    public String loginForm5() {
        log.info("=========fileForm=========");
        return "file/check";
    }


    // 파일 업로드 마지막으로 해보기
    @GetMapping("/fileForm4")
    public String loginForm4(@RequestParam(required = false) String originalFileName, Model model) {
        log.info("=========fileForm=========");
        model.addAttribute("originalFileName", originalFileName);
        return "file/fileUploadForm4";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("uploadFile") MultipartFile file, Model model) throws IOException { // 리다이렉트확인 RedirectAttributes redirectAttributes 사용

        String saveFileName = "";
        String uploadPath = "C:\\develop\\test6\\";
        String originalFileName = file.getOriginalFilename();
        log.info("originalFileName ==> {}", originalFileName);

        UUID uuid = UUID.randomUUID();
        saveFileName = uuid.toString() + "_" + originalFileName;

        File file1 = null;
        file1 = new File(uploadPath);
        if (!file1.exists())
            file1.mkdir();

        file1 = new File(uploadPath + saveFileName);
        file.transferTo(file1);

        FileVO data = new FileVO();
        data.setOriginalFileName(originalFileName);
        model.addAttribute("data" + data);

        //redirectAttributes.addAttribute("originalFileName",originalFileName); 리다이렉트로 확인
        //return "redirect:/fileForm4?originalFileName={originalFileName}"; 리다이렉트로 확인

        model.addAttribute("originalFileName",originalFileName);
        return "file/fileUploadResult";
    }



}
