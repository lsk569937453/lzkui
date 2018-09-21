package com.lzkui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 *
 *
 *
 *
 * <p>
 * 修改历史:                                                                                    &lt;br&gt;
 * 修改日期             修改人员        版本                     修改内容
 * --------------------------------------------------------------------
 * 2018年09月21日 上午10:14   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.lzkui"})
public class SpringMain extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringMain.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringMain.class, args);
//		LOG.info("dsadadadaasd-------------");
	}

	@Override public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(9090);

	}
}
