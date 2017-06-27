package com.example;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariDataSource;

@ApplicationScoped
public class DataSourceProvider {

	@Produces
	public DataSource dataSource() {
		URI uri = mySqlUri().orElseThrow(
				() -> new IllegalStateException("mysql service is not bound!"));
		String[] userInfo = uri.getUserInfo().split(":", 2);
		String url = "jdbc:mysql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath()
				+ "?autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8";
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		dataSource.setJdbcUrl(url);
		dataSource.setMaximumPoolSize(4 /* for ClearDB spark plan */);
		dataSource.setUsername(userInfo[0]);
		dataSource.setPassword(userInfo[1]);

		flywayMigration(dataSource);
		return dataSource;
	}

	void flywayMigration(DataSource dataSource) {
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.migrate();
	}

	Optional<URI> mySqlUri() {
		try {
			String json = System.getenv("VCAP_SERVICES");
			JsonNode node = new ObjectMapper().readValue(json, JsonNode.class);
			return StreamSupport.stream(node.findParent("uri").spliterator(), false)
					.map(JsonNode::asText).filter(x -> x.startsWith("mysql")).findFirst()
					.map(URI::create);
		}
		catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
