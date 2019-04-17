package com.saifintex.controller.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.saifintex.common.AbstractBase;

@Controller
@RequestMapping("/file")
public class FileUploadDownloadController extends AbstractBase {
	private FileOutputStream fileOutputStream;
	private File file;
	private BufferedOutputStream bufferedOutputStream;

	@Value("${app.path.skypay.apk}")
	private String apkPath;

	@Value("${app.skypay.apkName}")
	private String apkName;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView uploadFile(@RequestParam CommonsMultipartFile file, HttpSession session) {

		ServletContext context = session.getServletContext();
		String path = context.getRealPath("/SkyAppApk");
		String fileName = file.getOriginalFilename();
		byte[] bytes = file.getBytes();
		try {
			this.file = new File(path + File.separator + fileName);
			fileOutputStream = new FileOutputStream(this.file);
			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			bufferedOutputStream.write(bytes);
			bufferedOutputStream.flush();
			bufferedOutputStream.close();
			fileOutputStream.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			getLogger().error(e);
		} catch (IOException e) {
			getLogger().error(e);
		} finally {
			try {
				if (bufferedOutputStream != null) {
					bufferedOutputStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				getLogger().error(e);
			}
		}

		return null;
	}

	/*@RequestMapping("/download")
	public ModelAndView fileDownload(HttpServletRequest request, HttpServletResponse response) {
		Path file = Paths.get(apkPath, apkName);
		if (Files.exists(file)) {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + apkName);
			try {
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
		return null;
	}*/
	
	/*@RequestMapping("/download")
	public @ResponseBody void fileDownload(HttpServletRequest request, HttpServletResponse response) {
		Path file = Paths.get(apkPath, apkName);
		if (Files.exists(file)) {
			response.setContentType("application/force-download");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setContentLength((int)file.toFile().length());
			response.setHeader("Content-Disposition", "inline;filename=" + apkName);
			try {
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}finally {
				try {
					response.getOutputStream().close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}*/
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity<?> downloadPDFFile()
	         {

		Path file = Paths.get(apkPath, apkName);
		ByteArrayResource resource=null;
		try {
			resource = new ByteArrayResource(Files.readAllBytes(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
	
			e.printStackTrace();
		}finally {
	
		}

		HttpHeaders headers = new HttpHeaders();
	    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	    headers.add("Pragma", "public");
	    headers.add("Expires", "0");
	    headers.add("Content-Disposition", "attachment;filename=" + apkName);
	    headers.add("Content-Transfer-Encoding", "binary");
	    headers.add("connection", "keep-alive");
		   
	    return ResponseEntity
	            .ok()
	            .headers(headers)
	            .contentLength(file.toFile().length())
	            .contentType(MediaType.parseMediaType("application/vnd.android.package-archive"))
	           // .body(new InputStreamResource(new FileInputStream(file.toFile())));
	            .body(resource);
	}
}
