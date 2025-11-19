/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import controladorDAO.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Actividad;
import modelo.ActividadDAO;
import modelo.GesActividadDAO;
import modelo.Usuario;

@WebServlet("/GestionPermisosServlet")
public class GestionPermisosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GesActividadDAO gesActividadDAO;
    private UsuarioDAO usuarioDAO;
    private ActividadDAO actividadDAO;
    
    public void init() {
        gesActividadDAO = new GesActividadDAO();
        usuarioDAO = new UsuarioDAO();
        actividadDAO = new ActividadDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    
    HttpSession session = request.getSession(false);
    
    // Verificar sesión y que sea admin
    if (session == null || session.getAttribute("usuario") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
    if (usuarioSesion.getId_perfil() != 1) { // 1 = Admin
        response.sendRedirect("DashboardServlet");
        return;
    }
    
    String idUsuarioParam = request.getParameter("id_usuario");
    
    try {
        // SIEMPRE cargar lista de usuarios
        List<Usuario> usuarios = usuarioDAO.listar();
        request.setAttribute("usuarios", usuarios);
        
        if (idUsuarioParam != null && !idUsuarioParam.isEmpty()) {
            // MOSTRAR PERMISOS DE UN USUARIO ESPECÍFICO
            int idUsuario = Integer.parseInt(idUsuarioParam);
            Usuario usuario = usuarioDAO.listarPorId(idUsuario);
            
            if (usuario != null) {
                // Obtener actividades asignadas y no asignadas
                List<Actividad> actividadesAsignadas = gesActividadDAO.obtenerActividadesPorUsuario(idUsuario);
                List<Actividad> actividadesNoAsignadas = gesActividadDAO.obtenerActividadesNoAsignadas(idUsuario);
                
                request.setAttribute("usuarioSeleccionado", usuario);
                request.setAttribute("actividadesAsignadas", actividadesAsignadas);
                request.setAttribute("actividadesNoAsignadas", actividadesNoAsignadas);
                
                System.out.println("DEBUG: Cargando usuario " + usuario.getNombre() + 
                                 " - Asignadas: " + actividadesAsignadas.size() + 
                                 " - Disponibles: " + actividadesNoAsignadas.size());
            } else {
                request.setAttribute("error", "Usuario no encontrado");
            }
        }
        
        // Cargar reporte de asignaciones
        List<String> asignaciones = gesActividadDAO.obtenerTodasAsignaciones();
        request.setAttribute("asignaciones", asignaciones);
        
        request.getRequestDispatcher("gestion-permisos.jsp").forward(request, response);
        
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", "Error en el sistema: " + e.getMessage());
        response.sendRedirect("GestionPermisosServlet");
    }
}
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        // Verificar que sea admin
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
        if (usuarioSesion.getId_perfil() != 1) {
            response.sendRedirect("DashboardServlet");
            return;
        }
        
        String action = request.getParameter("action");
        int idUsuario = Integer.parseInt(request.getParameter("id_usuario"));
        
      
  
        try {
        
            if (action.equals("asignar")) {
                // ASIGNAR NUEVA ACTIVIDAD
                
                int idActividad = Integer.parseInt(request.getParameter("id_actividad"));
                boolean asignado = gesActividadDAO.asignarActividadUsuario(idUsuario, idActividad);
                
                if (asignado) {
                    request.setAttribute("exito", "Actividad asignada correctamente");
                  
                } else {
                    request.setAttribute("error", "Error al asignar actividad o ya estaba asignada");
                    
                }
                
            } else if (action.equals("eliminar")) {
                // ELIMINAR ACTIVIDAD
                int idActividad = Integer.parseInt(request.getParameter("id_actividad"));
                boolean eliminado = gesActividadDAO.eliminarActividadUsuario(idUsuario, idActividad);
                
                if (eliminado) {
                    request.setAttribute("exito", "Actividad eliminada correctamente");
                } else {
                    request.setAttribute("error", "Error al eliminar actividad");
                }
                
            } else if (action.equals("limpiar")) {
                // LIMPIAR TODOS LOS PERMISOS
                boolean limpiado = gesActividadDAO.eliminarTodasActividadesUsuario(idUsuario);
                
                if (limpiado) {
                    request.setAttribute("exito", "Todos los permisos han sido eliminados");
                } else {
                    request.setAttribute("error", "Error al limpiar permisos");
                }
            }
            
            // Redirigir manteniendo el usuario seleccionado
            response.sendRedirect("GestionPermisosServlet?id_usuario=" + idUsuario);
            
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error en el sistema: " + e.getMessage());
            response.sendRedirect("GestionPermisosServlet");
            
        }
    }
}