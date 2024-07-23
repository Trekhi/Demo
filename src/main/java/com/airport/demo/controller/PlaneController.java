package com.airport.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("plane")
public class PlaneController {

    @Autowired //se utiliza para inyectar dependencias en otras clases o componentes. Esto significa que Spring se encarga de buscar y proporcionar instancias de las dependencias necesarias a la clase que utiliza la anotación.
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
        for (Object session :sessions){ //Recuperación de información y visualización de registro
            if(session instanceof  User){
                userObject = (User) session; //Información del usuario
            }
            List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(session, false); //Información guardada en una lista para el tema de sessión se llama el Sessión para traer la información que se nesecita.

            for(SessionInformation sessionInformation : sessionInformations){ //información de la session
                sessionId = sessionInformation.getSessionId(); //OBtención del ID
            }
        }

        Map<String, Object> response = new HashMap<>(); // Creo un nuevo arreglo con la información que necesito, para desarrollar
        response.put("response", "Hello World");
        response.put("sessionId", sessionId);
        response.put("sessionUser", userObject);
        return ResponseEntity.ok(response);
    }

}
