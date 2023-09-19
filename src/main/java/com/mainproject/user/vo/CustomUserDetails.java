package com.mainproject.user.vo;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {	
	private static final long serialVersionUID = 1L;

	
    private final String username;
    private final String password;
    private final UserVO user;
    private final int usernum; 
//    private final UserRank rank;
 
    public CustomUserDetails(UserVO user) {
        this.user = user;
        this.username = user.getId();
        this.password = user.getPwd();
        this.usernum = user.getUser_num(); 
//        this.rank = user.getRank();
    }
     
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	 String rolePrefix = "ROLE_";
    	    String role = rolePrefix + user.getIs_admin().toUpperCase(); // "ROLE_ADMIN" 또는 "ROLE_USER" 등으로 변환
    	    return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    
    public String getNickname() {
        return user.getNickname();
    }

    public int getUsernum() {
        return user.getUser_num();
    }

    public String getProfile_picture() {
        return user.getProfile_picture();
    }
    
    public UserRank getRank() {
        return user.getRank();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public UserVO getUser() {
        return user;
    }
}
