package br.com.stefanini.pocjasper.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.stefanini.pocjasper.service.EmployeeService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

@RestController
@RequestMapping("/jasper")
public class EmployeeController {
    
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping
	public void report() throws JRException, IOException {
		JRBeanCollectionDataSource jbds = new JRBeanCollectionDataSource(employeeService.findAll());
		Map<String, Object> params = new HashMap<>();
		JasperDesign file = JRXmlLoader.load("./src/main/resources/jasper/Blank_A4.jrxml");
		JasperReport relatorio = JasperCompileManager.compileReport(file);
		JasperPrint print = JasperFillManager.fillReport(relatorio , params, jbds);
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(print));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\Users\\" + System.getProperty("user.name") + "\\Downloads\\relat.xls"));
        SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
        xlsReportConfiguration.setOnePagePerSheet(false);
        xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
        xlsReportConfiguration.setDetectCellType(true);
        xlsReportConfiguration.setWhitePageBackground(false);
        exporter.setConfiguration(xlsReportConfiguration);
        exporter.exportReport();
	}
}
