package org.example.repository;

import org.example.entities.UserEntity;
import org.sqlite.SQLiteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepository implements RepositoryEntity {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(UserEntity userEntity) {
        String query = "INSERT INTO users VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userEntity.getUuid().toString());
            statement.setString(2, userEntity.getNome());
            statement.setString(3, userEntity.getEmail());
            statement.setString(4, userEntity.getSenha());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserEntity> findById(UUID id) {
        String query = "SELECT * FROM users WHERE uuid = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UserEntity user = new UserEntity(
                    UUID.fromString(rs.getString("uuid")),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha")
                );

                return Optional.of(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<UserEntity> findAll() {
        String query = "SELECT * FROM users";
        List<UserEntity> users = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UserEntity user = new UserEntity(
                    UUID.fromString(rs.getString("uuid")),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha")
                );

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void deleteById(UUID id) {
        String query = "DELETE FROM users WHERE uuid = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, id.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
