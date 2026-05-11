package com.lesson.memo.controller;

import java.time.LocalDateTime;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lesson.memo.model.Admin;
import com.lesson.memo.repository.AdminRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 管理者登録画面の表示
    @GetMapping("/signup")
    public String signUp(Model model) {
    	// 画面表示に必要な変数を用意
    	model.addAttribute("admin", new Admin());
        return "admin-signup";
    }
    
    // 管理者の登録処理
    @PostMapping("/signup") 
    public String create(
    		@ModelAttribute @Valid Admin admin,
            BindingResult result,
            Model model
    ) {      	
    	if (result.hasErrors()) {
    		return "admin-signup";
    	}
        admin.setCreatedAt(LocalDateTime.now());
        admin.setUpdatedAt(LocalDateTime.now());
        
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        
        adminRepository.save(admin);
        return "redirect:/admin/signin";
    }
    
    // 管理者認証画面の表示
    @GetMapping("/signin")
    public String signIn(Model model) {
    	// 画面表示に必要な変数を用意
    	model.addAttribute("admin", new Admin());
        return "admin-signin";
    }
}