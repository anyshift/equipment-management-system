package com.demo.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        if (isMultipart) {
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
            try {
                List<FileItem> fileItems = servletFileUpload.parseRequest(req);
                for (FileItem item : fileItems) {
                    if (!item.isFormField()) {
                        String fileName = item.getName();
                        long timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
                        String newFilename = "";
                        int dotIndex = fileName.indexOf('.');
                        if (dotIndex != -1) {
                            newFilename += timestamp + fileName.substring(dotIndex);
                        } else newFilename += timestamp + "-" + fileName;

                        String savePath = this.getServletContext().getRealPath("img/device/") + newFilename;
                        item.write(new File(savePath));

                        Path source = Paths.get(savePath);
                        Path destination = Paths.get("/Users/tsaioil/Desktop/repair/web/img/device/" + newFilename);
                        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);

                        String imagePath = "/img/device/" + newFilename;
                        String jsonResponse = "{\"status\": \"success\", \"imagePath\": \"" + imagePath + "\"}";
                        resp.setContentType("application/json");
                        resp.setCharacterEncoding("UTF-8");
                        resp.getWriter().write(jsonResponse);
                    }
                }
            } catch (Exception e) {
                resp.setStatus(500);
                resp.getWriter().println("上传失败");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
