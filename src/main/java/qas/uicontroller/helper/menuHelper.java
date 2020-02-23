package qas.uicontroller.helper;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class menuHelper extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/processForm") || requestURI.equals("/allProcesses")) {
            modelAndView.addObject("menu", "process");
            if (requestURI.equals("/processForm")) {
                modelAndView.addObject("active", "processForm");
            }
            if (requestURI.equals("/allProcesses")) {
                modelAndView.addObject("active" , "allProcesses");
            }
        }
        super.postHandle(request, response, handler, modelAndView);
    }
}
