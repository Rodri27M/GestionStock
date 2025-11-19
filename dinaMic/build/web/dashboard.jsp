<%-- 
    Document   : dashboard
    Created on : 13/11/2025, 4:24:30 p. m.
    Author     : rodri
--%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>GestioStock - Dashboard</title>
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

            /* Sidebar */
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

            /* Cards */
            .cards-container {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                gap: 20px;
                margin-bottom: 20px;
            }

            .card {
                background: white;
                padding: 25px;
                border-radius: 10px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                text-align: center;
            }

            .card-icon {
                font-size: 40px;
                margin-bottom: 15px;
                color: var(--primary);
            }

            .card-number {
                font-size: 32px;
                font-weight: bold;
                color: #2c3e50;
                margin-bottom: 5px;
            }

            .card-label {
                color: #7f8c8d;
                font-size: 14px;
            }

            /* Admin Section */
            .admin-section {
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                margin-top: 20px;
            }

            .admin-section h3 {
                color: #2c3e50;
                margin-bottom: 15px;
                padding-bottom: 10px;
                border-bottom: 2px solid var(--primary);
            }

            .admin-actions {
                display: flex;
                gap: 15px;
            }

            .admin-btn {
                background: var(--primary);
                color: white;
                padding: 10px 20px;
                border-radius: 5px;
                text-decoration: none;
                transition: background 0.3s;
            }

            .admin-btn:hover {
                background: var(--secondary);
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
                <li class="menu-item ${pageContext.request.requestURI.contains('dashboard') ? 'active' : ''}">
                    <a href="DashboardServlet">Dashboard</a>
                </li>

                <%-- MENÚ DINÁMICO SEGÚN ACTIVIDADES PERMITIDAS --%>
                <c:forEach var="actividad" items="${actividades}">
                    <c:choose>
                        <c:when test="${actividad.nom_actividad == 'Dashboard'}">
                            <%-- Dashboard ya está arriba --%>
                        </c:when>
                        <c:when test="${actividad.nom_actividad == 'Gestión de Productos'}">
                            <li class="menu-item ${pageContext.request.requestURI.contains('producto') ? 'active' : ''}">
                                <a href="ProductoServlet">Productos</a>
                            </li>
                        </c:when>
                        <c:when test="${actividad.nom_actividad == 'Registro de Ventas'}">
                            <li class="menu-item ${pageContext.request.requestURI.contains('venta') ? 'active' : ''}">
                                <a href="VentaServlet">Ventas</a>
                            </li>
                        </c:when>
                        <c:when test="${actividad.nom_actividad == 'Reportes'}">
                            <li class="menu-item ${pageContext.request.requestURI.contains('reporte') ? 'active' : ''}">
                                <a href="ReporteServlet">Reportes</a>
                            </li>
                        </c:when>
                        <c:when test="${actividad.nom_actividad == 'Alertas'}">
                            <li class="menu-item ${pageContext.request.requestURI.contains('alerta') ? 'active' : ''}">
                                <a href="AlertaServlet">Alertas</a>
                            </li>
                        </c:when>
                        <c:when test="${actividad.nom_actividad == 'Gestión de Usuarios'}">
                            <li class="menu-item ${pageContext.request.requestURI.contains('usuario') ? 'active' : ''}">
                                <a href="GestionUsuariosServlet">Gestión de Usuarios</a>
                            </li>
                        </c:when>
                        <c:when test="${actividad.nom_actividad == 'Gestión de Permisos'}">
                            <li class="menu-item ${pageContext.request.requestURI.contains('permiso') ? 'active' : ''}">
                                <a href="GestionPermisosServlet">Gestión de Permisos</a>
                            </li>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </ul>
        </div>

        <!-- Main Content -->
        <div class="main-content">
            <!-- Header -->
            <div class="header">
                <h2>Dashboard Principal</h2>
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

            <!-- Cards de Resumen -->
            <div class="cards-container">
                <div class="card">
                    <div class="card-icon">📦</div>
                    <div class="card-number">0</div>
                    <div class="card-label">Productos en Inventario</div>
                </div>

                <div class="card">
                    <div class="card-icon">💰</div>
                    <div class="card-number">0</div>
                    <div class="card-label">Ventas del Mes</div>
                </div>

                <div class="card">
                    <div class="card-icon">⚠️</div>
                    <div class="card-number">0</div>
                    <div class="card-label">Alertas de Stock</div>
                </div>

                <div class="card">
                    <div class="card-icon">👥</div>
                    <div class="card-number">${totalUsuarios}</div>
                    <div class="card-label">Usuarios Activos</div>
                </div>
            </div>

            <!-- Sección de Administrador -->
            <c:if test="${esAdmin}">
                <div class="admin-section">
                    <h3>Panel de Administración</h3>
                    <div class="admin-actions">
                        <a href="GestionUsuariosServlet" class="admin-btn">Gestión de Usuarios</a>
                        <a href="GestionPermisosServlet" class="admin-btn">Gestión de Permisos</a>
                        <a href="ConfiguracionServlet" class="admin-btn">Configuración del Sistema</a>
                    </div>
                </div>
            </c:if>
        </div>
    </body>
</html>
