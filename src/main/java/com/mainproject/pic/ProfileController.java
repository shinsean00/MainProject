package com.mainproject.pic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mainproject.pet.dao.PetDAO;
import com.mainproject.user.dao.UserDAO;

@Controller
public class ProfileController {
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PetDAO petDAO;
	
	
	private final String UPLOAD_PREFIX = "/img/user-profile/";
	private final String UPLOAD__PET_PREFIX = "/img/user-profile/";

	@PostMapping("/api/update-profile") // 프로필 사진 변경 로직
	public ResponseEntity<String> updateProfile(@RequestParam("profile_picture") MultipartFile file, Principal principal) {
		try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("프로필 사진이 없습니다.");
            }

            // 파일 저장 로직
            byte[] bytes = file.getBytes();
            String filename = file.getOriginalFilename();
            String userID = filename.replace(".png", "");
            String filepath = UPLOAD_PREFIX + filename;
            Path path = Paths.get("src/main/resources/static" + filepath);
            Files.write(path, bytes);
            
            Map<String, Object> params = new HashMap<>();
            params.put("filePath", filepath);
            params.put("userID", userID);
            userDAO.updateUserProfile(params);

            return ResponseEntity.ok("프로필 사진이 업데이트되었습니다.");
        } catch (org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException e) {
            // 파일 크기 제한 예외를 처리
            return ResponseEntity.badRequest().body("파일 크기가 10MB를 초과하였습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("프로필 사진 업데이트 중 오류가 발생했습니다.");
        }
	}
	

	@PostMapping("/api/update-pet-profile") // 펫 프로필 사진 변경 로직
	public ResponseEntity<String> updatePetProfile(@RequestParam("profile_picture") MultipartFile file, Principal principal) {
		try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("프로필 사진이 없습니다.");
            }

            // 파일 저장 로직
            byte[] bytes = file.getBytes();
            String filePetname = file.getOriginalFilename();
            String petNo = filePetname.replace(".png", "");
            String filepath = UPLOAD__PET_PREFIX + filePetname;
            Path path = Paths.get("src/main/resources/static" + filepath);
            Files.write(path, bytes);
            
            Map<String, Object> params = new HashMap<>();
            params.put("filePath", filepath);
            params.put("petNo", petNo);
            petDAO.updatePetProfile(params);

            return ResponseEntity.ok("프로필 사진이 업데이트되었습니다.");
        } catch (org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException e) {
            // 파일 크기 제한 예외를 처리
            return ResponseEntity.badRequest().body("파일 크기가 10MB를 초과하였습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("프로필 사진 업데이트 중 오류가 발생했습니다.");
        }
	}	
}
