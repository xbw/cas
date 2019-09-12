package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;

/**
 * 该过滤器用户从CAS认证服务器中获取登录用户用户名，并填充必要的Session.
 * 
 */
@WebFilter(urlPatterns = { "/*" })
public class CasSetUserAdapterFilter implements Filter {
	private static final Logger logger = Logger.getLogger(CasSetUserAdapterFilter.class);

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 第一查询我的session中有没有值
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();

		// 如果session中没有用户信息，则填充用户信息
		if (session.getAttribute("j_userId") == null) {
			// 从Cas服务器获取登录账户的用户名
			Assertion assertion = AssertionHolder.getAssertion();
			String userName = assertion.getPrincipal().getName();
			logger.info("session id>>" + session.getId() + ", userName>>" + userName);
			try {
				// 根据单点登录的账户的用户名，从数据库用户表查找用户信息， 填充到session中
				session.setAttribute("userName", userName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}
}