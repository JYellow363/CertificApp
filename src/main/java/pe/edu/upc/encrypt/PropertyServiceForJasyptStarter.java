package pe.edu.upc.encrypt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceForJasyptStarter {
	
	@Value("${admin.password}")
	private String adminPassword;
	
	public String getAdminPassword() {
		return adminPassword;
	}

	public String getAdminPasswordlUsingEnvironment(Environment environment) {
		return environment.getProperty("admin.password");
	}
	
}