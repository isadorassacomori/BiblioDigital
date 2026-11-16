package br.edu.atitus.bibliodigital.repositories;


import br.edu.atitus.bibliodigital.entities.Livro;

import java.util.ArrayList;
import java.util.List;

public class LivroRepository {
    private List<Livro> acervo = new ArrayList<>();

    public List<Livro> findAll() {
        return new ArrayList<>(acervo);
    }

    public void inserir(Livro livro) {

        acervo.add(livro);
    }
    public List<Livro> findByTitulo(String parametro) {
        List<Livro> resultado = new ArrayList<>();
        for (Livro livro :acervo) {
            /*String titulo = livro.getTitulo();
            String tituloUpper = titulo.toUpperCase();
            String parametroUpper = parametro.toUpperCase();
            if (tituloUpper.contains(parametroUpper))*/
            if (livro.getTitulo().toUpperCase().contains(parametro.toUpperCase())) {
                resultado.add(livro);
            }
        }
        return resultado;
    }

    public List<Livro> findByAutor(String parametro) {
        List<Livro> resultado = new ArrayList<>();

        for (Livro livro : acervo) {
            if (livro.getAutor().toUpperCase().contains(parametro.toUpperCase())) {
                resultado.add(livro);
            }
        }

        return resultado;
    }

    public Livro findByIndex(int indice) {
        if (indice < 1 || indice > acervo.size()) {
            return null;
        }

        return acervo.get(indice - 1);
    }

    public boolean alterar(int indice, Livro livro) {
        if (indice < 1 || indice > acervo.size()) {
            return false;
        }

        acervo.set(indice - 1, livro);
        return true;
    }

    public boolean removeByIndex(int indice) {
        if (indice < 1 || indice > acervo.size()) {
            return false;
        }

        acervo.remove(indice - 1);
        return true;
    }
}
