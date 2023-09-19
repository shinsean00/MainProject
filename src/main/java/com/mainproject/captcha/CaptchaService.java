package com.mainproject.captcha;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.stream.Collectors;

@Service
public class CaptchaService {

	private static final String CLIENT_ID = "xhb9tw1ld7"; // 애플리케이션 클라이언트 아이디값
	private static final String CLIENT_SECRET = "gDAmFlZz2q4SaHnoD4Jrc1wGyi4zd1oqu87AfgBx"; // 애플리케이션 클라이언트 시크릿값

	// 네이버 캡차 API - 키발급
	public String generateCaptchaKey() {
		try {
			String code = "0"; // 키 발급시 0, 캡차 이미지 비교시 1로 세팅
			String apiURL = "https://naveropenapi.apigw.ntruss.com/captcha/v1/nkey?code=" + code;
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", CLIENT_ID);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", CLIENT_SECRET);

			StringBuilder response = new StringBuilder();

			// try-with-resources문으로 리소스 자동으로 닫기
			try (BufferedReader br = (con.getResponseCode() == 200)
					? new BufferedReader(new InputStreamReader(con.getInputStream()))
					: new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
				String inputLine;
				while ((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}
			}
			
			JSONObject jsonResponse = new JSONObject(response.toString());// response가 JSON 형태 - { "key": "YOUR_GENERATED_CAPTCHA_KEY" }
			return jsonResponse.getString("key"); // 키 값만 직접 반환
		} catch (Exception e) {
			throw new RuntimeException("CAPTCHA 키 생성 중 오류 발생: " + e.getMessage(), e);
		}
	}

	// 네이버 캡차 API - 캡차 이미지 수신
	// captcha 이미지 서버에 저장하지 않고 Base64로 인코딩 후 페이지로 전송하는 로직으로 변경
    public String getCaptchaImageAsBase64(String key) {
        try {
            String apiURL = "https://naveropenapi.apigw.ntruss.com/captcha-bin/v1/ncaptcha?key=" + key + "&X-NCP-APIGW-API-KEY-ID=" + CLIENT_ID;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();

            if(responseCode == 200) {
                InputStream is = con.getInputStream();
                byte[] imageBytes = is.readAllBytes();
                return Base64.getEncoder().encodeToString(imageBytes);
            } else {
                StringBuilder errorResponse = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        errorResponse.append(line);
                    }
                }
                throw new RuntimeException("CAPTCHA 이미지 가져오기 실패: " + errorResponse.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("CAPTCHA 이미지 가져오는 중 오류 발생: " + e.getMessage(), e);
        }
    } 
    
    // 네이버 캡챠 API - 입력값 비교
    public boolean checkCaptchaKeyResult(String key, String value) {
        try {
            String code = "1"; // 키 발급시 0, 캡차 이미지 비교시 1로 세팅
            String apiURL = "https://naveropenapi.apigw.ntruss.com/captcha/v1/nkey?code=" + code + "&key=" + key + "&value=" + value;

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", CLIENT_ID);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", CLIENT_SECRET);
            
            int responseCode = con.getResponseCode();
            
            // try-with-resources문으로 리소스 자동으로 닫기
            if (responseCode == 200) { // 정상 호출
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String response = br.lines().collect(Collectors.joining());
                    JSONObject jsonResponse = new JSONObject(response);
                    return jsonResponse.getBoolean("result");
                }
            } else {
                throw new RuntimeException("CAPTCHA 검증 실패: 응답 코드 " + responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("CAPTCHA 검증 중 오류 발생: " + e.getMessage(), e);
        }
    }

}