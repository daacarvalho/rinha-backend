package com.rinha.backend.rinha_backend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    PessoaRepository pessoaRepository;

    public PessoaController(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }
    @PostMapping("/cadastrar")
    public ResponseEntity<String> createPessoa(@RequestBody Pessoa pessoa) {
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        URI uri = URI.create("/pessoas/" + pessoaSalva.getId());

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getById(@PathVariable UUID id) {
        Pessoa pessoa = pessoaRepository.findById(id).get();
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("/busca-termo")
    public ResponseEntity<List<Pessoa>> findByTermo(@RequestParam String termoBusca) {

        if (termoBusca == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Pessoa> pessoas = pessoaRepository.findByTermo(termoBusca);

        return ResponseEntity.ok(pessoas);
    }
    @GetMapping("/count-pessoas")
    public ResponseEntity<String> createPessoa() {
        return ResponseEntity.ok(String.valueOf(pessoaRepository.findAll().size()));
    }

}
