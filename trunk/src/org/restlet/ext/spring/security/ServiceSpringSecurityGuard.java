package org.restlet.ext.spring.security;

import org.restlet.Guard;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Request;
import org.restlet.data.ChallengeResponse;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.BeansException;
import org.springframework.security.*;
import org.springframework.security.util.FilterInvocationUtils;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterSecurityInterceptor;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.util.Assert;

import java.util.Map;

public class ServiceSpringSecurityGuard extends Guard  implements ApplicationContextAware, InitializingBean {

	private AuthenticationManager authentificationManager;
	private ApplicationContext applicationContext;

	public ServiceSpringSecurityGuard() {
		super(null, ChallengeScheme.HTTP_BASIC, "Spring Security");
	}

	public AuthenticationManager getAuthentificationManager() {
		return authentificationManager;
	}

	public void setAuthentificationManager(AuthenticationManager authentificationManager) {
		this.authentificationManager = authentificationManager;
	}

//	private AccessDecisionManager accessDecisionManager;

//	public AccessDecisionManager getAccessDecisionManager() {
//		return accessDecisionManager;
//	}
//
//	public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
//		this.accessDecisionManager = accessDecisionManager;
//	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.applicationContext, "applicationContext is null");

//		if (null == accessDecisionManager) {
//			setAccessDecisionManager((AccessDecisionManager) applicationContext.getBean(BeanIds.ACCESS_MANAGER));
//		}

		if (null == authentificationManager) {
			setAuthentificationManager((AuthenticationManager) applicationContext.getBean(BeanIds.AUTHENTICATION_MANAGER));
		}

		Assert.notNull(this.authentificationManager, "authentificationManager should be specified");
//		Assert.notNull(this.accessDecisionManager, "accessDecisionManager should be specified");
	}

	@SuppressWarnings("unused")
	public boolean checkSecret(Request request, String identifier, char[] secret) {
		try {
			Authentication auth = authentificationManager.authenticate(new UsernamePasswordAuthenticationToken(identifier, new String(secret)));
			if (auth.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
			return auth.isAuthenticated();
		} catch (AuthenticationException e) {
			SecurityContextHolder.getContext().setAuthentication(null);
			return false;
		}
	}
}