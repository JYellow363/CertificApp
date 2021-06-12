package pe.edu.upc;

public interface CertificationService {
	byte[] generateCertification(String jaspertRoute, Certification certification) throws Exception;
}
