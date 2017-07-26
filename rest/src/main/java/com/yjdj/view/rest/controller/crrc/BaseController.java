package com.yjdj.view.rest.controller.crrc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by wangkai on 16/10/27.
 */
public abstract class BaseController {

    /**
     * 基类公共方法,类似拦截器,所有子类任何方法都会先执行以下方法
     */
    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response, Model model) {
        //TODO
    }
}
