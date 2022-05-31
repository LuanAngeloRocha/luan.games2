package application.controllers;
 
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
 
import application.models.Jogo;
import application.models.Plataforma;
import application.repositories.GeneroRepository;
import application.repositories.JogoRepository;
import application.repositories.PlataformaRepository;
 
@Controller
@RequestMapping("/jogos")
publicclass JogoController {​​​​​​​
    @Autowired
privateJogoRepository jogosRepo;
 
    @Autowired
privateGeneroRepository generosRepo;
 
    @Autowired
privatePlataformaRepository plataformasRepo;
 
    @RequestMapping("list")
publicString list(Model model) {​​​​​​​
        model.addAttribute("jogos", jogosRepo.findAll());
return"list.jsp"; 
    }​​​​​​​
 
    @RequestMapping("insert")
publicString formInsert(Model model) {​​​​​​​
        model.addAttribute("generos", generosRepo.findAll());
        model.addAttribute("plataformas", plataformasRepo.findAll());
return"insert.jsp";
    }​​​​​​​
 
    @RequestMapping(value = "insert", method = RequestMethod.POST)
publicString saveInsert(@RequestParam("titulo") String titulo,@RequestParam("genero") int generoId,
    @RequestParam("plataformas")int [] plataformas) {​​​​​​​
Jogo jogo = new Jogo();
        jogo.setTitulo(titulo);
        jogo.setGenero(generosRepo.findById(generoId).get());
for(int p: plataformas){​​​​​​​
Optional<Plataforma> plataforma = plataformasRepo.findById(p);
if(plataforma.isPresent())
                jogo.getPlataformas().add(plataforma.get());
 
        }​​​​​​​
 
        jogosRepo.save(jogo);
 
return"redirect:/jogos/list";
    }​​​​​​​
 
    @RequestMapping("update/{​​​​​​​id}​​​​​​​")
publicString formUpdate(Model model, @PathVariableint id) {​​​​​​​
Optional<Jogo> jogo = jogosRepo.findById(id);
 
if(!jogo.isPresent())
return"redirect:/jogos/list";
 
        model.addAttribute("jogos", jogo.get());
        model.addAttribute("generos", generosRepo.findAll());
        model.addAttribute("plafaformas", plataformasRepo.findAll());
return"/jogos/update.jsp";


    }​​​​​​​
 
    @RequestMapping(value = "update", method = RequestMethod.POST)
publicString saveUpdate(@RequestParam("titulo") String titulo, @RequestParam("id") int id,
    @RequestParam("genero") int generoId, @RequestParam(name="plataformas", required =false)int[] plafaformas) {​​​​​​​
Optional<Jogo> jogo = jogosRepo.findById(id);
if(!jogo.isPresent())
return"redirect:/jogos/list";
        jogo.get().setTitulo(titulo);
        jogo.get().setGenero(generosRepo.findById(generoId).get());
Set<Plataforma> updatePlataforma = newHashSet<>();

if(plataformas!=null)
for(int p: plataformas){​​​​​​​
Optional<Plataforma> plataforma = plataformasRepo.findById(p);
if(plataforma.isPresent())
                    updatePlataforma.add(plataforma.get());
            }​​​​​​​
        jogo.get().setPlataformas(updatePlataforma);
        jogosRepo.save(jogo.get());
return"redirect:/jogos/list";
    }​​​​​​​
 
    @RequestMapping("delete/{​​​​​​​id}​​​​​​​")
publicString formDelete(Model model, @PathVariableint id) {​​​​​​​
Optional<Jogo> jogo = jogosRepo.findById(id);
if(!jogo.isPresent())
return"redirect:/jogos/list";
        model.addAttribute("jogo", jogo.get());
return"/jogos/delete.jsp";
    }​​​​​​​
 
    @RequestMapping(value = "delete", method = RequestMethod.POST)
publicString confirmDelete(@RequestParam("id") int id) {​​​​​​​
        jogosRepo.deleteById(id);
return"redirect:/jogos/list";
    }​​​​​​​
}​​​​​​​
