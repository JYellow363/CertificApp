package pe.edu.upc;

import pe.edu.upc.model.Certification;

public interface CertificationService {
	byte[] generateCertification(String jaspertRoute, Certification certification) throws Exception;
}
