<%-- 
    Document   : gestion_usuarios
    Created on : 18/11/2025, 11:55:51 p. m.
    Author     : rodri
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GestioStock - Gestión de Usuarios</title>
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
        
        /* Sidebar (igual que dashboard) */
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
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            text-align: center;
            transition: all 0.3s;
        }
        
        .btn-primary {
            background: var(--primary);
            color: white;
        }
        
        .btn-primary:hover {
            background: var(--secondary);
        }
        
        .btn-edit {
            background: #3498db;
            color: white;
            padding: 5px 10px;
            border-radius: 3px;
            text-decoration: none;
            font-size: 12px;
        }
        
        .btn-delete {
            background: #e74c3c;
            color: white;
            padding: 5px 10px;
            border-radius: 3px;
            text-decoration: none;
            font-size: 12px;
        }
        
        .btn-permisos {
            background: #9b59b6;
            color: white;
            padding: 5px 10px;
            border-radius: 3px;
            text-decoration: none;
            font-size: 12px;
        }
        
        /* Tabla */
        .table-container {
            overflow-x: auto;
        }
        
        .table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }
        
        .table th,
        .table td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #e0e0e0;
        }
        
        .table th {
            background: #f8f9fa;
            font-weight: bold;
            color: #2c3e50;
        }
        
        .table tr:hover {
            background: #f8f9fa;
        }
        
        .actions {
            display: flex;
            gap: 8px;
        }
        
        /* Formulario */
        .form-container {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin-top: 20px;
        }
        
        .form-group {
            margin-bottom: 15px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #2c3e50;
        }
        
        .form-control {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
        }
        
        .form-row {
            display: flex;
            gap: 15px;
        }
        
        .form-row .form-group {
            flex: 1;
        }
        
        /* Tabs */
        .tabs {
            display: flex;
            margin-bottom: 20px;
            border-bottom: 2px solid #e0e0e0;
        }
        
        .tab {
            padding: 12px 25px;
            cursor: pointer;
            background: none;
            border: none;
            font-size: 16px;
            color: #7f8c8d;
            border-bottom: 2px solid transparent;
            transition: all 0.3s;
        }
        
        .tab.active {
            color: var(--primary);
            border-bottom-color: var(--primary);
            font-weight: bold;
        }
        
        .tab-content {
            display: none;
        }
        
        .tab-content.active {
            display: block;
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
            <li class="menu-item active">
                <a href="GestionUsuariosServlet">Gestión de Usuarios</a>
            </li>
            <li class="menu-item">
                <a href="GestionPermisosServlet">Gestión de Permisos</a>
            </li>
        </ul>
    </div>
    
    <!-- Main Content -->
    <div class="main-content">
        <!-- Header -->
        <div class="header">
            <h2>Gestión de Usuarios</h2>
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
            <h2 class="page-title">Administración de Usuarios</h2>
            
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
            
            <!-- Tabs -->
            <div class="tabs">
                <button class="tab active" onclick="showTab('lista')">Lista de Usuarios</button>
                <button class="tab" onclick="showTab('crear')">Crear Usuario</button>
                <c:if test="${not empty usuarioEditar}">
                    <button class="tab" onclick="showTab('editar')">Editar Usuario</button>
                </c:if>
            </div>
            
            <!-- Tab: Lista de Usuarios -->
            <div id="lista" class="tab-content active">
                <div class="table-container">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Documento</th>
                                <th>Nombre</th>
                                <th>Email</th>
                                <th>Usuario</th>
                                <th>Perfil</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" items="${usuarios}">
                                <tr>
                                    <td>${user.idusu}</td>
                                    <td>${user.num_docu}</td>
                                    <td>${user.nombre} ${user.apellido}</td>
                                    <td>${user.email}</td>
                                    <td>${user.usuario}</td>
                                    <td>
                                        <span style="padding: 4px 8px; border-radius: 3px; 
                                              background: ${user.id_perfil == 1 ? '#3498db' : '#2ecc71'}; 
                                              color: white; font-size: 12px;">
                                            ${user.nombrePerfil}
                                        </span>
                                    </td>
                                    <td class="actions">
                                        <a href="GestionUsuariosServlet?action=editar&id=${user.idusu}" 
                                           class="btn-edit">Editar</a>
                                        <a href="GestionPermisosServlet?id_usuario=${user.idusu}" 
                                           class="btn-permisos">Permisos</a>
                                        <a href="GestionUsuariosServlet?action=eliminar&id=${user.idusu}" 
                                           class="btn-delete" 
                                           onclick="return confirm('¿Está seguro de eliminar este usuario?')">Eliminar</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            
            <!-- Tab: Crear Usuario -->
            <div id="crear" class="tab-content">
                <div class="form-container">
                    <form action="GestionUsuariosServlet" method="post">
                        <input type="hidden" name="action" value="crear">
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="num_docu">Número de Documento:</label>
                                <input type="text" id="num_docu" name="num_docu" class="form-control" required>
                            </div>
                            
                            <div class="form-group">
                                <label for="id_perfil">Perfil:</label>
                                <select id="id_perfil" name="id_perfil" class="form-control" required>
                                    <option value="">Seleccionar perfil</option>
                                    <c:forEach var="perfil" items="${perfiles}">
                                        <option value="${perfil.id_perfil}">${perfil.perfil}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="nombre">Nombre:</label>
                                <input type="text" id="nombre" name="nombre" class="form-control" required>
                            </div>
                            
                            <div class="form-group">
                                <label for="apellido">Apellido:</label>
                                <input type="text" id="apellido" name="apellido" class="form-control" required>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" id="email" name="email" class="form-control" required>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="usuario">Usuario:</label>
                                <input type="text" id="usuario" name="usuario" class="form-control" required>
                            </div>
                            
                            <div class="form-group">
                                <label for="clave">Contraseña:</label>
                                <input type="password" id="clave" name="clave" class="form-control" required>
                            </div>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Crear Usuario</button>
                    </form>
                </div>
            </div>
            
            <!-- Tab: Editar Usuario -->
            <c:if test="${not empty usuarioEditar}">
                <div id="editar" class="tab-content">
                    <div class="form-container">
                        <form action="GestionUsuariosServlet" method="post">
                            <input type="hidden" name="action" value="actualizar">
                            <input type="hidden" name="idusu" value="${usuarioEditar.idusu}">
                            
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="num_docu_edit">Número de Documento:</label>
                                    <input type="text" id="num_docu_edit" name="num_docu" 
                                           value="${usuarioEditar.num_docu}" class="form-control" required>
                                </div>
                                
                                <div class="form-group">
                                    <label for="id_perfil_edit">Perfil:</label>
                                    <select id="id_perfil_edit" name="id_perfil" class="form-control" required>
                                        <c:forEach var="perfil" items="${perfiles}">
                                            <option value="${perfil.id_perfil}" 
                                                    ${perfil.id_perfil == usuarioEditar.id_perfil ? 'selected' : ''}>
                                                ${perfil.perfil}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="nombre_edit">Nombre:</label>
                                    <input type="text" id="nombre_edit" name="nombre" 
                                           value="${usuarioEditar.nombre}" class="form-control" required>
                                </div>
                                
                                <div class="form-group">
                                    <label for="apellido_edit">Apellido:</label>
                                    <input type="text" id="apellido_edit" name="apellido" 
                                           value="${usuarioEditar.apellido}" class="form-control" required>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="email_edit">Email:</label>
                                <input type="email" id="email_edit" name="email" 
                                       value="${usuarioEditar.email}" class="form-control" required>
                            </div>
                            
                            <div class="form-group">
                                <label for="usuario_edit">Usuario:</label>
                                <input type="text" id="usuario_edit" name="usuario" 
                                       value="${usuarioEditar.usuario}" class="form-control" required>
                            </div>
                            
                            <div class="form-group">
                                <label for="clave_edit">Contraseña (dejar en blanco para no cambiar):</label>
                                <input type="password" id="clave_edit" name="clave" class="form-control">
                            </div>
                            
                            <button type="submit" class="btn btn-primary">Actualizar Usuario</button>
                            <a href="GestionUsuariosServlet" class="btn" style="background: #95a5a6; color: white; margin-left: 10px;">Cancelar</a>
                        </form>
                    </div>
                </div>
            </c:if>
        </div>
    </div>

    <script>
        function showTab(tabName) {
            // Ocultar todos los tabs
            document.querySelectorAll('.tab-content').forEach(tab => {
                tab.classList.remove('active');
            });
            
            // Mostrar el tab seleccionado
            document.getElementById(tabName).classList.add('active');
            
            // Actualizar botones activos
            document.querySelectorAll('.tab').forEach(tab => {
                tab.classList.remove('active');
            });
            event.currentTarget.classList.add('active');
        }
        
        // Mostrar tab de editar si existe
        <c:if test="${not empty usuarioEditar}">
            showTab('editar');
        </c:if>
    </script>
</body>
</html>
