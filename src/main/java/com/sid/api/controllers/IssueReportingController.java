package com.sid.api.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sid.api.services.IssueReportingService;

@CrossOrigin
@RequestMapping("/issue")
@RestController
public class IssueReportingController {
	
	@Autowired
	private IssueReportingService issueReportingService;
	
	@PostMapping("/reportIssue")
	public Map<String, String> reportIssue(@RequestBody Map<String, String> request){
		return issueReportingService.reportIssue(request);
	}

}
