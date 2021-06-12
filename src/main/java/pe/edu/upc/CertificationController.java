package pe.edu.upc;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class CertificationController {

	@Autowired
	CertificationService certificationService;

	@Autowired
	private ServletContext servletContext;

	private Certification certification;

	@RequestMapping("")
	public String goGenerate(Model model) {
		setCertification(new Certification());
		model.addAttribute("certification", certification);
		return "certification";
	}

	@RequestMapping("/goGenerateSave")
	public String goGenerateSave(Model model) {
		if (certification == null)
			return "redirect:/";
		model.addAttribute("certification", certification);
		return "certification";
	}

	@RequestMapping("/generateCertification")
	public void generate(@ModelAttribute Certification certification, BindingResult binRes, Model model,
			RedirectAttributes redirectAttributes, HttpServletResponse response) throws Exception {
		setCertification(certification);
		if (certification == null)
			certification = new Certification();
		if (binRes.hasErrors()) {
			System.out.println("Error en BindingResult");
		} else {

			boolean flag = true;
			if (getCertification().getLink().isEmpty() || getCertification().getLink() == null) {
				redirectAttributes.addFlashAttribute("errorMessage", "Debe colocar el enlace.");
				flag = false;
			}
			if ((getCertification().getStudent1().isEmpty() || getCertification().getStudent1() == null)
					&& (getCertification().getStudent2().isEmpty() || getCertification().getStudent2() == null)) {
				redirectAttributes.addFlashAttribute("errorMessage", "Debe colocar al menos un estudiante.");
				flag = false;
			}
			if (getCertification().getTitle().isEmpty() || getCertification().getTitle() == null) {
				redirectAttributes.addFlashAttribute("errorMessage", "Debe colocar el nombre del proyecto.");
				flag = false;
			}

			String jaspertRoute = servletContext.getRealPath("/report");

			if (flag == true) {
				byte[] bytes = certificationService.generateCertification(jaspertRoute, certification);
				setPDFResponse(response, bytes, getFileName());
			}
		}
	}

	public String getFileName() {
		String part1[] = { "", "" };
		String part2[] = { "", "" };
		if (!getCertification().getStudent1().isEmpty()) {
			part1 = getCertification().getStudent1().split(" ", 2);
			part1[0] = "_" + part1[0];
		}
		if (!getCertification().getStudent2().isEmpty()) {
			part2 = getCertification().getStudent2().split(" ", 2);
			part2[0] = "_" + part2[0];
		}
		return "Certificado" + part1[0] + part2[0];
	}

	public static void setPDFResponse(HttpServletResponse response, byte[] bytes, String filename) throws IOException {
		response.reset();
		response.setContentType("application/octet-stream");
		response.setContentLength(bytes.length);
		response.setHeader("Content-disposition", "attachment; filename=\"" + filename + ".pdf\"");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		ServletOutputStream ouputStream = response.getOutputStream();
		ouputStream.write(bytes, 0, bytes.length);
		ouputStream.flush();
		ouputStream.close();
	}

	public Certification getCertification() {
		return certification;
	}

	public void setCertification(Certification certification) {
		this.certification = certification;
	}

}
