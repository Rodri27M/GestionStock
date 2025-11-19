
// controlador/LoginServlet.java
package servlet;

import controladorDAO.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Usuario;
import modelo.Actividad;
import java.io.IOException;
import java.util.List;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;
    
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        
        try {
            // Autenticar usuario (ahora carga actividades también)
            Usuario user = usuarioDAO.autenticar(usuario, clave);
            
            if (user != null) {
                // Crear sesión
                HttpSession session = request.getSession();
                session.setAttribute("usuario", user);
                session.setAttribute("id_usuario", user.getIdusu());
                session.setAttribute("nombre_usuario", user.getNombreCompleto());
                session.setAttribute("perfil", user.getNombrePerfil());
                session.setAttribute("id_perfil", user.getId_perfil());
                
                // GUARDAR LISTA DE ACTIVIDADES PERMITIDAS EN SESIÓN
                List<Actividad> actividades = user.getActividades();
                session.setAttribute("actividades", actividades);
                
                // Guardar también como lista de enlaces para fácil acceso en filtros
                List<String> enlacesPermitidos = new java.util.ArrayList<>();
                for (Actividad actividad : actividades) {
                    enlacesPermitidos.add(actividad.getEnlace());
                }
                session.setAttribute("enlacesPermitidos", enlacesPermitidos);
                
                // DEBUG: Mostrar actividades en consola
                System.out.println("Usuario: " + user.getNombreCompleto());
                System.out.println("Actividades permitidas: " + enlacesPermitidos);
                
                // Redirigir al dashboard
                response.sendRedirect("DashboardServlet");
            } else {
                // Credenciales incorrectas
                request.setAttribute("error", "Usuario o contraseña incorrectos");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en el sistema: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirigir a login si acceden por GET
        response.sendRedirect("login.jsp");
    }
}