package com.blog.realm;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
import com.blog.util.Const;

public class MyRealm extends AuthorizingRealm{
	
	@Resource
	private BloggerService bloggerService;
	/**
	 * 获取授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		return null;
	}
	/**
	 * 登录验证
	 * token:令牌，基于用户名和密码的令牌
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//从令牌中取出用户名
		String username = (String)token.getPrincipal();
		//让shiro去验证用户名是否存在，存在就继续验证
		Blogger blogger = bloggerService.getByUsername(username);
		if(blogger!=null) {
			AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(blogger.getUsername(), blogger.getPassword(),getName());
			return authenticationInfo;
		}
		return null;
	}

}
