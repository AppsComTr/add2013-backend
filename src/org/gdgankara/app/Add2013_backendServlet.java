package org.gdgankara.app;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Add2013_backendServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
