package yar.project.springdude.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yar.project.springdude.models.Post;
import yar.project.springdude.repo.PostRepo;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class YarController {

    @Autowired
    private PostRepo postRepo;//переменная, для работы с записями из таблицы бд

    @GetMapping("/yar")
    public String yar(Model model) {
        Iterable<Post> posts = postRepo.findAll(); // Массив данных, в котором будут все данные, полученые из таблицы бд, findAll() вытягивает все записи из таблицы
        model.addAttribute("posts", posts);//передаем в шаблон ниже записи таблицы
        return "blog-yar";
    }

    @GetMapping("/yar/add")
    public String yarAdd(Model model) {
        return "add-yar";
    }

    @PostMapping("/yar/add")
    public String postYarAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String text, Model model) {
        Post post = new Post(title, anons, text);
        postRepo.save(post);
        return "redirect:/yar";
    }

    @GetMapping("/yar/{id}")
    public String yarDetail(@PathVariable(value = "id") long id, Model model) {
        if(!postRepo.existsById(id)) return "redirect:/yar";

        Optional<Post> post = postRepo.findById(id);//вытаскиваем запись по айди
        ArrayList<Post> res = new ArrayList<>();//Создаем лист для работы с Optional
        post.ifPresent(res::add);//переводим объект Optional в лист
        model.addAttribute("post", res);//Передаем наш объект в шаблон
        return "detail-yar";
    }

    @GetMapping("/yar/{id}/edit")
    public String yarEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepo.existsById(id))
            return "redirect:/yar";

        Optional<Post> post = postRepo.findById(id);//вытаскиваем запись по айди
        ArrayList<Post> res = new ArrayList<>();//Создаем лист для работы с Optional
        post.ifPresent(res::add);//переводим объект Optional в лист
        model.addAttribute("post", res);//Передаем наш объект в шаблон
        return "edit-yar";
    }


    @PostMapping("/yar/{id}/edit")
    public String postYarUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String text, Model model) {
       Post post = postRepo.findById(id).orElseThrow();
       post.setTitle(title);
       post.setAnons(anons);
       post.setText(text);
       postRepo.save(post);
        return "redirect:/yar";
    }

    @PostMapping("/yar/{id}/remove")
    public String postYarDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepo.findById(id).orElseThrow();
        postRepo.delete(post);
        return "redirect:/yar";
    }

}
