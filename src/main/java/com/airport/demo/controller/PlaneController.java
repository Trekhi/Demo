package com.airport.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("plane")
public class PlaneController {

    @Autowired
    private SessionRegistry sessionRegistry; //Traer el objeto para poder acceder a sus metodos y session yo ya lo configure de acuerdo a mi necesidad

    /**
     * Rutas para acceder a la pagina Web
     */
    @GetMapping("/Index")
    public String index(){
        return "Información de vuelo!";
    }

    @GetMapping("/session")
    public ResponseEntity<?> getDetailsSessions(){

        String sessionId = "";
        User userObject = null; // Este objeto User ya esta definido por security

        List<Object> sessions = sessionRegistry.getAllPrincipals(); //Implemento la interfaz tipo lista, ya que es para almacenar toda la información de todos los reguistros, hechos en la plataforma

        return (ResponseEntity<?>) ResponseEntity.ok();
    }

}
