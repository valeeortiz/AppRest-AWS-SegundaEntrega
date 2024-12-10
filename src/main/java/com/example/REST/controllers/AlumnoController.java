package com.example.REST.controllers;

import com.example.REST.models.Alumno;
import com.example.REST.models.SesionesAlumnos;
import com.example.REST.repositories.AlumnoRepository;
import com.example.REST.services.AlumnoService;
import com.example.REST.services.FotoPerfilService;
import com.example.REST.services.SNSTopicService;
import com.example.REST.services.SesionesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {
    
    @Autowired
    FotoPerfilService fotoPerfil;

    @Autowired
    AlumnoRepository alumnoRepository;

    @Autowired
    SNSTopicService snsTopicService;

    @Autowired
    SesionesService sesionesService;

    @GetMapping
    public List<Alumno> getAlumnos() {
        return alumnoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> getAlumnoById(@PathVariable int id) {
        return alumnoRepository.findById(id).map(alumno -> new ResponseEntity<>(alumno, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Alumno> addAlumno(@RequestBody Alumno alumno) {
        Alumno newAlumno = alumnoRepository.saveAndFlush(alumno);
        return new ResponseEntity<>(newAlumno, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alumno> updateAlumno(@PathVariable int id, @RequestBody Alumno alumno) {
        alumno.setId(id);
        alumnoRepository.saveAndFlush(alumno);
        return new ResponseEntity<>(alumno, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable int id) {

        if (alumnoRepository.existsById(id)) {
            alumnoRepository.deleteById(id);
            alumnoRepository.flush();
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/fotoPerfil")
    public ResponseEntity<Alumno> subirFotoPerfil(@PathVariable int id, @RequestParam("foto") MultipartFile multipartFile) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);

        if(alumno!=null){
            String nombreArchivo = alumno.getNombres()+alumno.getApellidos()+alumno.getMatricula()+"fotoPerfil";
            String url = fotoPerfil.fotoPerfilURL(multipartFile, nombreArchivo);
            alumno.setFotoPerfilUrl(url);
            alumnoRepository.saveAndFlush(alumno);

            return new ResponseEntity<>(alumno, HttpStatus.OK);

        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/{id}/email")
    public ResponseEntity<Alumno> mandarCorreo(@PathVariable int id){
        Alumno alumno = alumnoRepository.findById(id).orElse(null);

        if(alumno!=null){
            boolean mensaje = snsTopicService.mandarCorreo("La calificación de "+alumno.getNombres()+" "+alumno.getApellidos()+" es "+alumno.getPromedio(), "Información del alumno "+alumno.getNombres()+" "+alumno.getApellidos());
            if(mensaje){
                return new ResponseEntity<>(alumno,HttpStatus.OK);

            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/{id}/session/login")
    public ResponseEntity<SesionesAlumnos> loginSession(@PathVariable int id, @RequestBody Map<String,String> password) {
        
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        String contraseña = password.get("password");
        if (alumno == null || !alumno.getPaswword().equals(contraseña)) { 
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SesionesAlumnos sesionRegistro = new SesionesAlumnos();
        sesionRegistro.setId(UUID.randomUUID().toString());  
        sesionRegistro.setFecha(Instant.now().getEpochSecond()); 
        sesionRegistro.setAlumnoId(id);
        sesionRegistro.setActive(true);
        sesionRegistro.setSessionString(new java.security.SecureRandom()
            .ints(128, 'a', 'z' + 1)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString());

        boolean sesionCreada = sesionesService.loginSession(sesionRegistro);

        if (sesionCreada) {
            return new ResponseEntity<>(sesionRegistro,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/session/verify")
    public ResponseEntity<?> verifySession(@PathVariable int id, @RequestBody Map<String,String> s){
        
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        String sessionString = s.get("sessionString");
        if(alumno == null || sessionString == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 

        boolean sesionVerificada = sesionesService.verifySession(sessionString);
        Map<String, Object> mensaje = new HashMap<>();

        if(sesionVerificada) {
            mensaje.put("resultado", "OK");
            return new ResponseEntity<>(mensaje,HttpStatus.OK);
        } else {
            mensaje.put("resultado", "BAD");
            return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/{id}/session/logout")
    public ResponseEntity<?> logoutSession(@PathVariable int id, @RequestBody Map<String,String> s){
        
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        String sessionString = s.get("sessionString");
        if(alumno == null || sessionString == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 

        boolean sesionCerrada = sesionesService.logoutSession(sessionString);
        Map<String, Object> mensaje = new HashMap<>();
        if(sesionCerrada) {
            mensaje.put("resultado", "OK");
            return new ResponseEntity<>(mensaje,HttpStatus.OK);
        } else {
            mensaje.put("resultado", "BAD");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}


