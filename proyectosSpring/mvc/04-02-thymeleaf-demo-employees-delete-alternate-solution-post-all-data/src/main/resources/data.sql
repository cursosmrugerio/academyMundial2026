--
-- Datos semilla para la tabla 'employee' (mismos empleados del curso).
-- No se especifica el id: la columna IDENTITY lo asigna (1..5) y deja
-- el contador de auto-incremento sincronizado para nuevas altas.
--
INSERT INTO employee (first_name, last_name, email) VALUES
    ('Leslie', 'Andrews', 'leslie@luv2code.com'),
    ('Emma', 'Baumgarten', 'emma@luv2code.com'),
    ('Avani', 'Gupta', 'avani@luv2code.com'),
    ('Yuri', 'Petrov', 'yuri@luv2code.com'),
    ('Juan', 'Vega', 'juan@luv2code.com');
