// controlador/ProductoServlet.java
package servlet;

import controladorDAO.ProductoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import modelo.Categoria;
import controladorDAO.CategoriaDAO;
import modelo.Producto;

@WebServlet("/ProductoServlet")
public class ProductoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductoDAO productoDAO;
    private CategoriaDAO categoriaDAO;

    public void init() {
        productoDAO = new ProductoDAO();
        categoriaDAO = new CategoriaDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Verificar sesión
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        try {
            if (action == null) {
                // LISTAR PRODUCTOS
                List<Producto> productos = productoDAO.listar();
                List<Categoria> categorias = categoriaDAO.listar();

                request.setAttribute("productos", productos);
                request.setAttribute("categorias", categorias);
                request.getRequestDispatcher("productos.jsp").forward(request, response);

            } else if (action.equals("editar")) {
                // CARGAR PRODUCTO PARA EDITAR
                int idProducto = Integer.parseInt(request.getParameter("id"));
                Producto producto = productoDAO.listarPorId(idProducto);
                List<Categoria> categorias = categoriaDAO.listar();

                if (producto != null) {
                    request.setAttribute("productoEditar", producto);
                    request.setAttribute("categorias", categorias);
                    request.getRequestDispatcher("productos.jsp").forward(request, response);
                }

            } else if (action.equals("eliminar")) {
                // ← AQUÍ VA LA NUEVA ACCIÓN
                int idProducto = Integer.parseInt(request.getParameter("id"));
                boolean eliminado = productoDAO.eliminar(idProducto);

                if (eliminado) {
                    session.setAttribute("exito", "Producto eliminado correctamente");
                } else {
                    session.setAttribute("error", "Error al eliminar producto");
                }

                response.sendRedirect("ProductoServlet");
            } else if (action.equals("eliminar_categoria")) {
                // ELIMINAR CATEGORÍA
                int idCategoria = Integer.parseInt(request.getParameter("id"));
                boolean eliminada = categoriaDAO.eliminar(idCategoria);

                if (eliminada) {
                    session.setAttribute("exito", "Categoría eliminada correctamente");
                } else {
                    session.setAttribute("error", "Error al eliminar categoría");
                }

                response.sendRedirect("ProductoServlet");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("productos.jsp").forward(request, response);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        try {
            if (action.equals("crear")) {
                // CREAR NUEVO PRODUCTO
                Producto nuevoProducto = new Producto();
                nuevoProducto.setNombre(request.getParameter("nombre"));
                nuevoProducto.setDescripcion(request.getParameter("descripcion"));
                nuevoProducto.setPrecio(Double.parseDouble(request.getParameter("precio")));
                nuevoProducto.setStock(Integer.parseInt(request.getParameter("stock")));
                nuevoProducto.setMin_stock(Integer.parseInt(request.getParameter("min_stock")));
                nuevoProducto.setId_categoria(Integer.parseInt(request.getParameter("id_categoria")));

                boolean creado = productoDAO.agregar(nuevoProducto);

                if (creado) {
                    session.setAttribute("exito", "Producto creado correctamente");
                } else {
                    session.setAttribute("error", "Error al crear producto");
                }

            } else if (action.equals("actualizar")) {
                // ACTUALIZAR PRODUCTO
                Producto productoActualizar = new Producto();
                productoActualizar.setId_producto(Integer.parseInt(request.getParameter("id_producto")));
                productoActualizar.setNombre(request.getParameter("nombre"));
                productoActualizar.setDescripcion(request.getParameter("descripcion"));
                productoActualizar.setPrecio(Double.parseDouble(request.getParameter("precio")));
                productoActualizar.setStock(Integer.parseInt(request.getParameter("stock")));
                productoActualizar.setMin_stock(Integer.parseInt(request.getParameter("min_stock")));
                productoActualizar.setId_categoria(Integer.parseInt(request.getParameter("id_categoria")));

                boolean actualizado = productoDAO.editar(productoActualizar);

                if (actualizado) {
                    session.setAttribute("exito", "Producto actualizado correctamente");
                } else {
                    session.setAttribute("error", "Error al actualizar producto");
                }
            } else if (action.equals("crear_categoria")) {
                // CREAR NUEVA CATEGORÍA
                Categoria nuevaCategoria = new Categoria();
                nuevaCategoria.setNombre(request.getParameter("nombre_categoria"));
                nuevaCategoria.setDescripcion(request.getParameter("descripcion_categoria"));

                System.out.println("=== DEBUG CREAR CATEGORIA ===");
                System.out.println("Nombre: " + nuevaCategoria.getNombre());
                System.out.println("Descripción: " + nuevaCategoria.getDescripcion());

                boolean creada = categoriaDAO.agregar(nuevaCategoria);

                System.out.println("Resultado: " + creada);

                if (creada) {
                    session.setAttribute("exito", "Categoría creada correctamente");
                } else {
                    session.setAttribute("error", "Error al crear categoría - Verifique los datos");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error: " + e.getMessage());
            response.sendRedirect("ProductoServlet");
        }
    }
}
