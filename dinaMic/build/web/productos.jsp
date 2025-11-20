<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GestioStock - Productos</title>
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
        
        .btn-categoria {
            background: #9b59b6;
            color: white;
            padding: 5px 10px;
            border-radius: 3px;
            text-decoration: none;
            font-size: 12px;
        }
        
        .btn-warning {
            background: #f39c12;
            color: white;
        }
        
        .btn-warning:hover {
            background: #d35400;
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
        
        .stock-bajo {
            color: #e74c3c;
            font-weight: bold;
        }
        
        .stock-normal {
            color: #27ae60;
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
        
        /* Badges */
        .badge {
            padding: 4px 8px;
            border-radius: 3px;
            font-size: 12px;
            font-weight: bold;
        }
        
        .badge-success {
            background: #d4edda;
            color: #155724;
        }
        
        .badge-warning {
            background: #fff3cd;
            color: #856404;
        }
        
        .badge-danger {
            background: #f8d7da;
            color: #721c24;
        }
        
        /* Sección de Categorías */
        .categorias-section {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 2px solid #e0e0e0;
        }
        
        .categorias-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            margin-top: 15px;
        }
        
        .categoria-card {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 8px;
            border: 1px solid #e0e0e0;
        }
        
        .categoria-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 10px;
        }
        
        .categoria-nombre {
            font-weight: bold;
            color: #2c3e50;
            font-size: 16px;
        }
        
        .categoria-descripcion {
            color: #7f8c8d;
            font-size: 14px;
        }
        
        .empty-message {
            padding: 20px;
            text-align: center;
            color: #7f8c8d;
            font-style: italic;
            grid-column: 1 / -1;
        }
        
        @media (max-width: 768px) {
            .categorias-grid {
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
            <li class="menu-item active">
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
            <li class="menu-item">
                <a href="GestionPermisosServlet">Gestión de Permisos</a>
            </li>
        </ul>
    </div>
    
    <!-- Main Content -->
    <div class="main-content">
        <!-- Header -->
        <div class="header">
            <h2>Gestión de Productos</h2>
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
            <h2 class="page-title">Administración de Productos</h2>
            
            <!-- Mensajes -->
<c:if test="${not empty sessionScope.exito}">
    <div class="alert alert-success">${sessionScope.exito}</div>
    <c:remove var="exito" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.error}">
    <div class="alert alert-error">${sessionScope.error}</div>
    <c:remove var="error" scope="session"/>
</c:if>

            
            <!-- Tabs -->
            <div class="tabs">
                <button class="tab active" onclick="showTab('lista')">Lista de Productos</button>
                <button class="tab" onclick="showTab('crear')">Crear Producto</button>
                <button class="tab" onclick="showTab('categorias')">Gestión de Categorías</button>
                <c:if test="${not empty productoEditar}">
                    <button class="tab" onclick="showTab('editar')">Editar Producto</button>
                </c:if>
            </div>
            
            <!-- Tab: Lista de Productos -->
            <div id="lista" class="tab-content active">
                <div class="table-container">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Descripción</th>
                                <th>Precio</th>
                                <th>Stock</th>
                                <th>Categoría</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="producto" items="${productos}">
                                <tr>
                                    <td>${producto.id_producto}</td>
                                    <td>${producto.nombre}</td>
                                    <td>${producto.descripcion}</td>
                                    <td>$${producto.precio}</td>
                                    <td class="${producto.stock <= producto.min_stock ? 'stock-bajo' : 'stock-normal'}">
                                        ${producto.stock}
                                        <c:if test="${producto.stock <= producto.min_stock}">
                                            <span class="badge badge-danger">¡Stock Bajo!</span>
                                        </c:if>
                                    </td>
                                    <td>${producto.nombreCategoria}</td>
                                    <td class="actions">
                                        <a href="ProductoServlet?action=editar&id=${producto.id_producto}" 
                                           class="btn-edit">Editar</a>
                                        <a href="ProductoServlet?action=eliminar&id=${producto.id_producto}" 
                                           class="btn-delete" 
                                           onclick="return confirm('¿Está seguro de eliminar este producto?')">Eliminar</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            
            <!-- Tab: Crear Producto -->
            <div id="crear" class="tab-content">
                <div class="form-container">
                    <form action="ProductoServlet" method="post">
                        <input type="hidden" name="action" value="crear">
                        
                        <div class="form-group">
                            <label for="nombre">Nombre del Producto:</label>
                            <input type="text" id="nombre" name="nombre" class="form-control" required 
                                   placeholder="Ingrese el nombre del producto">
                        </div>
                        
                        <div class="form-group">
                            <label for="descripcion">Descripción:</label>
                            <textarea id="descripcion" name="descripcion" class="form-control" rows="3"
                                      placeholder="Ingrese la descripción del producto"></textarea>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="precio">Precio:</label>
                                <input type="number" id="precio" name="precio" class="form-control" required 
                                       step="0.01" min="0" placeholder="0.00">
                            </div>
                            
                            <div class="form-group">
                                <label for="stock">Stock Inicial:</label>
                                <input type="number" id="stock" name="stock" class="form-control" required 
                                       min="0" placeholder="0">
                            </div>
                            
                            <div class="form-group">
                                <label for="min_stock">Stock Mínimo:</label>
                                <input type="number" id="min_stock" name="min_stock" class="form-control" required 
                                       min="0" placeholder="5" value="5">
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="id_categoria">Categoría:</label>
                            <select id="id_categoria" name="id_categoria" class="form-control" required>
                                <option value="">Seleccione una categoría</option>
                                <c:forEach var="categoria" items="${categorias}">
                                    <option value="${categoria.id_categoria}">${categoria.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Crear Producto</button>
                    </form>
                </div>
            </div>
            
            <!-- Tab: Gestión de Categorías -->
            <div id="categorias" class="tab-content">
                <div class="form-container">
                    <h3 style="color: #2c3e50; margin-bottom: 20px;">Crear Nueva Categoría</h3>
                    <form action="ProductoServlet" method="post">
                        <input type="hidden" name="action" value="crear_categoria">
                        
                        <div class="form-group">
                            <label for="nombre_categoria">Nombre de la Categoría:</label>
                            <input type="text" id="nombre_categoria" name="nombre_categoria" class="form-control" required 
                                   placeholder="Ingrese el nombre de la categoría">
                        </div>
                        
                        <div class="form-group">
                            <label for="descripcion_categoria">Descripción:</label>
                            <textarea id="descripcion_categoria" name="descripcion_categoria" class="form-control" rows="3"
                                      placeholder="Ingrese la descripción de la categoría"></textarea>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Crear Categoría</button>
                    </form>
                </div>
                
                <div class="categorias-section">
                    <h3 style="color: #2c3e50; margin-bottom: 15px;">Categorías Existentes</h3>
                    <div class="categorias-grid">
                        <c:if test="${empty categorias}">
                            <div class="empty-message">No hay categorías registradas</div>
                        </c:if>
                        <c:forEach var="categoria" items="${categorias}">
                            <div class="categoria-card">
                                <div class="categoria-header">
                                    <div class="categoria-nombre">${categoria.nombre}</div>
                                    <div class="actions">
                                        <a href="ProductoServlet?action=eliminar_categoria&id=${categoria.id_categoria}" 
                                           class="btn-delete" 
                                           onclick="return confirm('¿Está seguro de eliminar esta categoría?')">Eliminar</a>
                                    </div>
                                </div>
                                <div class="categoria-descripcion">
                                    ${categoria.descripcion}
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            
            <!-- Tab: Editar Producto -->
            <c:if test="${not empty productoEditar}">
                <div id="editar" class="tab-content">
                    <div class="form-container">
                        <form action="ProductoServlet" method="post">
                            <input type="hidden" name="action" value="actualizar">
                            <input type="hidden" name="id_producto" value="${productoEditar.id_producto}">
                            
                            <div class="form-group">
                                <label for="nombre_edit">Nombre del Producto:</label>
                                <input type="text" id="nombre_edit" name="nombre" class="form-control" required 
                                       value="${productoEditar.nombre}">
                            </div>
                            
                            <div class="form-group">
                                <label for="descripcion_edit">Descripción:</label>
                                <textarea id="descripcion_edit" name="descripcion" class="form-control" rows="3">${productoEditar.descripcion}</textarea>
                            </div>
                            
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="precio_edit">Precio:</label>
                                    <input type="number" id="precio_edit" name="precio" class="form-control" required 
                                           step="0.01" min="0" value="${productoEditar.precio}">
                                </div>
                                
                                <div class="form-group">
                                    <label for="stock_edit">Stock:</label>
                                    <input type="number" id="stock_edit" name="stock" class="form-control" required 
                                           min="0" value="${productoEditar.stock}">
                                </div>
                                
                                <div class="form-group">
                                    <label for="min_stock_edit">Stock Mínimo:</label>
                                    <input type="number" id="min_stock_edit" name="min_stock" class="form-control" required 
                                           min="0" value="${productoEditar.min_stock}">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="id_categoria_edit">Categoría:</label>
                                <select id="id_categoria_edit" name="id_categoria" class="form-control" required>
                                    <c:forEach var="categoria" items="${categorias}">
                                        <option value="${categoria.id_categoria}" 
                                                ${categoria.id_categoria == productoEditar.id_categoria ? 'selected' : ''}>
                                            ${categoria.nombre}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            
                            <button type="submit" class="btn btn-primary">Actualizar Producto</button>
                            <a href="ProductoServlet" class="btn" style="background: #95a5a6; color: white; margin-left: 10px;">Cancelar</a>
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
        <c:if test="${not empty productoEditar}">
            showTab('editar');
        </c:if>
    </script>
</body>
</html>