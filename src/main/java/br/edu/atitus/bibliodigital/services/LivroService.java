
package br.edu.atitus.bibliodigital.services;

import br.edu.atitus.bibliodigital.entities.Livro;
import br.edu.atitus.bibliodigital.repositories.LivroRepository;

import java.util.List;

public class LivroService {

    private final LivroRepository repository = new LivroRepository();

    private void validarLivro(Livro livro, Livro livroAtual) {
        if (livro == null)
            throw new RuntimeException("Objeto Nulo");

        if (livro.getTitulo() == null || livro.getTitulo().isBlank())
            throw new RuntimeException("Título Inválido");

        if (livro.getAutor() == null || livro.getAutor().isBlank())
            throw new RuntimeException("Autor Inválido");

        if (livro.getAnoPublicacao() < 1900)
            throw new RuntimeException("Ano de publicação inválido");

        livro.setTitulo(livro.getTitulo().trim());
        livro.setAutor(livro.getAutor().trim());

        List<Livro> encontrados = repository.findAll();

        for (Livro existente : encontrados) {
            if (existente != livroAtual &&
                    existente.getTitulo().equalsIgnoreCase(livro.getTitulo())) {
                throw new RuntimeException("Já existe um livro com esse título");
            }
        }
    }

    public void inserir(Livro livro) {
        validarLivro(livro, null);
        repository.inserir(livro);
    }

    public List<Livro> findAll() {
        return repository.findAll();
    }

    public List<Livro> findByTitulo(String parametro) {
        if (parametro == null || parametro.isBlank())
            throw new RuntimeException("Parâmetro inválido");

        return repository.findByTitulo(parametro.trim());
    }

    public List<Livro> findByAutor(String parametro) {
        if (parametro == null || parametro.isBlank())
            throw new RuntimeException("Parâmetro inválido");

        return repository.findByAutor(parametro.trim());
    }

    public void alterar(int indice, Livro livro) {
        Livro livroAtual = repository.findByIndex(indice);

        if (livroAtual == null)
            throw new RuntimeException("Índice inválido");

        validarLivro(livro, livroAtual);

        if (!repository.alterar(indice, livro))
            throw new RuntimeException("Não foi possível alterar o livro");
    }

    public void excluir(int indice) {
        Livro livro = repository.findByIndex(indice);

        if (livro == null)
            throw new RuntimeException("Índice inválido");

        if (!repository.removeByIndex(indice))
            throw new RuntimeException("Não foi possível excluir o livro");
    }
}
