<%-- 
    Document   : gestion-permisos
    Created on : 19/11/2025, 12:06:05 a. m.
    Author     : rodri
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GestioStock - Gestión de Permisos</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Arial', sans-serif;
        }
        
        :root {
            --primary: #667eea;
            --secondary: #764ba2;
            --sidebar-width: 250px;
        }
        
        body {
            display: flex;
            background: #f5f6fa;
        }
        
        /* Sidebar (igual que las otras vistas) */
        .sidebar {
            width: var(--sidebar-width);
            background: linear-gradient(135deg, var(--primary) 0%, var(--secondary) 100%);
            color: white;
            height: 100vh;
            position: fixed;
            padding: 20px 0;
        }
        
        .logo {
            text-align: center;
            padding: 20px;
            border-bottom: 1px solid rgba(255,255,255,0.1);
            margin-bottom: 20px;
        }
        
        .logo h1 {
            font-size: 24px;
            font-weight: bold;
        }
        
        .menu {
            list-style: none;
        }
        
        .menu-item {
            padding: 15px 25px;
            cursor: pointer;
            transition: background 0.3s;
            border-left: 4px solid transparent;
        }
        
        .menu-item:hover {
            background: rgba(255,255,255,0.1);
            border-left-color: white;
        }
        
        .menu-item.active {
            background: rgba(255,255,255,0.2);
            border-left-color: white;
        }
        
        .menu-item a {
            color: white;
            text-decoration: none;
            display: block;
        }
        
        /* Main Content */
        .main-content {
            flex: 1;
            margin-left: var(--sidebar-width);
            padding: 20px;
        }
        
        /* Header */
        .header {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .user-info {
            display: flex;
            align-items: center;
            gap: 15px;
        }
        
        .user-avatar {
            width: 40px;
            height: 40px;
            background: var(--primary);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: bold;
        }
        
        .btn-logout {
            background: #e74c3c;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
        }
        
        .btn-logout:hover {
            background: #c0392b;
        }
        
        /* Contenido específico */
        .container {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .page-title {
            color: #2c3e50;
            margin-bottom: 25px;
            padding-bottom: 15px;
            border-bottom: 2px solid var(--primary);
        }
        
        /* Mensajes */
        .alert {
            padding: 12px 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        
        .alert-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        
        .alert-error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        
        /* Botones */
        .btn {
            padding: 8px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            text-align: center;
            transition: all 0.3s;
            font-size: 14px;
        }
        
        .btn-primary {
            background: var(--primary);
            color: white;
        }
        
        .btn-primary:hover {
            background: var(--secondary);
        }
        
        .btn-danger {
            background: #e74c3c;
            color: white;
        }
        
        .btn-danger:hover {
            background: #c0392b;
        }
        
        .btn-success {
            background: #27ae60;
            color: white;
        }
        
        .btn-success:hover {
            background: #219a52;
        }
        
        .btn-warning {
            background: #f39c12;
            color: white;
        }
        
        .btn-warning:hover {
            background: #d35400;
        }
        
        /* Selector de usuario */
        .user-selector {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        
        .user-selector label {
            font-weight: bold;
            margin-bottom: 8px;
            display: block;
            color: #2c3e50;
        }
        
        .user-selector select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
        }
        
        /* Paneles de permisos */
        .permisos-container {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            margin-top: 20px;
        }
        
        .panel {
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            overflow: hidden;
        }
        
        .panel-header {
            background: #f8f9fa;
            padding: 15px;
            border-bottom: 1px solid #e0e0e0;
            font-weight: bold;
            color: #2c3e50;
        }
        
        .panel-body {
            padding: 0;
            max-height: 400px;
            overflow-y: auto;
        }
        
        .actividad-item {
            padding: 12px 15px;
            border-bottom: 1px solid #f0f0f0;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .actividad-item:last-child {
            border-bottom: none;
        }
        
        .actividad-item:hover {
            background: #f8f9fa;
        }
        
        .actividad-info {
            flex: 1;
        }
        
        .actividad-nombre {
            font-weight: bold;
            color: #2c3e50;
        }
        
        .actividad-enlace {
            font-size: 12px;
            color: #7f8c8d;
            margin-top: 2px;
        }
        
        .empty-message {
            padding: 20px;
            text-align: center;
            color: #7f8c8d;
            font-style: italic;
        }
        
        /* Reporte de asignaciones */
        .reporte-container {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 2px solid #e0e0e0;
        }
        
        .reporte-list {
            max-height: 300px;
            overflow-y: auto;
            border: 1px solid #e0e0e0;
            border-radius: 5px;
            padding: 10px;
        }
        
        .reporte-item {
            padding: 8px 10px;
            border-bottom: 1px solid #f0f0f0;
        }
        
        .reporte-item:last-child {
            border-bottom: none;
        }
        
        /* Responsive */
        @media (max-width: 768px) {
            .permisos-container {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <!-- Sidebar -->
    <div class="sidebar">
        <div class="logo">
            <h1>GestioStock</h1>
        </div>
        
        <ul class="menu">
            <li class="menu-item">
                <a href="DashboardServlet">Dashboard</a>
            </li>
            <li class="menu-item">
                <a href="ProductoServlet">Productos</a>
            </li>
            <li class="menu-item">
                <a href="VentaServlet">Ventas</a>
            </li>
            <li class="menu-item">
                <a href="ReporteServlet">Reportes</a>
            </li>
            <li class="menu-item">
                <a href="AlertaServlet">Alertas</a>
            </li>
            <li class="menu-item">
                <a href="GestionUsuariosServlet">Gestión de Usuarios</a>
            </li>
            <li class="menu-item active">
                <a href="GestionPermisosServlet">Gestión de Permisos</a>
            </li>
        </ul>
    </div>
    
    <!-- Main Content -->
    <div class="main-content">
        <!-- Header -->
        <div class="header">
            <h2>Gestión de Permisos</h2>
            <div class="user-info">
                <div class="user-avatar">
                    ${usuario.nombre.charAt(0)}${usuario.apellido.charAt(0)}
                </div>
                <div>
                    <strong>${usuario.nombreCompleto}</strong>
                    <div style="font-size: 12px; color: #7f8c8d;">${usuario.nombrePerfil}</div>
                </div>
                <a href="LogoutServlet" class="btn-logout">Cerrar Sesión</a>
            </div>
        </div>
        
        <div class="container">
            <h2 class="page-title">Administración de Permisos por Usuario</h2>
            
            <!-- Mensajes -->
            <c:if test="${not empty exito}">
                <div class="alert alert-success">
                    ${exito}
                </div>
            </c:if>
            
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    ${error}
                </div>
            </c:if>
            
            <!-- Selector de Usuario -->
            <div class="user-selector">
                <label for="selectUsuario">Seleccionar Usuario:</label>
                <select id="selectUsuario" onchange="cambiarUsuario()">
                    <option value="">-- Seleccione un usuario --</option>
                    <c:forEach var="user" items="${usuarios}">
                        <option value="${user.idusu}" 
                                ${user.idusu == usuarioSeleccionado.idusu ? 'selected' : ''}>
                            ${user.nombre} ${user.apellido} (${user.usuario}) - ${user.nombrePerfil}
                        </option>
                    </c:forEach>
                </select>
            </div>
            
            <!-- Información del Usuario Seleccionado -->
            <c:if test="${not empty usuarioSeleccionado}">
                <div style="background: #e8f4fd; padding: 15px; border-radius: 8px; margin-bottom: 20px;">
                    <h3 style="color: #2c3e50; margin-bottom: 10px;">
                        Permisos de: ${usuarioSeleccionado.nombre} ${usuarioSeleccionado.apellido}
                    </h3>
                    <p style="margin: 5px 0;"><strong>Usuario:</strong> ${usuarioSeleccionado.usuario}</p>
                    <p style="margin: 5px 0;"><strong>Email:</strong> ${usuarioSeleccionado.email}</p>
                    <p style="margin: 5px 0;"><strong>Perfil:</strong> ${usuarioSeleccionado.nombrePerfil}</p>
                    
                    <form action="GestionPermisosServlet" method="post" style="margin-top: 10px;">
                        <input type="hidden" name="action" value="limpiar">
                        <input type="hidden" name="id_usuario" value="${usuarioSeleccionado.idusu}">
                        <button type="submit" class="btn btn-warning" 
                                onclick="return confirm('¿Está seguro de eliminar TODOS los permisos de este usuario?')">
                            Limpiar Todos los Permisos
                        </button>
                    </form>
                </div>
                
                <!-- Paneles de Permisos -->
                <div class="permisos-container">
                    <!-- Panel: Permisos Asignados -->
                    <div class="panel">
                        <div class="panel-header">
                            Permisos Asignados (${actividadesAsignadas.size()})
                        </div>
                        <div class="panel-body">
                            <c:if test="${empty actividadesAsignadas}">
                                <div class="empty-message">
                                    No hay permisos asignados
                                </div>
                            </c:if>
                            <c:forEach var="actividad" items="${actividadesAsignadas}">
                                <div class="actividad-item">
                                    <div class="actividad-info">
                                        <div class="actividad-nombre">${actividad.nom_actividad}</div>
                                        <div class="actividad-enlace">${actividad.enlace}</div>
                                    </div>
                                    <form action="GestionPermisosServlet" method="post" style="margin: 0;">
                                        <input type="hidden" name="action" value="eliminar">
                                        <input type="hidden" name="id_usuario" value="${usuarioSeleccionado.idusu}">
                                        <input type="hidden" name="id_actividad" value="${actividad.id_actividad}">
                                        <button type="submit" class="btn btn-danger" 
                                                onclick="return confirm('¿Quitar este permiso?')">
                                            Quitar
                                        </button>
                                    </form>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    
                    <!-- Panel: Permisos Disponibles -->
                    <div class="panel">
                        <div class="panel-header">
                            Permisos Disponibles (${actividadesNoAsignadas.size()})
                        </div>
                        <div class="panel-body">
                            <c:if test="${empty actividadesNoAsignadas}">
                                <div class="empty-message">
                                    No hay permisos disponibles
                                </div>
                            </c:if>
                            <c:forEach var="actividad" items="${actividadesNoAsignadas}">
                                <div class="actividad-item">
                                    <div class="actividad-info">
                                        <div class="actividad-nombre">${actividad.nom_actividad}</div>
                                        <div class="actividad-enlace">${actividad.enlace}</div>
                                    </div>
                                    <form action="GestionPermisosServlet" method="post" style="margin: 0;">
                                        <input type="hidden" name="action" value="asignar">
                                        <input type="hidden" name="id_usuario" value="${usuarioSeleccionado.idusu}">
                                        <input type="hidden" name="id_actividad" value="${actividad.id_actividad}">
                                        <button type="submit" class="btn btn-success">
                                            Asignar
                                        </button>
                                    </form>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:if>
            
            <!-- Reporte de Todas las Asignaciones -->
            <div class="reporte-container">
                <h3 style="color: #2c3e50; margin-bottom: 15px;">Reporte de Todas las Asignaciones</h3>
                <div class="reporte-list">
                    <c:if test="${empty asignaciones}">
                        <div class="empty-message">No hay asignaciones registradas</div>
                    </c:if>
                    <c:forEach var="asignacion" items="${asignaciones}">
                        <div class="reporte-item">${asignacion}</div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

    <script>
        function cambiarUsuario() {
            var select = document.getElementById('selectUsuario');
            var userId = select.value;
            if (userId) {
                window.location.href = 'GestionPermisosServlet?id_usuario=' + userId;
            }
        }
        
        // Auto-scroll para mensajes
        <c:if test="${not empty exito or not empty error}">
            window.scrollTo(0, 0);
        </c:if>
    </script>
</body>
</html>