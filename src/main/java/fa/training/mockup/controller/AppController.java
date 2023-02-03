/*
 *
 *
 * Project ProductManager
 * Copyright (C) $year by Fanglong-it. All Rights Reserved.
 * For more information : Fang.longpc@gmail.com
 * Example project exist at : https://github.com/fanglong-it/
 * 11/12/21, 10:08 PM
 *
 *
 */

package fa.training.mockup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/")
    public String homePage(){
        return "redirect:course/";
    }




}