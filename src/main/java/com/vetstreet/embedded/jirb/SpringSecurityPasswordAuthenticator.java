package com.vetstreet.embedded.jirb;

import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.session.ServerSession;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class SpringSecurityPasswordAuthenticator implements PasswordAuthenticator {
	
	AuthenticationProvider provider;
	String authorizingRole;
	
	public boolean authenticate(String username, String password, ServerSession arg2) {
		System.out.println("AUTH RUNNING!@");
		try{
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
			Authentication authentication = provider.authenticate(token);
			if(authentication.isAuthenticated())
			{

				System.out.println(" AUTH!");
				for(GrantedAuthority authority : authentication.getAuthorities())
				{
					System.out.println(authority.getAuthority());
					if(authority.getAuthority().equals(authorizingRole) || authority.getAuthority().equals("ROLE_"+authorizingRole))
						return true;
				}
			}else{
				System.out.println("NOT AUTH!");
			}
			return false;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public AuthenticationProvider getProvider() {
		return provider;
	}

	public void setProvider(AuthenticationProvider provider) {
		this.provider = provider;
	}

	public String getAuthorizingRole() {
		return authorizingRole;
	}

	public void setAuthorizingRole(String authorizingRole) {
		this.authorizingRole = authorizingRole;
	}

}
