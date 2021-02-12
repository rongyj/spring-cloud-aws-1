/*
 * Copyright 2013-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.awspring.cloud.context.config.xml;

import io.awspring.cloud.core.support.documentation.RuntimeUse;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * {@link org.springframework.beans.factory.xml.NamespaceHandler} implementation
 * for the Spring Cloud AWS context namespace.
 *
 * @author Agim Emruli
 * @since 1.0
 */
@RuntimeUse
@Deprecated
public class ContextNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("context-credentials", new ContextCredentialsBeanDefinitionParser());
		registerBeanDefinitionParser("context-resource-loader", new ContextResourceLoaderBeanDefinitionParser());
		registerBeanDefinitionParser("context-region", new ContextRegionBeanDefinitionParser());
		registerBeanDefinitionParser("context-instance-data",
				new ContextInstanceDataPropertySourceBeanDefinitionParser());
		registerBeanDefinitionParser("stack-configuration", new StackConfigurationBeanDefinitionParser());
	}

}
