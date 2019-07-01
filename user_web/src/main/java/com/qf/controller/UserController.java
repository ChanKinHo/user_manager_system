package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Email;
import com.qf.entity.User;
import com.qf.service.IEmailService;
import com.qf.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Reference
    IEmailService emailService;

    @Reference
    IUserService userService;

    @RequestMapping("/toRegister")
    public String toRegister(){

        return "register";
    }
    @RequestMapping("/toLogin")
    public String toLogin(){

        return "login";
    }

    @RequestMapping("/sendCode")
    @ResponseBody
    public void sendCode(@RequestParam String email, HttpSession session){

        int a = (int)(10000+Math.random()*(99999-10000+1));

        System.out.println(email+a);

        session.setAttribute("code",a);

        emailService.sendCode(email,a);

    }

    @RequestMapping("/register")
    public String addUser(User user, HttpSession session, Model model){

         Integer code = (Integer) session.getAttribute("code");

        System.out.println(code);
        System.out.println(user);
        //System.out.println(code.equals(user.getCode()));

        if (code != null){
            if (code.equals(user.getCode())){
                userService.addUser(user);
                session.invalidate();
                return "login";
            }
        } else {
             session.invalidate();
             model.addAttribute("msg","验证失败！");
         }
        return "register";
    }

    @RequestMapping("/login")
    public String loginCheck(User user){

        Map<String,Object> map = new HashMap<>();
        map.put("username",user.getUsername());
        map.put("password",user.getPassword());

        List<User> users = userService.checkUser(map);
        if (user != null){
            return "welcome";
        }

        return "login";
    }

    @RequestMapping("/toFind")
    public String toFind(){
        return "findPassword";
    }

    @RequestMapping("/findPassword")
    public String findPassword(String username,Model model){

        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        List<User> users = userService.checkUser(map);
        boolean flag = false;

        try {
            if (users!=null){
                User user = users.get(0);
                flag = true;
                Email email = new Email();
                email.setSender("chankinho@sina.com");
                email.setReceiver(user.getEmail());
                email.setTitle("找回密码");
                email.setContent("找回密码请点击http://localhost:8080/user/forgetPassword?id="+user.getId());
                emailService.sendUrl(email);

            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (flag){
                return "tips";
            }

            model.addAttribute("msg","查无该用户名");
            return "findPassword";
        }
    }

    @RequestMapping("/forgetPassword")
    public String setNew(Integer id,Model model){

        model.addAttribute("id",id);
        return "setNew";
    }

    @RequestMapping("/setNew")
    public String setNewPassword(User user){

        userService.updateUser(user);

        return "redirect:/user/toLogin";
    }
}
