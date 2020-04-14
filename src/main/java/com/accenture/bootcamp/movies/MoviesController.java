package com.accenture.bootcamp.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MoviesController {

    public final MovieRepository movieRepository;

    @Autowired
    public MoviesController(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("movies", movieRepository.findAll());
        return "index";
    }
    @GetMapping("/signup")
    public String showSignUpForm(Movie movie){
        return "add-movie";
    }

    @PostMapping("/addmovie")
    public String addMovie(@Valid Movie movie, Model model){
        movieRepository.save(movie);
        model.addAttribute("movies", movieRepository.findAll());
        return "index";
    }
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: "+ id));
        model.addAttribute("movie", movie);
        return "update-movie";
    }

    @PostMapping("/update/{id}")
    public String updateMovie (@PathVariable("id") String id, @Valid Movie movie, Model model) {
        movieRepository.save(movie);
        model.addAttribute("movies", movieRepository.findAll());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable("id") String id, Model model){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: "+ id));
        movieRepository.delete(movie);
        model.addAttribute("movies", movieRepository.findAll());
        return "index";


    }
}
