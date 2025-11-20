package servlet;

import controladorDAO.VentaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import modelo.Venta;
import java.io.IOException;
import java.util.List;

@WebServlet("/VentaServlet")
public class VentaServlet extends HttpServlet {

    private VentaDAO ventaDAO;

    @Override
    public void init() {
        ventaDAO = new VentaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String action = req.getParameter("action");

        try {
            if (action == null) {
                List<Venta> ventas = ventaDAO.listar();
                req.setAttribute("ventas", ventas);
                req.getRequestDispatcher("ventas.jsp").forward(req, resp);

            } else if (action.equals("editar")) {
                int id = Integer.parseInt(req.getParameter("id"));
                Venta venta = ventaDAO.listarPorId(id);
                req.setAttribute("ventaEditar", venta);

                req.getRequestDispatcher("ventas.jsp").forward(req, resp);

            } else if (action.equals("eliminar")) {
                int id = Integer.parseInt(req.getParameter("id"));
                if (ventaDAO.eliminar(id)) {
                    session.setAttribute("exito", "Venta eliminada correctamente");
                } else {
                    session.setAttribute("error", "No se pudo eliminar la venta");
                }
                resp.sendRedirect("VentaServlet");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            resp.sendRedirect("VentaServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String action = req.getParameter("action");

        try {
            if (action.equals("crear")) {

                Venta v = new Venta();
                v.setFecha_venta(req.getParameter("fecha_venta"));
                v.setId_usuario(Integer.parseInt(req.getParameter("id_usuario")));
                v.setTotal(Double.parseDouble(req.getParameter("total")));

                if (ventaDAO.agregar(v))
                    session.setAttribute("exito", "Venta creada correctamente");
                else
                    session.setAttribute("error", "Error al crear venta");

            } else if (action.equals("actualizar")) {

                Venta v = new Venta();
                v.setId_venta(Integer.parseInt(req.getParameter("id_venta")));
                v.setFecha_venta(req.getParameter("fecha_venta"));
                v.setId_usuario(Integer.parseInt(req.getParameter("id_usuario")));
                v.setTotal(Double.parseDouble(req.getParameter("total")));

                if (ventaDAO.editar(v))
                    session.setAttribute("exito", "Venta actualizada");
                else
                    session.setAttribute("error", "Error al actualizar venta");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
        }

        resp.sendRedirect("VentaServlet");
    }
}
