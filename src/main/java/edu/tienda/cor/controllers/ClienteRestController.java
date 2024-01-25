package edu.tienda.cor.controllers;

import edu.tienda.cor.controllers.domain.Cliente;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteRestController {

    private List<Cliente> clientes = new ArrayList<>(Arrays.asList(
            new Cliente("pjr", "pjr123", "Patrick"),
            new Cliente("lgh", "lgh123", "Lety"),
            new Cliente("mrs", "mrs123", "Maga")
    ));

    @GetMapping
    public List<Cliente> getClientes() {
        return clientes;
    }

    @GetMapping("/{username}")
    public Cliente getCliente(@PathVariable String username){
        /*for (Cliente cli : clientes){
            if(cli.getUsername().equalsIgnoreCase(username)){
                return cli;
            }
        }
        return null;*/
        return clientes.stream().
                filter(cliente -> cliente.getUsername().equalsIgnoreCase(username))
                .findFirst().orElseThrow();
    }

    @PostMapping
    public Cliente altaCliente (@RequestBody Cliente cliente){
        clientes.add(cliente);
        return cliente;
    }

    @PutMapping
    public Cliente modificacionCliente(@RequestBody Cliente cliente){

        Cliente clienteEncontrado = clientes.stream().
                filter(cli -> cli.getUsername().equalsIgnoreCase(cliente.getUsername())).
                findFirst().orElseThrow();

        clienteEncontrado.setNombre(cliente.getNombre());
        clienteEncontrado.setPassword(cliente.getPassword());

        return clienteEncontrado;
    }

    @DeleteMapping("/{userName}")
    public void borrarCliente(@PathVariable String userName){
        Cliente clienteEncontrado = clientes.stream().
                filter(cli -> cli.getUsername().equalsIgnoreCase(userName)).
                findFirst().orElseThrow();

        clientes.remove(clienteEncontrado);
    }
}
