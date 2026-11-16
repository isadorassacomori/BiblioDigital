
package br.edu.atitus.bibliodigital.views;

import br.edu.atitus.bibliodigital.entities.Livro;
import br.edu.atitus.bibliodigital.entities.LivroFisico;
import br.edu.atitus.bibliodigital.entities.LivroDigital;
import br.edu.atitus.bibliodigital.entities.AudioLivro;
import br.edu.atitus.bibliodigital.services.LivroService;

import java.util.List;

public class BiblioDigitalApplication {

    private final LivroService service = new LivroService();

    void main() {
        int opcao = -1;

        while (opcao != 0) {
            IO.println("\n===== Bem vindo ao BiblioDigital =====");
            IO.println("Menu:");
            IO.println("1 - Cadastrar novo Livro");
            IO.println("2 - Pesquisar Livro por título");
            IO.println("3 - Pesquisar Livro por autor");
            IO.println("4 - Alterar dados do Livro");
            IO.println("5 - Excluir Livro");
            IO.println("0 - Sair");

            try {
                opcao = Integer.parseInt(IO.readln("Escolha uma das opções acima: "));

                switch (opcao) {
                    case 1:
                        cadastrarLivro();
                        break;

                    case 2:
                        pesquisarPorTitulo();
                        break;

                    case 3:
                        pesquisarPorAutor();
                        break;

                    case 4:
                        alterarLivro();
                        break;

                    case 5:
                        excluirLivro();
                        break;

                    case 0:
                        IO.println("Encerrando o sistema...");
                        break;

                    default:
                        IO.println("Opção inválida!");
                }

            } catch (RuntimeException ex) {
                IO.println("Erro: " + ex.getMessage());
            }

            if (opcao != 0) {
                IO.readln("Pressione ENTER para voltar ao menu...");
            }
        }
    }

    private void cadastrarLivro() {
        IO.println("\n=== CADASTRAR LIVRO ===");
        IO.println("1 - Livro Físico");
        IO.println("2 - Livro Digital");
        IO.println("3 - AudioLivro");

        int tipo = Integer.parseInt(IO.readln("Escolha o tipo: "));

        String titulo = IO.readln("Digite o título: ");
        String autor = IO.readln("Digite o autor: ");
        int ano = Integer.parseInt(
                IO.readln("Digite o ano de publicação: "));

        Livro novo = null;

        switch (tipo) {
            case 1:
                int quantidade = Integer.parseInt(
                        IO.readln("Quantidade de exemplares: "));

                String localizacao = IO.readln("Localização: ");

                novo = new LivroFisico(
                        titulo, autor, ano, quantidade, localizacao);
                break;

            case 2:
                String formato = IO.readln("Formato do arquivo: ");

                double tamanho = Double.parseDouble(
                        IO.readln("Tamanho do arquivo em MB: "));

                novo = new LivroDigital(
                        titulo, autor, ano, formato, tamanho);
                break;

            case 3:
                int duracao = Integer.parseInt(
                        IO.readln("Duração em minutos: "));

                String narrador = IO.readln("Nome do narrador: ");

                novo = new AudioLivro(
                        titulo, autor, ano, duracao, narrador);
                break;

            default:
                IO.println("Tipo de livro inválido!");
                return;
        }

        service.inserir(novo);

        IO.println("Livro cadastrado com sucesso!");
    }

    private void pesquisarPorTitulo() {
        IO.println("\n=== PESQUISAR LIVRO POR TÍTULO ===");

        String titulo = IO.readln("Digite o título ou parte dele: ");

        IO.println("Pesquisando por: " + titulo);

        List<Livro> encontrados = service.findByTitulo(titulo);

        IO.println("Livros encontrados: " + encontrados.size());

        if (encontrados.isEmpty()) {
            IO.println("Não existem resultados para essa pesquisa.");
        } else {
            for (Livro livro : encontrados) {
                IO.println(livro);

            }
        }
    }

    private void pesquisarPorAutor() {
        IO.println("\n=== PESQUISAR LIVRO POR AUTOR ===");

        String autor = IO.readln("Digite o nome ou parte do nome do autor: ");

        List<Livro> encontrados = service.findByAutor(autor);

        if (encontrados.isEmpty()) {
            IO.println("Não existem resultados para essa pesquisa.");
        } else {
            for (Livro livro : encontrados) {
                IO.println(livro);
            }
        }
    }

    private void alterarLivro() {
        IO.println("\n=== ALTERAR LIVRO ===");

        List<Livro> livros = service.findAll();

        if (livros.isEmpty()) {
            IO.println("Não existem livros cadastrados.");
            return;
        }

        for (int i = 0; i < livros.size(); i++) {
            IO.println((i + 1) + " - " + livros.get(i));
        }

        int indice = Integer.parseInt(
                IO.readln("Digite o número do livro que deseja alterar: "));

        if (indice < 1 || indice > livros.size()) {
            IO.println("Índice inválido!");
            return;
        }

        IO.println("\nDados atuais do livro:");
        IO.println(livros.get(indice - 1));

        IO.println("\nQual é o tipo do livro?");
        IO.println("1 - Livro Físico");
        IO.println("2 - Livro Digital");
        IO.println("3 - AudioLivro");

        int tipo = Integer.parseInt(IO.readln("Escolha o tipo: "));

        String titulo = IO.readln("Novo título: ");
        String autor = IO.readln("Novo autor: ");
        int ano = Integer.parseInt(
                IO.readln("Novo ano de publicação: "));

        Livro atualizado = null;

        switch (tipo) {
            case 1:
                int quantidade = Integer.parseInt(
                        IO.readln("Quantidade de exemplares: "));
                String localizacao = IO.readln("Localização: ");

                atualizado = new LivroFisico(
                        titulo, autor, ano, quantidade, localizacao);
                break;

            case 2:
                String formato = IO.readln("Formato do arquivo: ");
                double tamanho = Double.parseDouble(
                        IO.readln("Tamanho do arquivo em MB: "));

                atualizado = new LivroDigital(
                        titulo, autor, ano, formato, tamanho);
                break;

            case 3:
                int duracao = Integer.parseInt(
                        IO.readln("Duração em minutos: "));
                String narrador = IO.readln("Nome do narrador: ");

                atualizado = new AudioLivro(
                        titulo, autor, ano, duracao, narrador);
                break;

            default:
                IO.println("Tipo de livro inválido!");
                return;
        }

        service.alterar(indice, atualizado);

        IO.println("Livro alterado com sucesso!");
    }

    private void excluirLivro() {
        IO.println("\n=== EXCLUIR LIVRO ===");

        List<Livro> livros = service.findAll();

        if (livros.isEmpty()) {
            IO.println("Não existem livros cadastrados.");
            return;
        }

        for (int i = 0; i < livros.size(); i++) {
            IO.println((i + 1) + " - " + livros.get(i));
        }

        int indice = Integer.parseInt(
                IO.readln("Digite o número do livro que deseja excluir: "));

        if (indice < 1 || indice > livros.size()) {
            IO.println("Índice inválido!");
            return;
        }

        String confirmacao = IO.readln(
                "Tem certeza que deseja excluir este livro? (S/N): ");

        if (confirmacao.equalsIgnoreCase("S")) {
            service.excluir(indice);
            IO.println("Livro excluído com sucesso!");
        } else {
            IO.println("Exclusão cancelada.");
        }
    }
}
