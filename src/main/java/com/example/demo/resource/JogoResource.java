package com.example.demo.resource;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Jogo;
import com.example.demo.repository.JogoRepository;

@RestController
@RequestMapping("/jogos")
public class JogoResource {

	@Autowired
	private JogoRepository jogoRepository;

	@GetMapping
	public List<Jogo> listarJogos() {
		return jogoRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Jogo> salvarJogo(@Valid @RequestBody Jogo jogo) {
		Jogo jogoDb = jogoRepository.save(jogo);
		return ResponseEntity.ok(jogoDb);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Jogo> atualizar(@PathVariable Long id, @Valid @RequestBody Jogo jogo) {
		Jogo jogoAtualizar = jogoRepository.getOne(id);
		BeanUtils.copyProperties(jogo, jogoAtualizar, "id");
		return ResponseEntity.ok(jogoAtualizar);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		jogoRepository.deleteById(id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Jogo> jogoPorId(@PathVariable Long id) {
		Optional<Jogo> jogo = jogoRepository.findById(id);
		ResponseEntity<Jogo> re = jogo.isPresent() ? ok(jogo.get()) : notFound().build();
		return re;
	}
}
