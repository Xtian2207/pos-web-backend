package com.tghtechnology.posweb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tghtechnology.posweb.data.dto.ClienteDTO;
import com.tghtechnology.posweb.data.entities.Cliente;
import com.tghtechnology.posweb.data.entities.TipoDocumento;
import com.tghtechnology.posweb.service.ClienteService;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"http://localhost:5173",
"https://punto-de-venta.netlify.app"})
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteServiceImpl;

    @GetMapping
    public ResponseEntity<List<Cliente>> listaClientes(){
        List<Cliente> lista =  clienteServiceImpl.listaCliente();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

     // nuevo cliente
    @PostMapping
    public ResponseEntity<String> ingresarNuevoCliente(@Valid @RequestBody ClienteDTO clienteDTO){
        try {
            clienteServiceImpl.ingresarCliente(clienteDTO);
            return new ResponseEntity<>("Cliente creado correctamente", HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Error: El correo ya está registrado.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear cliente: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // obtener cliente con su ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerClienteId(@PathVariable Long id){
        ClienteDTO cliente = clienteServiceImpl.obtenerDatosCliente(id);
        if (cliente !=null) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // actualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarCliente(@PathVariable Long id, @Valid @RequestBody Cliente cliente){
        if(clienteServiceImpl.existecliente(id)){
            clienteServiceImpl.editarCliente(id, cliente);
            return new ResponseEntity<>("Cliente actualizado correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // eliminar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id) {
        if (clienteServiceImpl.existecliente(id)) {
            clienteServiceImpl.eliminarCliente(id);
            return new ResponseEntity<>("Cliente eliminado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }
    }


    // TIPOS DE DOCUMENTOS
    @GetMapping("/Documentos")
    public ResponseEntity<List<TipoDocumento>> listaDocumentos(){
        List<TipoDocumento> lista = clienteServiceImpl.tiposDocumentos();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }    

}
