package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;

@ApplicationScoped
public class MessageDao {
	@Inject
	DataSource dataSource;

	public List<Message> findAll() {
		List<Message> messages = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(
						"SELECT id, text FROM message ORDER BY created_at DESC");
				ResultSet rs = statement.executeQuery()) {
			while (rs.next()) {
				messages.add(new Message(rs.getInt("id"), rs.getString("text")));
			}
		}
		catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		return messages;
	}

	public Message insert(Message message) {
		try (Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			try (PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO message(text) VALUES(?)", new String[] { "id" })) {
				statement.setString(1, message.getText());
				statement.executeUpdate();
				connection.commit();
				try (ResultSet rs = statement.getGeneratedKeys()) {
					rs.next();
					int id = rs.getInt("id");
					return new Message(id, message.getText());
				}
			}
			catch (SQLException e) {
				connection.rollback();
				throw e;
			}
			finally {
				connection.setAutoCommit(true);
			}
		}
		catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
}
