package edu.tienda.cor.controllers;

import edu.tienda.cor.controllers.domain.Cliente;
import edu.tienda.cor.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Array;
import java.net.URI;
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
    public ResponseEntity<?> getClientes() {
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getCliente(@PathVariable String username){

        for(Cliente cliente : clientes){
            if(cliente.getUsername().equalsIgnoreCase(username)){
                return ResponseEntity.ok(cliente);
            }

        }
        //Se usa la exception creada
        throw new ResourceNotFoundException("Cliente no encontrado");
    }

    @PostMapping
    public ResponseEntity altaCliente (@RequestBody Cliente cliente){
        clientes.add(cliente);

        //Obteniendo URL del servicio
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userName}")
                .buildAndExpand(cliente.getUsername())
                .toUri();

        return ResponseEntity.created(location).body(cliente);
    }

    @PutMapping
    public ResponseEntity<?> modificacionCliente(@RequestBody Cliente cliente){

        Cliente clienteEncontrado = clientes.stream().
                filter(cli -> cli.getUsername().equalsIgnoreCase(cliente.getUsername())).
                findFirst().orElseThrow();

        clienteEncontrado.setNombre(cliente.getNombre());
        clienteEncontrado.setPassword(cliente.getPassword());

        return ResponseEntity.ok(clienteEncontrado);
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity borrarCliente(@PathVariable String userName){
        Cliente clienteEncontrado = clientes.stream().
                filter(cli -> cli.getUsername().equalsIgnoreCase(userName)).
                findFirst().orElseThrow();

        clientes.remove(clienteEncontrado);
        return ResponseEntity.noContent().build();
    }
}
