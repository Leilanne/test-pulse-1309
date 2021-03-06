package com.pulse.checkout.controller;

import com.pulse.checkout.model.Cliente;
import com.pulse.checkout.repository.ClienteRepository;
import com.pulse.checkout.services.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
@Api(value = "Api Rest Clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteRepository clienteRepository;

    @GetMapping("/clientes")
    @ApiOperation(value = "Retorna os clientes cadastrados")
    public ResponseEntity<List<Cliente>> buscarTodos(){
        List<Cliente> clientesCadastrados = clienteRepository.findAll();

        if(clientesCadastrados.isEmpty()) {
            return noContent().build();
        }else{
            return ok(clientesCadastrados);
        }
    }

    @GetMapping("/clienteId/{id}")
    @ApiOperation(value = "Busca cliente pelo id")
    public ResponseEntity<Cliente> buscaPorId(@PathVariable Long id){
        return ok(clienteService.buscaPorId(id));
    }

    @GetMapping("/clienteCpf/{cpf}")
    @ApiOperation(value = "Busca cliente pelo CPF")
    public ResponseEntity<Cliente>buscaPorCPF(@PathVariable String cpf){
        return ok(clienteService.buscaPorCpf(cpf));
    }

    @PostMapping("/cliente")
    @ApiOperation(value = "Cadastra um novo cliente")
    public ResponseEntity<Cliente>cadastrar(@Valid @RequestBody Cliente cliente){
        Cliente novoCliente = clienteService.salvar(cliente);
        return new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
    }

    @PutMapping("/clienteUpdate/{id}")
    @ApiOperation(value = "Atualiza dados de um cliente")
    public ResponseEntity<Cliente> atualizar(@Valid @RequestBody Cliente cliente){
        Cliente clienteAtualizado = clienteService.alterar(cliente);
        return new ResponseEntity<>(clienteAtualizado, HttpStatus.CREATED);
    }

    @DeleteMapping("/clienteId/{id}")
    @ApiOperation(value = "Deleta um cliente")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        clienteService.deletaClientePorId(id);
        return noContent().build();
    }
}
