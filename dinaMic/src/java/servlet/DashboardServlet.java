
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Usuario;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        // Verificar si hay sesión activa
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // Obtener datos del usuario de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        // Pasar datos a la vista
        request.setAttribute("usuario", usuario);
        request.setAttribute("esAdmin", usuario.getId_perfil() == 1); // Asumiendo que 1 = Admin
        
        // Cargar datos del dashboard (aquí irán luego los contadores de productos, ventas, etc.)
        
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}