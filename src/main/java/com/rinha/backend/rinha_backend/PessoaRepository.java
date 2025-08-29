package com.rinha.backend.rinha_backend;

import com.rinha.backend.rinha_backend.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {


    @Query(value = """
    SELECT * FROM pessoas p
    WHERE p.nome ILIKE '%' || :termo || '%'
       OR p.apelido ILIKE '%' || :termo || '%'
       OR EXISTS (
            SELECT 1
            FROM unnest(p.stack) AS val
            WHERE val ILIKE '%' || :termo || '%'
       )
    """, nativeQuery = true)
    List<Pessoa> findByTermo(@Param("termo") String termo);
}
