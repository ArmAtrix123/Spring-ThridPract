package com.example.calculatror.controller;

import com.example.calculatror.model.Iphone;
import com.example.calculatror.model.MacBook;
import com.example.calculatror.repo.IphoneRepository;
import com.example.calculatror.repo.MacBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/MacBook")
public class MacBookController {

    @Autowired
    private MacBookRepository MacBookRepository;

    @GetMapping("/")
    public  String Index(Model model){
        Iterable<MacBook> macbooks =  MacBookRepository.findAll();
        model.addAttribute("macbooks", macbooks);
        return "MacBook/index";
    }

    @GetMapping("/addView")
    public  String addView(Model model){

        return "/MacBook/add";
    }

    @PostMapping("/add")
    public  String add(
            @RequestParam("title") String title,
            @RequestParam("text") String text,
            @RequestParam("color") String color,
            @RequestParam("price") int price,
            @RequestParam("size") int size,
            Model model){

        MacBook macbook = new MacBook(title,text,color,price,size);
        MacBookRepository.save(macbook);
        return "redirect:/MacBook/";
    }

    @GetMapping("/search")
    public  String searchMetrhod(
            @RequestParam("title") String title,
            Model model) {
        List<MacBook> macBookList = MacBookRepository.findByTitle(title);
        model.addAttribute("macbooks", macBookList);
        return "MacBook/index";
    }
    @GetMapping("/searchContains")
    public  String searchMetrhodContains(
            @RequestParam("title") String title,
            Model model)
    {
        List<MacBook> macBookList = MacBookRepository.findByTitleContains(title);
        model.addAttribute("macbooks", macBookList);
        return "MacBook/index";
    }
    @GetMapping("/{id}")
    public  String read(@PathVariable("id") Long id, Model model)
    {
        ArrayList<MacBook> MacBookArray = new ArrayList<>();
        Optional<MacBook> MacBookPost = MacBookRepository.findById(id);
        MacBookPost.ifPresent(MacBookArray::add);
        model.addAttribute("macbooks", MacBookArray);
        return "/MacBook/mac-info";
    }
    @GetMapping("/mac-del/{id}")
    public  String delete(@PathVariable("id") Long id, Model model)
    {
        MacBookRepository.deleteById(id);
        return "redirect:/MacBook/";
    }
    @GetMapping("/mac-edit/{id}")
    public  String edit(@PathVariable("id") Long id, Model model)
    {
        if (!MacBookRepository.existsById(id))
        {
            return "redirect:/MacBook/";
        }
        ArrayList<MacBook> MacBookArray = new ArrayList<>();
        Optional<MacBook> MacBookPost = MacBookRepository.findById(id);
        MacBookPost.ifPresent(MacBookArray::add);
        model.addAttribute("macbooks", MacBookArray);
        return "/MacBook/mac-edit";
    }
    @PostMapping("/mac-edit/{id}")
    public  String editpost(@PathVariable("id") Long id, Model model,
                             @RequestParam("title") String title,
                             @RequestParam("text") String text,
                             @RequestParam("color") String color,
                             @RequestParam("price") int price,
                             @RequestParam("size") int size)
    {
        MacBook macs = MacBookRepository.findById(id).orElseThrow();
        macs.setTitle(title);
        macs.setText(text);
        macs.setColor(color);
        macs.setPrice(price);
        macs.setSize(size);
        MacBookRepository.save(macs);
        return "redirect:/MacBook/";
    }
}
