package com.example.calculatror.controller;

import com.example.calculatror.model.Iphone;
import com.example.calculatror.model.MacBook;
import com.example.calculatror.repo.IphoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Iphone")
public class IphoneController {

    @Autowired
    private IphoneRepository IphoneRepository;

    @GetMapping("/")
    public  String Index(Model model){
        Iterable<Iphone> iphones =  IphoneRepository.findAll();
        model.addAttribute("iphones", iphones);
        return "Iphone/index";
    }

    @GetMapping("/addView")
    public  String addView(Model model){

        return "/Iphone/add";
    }

    @PostMapping("/add")
    public  String add(
            @RequestParam("title") String title,
            @RequestParam("text") String text,
            @RequestParam("price") int price,
            @RequestParam("display") int display,
            @RequestParam("number") int number,
            Model model){

        Iphone iphone = new Iphone(title,text,price,display,number);
        IphoneRepository.save(iphone);
        return "redirect:/Iphone/";
    }

    @GetMapping("/search")
    public  String searchMetrhod(
            @RequestParam("title") String title,
            Model model) {
            List<Iphone> iphoneList = IphoneRepository.findByTitle(title);
            model.addAttribute("iphones", iphoneList);
            return "Iphone/index";
    }
    @GetMapping("/searchContains")
    public  String searchMetrhodContains(
            @RequestParam("title") String title,
            Model model)
    {
        List<Iphone> iphoneList = IphoneRepository.findByTitle(title);
        model.addAttribute("iphones", iphoneList);
        return "Iphone/index";
    }
    @GetMapping("/{id}")
    public  String read(@PathVariable("id") Long id, Model model)
    {
        ArrayList<Iphone> IphoneArray = new ArrayList<>();
        Optional<Iphone> IphonePost = IphoneRepository.findById(id);
        IphonePost.ifPresent(IphoneArray::add);
        model.addAttribute("iphones", IphoneArray);
        return "/Iphone/iphone-info";
    }
    @GetMapping("/iphone-del/{id}")
    public  String delete(@PathVariable("id") Long id, Model model)
    {
        IphoneRepository.deleteById(id);
        return "redirect:/Iphone/";
    }
    @GetMapping("/iphone-edit/{id}")
    public  String edit(@PathVariable("id") Long id, Model model)
    {
        if (!IphoneRepository.existsById(id))
        {
            return "redirect:/Iphone/";
        }
        ArrayList<Iphone> IphoneArray = new ArrayList<>();
        Optional<Iphone> IphonePost = IphoneRepository.findById(id);
        IphonePost.ifPresent(IphoneArray::add);
        model.addAttribute("iphones", IphoneArray);
        return "/Iphone/iphone-edit";
    }
    @PostMapping("/iphone-edit/{id}")
    public  String editpost(@PathVariable("id") Long id, Model model,
                            @RequestParam("title") String title,
                            @RequestParam("text") String text,
                            @RequestParam("price") int price,
                            @RequestParam("display") int display,
                            @RequestParam("number") int number)
    {
        Iphone iphons = IphoneRepository.findById(id).orElseThrow();
        iphons.setTitle(title);
        iphons.setText(text);
        iphons.setPrice(price);
        iphons.setDisplay(display);
        iphons.setNumber(number);
        IphoneRepository.save(iphons);
        return "redirect:/Iphone/";
    }
}
