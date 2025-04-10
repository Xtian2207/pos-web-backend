package com.tghtechnology.posweb.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tghtechnology.posweb.data.dto.ClienteDTO;
import com.tghtechnology.posweb.data.entities.Cliente;
import com.tghtechnology.posweb.data.entities.TipoDocumento;
import com.tghtechnology.posweb.data.mapper.ClienteMapper;
import com.tghtechnology.posweb.data.repository.ClienteRepository;
import com.tghtechnology.posweb.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Override
    public List<Cliente> listaCliente() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes;
    }

    @Override
    public ClienteDTO obtenerDatosCliente(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(clienteMapper::toDto).orElse(null);
    }

    @Override
    public void ingresarCliente(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }

        // Convertir DTO a entidad
        Cliente cliente = clienteMapper.toEntity(clienteDTO);

        try {
            clienteRepository.save(cliente);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el cliente", e);
        }
    }

    @Override
    public void eliminarCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        }
    }

    @Override
    public void editarCliente(Long id, Cliente cliente) {
        if (clienteRepository.existsById(id)) {
            if (cliente.validarNumeroDocumento()) {
                cliente.setIdCliente(id);
                clienteRepository.save(cliente);
            }
        }
    }

    @Override
    public Boolean existecliente(Long id) {
        return clienteRepository.existsById(id);
    }

    @Override
    public ClienteDTO buscarClienteByDoc(String doc) {
        Optional<Cliente> cliente = clienteRepository.obtenerClienteByDocu(doc);
        return cliente.map(clienteMapper::toDto).orElse(null);
    }

    @Override
    public List<TipoDocumento> tiposDocumentos() {
        TipoDocumento[] documentos = TipoDocumento.values();
        return Arrays.asList(documentos);
    }

    @Override
    public boolean existeClienteDoc(String documento) {
        return clienteRepository.findByDocument(documento).isPresent();
    }
}