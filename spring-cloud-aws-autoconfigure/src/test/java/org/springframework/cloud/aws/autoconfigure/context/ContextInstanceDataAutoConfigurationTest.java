/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.aws.autoconfigure.context;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Agim Emruli
 */
public class ContextInstanceDataAutoConfigurationTest {

	private AnnotationConfigApplicationContext context;

	@After
	public void tearDown() throws Exception {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	public void placeHolder_noExplicitConfiguration_createInstanceDataResolverThatResolvesWithDefaultAttributes() throws Exception {
		// Arrange
		HttpServer httpServer = MetaDataServer.setupHttpServer();
		HttpContext httpContext = httpServer.createContext("/latest/user-data", new MetaDataServer.HttpResponseWriterHandler("a:b;c:d"));

		this.context = new AnnotationConfigApplicationContext();
		this.context.register(ContextInstanceDataAutoConfiguration.class);

		// Act
		this.context.refresh();

		// Assert
		Assert.assertEquals("b", this.context.getEnvironment().getProperty("a"));
		Assert.assertEquals("d", this.context.getEnvironment().getProperty("c"));

		httpServer.removeContext(httpContext);
	}

	@Test
	public void placeHolder_customValueSeparator_createInstanceDataResolverThatResolvesWithCustomValueSeparator() throws Exception {
		// Arrange
		HttpServer httpServer = MetaDataServer.setupHttpServer();
		HttpContext httpContext = httpServer.createContext("/latest/user-data", new MetaDataServer.HttpResponseWriterHandler("a=b;c=d"));

		this.context = new AnnotationConfigApplicationContext();

		EnvironmentTestUtils.addEnvironment(this.context, "cloud.aws.instance.data.valueSeparator:=");

		this.context.register(ContextInstanceDataAutoConfiguration.class);

		// Act
		this.context.refresh();

		// Assert
		Assert.assertEquals("b", this.context.getEnvironment().getProperty("a"));
		Assert.assertEquals("d", this.context.getEnvironment().getProperty("c"));

		httpServer.removeContext(httpContext);
	}

	@Test
	public void placeHolder_customAttributeSeparator_createInstanceDataResolverThatResolvesWithCustomAttribute() throws Exception {
		// Arrange
		HttpServer httpServer = MetaDataServer.setupHttpServer();
		HttpContext httpContext = httpServer.createContext("/latest/user-data", new MetaDataServer.HttpResponseWriterHandler("a:b/c:d"));

		this.context = new AnnotationConfigApplicationContext();

		EnvironmentTestUtils.addEnvironment(this.context, "cloud.aws.instance.data.attributeSeparator:/");

		this.context.register(ContextInstanceDataAutoConfiguration.class);

		// Act
		this.context.refresh();

		// Assert
		Assert.assertEquals("b", this.context.getEnvironment().getProperty("a"));
		Assert.assertEquals("d", this.context.getEnvironment().getProperty("c"));

		httpServer.removeContext(httpContext);
	}
}