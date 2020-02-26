package qas.uicontroller.helper;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class menuHelper extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestURI = request.getRequestURI();

        //Процессы

        if (requestURI.equals("/processForm") || requestURI.equals("/showMyProcesses")) {
            modelAndView.addObject("menu", "process");
            if (requestURI.equals("/processForm")) {
                modelAndView.addObject("active", "processForm");
            }
            if (requestURI.equals("/showMyProcesses")) {
                modelAndView.addObject("active" , "showMyProcesses");
            }
        }

        //Пользователи

        if (requestURI.equals("/allusers") || requestURI.equals("/addUserRole")) {
            modelAndView.addObject("menu", "users");
            if (requestURI.equals("/allusers"))
                 modelAndView.addObject("active", "allusers");
            if (requestURI.equals("/addUserRole"))
                 modelAndView.addObject("active", "addUserRole");
        }

        //Админка-роли

        if (requestURI.equals("/roles") || requestURI.equals("/admin/addRoleForm")) {
            modelAndView.addObject("menu", "role");
            if (requestURI.equals("/roles"))
                modelAndView.addObject("active", "roles");
            if (requestURI.equals("/admin/addRoleForm"))
                modelAndView.addObject("active", "addRoleForm");
        }

        //Админка-типы процессов

        if (requestURI.equals("/getAllProcessTypes") || requestURI.equals("/admin/getProcessTypeForm")) {
            modelAndView.addObject("menu", "processType");
            if (requestURI.equals("/getAllProcessTypes"))
                modelAndView.addObject("active", "getAllProcessTypes");
            if (requestURI.equals("/admin/getProcessTypeForm"))
                modelAndView.addObject("active", "getProcessTypeForm");
        }

        //Админка-стадии

        if (requestURI.equals("/getAllProcessStages") || requestURI.equals("/admin/getProcessStageForm")) {
            modelAndView.addObject("menu", "processStage");
            if (requestURI.equals("/getAllProcessStages"))
                modelAndView.addObject("active", "getAllProcessStages");
            if (requestURI.equals("/admin/getProcessStageForm"))
                modelAndView.addObject("active", "getProcessStageForm");
        }
        super.postHandle(request, response, handler, modelAndView);
    }
}
