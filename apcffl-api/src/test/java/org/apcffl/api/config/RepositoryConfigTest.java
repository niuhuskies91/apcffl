package org.apcffl.api.config;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {RepositoryConfig.class})
@ActiveProfiles("local")
public class RepositoryConfigTest {

	@Autowired
	private RepositoryConfig config;

	@Test
	public void verify_dataSource() {
		
		DriverManagerDataSource ds = (DriverManagerDataSource)config.dataSource();
		
		assertNotNull(ds);
		assertNotNull(ds.getPassword());
		assertNotNull(ds.getUrl());
		assertNotNull(ds.getUsername());
	}
}
