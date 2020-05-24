package com.iit.aiposizi.lab1;

import com.iit.aiposizi.lab1.enums.ContentType;
import com.iit.aiposizi.lab1.enums.HttpCode;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.time.Instant;
import java.util.StringTokenizer;

import static com.iit.aiposizi.lab1.enums.ContentType.*;
import static com.iit.aiposizi.lab1.enums.HttpCode.*;
import static java.lang.String.format;

@Slf4j
public class HttpServer implements Runnable {

    private static final String DEFAULT_FILE = "/static/index.html";
    private static final String FILE_NOT_FOUND = "/static/404.html";
    private static final String METHOD_NOT_IMPLEMENTED = "/static/405.html";

    private final Socket connect;

    private BufferedReader in;
    private PrintWriter out;
    private BufferedOutputStream dataOut;

    HttpServer(Socket connect) {
        this.connect = connect;
        try {
            connect.setKeepAlive(true);
            connect.setSoTimeout(3000);
        } catch (SocketException e) {
            System.exit(0);
        }

    }

    @Override
    public void run() {
        var fileRequested = "";
        try {
            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            out = new PrintWriter(connect.getOutputStream());
            dataOut = new BufferedOutputStream(connect.getOutputStream());

            var input = in.readLine();
            log.info("Input is: {}", input);

            StringTokenizer parse;
            try {
                parse = new StringTokenizer(input);
            } catch (NullPointerException e) {
                return;
            }

            var method = parse.nextToken().toUpperCase();
            log.info("Request method: {}", method);
            fileRequested = parse.nextToken().toLowerCase();

            switch (method) {
                case "GET":
                    processGet(fileRequested);
                    break;
                case "POST":
                    processPost();
                    break;
                case "OPTIONS":
                    processOptions();
                    break;
                default:
                    methodNotAllowed(method);
                    break;
            }
            log.info("File {} returned", fileRequested);
        } catch (FileNotFoundException fnfe) {
            try {
                log.warn("File: {} not found, load {}", fileRequested, FILE_NOT_FOUND);
                fileNotFound(fileRequested);
            } catch (IOException ioe) {
                log.warn("Error with file not found exception : {}", ioe.getMessage());
            }
        } catch (IOException ioe) {
            log.error("Server error: {}", ioe.getMessage());
        } finally {
            try {
                in.close();
                out.close();
                dataOut.close();
                connect.close();
            } catch (Exception e) {
                log.error("Error closing stream : {}", e.getMessage());
            }
            log.info("Connection closed");
        }
    }

    private void processGet(String fileRequested) throws IOException {
        log.info("GET request was accepted");
        if (fileRequested.endsWith("/")) {
            fileRequested = DEFAULT_FILE;
        }

        var inputStream = findFile(fileRequested);
        var content = findByFileName(fileRequested);
        var data = content.getReader().read(inputStream);
        createResponse(OK, content, data.length, data);
        log.info("File {} of type {} returned", fileRequested, content.getText());
    }

    private void processPost() throws IOException {
        log.info("POST request was accepted");
        var inputStream = findFile("/file.txt");
        var data = PLAIN.getReader().read(inputStream);
        createResponse(CREATED, PLAIN, data.length, data);
    }

    private void processOptions() throws IOException {
        log.info("OPTIONS request was accepted");
        createResponse(OK, PLAIN, 0, new byte[]{});
    }

    private void methodNotAllowed(String method) throws IOException {
        log.warn("Unknown method: {}", method);
        var inputStream = findFile(METHOD_NOT_IMPLEMENTED);
        var data = HTML.getReader().read(inputStream);
        createResponse(NOT_ALLOWED, HTML, data.length, data);
    }

    private void fileNotFound(String fileRequested) throws IOException {
        var inputStream = findFile(FILE_NOT_FOUND);
        var data = HTML.getReader().read(inputStream);
        createResponse(HttpCode.NOT_FOUND, HTML, data.length, data);
        log.warn("File {} not found", fileRequested);

    }

    private InputStream findFile(String fileName) throws FileNotFoundException {
        var inputStream = this.getClass().getResourceAsStream(fileName);
        log.info("Requested path of the file is: {}", this.getClass().getResource(fileName));
        if (inputStream == null) {
            throw new FileNotFoundException();
        }
        return inputStream;
    }

    private void createResponse(HttpCode code, ContentType content, int fileLength, byte[] fileData)
            throws IOException {
        out.println(format("HTTP/1.1 %s %s", code.getCode(), code.getDescription()));
        out.println("Server: HTTP Server");
        out.println(format("Date: %s", Instant.now()));
        out.println(format("Content-type: %s", content.getText()));
        out.println(format("Content-length: %s", fileLength));
        out.println("Access-Control-Allow-Origin: localhost");
        out.println("Access-Control-Allow-Methods: GET, POST, OPTIONS");
        out.println();
        out.flush();

        dataOut.write(fileData, 0, fileLength);
        dataOut.flush();

        log.info("Type {}, size {}", content.getExtension(), fileLength);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
            log.info("Thread error.");
        }
        log.info("Creating header of response with code {}", code.getCode());
    }
}