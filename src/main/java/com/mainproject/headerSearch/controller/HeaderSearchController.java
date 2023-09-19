package com.mainproject.headerSearch.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface HeaderSearchController {
	
	public ResponseEntity<Map<String, Object>> search(@RequestParam("searchTerm") String searchTerm) throws Exception;
	public ModelAndView viewSearchResult(HttpServletRequest request, @RequestParam("results") String resultsJson, @RequestParam("searchTerm") String searchTerm) throws Exception;
}
