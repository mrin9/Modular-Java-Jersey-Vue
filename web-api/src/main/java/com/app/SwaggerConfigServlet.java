package com.app;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import io.swagger.models.Info;
import io.swagger.models.SecurityRequirement;
import io.swagger.models.Swagger;
import io.swagger.models.auth.ApiKeyAuthDefinition;
import io.swagger.models.auth.In;

@WebServlet(name = "SwaggerConfigServlet", loadOnStartup=2, urlPatterns = {"/apidocs"})
public class SwaggerConfigServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {

        ServletContext context = config.getServletContext();
        final List<SecurityRequirement> securityRequirements = new ArrayList<SecurityRequirement>();
        Info info = new Info()
                .title("Mrin Order Processing System")
                .version("1.0.0");

        Swagger swagger = new Swagger().info(info);
        swagger.setBasePath("/api");
        swagger.securityDefinition("api_key", new ApiKeyAuthDefinition("Authorization", In.HEADER));
        SecurityRequirement securityReq = new SecurityRequirement().requirement("api_key");
        swagger.addSecurity(securityReq);
        context.setAttribute("swagger", swagger);
    }
}
