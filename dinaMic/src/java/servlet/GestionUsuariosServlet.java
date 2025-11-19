// controlador/GestionUsuariosServlet.java
package servlet;

import controladorDAO.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import controladorDAO.PerfilDAO;
import modelo.Usuario;
import modelo.Perfil;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;

@WebServlet("/GestionUsuariosServlet")
public class GestionUsuariosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;
    private PerfilDAO perfilDAO;
    
    public void init() {
        usuarioDAO = new UsuarioDAO();
        perfilDAO = new PerfilDAO();
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
        
        String action = request.getParameter("action");
        
        try {
            if (action == null) {
                // LISTAR USUARIOS
                List<Usuario> usuarios = usuarioDAO.listar();
                List<Perfil> perfiles = perfilDAO.listar();
                
                request.setAttribute("usuarios", usuarios);
                request.setAttribute("perfiles", perfiles);
                request.getRequestDispatcher("gestion-usuarios.jsp").forward(request, response);
                
            } else if (action.equals("editar")) {
                // CARGAR USUARIO PARA EDITAR
                int idUsuario = Integer.parseInt(request.getParameter("id"));
                Usuario usuario = usuarioDAO.listarPorId(idUsuario);
                List<Perfil> perfiles = perfilDAO.listar();
                
                if (usuario != null) {
                    request.setAttribute("usuarioEditar", usuario);
                    request.setAttribute("perfiles", perfiles);
                    request.getRequestDispatcher("gestion-usuarios.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Usuario no encontrado");
                    response.sendRedirect("GestionUsuariosServlet");
                }
                
            } else if (action.equals("eliminar")) {
                // ELIMINAR USUARIO
                int idUsuario = Integer.parseInt(request.getParameter("id"));
                boolean eliminado = usuarioDAO.eliminar(idUsuario);
                
                if (eliminado) {
                    request.setAttribute("exito", "Usuario eliminado correctamente");
                } else {
                    request.setAttribute("error", "Error al eliminar usuario");
                }
                
                response.sendRedirect("GestionUsuariosServlet");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error en el sistema: " + e.getMessage());
            response.sendRedirect("GestionUsuariosServlet");
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
        
        try {
            if (action.equals("crear")) {
                // CREAR NUEVO USUARIO
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setNum_docu(request.getParameter("num_docu"));
                nuevoUsuario.setNombre(request.getParameter("nombre"));
                nuevoUsuario.setApellido(request.getParameter("apellido"));
                nuevoUsuario.setEmail(request.getParameter("email"));
                nuevoUsuario.setUsuario(request.getParameter("usuario"));
                nuevoUsuario.setClave(request.getParameter("clave"));
                nuevoUsuario.setId_perfil(Integer.parseInt(request.getParameter("id_perfil")));
                
                boolean creado = usuarioDAO.agregar(nuevoUsuario);
                
                if (creado) {
                    request.setAttribute("exito", "Usuario creado correctamente");
                } else {
                    request.setAttribute("error", "Error al crear usuario. El usuario o email ya existen.");
                }
                
            } else if (action.equals("actualizar")) {
                // ACTUALIZAR USUARIO
                Usuario usuarioActualizar = new Usuario();
                usuarioActualizar.setIdusu(Integer.parseInt(request.getParameter("idusu")));
                usuarioActualizar.setNum_docu(request.getParameter("num_docu"));
                usuarioActualizar.setNombre(request.getParameter("nombre"));
                usuarioActualizar.setApellido(request.getParameter("apellido"));
                usuarioActualizar.setEmail(request.getParameter("email"));
                usuarioActualizar.setUsuario(request.getParameter("usuario"));
                usuarioActualizar.setId_perfil(Integer.parseInt(request.getParameter("id_perfil")));
                
                boolean actualizado = usuarioDAO.editar(usuarioActualizar);
                
                if (actualizado) {
                    request.setAttribute("exito", "Usuario actualizado correctamente");
                } else {
                    request.setAttribute("error", "Error al actualizar usuario");
                }
            }
            
            response.sendRedirect("GestionUsuariosServlet");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error en el sistema: " + e.getMessage());
            response.sendRedirect("GestionUsuariosServlet");
        }
    }
}