package org.example;

import org.example.entities.UserEntity;
import org.example.repository.UserRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:database.db";

        try (Connection connection = DriverManager.getConnection(url);
             Scanner scanner = new Scanner(System.in)) {

            createTableIfNotExists(connection);
            UserRepository userRepository = new UserRepository(connection);
            boolean running = true;

            while (running) {
                System.out.println("\n==========================");
                System.out.println("    GERENCIADOR DE USUÁRIOS    ");
                System.out.println("==========================");
                System.out.println("1 - Criar usuário");
                System.out.println("2 - Buscar usuário por ID");
                System.out.println("3 - Listar todos os usuários");
                System.out.println("4 - Deletar usuário por ID");
                System.out.println("5 - Sair");
                System.out.println("==========================");
                System.out.print("Escolha uma opção: ");

                int opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha

                switch (opcao) {
                    case 1:
                        System.out.println("\n>>> Criando um novo usuário <<<");
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Senha: ");
                        String senha = scanner.nextLine();
                        UserEntity newUser = new UserEntity(UUID.randomUUID(), nome, email, senha);
                        userRepository.save(newUser);
                        System.out.println("Usuário salvo com sucesso!\n");
                        break;

                    case 2:
                        System.out.print("\nDigite o ID do usuário: ");
                        String idBusca = scanner.nextLine();
                        Optional<UserEntity> foundUser = userRepository.findById(UUID.fromString(idBusca));
                        foundUser.ifPresentOrElse(
                                user -> System.out.println("Usuário encontrado: " + user.getNome() + " - " + user.getEmail()),
                                () -> System.out.println("Usuário não encontrado."));
                        System.out.println();
                        break;

                    case 3:
                        System.out.println("\n>>> Lista de Usuários <<<");
                        List<UserEntity> users = userRepository.findAll();
                        if (users.isEmpty()) {
                            System.out.println("Nenhum usuário cadastrado.");
                        } else {
                            users.forEach(user -> System.out.println(user.getUuid() + " | " + user.getNome() + " | " + user.getEmail()));
                        }
                        System.out.println();
                        break;

                    case 4:
                        System.out.print("\nDigite o ID do usuário a ser deletado: ");
                        String idDelete = scanner.nextLine();
                        userRepository.deleteById(UUID.fromString(idDelete));
                        System.out.println("Usuário deletado com sucesso!\n");
                        break;

                    case 5:
                        running = false;
                        System.out.println("\nEncerrando o sistema... Até mais!\n");
                        break;

                    default:
                        System.out.println("\nOpção inválida. Tente novamente.\n");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTableIfNotExists(Connection connection) {
        String query = "CREATE TABLE IF NOT EXISTS users (" +
                "uuid TEXT PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "email TEXT NOT NULL UNIQUE, " +
                "senha TEXT NOT NULL)";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
