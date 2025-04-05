package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.dto.ClienteDTO;
import com.tghtechnology.posweb.data.entities.Cliente;
import com.tghtechnology.posweb.data.entities.TipoDocumento;

import java.util.List;

public interface ClienteService {

    List<Cliente> listaCliente();
    
    ClienteDTO obtenerDatosCliente(Long idcliente);

    void ingresarCliente(ClienteDTO clienteDTO);

    void eliminarCliente(Long idcliente);

    void editarCliente(Long idCliente, Cliente cliente);

    Boolean existecliente(Long cliente);

    List<TipoDocumento> tiposDocumentos();

    ClienteDTO buscarClienteByDoc(String doc);

    boolean existeClienteDoc(String doc);
}
