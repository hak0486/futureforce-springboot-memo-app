package com.lesson.memo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  // パスワードの暗号化
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        // ログインページの許可設定
        .formLogin((form) -> form // フォーム認証を使う
        	.loginPage("/admin/signin") // ログインページのURLを指定
        	.usernameParameter("email")
        	.loginProcessingUrl("/admin/signin")
            .defaultSuccessUrl("/memo", true) // 認証成功時のデフォルトの遷移先
            .permitAll()
         )

        // リクエストの許可設定
        .authorizeHttpRequests(authz -> authz
            // index.html の参照権限
        	// 静的リソース（CSS, JS, 画像など）へのアクセスを無条件で許可する
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .requestMatchers("/admin/signup", "/admin/signin","/error")
            .permitAll().anyRequest().authenticated()
        )
    
    	//ログアウト機能の設定
    	.logout(logout -> logout
            .logoutSuccessUrl("/admin/signin")
            .permitAll()
        );
    
    return http.build();
  }

}