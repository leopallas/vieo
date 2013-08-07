package com.test.performance.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.util.ClassUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/upload")
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private boolean isMultipart;

    private static       long   maxFileSize     = 50 * 1024;

    private static       int    maxMemSize      = 4 * 1024;

    private static final String PROPERTIES_NAME = "servlet-config.properties";

    private static String TMP_DIR_PATH;

    private        File   tmpDir;

    private static final String DESTINATION_DIR_PATH = "files";

    private File destinationDir;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
    }

    public void init() {
        try {
            ClassLoader cl = null;
            try {
                cl = Thread.currentThread().getContextClassLoader();
            } catch (Throwable ex) {
                // Cannot access thread context ClassLoader - falling back to system class loader...
            }
            if (cl == null) {
                // No thread context class loader -> use class loader of this class.
                cl = ClassUtils.class.getClassLoader();
            }

            InputStream in = cl.getResourceAsStream(PROPERTIES_NAME);
            Properties property = new Properties();
            property.load(in);
            in.close();

            TMP_DIR_PATH = (String) property.get("upload.file.path");
            System.out.println(TMP_DIR_PATH);

            tmpDir = new File(TMP_DIR_PATH);
            if (!tmpDir.isDirectory()) {
                tmpDir.mkdirs();
            }

            destinationDir = new File(TMP_DIR_PATH, DESTINATION_DIR_PATH);
            ;
            if (!destinationDir.isDirectory()) {
                destinationDir.mkdirs();
            }

            String maxFileSizeStr = (String) property.get("upload.file.maxFileSize");
            if (maxFileSizeStr != null && !"".equals(maxFileSizeStr)) {
                maxFileSize = Long.parseLong(maxFileSizeStr);
            }


            String maxMemSizeStr = (String) property.get("upload.file.maxMemSizeStr");
            if (maxMemSizeStr != null && !"".equals(maxMemSizeStr)) {
                maxMemSize = Integer.parseInt(maxMemSizeStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/plain");
        java.io.PrintWriter out = response.getWriter();
        out.println("<h1>Servlet File Upload</h1>");
        if (!isMultipart) {
            out.println("<h1>no file uploaded</h1>");
            out.close();
            return;
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(tmpDir);

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);

        try {
            // Parse the request to get file items.
            @SuppressWarnings("unchecked")
            List<FileItem> fileItems = upload.parseRequest(request);
            // Process the uploaded file items
            Iterator<FileItem> i = fileItems.iterator();
            while (i.hasNext()) {
                FileItem item = (FileItem) i.next();
                /*
				 * Handle Form Fields.
				 */
                if (item.isFormField()) {
                    out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                } else {
                    String fileName = String.format("%s--%s", item.getName(), UUID.randomUUID().toString());
                    // Handle Uploaded files.
                    out.println("Field Name = " + item.getFieldName()
                            + ", File Name = " + fileName
                            + ", Content type = " + item.getContentType()
                            + ", File Size = " + item.getSize());
					/*
					 * Write file to the ultimate location.
					 */
                    File file = new File(destinationDir, fileName);
                    item.write(file);
                }
            }
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        throw new ServletException("GET method used with "
                + getClass().getName() + ": POST method required.");
    }
}
