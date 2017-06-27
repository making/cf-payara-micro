package com.example;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class App extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		return Collections.singleton(MessageResource.class);
	}
}
