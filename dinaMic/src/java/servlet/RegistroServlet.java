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
import modelo.Usuario;

@WebServlet("/RegistroServlet")
public class RegistroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;
    
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Obtener parámetros del formulario
        String num_docu = request.getParameter("num_docu");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        String id_perfilStr = request.getParameter("id_perfil");
        
        try {
            // Validar parámetros
            if (num_docu == null || nombre == null || apellido == null || 
                email == null || usuario == null || clave == null || id_perfilStr == null) {
                request.setAttribute("error", "Todos los campos son obligatorios");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                return;
            }
            
            int id_perfil = Integer.parseInt(id_perfilStr);
            
            // Crear objeto Usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNum_docu(num_docu.trim());
            nuevoUsuario.setNombre(nombre.trim());
            nuevoUsuario.setApellido(apellido.trim());
            nuevoUsuario.setEmail(email.trim());
            nuevoUsuario.setUsuario(usuario.trim());
            nuevoUsuario.setClave(clave);
            nuevoUsuario.setId_perfil(id_perfil);
            
            // Intentar registrar el usuario
            boolean registrado = usuarioDAO.agregar(nuevoUsuario);
            
            if (registrado) {
                // Registro exitoso - redirigir al login con mensaje
                request.setAttribute("exito", "Usuario registrado correctamente. Ya puedes iniciar sesión.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                // Error en el registro
                request.setAttribute("error", "Error al registrar usuario. El usuario o email ya existen.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
            }
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en el formato del perfil");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en el sistema: " + e.getMessage());
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirigir al formulario de registro si acceden por GET
        request.getRequestDispatcher("registro.jsp").forward(request, response);
    }
}