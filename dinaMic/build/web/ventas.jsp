<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, modelo.Venta" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GestioStock - Ventas</title>

    <!-- ESTILOS IDENTICOS A GESTIÓN PERMISOS -->
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: Arial, sans-serif; }
        :root { --primary: #667eea; --secondary: #764ba2; --sidebar-width: 250px; }
        body { display: flex; background: #f5f6fa; }

        /* Sidebar */
        .sidebar {
            width: var(--sidebar-width);
            background: linear-gradient(135deg, var(--primary) 0%, var(--secondary) 100%);
            color: white;
            height: 100vh;
            position: fixed;
            padding: 20px 0;
        }
        .logo { text-align: center; padding: 20px; margin-bottom: 20px; border-bottom: 1px solid rgba(255,255,255,0.1); }
        .logo h1 { font-size: 24px; font-weight: bold; }
        .menu { list-style: none; }
        .menu-item { padding: 15px 25px; cursor: pointer; transition: background .3s; border-left: 4px solid transparent; }
        .menu-item:hover { background: rgba(255,255,255,0.1); border-left-color: white; }
        .menu-item.active { background: rgba(255,255,255,0.2); border-left-color: white; }
        .menu-item a { color: white; text-decoration: none; display: block; }

        /* Main Content */
        .main-content { flex: 1; margin-left: var(--sidebar-width); padding: 20px; }

        /* Header */
        .header {
            background: white; padding: 20px; border-radius: 10px;
            margin-bottom: 20px;
            display: flex; justify-content: space-between; align-items: center;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .user-info { display: flex; align-items: center; gap: 15px; }
        .user-avatar {
            width: 40px; height: 40px; background: var(--primary); color: white;
            border-radius: 50%; display: flex; align-items: center; justify-content: center; font-weight: bold;
        }
        .btn-logout { background: #e74c3c; color: white; padding: 8px 15px; border-radius: 5px; text-decoration: none; }
        .btn-logout:hover { background: #c0392b; }

        /* Container */
        .container { background: white; padding: 25px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .page-title { color: #2c3e50; margin-bottom: 25px; padding-bottom: 15px; border-bottom: 2px solid var(--primary); }

        /* Alerts */
        .alert { padding: 12px 15px; border-radius: 5px; margin-bottom: 20px; }
        .alert-success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }

        /* Formulario */
        .form-group { margin-bottom: 15px; }
        label { font-weight: bold; color: #2c3e50; }
        input {
            width: 100%; padding: 10px; border-radius: 5px;
            border: 1px solid #ddd; margin-top: 5px;
        }
        .btn { padding: 8px 15px; border-radius: 5px; cursor: pointer; border: none; color: white; }
        .btn-primary { background: var(--primary); }
        .btn-primary:hover { background: var(--secondary); }
        .btn-danger { background: #e74c3c; }
        .btn-danger:hover { background: #c0392b; }

        /* Tabla */
        table {
            width: 100%; border-collapse: collapse; margin-top: 20px;
        }
        table th, table td {
            padding: 12px; border-bottom: 1px solid #eee; text-align: left;
        }
        table th { background: #f8f9fa; }
        .acciones a { margin-right: 10px; color: var(--primary); font-weight: bold; text-decoration: none; }
        .acciones a:hover { text-decoration: underline; }
    </style>
</head>

<body>

<!-- SIDEBAR -->
<div class="sidebar">
    <div class="logo"><h1>GestioStock</h1></div>
    <ul class="menu">
        <li class="menu-item"><a href="DashboardServlet">Dashboard</a></li>
        <li class="menu-item"><a href="ProductoServlet">Productos</a></li>
        <li class="menu-item active"><a href="VentaServlet">Ventas</a></li>
        <li class="menu-item"><a href="ReporteServlet">Reportes</a></li>
        <li class="menu-item"><a href="AlertaServlet">Alertas</a></li>
        <li class="menu-item"><a href="GestionUsuariosServlet">Gestión de Usuarios</a></li>
        <li class="menu-item"><a href="GestionPermisosServlet">Gestión de Permisos</a></li>
    </ul>
</div>

<!-- MAIN CONTENT -->
<div class="main-content">

    <!-- Header -->
    <div class="header">
        <h2>Gestión de Ventas</h2>

        <div class="user-info">
            <div class="user-avatar">
                ${usuario.nombre.charAt(0)}${usuario.apellido.charAt(0)}
            </div>
            <div>
                <strong>${usuario.nombreCompleto}</strong>
                <div style="font-size:12px;color:#7f8c8d;">${usuario.nombrePerfil}</div>
            </div>
            <a href="LogoutServlet" class="btn-logout">Cerrar Sesión</a>
        </div>
    </div>

    <div class="container">

        <h2 class="page-title">Registrar / Editar Venta</h2>

        <!-- MENSAJES -->
        <c:if test="${not empty exito}">
            <div class="alert alert-success">${exito}</div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <!-- Formulario -->
        <%
            Venta ventaEditar = (Venta) request.getAttribute("ventaEditar");
            boolean editando = ventaEditar != null;
        %>

        <form action="VentaServlet" method="post">
            <input type="hidden" name="action" value="<%= editando ? "actualizar" : "crear" %>">
            <% if(editando){ %>
                <input type="hidden" name="id_venta" value="<%= ventaEditar.getId_venta() %>">
            <% } %>

            <div class="form-group">
                <label>Fecha Venta</label>
                <input type="date" name="fecha_venta"
                       value="<%= editando ? ventaEditar.getFecha_venta() : "" %>" required>
            </div>

            <div class="form-group">
                <label>ID Usuario</label>
                <input type="number" name="id_usuario"
                       value="<%= editando ? ventaEditar.getId_usuario() : "" %>" required>
            </div>

            <div class="form-group">
                <label>Total</label>
                <input type="number" step="0.01" name="total"
                       value="<%= editando ? ventaEditar.getTotal() : "" %>" required>
            </div>

            <button type="submit" class="btn btn-primary">
                <%= editando ? "Actualizar Venta" : "Registrar Venta" %>
            </button>
        </form>

        <!-- LISTADO -->
        <h2 class="page-title" style="margin-top:30px;">Listado de Ventas</h2>

        <table>
            <tr>
                <th>ID</th>
                <th>Fecha</th>
                <th>ID Usuario</th>
                <th>Total</th>
                <th>Acciones</th>
            </tr>

            <c:forEach var="v" items="${ventas}">
                <tr>
                    <td>${v.id_venta}</td>
                    <td>${v.fecha_venta}</td>
                    <td>${v.id_usuario}</td>
                    <td>${v.total}</td>

                    <td class="acciones">
                        <a href="VentaServlet?action=editar&id=${v.id_venta}">Editar</a>
                        <a href="VentaServlet?action=eliminar&id=${v.id_venta}"
                           onclick="return confirm('¿Eliminar venta?')">Eliminar</a>
                    </td>
                </tr>
            </c:forEach>

        </table>

    </div>
</div>

</body>
</html>
