package com.tghtechnology.posweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PosWebBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PosWebBackendApplication.class, args);
	}

	/*
	@Bean
    CommandLineRunner commandLineRunner(CategoriaService categoriaService, ProductoService productoService) {
        return args -> {
            // Probar métodos de CategoriaService
            System.out.println("Probar CategoriaService:");

            // Crear una nueva categoría
            Categoria categoria = new Categoria();
            categoria.setNombreCategoria("Electrónica");
            Categoria categoriaCreada = categoriaService.crearCategoria(categoria);
            System.out.println("Categoria creada: " + categoriaCreada);

            // Obtener todas las categorías
            List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
            System.out.println("Todas las categorías: " + categorias);

            // Obtener categoría por ID
            Optional<Categoria> categoriaPorId = categoriaService.obtenerCategoriaPorId(categoriaCreada.getIdCategoria());
            categoriaPorId.ifPresent(c -> System.out.println("Categoria por ID: " + c));

            // Actualizar categoría
            categoria.setNombreCategoria("Tecnología");
            Categoria categoriaActualizada = categoriaService.actualizarCategoria(categoriaCreada.getIdCategoria(), categoria);
            System.out.println("Categoria actualizada: " + categoriaActualizada);

            // Eliminar categoría
            categoriaService.eliminarCategoria(categoriaCreada.getIdCategoria());
            System.out.println("Categoria eliminada");

            // Probar métodos de ProductoService
            System.out.println("\nProbar ProductoService:");

            // Registrar un nuevo producto
            Producto producto = new Producto();
            producto.setNombreProducto("Smartphone");
            producto.setDescripcion("Último modelo");
            producto.setPrecio(799.99);
            producto.setCantidad(50);
            producto.setEstado(EstadoProducto.DISPONIBLE);
            producto.setCategoria(categoriaCreada);
            Producto productoRegistrado = productoService.registrarProducto(producto);
            System.out.println("Producto registrado: " + productoRegistrado);

            // Listar todos los productos
            List<Producto> productos = productoService.listarProductos();
            System.out.println("Todos los productos: " + productos);

            // Buscar producto por nombre
            Optional<Producto> productoPorNombre = productoService.buscarPorNombre("Smartphone");
            productoPorNombre.ifPresent(p -> System.out.println("Producto por nombre: " + p));

            // Buscar producto por ID
            Optional<Producto> productoPorId = productoService.buscarPorId(productoRegistrado.getIdProducto());
            productoPorId.ifPresent(p -> System.out.println("Producto por ID: " + p));

            // Actualizar producto
            producto.setPrecio(749.99);
            producto.setCantidad(45);
            Producto productoActualizado = productoService.actualizarProducto(productoRegistrado.getIdProducto(), producto);
            System.out.println("Producto actualizado: " + productoActualizado);

            // Cambiar estado del producto
            Producto productoConEstadoCambiado = productoService.cambiarEstadoProducto(productoRegistrado.getIdProducto(), EstadoProducto.NO_DISPONIBLE);
            System.out.println("Producto con estado cambiado: " + productoConEstadoCambiado);

            // Obtener productos por estado
            List<Producto> productosPorEstado = productoService.obtenerProductosPorEstado(EstadoProducto.NO_DISPONIBLE);
            System.out.println("Productos por estado (INACTIVO): " + productosPorEstado);

            // Obtener productos por categoría
            List<Producto> productosPorCategoria = productoService.obtenerProductosPorCategoria(categoriaCreada.getIdCategoria());
            System.out.println("Productos por categoría: " + productosPorCategoria);

            // Eliminar producto
            productoService.eliminarProducto(productoRegistrado.getIdProducto());
            System.out.println("Producto eliminado");
        };
    }
	*/
}
