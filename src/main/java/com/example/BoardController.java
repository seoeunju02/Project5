package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import static com.mysql.cj.conf.PropertyKey.logger;

@Controller
public class BoardController {

    @Autowired
    BoardServiceImpl boardService;


    /*@RequestMapping("/")
    public String home() {
        return "home";
    }*/

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model){
        Date date= new Date();
        DateFormat dateFormat=DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG,locale);
        String formattedDate=dateFormat.format(date);
        model.addAttribute("serverTime",formattedDate);
        return "home";
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String boardlist(Model model){
        model.addAttribute("list", boardService.getBoardList());
        return "list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public  String addPost(){
        return "addpostform";
    }

    @RequestMapping(value = "/addok", method = RequestMethod.POST)
    public String addPostOK(BoardVO vo){

        if(boardService.insertBoard(vo)==0) System.out.println("데이터 추가 실패");
        else System.out.println("데이터 추가 성공");
        return "redirect:list";
    }

    @RequestMapping(value = "/editform/{id}", method = RequestMethod.GET)
    public String editPost(@PathVariable("id") int id, Model model){
        BoardVO boardVO=boardService.getBoard(id);
        model.addAttribute("u",boardVO);
        return "editform";
    }

    @RequestMapping(value = "/editok", method = RequestMethod.POST)
    public String editPostOk(BoardVO vo){
        if(boardService.updateBoard(vo)==0) System.out.println("데이터 수정 실패");
        else System.out.println("데이터 수정 성공");
        return "redirect:list";
    }

    @RequestMapping(value = "/deleteok/{id}", method = RequestMethod.GET)
    public String deletePostOk(@PathVariable("id") int id){
        if(boardService.deleteBoard(id)==0) System.out.println("데이터 삭제 실패");
        else System.out.println("데이터 삭제 성공");
        return "redirect:/list";
    }


}
