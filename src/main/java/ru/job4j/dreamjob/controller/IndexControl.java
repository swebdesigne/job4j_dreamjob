package ru.job4j.dreamjob.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.dreamjob.utils.UserHttpSessionUtil;

@ThreadSafe
@Controller
public class IndexControl {

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        model.addAttribute("user", UserHttpSessionUtil.getUser(session));
        return "index";
    }
}
