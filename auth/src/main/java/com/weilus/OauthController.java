package com.weilus;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liutq on 2018/12/7.
 */
@Controller
public class OauthController {


    @RequestMapping("/login")
    public String login(){
        return "login";
    }


    @RequestMapping("/oauth/confirm_access")
    public String confirm_access(){
        return "confirm_access";
    }

    @RequestMapping("/oauth/error")
    public String oauth_error(){
        return "error";
    }
}
