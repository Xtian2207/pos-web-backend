package com.tghtechnology.posweb;

import com.tghtechnology.posweb.data.entities.Categoria;
import com.tghtechnology.posweb.data.entities.EstadoProducto;
import com.tghtechnology.posweb.data.entities.Producto;
import com.tghtechnology.posweb.service.CategoriaService;
import com.tghtechnology.posweb.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @Override
    public void run(String... args) throws Exception {
        // PRUEBAS PARA CATEGORIA
        probarCategoriaService();

        // PRUEBAS PARA PRODUCTO
        probarProductoService();
    }

    private void probarCategoriaService() {
        System.out.println("==== Pruebas para CategoriaService ====");

        // Crear categorías
        Categoria categoriaElectronica = categoriaService.crearCategoria(new Categoria("Electrónica"));
        Categoria categoriaHogar = categoriaService.crearCategoria(new Categoria("Hogar"));
        System.out.println("Categorías creadas:");

        // Imprimir todas las categorías
        List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
        categorias.forEach(c -> System.out.println("-> " + c.getNombreCategoria()));

        // Obtener categoría por ID
        Optional<Categoria> categoriaPorId = categoriaService.obtenerCategoriaPorId(categoriaElectronica.getIdCategoria());
        if (categoriaPorId.isPresent()) {
            System.out.println("Categoría obtenida por ID: " + categoriaPorId.get().getNombreCategoria());
        } else {
            System.out.println("Categoría no encontrada por ID.");
        }

        // Actualizar categoría
        categoriaElectronica.setNombreCategoria("Electrónica y Tecnología");
        Categoria categoriaActualizada = categoriaService.actualizarCategoria(categoriaElectronica.getIdCategoria(), categoriaElectronica);
        System.out.println("Categoría actualizada: " + categoriaActualizada.getNombreCategoria());

        // Eliminar categoría
        categoriaService.eliminarCategoria(categoriaHogar.getIdCategoria());
        System.out.println("Categoría 'Hogar' eliminada.");
        System.out.println("==== Fin pruebas CategoriaService ====\n");
    }

    private void probarProductoService() throws Exception {
        System.out.println("==== Pruebas para ProductoService ====");

        // Crear categorías para asociar productos
        Categoria categoriaElectronica = categoriaService.crearCategoria(new Categoria("Electrónica"));
        Categoria categoriaHogar = categoriaService.crearCategoria(new Categoria("Hogar"));

        // Crear productos
        Producto producto1 = new Producto("Laptop Gamer", "Una laptop poderosa para tus juegos", 1500.0, 10, EstadoProducto.DISPONIBLE);
        productoService.registrarProducto(categoriaElectronica.getIdCategoria(), producto1);

        Producto producto2 = new Producto("Sartén antiadherente", "Cocina tus alimentos de forma saludable", 25.0, 20, EstadoProducto.DISPONIBLE);
        productoService.registrarProducto(categoriaHogar.getIdCategoria(), producto2);

        System.out.println("Productos registrados:");

        // Imprimir todos los productos
        List<Producto> productos = productoService.listarProductos();
        productos.forEach(p -> System.out.println("-> " + p.getNombreProducto() + " (" + p.getCategoria().getNombreCategoria() + ")"));

        // Buscar producto por nombre
        System.out.println("\nBuscar producto por nombre:");
        Optional<Producto> productoBuscado = productoService.buscarPorNombre("Laptop Gamer");
        productoBuscado.ifPresentOrElse(
                p -> System.out.println("Producto encontrado: " + p.getNombreProducto()),
                () -> System.out.println("Producto no encontrado.")
        );

        // Buscar producto por ID
        Optional<Producto> productoPorId = productoService.buscarPorId(producto1.getIdProducto());
        productoPorId.ifPresentOrElse(
                p -> System.out.println("Producto encontrado por ID: " + p.getNombreProducto()),
                () -> System.out.println("Producto no encontrado por ID.")
        );

        // Actualizar producto
        Producto productoActualizado = new Producto("Laptop Pro", "Laptop con más potencia", 2000.0, 8, EstadoProducto.DISPONIBLE);
        Producto productoModificado = productoService.actualizarProducto(producto1.getIdProducto(), productoActualizado);
        System.out.println("Producto actualizado: " + productoModificado.getNombreProducto());

        // Cambiar estado de producto
        Producto productoConEstadoCambiado = productoService.cambiarEstadoProducto(producto1.getIdProducto(), EstadoProducto.NO_DISPONIBLE);
        System.out.println("Estado del producto cambiado a: " + productoConEstadoCambiado.getEstado());

        // Obtener productos por estado
        System.out.println("\nProductos disponibles:");
        List<Producto> productosDisponibles = productoService.obtenerProductosPorEstado(EstadoProducto.DISPONIBLE);
        productosDisponibles.forEach(p -> System.out.println("-> " + p.getNombreProducto()));

        // Obtener productos por categoría
        System.out.println("\nProductos en la categoría Electrónica:");
        List<Producto> productosEnCategoria = productoService.obtenerProductosPorCategoria(categoriaElectronica.getIdCategoria());
        productosEnCategoria.forEach(p -> System.out.println("-> " + p.getNombreProducto()));

        // Eliminar producto
        productoService.eliminarProducto(producto2.getIdProducto());
        System.out.println("Producto 'Sartén antiadherente' eliminado.");

        System.out.println("==== Fin pruebas ProductoService ====\n");
    }
}
