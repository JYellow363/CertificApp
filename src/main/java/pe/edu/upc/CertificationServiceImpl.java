package pe.edu.upc;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import pe.edu.upc.model.Certification;

@Service
public class CertificationServiceImpl implements CertificationService {

	@Override
	public byte[] generateCertification(String jaspertRoute, Certification certification) throws Exception {
		byte[] resultPdf = null;
		if(certification.getStudent2().isEmpty()) {
			certification.setStudent2(certification.getStudent1());
			certification.setStudent1("");
		}
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("title", certification.getTitle().toUpperCase());
		input.put("student1", certification.getStudent1().toUpperCase());
		input.put("student2", certification.getStudent2().toUpperCase());
		input.put("link", "");
		input.put("route", jaspertRoute);
		resultPdf = export(jaspertRoute + "/certification.jrxml", input);
		return resultPdf;
	}

    public static byte[] export(String jaspertRoute, Map<String, Object> parameters) throws Exception {
		JasperPrint jasperPrint;
		JasperDesign design = JRXmlLoader.load(jaspertRoute);
		JasperReport jasperReport = JasperCompileManager.compileReport(design);
		jasperReport.setProperty(JRTextField.PROPERTY_PRINT_KEEP_FULL_TEXT, "true");
		jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
		byte[] bytes = byteArrayOutputStream.toByteArray();
		return bytes;
	}
}
