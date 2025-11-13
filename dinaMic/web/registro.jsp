<%-- 
    Document   : Registro
    Created on : 13/11/2025, 2:48:33 p. m.
    Author     : rodri
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GestioStock - Registro</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Arial', sans-serif;
        }
        
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .register-container {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 500px;
        }
        
        .logo {
            text-align: center;
            margin-bottom: 30px;
        }
        
        .logo h1 {
            color: #333;
            font-size: 28px;
            font-weight: bold;
        }
        
        .form-row {
            display: flex;
            gap: 15px;
            margin-bottom: 20px;
        }
        
        .form-group {
            flex: 1;
            margin-bottom: 0;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #555;
            font-weight: bold;
        }
        
        .form-group input, .form-group select {
            width: 100%;
            padding: 12px;
            border: 2px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        
        .form-group input:focus, .form-group select:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .btn-register {
            width: 100%;
            padding: 12px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: transform 0.2s;
            margin-top: 10px;
        }
        
        .btn-register:hover {
            transform: translateY(-2px);
        }
        
        .links {
            text-align: center;
            margin-top: 20px;
        }
        
        .links a {
            color: #667eea;
            text-decoration: none;
        }
        
        .links a:hover {
            text-decoration: underline;
        }
        
        .developers {
            text-align: center;
            margin-top: 30px;
            color: #666;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="register-container">
        <div class="logo">
            <h1>GestioStock - Registro</h1>
        </div>
        
        <form action="RegistroServlet" method="post">
            <div class="form-row">
                <div class="form-group">
                    <label for="nombre">Nombre:</label>
                    <input type="text" id="nombre" name="nombre" required 
                           placeholder="Ingrese su nombre">
                </div>
                
                <div class="form-group">
                    <label for="apellido">Apellido:</label>
                    <input type="text" id="apellido" name="apellido" required 
                           placeholder="Ingrese su apellido">
                </div>
            </div>
            
            <div class="form-group">
                <label for="num_docu">Número de Documento:</label>
                <input type="text" id="num_docu" name="num_docu" required 
                       placeholder="Ingrese su documento">
            </div>
            
            <div class="form-group">
                <label for="email">Correo Electrónico:</label>
                <input type="email" id="email" name="email" required 
                       placeholder="Ingrese su email">
            </div>
            
            <div class="form-group">
                <label for="usuario">Usuario:</label>
                <input type="text" id="usuario" name="usuario" required 
                       placeholder="Cree un nombre de usuario">
            </div>
            
            <div class="form-group">
                <label for="clave">Contraseña:</label>
                <input type="password" id="clave" name="clave" required 
                       placeholder="Cree una contraseña">
            </div>
            
            <div class="form-group">
                <label for="id_perfil">Perfil:</label>
                <select id="id_perfil" name="id_perfil" required>
                    <option value="">Seleccione un perfil</option>
                    <option value="2">Usuario</option>
                    <!-- El valor 1 (Admin) solo se asigna manualmente en BD -->
                </select>
            </div>
            
            <button type="submit" class="btn-register">Registrarse</button>
        </form>
        
        <div class="links">
            <a href="login.jsp">¿Ya tienes cuenta? Inicia Sesión</a>
        </div>
        
        <div class="developers">
            <p>Rodrigo Munoz - Julieta Lozano</p>
        </div>
    </div>
</body>
</html>