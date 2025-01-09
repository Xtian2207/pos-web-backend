package com.tghtechnology.posweb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tghtechnology.posweb.data.entities.Cliente;
import com.tghtechnology.posweb.data.entities.TipoDocumento;
import com.tghtechnology.posweb.service.impl.ClienteServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteServiceImpl clienteServiceImpl;

    @GetMapping
    public ResponseEntity<List<Cliente>> listaClientes(){
        List<Cliente> lista =  clienteServiceImpl.listaCliente();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // obtener cliente con su ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClienteId(@PathVariable Long id){
        Cliente cliente = clienteServiceImpl.obtenerDatosCliente(id);
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
            return new ResponseEntity<>("Usuario actualizado correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // eliminar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id) {
        if (clienteServiceImpl.existecliente(id)) {
            clienteServiceImpl.eliminarCliente(id);
            return new ResponseEntity<>("Usuario eliminado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/Documentos")
    public ResponseEntity<List<TipoDocumento>> listaDocumentos(){
        List<TipoDocumento> lista = clienteServiceImpl.tiposDocumentos();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }    

}
