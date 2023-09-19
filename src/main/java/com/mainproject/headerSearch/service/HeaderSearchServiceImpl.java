package com.mainproject.headerSearch.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mainproject.board.vo.BoardVO;
import com.mainproject.headerSearch.dao.HeaderSearchDAO;
import com.mainproject.user.service.UserService;

@Service
public class HeaderSearchServiceImpl implements HeaderSearchService {
	
	@Autowired
	private HeaderSearchDAO headerSearchDAO; 
	
	@Value("${openai.api.key}")
	private String AUTH_KEY;
	
    private static final String OPENAI_ENDPOINT = "https://api.openai.com/v1/completions";
	
	@Override // 검색결과 반환 로직
	public List<Integer> getSearchResults(String searchTerm) throws DataAccessException {
		List<Integer> results = new ArrayList<Integer>();
		try {
        	String userInput = searchTerm.trim();
        	String promptInput = "검색어 '" + userInput + "'에서 단어만 추출해줘";
        	
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(OPENAI_ENDPOINT);

            // API Key 설정
            httpPost.setHeader("Authorization", "Bearer " + AUTH_KEY);
            httpPost.setHeader("Content-Type", "application/json");

            // 입력 데이터 설정
            String jsonInput = String.format("{\"model\":\"text-davinci-003\", \"prompt\":\"" + promptInput + "\", \"max_tokens\":1000}");
            StringEntity entity = new StringEntity(jsonInput, "UTF-8");
            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            // 결과 출력
            String result = EntityUtils.toString(responseEntity);
            System.out.println(result);
            
            // 결과에서 "choices" 키의 값을 추출
            JSONArray choicesArray = new JSONObject(result).getJSONArray("choices");
            if (choicesArray.length() > 0) {
                JSONObject firstChoice = choicesArray.getJSONObject(0);
                String textResult = firstChoice.getString("text").trim();

                // 특수 문자 제거
                textResult = textResult.replaceAll("[\n-'\"]", "").trim();

                // 결과를 "," 기준으로 분리하고 출력
                String[] keywordsArray = textResult.split(",");
                List<String> keywords = new ArrayList<>(Arrays.asList(keywordsArray));
                results = headerSearchDAO.getSearchResults(keywords);
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
		if (results.isEmpty()) { // 검색한 결과가 없다면 -1 반환
	        results.add(-1);
	    }
		return results;		
	}
	
	@Override // GPT 답변 로직
	public String getGptResponse(String searchTerm) throws DataAccessException {
	    String GptResponse = "AI 작동 중 오류가 발생했습니다."; // 기본 에러 메시지로 초기화
	    try {
	        String userInput = searchTerm.trim();
	        String promptInput = "검색어 '" + userInput + "'에 대해 1~3 줄로 답변해줘";
	        
	        HttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(OPENAI_ENDPOINT);

	        // API Key 설정
	        httpPost.setHeader("Authorization", "Bearer " + AUTH_KEY);
	        httpPost.setHeader("Content-Type", "application/json");

	        // 입력 데이터 설정
	        String jsonInput = String.format("{\"model\":\"text-davinci-003\", \"prompt\":\"" + promptInput + "\", \"max_tokens\":1000}");
	        StringEntity entity = new StringEntity(jsonInput, "UTF-8");
	        httpPost.setEntity(entity);

	        HttpResponse response = httpClient.execute(httpPost);
	        HttpEntity responseEntity = response.getEntity();
	        int statusCode = response.getStatusLine().getStatusCode();
	        if (statusCode != 200) {
	            // 요청 처리 실패
	            return "AI 요청에 실패했습니다. 상태 코드: " + statusCode;
	        }
	        
	        // 결과 출력
	        String result = EntityUtils.toString(responseEntity);
	        System.out.println(result);
	        
	        // 결과에서 "choices" 키의 값을 추출
	        JSONArray choicesArray = new JSONObject(result).getJSONArray("choices");
	        if (choicesArray.length() > 0) {
	            JSONObject firstChoice = choicesArray.getJSONObject(0);
	            String textResult = firstChoice.getString("text").trim();

	            // 개행 문자 제거
	            textResult = textResult.replace("\n", "").trim();

	            GptResponse = textResult;
	        }            
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return GptResponse; // 오타 수정
	}
	
	@Override // 게시글 번호로 게시글 조회
	public List<BoardVO> getBoardResults(List<Integer> postNumbers) throws DataAccessException {
		return headerSearchDAO.getBoardResults(postNumbers);
	}
}
